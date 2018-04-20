package org.telegram.telegrambots.bots;

import org.telegram.telegrambots.ApiContext;
import org.telegram.telegrambots.api.methods.updates.DeleteWebhook;
import org.telegram.telegrambots.exceptions.TelegramApiException;
import org.telegram.telegrambots.exceptions.TelegramApiRequestException;
import org.telegram.telegrambots.generics.LongPollingBot;

/**
 * @author Ruben Bermudez
 * @version 1.0
 * Base abstract class for a bot that will get updates using
 * <a href="https://core.telegram.org/bots/api#getupdates">long-polling</a> method
 */
public abstract class TelegramLongPollingBot extends DefaultAbsSender implements LongPollingBot {
    public TelegramLongPollingBot() {
        this(ApiContext.getInstance(DefaultBotOptions.class));
    }

    public TelegramLongPollingBot(DefaultBotOptions options) {
        super(options);
    }

    @Override
    public void clearWebhook() throws TelegramApiRequestException {
        try {
            boolean result = execute(new DeleteWebhook());
            if (!result) {
                throw new TelegramApiRequestException("Error removing old webhook");
            }
        } catch (TelegramApiException e) {
            throw new TelegramApiRequestException("Error removing old webhook", e);
        }
    }

    @Override
    public void onClosing() {
        exe.shutdown();
    }

}
