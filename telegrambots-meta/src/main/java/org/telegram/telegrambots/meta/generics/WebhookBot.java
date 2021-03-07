package org.telegram.telegrambots.meta.generics;

import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.updates.SetWebhook;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.exceptions.TelegramApiRequestException;

/**
 * @author Ruben Bermudez
 * @version 1.0
 * Callback to handle updates.
 */
public interface WebhookBot extends TelegramBot {
    /**
     * This method is called when receiving updates via webhook
     * @param update Update received
     */
    BotApiMethod<?> onWebhookUpdateReceived(Update update);

    /**
     * Execute setWebhook method to set up the url of the webhook
     * @throws TelegramApiRequestException In case of error executing the request
     */
    void setWebhook(SetWebhook setWebhook) throws TelegramApiException;

    /**
     * Gets in the url for the webhook
     * @return path in the url
     */
    String getBotPath();
}
