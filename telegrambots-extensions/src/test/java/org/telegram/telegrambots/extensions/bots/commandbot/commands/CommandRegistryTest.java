package org.telegram.telegrambots.extensions.bots.commandbot.commands;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.bots.AbsSender;

class CommandRegistryTest {

    @Test
    void should_executes_commandWithBotUsername() {
        CommandRegistry registry = new CommandRegistry(true, () -> "BotUsername");
        IBotCommand command = Mockito.mock(IBotCommand.class);
        Mockito.when(command.getCommandIdentifier()).thenReturn("command");
        registry.register(command);
        AbsSender absSender = Mockito.mock(AbsSender.class);
        Message message = Mockito.mock(Message.class);
        Mockito.when(message.hasText()).thenReturn(true);
        Mockito.when(message.getText()).thenReturn("/command@BotUsername I'll be test!");
        Assertions.assertTrue(registry.executeCommand(absSender, message), "Command must be executed");
        Mockito.verify(message).hasText();
        Mockito.verify(message).getText();
        Mockito.verify(command).processMessage(
                Mockito.same(absSender), Mockito.same(message), Mockito.eq(new String[]{"I'll", "be", "test!"}));
    }

    @Test
    void should_throws_NPE_on_executes_commandWithNullableBotUsername() {
        CommandRegistry registry = new CommandRegistry(true, () -> null);
        IBotCommand command = Mockito.mock(IBotCommand.class);
        Mockito.when(command.getCommandIdentifier()).thenReturn("command");
        registry.register(command);
        AbsSender absSender = Mockito.mock(AbsSender.class);
        Message message = Mockito.mock(Message.class);
        Mockito.when(message.hasText()).thenReturn(true);
        Mockito.when(message.getText()).thenReturn("/command@BotUsername ignore");
        NullPointerException exception = Assertions.assertThrows(
                NullPointerException.class, () -> registry.executeCommand(absSender, message), "Bot username is null");
        Assertions.assertEquals("Bot username must not be null", exception.getMessage(), "Invalid exception message");
    }
}