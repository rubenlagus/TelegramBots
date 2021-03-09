package org.telegram.abilitybots.api.bot;

import org.jetbrains.annotations.NotNull;
import org.telegram.abilitybots.api.objects.MessageContext;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.User;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.telegram.abilitybots.api.objects.MessageContext.newContext;

public final class TestUtils {
  public static final User USER = new User(1L, "first", false, "last", "username", null, false, false, false);
  public static final User CREATOR = new User(1337L, "creatorFirst", false, "creatorLast", "creatorUsername", null, false, false, false);

  private TestUtils() {

  }

  @NotNull
  static Update mockFullUpdate(AbilityBot bot, User user, String args) {
    bot.users().put(USER.getId(), USER);
    bot.users().put(CREATOR.getId(), CREATOR);
    bot.userIds().put(CREATOR.getUserName(), CREATOR.getId());
    bot.userIds().put(USER.getUserName(), USER.getId());

    bot.admins().add(CREATOR.getId());

    Update update = mock(Update.class);
    when(update.hasMessage()).thenReturn(true);
    Message message = mock(Message.class);
    when(message.getFrom()).thenReturn(user);
    when(message.getText()).thenReturn(args);
    when(message.hasText()).thenReturn(true);
    when(message.isUserMessage()).thenReturn(true);
    when(message.getChatId()).thenReturn((long) user.getId());
    when(update.getMessage()).thenReturn(message);
    return update;
  }

  @NotNull
  static MessageContext mockContext(User user, long groupId, String... args) {
    Update update = mock(Update.class);
    Message message = mock(Message.class);
    BaseAbilityBot bot = mock(BaseAbilityBot.class);

    when(update.hasMessage()).thenReturn(true);
    when(update.getMessage()).thenReturn(message);

    when(message.getFrom()).thenReturn(user);
    when(message.hasText()).thenReturn(true);

    return newContext(update, user, groupId, bot, args);
  }

  @NotNull
  static MessageContext mockContext(User user) {
    return mockContext(user, user.getId());
  }
}
