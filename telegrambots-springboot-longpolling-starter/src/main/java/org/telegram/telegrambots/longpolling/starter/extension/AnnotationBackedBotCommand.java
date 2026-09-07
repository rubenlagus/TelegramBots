package org.telegram.telegrambots.longpolling.starter.extension;

import org.telegram.telegrambots.extensions.bots.commandbot.commands.IBotCommand;
import org.telegram.telegrambots.meta.api.objects.message.Message;
import org.telegram.telegrambots.meta.generics.TelegramClient;

/**
 * {@link IBotCommand} adapter backed by a user-defined {@link ICommand} bean and {@link Command} annotation metadata.
 * <p>
 * Command identifier and description are taken from annotation attributes, while message processing is delegated
 * to the wrapped {@link ICommand} implementation.
 *
 *  @author Petrov Makariy (makariyp)
 */
final class AnnotationBackedBotCommand implements IBotCommand {
    private final String command;
    private final String description;
    private final ICommand delegate;

    AnnotationBackedBotCommand(Command meta, ICommand delegate) {
        this.command = meta.command();
        this.description = meta.description();
        this.delegate = delegate;
    }

    @Override public String getCommandIdentifier() { return command; }
    @Override public String getDescription() { return description; }

    @Override
    public void processMessage(TelegramClient telegramClient, Message message, String[] arguments) {
        delegate.processMessage(telegramClient, message, arguments);
    }
}