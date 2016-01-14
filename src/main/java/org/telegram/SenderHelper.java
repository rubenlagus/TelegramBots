package org.telegram;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.entity.BufferedHttpEntity;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;
import org.telegram.api.methods.*;
import org.telegram.services.BotLogger;
import org.telegram.updateshandlers.SentCallback;

import java.io.File;
import java.io.IOException;
import java.io.InvalidObjectException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author Ruben Bermudez
 * @version 1.0
 * @brief Send Helper
 * @date 20 of June of 2015
 */
public class SenderHelper {
    private static final  String LOGTAG = "SENDERHELPER";
    private static final ExecutorService exe = Executors.newSingleThreadExecutor();

    public static void SendDocument(SendDocument sendDocument, String botToken) {
        try {
            CloseableHttpClient httpClient = HttpClients.createDefault();
            String url = Constants.BASEURL + botToken + "/" + SendDocument.PATH;
            HttpPost httppost = new HttpPost(url);

            if (sendDocument.isNewDocument()) {
                MultipartEntityBuilder builder = MultipartEntityBuilder.create();
                builder.addTextBody(SendDocument.CHATID_FIELD, sendDocument.getChatId());
                builder.addBinaryBody(SendDocument.DOCUMENT_FIELD, new File(sendDocument.getDocument()), ContentType.APPLICATION_OCTET_STREAM, sendDocument.getDocumentName());
                if (sendDocument.getReplayMarkup() != null) {
                    builder.addTextBody(SendDocument.REPLYMARKUP_FIELD, sendDocument.getReplayMarkup().toJson().toString());
                }
                if (sendDocument.getReplayToMessageId() != null) {
                    builder.addTextBody(SendDocument.REPLYTOMESSAGEID_FIELD, sendDocument.getReplayToMessageId().toString());
                }
                HttpEntity multipart = builder.build();
                httppost.setEntity(multipart);
            } else {
                List<NameValuePair> nameValuePairs = new ArrayList<>();
                nameValuePairs.add(new BasicNameValuePair(SendDocument.CHATID_FIELD, sendDocument.getChatId()));
                nameValuePairs.add(new BasicNameValuePair(SendDocument.DOCUMENT_FIELD, sendDocument.getDocument()));
                if (sendDocument.getReplayMarkup() != null) {
                    nameValuePairs.add(new BasicNameValuePair(SendDocument.REPLYMARKUP_FIELD, sendDocument.getReplayMarkup().toString()));
                }
                if (sendDocument.getReplayToMessageId() != null) {
                    nameValuePairs.add(new BasicNameValuePair(SendDocument.REPLYTOMESSAGEID_FIELD, sendDocument.getReplayToMessageId().toString()));
                }
                httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs, "UTF-8"));
            }

            CloseableHttpResponse response = httpClient.execute(httppost);
        } catch (IOException e) {
            BotLogger.error(LOGTAG, e);
        } finally {
            if (sendDocument.isNewDocument()) {
                File fileToDelete = new File(sendDocument.getDocument());
                fileToDelete.delete();
            }
        }
    }

    public static void SendPhoto(SendPhoto sendPhoto, String botToken) {
        try {
            CloseableHttpClient httpClient = HttpClients.createDefault();
            String url = Constants.BASEURL + botToken + "/" + SendPhoto.PATH;
            HttpPost httppost = new HttpPost(url);

            if (sendPhoto.isNewPhoto()) {
                MultipartEntityBuilder builder = MultipartEntityBuilder.create();
                builder.addTextBody(SendPhoto.CHATID_FIELD, sendPhoto.getChatId());
                builder.addBinaryBody(SendPhoto.PHOTO_FIELD, new File(sendPhoto.getPhoto()), ContentType.APPLICATION_OCTET_STREAM, sendPhoto.getPhotoName());
                if (sendPhoto.getReplayMarkup() != null) {
                    builder.addTextBody(SendPhoto.REPLYMARKUP_FIELD, sendPhoto.getReplayMarkup().toJson().toString());
                }
                if (sendPhoto.getReplayToMessageId() != null) {
                    builder.addTextBody(SendPhoto.REPLYTOMESSAGEID_FIELD, sendPhoto.getReplayToMessageId().toString());
                }
                if (sendPhoto.getCaption() != null) {
                    builder.addTextBody(SendPhoto.CAPTION_FIELD, sendPhoto.getCaption());
                }
                HttpEntity multipart = builder.build();
                httppost.setEntity(multipart);
            } else {
                List<NameValuePair> nameValuePairs = new ArrayList<>();
                nameValuePairs.add(new BasicNameValuePair(SendPhoto.CHATID_FIELD, sendPhoto.getChatId()));
                nameValuePairs.add(new BasicNameValuePair(SendPhoto.PHOTO_FIELD, sendPhoto.getPhoto()));
                if (sendPhoto.getReplayMarkup() != null) {
                    nameValuePairs.add(new BasicNameValuePair(SendPhoto.REPLYMARKUP_FIELD, sendPhoto.getReplayMarkup().toString()));
                }
                if (sendPhoto.getReplayToMessageId() != null) {
                    nameValuePairs.add(new BasicNameValuePair(SendPhoto.REPLYTOMESSAGEID_FIELD, sendPhoto.getReplayToMessageId().toString()));
                }
                if (sendPhoto.getCaption() != null) {
                    nameValuePairs.add(new BasicNameValuePair(SendPhoto.CAPTION_FIELD, sendPhoto.getCaption()));
                }
                httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs, "UTF-8"));
            }

            CloseableHttpResponse response = httpClient.execute(httppost);
        } catch (IOException e) {
            BotLogger.error(LOGTAG, e);
        }
    }

    public static void SendVideo(SendVideo sendVideo, String botToken) {
        try {
            CloseableHttpClient httpClient = HttpClients.createDefault();
            String url = Constants.BASEURL + botToken + "/" + SendVideo.PATH;
            HttpPost httppost = new HttpPost(url);

            if (sendVideo.isNewVideo()) {
                MultipartEntityBuilder builder = MultipartEntityBuilder.create();
                builder.addTextBody(SendVideo.CHATID_FIELD, sendVideo.getChatId());
                builder.addBinaryBody(SendVideo.VIDEO_FIELD, new File(sendVideo.getVideo()), ContentType.APPLICATION_OCTET_STREAM, sendVideo.getVideoName());
                if (sendVideo.getReplayMarkup() != null) {
                    builder.addTextBody(SendVideo.REPLYMARKUP_FIELD, sendVideo.getReplayMarkup().toJson().toString());
                }
                if (sendVideo.getReplayToMessageId() != null) {
                    builder.addTextBody(SendVideo.REPLYTOMESSAGEID_FIELD, sendVideo.getReplayToMessageId().toString());
                }
                if (sendVideo.getCaption() != null) {
                    builder.addTextBody(SendVideo.CAPTION_FIELD, sendVideo.getCaption());
                }
                if (sendVideo.getDuration() != null) {
                    builder.addTextBody(SendVideo.DURATION_FIELD, sendVideo.getDuration().toString());
                }
                HttpEntity multipart = builder.build();
                httppost.setEntity(multipart);
            } else {
                List<NameValuePair> nameValuePairs = new ArrayList<>();
                nameValuePairs.add(new BasicNameValuePair(SendVideo.CHATID_FIELD, sendVideo.getChatId()));
                nameValuePairs.add(new BasicNameValuePair(SendVideo.VIDEO_FIELD, sendVideo.getVideo()));
                if (sendVideo.getReplayMarkup() != null) {
                    nameValuePairs.add(new BasicNameValuePair(SendVideo.REPLYMARKUP_FIELD, sendVideo.getReplayMarkup().toString()));
                }
                if (sendVideo.getReplayToMessageId() != null) {
                    nameValuePairs.add(new BasicNameValuePair(SendVideo.REPLYTOMESSAGEID_FIELD, sendVideo.getReplayToMessageId().toString()));
                }
                if (sendVideo.getCaption() != null) {
                    nameValuePairs.add(new BasicNameValuePair(SendVideo.CAPTION_FIELD, sendVideo.getCaption()));
                }
                if (sendVideo.getDuration() != null) {
                    nameValuePairs.add(new BasicNameValuePair(SendVideo.DURATION_FIELD, sendVideo.getDuration().toString()));
                }
                httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs, "UTF-8"));
            }

            CloseableHttpResponse response = httpClient.execute(httppost);
        } catch (IOException e) {
            BotLogger.error(LOGTAG, e);
        }
    }

    public static void sendSticker(SendSticker sendSticker, String botToken) {
        try {
            CloseableHttpClient httpClient = HttpClients.createDefault();
            String url = Constants.BASEURL + botToken + "/" + SendSticker.PATH;
            HttpPost httppost = new HttpPost(url);

            if (sendSticker.isNewSticker()) {
                MultipartEntityBuilder builder = MultipartEntityBuilder.create();
                builder.addTextBody(SendSticker.CHATID_FIELD, sendSticker.getChatId());
                builder.addBinaryBody(SendSticker.STICKER_FIELD, new File(sendSticker.getSticker()), ContentType.APPLICATION_OCTET_STREAM, sendSticker.getStickerName());
                if (sendSticker.getReplayMarkup() != null) {
                    builder.addTextBody(SendSticker.REPLYMARKUP_FIELD, sendSticker.getReplayMarkup().toJson().toString());
                }
                if (sendSticker.getReplayToMessageId() != null) {
                    builder.addTextBody(SendSticker.REPLYTOMESSAGEID_FIELD, sendSticker.getReplayToMessageId().toString());
                }
                HttpEntity multipart = builder.build();
                httppost.setEntity(multipart);
            } else {
                List<NameValuePair> nameValuePairs = new ArrayList<>();
                nameValuePairs.add(new BasicNameValuePair(SendSticker.CHATID_FIELD, sendSticker.getChatId()));
                nameValuePairs.add(new BasicNameValuePair(SendSticker.STICKER_FIELD, sendSticker.getSticker()));
                if (sendSticker.getReplayMarkup() != null) {
                    nameValuePairs.add(new BasicNameValuePair(SendSticker.REPLYMARKUP_FIELD, sendSticker.getReplayMarkup().toString()));
                }
                if (sendSticker.getReplayToMessageId() != null) {
                    nameValuePairs.add(new BasicNameValuePair(SendSticker.REPLYTOMESSAGEID_FIELD, sendSticker.getReplayToMessageId().toString()));
                }
                httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs, "UTF-8"));
            }

            CloseableHttpResponse response = httpClient.execute(httppost);
        } catch (IOException e) {
            BotLogger.error(LOGTAG, e);
        } finally {
            if (sendSticker.isNewSticker()) {
                File fileToDelete = new File(sendSticker.getSticker());
                fileToDelete.delete();
            }
        }
    }

    public static void SendWebhook(String webHookURL, String botToken, String publicCertificatePath, String publicCertificateName) {
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
            BotLogger.debug(LOGTAG, responseContent);
        } catch (IOException e) {
            BotLogger.error(LOGTAG, e);
        }

    }

    public static void SendApiMethod(BotApiMethod method, String botToken) throws InvalidObjectException {
        String responseContent = "{}";
        try {
            CloseableHttpClient httpclient = HttpClientBuilder.create().setSSLHostnameVerifier(new NoopHostnameVerifier()).build();
            String url = Constants.BASEURL + botToken + "/" + method.getPath();
            HttpPost httppost = new HttpPost(url);
            httppost.addHeader("charset", "UTF-8");
            httppost.setEntity(new StringEntity(method.toJson().toString(), ContentType.APPLICATION_JSON));
            CloseableHttpResponse response = httpclient.execute(httppost);
            HttpEntity ht = response.getEntity();
            BufferedHttpEntity buf = new BufferedHttpEntity(ht);
            responseContent = EntityUtils.toString(buf, "UTF-8");
        } catch (IOException e) {
            BotLogger.error(LOGTAG, e);
        }

        JSONObject jsonObject = new JSONObject(responseContent);
        if (!jsonObject.getBoolean("ok")) {
            throw new InvalidObjectException(jsonObject.getString("description"));
        }

    }

    public static void SendApiMethodAsync(BotApiMethod method, String botToken, SentCallback callback) {
        exe.submit(() -> {
            try {
                CloseableHttpClient httpclient = HttpClientBuilder.create().setSSLHostnameVerifier(new NoopHostnameVerifier()).build();
                String url = Constants.BASEURL + botToken + "/" + method.getPath();
                HttpPost httppost = new HttpPost(url);
                httppost.addHeader("charset", "UTF-8");
                httppost.setEntity(new StringEntity(method.toJson().toString(), ContentType.APPLICATION_JSON));
                CloseableHttpResponse response = httpclient.execute(httppost);
                HttpEntity ht = response.getEntity();
                BufferedHttpEntity buf = new BufferedHttpEntity(ht);
                String responseContent = EntityUtils.toString(buf, "UTF-8");

                JSONObject jsonObject = new JSONObject(responseContent);
                if (!jsonObject.getBoolean("ok")) {
                    callback.onError(method, jsonObject);
                }
                callback.onResult(method, jsonObject);
            } catch (IOException e) {
                BotLogger.error(LOGTAG, e);
            }
        });
    }
}
