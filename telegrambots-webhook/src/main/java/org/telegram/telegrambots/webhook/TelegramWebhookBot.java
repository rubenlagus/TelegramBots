package org.telegram.telegrambots.webhook;

import org.telegram.telegrambots.meta.api.methods.botapimethods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;

public interface TelegramWebhookBot {
    void runDeleteWebhook();

    void runSetWebhook();

    BotApiMethod<?> consumeUpdate(Update update);

    String getBotPath();
}
