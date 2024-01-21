package org.telegram.telegrambots.webhook.starter;

import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.telegram.telegrambots.meta.api.methods.botapimethods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.function.Function;

/**
 * @author Ruben Bermudez
 * @version 1.0
 */
@Data
@Builder
@RequiredArgsConstructor
public class SpringTelegramWebhookBot {
    private final String botPath;
    private final Function<Update, BotApiMethod<?>> updateHandler;
    private final Runnable setWebhook;
    private final Runnable deleteWebhook;

    public void runDeleteWebhook() {
        if (deleteWebhook != null) {
            deleteWebhook.run();
        }
    }

    public void runSetWebhook() {
        if (setWebhook != null) {
            setWebhook.run();
        }
    }

    public BotApiMethod<?> consumeUpdate(Update update) {
        if (updateHandler != null) {
            return updateHandler.apply(update);
        }
        return null;
    }
}
