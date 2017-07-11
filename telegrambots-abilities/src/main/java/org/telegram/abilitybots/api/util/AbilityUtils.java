package org.telegram.abilitybots.api.util;

import org.telegram.abilitybots.api.db.DBContext;
import org.telegram.abilitybots.api.objects.MessageContext;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.api.objects.User;

import java.util.function.Consumer;
import java.util.function.Predicate;

import static org.telegram.abilitybots.api.objects.Flag.*;

/**
 * Helper and utility methods
 */
public final class AbilityUtils {
  private AbilityUtils() {

  }

  /**
   * @param username any username
   * @return the username with the preceding "@" stripped off
   */
  public static String stripTag(String username) {
    String lowerCase = username.toLowerCase();
    return lowerCase.startsWith("@") ? lowerCase.substring(1, lowerCase.length()) : lowerCase;
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
    if (MESSAGE.test(update)) {
      return update.getMessage().getFrom();
    } else if (CALLBACK_QUERY.test(update)) {
      return update.getCallbackQuery().getFrom();
    } else if (INLINE_QUERY.test(update)) {
      return update.getInlineQuery().getFrom();
    } else if (CHANNEL_POST.test(update)) {
      return update.getChannelPost().getFrom();
    } else if (EDITED_CHANNEL_POST.test(update)) {
      return update.getEditedChannelPost().getFrom();
    } else if (EDITED_MESSAGE.test(update)) {
      return update.getEditedMessage().getFrom();
    } else if (CHOSEN_INLINE_QUERY.test(update)) {
      return update.getChosenInlineQuery().getFrom();
    } else {
      throw new IllegalStateException("Could not retrieve originating user from update");
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
    if (MESSAGE.test(update)) {
      return update.getMessage().getChatId();
    } else if (CALLBACK_QUERY.test(update)) {
      return update.getCallbackQuery().getMessage().getChatId();
    } else if (INLINE_QUERY.test(update)) {
      return (long) update.getInlineQuery().getFrom().getId();
    } else if (CHANNEL_POST.test(update)) {
      return update.getChannelPost().getChatId();
    } else if (EDITED_CHANNEL_POST.test(update)) {
      return update.getEditedChannelPost().getChatId();
    } else if (EDITED_MESSAGE.test(update)) {
      return update.getEditedMessage().getChatId();
    } else if (CHOSEN_INLINE_QUERY.test(update)) {
      return (long) update.getChosenInlineQuery().getFrom().getId();
    } else {
      throw new IllegalStateException("Could not retrieve originating chat ID from update");
    }
  }

  /**
   * @param update a Telegram {@link Update}
   * @return <tt>true</tt> if the update contains contains a private user message
   */
  public static boolean isUserMessage(Update update) {
    if (MESSAGE.test(update)) {
      return update.getMessage().isUserMessage();
    } else if (CALLBACK_QUERY.test(update)) {
      return update.getCallbackQuery().getMessage().isUserMessage();
    } else if (CHANNEL_POST.test(update)) {
      return update.getChannelPost().isUserMessage();
    } else if (EDITED_CHANNEL_POST.test(update)) {
      return update.getEditedChannelPost().isUserMessage();
    } else if (EDITED_MESSAGE.test(update)) {
      return update.getEditedMessage().isUserMessage();
    } else if (CHOSEN_INLINE_QUERY.test(update) || INLINE_QUERY.test(update)) {
      return true;
    } else {
      throw new IllegalStateException("Could not retrieve update context origin (user/group)");
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
}
