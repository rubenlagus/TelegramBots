package org.telegram.telegrambots.meta.api.methods.menubutton;

import org.junit.jupiter.api.Test;
import org.telegram.telegrambots.meta.api.objects.menubutton.MenuButtonCommands;
import org.telegram.telegrambots.meta.api.objects.menubutton.MenuButtonDefault;
import org.telegram.telegrambots.meta.api.objects.menubutton.MenuButtonWebApp;
import org.telegram.telegrambots.meta.api.objects.webapp.WebAppInfo;
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
public class SetChatMenuButtonTest {

    @Test
    public void testGetChatMenuButtonAsDefault() {
        SetChatMenuButton setChatMenuButton = SetChatMenuButton
                .builder()
                .menuButton(MenuButtonDefault.builder().build())
                .build();
        assertEquals("setChatMenuButton", setChatMenuButton.getMethod());
        assertDoesNotThrow(setChatMenuButton::validate);
    }

    @Test
    public void testGetChatMenuButtonAsCommands() {
        SetChatMenuButton setChatMenuButton = SetChatMenuButton
                .builder()
                .chatId(123456L)
                .menuButton(MenuButtonCommands.builder().build())
                .build();
        assertEquals("setChatMenuButton", setChatMenuButton.getMethod());
        assertDoesNotThrow(setChatMenuButton::validate);
    }

    @Test
    public void testGetChatMenuButtonAsWebApp() {
        SetChatMenuButton setChatMenuButton = SetChatMenuButton
                .builder()
                .chatId("123456")
                .menuButton(MenuButtonWebApp
                        .builder()
                        .text("Web app text")
                        .webAppInfo(WebAppInfo.builder().url("My url").build())
                        .build())
                .build();
        assertEquals("setChatMenuButton", setChatMenuButton.getMethod());
        assertDoesNotThrow(setChatMenuButton::validate);
    }

    @Test
    public void testGetChatMenuButtonAsWebAppWithEmptyText() {
        SetChatMenuButton setChatMenuButton = SetChatMenuButton
                .builder()
                .chatId("123456")
                .menuButton(MenuButtonWebApp
                        .builder()
                        .text("")
                        .webAppInfo(WebAppInfo.builder().url("My url").build())
                        .build())
                .build();
        assertEquals("setChatMenuButton", setChatMenuButton.getMethod());
        Throwable thrown = assertThrows(TelegramApiValidationException.class, setChatMenuButton::validate);
        assertEquals("Text can't be empty", thrown.getMessage());
    }

    @Test
    public void testGetChatMenuButtonAsWebAppWithEmptyWebUrl() {
        SetChatMenuButton setChatMenuButton = SetChatMenuButton
                .builder()
                .chatId("123456")
                .menuButton(MenuButtonWebApp
                        .builder()
                        .text("Text")
                        .webAppInfo(WebAppInfo.builder().url("").build())
                        .build())
                .build();
        assertEquals("setChatMenuButton", setChatMenuButton.getMethod());
        Throwable thrown = assertThrows(TelegramApiValidationException.class, setChatMenuButton::validate);
        assertEquals("Url can't be empty", thrown.getMessage());
    }

    @Test
    public void testGetChatMenuButtonForChat() {
        SetChatMenuButton setChatMenuButton = SetChatMenuButton
                .builder()
                .chatId("123456")
                .menuButton(MenuButtonDefault.builder().build())
                .build();
        assertEquals("setChatMenuButton", setChatMenuButton.getMethod());
        assertDoesNotThrow(setChatMenuButton::validate);
    }

    @Test
    public void testGetChatMenuButtonDeserializeValidResponse() {
        String responseText = "{\"ok\":true,\"result\": true}";

        SetChatMenuButton setChatMenuButton = SetChatMenuButton
                .builder()
                .chatId("12345")
                .build();

        try {
            boolean result = setChatMenuButton.deserializeResponse(responseText);
            assertTrue(result);
        } catch (TelegramApiRequestException e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void testGetChatMenuButtonErrorResponse() {
        String responseText = "{\"ok\":false,\"error_code\": 404,\"description\": \"Error message\"}";

        SetChatMenuButton setChatMenuButton = SetChatMenuButton
                .builder()
                .build();

        try {
            setChatMenuButton.deserializeResponse(responseText);
            fail();
        } catch (TelegramApiRequestException e) {
            assertEquals(404, e.getErrorCode());
            assertEquals("Error message", e.getApiResponse());
        }
    }

    @Test
    public void testGetChatMenuButtonDeserializeWithWrongObject() {
        String responseText = "{\"ok\":false\"error_code\": 404,\"description\": \"Error message\"}";

        SetChatMenuButton setChatMenuButton = SetChatMenuButton
                .builder()
                .chatId("12345")
                .build();

        try {
            setChatMenuButton.deserializeResponse(responseText);
            fail();
        } catch (TelegramApiRequestException e) {
            assertEquals("Unable to deserialize response", e.getMessage());
        }
    }
}
