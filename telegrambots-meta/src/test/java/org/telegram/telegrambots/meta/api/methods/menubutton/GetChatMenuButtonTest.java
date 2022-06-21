package org.telegram.telegrambots.meta.api.methods.menubutton;

import org.junit.jupiter.api.Test;
import org.telegram.telegrambots.meta.api.objects.menubutton.MenuButton;
import org.telegram.telegrambots.meta.api.objects.menubutton.MenuButtonDefault;
import org.telegram.telegrambots.meta.exceptions.TelegramApiRequestException;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

/**
 * @author Ruben Bermudez
 * @version 6.0
 */
public class GetChatMenuButtonTest {

    @Test
    public void testGetChatMenuButtonAsDefault() {
        GetChatMenuButton getChatMenuButton = GetChatMenuButton
                .builder()
                .build();
        assertEquals("getChatMenuButton", getChatMenuButton.getMethod());
        assertDoesNotThrow(getChatMenuButton::validate);
    }

    @Test
    public void testGetChatMenuButtonForChat() {
        GetChatMenuButton getChatMenuButton = GetChatMenuButton
                .builder()
                .chatId(123456L)
                .build();
        assertEquals("getChatMenuButton", getChatMenuButton.getMethod());
        assertDoesNotThrow(getChatMenuButton::validate);
    }

    @Test
    public void testGetChatMenuButtonDeserializeValidResponse() {
        String responseText = "{\"ok\":true,\"result\":{\"type\": \"default\"}}";

        GetChatMenuButton getChatMenuButton = GetChatMenuButton
                .builder()
                .chatId("12345")
                .build();

        try {
            MenuButton result = getChatMenuButton.deserializeResponse(responseText);
            assertNotNull(result);
            assertTrue(result instanceof MenuButtonDefault);
        } catch (TelegramApiRequestException e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void testGetChatMenuButtonErrorResponse() {
        String responseText = "{\"ok\":false,\"error_code\": 404,\"description\": \"Error message\"}";

        GetChatMenuButton getChatMenuButton = GetChatMenuButton
                .builder()
                .build();

        try {
            getChatMenuButton.deserializeResponse(responseText);
            fail();
        } catch (TelegramApiRequestException e) {
            assertEquals(404, e.getErrorCode());
            assertEquals("Error message", e.getApiResponse());
        }
    }

    @Test
    public void testGetChatMenuButtonDeserializeWithWrongObject() {
        String responseText = "{\"ok\":false\"error_code\": 404,\"description\": \"Error message\"}";

        GetChatMenuButton getChatMenuButton = GetChatMenuButton
                .builder()
                .chatId("12345")
                .build();

        try {
            getChatMenuButton.deserializeResponse(responseText);
            fail();
        } catch (TelegramApiRequestException e) {
            assertEquals("Unable to deserialize response", e.getMessage());
        }
    }
}
