package org.telegram.telegrambots.extensions.bots.commandbot;


import org.telegram.telegrambots.extensions.bots.commandbot.commands.CommandRegistry;
import org.telegram.telegrambots.extensions.bots.commandbot.commands.IBotCommand;
import org.telegram.telegrambots.extensions.bots.commandbot.commands.ICommandRegistry;
import org.telegram.telegrambots.meta.api.methods.botapimethods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.message.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.generics.TelegramClient;
import org.telegram.telegrambots.webhook.DefaultTelegramWebhookBot;

import java.util.Collection;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Supplier;

/**
 * This class adds command functionality to the TelegramWebhookBot
 *
 * @author Andrey Korsakov (loolzaaa)
 */
public abstract class TelegramWebhookCommandBot extends DefaultTelegramWebhookBot implements CommandBot, ICommandRegistry {
    private final CommandRegistry commandRegistry;

    /**
     * Creates a TelegramWebhookCommandBot
     * Use ICommandRegistry's methods on this bot to register commands
     *
     * @param telegramClient Telegram client used to send requests
     * @param allowCommandsWithUsername true to allow commands with parameters (default), false otherwise
     * @param botUsernameSupplier Bot username supplier
     *
     * @param botPath Bot Path for the webhook
     * @param setWebhook Set webhook method execution
     * @param deleteWebhook Delete webhook method execution
     */
    public TelegramWebhookCommandBot(
            TelegramClient telegramClient,
            boolean allowCommandsWithUsername,
            Supplier<String> botUsernameSupplier,
            String botPath,
            Runnable setWebhook,
            Runnable deleteWebhook) {
        super(botPath, null, setWebhook, deleteWebhook);
        this.commandRegistry = new CommandRegistry(telegramClient, allowCommandsWithUsername, botUsernameSupplier);
    }

    @Override
    public BotApiMethod<?> consumeUpdate(Update update) {
        if (update.hasMessage()) {
            Message message = update.getMessage();
            if (message.isCommand() && !filter(message)) {
                if (!commandRegistry.executeCommand(message)) {
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
    public void registerDefaultAction(BiConsumer<TelegramClient, Message> defaultConsumer) {
        commandRegistry.registerDefaultAction(defaultConsumer);
    }

    @Override
    public final IBotCommand getRegisteredCommand(String commandIdentifier) {
        return commandRegistry.getRegisteredCommand(commandIdentifier);
    }
}
