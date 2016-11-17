package org.telegram.telegrambots.fakes;

import org.telegram.telegrambots.exceptions.TelegramApiRequestException;
import org.telegram.telegrambots.generics.Webhook;
import org.telegram.telegrambots.generics.WebhookBot;

/**
 * @author Ruben Bermudez
 * @version 1.0
 * @brief TODO
 * @date 29 of October of 2016
 */
public class FakeWebhook implements Webhook {
    private String internalUrl;
    private String keyStore;
    private String keyStorePassword;


    @Override
    public void startServer() throws TelegramApiRequestException {

    }

    @Override
    public void registerWebhook(WebhookBot callback) {

    }

    @Override
    public void setInternalUrl(String internalUrl) {
        this.internalUrl = internalUrl;
    }

    @Override
    public void setKeyStore(String keyStore, String keyStorePassword) throws TelegramApiRequestException {
        this.keyStore = keyStore;
        this.keyStorePassword = keyStorePassword;
    }

    public String getInternalUrl() {
        return internalUrl;
    }

    public String getKeyStore() {
        return keyStore;
    }

    public String getKeyStorePassword() {
        return keyStorePassword;
    }
}
