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
    private final SetWebhook setWebhook;

    /**
     * If this is used getBotToken has to be overridden in order to return the bot token!
     * @deprecated Overwriting the getBotToken() method is deprecated. Use the constructor instead
     */
    @Deprecated
    public SpringWebhookBot(SetWebhook setWebhook) {
        super();
        this.setWebhook = setWebhook;
    }

    /**
     * If this is used getBotToken has to be overridden in order to return the bot token!
     * @deprecated Overwriting the getBotToken() method is deprecated. Use the constructor instead
     */
    @Deprecated
    public SpringWebhookBot(DefaultBotOptions options, SetWebhook setWebhook) {
        super(options);
        this.setWebhook = setWebhook;
    }

    public SpringWebhookBot(SetWebhook setWebhook, String botToken) {
        super(botToken);
        this.setWebhook = setWebhook;
    }

    public SpringWebhookBot(DefaultBotOptions options, SetWebhook setWebhook, String botToken) {
        super(options, botToken);
        this.setWebhook = setWebhook;
    }

    public SetWebhook getSetWebhook() {
        return setWebhook;
    }

    public static class TestSpringWebhookBot extends SpringWebhookBot {

        public TestSpringWebhookBot(SetWebhook setWebhook) {
            super(setWebhook, null);
        }

        public TestSpringWebhookBot(DefaultBotOptions options, SetWebhook setWebhook) {
            super(options, setWebhook, null);
        }

        @Override
        public String getBotUsername() {
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
