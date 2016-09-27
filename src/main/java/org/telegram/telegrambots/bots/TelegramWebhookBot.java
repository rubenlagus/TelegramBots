package org.telegram.telegrambots.bots;

/**
 * @author Ruben Bermudez
 * @version 1.0
 * @brief Base abstract class for a bot that will receive updates using a
 * <a href="https://core.telegram.org/bots/api#setwebhook">webhook</a>
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
