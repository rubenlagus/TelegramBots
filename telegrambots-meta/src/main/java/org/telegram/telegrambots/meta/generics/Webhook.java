package org.telegram.telegrambots.meta.generics;

import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

/**
 * @author Ruben Bermudez
 * @version 1.0
 * Webhook interface
 */
public interface Webhook {
    void startServer() throws TelegramApiException;
    void registerWebhook(WebhookBot callback);
    void setInternalUrl(String internalUrl);
    void setKeyStore(String keyStore, String keyStorePassword) throws TelegramApiException;
}
