package org.telegram.telegrambots.bots;

/**
 * @author Ruben Bermudez
 * @version 1.0
 * @brief Base abstract class for a bot that will get updates using
 * <a href="https://core.telegram.org/bots/api#getupdates">long-polling</a> method
 * @date 14 of January of 2016
 */
public abstract class TelegramLongPollingBot extends AbsSender implements ITelegramLongPollingBot {
    public TelegramLongPollingBot() {
        this(new BotOptions());
    }

    public TelegramLongPollingBot(BotOptions options) {
        super(options);
    }
}
