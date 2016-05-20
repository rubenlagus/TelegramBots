package org.telegram.telegrambots.api.commands;

import org.telegram.telegrambots.api.objects.Message;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * @author tschulz
 */
public final class CommandRegistry implements ICommandRegistery {

    private final Map<String, Command> commandRegistryMap = new HashMap<>();

    public CommandRegistry(String botToken) {
        register(new HelpCommand(this, botToken));
    }

    @Override
    public final boolean register(Command command) {
        if (commandRegistryMap.containsKey(command.getCommandIdentifier())) {
            return false;
        }
        commandRegistryMap.put(command.getCommandIdentifier(), command);
        return true;
    }

    @Override
    public final Map<Command, Boolean> registerAll(Command... commands) {
        Map<Command, Boolean> resultMap = new HashMap<>(commands.length);
        for (Command command : commands) {
            resultMap.put(command, register(command));
        }
        return resultMap;
    }

    @Override
    public final boolean deregister(Command command) {
        if (commandRegistryMap.containsKey(command.getCommandIdentifier())) {
            commandRegistryMap.remove(command.getCommandIdentifier());
            return true;
        }
        return false;
    }

    @Override
    public final Map<Command, Boolean> deregisterAll(Command... commands) {
        Map<Command, Boolean> resultMap = new HashMap<>(commands.length);
        for (Command command : commands) {
            resultMap.put(command, deregister(command));
        }
        return resultMap;
    }

    @Override
    public final Collection<Command> getRegisteredCommands() {
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
            if (!text.isEmpty() && text.startsWith(Command.COMMAND_INIT_CHARACTER)) {
                String commandMessage = text.substring(1);
                String[] commandSplit = commandMessage.split(Command.COMMAND_PARAMETER_SEPERATOR);

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