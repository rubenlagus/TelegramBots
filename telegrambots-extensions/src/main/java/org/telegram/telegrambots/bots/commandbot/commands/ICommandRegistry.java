package org.telegram.telegrambots.bots.commandbot.commands;

import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.bots.AbsSender;

import java.util.Collection;
import java.util.Map;
import java.util.function.BiConsumer;

/**
 * This Interface represents the gateway for registering and deregistering commands.
 *
 * @author Timo Schulz (Mit0x2)
 */
public interface ICommandRegistry {

    /**
     * Register a default action when there is no command register that match the message sent
     * @param defaultConsumer Consumer to evaluate the message
     *
     * @note Use this method if you want your bot to execute a default action when the user
     * sends a command that is not registered.
     */
    void registerDefaultAction(BiConsumer<AbsSender, Message> defaultConsumer);

    /**
     * register a command
     *
     * @param botCommand the command to register
     * @return whether the command could be registered, was not already registered
     */
    boolean register(DefaultBotCommand botCommand);

    /**
     * register multiple commands
     *
     * @param botCommands commands to register
     * @return map with results of the command register per command
     */
    Map<DefaultBotCommand, Boolean> registerAll(DefaultBotCommand... botCommands);

    /**
     * deregister a command
     *
     * @param botCommand the command to deregister
     * @return whether the command could be deregistered, was registered
     */
    boolean deregister(DefaultBotCommand botCommand);

    /**
     * deregister multiple commands
     *
     * @param botCommands commands to deregister
     * @return map with results of the command deregistered per command
     */
    Map<DefaultBotCommand, Boolean> deregisterAll(DefaultBotCommand... botCommands);

    /**
     * get a collection of all registered commands
     *
     * @return a collection of registered commands
     */
    Collection<DefaultBotCommand> getRegisteredCommands();

    /**
     * get registered command
     *
     * @return registered command if exists or null if not
     */
    DefaultBotCommand getRegisteredCommand(String commandIdentifier);
}