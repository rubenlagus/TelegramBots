package org.telegram.telegrambots.meta.test;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.generics.BotSession;
import org.telegram.telegrambots.meta.generics.Webhook;

import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.Mockito.mock;

/**
 * @author Ruben Bermudez
 * @version 1.0
 */
class TestTelegramApi {

    private Webhook webhook;

    @BeforeEach
    public void setUp() {
        webhook = mock(Webhook.class);
    }

    @Test
    void TestTelegramApiMustBeInitializableForLongPolling() {
        try {
            new TelegramBotsApi(BotSession.class);
        } catch (TelegramApiException e) {
            fail();
        }
    }

    @Test
    void TestTelegramApiMustBeInitializableForWebhook() {
        try {
            new TelegramBotsApi(BotSession.class, webhook);
        } catch (TelegramApiException e) {
            fail();
        }
    }
}
