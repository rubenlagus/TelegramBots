package org.telegram.telegrambots.bots;

import org.telegram.telegrambots.ApiContext;
import org.telegram.telegrambots.api.objects.Chat;
import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.api.objects.User;
import org.telegram.telegrambots.bots.commands.BotCommand;
import org.telegram.telegrambots.bots.commands.conversation.ConversationCommand;
import org.telegram.telegrambots.bots.commands.conversation.ConversationLockHolder;
import org.telegram.telegrambots.bots.commands.conversation.ConversationRegistry;
import org.telegram.telegrambots.bots.commands.conversation.IConversationRegistry;

import java.util.Arrays;
import java.util.List;
import java.util.function.BiConsumer;

public abstract class TelegramLongPollingConversationBot extends TelegramLongPollingBot
    implements IConversationRegistry {
    private IConversationRegistry commandRegistry;
    private ConversationLockHolder conversationHolderLock;

    public TelegramLongPollingConversationBot() {
        this(ApiContext.getInstance(DefaultBotOptions.class));
    }

    public TelegramLongPollingConversationBot(DefaultBotOptions options) {
        this(options, true);
    }

    public TelegramLongPollingConversationBot(DefaultBotOptions options, boolean allowCommandsWithUsername) {
        super(options);
        this.commandRegistry = new ConversationRegistry(allowCommandsWithUsername, getBotUsername());
        this.conversationHolderLock = new ConversationLockHolder();
    }


    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage()) {
            Message message = update.getMessage();
            Chat chat = message.getChat();
            User user = message.getFrom();
            if (message.isCommand()) {
                if (message.hasText()) {
                    String text = message.getText();
                    if (text.startsWith(BotCommand.COMMAND_INIT_CHARACTER)) {

                        String messageBody = text.substring(1);
                        String[] splittedMessage = messageBody.split(BotCommand.COMMAND_PARAMETER_SEPARATOR);
                        String[] arguments = Arrays.copyOfRange(splittedMessage, 1, splittedMessage.length);
                        String commandIdentifier = splittedMessage[0];

                        if (commandIdentifier.equals("cancel") && conversationHolderLock.isLocked(chat.getId())) {
                            conversationHolderLock.getLockedConversation(chat.getId())
                                .cancel(this, user, chat, arguments);
                            conversationHolderLock.unlock(chat.getId());
                            return;
                        }

                        ConversationCommand command = commandRegistry.getRegisteredCommand(commandIdentifier);
                        if (command != null) {
                            if (!conversationHolderLock.isLocked(chat.getId())) {
                                conversationHolderLock.lock(chat.getId(), command);
                                command.execute(this, user, chat, arguments);
                                return;
                            }
                        }
                    }
                }
            }
            String text = message.getText();
            String messageBody = text.substring(1);
            String[] splittedMessage = messageBody.split(BotCommand.COMMAND_PARAMETER_SEPARATOR);
            String[] arguments = Arrays.copyOfRange(splittedMessage, 1, splittedMessage.length);
            String commandIdentifier = splittedMessage[0];
            ConversationCommand command = commandRegistry.getRegisteredCommand(commandIdentifier);
            if (command != null && text.startsWith(BotCommand.COMMAND_INIT_CHARACTER)) {
                command.warning(this, user, chat, arguments);
                return;
            }
            if (conversationHolderLock.isLocked(chat.getId())) {
                ConversationCommand lockedConversation = conversationHolderLock.getLockedConversation(chat.getId());
                lockedConversation.update(this, user, chat, splittedMessage);
                return;
            }
        }
        processNonCommandUpdate(update);
    }

    protected abstract void processNonCommandUpdate(Update update);


    @Override
    public void registerDefaultAction(BiConsumer<AbsSender, Message> defaultConsumer) {
        commandRegistry.registerDefaultAction(defaultConsumer);
    }

    @Override
    public boolean register(ConversationCommand conversationCommand) {
        return commandRegistry.register(conversationCommand);
    }

    @Override
    public boolean deregister(ConversationCommand conversationCommand) {
        return commandRegistry.deregister(conversationCommand);
    }

    @Override
    public void registerAll(List<ConversationCommand> conversationCommands) {
        commandRegistry.registerAll(conversationCommands);

    }

    @Override
    public void deregisterAll(List<ConversationCommand> conversationCommands) {
        commandRegistry.deregisterAll(conversationCommands);
    }

    @Override
    public List<ConversationCommand> getRegisteredCommands() {
        return commandRegistry.getRegisteredCommands();
    }

    @Override
    public ConversationCommand getRegisteredCommand(String commandIdentifier) {
        return commandRegistry.getRegisteredCommand(commandIdentifier);
    }
}
