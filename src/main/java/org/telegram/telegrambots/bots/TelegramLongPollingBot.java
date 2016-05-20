package org.telegram.telegrambots.bots;

import org.telegram.telegrambots.BotLogger;
import org.telegram.telegrambots.TelegramApiException;
import org.telegram.telegrambots.api.commands.BotCommand;
import org.telegram.telegrambots.api.commands.CommandRegistry;
import org.telegram.telegrambots.api.commands.ICommandRegistry;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.api.objects.Update;

import java.util.Collection;
import java.util.Map;

/**
 * @author Ruben Bermudez
 * @version 1.0
 * @brief TODO
 * @date 14 of January of 2016
 */
public abstract class TelegramLongPollingBot extends AbsSender implements ITelegramLongPollingBot, ICommandRegistry {

    public static final String LOGTAG = "TelegramLongPollingBot";
    private final CommandRegistry commandRegistry;

    public TelegramLongPollingBot() {
        this.commandRegistry = new CommandRegistry(getBotToken());
    }

    @Override
    public final void onUpdateReceived(Update update) {
        if (update.hasMessage()) {
            Message message = update.getMessage();
            if (message.isCommand()) {
                if (!commandRegistry.executeCommand(message)) {
                    SendMessage sendMessage = new SendMessage();
                    sendMessage.setChatId(message.getChatId());
                    sendMessage.setText("The command you provided is not registered for this bot");
                    try {
                        sendMessage(sendMessage);
                    } catch (TelegramApiException e) {
                        BotLogger.error("Cannot send message", LOGTAG, e);
                    }
                }
                return;
            }
        }
        processNonCommandUpdate(update);
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

    public abstract void processNonCommandUpdate(Update update);
}