package org.telegram.telegrambots.meta.api.methods.webapp;

import org.junit.jupiter.api.Test;
import org.telegram.telegrambots.meta.api.objects.inlinequery.inputmessagecontent.InputTextMessageContent;
import org.telegram.telegrambots.meta.api.objects.inlinequery.result.InlineQueryResultArticle;
import org.telegram.telegrambots.meta.api.objects.webapp.SentWebAppMessage;
import org.telegram.telegrambots.meta.exceptions.TelegramApiRequestException;
import org.telegram.telegrambots.meta.exceptions.TelegramApiValidationException;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;

/**
 * @author Ruben Bermudez
 * @version 6.0
 */
public class AnswerWebAppQueryTest {

    @Test
    public void testAnswerWebAppQueryWithAllSet() {
        AnswerWebAppQuery answerWebAppQuery = AnswerWebAppQuery
                .builder()
                .webAppQueryId("123456789")
                .queryResult(InlineQueryResultArticle
                        .builder()
                        .id("MyId")
                        .title("Text")
                        .inputMessageContent(InputTextMessageContent
                                .builder()
                                .messageText("My own text")
                                .build())
                        .build())
                .build();
        assertEquals("answerWebAppQuery", answerWebAppQuery.getMethod());
        assertDoesNotThrow(answerWebAppQuery::validate);
    }

    @Test
    public void testAnswerWebAppQueryWithEmptyId() {
        AnswerWebAppQuery answerWebAppQuery = AnswerWebAppQuery
                .builder()
                .webAppQueryId("")
                .queryResult(InlineQueryResultArticle
                        .builder()
                        .id("MyId")
                        .title("Text")
                        .inputMessageContent(InputTextMessageContent
                                .builder()
                                .messageText("My own text")
                                .build())
                        .build())
                .build();
        assertEquals("answerWebAppQuery", answerWebAppQuery.getMethod());
        Throwable thrown = assertThrows(TelegramApiValidationException.class, answerWebAppQuery::validate);
        assertEquals("WebAppQueryId can't be empty", thrown.getMessage());
    }

    @Test
    public void testAnswerWebAppQueryWithInvalidResult() {
        AnswerWebAppQuery answerWebAppQuery = AnswerWebAppQuery
                .builder()
                .webAppQueryId("123456789")
                .queryResult(InlineQueryResultArticle
                        .builder()
                        .id("")
                        .title("Text")
                        .inputMessageContent(InputTextMessageContent
                                .builder()
                                .messageText("My own text")
                                .build())
                        .build())
                .build();
        assertEquals("answerWebAppQuery", answerWebAppQuery.getMethod());
        Throwable thrown = assertThrows(TelegramApiValidationException.class, answerWebAppQuery::validate);
        assertEquals("ID parameter can't be empty", thrown.getMessage());
    }


    @Test
    public void testAnswerWebAppQueryDeserializeValidResponse() {
        String responseText = "{\"ok\":true,\"result\": { \"inline_message_id\": \"123456\" } }";

        AnswerWebAppQuery answerWebAppQuery = AnswerWebAppQuery
                .builder()
                .webAppQueryId("123456789")
                .queryResult(InlineQueryResultArticle
                        .builder()
                        .id("")
                        .title("Text")
                        .inputMessageContent(InputTextMessageContent
                                .builder()
                                .messageText("My own text")
                                .build())
                        .build())
                .build();

        try {
            SentWebAppMessage result = answerWebAppQuery.deserializeResponse(responseText);
            assertNotNull(result);
            assertEquals("123456", result.getInlineMessageId());
        } catch (TelegramApiRequestException e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void testAnswerWebAppQueryDeserializeErrorResponse() {
        String responseText = "{\"ok\":false,\"error_code\": 404,\"description\": \"Error message\"}";

        AnswerWebAppQuery answerWebAppQuery = AnswerWebAppQuery
                .builder()
                .webAppQueryId("123456789")
                .queryResult(InlineQueryResultArticle
                        .builder()
                        .id("")
                        .title("Text")
                        .inputMessageContent(InputTextMessageContent
                                .builder()
                                .messageText("My own text")
                                .build())
                        .build())
                .build();

        try {
            answerWebAppQuery.deserializeResponse(responseText);
            fail();
        } catch (TelegramApiRequestException e) {
            assertEquals(404, e.getErrorCode());
            assertEquals("Error message", e.getApiResponse());
        }
    }

    @Test
    public void testAnswerWebAppQueryDeserializeWithWrongObject() {
        String responseText = "{\"ok\":false\"error_code\": 404,\"description\": \"Error message\"}";

        AnswerWebAppQuery answerWebAppQuery = AnswerWebAppQuery
                .builder()
                .webAppQueryId("123456789")
                .queryResult(InlineQueryResultArticle
                        .builder()
                        .id("")
                        .title("Text")
                        .inputMessageContent(InputTextMessageContent
                                .builder()
                                .messageText("My own text")
                                .build())
                        .build())
                .build();

        try {
            answerWebAppQuery.deserializeResponse(responseText);
            fail();
        } catch (TelegramApiRequestException e) {
            assertEquals("Unable to deserialize response", e.getMessage());
        }
    }
}
