package org.telegram.telegrambots.test.Fakes;

import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.bots.DefaultBotOptions;
import org.telegram.telegrambots.bots.TelegramBatchLongPollingBot;
import org.telegram.telegrambots.exceptions.TelegramApiRequestException;
import org.telegram.telegrambots.generics.BotOptions;
import org.telegram.telegrambots.generics.LongPollingBot;

import java.util.List;

public class FakeBatchLongPollingBot extends TelegramBatchLongPollingBot
{

    @Override
    public void onUpdatesReceived(List<Update> updates) {

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
    public void clearWebhook() throws TelegramApiRequestException {

    }
}
