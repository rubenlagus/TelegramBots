package org.telegram.telegrambots;

import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.client.config.RequestConfig;
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
import org.telegram.telegrambots.api.Constants;
import org.telegram.telegrambots.api.methods.SetWebhook;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.bots.TelegramWebhookBot;
import org.telegram.telegrambots.updatesreceivers.UpdatesThread;
import org.telegram.telegrambots.updatesreceivers.Webhook;

import java.io.File;
import java.io.IOException;

/**
 * @author Ruben Bermudez
 * @version 1.0
 * @brief TODO
 * @date 14 of January of 2016
 */
public class TelegramBotsApi {
    private final boolean useWebhook; ///<
    private final Webhook webhook; ///<
    private final String extrenalUrl; ///<
    private final String pathToCertificate; ///<
    private final String publicCertificateName; ///<

    /**
     *
     */
    public TelegramBotsApi() {
        this.useWebhook = false;
        webhook = null;
        extrenalUrl = null;
        this.pathToCertificate = null;
        this.publicCertificateName = null;
    }

    /**
     *
     * @param keyStore
     * @param keyStorePassword
     * @param externalUrl
     * @param internalUrl
     */
    public TelegramBotsApi(String keyStore, String keyStorePassword, String externalUrl, String internalUrl) throws TelegramApiException {
        this.useWebhook = true;
        this.extrenalUrl = fixExternalUrl(externalUrl);
        this.pathToCertificate = null;
        this.publicCertificateName = null;
        webhook = new Webhook(keyStore, keyStorePassword, internalUrl);
        webhook.startServer();
    }

    /**
     *
     * @param keyStore
     * @param keyStorePassword
     * @param externalUrl
     * @param internalUrl
     * @param pathToCertificate
     * @param publicCertificateName
     */
    public TelegramBotsApi(String keyStore, String keyStorePassword, String externalUrl, String internalUrl, String pathToCertificate, String publicCertificateName) throws TelegramApiException {
        this.useWebhook = true;
        this.extrenalUrl = fixExternalUrl(externalUrl);
        this.pathToCertificate = pathToCertificate;
        this.publicCertificateName = publicCertificateName;
        webhook = new Webhook(keyStore, keyStorePassword, internalUrl);
        webhook.startServer();
    }

    /**
     *
     * @param bot
     */
    public void registerBot(TelegramLongPollingBot bot) throws TelegramApiException {
        setWebhook(bot.getBotToken());
        new UpdatesThread(bot.getBotToken(), bot);
    }

    /**
     *
     * @param bot
     */
    public void registerBot(TelegramWebhookBot bot) throws TelegramApiException {
        if (useWebhook) {
            webhook.registerWebhook(bot);
            setWebhook(bot.getBotToken());
        }
    }

    /**
     *
     * @param botToken
     */
    private void setWebhook(String botToken) throws TelegramApiException {
        if (botToken == null) {
            throw new TelegramApiException("Parameter botToken can not be null");
        }
        setWebhook(extrenalUrl == null ? "" : extrenalUrl, botToken, pathToCertificate, publicCertificateName);
    }

    /**
     *
     * @param externalUrl
     * @return
     */
    private static String fixExternalUrl(String externalUrl) {
        if (!externalUrl.endsWith("/")) {
            externalUrl = externalUrl + "/";
        }
        return externalUrl;
    }

    /**
     *
     * @param webHookURL
     * @param botToken
     * @param publicCertificatePath
     * @param publicCertificateName
     * @throws TelegramApiException
     */
    private static void setWebhook(String webHookURL, String botToken, String publicCertificatePath, String publicCertificateName) throws TelegramApiException {
        try {
            CloseableHttpClient httpclient = HttpClientBuilder.create().setSSLHostnameVerifier(new NoopHostnameVerifier()).build();
            String url = Constants.BASEURL + botToken + "/" + SetWebhook.PATH;

            HttpPost httppost = new HttpPost(url);
            MultipartEntityBuilder builder = MultipartEntityBuilder.create();
            builder.addTextBody(SetWebhook.URL_FIELD, webHookURL);
            if (publicCertificatePath != null) {
                builder.addBinaryBody(SetWebhook.CERTIFICATE_FIELD, new File(publicCertificatePath), ContentType.APPLICATION_OCTET_STREAM, publicCertificateName);
            }
            HttpEntity multipart = builder.build();
            httppost.setEntity(multipart);
            CloseableHttpResponse response = httpclient.execute(httppost);
            HttpEntity ht = response.getEntity();
            BufferedHttpEntity buf = new BufferedHttpEntity(ht);
            String responseContent = EntityUtils.toString(buf, "UTF-8");
            JSONObject jsonObject = new JSONObject(responseContent);
            if (!jsonObject.getBoolean("ok")) {
                throw new TelegramApiException(webHookURL == null ? "Error removing old webhook" : "Error setting webhook", responseContent);
            }
        } catch (JSONException e) {
            throw new TelegramApiException("Error deserializing setWebhook method response", e);
        } catch (IOException e) {
            throw new TelegramApiException("Error executing setWebook method", e);
        }
    }
}
