package org.telegram.telegrambots.bots;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
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
import org.telegram.telegrambots.TelegramApiException;
import org.telegram.telegrambots.api.Constants;
import org.telegram.telegrambots.api.methods.AnswerInlineQuery;
import org.telegram.telegrambots.api.methods.BotApiMethod;
import org.telegram.telegrambots.api.methods.ForwardMessage;
import org.telegram.telegrambots.api.methods.GetMe;
import org.telegram.telegrambots.api.methods.GetUserProfilePhotos;
import org.telegram.telegrambots.api.methods.SendAudio;
import org.telegram.telegrambots.api.methods.SendChatAction;
import org.telegram.telegrambots.api.methods.SendDocument;
import org.telegram.telegrambots.api.methods.SendLocation;
import org.telegram.telegrambots.api.methods.SendMessage;
import org.telegram.telegrambots.api.methods.SendPhoto;
import org.telegram.telegrambots.api.methods.SendSticker;
import org.telegram.telegrambots.api.methods.SendVideo;
import org.telegram.telegrambots.api.methods.SendVoice;
import org.telegram.telegrambots.api.objects.File;
import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.api.objects.User;
import org.telegram.telegrambots.api.objects.UserProfilePhotos;
import org.telegram.telegrambots.updateshandlers.SentCallback;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author Ruben Bermudez
 * @version 1.0
 * @brief Implementation of all the methods needed to interact with Telegram Servers
 * @date 14 of January of 2016
 */
public abstract class AbsSender {
    private final ExecutorService exe = Executors.newSingleThreadExecutor();

    /**
     * Returns the token of the bot to be able to perform Telegram Api Requests
     * @return Token of the bot
     */
    public abstract String getBotToken();

    public Message sendMessage(SendMessage sendMessage) throws TelegramApiException {
        if (sendMessage == null) {
            throw new TelegramApiException("Parameter sendMessage can not be null");
        }

        return (Message) sendApiMethod(sendMessage);
    }

    public Boolean sendAnswerInlineQuery(AnswerInlineQuery answerInlineQuery) throws TelegramApiException {
        if (answerInlineQuery == null) {
            throw new TelegramApiException("Parameter answerInlineQuery can not be null");
        }

        return (Boolean) sendApiMethod(answerInlineQuery);
    }

    public Boolean sendChatAction(SendChatAction sendChatAction) throws TelegramApiException {
        if (sendChatAction == null) {
            throw new TelegramApiException("Parameter sendChatAction can not be null");
        }

        return (Boolean) sendApiMethod(sendChatAction);
    }

    public Message forwardMessage(ForwardMessage forwardMessage) throws TelegramApiException {
        if (forwardMessage == null) {
            throw new TelegramApiException("Parameter forwardMessage can not be null");
        }

        return (Message) sendApiMethod(forwardMessage);
    }

    public File sendLocation(SendLocation sendLocation) throws TelegramApiException {
        if (sendLocation == null) {
            throw new TelegramApiException("Parameter sendLocation can not be null");
        }

        return (File) sendApiMethod(sendLocation);
    }

    public UserProfilePhotos getUserProfilePhotos(GetUserProfilePhotos getUserProfilePhotos) throws TelegramApiException {
        if (getUserProfilePhotos == null) {
            throw new TelegramApiException("Parameter getUserProfilePhotos can not be null");
        }

        return (UserProfilePhotos) sendApiMethod(getUserProfilePhotos);
    }
    
    public File getFile(GetFile getFile) throws TelegramApiException{
    	if(getFile == null){
    		throw new TelegramApiException("Parameter getFile can not be null");
    	}
    	else if(getFile.getFileId() == null){
    		throw new TelegramApiException("Attribute file_id in parameter getFile can not be null");
    	}
    	return (File)sendApiMethod(getFile);
    	
    }

    public User getMe() throws TelegramApiException {
        GetMe getMe = new GetMe();

        return (User) sendApiMethod(getMe);
    }

    public void sendMessageAsync(SendMessage sendMessage, SentCallback<Message> sentCallback) throws TelegramApiException {
        if (sendMessage == null) {
            throw new TelegramApiException("Parameter sendMessage can not be null");
        }

        if (sentCallback == null) {
            throw new TelegramApiException("Parameter sentCallback can not be null");
        }

        sendApiMethodAsync(sendMessage, sentCallback);
    }

    public void sendAnswerInlineQueryAsync(AnswerInlineQuery answerInlineQuery, SentCallback<Boolean> sentCallback) throws TelegramApiException {
        if (answerInlineQuery == null) {
            throw new TelegramApiException("Parameter answerInlineQuery can not be null");
        }

        if (sentCallback == null) {
            throw new TelegramApiException("Parameter sentCallback can not be null");
        }

        sendApiMethodAsync(answerInlineQuery, sentCallback);
    }

    public void sendChatActionAsync(SendChatAction sendChatAction, SentCallback<Boolean> sentCallback) throws TelegramApiException {
        if (sendChatAction == null) {
            throw new TelegramApiException("Parameter sendChatAction can not be null");
        }

        if (sentCallback == null) {
            throw new TelegramApiException("Parameter sentCallback can not be null");
        }

        sendApiMethodAsync(sendChatAction, sentCallback);
    }

    public void forwardMessageAsync(ForwardMessage forwardMessage, SentCallback<Message> sentCallback) throws TelegramApiException {
        if (forwardMessage == null) {
            throw new TelegramApiException("Parameter forwardMessage can not be null");
        }

        if (sentCallback == null) {
            throw new TelegramApiException("Parameter sentCallback can not be null");
        }

        sendApiMethodAsync(forwardMessage, sentCallback);
    }

    public void sendLocationAsync(SendLocation sendLocation, SentCallback<File> sentCallback) throws TelegramApiException {
        if (sendLocation == null) {
            throw new TelegramApiException("Parameter sendLocation can not be null");
        }

        if (sentCallback == null) {
            throw new TelegramApiException("Parameter sentCallback can not be null");
        }

        sendApiMethodAsync(sendLocation, sentCallback);
    }

    public void getUserProfilePhotosAsync(GetUserProfilePhotos getUserProfilePhotos, SentCallback<UserProfilePhotos> sentCallback) throws TelegramApiException {
        if (getUserProfilePhotos == null) {
            throw new TelegramApiException("Parameter getUserProfilePhotos can not be null");
        }

        if (sentCallback == null) {
            throw new TelegramApiException("Parameter sentCallback can not be null");
        }

        sendApiMethodAsync(getUserProfilePhotos, sentCallback);
    }

    public void getMeAsync(SentCallback<User> sentCallback) throws TelegramApiException {
        if (sentCallback == null) {
            throw new TelegramApiException("Parameter sentCallback can not be null");
        }

        GetMe getMe = new GetMe();
        sendApiMethodAsync(getMe, sentCallback);
    }

    public Message sendDocument(SendDocument sendDocument) throws TelegramApiException {
        String responseContent;

        try {
            CloseableHttpClient httpClient = HttpClients.createDefault();
            String url = getBaseUrl() + SendDocument.PATH;
            HttpPost httppost = new HttpPost(url);
            if (sendDocument.isNewDocument()) {
                MultipartEntityBuilder builder = MultipartEntityBuilder.create();
                builder.addTextBody(SendDocument.CHATID_FIELD, sendDocument.getChatId());
                builder.addBinaryBody(SendDocument.DOCUMENT_FIELD, new java.io.File(sendDocument.getDocument()), ContentType.APPLICATION_OCTET_STREAM, sendDocument.getDocumentName());
                if (sendDocument.getReplayMarkup() != null) {
                    builder.addTextBody(SendDocument.REPLYMARKUP_FIELD, sendDocument.getReplayMarkup().toJson().toString());
                }
                if (sendDocument.getReplayToMessageId() != null) {
                    builder.addTextBody(SendDocument.REPLYTOMESSAGEID_FIELD, sendDocument.getReplayToMessageId().toString());
                }
                if (sendDocument.getCaption() != null) {
                    builder.addTextBody(SendDocument.CAPTION_FIELD, sendDocument.getCaption());
                }
                if (sendDocument.getDisableNotification() != null) {
                    builder.addTextBody(SendDocument.DISABLENOTIFICATION_FIELD, sendDocument.getDisableNotification().toString());
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
                if (sendDocument.getCaption() != null) {
                    nameValuePairs.add(new BasicNameValuePair(SendDocument.CAPTION_FIELD, sendDocument.getCaption()));
                }
                if (sendDocument.getReplayToMessageId() != null) {
                    nameValuePairs.add(new BasicNameValuePair(SendDocument.DISABLENOTIFICATION_FIELD, sendDocument.getDisableNotification().toString()));
                }
                httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs, "UTF-8"));
            }

            CloseableHttpResponse response = httpClient.execute(httppost);
            HttpEntity ht = response.getEntity();
            BufferedHttpEntity buf = new BufferedHttpEntity(ht);
            responseContent = EntityUtils.toString(buf, "UTF-8");
        } catch (IOException e) {
            throw new TelegramApiException("Unable to send document", e);
        }

        JSONObject jsonObject = new JSONObject(responseContent);
        if (!jsonObject.getBoolean("ok")) {
            throw new TelegramApiException("Error at sendDocument", jsonObject.getString("description"));
        }

        return new Message(jsonObject);
    }

    public Message sendPhoto(SendPhoto sendPhoto) throws TelegramApiException {
        String responseContent;
        try {
            CloseableHttpClient httpClient = HttpClients.createDefault();
            String url = getBaseUrl() + SendPhoto.PATH;
            HttpPost httppost = new HttpPost(url);
            if (sendPhoto.isNewPhoto()) {
                MultipartEntityBuilder builder = MultipartEntityBuilder.create();
                builder.addTextBody(SendPhoto.CHATID_FIELD, sendPhoto.getChatId());
                builder.addBinaryBody(SendPhoto.PHOTO_FIELD, new java.io.File(sendPhoto.getPhoto()), ContentType.APPLICATION_OCTET_STREAM, sendPhoto.getPhotoName());
                if (sendPhoto.getReplayMarkup() != null) {
                    builder.addTextBody(SendPhoto.REPLYMARKUP_FIELD, sendPhoto.getReplayMarkup().toJson().toString());
                }
                if (sendPhoto.getReplayToMessageId() != null) {
                    builder.addTextBody(SendPhoto.REPLYTOMESSAGEID_FIELD, sendPhoto.getReplayToMessageId().toString());
                }
                if (sendPhoto.getCaption() != null) {
                    builder.addTextBody(SendPhoto.CAPTION_FIELD, sendPhoto.getCaption());
                }
                if (sendPhoto.getDisableNotification() != null) {
                    builder.addTextBody(SendPhoto.DISABLENOTIFICATION_FIELD, sendPhoto.getDisableNotification().toString());
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
                if (sendPhoto.getDisableNotification() != null) {
                    nameValuePairs.add(new BasicNameValuePair(SendPhoto.DISABLENOTIFICATION_FIELD, sendPhoto.getDisableNotification().toString()));
                }
                httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs, "UTF-8"));
            }

            CloseableHttpResponse response = httpClient.execute(httppost);
            HttpEntity ht = response.getEntity();
            BufferedHttpEntity buf = new BufferedHttpEntity(ht);
            responseContent = EntityUtils.toString(buf, "UTF-8");
        } catch (IOException e) {
            throw new TelegramApiException("Unable to send photo", e);
        }

        JSONObject jsonObject = new JSONObject(responseContent);
        if (!jsonObject.getBoolean("ok")) {
            throw new TelegramApiException("Error at sendPhoto", jsonObject.getString("description"));
        }

        return new Message(jsonObject);
    }

    public Message sendVideo(SendVideo sendVideo) throws TelegramApiException {
        String responseContent;
        try {
            CloseableHttpClient httpClient = HttpClients.createDefault();
            String url = getBaseUrl() + SendVideo.PATH;
            HttpPost httppost = new HttpPost(url);
            if (sendVideo.isNewVideo()) {
                MultipartEntityBuilder builder = MultipartEntityBuilder.create();
                builder.addTextBody(SendVideo.CHATID_FIELD, sendVideo.getChatId());
                builder.addBinaryBody(SendVideo.VIDEO_FIELD, new java.io.File(sendVideo.getVideo()), ContentType.APPLICATION_OCTET_STREAM, sendVideo.getVideoName());
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
                if (sendVideo.getWidth() != null) {
                    builder.addTextBody(SendVideo.WIDTH_FIELD, sendVideo.getWidth().toString());
                }
                if (sendVideo.getHeight() != null) {
                    builder.addTextBody(SendVideo.HEIGHT_FIELD, sendVideo.getHeight().toString());
                }
                if (sendVideo.getDisableNotification() != null) {
                    builder.addTextBody(SendVideo.DISABLENOTIFICATION_FIELD, sendVideo.getDisableNotification().toString());
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
                if (sendVideo.getWidth() != null) {
                    nameValuePairs.add(new BasicNameValuePair(SendVideo.WIDTH_FIELD, sendVideo.getWidth().toString()));
                }
                if (sendVideo.getHeight() != null) {
                    nameValuePairs.add(new BasicNameValuePair(SendVideo.HEIGHT_FIELD, sendVideo.getHeight().toString()));
                }
                if (sendVideo.getDisableNotification() != null) {
                    nameValuePairs.add(new BasicNameValuePair(SendVideo.DISABLENOTIFICATION_FIELD, sendVideo.getDisableNotification().toString()));
                }
                httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs, "UTF-8"));
            }

            CloseableHttpResponse response = httpClient.execute(httppost);
            HttpEntity ht = response.getEntity();
            BufferedHttpEntity buf = new BufferedHttpEntity(ht);
            responseContent = EntityUtils.toString(buf, "UTF-8");
        } catch (IOException e) {
            throw new TelegramApiException("Unable to send video", e);
        }

        JSONObject jsonObject = new JSONObject(responseContent);
        if (!jsonObject.getBoolean("ok")) {
            throw new TelegramApiException("Error at sendVideo", jsonObject.getString("description"));
        }

        return new Message(jsonObject);
    }

    public Message sendSticker(SendSticker sendSticker) throws TelegramApiException {
        String responseContent;

        try {
            CloseableHttpClient httpClient = HttpClients.createDefault();
            String url = getBaseUrl() + SendSticker.PATH;
            HttpPost httppost = new HttpPost(url);
            if (sendSticker.isNewSticker()) {
                MultipartEntityBuilder builder = MultipartEntityBuilder.create();
                builder.addTextBody(SendSticker.CHATID_FIELD, sendSticker.getChatId());
                builder.addBinaryBody(SendSticker.STICKER_FIELD, new java.io.File(sendSticker.getSticker()), ContentType.APPLICATION_OCTET_STREAM, sendSticker.getStickerName());
                if (sendSticker.getReplayMarkup() != null) {
                    builder.addTextBody(SendSticker.REPLYMARKUP_FIELD, sendSticker.getReplayMarkup().toJson().toString());
                }
                if (sendSticker.getReplayToMessageId() != null) {
                    builder.addTextBody(SendSticker.REPLYTOMESSAGEID_FIELD, sendSticker.getReplayToMessageId().toString());
                }
                if (sendSticker.getDisableNotification() != null) {
                    builder.addTextBody(SendSticker.DISABLENOTIFICATION_FIELD, sendSticker.getDisableNotification().toString());
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
                if (sendSticker.getDisableNotification() != null) {
                    nameValuePairs.add(new BasicNameValuePair(SendSticker.DISABLENOTIFICATION_FIELD, sendSticker.getDisableNotification().toString()));
                }
                httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs, "UTF-8"));
            }

            CloseableHttpResponse response = httpClient.execute(httppost);
            HttpEntity ht = response.getEntity();
            BufferedHttpEntity buf = new BufferedHttpEntity(ht);
            responseContent = EntityUtils.toString(buf, "UTF-8");
        } catch (IOException e) {
            throw new TelegramApiException("Unable to send sticker", e);
        }

        JSONObject jsonObject = new JSONObject(responseContent);
        if (!jsonObject.getBoolean("ok")) {
            throw new TelegramApiException("Error at sendSticker", jsonObject.getString("description"));
        }

        return new Message(jsonObject);
    }

    /**
     * Sends a file using Send Audio method (https://core.telegram.org/bots/api#sendaudio)
     * @param sendAudio Information to send
     * @return If success, the sent Message is returned
     * @throws TelegramApiException If there is any error sending the audio
     */
    public Message sendAudio(SendAudio sendAudio) throws TelegramApiException {
        String responseContent;

        
        try {
            CloseableHttpClient httpClient = HttpClients.createDefault();
            String url = getBaseUrl() + SendAudio.PATH;
            HttpPost httppost = new HttpPost(url);

            if (sendAudio.isNewAudio()) {
                MultipartEntityBuilder builder = MultipartEntityBuilder.create();
                builder.addTextBody(SendAudio.CHATID_FIELD, sendAudio.getChatId());
                builder.addBinaryBody(SendAudio.AUDIO_FIELD, new java.io.File(sendAudio.getAudio()), ContentType.create("audio/mpeg"), sendAudio.getAudioName());
                if (sendAudio.getReplayMarkup() != null) {
                    builder.addTextBody(SendAudio.REPLYMARKUP_FIELD, sendAudio.getReplayMarkup().toJson().toString());
                }
                if (sendAudio.getReplayToMessageId() != null) {
                    builder.addTextBody(SendAudio.REPLYTOMESSAGEID_FIELD, sendAudio.getReplayToMessageId().toString());
                }
                if (sendAudio.getPerformer() != null) {
                    builder.addTextBody(SendAudio.PERFOMER_FIELD, sendAudio.getPerformer());
                }
                if (sendAudio.getTitle() != null) {
                    builder.addTextBody(SendAudio.TITLE_FIELD, sendAudio.getTitle());
                }
                if(sendAudio.getDuration() != null){
                	builder.addTextBody(SendAudio.DURATION_FIELD, sendAudio.getDuration().toString());
                }
                if (sendAudio.getDisableNotification() != null) {
                    builder.addTextBody(SendAudio.DISABLENOTIFICATION_FIELD, sendAudio.getDisableNotification().toString());
                }
                HttpEntity multipart = builder.build();
                httppost.setEntity(multipart);
            } else {
                List<NameValuePair> nameValuePairs = new ArrayList<>();
                nameValuePairs.add(new BasicNameValuePair(SendAudio.CHATID_FIELD, sendAudio.getChatId()));
                nameValuePairs.add(new BasicNameValuePair(SendAudio.AUDIO_FIELD, sendAudio.getAudio()));
                if (sendAudio.getReplayMarkup() != null) {
                    nameValuePairs.add(new BasicNameValuePair(SendAudio.REPLYMARKUP_FIELD, sendAudio.getReplayMarkup().toString()));
                }
                if (sendAudio.getReplayToMessageId() != null) {
                    nameValuePairs.add(new BasicNameValuePair(SendAudio.REPLYTOMESSAGEID_FIELD, sendAudio.getReplayToMessageId().toString()));
                }
                if (sendAudio.getPerformer() != null) {
                    nameValuePairs.add(new BasicNameValuePair(SendAudio.PERFOMER_FIELD, sendAudio.getPerformer()));
                }
                if (sendAudio.getTitle() != null) {
                    nameValuePairs.add(new BasicNameValuePair(SendAudio.TITLE_FIELD, sendAudio.getTitle()));
                }
                if (sendAudio.getDisableNotification() != null) {
                    nameValuePairs.add(new BasicNameValuePair(SendAudio.DISABLENOTIFICATION_FIELD, sendAudio.getDisableNotification().toString()));
                }
                httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs, "UTF-8"));
            }

            CloseableHttpResponse response = httpClient.execute(httppost);
            HttpEntity ht = response.getEntity();
            BufferedHttpEntity buf = new BufferedHttpEntity(ht);
            responseContent = EntityUtils.toString(buf, "UTF-8");
        } catch (IOException e) {
            throw new TelegramApiException("Unable to send sticker", e);
        }

        JSONObject jsonObject = new JSONObject(responseContent);
        if (!jsonObject.getBoolean("ok")) {
            throw new TelegramApiException("Error at sendAudio", jsonObject.getString("description"));
        }

        return new Message(jsonObject);
    }

    /**
     * Sends a voice note using Send Voice method (https://core.telegram.org/bots/api#sendvoice)
     * @param sendVoice Information to send
     * @return If success, the sent Message is returned
     * @throws TelegramApiException If there is any error sending the audio
     */
    public Message sendVoice(SendVoice sendVoice) throws TelegramApiException {
        String responseContent;

        try {
            CloseableHttpClient httpClient = HttpClients.createDefault();
            String url = getBaseUrl() + SendVoice.PATH;
            HttpPost httppost = new HttpPost(url);

            if (sendVoice.isNewVoice()) {
                MultipartEntityBuilder builder = MultipartEntityBuilder.create();
                builder.addTextBody(SendVoice.CHATID_FIELD, sendVoice.getChatId());
                builder.addBinaryBody(SendVoice.AUDIO_FIELD, new java.io.File(sendVoice.getAudio()), ContentType.create("audio/ogg"), sendVoice.getVoiceName());
                if (sendVoice.getReplayMarkup() != null) {
                    builder.addTextBody(SendVoice.REPLYMARKUP_FIELD, sendVoice.getReplayMarkup().toJson().toString());
                }
                if (sendVoice.getReplayToMessageId() != null) {
                    builder.addTextBody(SendVoice.REPLYTOMESSAGEID_FIELD, sendVoice.getReplayToMessageId().toString());
                }
                if (sendVoice.getDisableNotification() != null) {
                    builder.addTextBody(SendVoice.DISABLENOTIFICATION_FIELD, sendVoice.getDisableNotification().toString());
                }
                if (sendVoice.getDuration() != null) {
                    builder.addTextBody(SendVoice.DURATION_FIELD, sendVoice.getDuration().toString());
                }
                HttpEntity multipart = builder.build();
                httppost.setEntity(multipart);
            } else {
                List<NameValuePair> nameValuePairs = new ArrayList<>();
                nameValuePairs.add(new BasicNameValuePair(SendVoice.CHATID_FIELD, sendVoice.getChatId()));
                nameValuePairs.add(new BasicNameValuePair(SendVoice.AUDIO_FIELD, sendVoice.getAudio()));
                if (sendVoice.getReplayMarkup() != null) {
                    nameValuePairs.add(new BasicNameValuePair(SendVoice.REPLYMARKUP_FIELD, sendVoice.getReplayMarkup().toString()));
                }
                if (sendVoice.getReplayToMessageId() != null) {
                    nameValuePairs.add(new BasicNameValuePair(SendVoice.REPLYTOMESSAGEID_FIELD, sendVoice.getReplayToMessageId().toString()));
                }
                if (sendVoice.getDisableNotification() != null) {
                    nameValuePairs.add(new BasicNameValuePair(SendVoice.DISABLENOTIFICATION_FIELD, sendVoice.getDisableNotification().toString()));
                }
                if (sendVoice.getDuration() != null) {
                    nameValuePairs.add(new BasicNameValuePair(SendVoice.DURATION_FIELD, sendVoice.getDuration().toString()));
                }
                httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs, "UTF-8"));
            }

            CloseableHttpResponse response = httpClient.execute(httppost);
            HttpEntity ht = response.getEntity();
            BufferedHttpEntity buf = new BufferedHttpEntity(ht);
            responseContent = EntityUtils.toString(buf, "UTF-8");
        } catch (IOException e) {
            throw new TelegramApiException("Unable to send sticker", e);
        }

        JSONObject jsonObject = new JSONObject(responseContent);
        if (!jsonObject.getBoolean("ok")) {
            throw new TelegramApiException("Error at sendVoice", jsonObject.getString("description"));
        }

        return new Message(jsonObject);
    }

    private void sendApiMethodAsync(BotApiMethod method, SentCallback callback) {
        exe.submit(() -> {
            try {
                CloseableHttpClient httpclient = HttpClientBuilder.create().setSSLHostnameVerifier(new NoopHostnameVerifier()).build();
                String url = getBaseUrl() + method.getPath();
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
                callback.onException(method, e);
            }
        });
    }

    private Serializable sendApiMethod(BotApiMethod method) throws TelegramApiException {
        String responseContent;
        try {
            CloseableHttpClient httpclient = HttpClientBuilder.create().setSSLHostnameVerifier(new NoopHostnameVerifier()).build();
            String url = getBaseUrl() + method.getPath();
            HttpPost httppost = new HttpPost(url);
            httppost.addHeader("charset", "UTF-8");
            httppost.setEntity(new StringEntity(method.toJson().toString(), ContentType.APPLICATION_JSON));
            CloseableHttpResponse response = httpclient.execute(httppost);
            HttpEntity ht = response.getEntity();
            BufferedHttpEntity buf = new BufferedHttpEntity(ht);
            responseContent = EntityUtils.toString(buf, "UTF-8");
        } catch (IOException e) {
            throw new TelegramApiException("Unable to execute " + method.getPath() + " method", e);
        }

        JSONObject jsonObject = new JSONObject(responseContent);
        if (!jsonObject.getBoolean("ok")) {
            throw new TelegramApiException("Error at " + method.getPath(), jsonObject.getString("description"));
        }

        return method.deserializeResponse(jsonObject);
    }

    private String getBaseUrl() {
        return Constants.BASEURL + getBotToken() + "/";
    }
}
