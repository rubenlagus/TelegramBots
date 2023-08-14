package org.telegram.telegrambots.extensions.bots.commandbot;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.telegram.telegrambots.bots.DefaultBotOptions;
import org.telegram.telegrambots.extensions.bots.commandbot.activity.CommandState;
import org.telegram.telegrambots.extensions.bots.commandbot.activity.DefaultUserActivityHandler;
import org.telegram.telegrambots.extensions.bots.commandbot.activity.UserActivity;
import org.telegram.telegrambots.extensions.bots.commandbot.activity.UserActivityHandler;
import org.telegram.telegrambots.extensions.bots.commandbot.commands.BotCommand;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.bots.AbsSender;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TelegramLongPollingCommandBotTest {

    @Mock
    Update update;
    @Mock
    Message message;
    @Mock
    CallbackQuery callbackQuery;

    FakeBot bot;

    @BeforeEach
    void setUp() {
    }

    @Test
    void shouldProcessNonCommandUpdate() {
        bot = new FakeBot(new DefaultBotOptions(), false, "TOKEN", null);
        when(update.hasMessage()).thenReturn(false);
        when(update.hasCallbackQuery()).thenReturn(false);

        bot.onUpdateReceived(update);

        assertTrue(bot.nonCommandUpdateInvoked);
        verify(update).hasMessage();
        verify(update).hasCallbackQuery();
    }

    @Test
    void shouldExecuteStatelessCommand() {
        bot = new FakeBot(new DefaultBotOptions(), false, "TOKEN", null);
        FakeCommand command = new FakeCommand("/test", "test");
        bot.register(command);
        when(update.hasMessage()).thenReturn(true);
        when(update.getMessage()).thenReturn(message);
        when(message.isCommand()).thenReturn(true);
        when(message.hasText()).thenReturn(true);
        when(message.getText()).thenReturn("/test");

        bot.onUpdateReceived(update);

        verify(update).hasMessage();
        verify(update).getMessage();
        verify(message).isCommand();
        verify(message).hasText();
        verify(message).getText();
        assertTrue(command.statelessExecute);
    }

    @Test
    void shouldNotExecuteStatelessCommand() {
        bot = new FakeBot(new DefaultBotOptions(), false, "TOKEN", null);
        FakeCommand command = new FakeCommand("/test", "test");
        bot.register(command);
        when(update.hasMessage()).thenReturn(true);
        when(update.getMessage()).thenReturn(message);
        when(message.isCommand()).thenReturn(true);
        when(message.hasText()).thenReturn(true);
        when(message.getText()).thenReturn("/error");

        bot.onUpdateReceived(update);

        verify(update).hasMessage();
        verify(update).getMessage();
        verify(message).isCommand();
        verify(message).hasText();
        verify(message).getText();
        assertFalse(command.statelessExecute);
    }

    @Test
    void shouldExecuteStatefulCommand() {
        bot = new FakeBot(new DefaultBotOptions(), false, "TOKEN", new DefaultUserActivityHandler());
        FakeCommand command = new FakeCommand("/test", "test");
        bot.register(command);
        when(update.hasMessage()).thenReturn(true);
        when(update.getMessage()).thenReturn(message);
        when(message.isCommand()).thenReturn(true);
        when(message.hasText()).thenReturn(true);
        when(message.getText()).thenReturn("/test");
        when(message.getFrom()).thenReturn(new User(1L, "TEST", false));

        bot.onUpdateReceived(update);

        verify(update).hasMessage();
        verify(update).getMessage();
        verify(message).isCommand();
        verify(message).hasText();
        verify(message).getText();
        verify(message).getFrom();
        assertTrue(command.statefulExecute);
    }

    @Test
    void shouldNotExecuteStatefulCommand() {
        bot = new FakeBot(new DefaultBotOptions(), false, "TOKEN", new DefaultUserActivityHandler());
        FakeCommand command = new FakeCommand("/test", "test");
        bot.register(command);
        when(update.hasMessage()).thenReturn(true);
        when(update.getMessage()).thenReturn(message);
        when(message.isCommand()).thenReturn(true);
        when(message.hasText()).thenReturn(true);
        when(message.getText()).thenReturn("/error");
        when(message.getFrom()).thenReturn(new User(1L, "TEST", false));

        bot.onUpdateReceived(update);

        verify(update).hasMessage();
        verify(update).getMessage();
        verify(message).isCommand();
        verify(message).hasText();
        verify(message).getText();
        verify(message).getFrom();
        assertFalse(command.statefulExecute);
    }

    @Test
    void shouldContinueStatefulCommand() {
        UserActivityHandler activityHandler = new DefaultUserActivityHandler();
        UserActivity userActivity = new UserActivity();
        userActivity.setCommandState(new CommandState<>("test", null));
        activityHandler.saveUserActivity(1L, userActivity);
        bot = new FakeBot(new DefaultBotOptions(), false, "TOKEN", activityHandler);
        FakeCommand command = new FakeCommand("/test", "test");
        bot.register(command);
        when(update.hasMessage()).thenReturn(true);
        when(update.getMessage()).thenReturn(message);
        when(message.isCommand()).thenReturn(false);
        when(message.hasText()).thenReturn(true);
        when(message.getText()).thenReturn("test");
        when(message.getFrom()).thenReturn(new User(1L, "TEST", false));

        bot.onUpdateReceived(update);

        verify(update).hasMessage();
        verify(update).getMessage();
        verify(message).isCommand();
        verify(message).hasText();
        verify(message).getText();
        verify(message).getFrom();
        assertTrue(command.statefulExecute);
    }

    @Test
    void shouldNotContinueStatefulCommandIfUserActivityIsNull() {
        UserActivityHandler activityHandler = new DefaultUserActivityHandler();
        bot = new FakeBot(new DefaultBotOptions(), false, "TOKEN", activityHandler);
        FakeCommand command = new FakeCommand("/test", "test");
        bot.register(command);
        when(update.hasMessage()).thenReturn(true);
        when(update.getMessage()).thenReturn(message);
        when(message.isCommand()).thenReturn(false);
        when(message.getFrom()).thenReturn(new User(1L, "TEST", false));

        bot.onUpdateReceived(update);

        verify(update).hasMessage();
        verify(update).getMessage();
        verify(message).isCommand();
        verify(message).getFrom();
        assertFalse(command.statefulExecute);
    }

    @Test
    void shouldNotContinueStatefulCommandIfInvalidUserActivity() {
        UserActivityHandler activityHandler = new DefaultUserActivityHandler();
        UserActivity userActivity = new UserActivity();
        userActivity.setCommandState(new CommandState<>("test", null));
        userActivity.setLastActivity(LocalDateTime.now().minusYears(10));
        activityHandler.saveUserActivity(1L, userActivity);
        bot = new FakeBot(new DefaultBotOptions(), false, "TOKEN", activityHandler);
        FakeCommand command = new FakeCommand("/test", "test");
        bot.register(command);
        when(update.hasMessage()).thenReturn(true);
        when(update.getMessage()).thenReturn(message);
        when(message.isCommand()).thenReturn(false);
        when(message.getFrom()).thenReturn(new User(1L, "TEST", false));

        bot.onUpdateReceived(update);

        verify(update).hasMessage();
        verify(update).getMessage();
        verify(message).isCommand();
        verify(message).getFrom();
        assertFalse(command.statefulExecute);
    }

    @Test
    void shouldContinueStatefulCommandWithCallbackQuery() {
        UserActivityHandler activityHandler = new DefaultUserActivityHandler();
        UserActivity userActivity = new UserActivity();
        userActivity.setCommandState(new CommandState<>("test", null));
        activityHandler.saveUserActivity(1L, userActivity);
        bot = new FakeBot(new DefaultBotOptions(), false, "TOKEN", activityHandler);
        FakeCommand command = new FakeCommand("/test", "test");
        bot.register(command);
        when(update.hasMessage()).thenReturn(false);
        when(update.hasCallbackQuery()).thenReturn(true);
        when(update.getCallbackQuery()).thenReturn(callbackQuery);
        when(callbackQuery.getMessage()).thenReturn(message);
        when(message.getFrom()).thenReturn(new User(1L, "TEST", false));

        bot.onUpdateReceived(update);

        verify(update).hasMessage();
        verify(update).hasCallbackQuery();
        verify(update).getCallbackQuery();
        verify(message).getFrom();
        assertTrue(command.statefulExecute);
    }

    @Test
    void shouldNotContinueStatefulCommandWithCallbackQueryIfUserActivityIsNull() {
        UserActivityHandler activityHandler = new DefaultUserActivityHandler();
        bot = new FakeBot(new DefaultBotOptions(), false, "TOKEN", activityHandler);
        FakeCommand command = new FakeCommand("/test", "test");
        bot.register(command);
        when(update.hasMessage()).thenReturn(false);
        when(update.hasCallbackQuery()).thenReturn(true);
        when(update.getCallbackQuery()).thenReturn(callbackQuery);
        when(callbackQuery.getMessage()).thenReturn(message);
        when(message.getFrom()).thenReturn(new User(1L, "TEST", false));

        bot.onUpdateReceived(update);

        verify(update).hasMessage();
        verify(update).hasCallbackQuery();
        verify(update).getCallbackQuery();
        verify(message).getFrom();
        assertFalse(command.statefulExecute);
    }

    @Test
    void shouldNotContinueStatefulCommandWithCallbackQueryIfInvalidUserActivity() {
        UserActivityHandler activityHandler = new DefaultUserActivityHandler();
        UserActivity userActivity = new UserActivity();
        userActivity.setCommandState(new CommandState<>("test", null));
        userActivity.setLastActivity(LocalDateTime.now().minusYears(10));
        activityHandler.saveUserActivity(1L, userActivity);
        bot = new FakeBot(new DefaultBotOptions(), false, "TOKEN", activityHandler);
        FakeCommand command = new FakeCommand("/test", "test");
        bot.register(command);
        when(update.hasMessage()).thenReturn(false);
        when(update.hasCallbackQuery()).thenReturn(true);
        when(update.getCallbackQuery()).thenReturn(callbackQuery);
        when(callbackQuery.getMessage()).thenReturn(message);
        when(message.getFrom()).thenReturn(new User(1L, "TEST", false));

        bot.onUpdateReceived(update);

        verify(update).hasMessage();
        verify(update).hasCallbackQuery();
        verify(update).getCallbackQuery();
        verify(message).getFrom();
        assertFalse(command.statefulExecute);
    }

    static class FakeBot extends TelegramLongPollingCommandBot {

        boolean nonCommandUpdateInvoked = false;

        public FakeBot(DefaultBotOptions options, boolean allowCommandsWithUsername, String botToken, UserActivityHandler userActivityHandler) {
            super(options, allowCommandsWithUsername, botToken, userActivityHandler);
        }

        @Override
        public void processNonCommandUpdate(Update update) {
            nonCommandUpdateInvoked = true;
        }

        @Override
        public String getBotUsername() {
            return "BotUsername";
        }
    }

    static class FakeCommand extends BotCommand {

        boolean statelessExecute = false;
        boolean statefulExecute = false;

        public FakeCommand(String commandIdentifier, String description) {
            super(commandIdentifier, description);
        }

        @Override
        public void processMessage(AbsSender absSender, Message message, String[] arguments) {
            statelessExecute = true;
        }

        @Override
        public CommandState<?> processMessage(AbsSender absSender, Message message, String[] arguments, CommandState<?> commandState) {
            statefulExecute = true;
            return commandState;
        }
    }
}