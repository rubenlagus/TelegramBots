package org.telegram.telegrambots.webhook.starter.extension;

import org.telegram.telegrambots.meta.api.objects.message.Message;
import org.telegram.telegrambots.meta.generics.TelegramClient;

/**
 * This Interface represents the Command that can be executed
 *
 * @author Petrov Makariy (makariyp)
 */
public interface ICommand {
    /**
     * Process the message and execute the command
     *
     * @param telegramClient client to send requests
     * @param message   the message to process
     */
    void processMessage(TelegramClient telegramClient, Message message, String[] arguments);

}
