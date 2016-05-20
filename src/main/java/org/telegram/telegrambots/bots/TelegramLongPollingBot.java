package org.telegram.telegrambots.bots;

import org.telegram.telegrambots.BotLogger;
import org.telegram.telegrambots.TelegramApiException;
import org.telegram.telegrambots.api.commands.Command;
import org.telegram.telegrambots.api.commands.CommandRegistry;
import org.telegram.telegrambots.api.commands.ICommandRegistery;
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
public abstract class TelegramLongPollingBot extends AbsSender implements ITelegramLongPollingBot, ICommandRegistery {

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
    public final boolean register(Command command) {
        return commandRegistry.register(command);
    }

    @Override
    public final Map<Command, Boolean> registerAll(Command... commands) {
        return commandRegistry.registerAll(commands);
    }

    @Override
    public final boolean deregister(Command command) {
        return commandRegistry.deregister(command);
    }

    @Override
    public final Map<Command, Boolean> deregisterAll(Command... commands) {
        return commandRegistry.deregisterAll(commands);
    }

    @Override
    public final Collection<Command> getRegisteredCommands() {
        return commandRegistry.getRegisteredCommands();
    }

    public abstract void processNonCommandUpdate(Update update);
}