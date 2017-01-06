package org.telegram.telegrambots.bots.commands.conversation;

import org.telegram.telegrambots.api.objects.Chat;
import org.telegram.telegrambots.api.objects.User;
import org.telegram.telegrambots.bots.AbsSender;
import org.telegram.telegrambots.bots.commands.BotCommand;

/**
 * @author Roman_Rahozhyn
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
     * Update the command
     *
     * @param absSender absSender to send messages over
     * @param user      the user who sent the command
     * @param chat      the chat, to be able to send replies
     * @param arguments passed arguments
     */
    public abstract void update(AbsSender absSender, User user, Chat chat, String[] arguments);

    /**
     * Cancel the command
     *
     * @param absSender absSender to send messages over
     * @param user      the user who sent the command
     * @param chat      the chat, to be able to send replies
     * @param arguments passed arguments
     */
    public abstract void cancel(AbsSender absSender, User user, Chat chat, String[] arguments);

    /**
     * Warning for command
     *
     * @param absSender absSender to send messages over
     * @param user      the user who sent the command
     * @param chat      the chat, to be able to send replies
     * @param arguments passed arguments
     */
    public abstract void warning(AbsSender absSender, User user, Chat chat, String[] arguments);
}
