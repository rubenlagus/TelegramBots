package org.telegram.telegrambots.meta.api.objects;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

/**
 * @author Ruben Bermudez
 * @version 10.0
 */
public class TestSentGuestMessage {

    @Test
    public void testSentGuestMessageDefaultConstructor() {
        SentGuestMessage message = new SentGuestMessage();

        assertNull(message.getInlineMessageId());
    }

    @Test
    public void testSentGuestMessageWithInlineMessageId() {
        SentGuestMessage message = SentGuestMessage.builder()
                .inlineMessageId("test_inline_message_id")
                .build();

        assertEquals("test_inline_message_id", message.getInlineMessageId());
    }

    @Test
    public void testSentGuestMessageAllArgsConstructor() {
        SentGuestMessage message = new SentGuestMessage("inline_id_123");

        assertEquals("inline_id_123", message.getInlineMessageId());
    }

    @Test
    public void testSentGuestMessageSetter() {
        SentGuestMessage message = new SentGuestMessage();
        message.setInlineMessageId("new_inline_id");

        assertEquals("new_inline_id", message.getInlineMessageId());
    }
}
