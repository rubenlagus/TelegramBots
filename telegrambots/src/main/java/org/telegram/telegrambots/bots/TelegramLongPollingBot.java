package org.telegram.telegrambots.bots;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.entity.BufferedHttpEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;
import org.telegram.telegrambots.ApiConstants;
import org.telegram.telegrambots.ApiContext;
import org.telegram.telegrambots.api.methods.updates.SetWebhook;
import org.telegram.telegrambots.exceptions.TelegramApiRequestException;
import org.telegram.telegrambots.generics.LongPollingBot;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * @author Ruben Bermudez
 * @version 1.0
 * @brief Base abstract class for a bot that will get updates using
 * <a href="https://core.telegram.org/bots/api#getupdates">long-polling</a> method
 * @date 14 of January of 2016
 */
public abstract class TelegramLongPollingBot extends DefaultAbsSender implements LongPollingBot {
    public TelegramLongPollingBot() {
        this(ApiContext.getInstance(DefaultBotOptions.class));
    }

    public TelegramLongPollingBot(DefaultBotOptions options) {
        super(options);
    }

    @Override
    public void clearWebhook() throws TelegramApiRequestException {
        try (CloseableHttpClient httpclient = HttpClientBuilder.create().setSSLHostnameVerifier(new NoopHostnameVerifier()).build()) {
            String url = getOptions().getBaseUrl() + getBotToken() + "/" + SetWebhook.PATH;
            HttpGet httpGet = new HttpGet(url);
            httpGet.setConfig(getOptions().getRequestConfig());
            try (CloseableHttpResponse response = httpclient.execute(httpGet)) {
                HttpEntity ht = response.getEntity();
                BufferedHttpEntity buf = new BufferedHttpEntity(ht);
                String responseContent = EntityUtils.toString(buf, StandardCharsets.UTF_8);
                JSONObject jsonObject = new JSONObject(responseContent);
                if (!jsonObject.getBoolean(ApiConstants.RESPONSE_FIELD_OK)) {
                    throw new TelegramApiRequestException("Error removing old webhook", jsonObject);
                }
            }
        } catch (JSONException e) {
            throw new TelegramApiRequestException("Error deserializing setWebhook method response", e);
        } catch (IOException e) {
            throw new TelegramApiRequestException("Error executing setWebook method", e);
        }
    }
}
