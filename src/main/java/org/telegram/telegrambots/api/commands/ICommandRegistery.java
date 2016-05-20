package org.telegram.telegrambots.api.commands;

import java.util.Map;

/**
 *
 */
public interface ICommandRegistery {

    /**
     * register a command
     *
     * @param command the command to register
     * @return        whether the command could be registered, was not already registered
     */
    boolean register(Command command);

    /**
     * register multiple commands
     *
     * @param commands commands to register
     * @return         map with results of the command register per command
     */
    Map<Command, Boolean> registerAll(Command... commands);

    /**
     * deregister a command
     *
     * @param command the command to deregister
     * @return        whether the command could be deregistered, was registered
     */
    boolean deregister(Command command);

    /**
     * deregister multiple commands
     *
     * @param commands commands to deregister
     * @return         map with results of the command deregistered per command
     */
    Map<Command, Boolean> deregisterAll(Command... commands);

}