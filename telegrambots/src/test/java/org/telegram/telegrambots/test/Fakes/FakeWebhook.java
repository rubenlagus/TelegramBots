package org.telegram.telegrambots.test.Fakes;

import org.telegram.telegrambots.api.methods.BotApiMethod;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.exceptions.TelegramApiRequestException;
import org.telegram.telegrambots.generics.WebhookBot;

/**
 * @author Ruben Bermudez
 * @version 1.0
 */
public class FakeWebhook implements WebhookBot {
    private BotApiMethod returnValue;

    public void setReturnValue(BotApiMethod returnValue) {
        this.returnValue = returnValue;
    }

    @Override
    public BotApiMethod onWebhookUpdateReceived(Update update) {
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
    public void setWebhook(String url, String publicCertificatePath) throws TelegramApiRequestException {

    }

    @Override
    public String getBotPath() {
        return "testbot";
    }
}
