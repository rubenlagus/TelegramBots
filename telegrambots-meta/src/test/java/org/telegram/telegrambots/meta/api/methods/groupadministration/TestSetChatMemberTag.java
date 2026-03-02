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
 * @version 9.5
 */
public class TestSetChatMemberTag {

    @Test
    public void testSetChatMemberTagWithStringChatId() {
        SetChatMemberTag setChatMemberTag = SetChatMemberTag
                .builder()
                .chatId("@testgroup")
                .userId(123456L)
                .tag("VIP")
                .build();
        assertEquals("setChatMemberTag", setChatMemberTag.getMethod());
        assertDoesNotThrow(setChatMemberTag::validate);
    }

    @Test
    public void testSetChatMemberTagWithLongChatId() {
        SetChatMemberTag setChatMemberTag = SetChatMemberTag
                .builder()
                .chatId(-1001234567890L)
                .userId(123456L)
                .tag("Moderator")
                .build();
        assertEquals("setChatMemberTag", setChatMemberTag.getMethod());
        assertDoesNotThrow(setChatMemberTag::validate);
    }

    @Test
    public void testSetChatMemberTagWithoutTag() {
        SetChatMemberTag setChatMemberTag = SetChatMemberTag
                .builder()
                .chatId("@testgroup")
                .userId(123456L)
                .build();
        assertEquals("setChatMemberTag", setChatMemberTag.getMethod());
        assertDoesNotThrow(setChatMemberTag::validate);
    }

    @Test
    public void testSetChatMemberTagWithEmptyChatId() {
        SetChatMemberTag setChatMemberTag = SetChatMemberTag
                .builder()
                .chatId("")
                .userId(123456L)
                .tag("VIP")
                .build();
        Throwable thrown = assertThrows(TelegramApiValidationException.class, setChatMemberTag::validate);
        assertEquals("chatId can't be empty", thrown.getMessage());
    }

    @Test
    public void testSetChatMemberTagDeserializeValidResponse() {
        String responseText = "{\"ok\":true,\"result\": true}";

        SetChatMemberTag setChatMemberTag = SetChatMemberTag
                .builder()
                .chatId("@testgroup")
                .userId(123456L)
                .tag("VIP")
                .build();

        try {
            boolean result = setChatMemberTag.deserializeResponse(responseText);
            assertTrue(result);
        } catch (TelegramApiRequestException e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void testSetChatMemberTagErrorResponse() {
        String responseText = "{\"ok\":false,\"error_code\": 400,\"description\": \"Bad Request: user not found\"}";

        SetChatMemberTag setChatMemberTag = SetChatMemberTag
                .builder()
                .chatId("@testgroup")
                .userId(123456L)
                .build();

        try {
            setChatMemberTag.deserializeResponse(responseText);
            fail();
        } catch (TelegramApiRequestException e) {
            assertEquals(400, e.getErrorCode());
            assertEquals("Bad Request: user not found", e.getApiResponse());
        }
    }

    @Test
    public void testSetChatMemberTagDeserializeWithWrongObject() {
        String responseText = "{\"ok\":false\"error_code\": 400,\"description\": \"Bad Request\"}";

        SetChatMemberTag setChatMemberTag = SetChatMemberTag
                .builder()
                .chatId("@testgroup")
                .userId(123456L)
                .build();

        try {
            setChatMemberTag.deserializeResponse(responseText);
            fail();
        } catch (TelegramApiRequestException e) {
            assertEquals("Unable to deserialize response", e.getMessage());
        }
    }
}
