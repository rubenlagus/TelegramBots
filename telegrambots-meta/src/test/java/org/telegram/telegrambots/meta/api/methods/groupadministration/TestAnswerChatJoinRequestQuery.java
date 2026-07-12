package org.telegram.telegrambots.meta.api.methods.groupadministration;

import org.junit.jupiter.api.Test;
import org.telegram.telegrambots.meta.exceptions.TelegramApiValidationException;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * @author Ruben Bermudez
 * @version 10.1
 */
public class TestAnswerChatJoinRequestQuery {

    @Test
    public void testAnswerChatJoinRequestQueryGetPath() {
        AnswerChatJoinRequestQuery method = AnswerChatJoinRequestQuery.builder()
                .chatJoinRequestQueryId("query123")
                .result("approve")
                .build();

        assertEquals("answerChatJoinRequestQuery", method.getMethod());
    }

    @Test
    public void testAnswerChatJoinRequestQueryValidationApprove() {
        AnswerChatJoinRequestQuery method = AnswerChatJoinRequestQuery.builder()
                .chatJoinRequestQueryId("query123")
                .result("approve")
                .build();

        assertDoesNotThrow(method::validate);
    }

    @Test
    public void testAnswerChatJoinRequestQueryValidationDecline() {
        AnswerChatJoinRequestQuery method = AnswerChatJoinRequestQuery.builder()
                .chatJoinRequestQueryId("query123")
                .result("decline")
                .build();

        assertDoesNotThrow(method::validate);
    }

    @Test
    public void testAnswerChatJoinRequestQueryValidationQueue() {
        AnswerChatJoinRequestQuery method = AnswerChatJoinRequestQuery.builder()
                .chatJoinRequestQueryId("query123")
                .result("queue")
                .build();

        assertDoesNotThrow(method::validate);
    }

    @Test
    public void testAnswerChatJoinRequestQueryValidationEmptyQueryId() {
        AnswerChatJoinRequestQuery method = AnswerChatJoinRequestQuery.builder()
                .chatJoinRequestQueryId("")
                .result("approve")
                .build();

        assertThrows(TelegramApiValidationException.class, method::validate);
    }

    @Test
    public void testAnswerChatJoinRequestQueryValidationEmptyResult() {
        AnswerChatJoinRequestQuery method = AnswerChatJoinRequestQuery.builder()
                .chatJoinRequestQueryId("query123")
                .result("")
                .build();

        assertThrows(TelegramApiValidationException.class, method::validate);
    }

    @Test
    public void testAnswerChatJoinRequestQueryValidationInvalidResult() {
        AnswerChatJoinRequestQuery method = AnswerChatJoinRequestQuery.builder()
                .chatJoinRequestQueryId("query123")
                .result("banana")
                .build();

        assertThrows(TelegramApiValidationException.class, method::validate);
    }

    @Test
    public void testAnswerChatJoinRequestQueryFields() {
        AnswerChatJoinRequestQuery method = AnswerChatJoinRequestQuery.builder()
                .chatJoinRequestQueryId("query123")
                .result("approve")
                .build();

        assertEquals("query123", method.getChatJoinRequestQueryId());
        assertEquals("approve", method.getResult());
    }

    @Test
    public void testAnswerChatJoinRequestQueryNullQueryIdThrowsNPE() {
        assertThrows(NullPointerException.class, () ->
            AnswerChatJoinRequestQuery.builder()
                .chatJoinRequestQueryId(null)
                .result("approve")
                .build());
    }

    @Test
    public void testAnswerChatJoinRequestQueryNullResultThrowsNPE() {
        assertThrows(NullPointerException.class, () ->
            AnswerChatJoinRequestQuery.builder()
                .chatJoinRequestQueryId("query123")
                .result(null)
                .build());
    }
}
