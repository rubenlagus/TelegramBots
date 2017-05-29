package org.telegram.telegrambots.bots.commands.conversation;

import com.google.common.collect.Lists;
import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.bots.AbsSender;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;

/** The ConversationRegistry class for {@link ConversationCommand}.
 * @author jrrakh
 *         12/26/16
 */
public class ConversationRegistry implements IConversationRegistry {
    private final Map<String, ConversationCommand> commandRegistryMap = new HashMap<>();
    private final boolean allowCommandsWithUsername;
    private final String botUsername;
    private BiConsumer<AbsSender, Message> defaultConsumer;

    /**
     * Creates a Command registry
     *
     * @param allowCommandsWithUsername True to allow commands with username, false otherwise
     * @param botUsername               Bot username
     */
    public ConversationRegistry(boolean allowCommandsWithUsername, String botUsername) {
        this.allowCommandsWithUsername = allowCommandsWithUsername;
        this.botUsername = botUsername;
    }

    @Override
    public void registerDefaultAction(BiConsumer<AbsSender, Message> defaultConsumer) {
        this.defaultConsumer = defaultConsumer;
    }

    @Override
    public boolean register(ConversationCommand conversationCommand) {
        if (commandRegistryMap.containsKey(conversationCommand.getCommandIdentifier())) {
            return false;
        }
        commandRegistryMap.put(conversationCommand.getCommandIdentifier(), conversationCommand);
        return true;
    }

    @Override
    public boolean deregister(ConversationCommand conversationCommand) {
        return commandRegistryMap.remove(conversationCommand.getCommandIdentifier()) != null;
    }

    @Override
    public void registerAll(List<ConversationCommand> conversationCommands) {
        conversationCommands
            .forEach(command -> commandRegistryMap.put(command.getCommandIdentifier(), command));
    }

    @Override
    public void deregisterAll(List<ConversationCommand> conversationCommands) {
        conversationCommands
            .forEach(command -> commandRegistryMap.remove(command.getCommandIdentifier()));
    }

    @Override
    public List<ConversationCommand> getRegisteredCommands() {
        return Lists.newArrayList(commandRegistryMap.values());
    }

    @Override
    public ConversationCommand getRegisteredCommand(String commandIdentifier) {
        return commandRegistryMap.get(commandIdentifier);
    }
}
