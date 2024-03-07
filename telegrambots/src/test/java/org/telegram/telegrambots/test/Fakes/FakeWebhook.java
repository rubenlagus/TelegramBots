package org.telegram.telegrambots.test.Fakes;

import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.updates.SetWebhook;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.generics.WebhookBot;

/**
 * @author Ruben Bermudez
 * @version 1.0
 */
public class FakeWebhook implements WebhookBot {
    private BotApiMethod<?> returnValue;

    public void setReturnValue(BotApiMethod<?> returnValue) {
        this.returnValue = returnValue;
    }

    @Override
    public BotApiMethod<?> onWebhookUpdateReceived(Update update) {
        return returnValue;
    }

    @Override
    public String getBotUsername() {
        return null;
    }

    @Override
    public String getBotToken() {
        return "";
    }

    @Override
    public void setWebhook(SetWebhook setWebhook) throws TelegramApiException {

    }

    @Override
    public String getBotPath() {
        return "testbot";
    }
}
