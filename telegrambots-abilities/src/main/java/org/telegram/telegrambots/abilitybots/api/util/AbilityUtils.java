package org.telegram.telegrambots.abilitybots.api.util;

import com.google.common.base.Strings;
import org.telegram.telegrambots.abilitybots.api.db.DBContext;
import org.telegram.telegrambots.abilitybots.api.objects.MessageContext;
import org.telegram.telegrambots.abilitybots.api.objects.Flag;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.User;

import java.text.MessageFormat;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import java.util.StringJoiner;
import java.util.function.Consumer;
import java.util.function.Predicate;

import static java.util.ResourceBundle.Control.FORMAT_PROPERTIES;
import static java.util.ResourceBundle.Control.getNoFallbackControl;
import static java.util.ResourceBundle.getBundle;
import static org.apache.commons.lang3.ObjectUtils.defaultIfNull;
import static org.apache.commons.lang3.StringUtils.isEmpty;

/**
 * Helper and utility methods
 */
public final class AbilityUtils {
  public static User EMPTY_USER = new User(0L, "", false);

  private AbilityUtils() {

  }

  /**
   * @param username any username
   * @return the username with the preceding "@" stripped off
   */
  public static String stripTag(String username) {
    String lowerCase = username.toLowerCase();
    return lowerCase.startsWith("@") ? lowerCase.substring(1) : lowerCase;
  }

  /**
   * Commits to DB.
   *
   * @param db the database to commit on
   * @return a lambda consumer that takes in a {@link MessageContext}, used in post actions for abilities
   */
  public static Consumer<MessageContext> commitTo(DBContext db) {
    return ctx -> db.commit();
  }

  /**
   * Fetches the user who caused the update.
   *
   * @param update a Telegram {@link Update}
   * @return the originating user
   * @throws IllegalStateException if the user could not be found
   */
  public static User getUser(Update update) {
    return defaultIfNull(getUserElseThrow(update), EMPTY_USER);
  }

  private static User getUserElseThrow(Update update) {
    if (Flag.MESSAGE.test(update)) {
      return update.getMessage().getFrom();
    } else if (Flag.CALLBACK_QUERY.test(update)) {
      return update.getCallbackQuery().getFrom();
    } else if (Flag.INLINE_QUERY.test(update)) {
      return update.getInlineQuery().getFrom();
    } else if (Flag.CHANNEL_POST.test(update)) {
      return update.getChannelPost().getFrom();
    } else if (Flag.EDITED_CHANNEL_POST.test(update)) {
      return update.getEditedChannelPost().getFrom();
    } else if (Flag.EDITED_MESSAGE.test(update)) {
      return update.getEditedMessage().getFrom();
    } else if (Flag.CHOSEN_INLINE_QUERY.test(update)) {
      return update.getChosenInlineQuery().getFrom();
    } else if (Flag.SHIPPING_QUERY.test(update)) {
      return update.getShippingQuery().getFrom();
    } else if (Flag.PRECHECKOUT_QUERY.test(update)) {
      return update.getPreCheckoutQuery().getFrom();
    } else if (Flag.POLL_ANSWER.test(update)) {
      return update.getPollAnswer().getUser();
    } else if (Flag.MY_CHAT_MEMBER.test(update)) {
      return update.getMyChatMember().getFrom();
    } else if (Flag.CHAT_MEMBER.test(update)) {
      return update.getChatMember().getFrom();
    } else if (Flag.CHAT_JOIN_REQUEST.test(update)) {
      return update.getChatJoinRequest().getUser();
    } else if (Flag.POLL.test(update)) {
      return EMPTY_USER;
    } else {
      throw new IllegalStateException("Could not retrieve originating user from update");
    }
  }

  /**
   * A "best-effort" boolean stating whether the update is a group message or not.
   *
   * @param update a Telegram {@link Update}
   * @return whether the update is linked to a group
   */
  public static boolean isGroupUpdate(Update update) {
    if (Flag.MESSAGE.test(update)) {
      return update.getMessage().isGroupMessage();
    } else if (Flag.CALLBACK_QUERY.test(update)) {
      return update.getCallbackQuery().getMessage().isGroupMessage();
    } else if (Flag.CHANNEL_POST.test(update)) {
      return update.getChannelPost().isGroupMessage();
    } else if (Flag.EDITED_CHANNEL_POST.test(update)) {
      return update.getEditedChannelPost().isGroupMessage();
    } else if (Flag.EDITED_MESSAGE.test(update)) {
      return update.getEditedMessage().isGroupMessage();
    } else {
      return false;
    }
  }

  /**
   * A "best-effort" boolean stating whether the update is a super-group message or not.
   *
   * @param update a Telegram {@link Update}
   * @return whether the update is linked to a group
   */
  public static boolean isSuperGroupUpdate(Update update) {
    if (Flag.MESSAGE.test(update)) {
      return update.getMessage().isSuperGroupMessage();
    } else if (Flag.CALLBACK_QUERY.test(update)) {
      return update.getCallbackQuery().getMessage().isSuperGroupMessage();
    } else if (Flag.CHANNEL_POST.test(update)) {
      return update.getChannelPost().isSuperGroupMessage();
    } else if (Flag.EDITED_CHANNEL_POST.test(update)) {
      return update.getEditedChannelPost().isSuperGroupMessage();
    } else if (Flag.EDITED_MESSAGE.test(update)) {
      return update.getEditedMessage().isSuperGroupMessage();
    } else {
      return false;
    }
  }

  /**
   * Fetches the direct chat ID of the specified update.
   *
   * @param update a Telegram {@link Update}
   * @return the originating chat ID
   * @throws IllegalStateException if the chat ID could not be found
   */
  public static Long getChatId(Update update) {
    if (Flag.MESSAGE.test(update)) {
      return update.getMessage().getChatId();
    } else if (Flag.CALLBACK_QUERY.test(update)) {
      return update.getCallbackQuery().getMessage().getChatId();
    } else if (Flag.INLINE_QUERY.test(update)) {
      return update.getInlineQuery().getFrom().getId();
    } else if (Flag.CHANNEL_POST.test(update)) {
      return update.getChannelPost().getChatId();
    } else if (Flag.EDITED_CHANNEL_POST.test(update)) {
      return update.getEditedChannelPost().getChatId();
    } else if (Flag.EDITED_MESSAGE.test(update)) {
      return update.getEditedMessage().getChatId();
    } else if (Flag.CHOSEN_INLINE_QUERY.test(update)) {
      return update.getChosenInlineQuery().getFrom().getId();
    } else if (Flag.SHIPPING_QUERY.test(update)) {
      return update.getShippingQuery().getFrom().getId();
    } else if (Flag.PRECHECKOUT_QUERY.test(update)) {
      return update.getPreCheckoutQuery().getFrom().getId();
    } else if (Flag.POLL_ANSWER.test(update)) {
      return update.getPollAnswer().getUser().getId();
    } else if (Flag.POLL.test(update)) {
      return EMPTY_USER.getId();
    } else if (Flag.MY_CHAT_MEMBER.test(update)) {
      return update.getMyChatMember().getChat().getId();
    } else if (Flag.CHAT_MEMBER.test(update)) {
      return update.getChatMember().getChat().getId();
    } else if (Flag.CHAT_JOIN_REQUEST.test(update)) {
      return update.getChatJoinRequest().getChat().getId();
    } else {
      throw new IllegalStateException("Could not retrieve originating chat ID from update");
    }
  }

  /**
   * @param update a Telegram {@link Update}
   * @return <tt>true</tt> if the update contains contains a private user message
   */
  public static boolean isUserMessage(Update update) {
    if (Flag.MESSAGE.test(update)) {
      return update.getMessage().isUserMessage();
    } else if (Flag.CALLBACK_QUERY.test(update)) {
      return update.getCallbackQuery().getMessage().isUserMessage();
    } else if (Flag.CHANNEL_POST.test(update)) {
      return update.getChannelPost().isUserMessage();
    } else if (Flag.EDITED_CHANNEL_POST.test(update)) {
      return update.getEditedChannelPost().isUserMessage();
    } else if (Flag.EDITED_MESSAGE.test(update)) {
      return update.getEditedMessage().isUserMessage();
    } else {
      return true;
    }
  }

  /**
   * @param username the username to add the tag to
   * @return the username prefixed with the "@" tag.
   */
  public static String addTag(String username) {
    return "@" + username;
  }

  /**
   * @param msg the message to be replied to
   * @return a predicate that asserts that the update is a reply to the specified message.
   */
  public static Predicate<Update> isReplyTo(String msg) {
    return update -> update.getMessage().getReplyToMessage().getText().equals(msg);
  }

  public static String getLocalizedMessage(String messageCode, Locale locale, Object... arguments) {
    ResourceBundle bundle;
    if (locale == null) {
      bundle = getBundle("messages", Locale.ROOT);
    } else {
      try {
        bundle = getBundle(
            "messages",
            locale,
            getNoFallbackControl(FORMAT_PROPERTIES));
      } catch (MissingResourceException e) {
        bundle = getBundle("messages", Locale.ROOT);
      }
    }
    String message = bundle.getString(messageCode);
    return MessageFormat.format(message, arguments);
  }

  public static String getLocalizedMessage(String messageCode, String languageCode, Object... arguments) {
    Locale locale = Strings.isNullOrEmpty(languageCode) ? null : Locale.forLanguageTag(languageCode);
    return getLocalizedMessage(messageCode, locale, arguments);
  }

  /**
   * The short name is one of the following:
   * <ol>
   * <li>First name</li>
   * <li>Last name</li>
   * <li>Username</li>
   * </ol>
   * The method will try to return the first valid name in the specified order.
   *
   * @return the short name of the user
   */
  public static String shortName(User user) {
    if (!isEmpty(user.getFirstName()))
      return user.getFirstName();

    if (!isEmpty(user.getLastName()))
      return user.getLastName();

    return user.getUserName();
  }

  /**
   * The full name is identified as the concatenation of the first and last name, separated by a space.
   * This method can return an empty name if both first and last name are empty.
   *
   * @param user User to use
   * @return the full name of the user
   */
  public static String fullName(User user) {
    StringJoiner name = new StringJoiner(" ");

    if (!isEmpty(user.getFirstName()))
      name.add(user.getFirstName());
    if (!isEmpty(user.getLastName()))
      name.add(user.getLastName());

    return name.toString();
  }

  public static String escape(String username) {
    return username.replace("_", "\\_");
  }
  
  /**
   * Checks if the passed string is a valid bot command according to the requirements of Telegram Bot API:
   *    "A command must always start with the '/' symbol and may not be longer than 32 characters.
   *    Commands can use latin letters, numbers and underscores."
   *    (https://core.telegram.org/bots#commands)
   *
   * @param command String representation of a command to be checked for validity
   * @return whether the command is valid
   */
  public static boolean isValidCommand(String command){
    if (command == null || command.length() > 32) return false;
    return command.matches("/[A-Za-z_0-9]+");
  }

  /**
   * Checks if the passed String is a valid command name. Command name is text of a command without leading '/'
   *
   * @param commandName the command name to be checked for validity
   * @return whether the command name is valid
   */
  public static boolean isValidCommandName(String commandName){
    if (commandName == null || commandName.length() > 31) return false;
    return commandName.matches("[A-Za-z_0-9]+");
  }
}
