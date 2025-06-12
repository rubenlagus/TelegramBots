package org.telegram.telegrambots.meta.api.methods.business;

import org.junit.jupiter.api.Test;
import org.telegram.telegrambots.meta.exceptions.TelegramApiValidationException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;

/**
 * @author Ruben Bermudez
 * @version 9.0
 */
public class TestReadBusinessMessage {

    @Test
    public void testReadBusinessMessageGetPath() {
        ReadBusinessMessage readBusinessMessage = ReadBusinessMessage.builder()
                .businessConnectionId("test-connection-id")
                .chatId(123456789L)
                .messageId(987654321L)
                .build();

        assertEquals("readBusinessMessage", readBusinessMessage.getMethod());
    }

    @Test
    public void testReadBusinessMessageValidation() {
        ReadBusinessMessage readBusinessMessage = ReadBusinessMessage.builder()
                .businessConnectionId("test-connection-id")
                .chatId(123456789L)
                .messageId(987654321L)
                .build();

        // Should not throw an exception
        try {
            readBusinessMessage.validate();
        } catch (TelegramApiValidationException e) {
            fail();
        }

        // Test with empty business connection ID
        ReadBusinessMessage invalidReadBusinessMessage = ReadBusinessMessage.builder()
                .businessConnectionId("")
                .chatId(123456789L)
                .messageId(987654321L)
                .build();

        assertThrows(TelegramApiValidationException.class, invalidReadBusinessMessage::validate);
    }

    @Test
    public void testReadBusinessMessageGetters() {
        ReadBusinessMessage readBusinessMessage = ReadBusinessMessage.builder()
                .businessConnectionId("test-connection-id")
                .chatId(123456789L)
                .messageId(987654321L)
                .build();

        assertEquals("test-connection-id", readBusinessMessage.getBusinessConnectionId());
        assertEquals(Long.valueOf(123456789L), readBusinessMessage.getChatId());
        assertEquals(Long.valueOf(987654321L), readBusinessMessage.getMessageId());
    }
}
