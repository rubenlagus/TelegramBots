package org.telegram.telegrambots;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.entity.BufferedHttpEntity;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;
import org.telegram.telegrambots.api.methods.updates.SetWebhook;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.bots.TelegramWebhookBot;
import org.telegram.telegrambots.updatesreceivers.BotSession;
import org.telegram.telegrambots.updatesreceivers.Webhook;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.text.MessageFormat;

import static org.telegram.telegrambots.Constants.ERRORCODEFIELD;
import static org.telegram.telegrambots.Constants.ERRORDESCRIPTIONFIELD;

/**
 * @author Ruben Bermudez
 * @version 1.0
 * @brief Bots manager
 * @date 14 of January of 2016
 */
public class TelegramBotsApi {
    private static final String webhookUrlFormat = "{0}callback/";
    private boolean useWebhook; ///<
    private Webhook webhook; ///<
    private String extrenalUrl; ///<
    private String pathToCertificate; ///<

    /**
     *
     */
    public TelegramBotsApi() {
    }

    /**
     *
     * @param keyStore
     * @param keyStorePassword
     * @param externalUrl
     * @param internalUrl
     */
    public TelegramBotsApi(String keyStore, String keyStorePassword, String externalUrl, String internalUrl) throws TelegramApiException {
        if (externalUrl == null || externalUrl.isEmpty()) {
            throw new TelegramApiException("Parameter externalUrl can not be null or empty");
        }
        if (internalUrl == null || internalUrl.isEmpty()) {
            throw new TelegramApiException("Parameter internalUrl can not be null or empty");
        }

        this.useWebhook = true;
        this.extrenalUrl = fixExternalUrl(externalUrl);
        webhook = new Webhook(keyStore, keyStorePassword, internalUrl);
        webhook.startServer();
    }

    /**
     *
     * @param keyStore
     * @param keyStorePassword
     * @param externalUrl
     * @param internalUrl
     * @param pathToCertificate Full path until .pem public certificate keys
     */
    public TelegramBotsApi(String keyStore, String keyStorePassword, String externalUrl, String internalUrl, String pathToCertificate) throws TelegramApiException {
        if (externalUrl == null || externalUrl.isEmpty()) {
            throw new TelegramApiException("Parameter externalUrl can not be null or empty");
        }
        if (internalUrl == null || internalUrl.isEmpty()) {
            throw new TelegramApiException("Parameter internalUrl can not be null or empty");
        }
        this.useWebhook = true;
        this.extrenalUrl = fixExternalUrl(externalUrl);
        this.pathToCertificate = pathToCertificate;
        webhook = new Webhook(keyStore, keyStorePassword, internalUrl);
        webhook.startServer();
    }

    /**
     *
     * @param externalUrl
     * @return
     */
    private static String fixExternalUrl(String externalUrl) {
        if (externalUrl != null && !externalUrl.endsWith("/")) {
            externalUrl = externalUrl + "/";
        }
        return MessageFormat.format(webhookUrlFormat, externalUrl);
    }

    /**
     *
     * @param webHookURL
     * @param botToken
     * @param publicCertificatePath
     * @throws TelegramApiException
     */
    private static void setWebhook(String webHookURL, String botToken, String publicCertificatePath) throws TelegramApiException {
        try (CloseableHttpClient httpclient = HttpClientBuilder.create().setSSLHostnameVerifier(new NoopHostnameVerifier()).build()) {
            String url = Constants.BASEURL + botToken + "/" + SetWebhook.PATH;

            HttpPost httppost = new HttpPost(url);
            MultipartEntityBuilder builder = MultipartEntityBuilder.create();
            builder.addTextBody(SetWebhook.URL_FIELD, webHookURL);
            if (publicCertificatePath != null) {
                File certificate = new File(publicCertificatePath);
                if (certificate.exists()) {
                    builder.addBinaryBody(SetWebhook.CERTIFICATE_FIELD, certificate, ContentType.TEXT_PLAIN, certificate.getName());
                }
            }
            HttpEntity multipart = builder.build();
            httppost.setEntity(multipart);
            try (CloseableHttpResponse response = httpclient.execute(httppost)) {
                HttpEntity ht = response.getEntity();
                BufferedHttpEntity buf = new BufferedHttpEntity(ht);
                String responseContent = EntityUtils.toString(buf, StandardCharsets.UTF_8);
                JSONObject jsonObject = new JSONObject(responseContent);
                if (!jsonObject.getBoolean(Constants.RESPONSEFIELDOK)) {
                    throw new TelegramApiException(webHookURL == null ? "Error removing old webhook" : "Error setting webhook", jsonObject.getString(ERRORDESCRIPTIONFIELD), jsonObject.getInt(ERRORCODEFIELD));
                }
            }
        } catch (JSONException e) {
            throw new TelegramApiException("Error deserializing setWebhook method response", e);
        } catch (IOException e) {
            throw new TelegramApiException("Error executing setWebook method", e);
        }
    }

    /**
     * Register a bot. The Bot Session is started immediately, and may be disconnected by calling close.
     * @param bot the bot to register
     */
    public BotSession registerBot(TelegramLongPollingBot bot) throws TelegramApiException {
        setWebhook(bot.getBotToken(), null);
        return new BotSession(bot.getBotToken(), bot, bot.getOptions());
    }

    /**
     *
     * @param bot
     */
    public void registerBot(TelegramWebhookBot bot) throws TelegramApiException {
        if (useWebhook) {
            webhook.registerWebhook(bot);
            setWebhook(bot.getBotToken(), bot.getBotPath());
        }
    }

    /**
     *
     * @param botToken
     */
    private void setWebhook(String botToken, String urlPath) throws TelegramApiException {
        if (botToken == null) {
            throw new TelegramApiException("Parameter botToken can not be null");
        }
        String completeExternalUrl = urlPath == null ? "" : extrenalUrl + urlPath;
        setWebhook(completeExternalUrl, botToken, pathToCertificate);
    }
}
