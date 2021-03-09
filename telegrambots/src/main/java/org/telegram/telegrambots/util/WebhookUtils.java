package org.telegram.telegrambots.util;

import com.google.common.base.Strings;
import org.apache.http.HttpEntity;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.telegram.telegrambots.Constants;
import org.telegram.telegrambots.bots.DefaultAbsSender;
import org.telegram.telegrambots.bots.DefaultBotOptions;
import org.telegram.telegrambots.facilities.TelegramHttpClientBuilder;
import org.telegram.telegrambots.meta.ApiConstants;
import org.telegram.telegrambots.meta.api.methods.updates.DeleteWebhook;
import org.telegram.telegrambots.meta.api.methods.updates.SetWebhook;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.exceptions.TelegramApiRequestException;
import org.telegram.telegrambots.meta.generics.WebhookBot;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

import static org.telegram.telegrambots.Constants.SOCKET_TIMEOUT;

public final class WebhookUtils {
  private static final ContentType TEXT_PLAIN_CONTENT_TYPE = ContentType.create("text/plain", StandardCharsets.UTF_8);

  private WebhookUtils() {

  }

  /**
   * Set webhook address to receive updates
   * @param bot Bot to set the webhook to
   * @param setWebhook SetSebhook object with webhook information
   * @throws TelegramApiRequestException If any issue executing the request
   *
   * @apiNote Telegram API parameters will be taken only from SetWebhook object
   * @apiNote Bot options will be fetched from Bot to set up the HTTP connection
   */
  public static void setWebhook(DefaultAbsSender bot, WebhookBot webhookBot, SetWebhook setWebhook) throws TelegramApiException {
    setWebhook.validate();

    DefaultBotOptions botOptions = bot.getOptions();
    try (CloseableHttpClient httpclient = TelegramHttpClientBuilder.build(botOptions)) {
      String requestUrl = bot.getBaseUrl() + SetWebhook.PATH;

      RequestConfig requestConfig = botOptions.getRequestConfig();
      if (requestConfig == null) {
        requestConfig = RequestConfig.copy(RequestConfig.custom().build())
                .setSocketTimeout(SOCKET_TIMEOUT)
                .setConnectTimeout(SOCKET_TIMEOUT)
                .setConnectionRequestTimeout(SOCKET_TIMEOUT).build();
      }

      HttpPost httppost = new HttpPost(requestUrl);
      httppost.setConfig(requestConfig);
      MultipartEntityBuilder builder = MultipartEntityBuilder.create();
      builder.addTextBody(SetWebhook.URL_FIELD, getBotUrl(setWebhook, webhookBot), TEXT_PLAIN_CONTENT_TYPE);
      if (setWebhook.getMaxConnections() != null) {
        builder.addTextBody(SetWebhook.MAXCONNECTIONS_FIELD, setWebhook.getMaxConnections().toString(), TEXT_PLAIN_CONTENT_TYPE);
      }
      if (setWebhook.getAllowedUpdates() != null) {
        builder.addTextBody(SetWebhook.ALLOWEDUPDATES_FIELD, new JSONArray(setWebhook.getAllowedUpdates()).toString(), TEXT_PLAIN_CONTENT_TYPE);
      }
      if (setWebhook.getIpAddress() != null) {
        builder.addTextBody(SetWebhook.IPADDRESS_FIELD, setWebhook.getIpAddress(), TEXT_PLAIN_CONTENT_TYPE);
      }
      if (setWebhook.getDropPendingUpdates() != null) {
        builder.addTextBody(SetWebhook.DROPPENDINGUPDATES_FIELD, setWebhook.getDropPendingUpdates().toString(), TEXT_PLAIN_CONTENT_TYPE);
      }
      if (setWebhook.getCertificate() != null) {
        InputFile webhookFile = setWebhook.getCertificate();
        if (webhookFile.getNewMediaFile() != null) {
          builder.addBinaryBody(SetWebhook.CERTIFICATE_FIELD, webhookFile.getNewMediaFile(), ContentType.TEXT_PLAIN, webhookFile.getMediaName());
        } else if (webhookFile.getNewMediaStream() != null) {
          builder.addBinaryBody(SetWebhook.CERTIFICATE_FIELD, webhookFile.getNewMediaStream(), ContentType.TEXT_PLAIN, webhookFile.getMediaName());
        }
      }

      HttpEntity multipart = builder.build();
      httppost.setEntity(multipart);
      try (CloseableHttpResponse response = httpclient.execute(httppost, botOptions.getHttpContext())) {
        String responseContent = EntityUtils.toString(response.getEntity(), StandardCharsets.UTF_8);
        Boolean result = setWebhook.deserializeResponse(responseContent);
        if (!result) {
          throw new TelegramApiRequestException("Error setting webhook:" + responseContent);
        }
      }
    } catch (JSONException e) {
      throw new TelegramApiRequestException("Error deserializing setWebhook method response", e);
    } catch (IOException e) {
      throw new TelegramApiRequestException("Error executing setWebook method", e);
    }
  }

  /**
   * @deprecated Use {{@link #setWebhook(DefaultAbsSender, WebhookBot, SetWebhook)}} instead
   */
  @Deprecated
  public static void setWebhook(DefaultAbsSender bot, WebhookBot webhookBot, String url, String publicCertificatePath) throws TelegramApiRequestException {
    DefaultBotOptions botOptions = bot.getOptions();

    try (CloseableHttpClient httpclient = TelegramHttpClientBuilder.build(botOptions)) {
      String requestUrl = bot.getBaseUrl() + SetWebhook.PATH;

      RequestConfig requestConfig = botOptions.getRequestConfig();
      if (requestConfig == null) {
        requestConfig = RequestConfig.copy(RequestConfig.custom().build())
                .setSocketTimeout(SOCKET_TIMEOUT)
                .setConnectTimeout(SOCKET_TIMEOUT)
                .setConnectionRequestTimeout(SOCKET_TIMEOUT).build();
      }

      HttpPost httppost = new HttpPost(requestUrl);
      httppost.setConfig(requestConfig);
      MultipartEntityBuilder builder = MultipartEntityBuilder.create();
      builder.addTextBody(SetWebhook.URL_FIELD, getBotUrl(url, webhookBot.getBotPath()));
      if (botOptions.getMaxWebhookConnections() != null) {
        builder.addTextBody(SetWebhook.MAXCONNECTIONS_FIELD, botOptions.getMaxWebhookConnections().toString());
      }
      if (botOptions.getAllowedUpdates() != null) {
        builder.addTextBody(SetWebhook.ALLOWEDUPDATES_FIELD, new JSONArray(botOptions.getAllowedUpdates()).toString());
      }
      if (botOptions.getAllowedUpdates() != null) {
        builder.addTextBody(SetWebhook.ALLOWEDUPDATES_FIELD, new JSONArray(botOptions.getAllowedUpdates()).toString());
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
        String responseContent = EntityUtils.toString(response.getEntity(), StandardCharsets.UTF_8);
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

  private static String getBotUrl(SetWebhook setWebhook, WebhookBot webhookBot) {
    String externalUrl = setWebhook.getUrl();
    return getBotUrl(externalUrl, webhookBot.getBotPath());
  }

  private static String getBotUrl(String externalUrl, String botPath) {
    if (!externalUrl.endsWith("/")) {
      externalUrl += "/";
    }
    externalUrl += Constants.WEBHOOK_URL_PATH;
    if (!Strings.isNullOrEmpty(botPath)) {
      if (!botPath.startsWith("/")) {
        externalUrl += "/";
      }
      externalUrl += botPath;
    }

    return externalUrl;
  }
}
