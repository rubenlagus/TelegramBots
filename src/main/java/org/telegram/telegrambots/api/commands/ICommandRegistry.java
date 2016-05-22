package org.telegram.telegrambots.api.commands;

import java.util.Collection;
import java.util.Map;

/**
 *
 */
public interface ICommandRegistry {

    /**
     * register a command
     *
     * @param botCommand the command to register
     * @return        whether the command could be registered, was not already registered
     */
    boolean register(BotCommand botCommand);

    /**
     * register multiple commands
     *
     * @param botCommands commands to register
     * @return         map with results of the command register per command
     */
    Map<BotCommand, Boolean> registerAll(BotCommand... botCommands);

    /**
     * deregister a command
     *
     * @param botCommand the command to deregister
     * @return        whether the command could be deregistered, was registered
     */
    boolean deregister(BotCommand botCommand);

    /**
     * deregister multiple commands
     *
     * @param botCommands commands to deregister
     * @return         map with results of the command deregistered per command
     */
    Map<BotCommand, Boolean> deregisterAll(BotCommand... botCommands);

    /**
     * get a collection of all registered commands
     *
     * @return a collection of registered commands
     */
    Collection<BotCommand> getRegisteredCommands();

}