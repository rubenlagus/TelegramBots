package org.telegram.telegrambots.generics;

import org.telegram.telegrambots.exceptions.TelegramApiRequestException;

/**
 * @author Ruben Bermudez
 * @version 1.0
 * Webhook interface
 */
public interface Webhook {
    void startServer() throws TelegramApiRequestException;
    void registerWebhook(WebhookBot callback);
    void setInternalUrl(String internalUrl);
    void setKeyStore(String keyStore, String keyStorePassword) throws TelegramApiRequestException;
}
