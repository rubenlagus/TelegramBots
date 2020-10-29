package org.telegram.telegrambots.meta.generics;

import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiRequestException;

import java.util.List;

/**
 * @author Ruben Bermudez
 * @version 1.0
 * @brief Callback to handle updates.
 * @date 20 of June of 2015
 */
public interface LongPollingBot extends TelegramBot {
    /**
     * This method is called when receiving updates via GetUpdates method
     * @param update Update received
     */
    void onUpdateReceived(Update update);

    /**
     * This method is called when receiving updates via GetUpdates method.
     * If not reimplemented - it just sends updates by one into {@link #onUpdateReceived(Update)}
     * @param updates list of Update received
     */
    default void onUpdatesReceived(List<Update> updates) {
        updates.forEach(this::onUpdateReceived);
    }

    /**
     * Gets options for current bot
     * @return BotOptions object with options information
     */
    BotOptions getOptions();

    /**
     * Clear current webhook (if present) calling setWebhook method with empty url.
     */
    void clearWebhook() throws TelegramApiRequestException;

    /**
     * Called when the BotSession is being closed
     */
    default void onClosing() {
    }
}
