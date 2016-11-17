package org.telegram.telegrambots;

import org.telegram.telegrambots.exceptions.TelegramApiRequestException;
import org.telegram.telegrambots.generics.BotSession;
import org.telegram.telegrambots.generics.LongPollingBot;
import org.telegram.telegrambots.generics.Webhook;
import org.telegram.telegrambots.generics.WebhookBot;

import java.text.MessageFormat;

/**
 * @author Ruben Bermudez
 * @version 1.0
 * @brief Bots manager
 * @date 14 of January of 2016
 */
public class TelegramBotsApi {
    private static final String webhookUrlFormat = "{0}callback/";
    private boolean useWebhook; ///< True to enable webhook usage
    private Webhook webhook; ///< Webhook instance
    private String extrenalUrl; ///< External url of the bots
    private String pathToCertificate; ///< Path to public key certificate

    /**
     *
     */
    public TelegramBotsApi() {
    }

    /**
     *
     * @param keyStore KeyStore for the server
     * @param keyStorePassword Key store password for the server
     * @param externalUrl External base url for the webhook
     * @param internalUrl Internal base url for the webhook
     */
    public TelegramBotsApi(String keyStore, String keyStorePassword, String externalUrl, String internalUrl) throws TelegramApiRequestException {
        if (externalUrl == null || externalUrl.isEmpty()) {
            throw new TelegramApiRequestException("Parameter externalUrl can not be null or empty");
        }
        if (internalUrl == null || internalUrl.isEmpty()) {
            throw new TelegramApiRequestException("Parameter internalUrl can not be null or empty");
        }

        this.useWebhook = true;
        this.extrenalUrl = fixExternalUrl(externalUrl);
        webhook = ApiContext.getInstance(Webhook.class);
        webhook.setInternalUrl(internalUrl);
        webhook.setKeyStore(keyStore, keyStorePassword);
        webhook.startServer();
    }

    /**
     *
     * @param keyStore KeyStore for the server
     * @param keyStorePassword Key store password for the server
     * @param externalUrl External base url for the webhook
     * @param internalUrl Internal base url for the webhook
     * @param pathToCertificate Full path until .pem public certificate keys
     */
    public TelegramBotsApi(String keyStore, String keyStorePassword, String externalUrl, String internalUrl, String pathToCertificate) throws TelegramApiRequestException {
        if (externalUrl == null || externalUrl.isEmpty()) {
            throw new TelegramApiRequestException("Parameter externalUrl can not be null or empty");
        }
        if (internalUrl == null || internalUrl.isEmpty()) {
            throw new TelegramApiRequestException("Parameter internalUrl can not be null or empty");
        }
        this.useWebhook = true;
        this.extrenalUrl = fixExternalUrl(externalUrl);
        this.pathToCertificate = pathToCertificate;
        webhook = ApiContext.getInstance(Webhook.class);
        webhook.setInternalUrl(internalUrl);
        webhook.setKeyStore(keyStore, keyStorePassword);
        webhook.startServer();
    }

    /**
     * Register a bot. The Bot Session is started immediately, and may be disconnected by calling close.
     * @param bot the bot to register
     */
    public BotSession registerBot(LongPollingBot bot) throws TelegramApiRequestException {
        bot.clearWebhook();
        BotSession session = ApiContext.getInstance(BotSession.class);
        session.setToken(bot.getBotToken());
        session.setOptions(bot.getOptions());
        session.setCallback(bot);
        session.start();
        return session;
    }

    /**
     * Register a bot in the api that will receive updates using webhook method
     * @param bot Bot to register
     */
    public void registerBot(WebhookBot bot) throws TelegramApiRequestException {
        if (useWebhook) {
            webhook.registerWebhook(bot);
            bot.setWebhook(extrenalUrl + bot.getBotPath(), pathToCertificate);
        }
    }

    private static String fixExternalUrl(String externalUrl) {
        if (externalUrl != null && !externalUrl.endsWith("/")) {
            externalUrl = externalUrl + "/";
        }
        return MessageFormat.format(webhookUrlFormat, externalUrl);
    }
}
