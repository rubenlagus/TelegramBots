package org.telegram.telegrambots.bots;


import org.telegram.telegrambots.ApiContext;
import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.bots.commands.BotCommand;
import org.telegram.telegrambots.bots.commands.CommandRegistry;
import org.telegram.telegrambots.bots.commands.ICommandRegistry;

import java.util.Collection;
import java.util.Map;
import java.util.function.BiConsumer;

/**
 * This class adds command functionality to the TelegramLongPollingBot
 *
 * @author Timo Schulz (Mit0x2)
 */
public abstract class TelegramLongPollingCommandBot extends TelegramLongPollingBot implements ICommandRegistry {
    private final CommandRegistry commandRegistry;

    /**
     * Creates a TelegramLongPollingCommandBot using default options
     * Use ICommandRegistry's methods on this bot to register commands
     */
    public TelegramLongPollingCommandBot() {
        this(ApiContext.getInstance(DefaultBotOptions.class));
    }

    /**
     * Creates a TelegramLongPollingCommandBot with custom options and allowing commands with
     * usernames
     * Use ICommandRegistry's methods on this bot to register commands
     *
     * @param options Bot options
     */
    public TelegramLongPollingCommandBot(DefaultBotOptions options) {
        this(options, true);
    }

    /**
     * Creates a TelegramLongPollingCommandBot
     * Use ICommandRegistry's methods on this bot to register commands.
     * <p>
     * This ctor will call {@link #getBotUsername()}
     * </p>
     *
     * @param options                   Bot options
     * @param allowCommandsWithUsername true to allow commands with parameters (default),
     *                                  false otherwise
     */
    public TelegramLongPollingCommandBot(DefaultBotOptions options, boolean allowCommandsWithUsername) {
        super(options);
        this.commandRegistry = new CommandRegistry(allowCommandsWithUsername, getBotUsername());
    }

    /**
     * Creates a TelegramLongPollingCommandBot
     * Use ICommandRegistry's methods on this bot to register commands.
     *
     * @param options                   Bot options
     * @param botUsername               the name of the bot, used in the {@link CommandRegistry}.
     * @param allowCommandsWithUsername true to allow commands with parameters (default),
     *                                  false otherwise
     */
    public TelegramLongPollingCommandBot(DefaultBotOptions options, String botUsername, boolean allowCommandsWithUsername) {
        super(options);
        this.commandRegistry = new CommandRegistry(allowCommandsWithUsername, botUsername);
    }

    @Override
    public final void onUpdateReceived(Update update) {
        if (update.hasMessage()) {
            Message message = update.getMessage();
            if (message.isCommand() && !filter(message)) {
                if (commandRegistry.executeCommand(this, message)) {
                    return;
                }
            }
        }
        processNonCommandUpdate(update);
    }

    /**
     * Override this function in your bot implementation to filter messages with commands
     * <p>
     * For example, if you want to prevent commands execution incoming from group chat:
     * #
     * # return !message.getChat().isGroupChat();
     * #
     *
     * @param message Received message
     * @return true if the message must be ignored by the command bot and treated as a non command message,
     * false otherwise
     * @note Default implementation doesn't filter anything
     */
    protected boolean filter(Message message) {
        return false;
    }

    @Override
    public final boolean register(BotCommand botCommand) {
        return commandRegistry.register(botCommand);
    }

    @Override
    public final Map<BotCommand, Boolean> registerAll(BotCommand... botCommands) {
        return commandRegistry.registerAll(botCommands);
    }

    @Override
    public final boolean deregister(BotCommand botCommand) {
        return commandRegistry.deregister(botCommand);
    }

    @Override
    public final Map<BotCommand, Boolean> deregisterAll(BotCommand... botCommands) {
        return commandRegistry.deregisterAll(botCommands);
    }

    @Override
    public final Collection<BotCommand> getRegisteredCommands() {
        return commandRegistry.getRegisteredCommands();
    }

    @Override
    public void registerDefaultAction(BiConsumer<AbsSender, Message> defaultConsumer) {
        commandRegistry.registerDefaultAction(defaultConsumer);
    }

    @Override
    public final BotCommand getRegisteredCommand(String commandIdentifier) {
        return commandRegistry.getRegisteredCommand(commandIdentifier);
    }

    /**
     * Process all updates, that are not commands.
     *
     * @param update the update
     * @warning Commands that have valid syntax but are not registered on this bot,
     * won't be forwarded to this method <b>if a default action is present</b>.
     */
    public abstract void processNonCommandUpdate(Update update);
}
