package org.telegram.telegrambots.meta;

import com.google.common.base.Strings;
import org.telegram.telegrambots.meta.api.methods.updates.SetWebhook;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.generics.BotSession;
import org.telegram.telegrambots.meta.generics.LongPollingBot;
import org.telegram.telegrambots.meta.generics.TelegramBot;
import org.telegram.telegrambots.meta.generics.Webhook;
import org.telegram.telegrambots.meta.generics.WebhookBot;

import java.lang.reflect.InvocationTargetException;

/**
 * @author Ruben Bermudez
 * @version 1.0
 * Bots manager
 */
public class TelegramBotsApi {
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
        if (bot == null) {
            throw new TelegramApiException("Parameter bot can not be null");
        }
        if (!validateBotUsernameAndToken(bot)) {
            throw new TelegramApiException("Bot token and username can't be empty");
        }
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
     *
     * @apiNote The webhook url will be appended with `/callback/bot.getBotPath()` at the end
     */
    public void registerBot(WebhookBot bot, SetWebhook setWebhook) throws TelegramApiException {
        if (setWebhook == null) {
            throw new TelegramApiException("Parameter setWebhook can not be null");
        }
        if (useWebhook) {
            if (webhook == null) {
                throw new TelegramApiException("This instance doesn't support Webhook bot, use correct constructor");
            }
            if (!validateBotUsernameAndToken(bot)) {
                throw new TelegramApiException("Bot token and username can't be empty");
            }
            bot.onRegister();
            webhook.registerWebhook(bot);
            bot.setWebhook(setWebhook);
        }
    }

    /**
     * Checks that the username and token are presented
     * @param telegramBot bot to register
     * @return False if username or token are empty or null, true otherwise
     */
    private boolean validateBotUsernameAndToken(TelegramBot telegramBot) {
        return !Strings.isNullOrEmpty(telegramBot.getBotToken()) &&
                !Strings.isNullOrEmpty(telegramBot.getBotUsername());
    }
}
