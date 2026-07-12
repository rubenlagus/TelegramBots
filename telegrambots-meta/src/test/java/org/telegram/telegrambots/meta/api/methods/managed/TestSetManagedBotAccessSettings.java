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
public class TestSetManagedBotAccessSettings {

    @Test
    public void testSetManagedBotAccessSettingsGetPath() {
        SetManagedBotAccessSettings method = SetManagedBotAccessSettings.builder()
                .userId(123456789L)
                .isAccessRestricted(false)
                .build();

        assertEquals("setManagedBotAccessSettings", method.getMethod());
    }

    @Test
    public void testSetManagedBotAccessSettingsValidation() {
        SetManagedBotAccessSettings method = SetManagedBotAccessSettings.builder()
                .userId(123456789L)
                .isAccessRestricted(false)
                .build();

        assertDoesNotThrow(method::validate);
    }

    @Test
    public void testSetManagedBotAccessSettingsMissingUserId() {
        assertThrows(NullPointerException.class, () -> SetManagedBotAccessSettings.builder()
                .isAccessRestricted(false)
                .build());
    }

    @Test
    public void testSetManagedBotAccessSettingsMissingIsAccessRestricted() {
        assertThrows(NullPointerException.class, () -> SetManagedBotAccessSettings.builder()
                .userId(123456789L)
                .build());
    }

    @Test
    public void testSetManagedBotAccessSettingsZeroUserId() {
        SetManagedBotAccessSettings method = SetManagedBotAccessSettings.builder()
                .userId(0L)
                .isAccessRestricted(false)
                .build();

        assertThrows(TelegramApiValidationException.class, method::validate);
    }

    @Test
    public void testSetManagedBotAccessSettingsWithAddedUserIds() {
        SetManagedBotAccessSettings method = SetManagedBotAccessSettings.builder()
                .userId(123456789L)
                .isAccessRestricted(true)
                .addedUserId(111L)
                .addedUserId(222L)
                .build();

        assertDoesNotThrow(method::validate);
        assertEquals(2, method.getAddedUserIds().size());
    }

    @Test
    public void testSetManagedBotAccessSettingsTooManyAddedUserIds() {
        SetManagedBotAccessSettings.SetManagedBotAccessSettingsBuilder<?, ?> builder = SetManagedBotAccessSettings.builder()
                .userId(123456789L)
                .isAccessRestricted(true);

        for (int i = 1; i <= 11; i++) {
            builder.addedUserId((long) i);
        }
        SetManagedBotAccessSettings method = builder.build();

        assertThrows(TelegramApiValidationException.class, method::validate);
    }
}
