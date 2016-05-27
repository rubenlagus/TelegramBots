package org.telegram.telegrambots.bots;


import org.telegram.telegrambots.TelegramApiException;
import org.telegram.telegrambots.api.commands.BotCommand;
import org.telegram.telegrambots.api.commands.CommandRegistry;
import org.telegram.telegrambots.api.commands.ICommandRegistry;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.logging.BotLogger;

import java.util.Collection;
import java.util.Map;

/**
 * This class adds command functionality to the TelegramLongPollingBot
 *
 * @author tschulz
 */
public abstract class TelegramLongPollingCommandBot extends TelegramLongPollingBot implements ICommandRegistry {

    public static final String LOGTAG = "TelegramLongPollingCommandBot";
    private final CommandRegistry commandRegistry;

    /**
     * construct creates CommandRegistry for this bot.
     * Use ICommandRegistry's methods on this bot to register commands
     */
    public TelegramLongPollingCommandBot() {
        this.commandRegistry = new CommandRegistry(getBotToken());
    }

    @Override
    public final void onUpdateReceived(Update update) {
        if (update.hasMessage()) {
            Message message = update.getMessage();
            if (message.isCommand()) {
                if (!commandRegistry.executeCommand(this, message)) {
                    SendMessage sendMessage = new SendMessage();
                    sendMessage.setChatId(message.getChatId().toString());
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

    /**
     * Process all updates, that are not commands.
     * Attention: commands, that have valid syntax but are not registered on this bot,
     * won't be forwarded to this method!
     *
     * @param update the update
     */
    public abstract void processNonCommandUpdate(Update update);
}
