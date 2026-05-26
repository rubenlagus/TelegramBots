package org.telegram.telegrambots.meta.api.methods.user;

import org.junit.jupiter.api.Test;
import org.telegram.telegrambots.meta.exceptions.TelegramApiValidationException;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * @author Ruben Bermudez
 * @version 10.0
 */
public class TestGetUserPersonalChatMessages {

    @Test
    public void testGetUserPersonalChatMessagesGetPath() {
        GetUserPersonalChatMessages method = GetUserPersonalChatMessages.builder()
                .userId(123456789L)
                .limit(10)
                .build();

        assertEquals("getUserPersonalChatMessages", method.getMethod());
    }

    @Test
    public void testGetUserPersonalChatMessagesValidation() {
        GetUserPersonalChatMessages method = GetUserPersonalChatMessages.builder()
                .userId(123456789L)
                .limit(10)
                .build();

        assertDoesNotThrow(method::validate);
    }

    @Test
    public void testGetUserPersonalChatMessagesMissingUserId() {
        assertThrows(NullPointerException.class, () -> GetUserPersonalChatMessages.builder()
                .limit(10)
                .build());
    }

    @Test
    public void testGetUserPersonalChatMessagesMissingLimit() {
        assertThrows(NullPointerException.class, () -> GetUserPersonalChatMessages.builder()
                .userId(123456789L)
                .build());
    }

    @Test
    public void testGetUserPersonalChatMessagesZeroUserId() {
        GetUserPersonalChatMessages method = GetUserPersonalChatMessages.builder()
                .userId(0L)
                .limit(10)
                .build();

        assertThrows(TelegramApiValidationException.class, method::validate);
    }

    @Test
    public void testGetUserPersonalChatMessagesLimitTooLow() {
        GetUserPersonalChatMessages method = GetUserPersonalChatMessages.builder()
                .userId(123456789L)
                .limit(0)
                .build();

        assertThrows(TelegramApiValidationException.class, method::validate);
    }

    @Test
    public void testGetUserPersonalChatMessagesLimitTooHigh() {
        GetUserPersonalChatMessages method = GetUserPersonalChatMessages.builder()
                .userId(123456789L)
                .limit(21)
                .build();

        assertThrows(TelegramApiValidationException.class, method::validate);
    }

    @Test
    public void testGetUserPersonalChatMessagesLimitBoundaryMin() {
        GetUserPersonalChatMessages method = GetUserPersonalChatMessages.builder()
                .userId(123456789L)
                .limit(1)
                .build();

        assertDoesNotThrow(method::validate);
    }

    @Test
    public void testGetUserPersonalChatMessagesLimitBoundaryMax() {
        GetUserPersonalChatMessages method = GetUserPersonalChatMessages.builder()
                .userId(123456789L)
                .limit(20)
                .build();

        assertDoesNotThrow(method::validate);
    }
}
