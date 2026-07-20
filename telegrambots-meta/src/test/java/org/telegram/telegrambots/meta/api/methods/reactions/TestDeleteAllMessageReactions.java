package org.telegram.telegrambots.meta.api.methods.reactions;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * @author Ruben Bermudez
 * @version 10.0
 */
public class TestDeleteAllMessageReactions {

    @Test
    public void testDeleteAllMessageReactionsGetPath() {
        DeleteAllMessageReactions method = DeleteAllMessageReactions.builder()
                .chatId("123456789")
                .build();

        assertEquals("deleteAllMessageReactions", method.getMethod());
    }

    @Test
    public void testDeleteAllMessageReactionsValidation() {
        DeleteAllMessageReactions method = DeleteAllMessageReactions.builder()
                .chatId("123456789")
                .build();

        assertDoesNotThrow(method::validate);
    }

    @Test
    public void testDeleteAllMessageReactionsMissingChatId() {
        assertThrows(NullPointerException.class, () -> DeleteAllMessageReactions.builder()
                .build());
    }

    @Test
    public void testDeleteAllMessageReactionsWithLongChatId() {
        DeleteAllMessageReactions method = DeleteAllMessageReactions.builder()
                .chatId(123456789L)
                .build();

        assertEquals("123456789", method.getChatId());
        assertDoesNotThrow(method::validate);
    }

    @Test
    public void testDeleteAllMessageReactionsWithOptionalUserId() {
        DeleteAllMessageReactions method = DeleteAllMessageReactions.builder()
                .chatId("123456789")
                .userId(987654321L)
                .build();

        assertDoesNotThrow(method::validate);
        assertEquals(987654321L, method.getUserId());
    }

    @Test
    public void testDeleteAllMessageReactionsWithOptionalActorChatId() {
        DeleteAllMessageReactions method = DeleteAllMessageReactions.builder()
                .chatId("123456789")
                .actorChatId(987654321L)
                .build();

        assertDoesNotThrow(method::validate);
        assertEquals(987654321L, method.getActorChatId());
    }
}
