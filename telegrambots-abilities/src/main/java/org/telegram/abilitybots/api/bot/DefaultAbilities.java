package org.telegram.abilitybots.api.bot;

import com.google.common.collect.ListMultimap;
import com.google.common.collect.Multimap;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.telegram.abilitybots.api.db.DBContext;
import org.telegram.abilitybots.api.objects.Ability;
import org.telegram.abilitybots.api.objects.MessageContext;
import org.telegram.abilitybots.api.objects.Privacy;
import org.telegram.abilitybots.api.util.AbilityExtension;
import org.telegram.abilitybots.api.util.AbilityUtils;
import org.telegram.abilitybots.api.util.Pair;
import org.telegram.telegrambots.meta.api.methods.GetFile;
import org.telegram.telegrambots.meta.api.methods.send.SendDocument;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.PrintStream;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.StringJoiner;

import static com.google.common.base.Strings.isNullOrEmpty;
import static com.google.common.collect.MultimapBuilder.hashKeys;
import static java.lang.String.format;
import static java.util.Comparator.comparing;
import static java.util.Objects.nonNull;
import static java.util.stream.Collectors.joining;
import static org.apache.commons.lang3.StringUtils.isEmpty;
import static org.telegram.abilitybots.api.objects.Ability.builder;
import static org.telegram.abilitybots.api.objects.Flag.DOCUMENT;
import static org.telegram.abilitybots.api.objects.Flag.MESSAGE;
import static org.telegram.abilitybots.api.objects.Flag.REPLY;
import static org.telegram.abilitybots.api.objects.Locality.ALL;
import static org.telegram.abilitybots.api.objects.Locality.USER;
import static org.telegram.abilitybots.api.objects.Privacy.ADMIN;
import static org.telegram.abilitybots.api.objects.Privacy.CREATOR;
import static org.telegram.abilitybots.api.objects.Privacy.PUBLIC;
import static org.telegram.abilitybots.api.util.AbilityMessageCodes.ABILITY_BAN_FAIL;
import static org.telegram.abilitybots.api.util.AbilityMessageCodes.ABILITY_BAN_SUCCESS;
import static org.telegram.abilitybots.api.util.AbilityMessageCodes.ABILITY_CLAIM_FAIL;
import static org.telegram.abilitybots.api.util.AbilityMessageCodes.ABILITY_CLAIM_SUCCESS;
import static org.telegram.abilitybots.api.util.AbilityMessageCodes.ABILITY_COMMANDS_NOT_FOUND;
import static org.telegram.abilitybots.api.util.AbilityMessageCodes.ABILITY_DEMOTE_FAIL;
import static org.telegram.abilitybots.api.util.AbilityMessageCodes.ABILITY_DEMOTE_SUCCESS;
import static org.telegram.abilitybots.api.util.AbilityMessageCodes.ABILITY_PROMOTE_FAIL;
import static org.telegram.abilitybots.api.util.AbilityMessageCodes.ABILITY_PROMOTE_SUCCESS;
import static org.telegram.abilitybots.api.util.AbilityMessageCodes.ABILITY_RECOVER_ERROR;
import static org.telegram.abilitybots.api.util.AbilityMessageCodes.ABILITY_RECOVER_FAIL;
import static org.telegram.abilitybots.api.util.AbilityMessageCodes.ABILITY_RECOVER_MESSAGE;
import static org.telegram.abilitybots.api.util.AbilityMessageCodes.ABILITY_RECOVER_SUCCESS;
import static org.telegram.abilitybots.api.util.AbilityMessageCodes.ABILITY_UNBAN_FAIL;
import static org.telegram.abilitybots.api.util.AbilityMessageCodes.ABILITY_UNBAN_SUCCESS;
import static org.telegram.abilitybots.api.util.AbilityMessageCodes.USER_NOT_FOUND;
import static org.telegram.abilitybots.api.util.AbilityUtils.addTag;
import static org.telegram.abilitybots.api.util.AbilityUtils.escape;
import static org.telegram.abilitybots.api.util.AbilityUtils.getLocalizedMessage;
import static org.telegram.abilitybots.api.util.AbilityUtils.shortName;
import static org.telegram.abilitybots.api.util.AbilityUtils.stripTag;

public final class DefaultAbilities implements AbilityExtension {
  // Default commands
  public static final String CLAIM = "claim";
  public static final String BAN = "ban";
  public static final String PROMOTE = "promote";
  public static final String DEMOTE = "demote";
  public static final String UNBAN = "unban";
  public static final String BACKUP = "backup";
  public static final String RECOVER = "recover";
  public static final String COMMANDS = "commands";
  public static final String REPORT = "report";
  public static final String STATS = "stats";
  private static final Logger log = LoggerFactory.getLogger(DefaultAbilities.class);
  private final BaseAbilityBot bot;

  public DefaultAbilities(BaseAbilityBot bot) {
    this.bot = bot;
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
          String commands = bot.abilities().values().stream()
              .filter(ability -> nonNull(ability.info()))
              .map(ability -> {
                String name = ability.name();
                String info = ability.info();
                return format("%s - %s", name, info);
              })
              .sorted()
              .reduce((a, b) -> format("%s%n%s", a, b))
              .orElse(getLocalizedMessage(ABILITY_COMMANDS_NOT_FOUND, ctx.user().getLanguageCode()));

          bot.silent.send(commands, ctx.chatId());
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
          Privacy privacy = bot.getPrivacy(ctx.update(), ctx.user().getId());

          ListMultimap<Privacy, String> abilitiesPerPrivacy = bot.abilities().values().stream()
              .map(ability -> {
                String name = ability.name();
                String info = ability.info();

                if (!isEmpty(info))
                  return Pair.of(ability.privacy(), format("/%s - %s", name, info));
                return Pair.of(ability.privacy(), format("/%s", name));
              })
              .sorted(comparing(Pair::b))
              .collect(() -> hashKeys().arrayListValues().build(),
                  (map, pair) -> map.put(pair.a(), pair.b()),
                  Multimap::putAll);

          String commands = abilitiesPerPrivacy.asMap().entrySet().stream()
              .filter(entry -> privacy.compareTo(entry.getKey()) >= 0)
              .sorted(comparing(Map.Entry::getKey))
              .map(entry ->
                  entry.getValue().stream()
                      .reduce(entry.getKey().toString(), (a, b) -> format("%s\n%s", a, b))
              )
              .collect(joining("\n"));

          if (commands.isEmpty())
            commands = getLocalizedMessage(ABILITY_COMMANDS_NOT_FOUND, ctx.user().getLanguageCode());

          bot.silent.send(commands, ctx.chatId());
        })
        .build();
  }

  /**
   * @return the ability to report statistics for abilities and replies.
   */
  public Ability reportStats() {
    return builder()
        .name(STATS)
        .locality(ALL)
        .privacy(ADMIN)
        .input(0)
        .action(ctx -> {
          String stats = bot.stats().entrySet().stream()
              .map(entry -> String.format("%s: %d", entry.getKey(), entry.getValue().hits()))
              .reduce(new StringJoiner("\n"), StringJoiner::add, StringJoiner::merge)
              .toString();

          bot.silent.send(stats, ctx.chatId());
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
            printStream.print(bot.db.backup());
            bot.sender.sendDocument(SendDocument.builder()
                    .document(new InputFile(backup))
                    .chatId(ctx.chatId())
                    .build()
            );
          } catch (FileNotFoundException e) {
            log.error("Error while fetching backup", e);
          } catch (TelegramApiException e) {
            log.error("Error while sending document/backup file", e);
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
        .action(ctx -> bot.silent.forceReply(
            getLocalizedMessage(ABILITY_RECOVER_MESSAGE, ctx.user().getLanguageCode()), ctx.chatId()))
        .reply((bot, update) -> {
          String replyToMsg = update.getMessage().getReplyToMessage().getText();
          String recoverMessage = getLocalizedMessage(ABILITY_RECOVER_MESSAGE, AbilityUtils.getUser(update).getLanguageCode());
          if (!replyToMsg.equals(recoverMessage))
            return;

          String fileId = update.getMessage().getDocument().getFileId();
          try (FileReader reader = new FileReader(downloadFileWithId(fileId))) {
            String backupData = IOUtils.toString(reader);
            if (bot.db.recover(backupData)) {
              send(ABILITY_RECOVER_SUCCESS, update);
            } else {
              send(ABILITY_RECOVER_FAIL, update);
            }
          } catch (Exception e) {
            log.error("Could not recover DB from backup", e);
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
          long userId = getUserIdSendError(username, ctx);
          String bannedUser;

          // Protection from abuse
          if (userId == bot.creatorId()) {
            userId = ctx.user().getId();
            bannedUser = isNullOrEmpty(ctx.user().getUserName()) ? addTag(ctx.user().getUserName()) : shortName(ctx.user());
          } else {
            bannedUser = addTag(username);
          }

          Set<Long> blacklist = bot.blacklist();
          if (blacklist.contains(userId))
            sendMd(ABILITY_BAN_FAIL, ctx, escape(bannedUser));
          else {
            blacklist.add(userId);
            sendMd(ABILITY_BAN_SUCCESS, ctx, escape(bannedUser));
          }
        })
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
          Long userId = getUserIdSendError(username, ctx);

          Set<Long> blacklist = bot.blacklist();

          if (!blacklist.remove(userId))
            bot.silent.sendMd(getLocalizedMessage(ABILITY_UNBAN_FAIL, ctx.user().getLanguageCode(), escape(username)), ctx.chatId());
          else {
            bot.silent.sendMd(getLocalizedMessage(ABILITY_UNBAN_SUCCESS, ctx.user().getLanguageCode(), escape(username)), ctx.chatId());
          }
        })
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
          Long userId = getUserIdSendError(username, ctx);

          Set<Long> admins = bot.admins();
          if (admins.contains(userId))
            sendMd(ABILITY_PROMOTE_FAIL, ctx, escape(username));
          else {
            admins.add(userId);
            sendMd(ABILITY_PROMOTE_SUCCESS, ctx, escape(username));
          }
        })
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
          Long userId = getUserIdSendError(username, ctx);

          Set<Long> admins = bot.admins();
          if (admins.remove(userId)) {
            sendMd(ABILITY_DEMOTE_SUCCESS, ctx, escape(username));
          } else {
            sendMd(ABILITY_DEMOTE_FAIL, ctx, escape(username));
          }
        })
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
          Set<Long> admins = bot.admins();
          long id = bot.creatorId();

          if (admins.contains(id))
            send(ABILITY_CLAIM_FAIL, ctx);
          else {
            admins.add(id);
            send(ABILITY_CLAIM_SUCCESS, ctx);
          }
        })
        .build();
  }

  /**
   * Gets the user with the specified username.
   *
   * @param username the username of the required user
   * @return the user
   */
  private User getUser(String username) {
    Long id = bot.userIds().get(username.toLowerCase());
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
  private User getUser(long id) {
    User user = bot.users().get(id);
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
  private long getUserIdSendError(String username, MessageContext ctx) {
    try {
      return getUser(username).getId();
    } catch (IllegalStateException ex) {
      bot.silent.send(getLocalizedMessage(USER_NOT_FOUND, ctx.user().getLanguageCode(), username), ctx.chatId());
      throw ex;
    }
  }


  private Optional<Message> send(String message, MessageContext ctx, String... args) {
    return bot.silent.send(getLocalizedMessage(message, ctx.user().getLanguageCode(), args), ctx.chatId());
  }

  private Optional<Message> sendMd(String message, MessageContext ctx, String... args) {
    return bot.silent.sendMd(getLocalizedMessage(message, ctx.user().getLanguageCode(), args), ctx.chatId());
  }

  private Optional<Message> send(String message, Update upd) {
    Long chatId = upd.getMessage().getChatId();
    return bot.silent.send(getLocalizedMessage(message, AbilityUtils.getUser(upd).getLanguageCode()), chatId);
  }

  protected File downloadFileWithId(String fileId) throws TelegramApiException {
    return bot.sender.downloadFile(bot.sender.execute(GetFile.builder().fileId(fileId).build()));
  }
}
