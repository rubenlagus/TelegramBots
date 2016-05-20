package org.telegram.telegrambots.api.commands;

import org.telegram.telegrambots.BotLogger;
import org.telegram.telegrambots.TelegramApiException;
import org.telegram.telegrambots.api.methods.send.SendMessage;

/**
 * standard help command, which gets registered by default, to supply a list of all available commands
 *
 * @author tschulz
 */
public class HelpCommand extends Command {

    private static final String LOGTAG = "HELPCOMMAND";
    private final ICommandRegistry commandRegistry;

    public HelpCommand(ICommandRegistry commandRegistry, String botToken) {
        super("help", "Gives an overview over all Commands registered for this bot", botToken);
        this.commandRegistry = commandRegistry;
    }

    @Override
    void execute(String[] arguments, long chatId) {
        for (Command registeredCommand : commandRegistry.getRegisteredCommands()) {
            SendMessage sendMessage = new SendMessage();
            sendMessage.setChatId(chatId);
            sendMessage.enableHtml(true);
            sendMessage.setText("<b>" + registeredCommand.getCommandIdentifier() + "</b>\n" + registeredCommand.getDescription());

            try {
                sendMessage(sendMessage);
            } catch (TelegramApiException e) {
                BotLogger.error("Failed to send HelpMessage", LOGTAG, e);
            }
        }
    }
}
