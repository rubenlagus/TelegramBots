package org.telegram.telegrambots.extensions.bots.commandbot;


import org.telegram.telegrambots.bots.DefaultBotOptions;
import org.telegram.telegrambots.bots.TelegramWebhookBot;
import org.telegram.telegrambots.extensions.bots.commandbot.commands.CommandRegistry;
import org.telegram.telegrambots.extensions.bots.commandbot.commands.IBotCommand;
import org.telegram.telegrambots.extensions.bots.commandbot.commands.ICommandRegistry;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.bots.AbsSender;

import java.util.Collection;
import java.util.Map;
import java.util.function.BiConsumer;

/**
 * This class adds command functionality to the TelegramWebhookBot
 *
 * @author Andrey Korsakov (loolzaaa)
 */
public abstract class TelegramWebhookCommandBot extends TelegramWebhookBot implements CommandBot, ICommandRegistry {
    private final CommandRegistry commandRegistry;

    /**
     * Creates a TelegramWebhookCommandBot using default options
     * Use ICommandRegistry's methods on this bot to register commands
     *
     * @deprecated Overwriting the getBotToken() method is deprecated. Use the constructor instead
     */
    @Deprecated
    public TelegramWebhookCommandBot() {
        this(new DefaultBotOptions());
    }

    /**
     * Creates a TelegramWebhookCommandBot with custom options and allowing commands with
     * usernames
     * Use ICommandRegistry's methods on this bot to register commands
     *
     * @deprecated Overwriting the getBotToken() method is deprecated. Use the constructor instead
     *
     * @param options     Bot options
     */
    @Deprecated
    public TelegramWebhookCommandBot(DefaultBotOptions options) {
        this(options, true);
    }

    /**
     * Creates a TelegramWebhookCommandBot
     * Use ICommandRegistry's methods on this bot to register commands
     *
     * @deprecated Overwriting the getBotToken() method is deprecated. Use the constructor instead
     *
     * @param options                   Bot options
     * @param allowCommandsWithUsername true to allow commands with parameters (default),
     *                                  false otherwise
     */
    @Deprecated
    public TelegramWebhookCommandBot(DefaultBotOptions options, boolean allowCommandsWithUsername) {
        super(options);
        this.commandRegistry = new CommandRegistry(allowCommandsWithUsername, this::getBotUsername);
    }

    /**
     * Creates a TelegramWebhookCommandBot using default options
     * Use ICommandRegistry's methods on this bot to register commands
     *
     * @param botToken the telegram api token
     */
    public TelegramWebhookCommandBot(String botToken) {
        this(new DefaultBotOptions(), botToken);
    }

    /**
     * Creates a TelegramWebhookCommandBot with custom options and allowing commands with
     * usernames
     * Use ICommandRegistry's methods on this bot to register commands
     *
     * @param options     Bot options
     * @param botToken the telegram api token
     */
    public TelegramWebhookCommandBot(DefaultBotOptions options, String botToken) {
        this(options, true, botToken);
    }

    /**
     * Creates a TelegramWebhookCommandBot
     * Use ICommandRegistry's methods on this bot to register commands
     *
     * @param options                   Bot options
     * @param allowCommandsWithUsername true to allow commands with parameters (default),
     *                                  false otherwise
     * @param botToken the telegram api token
     */
    public TelegramWebhookCommandBot(DefaultBotOptions options, boolean allowCommandsWithUsername, String botToken) {
        super(options, botToken);
        this.commandRegistry = new CommandRegistry(allowCommandsWithUsername, this::getBotUsername);
    }

    @Override
    public BotApiMethod<?> onWebhookUpdateReceived(Update update) {
        if (update.hasMessage()) {
            Message message = update.getMessage();
            if (message.isCommand() && !filter(message)) {
                if (!commandRegistry.executeCommand(this, message)) {
                    //we have received a not registered command, handle it as invalid
                    processInvalidCommandUpdate(update);
                }
                return null;
            }
        }
        processNonCommandUpdate(update);
        return null;
    }

    @Override
    public final boolean register(IBotCommand botCommand) {
        return commandRegistry.register(botCommand);
    }

    @Override
    public final Map<IBotCommand, Boolean> registerAll(IBotCommand... botCommands) {
        return commandRegistry.registerAll(botCommands);
    }

    @Override
    public final boolean deregister(IBotCommand botCommand) {
        return commandRegistry.deregister(botCommand);
    }

    @Override
    public final Map<IBotCommand, Boolean> deregisterAll(IBotCommand... botCommands) {
        return commandRegistry.deregisterAll(botCommands);
    }

    @Override
    public final Collection<IBotCommand> getRegisteredCommands() {
        return commandRegistry.getRegisteredCommands();
    }

    @Override
    public void registerDefaultAction(BiConsumer<AbsSender, Message> defaultConsumer) {
        commandRegistry.registerDefaultAction(defaultConsumer);
    }

    @Override
    public final IBotCommand getRegisteredCommand(String commandIdentifier) {
        return commandRegistry.getRegisteredCommand(commandIdentifier);
    }
}
