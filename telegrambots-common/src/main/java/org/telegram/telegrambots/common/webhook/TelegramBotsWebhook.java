package org.telegram.telegrambots.common.webhook;

import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

/**
 * @author Ruben Bermudez
 * @version 1.0
 */
public interface TelegramBotsWebhook extends AutoCloseable {
    /**
     * Use this method to register a new bot in the webhook server
     * @param telegramWebhookBot New Bot to register
     *
     * @throws TelegramApiException if any issue registering the bot
     */
    void registerBot(TelegramWebhookBot telegramWebhookBot) throws TelegramApiException;

    /**
     * Use this method to unregister a bot in the webhook server
     * @param telegramWebhookBot Bot to unregister
     *
     * @throws TelegramApiException if any issue unregistering the bot
     */
    void unregisterBot(TelegramWebhookBot telegramWebhookBot) throws TelegramApiException;

    /**
     * Checks if the webhook server is running
     * @return True if the bot is running, False otherwise
     */
    boolean isRunning();

    /**
     * Starts the webhook server
     *
     * @throws TelegramApiException if any issue starting the server (it can be an issue starting one of the bots registered)
     */
    void start() throws TelegramApiException;

    /**
     * Stop the webhook server
     *
     * @throws TelegramApiException if any issue stopping the server (it can be an issue stopping one of the bots registered)
     */
    void stop() throws TelegramApiException;
}
