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
public class DeleteMyCommandsTest {
    @Test
    public void testDeleteMyCommandsWithEmptyScope() {
        DeleteMyCommands deleteMyCommands = DeleteMyCommands.builder().build();
        assertEquals("deleteMyCommands", deleteMyCommands.getMethod());
        assertDoesNotThrow(deleteMyCommands::validate);
    }

    @Test
    public void testDeleteMyCommandsWithEmptyStringLanguageCode() {
        DeleteMyCommands deleteMyCommands = DeleteMyCommands.builder().languageCode("").build();
        assertEquals("deleteMyCommands", deleteMyCommands.getMethod());
        Throwable thrown = assertThrows(TelegramApiValidationException.class, deleteMyCommands::validate);
        assertEquals("LanguageCode parameter can't be empty string", thrown.getMessage());
    }

    @Test
    public void testDeleteMyCommandsWithScope() {
        DeleteMyCommands deleteMyCommands = DeleteMyCommands
                .builder()
                .scope(BotCommandScopeDefault.builder().build())
                .build();
        assertEquals("deleteMyCommands", deleteMyCommands.getMethod());
        assertEquals("default", deleteMyCommands.getScope().getType());
        assertDoesNotThrow(deleteMyCommands::validate);
    }
}
