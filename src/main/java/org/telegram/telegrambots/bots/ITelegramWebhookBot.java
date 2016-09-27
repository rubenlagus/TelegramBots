package org.telegram.telegrambots.bots;

import org.telegram.telegrambots.api.methods.BotApiMethod;
import org.telegram.telegrambots.api.objects.Update;

/**
 * @author Ruben Bermudez
 * @version 1.0
 * @brief Callback to handle updates.
 * @date 20 of June of 2015
 */
public interface ITelegramWebhookBot {
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
     * Gets in the url for the webhook
     * @return path in the url
     */
    String getBotPath();
}
