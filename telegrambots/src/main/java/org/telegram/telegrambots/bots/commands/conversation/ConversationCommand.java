package org.telegram.telegrambots.bots.commands.conversation;

import org.telegram.telegrambots.api.objects.Chat;
import org.telegram.telegrambots.api.objects.User;
import org.telegram.telegrambots.bots.AbsSender;
import org.telegram.telegrambots.bots.commands.BotCommand;

/**
 * The ConversationCommand class.
 * Type of command which provide ability to create conversation with bot.
 * @author jrrakh
 *         12/26/16
 */
public abstract class ConversationCommand extends BotCommand {

    /**
     * Construct a command
     *
     * @param commandIdentifier the unique identifier of this command (e.g. the command string to
     *                          enter into chat)
     * @param description       the description of this command
     */
    public ConversationCommand(String commandIdentifier, String description) {
        super(commandIdentifier, description);
    }

    /**
     * Execute on update, when message to bot sent.
     *
     * @param absSender absSender to send messages over
     * @param user      the user who sent the command
     * @param chat      the chat, to be able to send replies
     * @param arguments passed arguments
     */
    public abstract void onUpdate(AbsSender absSender, User user, Chat chat, String[] arguments);

    /**
     * Execute when user cancel conversation command.
     *
     * @param absSender absSender to send messages over
     * @param user      the user who sent the command
     * @param chat      the chat, to be able to send replies
     * @param arguments passed arguments
     */
    public abstract void cancel(AbsSender absSender, User user, Chat chat, String[] arguments);

    /**
     * Execute if user try to execute another command while conversation command in process.
     *
     * @param absSender absSender to send messages over
     * @param user      the user who sent the command
     * @param chat      the chat, to be able to send replies
     * @param arguments passed arguments
     */
    public abstract void warning(AbsSender absSender, User user, Chat chat, String[] arguments);
}
