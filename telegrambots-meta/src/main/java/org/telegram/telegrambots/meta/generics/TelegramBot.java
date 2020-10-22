package org.telegram.telegrambots.meta.generics;

/**
 * Main interface for telegram bots.
 */
public interface TelegramBot {

    /**
     * Return username of this bot
     */
    String getBotUsername();

    /**
     * Return bot token to access Telegram API
     */
    String getBotToken();

    /**
     * Is called when bot gets registered
     */
    default void onRegister() {
    }

}
