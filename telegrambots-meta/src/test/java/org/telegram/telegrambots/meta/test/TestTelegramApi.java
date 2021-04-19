package org.telegram.telegrambots.meta.test;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.updates.SetWebhook;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.generics.BotSession;
import org.telegram.telegrambots.meta.generics.Webhook;
import org.telegram.telegrambots.meta.generics.WebhookBot;

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
    void testTelegramApiMustBeInitializableForLongPolling() {
        try {
            new TelegramBotsApi(BotSession.class);
        } catch (TelegramApiException e) {
            fail();
        }
    }

    @Test
    void testTelegramApiMustBeInitializableForWebhook() {
        try {
            new TelegramBotsApi(BotSession.class, webhook);
        } catch (TelegramApiException e) {
            fail();
        }
    }

    @Test
    void testTelegramApiMustBeThrowIfNotCreatedForWebhook() {
        try {
            TelegramBotsApi telegramBotsApi = new TelegramBotsApi(BotSession.class, null);
            telegramBotsApi.registerBot(new WebhookBot() {
                @Override
                public BotApiMethod onWebhookUpdateReceived(Update update) {
                    return null;
                }

                @Override
                public void setWebhook(SetWebhook setWebhook) throws TelegramApiException {

                }

                @Override
                public String getBotPath() {
                    return null;
                }

                @Override
                public String getBotUsername() {
                    return null;
                }

                @Override
                public String getBotToken() {
                    return null;
                }
            }, new SetWebhook());
            fail();
        } catch (TelegramApiException e) {
            // Ignore
        }
    }
}
