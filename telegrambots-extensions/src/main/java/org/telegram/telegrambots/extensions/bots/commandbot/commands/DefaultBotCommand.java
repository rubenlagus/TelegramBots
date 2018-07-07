package org.telegram.telegrambots.extensions.bots.commandbot.commands;

import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.bots.AbsSender;

/**
 * Bot command with message ID in execute method
 *
 * @author Vadim Goroshevsky (@vadimgoroshevsky)
 */
public abstract class DefaultBotCommand extends BotCommand {

    /**
     * Construct a command
     *
     * @param commandIdentifier the unique identifier of this command (e.g. the command string to
     *                          enter into chat)
     * @param description       the description of this command
     */
    public DefaultBotCommand(String commandIdentifier, String description) {
        super(commandIdentifier, description);
    }

    /**
     * Process the message and execute the command
     *
     * @param absSender absSender to send messages over
     * @param message   the message to process
     * @param arguments passed arguments
     */
    @Override
    public void processMessage(AbsSender absSender, Message message, String[] arguments) {
        execute(absSender, message.getFrom(), message.getChat(), message.getMessageId(), arguments);
    }

    // We'll override this method here for not repeating it in DefaultBotCommand's children
    @Override
    public final void execute(AbsSender absSender, User user, Chat chat, String[] arguments) {
    }

    /**
     * Execute the command
     *
     * @param absSender absSender to send messages over
     * @param user      the user who sent the command
     * @param chat      the chat, to be able to send replies
     * @param messageId message id for interaction
     * @param arguments passed arguments
     */
    public abstract void execute(AbsSender absSender, User user, Chat chat, Integer messageId, String[] arguments);
}
