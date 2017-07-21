package org.telegram.abilitybots.api.bot;

import org.apache.commons.io.IOUtils;
import org.telegram.abilitybots.api.db.DBContext;
import org.telegram.abilitybots.api.objects.*;
import org.telegram.abilitybots.api.sender.DefaultMessageSender;
import org.telegram.abilitybots.api.sender.MessageSender;
import org.telegram.abilitybots.api.util.AbilityUtils;
import org.telegram.abilitybots.api.util.Pair;
import org.telegram.abilitybots.api.util.Trio;
import org.telegram.telegrambots.api.methods.GetFile;
import org.telegram.telegrambots.api.methods.send.SendDocument;
import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.bots.DefaultBotOptions;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.exceptions.TelegramApiException;
import org.telegram.telegrambots.logging.BotLogger;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.PrintStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.BiFunction;
import java.util.function.Predicate;
import java.util.stream.Stream;

import static com.google.common.base.Strings.isNullOrEmpty;
import static java.lang.String.format;
import static java.time.ZonedDateTime.now;
import static java.util.Arrays.stream;
import static java.util.Objects.nonNull;
import static java.util.Optional.ofNullable;
import static java.util.function.Function.identity;
import static java.util.regex.Pattern.CASE_INSENSITIVE;
import static java.util.regex.Pattern.compile;
import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toMap;
import static jersey.repackaged.com.google.common.base.Throwables.propagate;
import static org.telegram.abilitybots.api.db.MapDBContext.onlineInstance;
import static org.telegram.abilitybots.api.objects.Ability.builder;
import static org.telegram.abilitybots.api.objects.EndUser.fromUser;
import static org.telegram.abilitybots.api.objects.Flag.*;
import static org.telegram.abilitybots.api.objects.Locality.*;
import static org.telegram.abilitybots.api.objects.MessageContext.newContext;
import static org.telegram.abilitybots.api.objects.Privacy.*;
import static org.telegram.abilitybots.api.util.AbilityUtils.*;

/**
 * The <b>father</b> of all ability bots. Bots that need to utilize abilities need to extend this bot.
 * <p>
 * It's important to note that this bot strictly extends {@link TelegramLongPollingBot}.
 * <p>
 * All bots extending the {@link AbilityBot} get implicit abilities:
 * <ul>
 * <li>/claim - Claims this bot</li>
 * <ul>
 * <li>Sets the user as the {@link Privacy#CREATOR} of the bot</li>
 * <li>Only the user with the ID returned by {@link AbilityBot#creatorId()} can genuinely claim the bot</li>
 * </ul>
 * <li>/commands - reports all user-defined commands (abilities)</li>
 * <ul>
 * <li>The same format acceptable by BotFather</li>
 * </ul>
 * <li>/backup - returns a backup of the bot database</li>
 * <li>/recover - recovers the database</li>
 * <li>/promote <code>@username</code> - promotes user to bot admin</li>
 * <li>/demote <code>@username</code> - demotes bot admin to user</li>
 * <li>/ban <code>@username</code> - bans the user from accessing your bot commands and features</li>
 * <li>/unban <code>@username</code> - lifts the ban from the user</li>
 * </ul>
 * <p>
 * Additional information of the implicit abilities are present in the methods that declare them.
 * <p>
 * The two most important handles in the AbilityBot are the {@link DBContext} <b><code>db</code></b> and the {@link MessageSender} <b><code>sender</code></b>.
 * All bots extending AbilityBot can use both handles in their update consumers.
 *
 * @author Abbas Abou Daya
 */
public abstract class AbilityBot extends TelegramLongPollingBot {
  private static final String TAG = AbilityBot.class.getSimpleName();

  // DB objects
  public static final String ADMINS = "ADMINS";
  public static final String USERS = "USERS";
  public static final String USER_ID = "USER_ID";
  public static final String BLACKLIST = "BLACKLIST";

  // Factory commands
  protected static final String DEFAULT = "default";
  protected static final String CLAIM = "claim";
  protected static final String BAN = "ban";
  protected static final String PROMOTE = "promote";
  protected static final String DEMOTE = "demote";
  protected static final String UNBAN = "unban";
  protected static final String BACKUP = "backup";
  protected static final String RECOVER = "recover";
  protected static final String COMMANDS = "commands";

  // Messages
  protected static final String RECOVERY_MESSAGE = "I am ready to receive the backup file. Please reply to this message with the backup file attached.";
  protected static final String RECOVER_SUCCESS = "I have successfully recovered.";

  // DB and sender
  protected final DBContext db;
  protected MessageSender sender;

  // Bot token and username
  private final String botToken;
  private final String botUsername;

  // Ability registry
  private Map<String, Ability> abilities;

  // Reply registry
  private List<Reply> replies;

  protected AbilityBot(String botToken, String botUsername, DBContext db, DefaultBotOptions botOptions) {
    super(botOptions);

    this.botToken = botToken;
    this.botUsername = botUsername;
    this.db = db;
    this.sender = new DefaultMessageSender(this);

    registerAbilities();
  }

  protected AbilityBot(String botToken, String botUsername, DBContext db) {
    this(botToken, botUsername, db, new DefaultBotOptions());
  }

  protected AbilityBot(String botToken, String botUsername, DefaultBotOptions botOptions) {
    this(botToken, botUsername, onlineInstance(botUsername), botOptions);
  }

  protected AbilityBot(String botToken, String botUsername) {
    this(botToken, botUsername, onlineInstance(botUsername));
  }

  public abstract int creatorId();

  /**
   * @return the map of ID -> EndUser
   */
  protected Map<Integer, EndUser> users() {
    return db.getMap(USERS);
  }

  /**
   * @return the map of Username -> ID
   */
  protected Map<String, Integer> userIds() {
    return db.getMap(USER_ID);
  }

  /**
   * @return a blacklist containing all the IDs of the banned users
   */
  protected Set<Integer> blacklist() {
    return db.getSet(BLACKLIST);
  }

  /**
   * @return an admin set of all the IDs of bot administrators
   */
  protected Set<Integer> admins() {
    return db.getSet(ADMINS);
  }

  /**
   * This method contains the stream of actions that are applied on any update.
   * <p>
   * It will correctly handle addition of users into the DB and the execution of abilities and replies.
   *
   * @param update the update received by Telegram's API
   */
  @Override
  public void onUpdateReceived(Update update) {
    BotLogger.info(format("New update [%s] received at %s", update.getUpdateId(), now()), format("%s - %s", TAG, botUsername));
    BotLogger.info(update.toString(), TAG);
    long millisStarted = System.currentTimeMillis();

    Stream.of(update)
        .filter(this::checkGlobalFlags)
        .filter(this::checkBlacklist)
        .map(this::addUser)
        .filter(this::filterReply)
        .map(this::getAbility)
        .filter(this::validateAbility)
        .filter(this::checkPrivacy)
        .filter(this::checkLocality)
        .filter(this::checkInput)
        .filter(this::checkMessageFlags)
        .map(this::getContext)
        .map(this::consumeUpdate)
        .forEach(this::postConsumption);

    long processingTime = System.currentTimeMillis() - millisStarted;
    BotLogger.info(format("Processing of update [%s] ended at %s%n---> Processing time: [%d ms] <---%n", update.getUpdateId(), now(), processingTime), format("%s - %s", TAG, botUsername));
  }

  @Override
  public String getBotToken() {
    return botToken;
  }

  @Override
  public String getBotUsername() {
    return botUsername;
  }

  /**
   * Test the update against the provided global flags. The default implementation requires a {@link Flag#MESSAGE}.
   * <p>
   * This method should be <b>overridden</b> if the user wants updates that don't require a MESSAGE to pass through.
   *
   * @param update a Telegram {@link Update}
   * @return <tt>true</tt> if the update satisfies the global flags
   */
  protected boolean checkGlobalFlags(Update update) {
    return MESSAGE.test(update);
  }

  /**
   * Gets the user with the specified username.
   *
   * @param username the username of the required user
   * @return the user
   */
  protected EndUser getUser(String username) {
    Integer id = userIds().get(username.toLowerCase());
    if (id == null) {
      throw new IllegalStateException(format("Could not find ID corresponding to username [%s]", username));
    }

    return getUser(id);
  }

  /**
   * Gets the user with the specified ID.
   *
   * @param id the id of the required user
   * @return the user
   */
  protected EndUser getUser(int id) {
    EndUser endUser = users().get(id);
    if (endUser == null) {
      throw new IllegalStateException(format("Could not find user corresponding to id [%d]", id));
    }

    return endUser;
  }

  /**
   * Gets the user with the specified username. If user was not found, the bot will send a message on Telegram.
   *
   * @param username the username of the required user
   * @return the id of the user
   */
  protected int getUserIdSendError(String username, long chatId) {
    try {
      return getUser(username).id();
    } catch (IllegalStateException ex) {
      sender.send(format("Sorry, I could not find the user [%s].", username), chatId);
      throw propagate(ex);
    }
  }

  /**
   * <p>
   * Format of the report:
   * <p>
   * [command1] - [description1]
   * <p>
   * [command2] - [description2]
   * <p>
   * ...
   * <p>
   * Once you invoke it, the bot will send the available commands to the chat. This is a public ability so anyone can invoke it.
   * <p>
   * Usage: <code>/commands</code>
   *
   * @return the ability to report commands defined by the child bot.
   */
  public Ability reportCommands() {
    return builder()
        .name(COMMANDS)
        .locality(ALL)
        .privacy(PUBLIC)
        .input(0)
        .action(ctx -> {
          String commands = abilities.entrySet().stream()
              .filter(entry -> nonNull(entry.getValue().info()))
              .map(entry -> {
                String name = entry.getValue().name();
                String info = entry.getValue().info();
                return format("%s - %s", name, info);
              })
              .sorted()
              .reduce((a, b) -> format("%s%n%s", a, b))
              .orElse("No public commands found.");

          sender.send(commands, ctx.chatId());
        })
        .build();
  }

  /**
   * This backup ability returns the object defined by {@link DBContext#backup()} as a message document.
   * <p>
   * This is a high-profile ability and is restricted to the CREATOR only.
   * <p>
   * Usage: <code>/backup</code>
   *
   * @return the ability to back-up the database of the bot
   */
  public Ability backupDB() {
    return builder()
        .name(BACKUP)
        .locality(USER)
        .privacy(CREATOR)
        .input(0)
        .action(ctx -> {
          File backup = new File("backup.json");

          try (PrintStream printStream = new PrintStream(backup)) {
            printStream.print(db.backup());
            sender.sendDocument(new SendDocument()
                .setNewDocument(backup)
                .setChatId(ctx.chatId())
            );
          } catch (FileNotFoundException e) {
            BotLogger.error("Error while fetching backup", TAG, e);
          } catch (TelegramApiException e) {
            BotLogger.error("Error while sending document/backup file", TAG, e);
          }
        })
        .build();
  }

  /**
   * Recovers the bot database using {@link DBContext#recover(Object)}.
   * <p>
   * The bot recovery process hugely depends on the implementation of the recovery method of {@link DBContext}.
   * <p>
   * Usage: <code>/recover</code>
   *
   * @return the ability to recover the database of the bot
   */
  public Ability recoverDB() {
    return builder()
        .name(RECOVER)
        .locality(USER)
        .privacy(CREATOR)
        .input(0)
        .action(ctx -> sender.forceReply(RECOVERY_MESSAGE, ctx.chatId()))
        .reply(update -> {
          Long chatId = update.getMessage().getChatId();
          String fileId = update.getMessage().getDocument().getFileId();

          try (FileReader reader = new FileReader(downloadFileWithId(fileId))) {
            String backupData = IOUtils.toString(reader);
            if (db.recover(backupData)) {
              sender.send(RECOVER_SUCCESS, chatId);
            } else {
              sender.send("Oops, something went wrong during recovery.", chatId);
            }
          } catch (Exception e) {
            BotLogger.error("Could not recover DB from backup", TAG, e);
            sender.send("I have failed to recover.", chatId);
          }
        }, MESSAGE, DOCUMENT, REPLY, isReplyTo(RECOVERY_MESSAGE))
        .build();
  }

  /**
   * Banned users are accumulated in the blacklist. Use {@link DBContext#getSet(String)} with name specified by {@link AbilityBot#BLACKLIST}.
   * <p>
   * Usage: <code>/ban @username</code>
   * <p>
   * <u>Note that admins who try to ban the creator, get banned.</u>
   *
   * @return the ability to ban the user from any kind of <b>bot interaction</b>
   */
  public Ability banUser() {
    return builder()
        .name(BAN)
        .locality(ALL)
        .privacy(ADMIN)
        .input(1)
        .action(ctx -> {
          String username = stripTag(ctx.firstArg());
          int userId = getUserIdSendError(username, ctx.chatId());
          String bannedUser;

          // Protection from abuse
          if (userId == creatorId()) {
            userId = ctx.user().id();
            bannedUser = isNullOrEmpty(ctx.user().username()) ? addTag(ctx.user().username()) : ctx.user().shortName();
          } else {
            bannedUser = addTag(username);
          }

          Set<Integer> blacklist = blacklist();
          if (blacklist.contains(userId))
            sender.sendMd(format("%s is already *banned*.", bannedUser), ctx.chatId());
          else {
            blacklist.add(userId);
            sender.sendMd(format("%s is now *banned*.", bannedUser), ctx.chatId());
          }
        })
        .post(commitTo(db))
        .build();
  }

  /**
   * Usage: <code>/unban @username</code>
   *
   * @return the ability to unban a user
   */
  public Ability unbanUser() {
    return builder()
        .name(UNBAN)
        .locality(ALL)
        .privacy(ADMIN)
        .input(1)
        .action(ctx -> {
          String username = stripTag(ctx.firstArg());
          Integer userId = getUserIdSendError(username, ctx.chatId());

          Set<Integer> blacklist = blacklist();

          if (!blacklist.remove(userId))
            sender.sendMd(format("@%s is *not* on the *blacklist*.", username), ctx.chatId());
          else {
            sender.sendMd(format("@%s, your ban has been *lifted*.", username), ctx.chatId());
          }
        })
        .post(commitTo(db))
        .build();
  }

  /**
   * @return the ability to promote a user to a bot admin
   */
  public Ability promoteAdmin() {
    return builder()
        .name(PROMOTE)
        .locality(ALL)
        .privacy(ADMIN)
        .input(1)
        .action(ctx -> {
          String username = stripTag(ctx.firstArg());
          Integer userId = getUserIdSendError(username, ctx.chatId());

          Set<Integer> admins = admins();
          if (admins.contains(userId))
            sender.sendMd(format("@%s is already an *admin*.", username), ctx.chatId());
          else {
            admins.add(userId);
            sender.sendMd(format("@%s has been *promoted*.", username), ctx.chatId());
          }
        }).post(commitTo(db))
        .build();
  }

  /**
   * @return the ability to demote an admin to a user
   */
  public Ability demoteAdmin() {
    return builder()
        .name(DEMOTE)
        .locality(ALL)
        .privacy(ADMIN)
        .input(1)
        .action(ctx -> {
          String username = stripTag(ctx.firstArg());
          Integer userId = getUserIdSendError(username, ctx.chatId());

          Set<Integer> admins = admins();
          if (admins.remove(userId)) {
            sender.sendMd(format("@%s has been *demoted*.", username), ctx.chatId());
          } else {
            sender.sendMd(format("@%s is *not* an *admin*.", username), ctx.chatId());
          }
        })
        .post(commitTo(db))
        .build();
  }

  /**
   * Regular users and admins who try to claim the bot will get <b>banned</b>.
   *
   * @return the ability to claim yourself as the master and creator of the bot
   */
  public Ability claimCreator() {
    return builder()
        .name(CLAIM)
        .locality(ALL)
        .privacy(PUBLIC)
        .input(0)
        .action(ctx -> {
          if (ctx.user().id() == creatorId()) {
            Set<Integer> admins = admins();
            int id = creatorId();
            long chatId = ctx.chatId();

            if (admins.contains(id))
              sender.send("You're already my master.", chatId);
            else {
              admins.add(id);
              sender.send("You're now my master.", chatId);
            }
          } else {
            // This is not a joke
            abilities.get(BAN).action().accept(newContext(ctx.update(), ctx.user(), ctx.chatId(), ctx.user().username()));
          }
        })
        .post(commitTo(db))
        .build();
  }

  /**
   * Registers the declared abilities using method reflection. Also, replies are accumulated using the built abilities and standalone methods that return a Reply.
   * <p>
   * <b>Only abilities and replies with the <u>public</u> accessor are registered!</b>
   */
  private void registerAbilities() {
    try {
      abilities = stream(this.getClass().getMethods())
          .filter(method -> method.getReturnType().equals(Ability.class))
          .map(this::returnAbility)
          .collect(toMap(Ability::name, identity()));

      Stream<Reply> methodReplies = stream(this.getClass().getMethods())
          .filter(method -> method.getReturnType().equals(Reply.class))
          .map(this::returnReply);

      Stream<Reply> abilityReplies = abilities.values().stream()
          .flatMap(ability -> ability.replies().stream());

      replies = Stream.concat(methodReplies, abilityReplies).collect(toList());
    } catch (IllegalStateException e) {
      BotLogger.error(TAG, "Duplicate names found while registering abilities. Make sure that the abilities declared don't clash with the reserved ones.", e);
      throw propagate(e);
    }

  }

  /**
   * Invokes the method and retrieves its return {@link Ability}.
   *
   * @param method a method that returns an ability
   * @return the ability returned by the method
   */
  private Ability returnAbility(Method method) {
    try {
      return (Ability) method.invoke(this);
    } catch (IllegalAccessException | InvocationTargetException e) {
      BotLogger.error("Could not add ability", TAG, e);
      throw propagate(e);
    }
  }

  /**
   * Invokes the method and retrieves its returned Reply.
   *
   * @param method a method that returns a reply
   * @return the reply returned by the method
   */
  private Reply returnReply(Method method) {
    try {
      return (Reply) method.invoke(this);
    } catch (IllegalAccessException | InvocationTargetException e) {
      BotLogger.error("Could not add reply", TAG, e);
      throw propagate(e);
    }
  }

  private void postConsumption(Pair<MessageContext, Ability> pair) {
    ofNullable(pair.b().postAction())
        .ifPresent(consumer -> consumer.accept(pair.a()));
  }

  Pair<MessageContext, Ability> consumeUpdate(Pair<MessageContext, Ability> pair) {
    pair.b().action().accept(pair.a());
    return pair;
  }

  Pair<MessageContext, Ability> getContext(Trio<Update, Ability, String[]> trio) {
    Update update = trio.a();
    EndUser user = fromUser(AbilityUtils.getUser(update));

    return Pair.of(newContext(update, user, getChatId(update), trio.c()), trio.b());
  }

  boolean checkBlacklist(Update update) {
    Integer id = AbilityUtils.getUser(update).getId();

    return id == creatorId() || !blacklist().contains(id);
  }

  boolean checkInput(Trio<Update, Ability, String[]> trio) {
    String[] tokens = trio.c();
    int abilityTokens = trio.b().tokens();

    boolean isOk = abilityTokens == 0 || (tokens.length > 0 && tokens.length == abilityTokens);

    if (!isOk)
      sender.send(String.format("Sorry, this feature requires %d additional %s.", abilityTokens, abilityTokens == 1 ? "input" : "inputs"), getChatId(trio.a()));
    return isOk;
  }

  boolean checkLocality(Trio<Update, Ability, String[]> trio) {
    Update update = trio.a();
    Locality locality = isUserMessage(update) ? USER : GROUP;
    Locality abilityLocality = trio.b().locality();

    boolean isOk = abilityLocality == ALL || locality == abilityLocality;

    if (!isOk)
      sender.send(String.format("Sorry, %s-only feature.", abilityLocality.toString().toLowerCase()), getChatId(trio.a()));
    return isOk;
  }

  boolean checkPrivacy(Trio<Update, Ability, String[]> trio) {
    Update update = trio.a();
    EndUser user = fromUser(AbilityUtils.getUser(update));
    Privacy privacy;
    int id = user.id();

    privacy = isCreator(id) ? CREATOR : isAdmin(id) ? ADMIN : PUBLIC;

    boolean isOk = privacy.compareTo(trio.b().privacy()) >= 0;

    if (!isOk)
      sender.send(String.format("Sorry, %s-only feature.", trio.b().privacy().toString().toLowerCase()), getChatId(trio.a()));
    return isOk;
  }

  private boolean isCreator(int id) {
    return id == creatorId();
  }

  private boolean isAdmin(Integer id) {
    return admins().contains(id);
  }

  boolean validateAbility(Trio<Update, Ability, String[]> trio) {
    return trio.b() != null;
  }

  Trio<Update, Ability, String[]> getAbility(Update update) {
    // Handle updates without messages
    // Passing through this function means that the global flags have passed
    Message msg = update.getMessage();
    if (!update.hasMessage() || !msg.hasText())
      return Trio.of(update, abilities.get(DEFAULT), new String[]{});

    // Priority goes to text before captions
    String[] tokens = msg.getText().split(" ");

    if (tokens[0].startsWith("/")) {
      String abilityToken = stripBotUsername(tokens[0].substring(1));
      Ability ability = abilities.get(abilityToken);
      tokens = Arrays.copyOfRange(tokens, 1, tokens.length);
      return Trio.of(update, ability, tokens);
    } else {
      Ability ability = abilities.get(DEFAULT);
      return Trio.of(update, ability, tokens);
    }
  }

  private String stripBotUsername(String token) {
    return compile(format("@%s", botUsername), CASE_INSENSITIVE)
        .matcher(token)
        .replaceAll("");
  }

  Update addUser(Update update) {
    EndUser endUser = fromUser(AbilityUtils.getUser(update));

    users().compute(endUser.id(), (id, user) -> {
      if (user == null) {
        updateUserId(user, endUser);
        return endUser;
      }

      if (!user.equals(endUser)) {
        updateUserId(user, endUser);
        return endUser;
      }

      return user;
    });

    db.commit();
    return update;
  }

  private void updateUserId(EndUser oldUser, EndUser newUser) {
    if (oldUser != null && oldUser.username() != null) {
      // Remove old username -> ID
      userIds().remove(oldUser.username());
    }

    if (newUser.username() != null) {
      // Add new mapping with the new username
      userIds().put(newUser.username().toLowerCase(), newUser.id());
    }
  }

  boolean filterReply(Update update) {
    return replies.stream()
        .filter(reply -> reply.isOkFor(update))
        .map(reply -> {
          reply.actOn(update);
          return false;
        })
        .reduce(true, Boolean::logicalAnd);
  }

  boolean checkMessageFlags(Trio<Update, Ability, String[]> trio) {
    Ability ability = trio.b();
    Update update = trio.a();
    
    // The following variable is required to avoid bug #JDK-8044546
    BiFunction<Boolean, Predicate<Update>, Boolean> flagAnd = (flag, nextFlag) -> flag && nextFlag.test(update);
    return ability.flags().stream()
        .reduce(true, flagAnd, Boolean::logicalAnd);
  }

  private File downloadFileWithId(String fileId) throws TelegramApiException {
    return sender.downloadFile(sender.getFile(new GetFile().setFileId(fileId)));
  }
}