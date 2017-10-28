package org.telegram.telegrambots.bots.commandbot.commands;

import org.telegram.telegrambots.api.objects.Chat;
import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.api.objects.User;
import org.telegram.telegrambots.bots.AbsSender;

/**
 * Representation of a command, which can be executed
 *
 * @author Timo Schulz (Mit0x2)
 */
public abstract class BotCommand extends DefaultBotCommand {

    /**
     * Construct a command
     *
     * @param commandIdentifier the unique identifier of this command (e.g. the command string to
     *                          enter into chat)
     * @param description       the description of this command
     */
    public BotCommand(String commandIdentifier, String description) {
        super(commandIdentifier, description);
    }

    /**
     * Overridden method from DefaultBotCommand, for backwards compatibility
     *
     * @param absSender absSender to send messages over
     * @param message   the message with command, for further processing
     * @param arguments passed arguments
     */

    @Override
    public void execute(AbsSender absSender, Message message, String[] arguments) {
        execute(absSender, message.getFrom(), message.getChat(), arguments);
    }

    /**
     * Execute the command
     *
     * @param absSender absSender to send messages over
     * @param user      the user who sent the command
     * @param chat      the chat, to be able to send replies
     * @param arguments passed arguments
     */
    public abstract void execute(AbsSender absSender, User user, Chat chat, String[] arguments);
}
