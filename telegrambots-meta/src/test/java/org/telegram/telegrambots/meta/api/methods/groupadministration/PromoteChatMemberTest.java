package org.telegram.telegrambots.meta.api.methods.groupadministration;

import org.junit.jupiter.api.Test;
import org.telegram.telegrambots.meta.exceptions.TelegramApiRequestException;
import org.telegram.telegrambots.meta.exceptions.TelegramApiValidationException;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

/**
 * @author Ruben Bermudez
 * @version 6.0
 */
public class PromoteChatMemberTest {
    @Test
    public void testPromoteChatMemberWithAllSet() {
        PromoteChatMember promoteChatMember = PromoteChatMember
                .builder()
                .chatId(12345L)
                .userId(12345L)
                .build();
        assertEquals("promoteChatMember", promoteChatMember.getMethod());
        assertDoesNotThrow(promoteChatMember::validate);
    }

    @Test
    public void testPromoteChatMemberWithEmptyChatId() {
        PromoteChatMember promoteChatMember = PromoteChatMember
                .builder()
                .chatId("")
                .userId(12345L)
                .build();
        assertEquals("promoteChatMember", promoteChatMember.getMethod());
        Throwable thrown = assertThrows(TelegramApiValidationException.class, promoteChatMember::validate);
        assertEquals("ChatId can't be empty", thrown.getMessage());
    }

    @Test
    public void testPromoteChatMemberWithEmptyUserId() {
        PromoteChatMember promoteChatMember = PromoteChatMember
                .builder()
                .chatId("12345")
                .userId(0L)
                .build();
        assertEquals("promoteChatMember", promoteChatMember.getMethod());
        Throwable thrown = assertThrows(TelegramApiValidationException.class, promoteChatMember::validate);
        assertEquals("UserId can't be empty", thrown.getMessage());
    }

    @Test
    public void testPromoteChatMemberDeserializeValidResponse() {
        String responseText = "{\"ok\":true,\"result\": true}";

        PromoteChatMember promoteChatMember = PromoteChatMember
                .builder()
                .chatId("12345")
                .userId(123456L)
                .build();

        try {
            boolean result = promoteChatMember.deserializeResponse(responseText);
            assertTrue(result);
        } catch (TelegramApiRequestException e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void testPromoteChatMemberDeserializeErrorResponse() {
        String responseText = "{\"ok\":false,\"error_code\": 404,\"description\": \"Error message\"}";

        PromoteChatMember promoteChatMember = PromoteChatMember
                .builder()
                .chatId("12345")
                .userId(123456L)
                .build();

        try {
            promoteChatMember.deserializeResponse(responseText);
            fail();
        } catch (TelegramApiRequestException e) {
            assertEquals(404, e.getErrorCode());
            assertEquals("Error message", e.getApiResponse());
        }
    }

    @Test
    public void testPromoteChatMemberDeserializeWithWrongObject() {
        String responseText = "{\"ok\":false\"error_code\": 404,\"description\": \"Error message\"}";

        PromoteChatMember promoteChatMember = PromoteChatMember
                .builder()
                .chatId("12345")
                .userId(123456L)
                .build();

        try {
            promoteChatMember.deserializeResponse(responseText);
            fail();
        } catch (TelegramApiRequestException e) {
            assertEquals("Unable to deserialize response", e.getMessage());
        }
    }
}
