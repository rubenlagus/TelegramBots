package org.telegram.telegrambots.meta.api.methods.commands;

import org.junit.jupiter.api.Test;
import org.telegram.telegrambots.meta.api.objects.commands.BotCommand;
import org.telegram.telegrambots.meta.api.objects.commands.scope.BotCommandScopeDefault;
import org.telegram.telegrambots.meta.exceptions.TelegramApiValidationException;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * @author Ruben Bermudez
 * @version 5.3
 */
public class SetMyCommandsTest {
    @Test
    public void testSetMyCommandsWithAllSet() {
        SetMyCommands setMyCommands = SetMyCommands
                .builder()
                .command(BotCommand.builder().command("test").description("Test description").build())
                .languageCode("en")
                .scope(BotCommandScopeDefault.builder().build())
                .build();
        assertEquals("setMyCommands", setMyCommands.getMethod());
        assertDoesNotThrow(setMyCommands::validate);
    }

    @Test
    public void testSetMyCommandsWithEmptyScope() {
        SetMyCommands setMyCommands = SetMyCommands
                .builder()
                .command(BotCommand.builder().command("test").description("Test description").build())
                .languageCode("en")
                .build();
        assertEquals("setMyCommands", setMyCommands.getMethod());
        assertDoesNotThrow(setMyCommands::validate);
    }

    @Test
    public void testSetMyCommandsWithEmptyStringLanguageCode() {
        SetMyCommands setMyCommands = SetMyCommands
                .builder()
                .command(BotCommand.builder().command("test").description("Test description").build())
                .languageCode("")
                .scope(BotCommandScopeDefault.builder().build())
                .build();
        assertEquals("setMyCommands", setMyCommands.getMethod());
        Throwable thrown = assertThrows(TelegramApiValidationException.class, setMyCommands::validate);
        assertEquals("LanguageCode parameter can't be empty string", thrown.getMessage());
    }

    @Test
    public void testSetMyCommandsWithEmptyCommands() {
        SetMyCommands setMyCommands = SetMyCommands
                .builder()
                .languageCode("en")
                .scope(BotCommandScopeDefault.builder().build())
                .build();
        assertEquals("setMyCommands", setMyCommands.getMethod());
        Throwable thrown = assertThrows(TelegramApiValidationException.class, setMyCommands::validate);
        assertEquals("Commands parameter can't be empty", thrown.getMessage());
    }

    @Test
    public void testSetMyCommandsWithMoreThan100Commands() {
        SetMyCommands setMyCommands = SetMyCommands
                .builder()
                .languageCode("en")
                .scope(BotCommandScopeDefault.builder().build())
                .build();
        List<BotCommand> commands = new ArrayList<>();
        for(int i = 0; i < 102; i++) {
            commands.add(BotCommand.builder().command("test").description("Test Description").build());
        }
        setMyCommands.setCommands(commands);
        assertEquals("setMyCommands", setMyCommands.getMethod());
        Throwable thrown = assertThrows(TelegramApiValidationException.class, setMyCommands::validate);
        assertEquals("No more than 100 commands are allowed", thrown.getMessage());
    }
}
