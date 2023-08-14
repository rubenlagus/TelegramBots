package org.telegram.telegrambots.extensions.bots.commandbot.commands;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.telegram.telegrambots.extensions.bots.commandbot.activity.CommandState;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.bots.AbsSender;

import java.util.function.BiConsumer;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CommandRegistryTest {

    @Mock
    AbsSender absSender;
    @Mock
    Message message;
    @Mock
    CallbackQuery callbackQuery;

    CommandRegistry registry;

    FakeBotCommand command;
    FakeDefaultConsumer defaultConsumer;

    @BeforeEach
    void setUp() {
        registry = new CommandRegistry(true, () -> "BotUsername");
        command = new FakeBotCommand("/test", "test");
        registry.register(command);

        defaultConsumer = new FakeDefaultConsumer();
        registry.registerDefaultAction(defaultConsumer);
    }

    @Test
    void shouldNotExecuteStatelessCommandWithoutTextInMessage() {
        when(message.hasText()).thenReturn(false);

        boolean result = registry.executeCommand(absSender, message);

        assertFalse(result);
        verify(message).hasText();
    }

    @Test
    void shouldNotExecuteStatelessCommandIfMessageTextNotCommand() {
        when(message.hasText()).thenReturn(true);
        when(message.getText()).thenReturn("test");

        boolean result = registry.executeCommand(absSender, message);

        assertFalse(result);
        verify(message).hasText();
        verify(message).getText();
    }

    @Test
    void should_executes_statelessCommandWithBotUsername() {
        when(message.hasText()).thenReturn(true);
        when(message.getText()).thenReturn("/test@BotUsername I'll be test!");

        boolean result = registry.executeCommand(absSender, message);

        assertTrue(result, "Command must be executed");
        assertTrue(command.statelessExecute);
        assertArrayEquals(new String[]{"I'll", "be", "test!"}, command.arguments);
        verify(message).hasText();
        verify(message).getText();
    }

    @Test
    void shouldUseDefaultConsumerForStatelessCommand() {
        when(message.hasText()).thenReturn(true);
        when(message.getText()).thenReturn("/error@BotUsername I'll be test!");

        boolean result = registry.executeCommand(absSender, message);

        assertTrue(result, "Command must be executed");
        assertFalse(command.statelessExecute);
        assertTrue(defaultConsumer.used);
        verify(message).hasText();
        verify(message).getText();
    }

    @Test
    void should_throws_NPE_on_executes_statelessCommandWithNullableBotUsername() {
        registry = new CommandRegistry(true, () -> null);
        registry.register(new FakeBotCommand("/test", "test"));
        when(message.hasText()).thenReturn(true);
        when(message.getText()).thenReturn("/test@BotUsername ignore");

        NullPointerException exception = assertThrows(
                NullPointerException.class, () -> registry.executeCommand(absSender, message), "Bot username is null");
        Assertions.assertEquals("Bot username must not be null", exception.getMessage(), "Invalid exception message");
    }

    @Test
    void shouldNotExecuteStatefulCommandWithoutTextInMessage() {
        when(message.hasText()).thenReturn(false);

        CommandState<?> result = registry.executeCommand(absSender, message, new CommandState<>(null, null));

        assertNull(result);
        verify(message).hasText();
    }

    @Test
    void shouldExecuteStatefulCommandIfMessageTextNotCommand() {
        final CommandState<?> expectedState = new CommandState<>("test", null);
        when(message.hasText()).thenReturn(true);
        when(message.getText()).thenReturn("test");

        CommandState<?> result = registry.executeCommand(absSender, message, expectedState);

        assertNotNull(result);
        assertEquals(expectedState, result);
        assertTrue(command.statefulExecute);
        assertArrayEquals(new String[0], command.arguments);
        verify(message).hasText();
        verify(message).getText();
    }

    @Test
    void shouldNotExecuteStatefulCommandIfMessageTextNotCommand() {
        final CommandState<?> expectedState = new CommandState<>("error", null);
        when(message.hasText()).thenReturn(true);
        when(message.getText()).thenReturn("test");

        CommandState<?> result = registry.executeCommand(absSender, message, expectedState);

        assertNull(result);
        verify(message).hasText();
        verify(message).getText();
    }

    @Test
    void shouldExecuteStatefulCommandWithBotUsername() {
        final CommandState<?> expectedState = new CommandState<>("test", null);
        when(message.hasText()).thenReturn(true);
        when(message.getText()).thenReturn("/test@BotUsername I'll be test!");

        CommandState<?> result = registry.executeCommand(absSender, message, expectedState);

        assertNotNull(result);
        assertEquals(expectedState, result);
        assertTrue(command.statefulExecute);
        assertArrayEquals(new String[]{"I'll", "be", "test!"}, command.arguments);
        verify(message).hasText();
        verify(message).getText();
    }

    @Test
    void shouldUseDefaultConsumerForStatefulCommand() {
        final CommandState<?> expectedState = new CommandState<>("test", null);
        when(message.hasText()).thenReturn(true);
        when(message.getText()).thenReturn("/error@BotUsername I'll be test!");

        CommandState<?> result = registry.executeCommand(absSender, message, expectedState);

        assertNotNull(result);
        assertEquals(new CommandState<>("error", null), result);
        assertFalse(command.statefulExecute);
        assertTrue(defaultConsumer.used);
        verify(message).hasText();
        verify(message).getText();
    }

    @Test
    void should_throws_NPE_on_executes_statefulCommandWithNullableBotUsername() {
        registry = new CommandRegistry(true, () -> null);
        registry.register(new FakeBotCommand("/test", "test"));
        when(message.hasText()).thenReturn(true);
        when(message.getText()).thenReturn("/test@BotUsername ignore");

        NullPointerException exception = assertThrows(
                NullPointerException.class, () -> registry.executeCommand(absSender, message, new CommandState<>(null, null)),
                "Bot username is null");
        Assertions.assertEquals("Bot username must not be null", exception.getMessage(), "Invalid exception message");
    }

    @Test
    void shouldNotContinueStatefulCommand() {
        final CommandState<?> expectedState = new CommandState<>("error", null);
        when(callbackQuery.getMessage()).thenReturn(message);
        when(callbackQuery.getData()).thenReturn("data1 data2");

        CommandState<?> result = registry.executeCommand(absSender, callbackQuery, expectedState);

        assertNull(result);
        assertFalse(command.statefulExecute);
        verify(callbackQuery).getMessage();
        verify(callbackQuery).getData();
    }

    @Test
    void shouldContinueStatefulCommand() {
        final CommandState<?> expectedState = new CommandState<>("test", null);
        when(callbackQuery.getId()).thenReturn("123123123");
        when(callbackQuery.getMessage()).thenReturn(message);
        when(callbackQuery.getData()).thenReturn("data1 data2");

        CommandState<?> result = registry.executeCommand(absSender, callbackQuery, expectedState);

        assertNotNull(result);
        assertEquals(expectedState, result);
        assertTrue(command.statefulExecute);
        assertArrayEquals(new String[]{"123123123", "data1", "data2"}, command.arguments);
        verify(callbackQuery).getMessage();
        verify(callbackQuery).getData();
    }

    static class FakeBotCommand extends BotCommand {

        boolean statelessExecute = false;
        boolean statefulExecute = false;
        String[] arguments;

        public FakeBotCommand(String commandIdentifier, String description) {
            super(commandIdentifier, description);
        }

        @Override
        public void processMessage(AbsSender absSender, Message message, String[] arguments) {
            statelessExecute = true;
            this.arguments = arguments;
        }

        @Override
        public CommandState<?> processMessage(AbsSender absSender, Message message, String[] arguments, CommandState<?> commandState) {
            statefulExecute = true;
            this.arguments = arguments;
            return commandState;
        }
    }

    static class FakeDefaultConsumer implements BiConsumer<AbsSender, Message> {

        boolean used = false;

        @Override
        public void accept(AbsSender absSender, Message message) {
            used = true;
        }
    }
}