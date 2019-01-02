package org.telegram.telegrambots.util;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.BufferedHttpEntity;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.telegram.telegrambots.meta.ApiConstants;
import org.telegram.telegrambots.meta.api.methods.updates.DeleteWebhook;
import org.telegram.telegrambots.bots.DefaultAbsSender;
import org.telegram.telegrambots.bots.DefaultBotOptions;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.facilities.TelegramHttpClientBuilder;
import org.telegram.telegrambots.meta.api.methods.updates.SetWebhook;
import org.telegram.telegrambots.meta.exceptions.TelegramApiRequestException;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

public final class WebhookUtils {
  private WebhookUtils() {

  }

  public static void setWebhook(DefaultAbsSender bot, String url, String publicCertificatePath) throws TelegramApiRequestException {
    DefaultBotOptions botOptions = bot.getOptions();

    try (CloseableHttpClient httpclient = TelegramHttpClientBuilder.build(botOptions)) {
      String requestUrl = bot.getBaseUrl() + SetWebhook.PATH;

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
      try (CloseableHttpResponse response = httpclient.execute(httppost, botOptions.getHttpContext())) {
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

  public static void clearWebhook(DefaultAbsSender bot) throws TelegramApiRequestException {
    try {
      boolean result = bot.execute(new DeleteWebhook());
      if (!result) {
        throw new TelegramApiRequestException("Error removing old webhook");
      }
    } catch (TelegramApiException e) {
      throw new TelegramApiRequestException("Error removing old webhook", e);
    }
  }
}
