package org.telegram.telegrambots.updatesreceivers;

import lombok.extern.slf4j.Slf4j;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.exceptions.TelegramApiValidationException;
import org.telegram.telegrambots.meta.generics.Webhook;
import org.telegram.telegrambots.meta.generics.WebhookBot;

import java.util.NoSuchElementException;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
public class ServerlessWebhook implements Webhook {

    private final ConcurrentHashMap<String, WebhookBot> callbacks = new ConcurrentHashMap<>();

    public BotApiMethod<?> updateReceived(String botPath, Update update) throws TelegramApiValidationException {
        if (callbacks.containsKey(botPath)) {
            try {
                BotApiMethod<?> response = callbacks.get(botPath).onWebhookUpdateReceived(update);
                if (response != null) {
                    response.validate();
                }
                return response;
            } catch (TelegramApiValidationException e) {
                log.error(e.getLocalizedMessage(), e);
                throw e;
            }
        } else {
            throw new NoSuchElementException(String.format("Callback '%s' not exist", botPath));
        }
    }

    @Override
    public void startServer() throws TelegramApiException {
        // Do nothing, because there is no abstraction for webhook without server
    }

    @Override
    public void registerWebhook(WebhookBot callback) {
        callbacks.putIfAbsent(callback.getBotPath(), callback);
    }

    @Override
    public void setInternalUrl(String internalUrl) {
        throw new UnsupportedOperationException("Not implemented for Serverless Webhook");
    }

    @Override
    public void setKeyStore(String keyStore, String keyStorePassword) throws TelegramApiException {
        throw new UnsupportedOperationException("Not implemented for Serverless Webhook");
    }
}
