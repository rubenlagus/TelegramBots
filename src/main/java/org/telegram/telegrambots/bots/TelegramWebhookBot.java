package org.telegram.telegrambots.bots;

/**
 * @author Ruben Bermudez
 * @version 1.0
 * @brief TODO
 * @date 14 of January of 2016
 */
public abstract class TelegramWebhookBot extends AbsSender implements ITelegramWebhookBot {
    public TelegramWebhookBot() {
        this(new BotOptions());
    }

    public TelegramWebhookBot(BotOptions options) {
        super(options);
    }
}
