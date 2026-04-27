package org.telegram.telegrambots.meta.api.methods.managed;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author Ruben Bermudez
 * @version 9.6
 */
public class TestGetManagedBotToken {

    @Test
    public void testGetManagedBotTokenGetPath() {
        GetManagedBotToken getManagedBotToken = GetManagedBotToken.builder()
                .userId(123456789L)
                .build();

        assertEquals("getManagedBotToken", getManagedBotToken.getMethod());
    }

    @Test
    public void testGetManagedBotTokenValidation() {
        GetManagedBotToken getManagedBotToken = GetManagedBotToken.builder()
                .userId(123456789L)
                .build();

        assertDoesNotThrow(getManagedBotToken::validate);
    }

    @Test
    public void testGetManagedBotTokenWithOptionalParams() {
        GetManagedBotToken getManagedBotToken = GetManagedBotToken.builder()
                .userId(123456789L)
                .build();

        assertEquals(123456789L, getManagedBotToken.getUserId());
    }
}
