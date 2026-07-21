package org.telegram.telegrambots.abilitybots.api.bot;

import org.jetbrains.annotations.NotNull;
import org.mockito.Mockito;
import org.telegram.telegrambots.abilitybots.api.objects.MessageContext;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.api.objects.message.Message;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.telegram.telegrambots.abilitybots.api.objects.MessageContext.newContext;

public final class TestUtils {
  public static final User USER = User.builder()
          .id(1L)
          .firstName("first")
          .isBot(false)
          .lastName("last")
          .userName("username")
          .languageCode(null)
          .canJoinGroups(false)
          .canReadAllGroupMessages(false)
          .supportInlineQueries(false)
          .isPremium(false)
          .addedToAttachmentMenu(false)
          .canConnectToBusiness(false)
          .hasMainWebApp(false)
          .hasTopicsEnabled(false)
          .build();
  public static final User CREATOR = User.builder()
          .id(1337L)
          .firstName("creatorFirst")
          .isBot(false)
          .lastName("creatorLast")
          .userName("creatorUsername")
          .languageCode(null)
          .canJoinGroups(false)
          .canReadAllGroupMessages(false)
          .supportInlineQueries(false)
          .isPremium(false)
          .addedToAttachmentMenu(false)
          .canConnectToBusiness(false)
          .hasMainWebApp(false)
          .hasTopicsEnabled(false)
          .build();

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
    when(message.getChatId()).thenReturn(user.getId());
    when(update.getMessage()).thenReturn(message);
    return update;
  }

  @NotNull
  static MessageContext mockContext(User user, long groupId, String... args) {
    Update update = mock(Update.class);
    Message message = mock(Message.class);
    BaseAbilityBot bot = Mockito.mock(BaseAbilityBot.class);

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
