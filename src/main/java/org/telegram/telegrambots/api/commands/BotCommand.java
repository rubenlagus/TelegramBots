package org.telegram.telegrambots.api.commands;

import org.telegram.telegrambots.api.objects.Chat;
import org.telegram.telegrambots.bots.AbsSender;

/**
 * Representation of a command, which can be executed
 *
 * @author tschulz
 */
public abstract class BotCommand {

    public final static String COMMAND_INIT_CHARACTER = "/";
    public final static String COMMAND_PARAMETER_SEPARATOR = " ";
    private final static int COMMAND_MAX_LENGTH = 32;

    private final String commandIdentifier;
    private final String description;

    /**
     * construct a command
     *
     * @param commandIdentifier the unique identifier of this command (e.g. the command string to enter into chat)
     * @param description       the description of this command
     */
    public BotCommand(String commandIdentifier, String description) {

        if (commandIdentifier == null || commandIdentifier.isEmpty()) {
            throw new IllegalArgumentException("commandIdentifier for command cannot be null or empty");
        }

        if (commandIdentifier.startsWith(COMMAND_INIT_CHARACTER)) {
            commandIdentifier = commandIdentifier.substring(1);
        }

        if (commandIdentifier.length() + 1 > COMMAND_MAX_LENGTH) {
            throw new IllegalArgumentException("commandIdentifier cannot be longer than " + COMMAND_MAX_LENGTH + " (including " + COMMAND_INIT_CHARACTER + ")");
        }

        this.commandIdentifier = commandIdentifier.toLowerCase();
        this.description = description;
    }

    /**
     * get the identifier of this command
     *
     * @return the identifier
     */
    public final String getCommandIdentifier() {
        return commandIdentifier;
    }

    /**
     * get the description of this command
     *
     * @return the description as String
     */
    public final String getDescription() {
        return description;
    }

    /**
     * execute the command
     *
     * @param absSender absSender to send messages over
     * @param chat      the chat, to be able to send replies
     * @param arguments passed arguments
     */
    public abstract void execute(AbsSender absSender, Chat chat, String[] arguments);
}