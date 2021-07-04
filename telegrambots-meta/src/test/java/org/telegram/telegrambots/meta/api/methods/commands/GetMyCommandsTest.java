package org.telegram.telegrambots.meta.api.methods.commands;

import org.junit.jupiter.api.Test;
import org.telegram.telegrambots.meta.api.objects.commands.scope.BotCommandScopeDefault;
import org.telegram.telegrambots.meta.exceptions.TelegramApiValidationException;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * @author Ruben Bermudez
 * @version 5.3
 */
public class GetMyCommandsTest {
    @Test
    public void testGetMyCommandsWithAllSet() {
        GetMyCommands getMyCommands = GetMyCommands
                .builder()
                .languageCode("en")
                .scope(BotCommandScopeDefault.builder().build())
                .build();
        assertEquals("getMyCommands", getMyCommands.getMethod());
        assertDoesNotThrow(getMyCommands::validate);
    }

    @Test
    public void testGetMyCommandsWithEmptyScope() {
        GetMyCommands getMyCommands = GetMyCommands
                .builder()
                .languageCode("en")
                .build();
        assertEquals("getMyCommands", getMyCommands.getMethod());
        assertDoesNotThrow(getMyCommands::validate);
    }

    @Test
    public void testSetMyCommandsWithEmptyStringLanguageCode() {
        GetMyCommands getMyCommands = GetMyCommands
                .builder()
                .languageCode("")
                .scope(BotCommandScopeDefault.builder().build())
                .build();
        assertEquals("getMyCommands", getMyCommands.getMethod());
        Throwable thrown = assertThrows(TelegramApiValidationException.class, getMyCommands::validate);
        assertEquals("LanguageCode parameter can't be empty string", thrown.getMessage());
    }
}
