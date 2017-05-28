package org.telegram.telegrambots.bots;

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
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.telegram.telegrambots.ApiConstants;
import org.telegram.telegrambots.ApiContext;
import org.telegram.telegrambots.api.methods.updates.SetWebhook;
import org.telegram.telegrambots.exceptions.TelegramApiRequestException;
import org.telegram.telegrambots.generics.WebhookBot;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * @author Ruben Bermudez
 * @version 1.0
 * @brief Base abstract class for a bot that will receive updates using a
 * <a href="https://core.telegram.org/bots/api#setwebhook">webhook</a>
 * @date 14 of January of 2016
 */
public abstract class TelegramWebhookBot extends DefaultAbsSender implements WebhookBot {
    private final DefaultBotOptions botOptions;

    public TelegramWebhookBot() {
        this(ApiContext.getInstance(DefaultBotOptions.class));
    }

    public TelegramWebhookBot(DefaultBotOptions options) {
        super(options);
        this.botOptions = options;
    }

    @Override
    public void setWebhook(String url, String publicCertificatePath) throws TelegramApiRequestException {
        try (CloseableHttpClient httpclient = HttpClientBuilder.create().setSSLHostnameVerifier(new NoopHostnameVerifier()).build()) {
            String requestUrl = getBaseUrl() + SetWebhook.PATH;

            HttpPost httppost = new HttpPost(requestUrl);
            httppost.setConfig(botOptions.getRequestConfig());
            MultipartEntityBuilder builder = MultipartEntityBuilder.create();
            builder.addTextBody(SetWebhook.URL_FIELD, url);
            if (botOptions.getMaxWebhookConnections() != null) {
                builder.addTextBody(SetWebhook.MAXCONNECTIONS_FIELD, botOptions.getMaxWebhookConnections().toString());
            }
            if (botOptions.getAllowedUpdates() != null) {
                builder.addTextBody(SetWebhook.ALLOWEDUPDATES_FIELD, new JSONArray(botOptions.getMaxWebhookConnections()).toString());
            }
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
                if (!jsonObject.getBoolean(ApiConstants.RESPONSE_FIELD_OK)) {
                    throw new TelegramApiRequestException("Error setting webhook", jsonObject);
                }
            }
        } catch (JSONException e) {
            throw new TelegramApiRequestException("Error deserializing setWebhook method response", e);
        } catch (IOException e) {
            throw new TelegramApiRequestException("Error executing setWebook method", e);
        }

    }
}
