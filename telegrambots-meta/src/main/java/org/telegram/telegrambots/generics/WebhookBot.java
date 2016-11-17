package org.telegram.telegrambots.generics;

import org.telegram.telegrambots.api.methods.BotApiMethod;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.exceptions.TelegramApiRequestException;

/**
 * @author Ruben Bermudez
 * @version 1.0
 * @brief Callback to handle updates.
 * @date 20 of June of 2015
 */
public interface WebhookBot {
    /**
     * This method is called when receiving updates via webhook
     * @param update Update received
     */
    BotApiMethod onWebhookUpdateReceived(Update update);

    /**
     * Gets bot username of this bot
     * @return Bot username
     */
    String getBotUsername();

    /**
     * Gets bot token to access Telegram API
     * @return Bot token
     */
    String getBotToken();

    /**
     * Execute setWebhook method to set up the url of the webhook
     * @param url Url for the webhook
     * @param publicCertificatePath Path to the public key certificate of the webhook
     * @throws TelegramApiRequestException In case of error executing the request
     */
    void setWebhook(String url, String publicCertificatePath) throws TelegramApiRequestException;

    /**
     * Gets in the url for the webhook
     * @return path in the url
     */
    String getBotPath();
}
