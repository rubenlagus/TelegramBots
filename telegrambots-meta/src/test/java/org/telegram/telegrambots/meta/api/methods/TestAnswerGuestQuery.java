package org.telegram.telegrambots.meta.api.methods;

import org.junit.jupiter.api.Test;
import org.telegram.telegrambots.meta.api.objects.inlinequery.inputmessagecontent.InputTextMessageContent;
import org.telegram.telegrambots.meta.api.objects.inlinequery.result.InlineQueryResultArticle;
import org.telegram.telegrambots.meta.exceptions.TelegramApiValidationException;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class TestAnswerGuestQuery {

    @Test
    public void testAnswerGuestQueryGetPath() {
        AnswerGuestQuery answerGuestQuery = AnswerGuestQuery.builder()
                .guestQueryId("query_id")
                .result(InlineQueryResultArticle.builder()
                        .id("result_id")
                        .title("title")
                        .inputMessageContent(InputTextMessageContent.builder().messageText("text").build())
                        .build())
                .build();

        assertEquals("answerGuestQuery", answerGuestQuery.getMethod());
    }

    @Test
    public void testAnswerGuestQueryValidation() {
        AnswerGuestQuery answerGuestQuery = AnswerGuestQuery.builder()
                .guestQueryId("query_id")
                .result(InlineQueryResultArticle.builder()
                        .id("result_id")
                        .title("title")
                        .inputMessageContent(InputTextMessageContent.builder().messageText("text").build())
                        .build())
                .build();

        assertDoesNotThrow(answerGuestQuery::validate);
    }

    @Test
    public void testAnswerGuestQueryValidationMissingQueryId() {
        assertThrows(NullPointerException.class, () -> AnswerGuestQuery.builder()
                .result(InlineQueryResultArticle.builder()
                        .id("result_id")
                        .title("title")
                        .inputMessageContent(InputTextMessageContent.builder().messageText("text").build())
                        .build())
                .build());
    }

    @Test
    public void testAnswerGuestQueryValidationEmptyQueryId() {
        AnswerGuestQuery answerGuestQuery = AnswerGuestQuery.builder()
                .guestQueryId("")
                .result(InlineQueryResultArticle.builder()
                        .id("result_id")
                        .title("title")
                        .inputMessageContent(InputTextMessageContent.builder().messageText("text").build())
                        .build())
                .build();

        assertThrows(TelegramApiValidationException.class, answerGuestQuery::validate);
    }

    @Test
    public void testAnswerGuestQueryValidationMissingResult() {
        assertThrows(NullPointerException.class, () -> AnswerGuestQuery.builder()
                .guestQueryId("query_id")
                .build());
    }
}
