package org.telegram.telegrambots.starter;

import org.telegram.telegrambots.bots.DefaultBotOptions;
import org.telegram.telegrambots.bots.TelegramWebhookBot;
import org.telegram.telegrambots.meta.api.methods.updates.SetWebhook;

/**
 * @author Ruben Bermudez
 * @version 1.0
 */
public abstract class SpringWebhookBot extends TelegramWebhookBot {
    private SetWebhook setWebhook;

    public SpringWebhookBot(SetWebhook setWebhook) {
        super();
        this.setWebhook = setWebhook;
    }

    public SpringWebhookBot(DefaultBotOptions options, SetWebhook setWebhook) {
        super(options);
        this.setWebhook = setWebhook;
    }

    public SetWebhook getSetWebhook() {
        return setWebhook;
    }
}
