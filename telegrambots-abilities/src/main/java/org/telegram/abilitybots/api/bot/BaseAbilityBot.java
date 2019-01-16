package org.telegram.abilitybots.api.bot;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableList.Builder;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ListMultimap;
import com.google.common.collect.Multimap;
import org.apache.commons.io.IOUtils;
import org.jetbrains.annotations.NotNull;
import org.telegram.abilitybots.api.db.DBContext;
import org.telegram.abilitybots.api.objects.*;
import org.telegram.abilitybots.api.sender.DefaultSender;
import org.telegram.abilitybots.api.sender.MessageSender;
import org.telegram.abilitybots.api.sender.SilentSender;
import org.telegram.abilitybots.api.util.AbilityExtension;
import org.telegram.abilitybots.api.util.AbilityUtils;
import org.telegram.abilitybots.api.util.Pair;
import org.telegram.abilitybots.api.util.Trio;
import org.telegram.telegrambots.bots.DefaultAbsSender;
import org.telegram.telegrambots.bots.DefaultBotOptions;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.GetFile;
import org.telegram.telegrambots.meta.api.methods.groupadministration.GetChatAdministrators;
import org.telegram.telegrambots.meta.api.methods.send.SendDocument;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.logging.BotLogger;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.PrintStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;
import java.util.Map.Entry;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.google.common.base.Strings.isNullOrEmpty;
import static com.google.common.collect.MultimapBuilder.hashKeys;
import static java.lang.String.format;
import static java.time.ZonedDateTime.now;
import static java.util.Arrays.stream;
import static java.util.Comparator.comparing;
import static java.util.Objects.nonNull;
import static java.util.Optional.ofNullable;
import static java.util.regex.Pattern.CASE_INSENSITIVE;
import static java.util.regex.Pattern.compile;
import static java.util.stream.Collectors.joining;
import static jersey.repackaged.com.google.common.base.Throwables.propagate;
import static org.apache.commons.lang3.StringUtils.isEmpty;
import static org.telegram.abilitybots.api.objects.Ability.builder;
import static org.telegram.abilitybots.api.objects.Flag.*;
import static org.telegram.abilitybots.api.objects.Locality.*;
import static org.telegram.abilitybots.api.objects.MessageContext.newContext;
import static org.telegram.abilitybots.api.objects.Privacy.*;
import static org.telegram.abilitybots.api.util.AbilityMessageCodes.*;
import static org.telegram.abilitybots.api.util.AbilityUtils.*;

/**
 * The <b>father</b> of all ability bots. Bots that need to utilize abilities need to extend this bot.
 * <p>
 * It's important to note that this bot strictly extends {@link TelegramLongPollingBot}.
 * <p>
 * All bots extending the {@link BaseAbilityBot} get implicit abilities:
 * <ul>
 * <li>/claim - Claims this bot</li>
 * <ul>
 * <li>Sets the user as the {@link Privacy#CREATOR} of the bot</li>
 * <li>Only the user with the ID returned by {@link BaseAbilityBot#creatorId()} can genuinely claim the bot</li>
 * </ul>
 * <li>/report - reports all user-defined commands (abilities)</li>
 * <ul>
 * <li>The same format acceptable by BotFather</li>
 * </ul>
 * <li>/commands - returns a list of all possible bot commands based on the privacy of the requesting user</li>
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
 * The two most important handles in the BaseAbilityBot are the {@link DBContext} <b><code>db</code></b> and the {@link MessageSender} <b><code>sender</code></b>.
 * All bots extending BaseAbilityBot can use both handles in their update consumers.
 *
 * @author Abbas Abou Daya
 */
@SuppressWarnings({"ConfusingArgumentToVarargsMethod", "UnusedReturnValue", "WeakerAccess", "unused", "ConstantConditions"})
public abstract class BaseAbilityBot extends DefaultAbsSender implements AbilityExtension {
  private static final String TAG = BaseAbilityBot.class.getSimpleName();

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
  protected static final String REPORT = "report";

  // DB and sender
  protected final DBContext db;
  protected MessageSender sender;
  protected SilentSender silent;

  // Bot token and username
  private final String botToken;
  private final String botUsername;

  // Ability registry
  private Map<String, Ability> abilities;

  // Reply registry
  private List<Reply> replies;

  public abstract int creatorId();

  protected BaseAbilityBot(String botToken, String botUsername, DBContext db, DefaultBotOptions botOptions) {
    super(botOptions);

    this.botToken = botToken;
    this.botUsername = botUsername;
    this.db = db;
    this.sender = new DefaultSender(this);
    silent = new SilentSender(sender);

    registerAbilities();
  }

  /**
   * @return the map of ID -> User
   */
  protected Map<Integer, User> users() {
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
   * @return the immutable map of String -> Ability
   */
  public Map<String, Ability> abilities() {
    return abilities;
  }

  /**
   * @return the immutable list carrying the embedded replies
   */
  public List<Reply> replies() {
    return replies;
  }

  /**
   * This method contains the stream of actions that are applied on any update.
   * <p>
   * It will correctly handle addition of users into the DB and the execution of abilities and replies.
   *
   * @param update the update received by Telegram's API
   */
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

  public String getBotToken() {
    return botToken;
  }

  public String getBotUsername() {
    return botUsername;
  }

  /**
   * Test the update against the provided global flags. The default implementation is a passthrough to all updates.
   * <p>
   * This method should be <b>overridden</b> if the user wants to restrict bot usage to only certain updates.
   *
   * @param update a Telegram {@link Update}
   * @return <tt>true</tt> if the update satisfies the global flags
   */
  protected boolean checkGlobalFlags(Update update) {
    return true;
  }

  /**
   * Gets the user with the specified username.
   *
   * @param username the username of the required user
   * @return the user
   */
  protected User getUser(String username) {
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
  protected User getUser(int id) {
    User user = users().get(id);
    if (user == null) {
      throw new IllegalStateException(format("Could not find user corresponding to id [%d]", id));
    }

    return user;
  }

  /**
   * Gets the user with the specified username. If user was not found, the bot will send a message on Telegram.
   *
   * @param username the username of the required user
   * @param ctx      the message context with the originating user
   * @return the id of the user
   */
  protected int getUserIdSendError(String username, MessageContext ctx) {
    try {
      return getUser(username).getId();
    } catch (IllegalStateException ex) {
      silent.send(getLocalizedMessage(USER_NOT_FOUND, ctx.user().getLanguageCode(), username), ctx.chatId());
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
        .name(REPORT)
        .locality(ALL)
        .privacy(CREATOR)
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
              .orElse(getLocalizedMessage(ABILITY_COMMANDS_NOT_FOUND, ctx.user().getLanguageCode()));

          silent.send(commands, ctx.chatId());
        })
        .build();
  }

  /**
   * Default format:
   * <p>
   * PUBLIC
   * <p>
   * [command1] - [description1]
   * <p>
   * [command2] - [description2]
   * <p>
   * GROUP_ADMIN
   * <p>
   * [command1] - [description1]
   * <p>
   * ...
   *
   * @return the ability to print commands based on the privacy of the requesting user
   */
  public Ability commands() {
    return builder()
        .name(COMMANDS)
        .locality(USER)
        .privacy(PUBLIC)
        .input(0)
        .action(ctx -> {
          Privacy privacy = getPrivacy(ctx.update(), ctx.user().getId());

          ListMultimap<Privacy, String> abilitiesPerPrivacy = abilities.entrySet().stream()
              .map(entry -> {
                String name = entry.getValue().name();
                String info = entry.getValue().info();

                if (!isEmpty(info))
                  return Pair.of(entry.getValue().privacy(), format("/%s - %s", name, info));
                return Pair.of(entry.getValue().privacy(), format("/%s", name));
              })
              .sorted(comparing(Pair::b))
              .collect(() -> hashKeys().arrayListValues().build(),
                  (map, pair) -> map.put(pair.a(), pair.b()),
                  Multimap::putAll);

          String commands = abilitiesPerPrivacy.asMap().entrySet().stream()
              .filter(entry -> privacy.compareTo(entry.getKey()) >= 0)
              .sorted(comparing(Entry::getKey))
              .map(entry ->
                  entry.getValue().stream()
                      .reduce(entry.getKey().toString(), (a, b) -> format("%s\n%s", a, b))
              )
              .collect(joining("\n"));

          if (commands.isEmpty())
            commands = getLocalizedMessage(ABILITY_COMMANDS_NOT_FOUND, ctx.user().getLanguageCode());

          silent.send(commands, ctx.chatId());
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
                .setDocument(backup)
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
        .action(ctx -> silent.forceReply(
            getLocalizedMessage(ABILITY_RECOVER_MESSAGE, ctx.user().getLanguageCode()), ctx.chatId()))
        .reply(update -> {
          String replyToMsg = update.getMessage().getReplyToMessage().getText();
          String recoverMessage = getLocalizedMessage(ABILITY_RECOVER_MESSAGE, AbilityUtils.getUser(update).getLanguageCode());
          if (!replyToMsg.equals(recoverMessage))
            return;

          String fileId = update.getMessage().getDocument().getFileId();
          try (FileReader reader = new FileReader(downloadFileWithId(fileId))) {
            String backupData = IOUtils.toString(reader);
            if (db.recover(backupData)) {
              send(ABILITY_RECOVER_SUCCESS, update);
            } else {
              send(ABILITY_RECOVER_FAIL, update);
            }
          } catch (Exception e) {
            BotLogger.error("Could not recover DB from backup", TAG, e);
            send(ABILITY_RECOVER_ERROR, update);
          }
        }, MESSAGE, DOCUMENT, REPLY)
        .build();
  }

  /**
   * Banned users are accumulated in the blacklist. Use {@link DBContext#getSet(String)} with name specified by {@link BaseAbilityBot#BLACKLIST}.
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
          int userId = getUserIdSendError(username, ctx);
          String bannedUser;

          // Protection from abuse
          if (userId == creatorId()) {
            userId = ctx.user().getId();
            bannedUser = isNullOrEmpty(ctx.user().getUserName()) ? addTag(ctx.user().getUserName()) : shortName(ctx.user());
          } else {
            bannedUser = addTag(username);
          }

          Set<Integer> blacklist = blacklist();
          if (blacklist.contains(userId))
            sendMd(ABILITY_BAN_FAIL, ctx, escape(bannedUser));
          else {
            blacklist.add(userId);
            sendMd(ABILITY_BAN_SUCCESS, ctx, escape(bannedUser));
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
          Integer userId = getUserIdSendError(username, ctx);

          Set<Integer> blacklist = blacklist();

          if (!blacklist.remove(userId))
            silent.sendMd(getLocalizedMessage(ABILITY_UNBAN_FAIL, ctx.user().getLanguageCode(), escape(username)), ctx.chatId());
          else {
            silent.sendMd(getLocalizedMessage(ABILITY_UNBAN_SUCCESS, ctx.user().getLanguageCode(), escape(username)), ctx.chatId());
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
          Integer userId = getUserIdSendError(username, ctx);

          Set<Integer> admins = admins();
          if (admins.contains(userId))
            sendMd(ABILITY_PROMOTE_FAIL, ctx, escape(username));
          else {
            admins.add(userId);
            sendMd(ABILITY_PROMOTE_SUCCESS, ctx, escape(username));
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
          Integer userId = getUserIdSendError(username, ctx);

          Set<Integer> admins = admins();
          if (admins.remove(userId)) {
            sendMd(ABILITY_DEMOTE_SUCCESS, ctx, escape(username));
          } else {
            sendMd(ABILITY_DEMOTE_FAIL, ctx, escape(username));
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
        .privacy(CREATOR)
        .input(0)
        .action(ctx -> {
          Set<Integer> admins = admins();
          int id = creatorId();

          if (admins.contains(id))
            send(ABILITY_CLAIM_FAIL, ctx);
          else {
            admins.add(id);
            send(ABILITY_CLAIM_SUCCESS, ctx);
          }
        })
        .post(commitTo(db))
        .build();
  }

  private Optional<Message> send(String message, MessageContext ctx, String... args) {
    return silent.send(getLocalizedMessage(message, ctx.user().getLanguageCode(), args), ctx.chatId());
  }

  private Optional<Message> sendMd(String message, MessageContext ctx, String... args) {
    return silent.sendMd(getLocalizedMessage(message, ctx.user().getLanguageCode(), args), ctx.chatId());
  }

  private Optional<Message> send(String message, Update upd) {
    Long chatId = upd.getMessage().getChatId();
    return silent.send(getLocalizedMessage(message, AbilityUtils.getUser(upd).getLanguageCode()), chatId);
  }

  /**
   * Registers the declared abilities using method reflection. Also, replies are accumulated using the built abilities and standalone methods that return a Reply.
   * <p>
   * <b>Only abilities and replies with the <u>public</u> accessor are registered!</b>
   */
  private void registerAbilities() {
    try {
      // Collect all classes that implement AbilityExtension declared in the bot
      List<AbilityExtension> extensions = stream(getClass().getMethods())
          .filter(checkReturnType(AbilityExtension.class))
          .map(returnExtension(this))
          .collect(Collectors.toList());

      // Add the bot itself as it is an AbilityExtension
      extensions.add(this);

      // Extract all abilities from every single extension instance
      abilities = extensions.stream()
          .flatMap(ext -> stream(ext.getClass().getMethods())
              .filter(checkReturnType(Ability.class))
              .map(returnAbility(ext)))
          // Abilities are immutable, build it respectively
          .collect(ImmutableMap::<String, Ability>builder,
              (b, a) -> b.put(a.name(), a),
              (b1, b2) -> b1.putAll(b2.build()))
          .build();

      // Extract all replies from every single extension instance
      Stream<Reply> extensionReplies = extensions.stream()
          .flatMap(ext -> stream(ext.getClass().getMethods())
              .filter(checkReturnType(Reply.class))
              .map(returnReply(ext)));

      // Replies can be standalone or attached to abilities, fetch those too
      Stream<Reply> abilityReplies = abilities.values().stream()
          .flatMap(ability -> ability.replies().stream());

      // Now create the replies registry (list)
      replies = Stream.concat(abilityReplies, extensionReplies).collect(
          ImmutableList::<Reply>builder,
          Builder::add,
          (b1, b2) -> b1.addAll(b2.build()))
          .build();
    } catch (IllegalStateException e) {
      BotLogger.error(TAG, "Duplicate names found while registering abilities. Make sure that the abilities declared don't clash with the reserved ones.", e);
      throw propagate(e);
    }
  }

  /**
   * @param clazz the type to be tested
   * @return a predicate testing the return type of the method corresponding to the class parameter
   */
  private Predicate<Method> checkReturnType(Class<?> clazz) {
    return method -> clazz.isAssignableFrom(method.getReturnType());
  }

  /**
   * Invokes the method and retrieves its return {@link Reply}.
   *
   * @param obj an bot or extension that this method is invoked with
   * @return a {@link Function} which returns the {@link Reply} returned by the given method
   */
  private Function<? super Method, AbilityExtension> returnExtension(Object obj) {
    return method -> {
      try {
        return (AbilityExtension) method.invoke(obj);
      } catch (IllegalAccessException | InvocationTargetException e) {
        BotLogger.error("Could not add ability extension", TAG, e);
        throw propagate(e);
      }
    };
  }

  /**
   * Invokes the method and retrieves its return {@link Ability}.
   *
   * @param obj an bot or extension that this method is invoked with
   * @return a {@link Function} which returns the {@link Ability} returned by the given method
   */
  private Function<? super Method, Ability> returnAbility(Object obj) {
    return method -> {
      try {
        return (Ability) method.invoke(obj);
      } catch (IllegalAccessException | InvocationTargetException e) {
        BotLogger.error("Could not add ability", TAG, e);
        throw propagate(e);
      }
    };
  }

  /**
   * Invokes the method and retrieves its return {@link Reply}.
   *
   * @param obj an bot or extension that this method is invoked with
   * @return a {@link Function} which returns the {@link Reply} returned by the given method
   */
  private Function<? super Method, Reply> returnReply(Object obj) {
    return method -> {
      try {
        return (Reply) method.invoke(obj);
      } catch (IllegalAccessException | InvocationTargetException e) {
        BotLogger.error("Could not add reply", TAG, e);
        throw propagate(e);
      }
    };
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
    User user = AbilityUtils.getUser(update);

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
      silent.send(
          getLocalizedMessage(
              CHECK_INPUT_FAIL,
              AbilityUtils.getUser(trio.a()).getLanguageCode(),
              abilityTokens, abilityTokens == 1 ? "input" : "inputs"),
          getChatId(trio.a()));
    return isOk;
  }

  boolean checkLocality(Trio<Update, Ability, String[]> trio) {
    Update update = trio.a();
    Locality locality = isUserMessage(update) ? USER : GROUP;
    Locality abilityLocality = trio.b().locality();

    boolean isOk = abilityLocality == ALL || locality == abilityLocality;

    if (!isOk)
      silent.send(
          getLocalizedMessage(
              CHECK_LOCALITY_FAIL,
              AbilityUtils.getUser(trio.a()).getLanguageCode(),
              abilityLocality.toString().toLowerCase()),
          getChatId(trio.a()));
    return isOk;
  }

  boolean checkPrivacy(Trio<Update, Ability, String[]> trio) {
    Update update = trio.a();
    User user = AbilityUtils.getUser(update);
    Privacy privacy;
    int id = user.getId();

    privacy = getPrivacy(update, id);

    boolean isOk = privacy.compareTo(trio.b().privacy()) >= 0;

    if (!isOk)
      silent.send(
          getLocalizedMessage(
              CHECK_PRIVACY_FAIL,
              AbilityUtils.getUser(trio.a()).getLanguageCode()),
          getChatId(trio.a()));
    return isOk;
  }

  @NotNull
  private Privacy getPrivacy(Update update, int id) {
    return isCreator(id) ?
        CREATOR : isAdmin(id) ?
        ADMIN : (isGroupUpdate(update) || isSuperGroupUpdate(update)) && isGroupAdmin(update, id) ?
        GROUP_ADMIN : PUBLIC;
  }

  private boolean isGroupAdmin(Update update, int id) {
    GetChatAdministrators admins = new GetChatAdministrators().setChatId(getChatId(update));

    return silent.execute(admins)
        .orElse(new ArrayList<>()).stream()
        .anyMatch(member -> member.getUser().getId() == id);
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

    String[] tokens = msg.getText().split(" ");

    if (tokens[0].startsWith("/")) {
      String abilityToken = stripBotUsername(tokens[0].substring(1)).toLowerCase();
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
    User endUser = AbilityUtils.getUser(update);

    users().compute(endUser.getId(), (id, user) -> {
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

  private void updateUserId(User oldUser, User newUser) {
    if (oldUser != null && oldUser.getUserName() != null) {
      // Remove old username -> ID
      userIds().remove(oldUser.getUserName());
    }

    if (newUser.getUserName() != null) {
      // Add new mapping with the new username
      userIds().put(newUser.getUserName().toLowerCase(), newUser.getId());
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
    return sender.downloadFile(sender.execute(new GetFile().setFileId(fileId)));
  }


  private String escape(String username) {
    return username.replace("_", "\\_");
  }
}