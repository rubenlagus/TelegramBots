package org.telegram.abilitybots.api.objects;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import org.telegram.abilitybots.api.bot.BaseAbilityBot;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.User;

import java.util.Arrays;

/**
 * MessageContext is a wrapper class to the {@link Update}, originating end-user and the arguments present in its message (if any).
 * <p>
 * A user is not bound to the usage of this higher level context as it's possible to fetch the underlying {@link Update} using {@link MessageContext#update()}.
 *
 * @author Abbas Abou Daya
 */
public class MessageContext {
  private final User user;
  private final Long chatId;
  private final String[] arguments;
  private final Update update;
  private final BaseAbilityBot bot;

  private MessageContext(Update update, User user, Long chatId, BaseAbilityBot bot, String[] arguments) {
    this.user = user;
    this.chatId = chatId;
    this.update = update;
    this.bot = bot;
    this.arguments = arguments;
  }

  public static MessageContext newContext(Update update, User user, Long chatId, BaseAbilityBot bot, String... arguments) {
    return new MessageContext(update, user, chatId, bot, arguments);
  }

  /**
   * @return the originating Telegram user of this update
   */
  public User user() {
    return user;
  }

  /**
   * @return the originating chatId, maps correctly to both group and user-private chats
   */
  public Long chatId() {
    return chatId;
  }

  /**
   * @return the bot in which this message is executed
   */
  public BaseAbilityBot bot() {
    return bot;
  }

  /**
   * If there's no message in the update, then this will an empty array.
   *
   * @return the text sent by the user message.
   */
  public String[] arguments() {
    return arguments;
  }

  /**
   * @return the first argument directly after the command
   * @throws IllegalStateException if message has no arguments
   */
  public String firstArg() {
    checkLength();
    return arguments[0];
  }

  /**
   * @return the second argument directly after the command
   * @throws IllegalStateException if message has no arguments
   */
  public String secondArg() {
    checkLength();
    return arguments[1 % arguments.length];
  }

  /**
   * @return the third argument directly after the command
   * @throws IllegalStateException if message has no arguments
   */
  public String thirdArg() {
    checkLength();
    return arguments[2 % arguments.length];
  }

  /**
   * @return the actual update behind this context
   */
  public Update update() {
    return update;
  }

  private void checkLength() {
    if (arguments.length == 0)
      throw new IllegalStateException("This message has no arguments");
  }

  @Override
  public String toString() {
    return MoreObjects.toStringHelper(this)
        .add("user", user)
        .add("chatId", chatId)
        .add("arguments", arguments)
        .add("update", update)
        .toString();
  }

  @Override
  public boolean equals(Object o) {
    if (this == o)
      return true;
    if (o == null || getClass() != o.getClass())
      return false;

    MessageContext that = (MessageContext) o;
    return Objects.equal(user, that.user) &&
        Objects.equal(chatId, that.chatId) &&
        Arrays.equals(arguments, that.arguments) &&
        Objects.equal(update, that.update);
  }

  @Override
  public int hashCode() {
    return Objects.hashCode(user, chatId, Arrays.hashCode(arguments), update);
  }
}
