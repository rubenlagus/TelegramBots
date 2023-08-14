package org.telegram.telegrambots.extensions.bots.commandbot.commands;

import org.telegram.telegrambots.extensions.bots.commandbot.activity.CommandState;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.bots.AbsSender;

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
     * @param absSender absSender to send messages over
     * @param message   the message to process
     * @param arguments command arguments
     */
    void processMessage(AbsSender absSender, Message message, String[] arguments);

    /**
     * Process the message and execute the command
     * with concrete state
     *
     * @implNote This default implementation for stateless commands
     *
     * @implSpec Implementation should return null if command
     * execution failed for any reason
     *
     * @param absSender    absSender to send messages over
     * @param message      the message to process
     * @param arguments    command arguments
     * @param commandState state of the current command
     */
    default CommandState<?> processMessage(AbsSender absSender, Message message, String[] arguments, CommandState<?> commandState) {
        processMessage(absSender, message, arguments);
        return new CommandState<>(null, null);
    }
}