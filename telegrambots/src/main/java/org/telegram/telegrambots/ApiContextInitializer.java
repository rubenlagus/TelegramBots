package org.telegram.telegrambots;

import org.telegram.telegrambots.generics.BotSession;
import org.telegram.telegrambots.generics.Webhook;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;
import org.telegram.telegrambots.updatesreceivers.DefaultWebhook;

/**
 * @author Ruben Bermudez
 * @version 1.0
 * @brief TODO
 * @date 29 of October of 2016
 */
public final class ApiContextInitializer {
    private ApiContextInitializer() {
    }

    public static void init() {
        ApiContext.register(BotSession.class, DefaultBotSession.class);
        ApiContext.register(Webhook.class, DefaultWebhook.class);
    }
}
