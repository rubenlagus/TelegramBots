package org.telegram.telegrambots.bots.commands;

import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.bots.AbsSender;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;

/**
 * This class manages all the commands for a bot. You can register and deregister commands on demand
 *
 * @author Timo Schulz (Mit0x2)
 */
public final class CommandRegistry implements ICommandRegistry {

    private final Map<String, BotCommand> commandRegistryMap = new HashMap<>();
    private final boolean allowCommandsWithUsername;
    private final String botUsername;
    private BiConsumer<AbsSender, Message> defaultConsumer;

    /**
     * Creates a Command registry
     * @param allowCommandsWithUsername True to allow commands with username, false otherwise
     * @param botUsername Bot username
     */
    public CommandRegistry(boolean allowCommandsWithUsername, String botUsername) {
        this.allowCommandsWithUsername = allowCommandsWithUsername;
        this.botUsername = botUsername;
    }

    @Override
    public void registerDefaultAction(BiConsumer<AbsSender, Message> defaultConsumer) {
        this.defaultConsumer = defaultConsumer;
    }

    @Override
    public final boolean register(BotCommand botCommand) {
        if (commandRegistryMap.containsKey(botCommand.getCommandIdentifier())) {
            return false;
        }
        commandRegistryMap.put(botCommand.getCommandIdentifier(), botCommand);
        return true;
    }

    @Override
    public final Map<BotCommand, Boolean> registerAll(BotCommand... botCommands) {
        Map<BotCommand, Boolean> resultMap = new HashMap<>(botCommands.length);
        for (BotCommand botCommand : botCommands) {
            resultMap.put(botCommand, register(botCommand));
        }
        return resultMap;
    }

    @Override
    public final boolean deregister(BotCommand botCommand) {
        if (commandRegistryMap.containsKey(botCommand.getCommandIdentifier())) {
            commandRegistryMap.remove(botCommand.getCommandIdentifier());
            return true;
        }
        return false;
    }

    @Override
    public final Map<BotCommand, Boolean> deregisterAll(BotCommand... botCommands) {
        Map<BotCommand, Boolean> resultMap = new HashMap<>(botCommands.length);
        for (BotCommand botCommand : botCommands) {
            resultMap.put(botCommand, deregister(botCommand));
        }
        return resultMap;
    }

    @Override
    public final Collection<BotCommand> getRegisteredCommands() {
        return commandRegistryMap.values();
    }

    @Override
    public final BotCommand getRegisteredCommand(String commandIdentifier) {
        return commandRegistryMap.get(commandIdentifier);
    }

    /**
     * Executes a command action if the command is registered.
     *
     * @note If the command is not registered and there is a default consumer,
     * that action will be performed
     *
     * @param absSender absSender
     * @param message input message
     * @return True if a command or default action is executed, false otherwise
     */
    public final boolean executeCommand(AbsSender absSender, Message message) {
        if (message.hasText()) {
            String text = message.getText();
            if (text.startsWith(BotCommand.COMMAND_INIT_CHARACTER)) {
                String commandMessage = text.substring(1);
                String[] commandSplit = commandMessage.split(BotCommand.COMMAND_PARAMETER_SEPARATOR);

                String command = removeUsernameFromCommandIfNeeded(commandSplit[0]);

                if (commandRegistryMap.containsKey(command)) {
                    String[] parameters = Arrays.copyOfRange(commandSplit, 1, commandSplit.length);
                    commandRegistryMap.get(command).execute(absSender, message.getFrom(), message.getChat(), parameters);
                    return true;
                } else if (defaultConsumer != null) {
                    defaultConsumer.accept(absSender, message);
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * if {@link #allowCommandsWithUsername} is enabled, the username of the bot is removed from
     * the command
     * @param command Command to simplify
     * @return Simplified command
     */
    private String removeUsernameFromCommandIfNeeded(String command) {
        if (allowCommandsWithUsername) {
            return command.replace("@" + botUsername, "").trim();
        }
        return command;
    }
}