package org.telegram.telegrambots.extensions.bots.commandbot.commands;

import org.telegram.telegrambots.meta.api.objects.message.Message;
import org.telegram.telegrambots.meta.generics.TelegramClient;

/**
 * This Interface represents the a Command that can be executed
 *
 * @author Timo Schulz (Mit0x2)
 */
public interface IBotCommand {
    /**
     * Get the identifier of this command
     *
     * @return the identifier
     */
    String getCommandIdentifier();

    /**
     * Get the description of this command
     *
     * @return the description as String
     */
    String getDescription();

    /**
     * Process the message and execute the command
     *
     * @param telegramClient client to send requests
     * @param message   the message to process
     */
    void processMessage(TelegramClient telegramClient, Message message, String[] arguments);
}