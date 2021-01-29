package org.telegram.telegrambots.meta;

import org.telegram.telegrambots.meta.api.methods.updates.SetWebhook;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.generics.BotSession;
import org.telegram.telegrambots.meta.generics.LongPollingBot;
import org.telegram.telegrambots.meta.generics.Webhook;
import org.telegram.telegrambots.meta.generics.WebhookBot;

import java.lang.reflect.InvocationTargetException;

/**
 * @author Ruben Bermudez
 * @version 1.0
 * Bots manager
 */
public class TelegramBotsApi {
    private static final String webhookUrlFormat = "{0}callback/";

    Class<? extends BotSession> botSessionClass;
    private boolean useWebhook; ///< True to enable webhook usage
    private Webhook webhook; ///< Webhook instance

    /**
     *
     */
    public TelegramBotsApi(Class<? extends BotSession> botSessionClass) throws TelegramApiException {
        if (botSessionClass == null) {
            throw new TelegramApiException("Parameter botSessionClass can not be null or empty");
        }
        this.botSessionClass = botSessionClass;
    }

    /**
     * Creates an HTTP server to receive webhook request
     * @param webhook Webhook class
     *
     * @implSpec This option may require externally handled HTTPS support (i.e. via proxy)
     */
    public TelegramBotsApi(Class<? extends BotSession> botSessionClass, Webhook webhook) throws TelegramApiException {
        if (botSessionClass == null) {
            throw new TelegramApiException("Parameter botSessionClass can not be null or empty");
        }
        if (webhook == null) {
            throw new TelegramApiException("Parameter webhook can not be null or empty");
        }
        this.botSessionClass = botSessionClass;
        this.useWebhook = true;
        this.webhook = webhook;
        this.webhook.startServer();
    }

    /**
     * Register a bot. The Bot Session is started immediately, and may be disconnected by calling close.
     * @param bot the bot to register
     */
    public BotSession registerBot(LongPollingBot bot) throws TelegramApiException {
        bot.onRegister();
        bot.clearWebhook();
        BotSession session;
        try {
            session = botSessionClass.getConstructor().newInstance();
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException | InstantiationException e) {
            throw new TelegramApiException(e);
        }
        session.setToken(bot.getBotToken());
        session.setOptions(bot.getOptions());
        session.setCallback(bot);
        session.start();
        return session;
    }

    /**
     * Register a bot in the api that will receive updates using webhook method
     * @param bot Bot to register
     * @param setWebhook Set webhook request to initialize the bot
     */
    public void registerBot(WebhookBot bot, SetWebhook setWebhook) throws TelegramApiException {
        if (setWebhook == null) {
            throw new TelegramApiException("Parameter setWebhook can not be null or empty");
        }
        if (useWebhook) {
            if (webhook == null) {
                throw new TelegramApiException("This instance doesn't support Webhook bot, use correct constructor");
            }
            bot.onRegister();
            webhook.registerWebhook(bot);
            bot.setWebhook(setWebhook);
        }
    }
}
