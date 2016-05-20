package org.telegram.telegrambots.api.commands;

import org.telegram.telegrambots.api.objects.Message;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * @author tschulz
 */
public final class CommandRegistry implements ICommandRegistry {

    private final Map<String, BotCommand> commandRegistryMap = new HashMap<>();

    public CommandRegistry(String botToken) {
        register(new HelpBotCommand(this, botToken));
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

    /**
     * executes a command if present and replies the success
     *
     * @param message input message
     * @return        true if success or false otherwise
     */
    public final boolean executeCommand(Message message) {
        if (message.hasText()) {
            String text = message.getText();
            if (!text.isEmpty() && text.startsWith(BotCommand.COMMAND_INIT_CHARACTER)) {
                String commandMessage = text.substring(1);
                String[] commandSplit = commandMessage.split(BotCommand.COMMAND_PARAMETER_SEPARATOR);

                String command = commandSplit[0];

                if (commandRegistryMap.containsKey(command)) {
                    String[] parameters = Arrays.copyOfRange(commandSplit, 1, commandSplit.length);
                    commandRegistryMap.get(command).execute(parameters, message.getChatId());
                    return true;
                }
            }
        }
        return false;
    }
}