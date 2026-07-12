package org.telegram.telegrambots.meta.api.methods.reactions;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class TestDeleteMessageReaction {

    @Test
    public void testDeleteMessageReactionGetPath() {
        DeleteMessageReaction deleteMessageReaction = DeleteMessageReaction.builder()
                .chatId("123456789")
                .messageId(1)
                .userId(123456L)
                .build();

        assertEquals("deleteMessageReaction", deleteMessageReaction.getMethod());
    }

    @Test
    public void testDeleteMessageReactionValidation() {
        DeleteMessageReaction deleteMessageReaction = DeleteMessageReaction.builder()
                .chatId("123456789")
                .messageId(1)
                .build();

        assertDoesNotThrow(deleteMessageReaction::validate);
    }

    @Test
    public void testDeleteMessageReactionValidationMissingChatId() {
        assertThrows(NullPointerException.class, () -> DeleteMessageReaction.builder()
                .messageId(1)
                .build());
    }

    @Test
    public void testDeleteMessageReactionValidationMissingMessageId() {
        assertThrows(NullPointerException.class, () -> DeleteMessageReaction.builder()
                .chatId("123456789")
                .build());
    }
}
