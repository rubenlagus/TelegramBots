package org.telegram.telegrambots.test.Fakes;

import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.bots.DefaultBotOptions;
import org.telegram.telegrambots.meta.exceptions.TelegramApiRequestException;
import org.telegram.telegrambots.meta.generics.BotOptions;
import org.telegram.telegrambots.meta.generics.LongPollingBot;

/**
 * @author Ruben Bermudez
 * @version 1.0
 */
public class FakeLongPollingBot implements LongPollingBot
{
    @Override
    public void onUpdateReceived(Update update) {

    }

    @Override
    public String getBotUsername() {
        return "";
    }

    @Override
    public String getBotToken() {
        return "";
    }

    @Override
    public BotOptions getOptions() {
        return new DefaultBotOptions();
    }

    @Override
    public void clearWebhook() throws TelegramApiRequestException {

    }
}
