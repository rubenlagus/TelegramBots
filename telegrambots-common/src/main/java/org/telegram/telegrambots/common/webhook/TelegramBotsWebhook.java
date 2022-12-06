package org.telegram.telegrambots.common.webhook;

/**
 * @author Ruben Bermudez
 * @version 1.0
 */
public interface TelegramBotsWebhook extends AutoCloseable {
    /**
     * Use this method to register a new bot in the webhook server
     * @param telegramWebhookBot New Bot to register
     */
    void registerBot(TelegramWebhookBot telegramWebhookBot);

    /**
     * Use this method to unregister a bot in the webhook server
     * @param telegramWebhookBot Bot to unregister
     */
    void unregisterBot(TelegramWebhookBot telegramWebhookBot);

    /**
     * Checks if the webhook server is running
     * @return
     */
    boolean isRunning();

    /**
     * Starts the webhook server
     */
    void start();

    /**
     * Stop the webhook server
     */
    void stop();
}
