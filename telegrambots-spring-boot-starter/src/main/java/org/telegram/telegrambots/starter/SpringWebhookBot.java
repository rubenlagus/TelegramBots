package org.telegram.telegrambots.starter;

import org.telegram.telegrambots.bots.DefaultBotOptions;
import org.telegram.telegrambots.bots.TelegramWebhookBot;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.updates.SetWebhook;
import org.telegram.telegrambots.meta.api.objects.Update;

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

    public class TestSpringWebhookBot extends SpringWebhookBot {

        public TestSpringWebhookBot(SetWebhook setWebhook) {
            super(setWebhook);
        }

        public TestSpringWebhookBot(DefaultBotOptions options, SetWebhook setWebhook) {
            super(options, setWebhook);
        }

        @Override
        public String getBotUsername() {
            return null;
        }

        @Override
        public String getBotToken() {
            return null;
        }

        @Override
        public BotApiMethod onWebhookUpdateReceived(Update update) {
            return null;
        }

        @Override
        public String getBotPath() {
            return null;
        }
    }
}
