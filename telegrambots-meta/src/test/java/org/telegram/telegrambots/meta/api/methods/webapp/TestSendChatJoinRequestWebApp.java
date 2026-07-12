package org.telegram.telegrambots.meta.api.methods.webapp;

import org.junit.jupiter.api.Test;
import org.telegram.telegrambots.meta.exceptions.TelegramApiValidationException;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * @author Ruben Bermudez
 * @version 10.1
 */
public class TestSendChatJoinRequestWebApp {

    @Test
    public void testSendChatJoinRequestWebAppGetPath() {
        SendChatJoinRequestWebApp method = SendChatJoinRequestWebApp.builder()
                .chatJoinRequestQueryId("query123")
                .webAppUrl("https://example.com/app")
                .build();

        assertEquals("sendChatJoinRequestWebApp", method.getMethod());
    }

    @Test
    public void testSendChatJoinRequestWebAppValidation() {
        SendChatJoinRequestWebApp method = SendChatJoinRequestWebApp.builder()
                .chatJoinRequestQueryId("query123")
                .webAppUrl("https://example.com/app")
                .build();

        assertDoesNotThrow(method::validate);
    }

    @Test
    public void testSendChatJoinRequestWebAppValidationEmptyQueryId() {
        SendChatJoinRequestWebApp method = SendChatJoinRequestWebApp.builder()
                .chatJoinRequestQueryId("")
                .webAppUrl("https://example.com/app")
                .build();

        assertThrows(TelegramApiValidationException.class, method::validate);
    }

    @Test
    public void testSendChatJoinRequestWebAppValidationEmptyWebAppUrl() {
        SendChatJoinRequestWebApp method = SendChatJoinRequestWebApp.builder()
                .chatJoinRequestQueryId("query123")
                .webAppUrl("")
                .build();

        assertThrows(TelegramApiValidationException.class, method::validate);
    }

    @Test
    public void testSendChatJoinRequestWebAppFields() {
        SendChatJoinRequestWebApp method = SendChatJoinRequestWebApp.builder()
                .chatJoinRequestQueryId("query123")
                .webAppUrl("https://example.com/app")
                .build();

        assertEquals("query123", method.getChatJoinRequestQueryId());
        assertEquals("https://example.com/app", method.getWebAppUrl());
    }
}
