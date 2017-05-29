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

/**
 * The TelegramLongPollingConversationBot class.
 * Implementation of LongPollingBot for {@link ConversationCommand}.
 *
 * @author jrrakh
 *         12/26/16
 */
public abstract class TelegramLongPollingConversationBot
    extends TelegramLongPollingBot implements IConversationRegistry {

    private static final String DEFAULT_CANCEL_COMMAND_IDENTIFIER = "cancel";
    private IConversationRegistry commandRegistry;
    private ConversationLockHolder conversationHolderLock;

    /**
     * Instantiates a new Telegram long polling conversation bot.
     */
    public TelegramLongPollingConversationBot() {
        this(ApiContext.getInstance(DefaultBotOptions.class));
    }

    /**
     * Instantiates a new Telegram long polling conversation bot.
     *
     * @param options the options
     */
    public TelegramLongPollingConversationBot(DefaultBotOptions options) {
        this(options, true);
    }

    /**
     * Instantiates a new Telegram long polling conversation bot.
     *
     * @param options                   the options
     * @param allowCommandsWithUsername the allow commands with username
     */
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
            UpdateData updateData = new UpdateData(message.getText());
            if (message.isCommand()) {
                if (message.hasText()) {
                    if (updateData.isTextStartWithCommand()) {
                        if (checkForCancelAndExecute(chat, user, updateData)) {
                            return;
                        }
                        if (checkForCommandAndExecute(chat, user, updateData)) {
                            return;
                        }
                        if (checkForWarningAndExecute(chat, user, updateData)) {
                            return;
                        }
                    }
                }
            }
            checkForUpdateAndExecute(chat, user, updateData);
        }
    }

    private void checkForUpdateAndExecute(Chat chat, User user, UpdateData updateData) {
        if (conversationHolderLock.isLocked(chat.getId())) {
            ConversationCommand lockedConversation = conversationHolderLock.getLockedConversation(chat.getId());
            lockedConversation.onUpdate(this, user, chat, updateData.getArguments());
        }
    }

    private boolean checkForWarningAndExecute(Chat chat, User user, UpdateData updateData) {
        ConversationCommand command = commandRegistry.getRegisteredCommand(updateData.getCommandIdentifier());
        if (command != null && updateData.getText().startsWith(BotCommand.COMMAND_INIT_CHARACTER)) {
            command.warning(this, user, chat, updateData.getArguments());
            return true;
        }
        return false;
    }

    private boolean checkForCommandAndExecute(Chat chat, User user, UpdateData updateData) {
        ConversationCommand command = commandRegistry.getRegisteredCommand(updateData.getCommandIdentifier());
        if (command != null) {
            if (!conversationHolderLock.isLocked(chat.getId())) {
                conversationHolderLock.lock(chat.getId(), command);
                command.execute(this, user, chat, updateData.getArguments());
                return true;
            }
        }
        return false;
    }

    private boolean checkForCancelAndExecute(Chat chat, User user, UpdateData updateData) {
        if (updateData.getCommandIdentifier().equals(DEFAULT_CANCEL_COMMAND_IDENTIFIER)
            && conversationHolderLock.isLocked(chat.getId())) {
            conversationHolderLock.getLockedConversation(chat.getId())
                .cancel(this, user, chat, updateData.getArguments());
            conversationHolderLock.unlock(chat.getId());
            return true;
        }
        return false;
    }

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

    private final class UpdateData {
        private final String text;
        private final String messageBody;

        private UpdateData(String text) {
            this.text = text;
            this.messageBody = text.substring(1);
        }

        private String getText() {
            return text;
        }

        private String[] getArguments() {
            String[] splittedMessage = messageBody.split(BotCommand.COMMAND_PARAMETER_SEPARATOR);
            return Arrays.copyOfRange(splittedMessage, 1, splittedMessage.length);
        }

        private String getCommandIdentifier() {
            return messageBody.split(BotCommand.COMMAND_PARAMETER_SEPARATOR)[0];
        }

        private boolean isTextStartWithCommand() {
            return text.startsWith(BotCommand.COMMAND_INIT_CHARACTER);
        }
    }
}
