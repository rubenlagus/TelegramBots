package org.telegram.telegrambots.meta.api.methods.managed;

import org.junit.jupiter.api.Test;
import org.telegram.telegrambots.meta.exceptions.TelegramApiValidationException;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * @author Ruben Bermudez
 * @version 10.0
 */
public class TestGetManagedBotAccessSettings {

    @Test
    public void testGetManagedBotAccessSettingsGetPath() {
        GetManagedBotAccessSettings method = GetManagedBotAccessSettings.builder()
                .userId(123456789L)
                .build();

        assertEquals("getManagedBotAccessSettings", method.getMethod());
    }

    @Test
    public void testGetManagedBotAccessSettingsValidation() {
        GetManagedBotAccessSettings method = GetManagedBotAccessSettings.builder()
                .userId(123456789L)
                .build();

        assertDoesNotThrow(method::validate);
    }

    @Test
    public void testGetManagedBotAccessSettingsMissingUserId() {
        assertThrows(NullPointerException.class, () -> GetManagedBotAccessSettings.builder()
                .build());
    }

    @Test
    public void testGetManagedBotAccessSettingsZeroUserId() {
        GetManagedBotAccessSettings method = GetManagedBotAccessSettings.builder()
                .userId(0L)
                .build();

        assertThrows(TelegramApiValidationException.class, method::validate);
    }

    @Test
    public void testGetManagedBotAccessSettingsUserId() {
        GetManagedBotAccessSettings method = GetManagedBotAccessSettings.builder()
                .userId(123456789L)
                .build();

        assertEquals(123456789L, method.getUserId());
    }
}
