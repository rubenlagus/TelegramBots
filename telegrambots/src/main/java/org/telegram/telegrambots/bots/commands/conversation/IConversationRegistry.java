package org.telegram.telegrambots.bots.commands.conversation;

import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.bots.AbsSender;

import java.util.List;
import java.util.function.BiConsumer;

/**
 * The interface Conversation registry.
 *
 * @author Roman_Rahozhyn 12/26/16
 */
public interface IConversationRegistry {

    /**
     * Register default action.
     *
     * @param defaultConsumer the default consumer
     */
    void registerDefaultAction(BiConsumer<AbsSender, Message> defaultConsumer);

    /**
     * Register.
     *
     * @param conversationCommand the conversation command
     * @return the boolean
     */
    boolean register(ConversationCommand conversationCommand);

    /**
     * Deregister.
     *
     * @param conversationCommand the conversation command
     * @return the boolean
     */
    boolean deregister(ConversationCommand conversationCommand);

    /**
     * Register all.
     *
     * @param conversationCommands the conversation commands
     */
    void registerAll(List<ConversationCommand> conversationCommands);

    /**
     * Deregister all.
     *
     * @param conversationCommands the conversation commands
     */
    void deregisterAll(List<ConversationCommand> conversationCommands);

    /**
     * Gets registered commands.
     *
     * @return the registered commands
     */
    List<ConversationCommand> getRegisteredCommands();

    /**
     * Gets registered command.
     *
     * @param commandIdentifier the command identifier
     * @return the regisetred command
     */
    ConversationCommand getRegisteredCommand(String commandIdentifier);

}
