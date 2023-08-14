package org.telegram.telegrambots.extensions.bots.commandbot.commands;

import org.telegram.telegrambots.extensions.bots.commandbot.activity.CommandState;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.bots.AbsSender;

import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.Supplier;
import java.util.regex.Pattern;

/**
 * This class manages all the commands for a bot.
 * You can register and deregister commands on demand
 *
 * @author Timo Schulz (Mit0x2)
 */
public final class CommandRegistry implements ICommandRegistry {

    private final Map<String, IBotCommand> commandRegistryMap = new HashMap<>();
    private final boolean allowCommandsWithUsername;
    private final Supplier<String> botUsernameSupplier;
    private BiConsumer<AbsSender, Message> defaultConsumer;

    /**
     * Creates a Command registry
     * @param allowCommandsWithUsername True to allow commands with username, false otherwise
     * @param botUsernameSupplier       Bot username supplier
     */
    public CommandRegistry(boolean allowCommandsWithUsername, Supplier<String> botUsernameSupplier) {
        this.allowCommandsWithUsername = allowCommandsWithUsername;
        this.botUsernameSupplier = botUsernameSupplier;
    }

    @Override
    public void registerDefaultAction(BiConsumer<AbsSender, Message> defaultConsumer) {
        this.defaultConsumer = defaultConsumer;
    }

    @Override
    public boolean register(IBotCommand botCommand) {
        if (commandRegistryMap.containsKey(botCommand.getCommandIdentifier())) {
            return false;
        }
        commandRegistryMap.put(botCommand.getCommandIdentifier(), botCommand);
        return true;
    }

    @Override
    public Map<IBotCommand, Boolean> registerAll(IBotCommand... botCommands) {
        Map<IBotCommand, Boolean> resultMap = new HashMap<>(botCommands.length);
        for (IBotCommand botCommand : botCommands) {
            resultMap.put(botCommand, register(botCommand));
        }
        return resultMap;
    }

    @Override
    public boolean deregister(IBotCommand botCommand) {
        if (commandRegistryMap.containsKey(botCommand.getCommandIdentifier())) {
            commandRegistryMap.remove(botCommand.getCommandIdentifier());
            return true;
        }
        return false;
    }

    @Override
    public Map<IBotCommand, Boolean> deregisterAll(IBotCommand... botCommands) {
        Map<IBotCommand, Boolean> resultMap = new HashMap<>(botCommands.length);
        for (IBotCommand botCommand : botCommands) {
            resultMap.put(botCommand, deregister(botCommand));
        }
        return resultMap;
    }

    @Override
    public Collection<IBotCommand> getRegisteredCommands() {
        return commandRegistryMap.values();
    }

    @Override
    public IBotCommand getRegisteredCommand(String commandIdentifier) {
        return commandRegistryMap.get(commandIdentifier);
    }

    /**
     * Executes a stateless command action if the command is registered.
     *
     * @apiNote  If the command is not registered and there is a default consumer,
     * that action will be performed
     *
     * @param absSender absSender
     * @param message input message
     * @return True if a command or default action is executed, false otherwise
     */
    public boolean executeCommand(AbsSender absSender, Message message) {
        if (message.hasText()) {
            String text = message.getText();
            if (text.startsWith(BotCommand.COMMAND_INIT_CHARACTER)) {
                String commandMessage = text.substring(1);
                String[] commandSplit = commandMessage.split(BotCommand.COMMAND_PARAMETER_SEPARATOR_REGEXP);

                String command = removeUsernameFromCommandIfNeeded(commandSplit[0]);

                if (commandRegistryMap.containsKey(command)) {
                    String[] parameters = Arrays.copyOfRange(commandSplit, 1, commandSplit.length);
                    commandRegistryMap.get(command).processMessage(absSender, message, parameters);
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
     * Start or continue executes a stateful command action.
     *
     * @apiNote  If the command is not registered and there is a default consumer,
     * that action will be performed for <b>start command</b>
     *
     * @implNote {@link CommandState} object also contains name
     * of the command to be continued or started
     *
     * @param absSender absSender
     * @param message input message
     * @param commandState state of the current command
     * @return new state of the command or null if command not found,
     * or message is empty
     */
    public CommandState<?> executeCommand(AbsSender absSender, Message message, CommandState<?> commandState) {
        if (message.hasText()) {
            String text = message.getText();
            if (text.startsWith(BotCommand.COMMAND_INIT_CHARACTER)) {
                // Start new command
                String commandMessage = text.substring(1);
                String[] commandSplit = commandMessage.split(BotCommand.COMMAND_PARAMETER_SEPARATOR_REGEXP);

                String command = removeUsernameFromCommandIfNeeded(commandSplit[0]);

                if (commandRegistryMap.containsKey(command)) {
                    String[] parameters = Arrays.copyOfRange(commandSplit, 1, commandSplit.length);
                    return commandRegistryMap.get(command).processMessage(absSender, message, parameters, commandState);
                } else if (defaultConsumer != null) {
                    defaultConsumer.accept(absSender, message);
                    return new CommandState<>(command, null);
                }
            } else {
                // Continue some command with simple message
                String command = commandState.getIdentifier();

                if (command != null && commandRegistryMap.containsKey(command)) {
                    return commandRegistryMap.get(command).processMessage(absSender, message, new String[0], commandState);
                }
            }
        }
        return null;
    }

    /**
     * Continue executes a stateful command action.
     * Callback data can contain parameters for command.
     *
     * @implNote {@link CommandState} object also contains name
     * of the command to be continued
     *
     * @param absSender absSender
     * @param callbackQuery action from chat with callback data
     * @param commandState state of the current command
     * @return new state of the command or null if command not found
     */
    public CommandState<?> executeCommand(AbsSender absSender, CallbackQuery callbackQuery, CommandState<?> commandState) {
        Message message = callbackQuery.getMessage();
        String commandMessage = callbackQuery.getData();
        String[] parameters = new String[0];
        if (commandMessage != null) {
            parameters = commandMessage.split(BotCommand.COMMAND_PARAMETER_SEPARATOR_REGEXP);
        }

        String command = commandState.getIdentifier();

        if (command != null && commandRegistryMap.containsKey(command)) {
            return commandRegistryMap.get(command).processMessage(absSender, message, parameters, commandState);
        }
        return null;
    }

    /**
     * if {@link #allowCommandsWithUsername} is enabled, the username of the bot is removed from
     * the command
     * @param command Command to simplify
     * @return Simplified command
     * @throws java.lang.NullPointerException if {@code allowCommandsWithUsername} is {@code true}
     *                                        and {@code botUsernameSupplier} returns {@code null}
     */
    private String removeUsernameFromCommandIfNeeded(String command) {
        if (allowCommandsWithUsername) {
            String botUsername = Objects.requireNonNull(botUsernameSupplier.get(), "Bot username must not be null");
            return command.replaceAll("(?i)@" + Pattern.quote(botUsername), "").trim();
        }
        return command;
    }
}
