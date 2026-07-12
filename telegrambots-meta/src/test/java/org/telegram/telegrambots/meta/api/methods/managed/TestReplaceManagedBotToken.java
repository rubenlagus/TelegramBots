package org.telegram.telegrambots.meta.api.methods.managed;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author Ruben Bermudez
 * @version 9.6
 */
public class TestReplaceManagedBotToken {

    @Test
    public void testReplaceManagedBotTokenGetPath() {
        ReplaceManagedBotToken replaceManagedBotToken = ReplaceManagedBotToken.builder()
                .userId(123456789L)
                .build();

        assertEquals("replaceManagedBotToken", replaceManagedBotToken.getMethod());
    }

    @Test
    public void testReplaceManagedBotTokenValidation() {
        ReplaceManagedBotToken replaceManagedBotToken = ReplaceManagedBotToken.builder()
                .userId(123456789L)
                .build();

        assertDoesNotThrow(replaceManagedBotToken::validate);
    }

    @Test
    public void testReplaceManagedBotTokenFields() {
        ReplaceManagedBotToken replaceManagedBotToken = ReplaceManagedBotToken.builder()
                .userId(123456789L)
                .build();

        assertEquals(123456789L, replaceManagedBotToken.getUserId());
    }
}
