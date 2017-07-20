package org.telegram.telegrambots.bots;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.io.FileUtils;
import org.apache.http.HttpEntity;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.entity.BufferedHttpEntity;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.telegram.telegrambots.api.methods.BotApiMethod;
import org.telegram.telegrambots.api.methods.groupadministration.SetChatPhoto;
import org.telegram.telegrambots.api.methods.send.*;
import org.telegram.telegrambots.api.objects.File;
import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.exceptions.TelegramApiException;
import org.telegram.telegrambots.exceptions.TelegramApiRequestException;
import org.telegram.telegrambots.exceptions.TelegramApiValidationException;
import org.telegram.telegrambots.updateshandlers.DownloadFileCallback;
import org.telegram.telegrambots.updateshandlers.SentCallback;

import java.io.IOException;
import java.io.Serializable;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static org.telegram.telegrambots.Constants.SOCKET_TIMEOUT;

/**
 * @author Ruben Bermudez
 * @version 1.0
 * @brief Implementation of all the methods needed to interact with Telegram Servers
 * @date 14 of January of 2016
 */
@SuppressWarnings("unused")
public abstract class DefaultAbsSender extends AbsSender {
    private static final ContentType TEXT_PLAIN_CONTENT_TYPE = ContentType.create("text/plain", StandardCharsets.UTF_8);

    private final ExecutorService exe;
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final DefaultBotOptions options;
    private volatile CloseableHttpClient httpclient;
    private volatile RequestConfig requestConfig;

    protected DefaultAbsSender(DefaultBotOptions options) {
        super();
        this.exe = Executors.newFixedThreadPool(options.getMaxThreads());
        this.options = options;
        httpclient = HttpClientBuilder.create()
                .setSSLHostnameVerifier(new NoopHostnameVerifier())
                .setConnectionTimeToLive(70, TimeUnit.SECONDS)
                .setMaxConnTotal(100)
                .build();

        requestConfig = options.getRequestConfig();

        if (requestConfig == null) {
            requestConfig = RequestConfig.copy(RequestConfig.custom().build())
                    .setSocketTimeout(SOCKET_TIMEOUT)
                    .setConnectTimeout(SOCKET_TIMEOUT)
                    .setConnectionRequestTimeout(SOCKET_TIMEOUT).build();
        }
    }

    /**
     * Returns the token of the bot to be able to perform Telegram Api Requests
     * @return Token of the bot
     */
    public abstract String getBotToken();

    public final DefaultBotOptions getOptions() {
        return options;
    }

    // Send Requests

    public final java.io.File downloadFile(String filePath) throws TelegramApiException {
        if(filePath == null || filePath.isEmpty()){
            throw new TelegramApiException("Parameter file can not be null");
        }
        String url = File.getFileUrl(getBotToken(), filePath);
        java.io.File output;
        try {
            output = java.io.File.createTempFile(Long.toString(System.currentTimeMillis()), ".tmp");
            FileUtils.copyURLToFile(new URL(url), output);
        } catch (MalformedURLException e) {
            throw new TelegramApiException("Wrong url for file: " + url);
        } catch (IOException e) {
            throw new TelegramApiRequestException("Error downloading the file", e);
        }

        return output;
    }

    public final java.io.File downloadFile(File file) throws TelegramApiException {
        if(file == null){
            throw new TelegramApiException("Parameter file can not be null");
        }
        String url = file.getFileUrl(getBotToken());
        java.io.File output;
        try {
            output = java.io.File.createTempFile(file.getFileId(), ".tmp");
            FileUtils.copyURLToFile(new URL(url), output);
        } catch (MalformedURLException e) {
            throw new TelegramApiException("Wrong url for file: " + url);
        } catch (IOException e) {
            throw new TelegramApiRequestException("Error downloading the file", e);
        }

        return output;
    }

    public final void downloadFileAsync(String filePath, DownloadFileCallback<String> callback) throws TelegramApiException {
        if(filePath == null || filePath.isEmpty()){
            throw new TelegramApiException("Parameter filePath can not be null");
        }
        if (callback == null) {
            throw new TelegramApiException("Parameter callback can not be null");
        }

        exe.submit(new Runnable() {
            @Override
            public void run() {
                String url = File.getFileUrl(getBotToken(), filePath);
                try {
                    java.io.File output = java.io.File.createTempFile(Long.toString(System.currentTimeMillis()), ".tmp");
                    FileUtils.copyURLToFile(new URL(url), output);
                    callback.onResult(filePath, output);
                } catch (MalformedURLException e) {
                    callback.onException(filePath, new TelegramApiException("Wrong url for file: " + url));
                } catch (IOException e) {
                    callback.onException(filePath, new TelegramApiRequestException("Error downloading the file", e));
                }
            }
        });
    }

    public final void downloadFileAsync(File file, DownloadFileCallback<File> callback) throws TelegramApiException {
        if(file == null){
            throw new TelegramApiException("Parameter file can not be null");
        }
        if (callback == null) {
            throw new TelegramApiException("Parameter callback can not be null");
        }

        exe.submit(new Runnable() {
            @Override
            public void run() {
                String url = file.getFileUrl(getBotToken());
                try {
                    java.io.File output = java.io.File.createTempFile(file.getFileId(), ".tmp");
                    FileUtils.copyURLToFile(new URL(url), output);
                    callback.onResult(file, output);
                } catch (MalformedURLException e) {
                    callback.onException(file, new TelegramApiException("Wrong url for file: " + url));
                } catch (IOException e) {
                    callback.onException(file, new TelegramApiRequestException("Error downloading the file", e));
                }
            }
        });
    }

    // Specific Send Requests

    @Override
    public final Message sendDocument(SendDocument sendDocument) throws TelegramApiException {
        if(sendDocument == null){
            throw new TelegramApiException("Parameter sendDocument can not be null");
        }

        sendDocument.validate();
        String responseContent;

        try {
            String url = getBaseUrl() + SendDocument.PATH;
            HttpPost httppost = new HttpPost(url);
            httppost.setConfig(requestConfig);

            MultipartEntityBuilder builder = MultipartEntityBuilder.create();
            builder.addTextBody(SendDocument.CHATID_FIELD, sendDocument.getChatId(), TEXT_PLAIN_CONTENT_TYPE);
            if (sendDocument.isNewDocument()) {
                if (sendDocument.getNewDocumentFile() != null) {
                    builder.addBinaryBody(SendDocument.DOCUMENT_FIELD, sendDocument.getNewDocumentFile());
                } else if (sendDocument.getNewDocumentStream() != null) {
                    builder.addBinaryBody(SendDocument.DOCUMENT_FIELD, sendDocument.getNewDocumentStream(), ContentType.APPLICATION_OCTET_STREAM, sendDocument.getDocumentName());
                } else {
                    builder.addBinaryBody(SendDocument.DOCUMENT_FIELD, new java.io.File(sendDocument.getDocument()), ContentType.APPLICATION_OCTET_STREAM, sendDocument.getDocumentName());
                }
            } else {
                builder.addTextBody(SendDocument.DOCUMENT_FIELD, sendDocument.getDocument(), TEXT_PLAIN_CONTENT_TYPE);
            }
            if (sendDocument.getReplyMarkup() != null) {
                builder.addTextBody(SendDocument.REPLYMARKUP_FIELD, objectMapper.writeValueAsString(sendDocument.getReplyMarkup()), TEXT_PLAIN_CONTENT_TYPE);
            }
            if (sendDocument.getReplyToMessageId() != null) {
                builder.addTextBody(SendDocument.REPLYTOMESSAGEID_FIELD, sendDocument.getReplyToMessageId().toString(), TEXT_PLAIN_CONTENT_TYPE);
            }
            if (sendDocument.getCaption() != null) {
                builder.addTextBody(SendDocument.CAPTION_FIELD, sendDocument.getCaption(), TEXT_PLAIN_CONTENT_TYPE);
            }
            if (sendDocument.getDisableNotification() != null) {
                builder.addTextBody(SendDocument.DISABLENOTIFICATION_FIELD, sendDocument.getDisableNotification().toString(), TEXT_PLAIN_CONTENT_TYPE);
            }
            HttpEntity multipart = builder.build();
            httppost.setEntity(multipart);

            try (CloseableHttpResponse response = httpclient.execute(httppost)) {
                HttpEntity ht = response.getEntity();
                BufferedHttpEntity buf = new BufferedHttpEntity(ht);
                responseContent = EntityUtils.toString(buf, StandardCharsets.UTF_8);
            }
        } catch (IOException e) {
            throw new TelegramApiException("Unable to send document", e);
        }

        return sendDocument.deserializeResponse(responseContent);
    }

    @Override
    public final Message sendPhoto(SendPhoto sendPhoto) throws TelegramApiException {
        if(sendPhoto == null){
            throw new TelegramApiException("Parameter sendPhoto can not be null");
        }

        sendPhoto.validate();
        String responseContent;
        try {
            String url = getBaseUrl() + SendPhoto.PATH;
            HttpPost httppost = new HttpPost(url);
            httppost.setConfig(requestConfig);

            MultipartEntityBuilder builder = MultipartEntityBuilder.create();
            builder.addTextBody(SendPhoto.CHATID_FIELD, sendPhoto.getChatId());
            if (sendPhoto.isNewPhoto()) {
                if (sendPhoto.getNewPhotoFile() != null) {
                    builder.addBinaryBody(SendPhoto.PHOTO_FIELD, sendPhoto.getNewPhotoFile());
                } else if (sendPhoto.getNewPhotoStream() != null) {
                    builder.addBinaryBody(SendPhoto.PHOTO_FIELD, sendPhoto.getNewPhotoStream(), ContentType.APPLICATION_OCTET_STREAM, sendPhoto.getPhotoName());
                } else {
                    builder.addBinaryBody(SendPhoto.PHOTO_FIELD, new java.io.File(sendPhoto.getPhoto()), ContentType.APPLICATION_OCTET_STREAM, sendPhoto.getPhotoName());
                }
            } else {
                builder.addTextBody(SendPhoto.PHOTO_FIELD, sendPhoto.getPhoto());
            }
            if (sendPhoto.getReplyMarkup() != null) {
                builder.addTextBody(SendPhoto.REPLYMARKUP_FIELD, objectMapper.writeValueAsString(sendPhoto.getReplyMarkup()), TEXT_PLAIN_CONTENT_TYPE);
            }
            if (sendPhoto.getReplyToMessageId() != null) {
                builder.addTextBody(SendPhoto.REPLYTOMESSAGEID_FIELD, sendPhoto.getReplyToMessageId().toString());
            }
            if (sendPhoto.getCaption() != null) {
                builder.addTextBody(SendPhoto.CAPTION_FIELD, sendPhoto.getCaption(), TEXT_PLAIN_CONTENT_TYPE);
            }
            if (sendPhoto.getDisableNotification() != null) {
                builder.addTextBody(SendPhoto.DISABLENOTIFICATION_FIELD, sendPhoto.getDisableNotification().toString());
            }
            HttpEntity multipart = builder.build();
            httppost.setEntity(multipart);

            try (CloseableHttpResponse response = httpclient.execute(httppost)) {
                HttpEntity ht = response.getEntity();
                BufferedHttpEntity buf = new BufferedHttpEntity(ht);
                responseContent = EntityUtils.toString(buf, StandardCharsets.UTF_8);
            }
        } catch (IOException e) {
            throw new TelegramApiException("Unable to send photo", e);
        }

        return sendPhoto.deserializeResponse(responseContent);
    }

    @Override
    public final Message sendVideo(SendVideo sendVideo) throws TelegramApiException {
        if(sendVideo == null){
            throw new TelegramApiException("Parameter sendVideo can not be null");
        }

        sendVideo.validate();
        String responseContent;
        try {
            String url = getBaseUrl() + SendVideo.PATH;
            HttpPost httppost = new HttpPost(url);
            httppost.setConfig(requestConfig);

            MultipartEntityBuilder builder = MultipartEntityBuilder.create();
            builder.addTextBody(SendVideo.CHATID_FIELD, sendVideo.getChatId());
            if (sendVideo.isNewVideo()) {
                if (sendVideo.getNewVideoFile() != null) {
                    builder.addBinaryBody(SendVideo.VIDEO_FIELD, sendVideo.getNewVideoFile());
                } else if (sendVideo.getNewVideoStream() != null) {
                    builder.addBinaryBody(SendVideo.VIDEO_FIELD, sendVideo.getNewVideoStream(), ContentType.APPLICATION_OCTET_STREAM, sendVideo.getVideoName());
                } else {
                    builder.addBinaryBody(SendVideo.VIDEO_FIELD, new java.io.File(sendVideo.getVideo()), ContentType.APPLICATION_OCTET_STREAM, sendVideo.getVideoName());
                }
            } else {
                builder.addTextBody(SendVideo.VIDEO_FIELD, sendVideo.getVideo());
            }
            if (sendVideo.getReplyMarkup() != null) {
                builder.addTextBody(SendVideo.REPLYMARKUP_FIELD, objectMapper.writeValueAsString(sendVideo.getReplyMarkup()), TEXT_PLAIN_CONTENT_TYPE);
            }
            if (sendVideo.getReplyToMessageId() != null) {
                builder.addTextBody(SendVideo.REPLYTOMESSAGEID_FIELD, sendVideo.getReplyToMessageId().toString());
            }
            if (sendVideo.getCaption() != null) {
                builder.addTextBody(SendVideo.CAPTION_FIELD, sendVideo.getCaption(), TEXT_PLAIN_CONTENT_TYPE);
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

            try (CloseableHttpResponse response = httpclient.execute(httppost)) {
                HttpEntity ht = response.getEntity();
                BufferedHttpEntity buf = new BufferedHttpEntity(ht);
                responseContent = EntityUtils.toString(buf, StandardCharsets.UTF_8);
            }
        } catch (IOException e) {
            throw new TelegramApiException("Unable to send video", e);
        }

        return sendVideo.deserializeResponse(responseContent);
    }

    @Override
    public final Message sendVideoNote(SendVideoNote sendVideoNote) throws TelegramApiException {
        if(sendVideoNote == null){
            throw new TelegramApiException("Parameter sendVideoNote can not be null");
        }

        sendVideoNote.validate();
        String responseContent;
        try {
            String url = getBaseUrl() + SendVideoNote.PATH;
            HttpPost httppost = new HttpPost(url);
            httppost.setConfig(requestConfig);

            MultipartEntityBuilder builder = MultipartEntityBuilder.create();
            builder.addTextBody(SendVideoNote.CHATID_FIELD, sendVideoNote.getChatId());
            if (sendVideoNote.isNewVideoNote()) {
                if (sendVideoNote.getNewVideoNoteFile() != null) {
                    builder.addBinaryBody(SendVideoNote.VIDEONOTE_FIELD, sendVideoNote.getNewVideoNoteFile());
                } else if (sendVideoNote.getNewVideoNoteStream() != null) {
                    builder.addBinaryBody(SendVideoNote.VIDEONOTE_FIELD, sendVideoNote.getNewVideoNoteStream(), ContentType.APPLICATION_OCTET_STREAM, sendVideoNote.getVideoNoteName());
                } else {
                    builder.addBinaryBody(SendVideoNote.VIDEONOTE_FIELD, new java.io.File(sendVideoNote.getVideoNote()), ContentType.APPLICATION_OCTET_STREAM, sendVideoNote.getVideoNoteName());
                }
            } else {
                builder.addTextBody(SendVideoNote.VIDEONOTE_FIELD, sendVideoNote.getVideoNote());
            }
            if (sendVideoNote.getReplyMarkup() != null) {
                builder.addTextBody(SendVideoNote.REPLYMARKUP_FIELD, objectMapper.writeValueAsString(sendVideoNote.getReplyMarkup()), TEXT_PLAIN_CONTENT_TYPE);
            }
            if (sendVideoNote.getReplyToMessageId() != null) {
                builder.addTextBody(SendVideoNote.REPLYTOMESSAGEID_FIELD, sendVideoNote.getReplyToMessageId().toString());
            }
            if (sendVideoNote.getDuration() != null) {
                builder.addTextBody(SendVideoNote.DURATION_FIELD, sendVideoNote.getDuration().toString());
            }
            if (sendVideoNote.getLength() != null) {
                builder.addTextBody(SendVideoNote.LENGTH_FIELD, sendVideoNote.getLength().toString());
            }
            if (sendVideoNote.getDisableNotification() != null) {
                builder.addTextBody(SendVideoNote.DISABLENOTIFICATION_FIELD, sendVideoNote.getDisableNotification().toString());
            }
            HttpEntity multipart = builder.build();
            httppost.setEntity(multipart);


            try (CloseableHttpResponse response = httpclient.execute(httppost)) {
                HttpEntity ht = response.getEntity();
                BufferedHttpEntity buf = new BufferedHttpEntity(ht);
                responseContent = EntityUtils.toString(buf, StandardCharsets.UTF_8);
            }
        } catch (IOException e) {
            throw new TelegramApiException("Unable to send video note", e);
        }

        return sendVideoNote.deserializeResponse(responseContent);
    }

    @Override
    public final Message sendSticker(SendSticker sendSticker) throws TelegramApiException {
        if(sendSticker == null){
            throw new TelegramApiException("Parameter sendSticker can not be null");
        }

        sendSticker.validate();
        String responseContent;
        try {
            String url = getBaseUrl() + SendSticker.PATH;
            HttpPost httppost = new HttpPost(url);
            httppost.setConfig(requestConfig);

            MultipartEntityBuilder builder = MultipartEntityBuilder.create();
            builder.addTextBody(SendSticker.CHATID_FIELD, sendSticker.getChatId());
            if (sendSticker.isNewSticker()) {
                if (sendSticker.getNewStickerFile() != null) {
                    builder.addBinaryBody(SendSticker.STICKER_FIELD, sendSticker.getNewStickerFile());
                } else if (sendSticker.getNewStickerStream() != null) {
                    builder.addBinaryBody(SendSticker.STICKER_FIELD, sendSticker.getNewStickerStream(), ContentType.APPLICATION_OCTET_STREAM, sendSticker.getStickerName());
                } else {
                    builder.addBinaryBody(SendSticker.STICKER_FIELD, new java.io.File(sendSticker.getSticker()), ContentType.APPLICATION_OCTET_STREAM, sendSticker.getStickerName());
                }
            } else {
                builder.addTextBody(SendSticker.STICKER_FIELD, sendSticker.getSticker());
            }
            if (sendSticker.getReplyMarkup() != null) {
                builder.addTextBody(SendSticker.REPLYMARKUP_FIELD, objectMapper.writeValueAsString(sendSticker.getReplyMarkup()), TEXT_PLAIN_CONTENT_TYPE);
            }
            if (sendSticker.getReplyToMessageId() != null) {
                builder.addTextBody(SendSticker.REPLYTOMESSAGEID_FIELD, sendSticker.getReplyToMessageId().toString());
            }
            if (sendSticker.getDisableNotification() != null) {
                builder.addTextBody(SendSticker.DISABLENOTIFICATION_FIELD, sendSticker.getDisableNotification().toString());
            }
            HttpEntity multipart = builder.build();
            httppost.setEntity(multipart);

            try (CloseableHttpResponse response = httpclient.execute(httppost)) {
                HttpEntity ht = response.getEntity();
                BufferedHttpEntity buf = new BufferedHttpEntity(ht);
                responseContent = EntityUtils.toString(buf, StandardCharsets.UTF_8);
            }
        } catch (IOException e) {
            throw new TelegramApiException("Unable to send sticker", e);
        }

        return sendSticker.deserializeResponse(responseContent);
    }

    /**
     * Sends a file using Send Audio method (https://core.telegram.org/bots/api#sendaudio)
     * @param sendAudio Information to send
     * @return If success, the sent Message is returned
     * @throws TelegramApiException If there is any error sending the audio
     */
    @Override
    public final Message sendAudio(SendAudio sendAudio) throws TelegramApiException {
        if(sendAudio == null){
            throw new TelegramApiException("Parameter sendAudio can not be null");
        }
        sendAudio.validate();
        String responseContent;

        try {
            String url = getBaseUrl() + SendAudio.PATH;
            HttpPost httppost = new HttpPost(url);
            httppost.setConfig(requestConfig);
            MultipartEntityBuilder builder = MultipartEntityBuilder.create();
            builder.addTextBody(SendAudio.CHATID_FIELD, sendAudio.getChatId());
            if (sendAudio.isNewAudio()) {
                if (sendAudio.getNewAudioFile() != null) {
                    builder.addBinaryBody(SendAudio.AUDIO_FIELD, sendAudio.getNewAudioFile());
                } else if (sendAudio.getNewAudioStream() != null) {
                    builder.addBinaryBody(SendAudio.AUDIO_FIELD, sendAudio.getNewAudioStream(), ContentType.APPLICATION_OCTET_STREAM, sendAudio.getAudioName());
                } else {
                    builder.addBinaryBody(SendAudio.AUDIO_FIELD, new java.io.File(sendAudio.getAudio()), ContentType.create("audio/mpeg"), sendAudio.getAudioName());
                }
            } else {
                builder.addTextBody(SendAudio.AUDIO_FIELD, sendAudio.getAudio());
            }
            if (sendAudio.getReplyMarkup() != null) {
                builder.addTextBody(SendAudio.REPLYMARKUP_FIELD, objectMapper.writeValueAsString(sendAudio.getReplyMarkup()), TEXT_PLAIN_CONTENT_TYPE);
            }
            if (sendAudio.getReplyToMessageId() != null) {
                builder.addTextBody(SendAudio.REPLYTOMESSAGEID_FIELD, sendAudio.getReplyToMessageId().toString());
            }
            if (sendAudio.getPerformer() != null) {
                builder.addTextBody(SendAudio.PERFOMER_FIELD, sendAudio.getPerformer(), TEXT_PLAIN_CONTENT_TYPE);
            }
            if (sendAudio.getTitle() != null) {
                builder.addTextBody(SendAudio.TITLE_FIELD, sendAudio.getTitle(), TEXT_PLAIN_CONTENT_TYPE);
            }
            if(sendAudio.getDuration() != null){
                builder.addTextBody(SendAudio.DURATION_FIELD, sendAudio.getDuration().toString());
            }
            if (sendAudio.getDisableNotification() != null) {
                builder.addTextBody(SendAudio.DISABLENOTIFICATION_FIELD, sendAudio.getDisableNotification().toString());
            }
            if (sendAudio.getCaption() != null) {
                builder.addTextBody(SendAudio.CAPTION_FIELD, sendAudio.getCaption(), TEXT_PLAIN_CONTENT_TYPE);
            }
            HttpEntity multipart = builder.build();
            httppost.setEntity(multipart);


            try (CloseableHttpResponse response = httpclient.execute(httppost)) {
                HttpEntity ht = response.getEntity();
                BufferedHttpEntity buf = new BufferedHttpEntity(ht);
                responseContent = EntityUtils.toString(buf, StandardCharsets.UTF_8);
            }
        } catch (IOException e) {
            throw new TelegramApiException("Unable to send sticker", e);
        }

        return sendAudio.deserializeResponse(responseContent);
    }

    /**
     * Sends a voice note using Send Voice method (https://core.telegram.org/bots/api#sendvoice)
     * For this to work, your audio must be in an .ogg file encoded with OPUS
     * @param sendVoice Information to send
     * @return If success, the sent Message is returned
     * @throws TelegramApiException If there is any error sending the audio
     */
    @Override
    public final Message sendVoice(SendVoice sendVoice) throws TelegramApiException {
        if(sendVoice == null){
            throw new TelegramApiException("Parameter sendVoice can not be null");
        }
        sendVoice.validate();
        String responseContent;

        try {
            String url = getBaseUrl() + SendVoice.PATH;
            HttpPost httppost = new HttpPost(url);
            httppost.setConfig(requestConfig);
            MultipartEntityBuilder builder = MultipartEntityBuilder.create();
            builder.addTextBody(SendVoice.CHATID_FIELD, sendVoice.getChatId());
            if (sendVoice.isNewVoice()) {
                if (sendVoice.getNewVoiceFile() != null) {
                    builder.addBinaryBody(SendVoice.VOICE_FIELD, sendVoice.getNewVoiceFile());
                } else if (sendVoice.getNewVoiceStream() != null) {
                    builder.addBinaryBody(SendVoice.VOICE_FIELD, sendVoice.getNewVoiceStream(), ContentType.APPLICATION_OCTET_STREAM, sendVoice.getVoiceName());
                } else {
                    builder.addBinaryBody(SendVoice.VOICE_FIELD, new java.io.File(sendVoice.getVoice()), ContentType.create("audio/ogg"), sendVoice.getVoiceName());
                }
            } else {
                builder.addTextBody(SendVoice.VOICE_FIELD, sendVoice.getVoice());
            }
            if (sendVoice.getReplyMarkup() != null) {
                builder.addTextBody(SendVoice.REPLYMARKUP_FIELD, objectMapper.writeValueAsString(sendVoice.getReplyMarkup()), TEXT_PLAIN_CONTENT_TYPE);
            }
            if (sendVoice.getReplyToMessageId() != null) {
                builder.addTextBody(SendVoice.REPLYTOMESSAGEID_FIELD, sendVoice.getReplyToMessageId().toString());
            }
            if (sendVoice.getDisableNotification() != null) {
                builder.addTextBody(SendVoice.DISABLENOTIFICATION_FIELD, sendVoice.getDisableNotification().toString());
            }
            if (sendVoice.getDuration() != null) {
                builder.addTextBody(SendVoice.DURATION_FIELD, sendVoice.getDuration().toString());
            }
            if (sendVoice.getCaption() != null) {
                builder.addTextBody(SendVoice.CAPTION_FIELD, sendVoice.getCaption(), TEXT_PLAIN_CONTENT_TYPE);
            }
            HttpEntity multipart = builder.build();
            httppost.setEntity(multipart);

            try (CloseableHttpResponse response = httpclient.execute(httppost)) {
                HttpEntity ht = response.getEntity();
                BufferedHttpEntity buf = new BufferedHttpEntity(ht);
                responseContent = EntityUtils.toString(buf, StandardCharsets.UTF_8);
            }
        } catch (IOException e) {
            throw new TelegramApiException("Unable to send voice", e);
        }

        return sendVoice.deserializeResponse(responseContent);
    }

    @Override
    public Boolean setChatPhoto(SetChatPhoto setChatPhoto) throws TelegramApiException {
        if(setChatPhoto == null){
            throw new TelegramApiException("Parameter setChatPhoto can not be null");
        }
        setChatPhoto.validate();
        String responseContent;

        try {
            String url = getBaseUrl() + SetChatPhoto.PATH;
            HttpPost httppost = new HttpPost(url);
            httppost.setConfig(requestConfig);

            MultipartEntityBuilder builder = MultipartEntityBuilder.create();
            builder.addTextBody(SetChatPhoto.CHATID_FIELD, setChatPhoto.getChatId());
            if (setChatPhoto.getPhoto() != null) {
                builder.addBinaryBody(SetChatPhoto.PHOTO_FIELD, setChatPhoto.getPhoto());
            } else if (setChatPhoto.getPhotoStream() != null) {
                builder.addBinaryBody(SetChatPhoto.PHOTO_FIELD, setChatPhoto.getPhotoStream(), ContentType.APPLICATION_OCTET_STREAM, setChatPhoto.getPhotoName());
            }
            HttpEntity multipart = builder.build();
            httppost.setEntity(multipart);

            try (CloseableHttpResponse response = httpclient.execute(httppost)) {
                HttpEntity ht = response.getEntity();
                BufferedHttpEntity buf = new BufferedHttpEntity(ht);
                responseContent = EntityUtils.toString(buf, StandardCharsets.UTF_8);
            }
        } catch (IOException e) {
            throw new TelegramApiException("Unable to set chat photo", e);
        }

        return setChatPhoto.deserializeResponse(responseContent);
    }

    // Simplified methods

    @Override
    protected final <T extends Serializable, Method extends BotApiMethod<T>, Callback extends SentCallback<T>> void sendApiMethodAsync(Method method, Callback callback) {
        //noinspection Convert2Lambda
        exe.submit(new Runnable() {
            @Override
            public void run() {
                try {
                    method.validate();
                    String url = getBaseUrl() + method.getMethod();
                    HttpPost httppost = new HttpPost(url);
                    httppost.setConfig(requestConfig);
                    httppost.addHeader("charset", StandardCharsets.UTF_8.name());
                    httppost.setEntity(new StringEntity(objectMapper.writeValueAsString(method), ContentType.APPLICATION_JSON));
                    try (CloseableHttpResponse response = httpclient.execute(httppost)) {
                        HttpEntity ht = response.getEntity();
                        BufferedHttpEntity buf = new BufferedHttpEntity(ht);
                        String responseContent = EntityUtils.toString(buf, StandardCharsets.UTF_8);
                        try {
                            callback.onResult(method, method.deserializeResponse(responseContent));
                        } catch (TelegramApiRequestException e) {
                            callback.onError(method, e);
                        }
                    }
                } catch (IOException | TelegramApiValidationException e) {
                    callback.onException(method, e);
                }

            }
        });
    }

    @Override
    protected final <T extends Serializable, Method extends BotApiMethod<T>> T sendApiMethod(Method method) throws TelegramApiException {
        method.validate();
        String responseContent;
        try {
            String url = getBaseUrl() + method.getMethod();
            HttpPost httppost = new HttpPost(url);
            httppost.setConfig(requestConfig);
            httppost.addHeader("charset", StandardCharsets.UTF_8.name());
            httppost.setEntity(new StringEntity(objectMapper.writeValueAsString(method), ContentType.APPLICATION_JSON));
            try (CloseableHttpResponse response = httpclient.execute(httppost)) {
                HttpEntity ht = response.getEntity();
                BufferedHttpEntity buf = new BufferedHttpEntity(ht);
                responseContent = EntityUtils.toString(buf, StandardCharsets.UTF_8);
            }
        } catch (IOException e) {
            throw new TelegramApiException("Unable to execute " + method.getMethod() + " method", e);
        }

        return method.deserializeResponse(responseContent);
    }

    protected String getBaseUrl() {
        return options.getBaseUrl() + getBotToken() + "/";
    }
}
