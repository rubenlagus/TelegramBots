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
import org.telegram.telegrambots.api.methods.stickers.AddStickerToSet;
import org.telegram.telegrambots.api.methods.stickers.CreateNewStickerSet;
import org.telegram.telegrambots.api.methods.stickers.UploadStickerFile;
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
        String tempFileName = Long.toString(System.currentTimeMillis());
        return downloadToTemporaryFileWrappingExceptions(url, tempFileName);
    }

    public final java.io.File downloadFile(File file) throws TelegramApiException {
        assertParamNotNull(file, "file");
        String url = file.getFileUrl(getBotToken());
        String tempFileName = file.getFileId();
        return downloadToTemporaryFileWrappingExceptions(url, tempFileName);
    }

    public final void downloadFileAsync(String filePath, DownloadFileCallback<String> callback) throws TelegramApiException {
        if(filePath == null || filePath.isEmpty()){
            throw new TelegramApiException("Parameter filePath can not be null");
        }
        assertParamNotNull(callback, "callback");
        String url = File.getFileUrl(getBotToken(), filePath);
        String tempFileName = Long.toString(System.currentTimeMillis());
        exe.submit(getDownloadFileAsyncJob(filePath, callback, url, tempFileName));
    }

    public final void downloadFileAsync(File file, DownloadFileCallback<File> callback) throws TelegramApiException {
        assertParamNotNull(file, "file");
        assertParamNotNull(callback, "callback");
        String url = file.getFileUrl(getBotToken());
        String tempFileName = file.getFileId();
        exe.submit(getDownloadFileAsyncJob(file, callback, url, tempFileName));
    }

    // Specific Send Requests

    @Override
    public final Message sendDocument(SendDocument sendDocument) throws TelegramApiException {
        assertParamNotNull(sendDocument, "sendDocument");

        sendDocument.validate();
        try {
            String url = getBaseUrl() + SendDocument.PATH;
            HttpPost httppost = configuredHttpPost(url);

            MultipartEntityBuilder builder = MultipartEntityBuilder.create();
            builder.setCharset(StandardCharsets.UTF_8);
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

            return sendDocument.deserializeResponse(sendHttpPostRequest(httppost));
        } catch (IOException e) {
            throw new TelegramApiException("Unable to send document", e);
        }
    }

    @Override
    public final Message sendPhoto(SendPhoto sendPhoto) throws TelegramApiException {
        assertParamNotNull(sendPhoto, "sendPhoto");

        sendPhoto.validate();
        try {
            String url = getBaseUrl() + SendPhoto.PATH;
            HttpPost httppost = configuredHttpPost(url);

            MultipartEntityBuilder builder = MultipartEntityBuilder.create();
            builder.setCharset(StandardCharsets.UTF_8);
            builder.addTextBody(SendPhoto.CHATID_FIELD, sendPhoto.getChatId(), TEXT_PLAIN_CONTENT_TYPE);
            if (sendPhoto.isNewPhoto()) {
                if (sendPhoto.getNewPhotoFile() != null) {
                    builder.addBinaryBody(SendPhoto.PHOTO_FIELD, sendPhoto.getNewPhotoFile());
                } else if (sendPhoto.getNewPhotoStream() != null) {
                    builder.addBinaryBody(SendPhoto.PHOTO_FIELD, sendPhoto.getNewPhotoStream(), ContentType.APPLICATION_OCTET_STREAM, sendPhoto.getPhotoName());
                } else {
                    builder.addBinaryBody(SendPhoto.PHOTO_FIELD, new java.io.File(sendPhoto.getPhoto()), ContentType.APPLICATION_OCTET_STREAM, sendPhoto.getPhotoName());
                }
            } else {
                builder.addTextBody(SendPhoto.PHOTO_FIELD, sendPhoto.getPhoto(), TEXT_PLAIN_CONTENT_TYPE);
            }
            if (sendPhoto.getReplyMarkup() != null) {
                builder.addTextBody(SendPhoto.REPLYMARKUP_FIELD, objectMapper.writeValueAsString(sendPhoto.getReplyMarkup()), TEXT_PLAIN_CONTENT_TYPE);
            }
            if (sendPhoto.getReplyToMessageId() != null) {
                builder.addTextBody(SendPhoto.REPLYTOMESSAGEID_FIELD, sendPhoto.getReplyToMessageId().toString(), TEXT_PLAIN_CONTENT_TYPE);
            }
            if (sendPhoto.getCaption() != null) {
                builder.addTextBody(SendPhoto.CAPTION_FIELD, sendPhoto.getCaption(), TEXT_PLAIN_CONTENT_TYPE);
            }
            if (sendPhoto.getDisableNotification() != null) {
                builder.addTextBody(SendPhoto.DISABLENOTIFICATION_FIELD, sendPhoto.getDisableNotification().toString(), TEXT_PLAIN_CONTENT_TYPE);
            }
            HttpEntity multipart = builder.build();
            httppost.setEntity(multipart);

            return sendPhoto.deserializeResponse(sendHttpPostRequest(httppost));
        } catch (IOException e) {
            throw new TelegramApiException("Unable to send photo", e);
        }
    }

    @Override
    public final Message sendVideo(SendVideo sendVideo) throws TelegramApiException {
        assertParamNotNull(sendVideo, "sendVideo");

        sendVideo.validate();
        try {
            String url = getBaseUrl() + SendVideo.PATH;
            HttpPost httppost = configuredHttpPost(url);

            MultipartEntityBuilder builder = MultipartEntityBuilder.create();
            builder.setCharset(StandardCharsets.UTF_8);
            builder.addTextBody(SendVideo.CHATID_FIELD, sendVideo.getChatId(), TEXT_PLAIN_CONTENT_TYPE);
            if (sendVideo.isNewVideo()) {
                if (sendVideo.getNewVideoFile() != null) {
                    builder.addBinaryBody(SendVideo.VIDEO_FIELD, sendVideo.getNewVideoFile());
                } else if (sendVideo.getNewVideoStream() != null) {
                    builder.addBinaryBody(SendVideo.VIDEO_FIELD, sendVideo.getNewVideoStream(), ContentType.APPLICATION_OCTET_STREAM, sendVideo.getVideoName());
                } else {
                    builder.addBinaryBody(SendVideo.VIDEO_FIELD, new java.io.File(sendVideo.getVideo()), ContentType.APPLICATION_OCTET_STREAM, sendVideo.getVideoName());
                }
            } else {
                builder.addTextBody(SendVideo.VIDEO_FIELD, sendVideo.getVideo(), TEXT_PLAIN_CONTENT_TYPE);
            }
            if (sendVideo.getReplyMarkup() != null) {
                builder.addTextBody(SendVideo.REPLYMARKUP_FIELD, objectMapper.writeValueAsString(sendVideo.getReplyMarkup()), TEXT_PLAIN_CONTENT_TYPE);
            }
            if (sendVideo.getReplyToMessageId() != null) {
                builder.addTextBody(SendVideo.REPLYTOMESSAGEID_FIELD, sendVideo.getReplyToMessageId().toString(), TEXT_PLAIN_CONTENT_TYPE);
            }
            if (sendVideo.getCaption() != null) {
                builder.addTextBody(SendVideo.CAPTION_FIELD, sendVideo.getCaption(), TEXT_PLAIN_CONTENT_TYPE);
            }
            if (sendVideo.getDuration() != null) {
                builder.addTextBody(SendVideo.DURATION_FIELD, sendVideo.getDuration().toString(), TEXT_PLAIN_CONTENT_TYPE);
            }
            if (sendVideo.getWidth() != null) {
                builder.addTextBody(SendVideo.WIDTH_FIELD, sendVideo.getWidth().toString(), TEXT_PLAIN_CONTENT_TYPE);
            }
            if (sendVideo.getHeight() != null) {
                builder.addTextBody(SendVideo.HEIGHT_FIELD, sendVideo.getHeight().toString(), TEXT_PLAIN_CONTENT_TYPE);
            }
            if (sendVideo.getDisableNotification() != null) {
                builder.addTextBody(SendVideo.DISABLENOTIFICATION_FIELD, sendVideo.getDisableNotification().toString(), TEXT_PLAIN_CONTENT_TYPE);
            }
            HttpEntity multipart = builder.build();
            httppost.setEntity(multipart);

            return sendVideo.deserializeResponse(sendHttpPostRequest(httppost));
        } catch (IOException e) {
            throw new TelegramApiException("Unable to send video", e);
        }
    }

    @Override
    public final Message sendVideoNote(SendVideoNote sendVideoNote) throws TelegramApiException {
        assertParamNotNull(sendVideoNote, "sendVideoNote");

        sendVideoNote.validate();
        try {
            String url = getBaseUrl() + SendVideoNote.PATH;
            HttpPost httppost = configuredHttpPost(url);

            MultipartEntityBuilder builder = MultipartEntityBuilder.create();
            builder.setCharset(StandardCharsets.UTF_8);
            builder.addTextBody(SendVideoNote.CHATID_FIELD, sendVideoNote.getChatId(), TEXT_PLAIN_CONTENT_TYPE);
            if (sendVideoNote.isNewVideoNote()) {
                if (sendVideoNote.getNewVideoNoteFile() != null) {
                    builder.addBinaryBody(SendVideoNote.VIDEONOTE_FIELD, sendVideoNote.getNewVideoNoteFile());
                } else if (sendVideoNote.getNewVideoNoteStream() != null) {
                    builder.addBinaryBody(SendVideoNote.VIDEONOTE_FIELD, sendVideoNote.getNewVideoNoteStream(), ContentType.APPLICATION_OCTET_STREAM, sendVideoNote.getVideoNoteName());
                } else {
                    builder.addBinaryBody(SendVideoNote.VIDEONOTE_FIELD, new java.io.File(sendVideoNote.getVideoNote()), ContentType.APPLICATION_OCTET_STREAM, sendVideoNote.getVideoNoteName());
                }
            } else {
                builder.addTextBody(SendVideoNote.VIDEONOTE_FIELD, sendVideoNote.getVideoNote(), TEXT_PLAIN_CONTENT_TYPE);
            }
            if (sendVideoNote.getReplyMarkup() != null) {
                builder.addTextBody(SendVideoNote.REPLYMARKUP_FIELD, objectMapper.writeValueAsString(sendVideoNote.getReplyMarkup()), TEXT_PLAIN_CONTENT_TYPE);
            }
            if (sendVideoNote.getReplyToMessageId() != null) {
                builder.addTextBody(SendVideoNote.REPLYTOMESSAGEID_FIELD, sendVideoNote.getReplyToMessageId().toString(), TEXT_PLAIN_CONTENT_TYPE);
            }
            if (sendVideoNote.getDuration() != null) {
                builder.addTextBody(SendVideoNote.DURATION_FIELD, sendVideoNote.getDuration().toString(), TEXT_PLAIN_CONTENT_TYPE);
            }
            if (sendVideoNote.getLength() != null) {
                builder.addTextBody(SendVideoNote.LENGTH_FIELD, sendVideoNote.getLength().toString(), TEXT_PLAIN_CONTENT_TYPE);
            }
            if (sendVideoNote.getDisableNotification() != null) {
                builder.addTextBody(SendVideoNote.DISABLENOTIFICATION_FIELD, sendVideoNote.getDisableNotification().toString(), TEXT_PLAIN_CONTENT_TYPE);
            }
            HttpEntity multipart = builder.build();
            httppost.setEntity(multipart);


            return sendVideoNote.deserializeResponse(sendHttpPostRequest(httppost));
        } catch (IOException e) {
            throw new TelegramApiException("Unable to send video note", e);
        }
    }

    @Override
    public final Message sendSticker(SendSticker sendSticker) throws TelegramApiException {
        assertParamNotNull(sendSticker, "sendSticker");

        sendSticker.validate();
        try {
            String url = getBaseUrl() + SendSticker.PATH;
            HttpPost httppost = configuredHttpPost(url);

            MultipartEntityBuilder builder = MultipartEntityBuilder.create();
            builder.setCharset(StandardCharsets.UTF_8);
            builder.addTextBody(SendSticker.CHATID_FIELD, sendSticker.getChatId(), TEXT_PLAIN_CONTENT_TYPE);
            if (sendSticker.isNewSticker()) {
                if (sendSticker.getNewStickerFile() != null) {
                    builder.addBinaryBody(SendSticker.STICKER_FIELD, sendSticker.getNewStickerFile());
                } else if (sendSticker.getNewStickerStream() != null) {
                    builder.addBinaryBody(SendSticker.STICKER_FIELD, sendSticker.getNewStickerStream(), ContentType.APPLICATION_OCTET_STREAM, sendSticker.getStickerName());
                } else {
                    builder.addBinaryBody(SendSticker.STICKER_FIELD, new java.io.File(sendSticker.getSticker()), ContentType.APPLICATION_OCTET_STREAM, sendSticker.getStickerName());
                }
            } else {
                builder.addTextBody(SendSticker.STICKER_FIELD, sendSticker.getSticker(), TEXT_PLAIN_CONTENT_TYPE);
            }
            if (sendSticker.getReplyMarkup() != null) {
                builder.addTextBody(SendSticker.REPLYMARKUP_FIELD, objectMapper.writeValueAsString(sendSticker.getReplyMarkup()), TEXT_PLAIN_CONTENT_TYPE);
            }
            if (sendSticker.getReplyToMessageId() != null) {
                builder.addTextBody(SendSticker.REPLYTOMESSAGEID_FIELD, sendSticker.getReplyToMessageId().toString(), TEXT_PLAIN_CONTENT_TYPE);
            }
            if (sendSticker.getDisableNotification() != null) {
                builder.addTextBody(SendSticker.DISABLENOTIFICATION_FIELD, sendSticker.getDisableNotification().toString(), TEXT_PLAIN_CONTENT_TYPE);
            }
            HttpEntity multipart = builder.build();
            httppost.setEntity(multipart);

            return sendSticker.deserializeResponse(sendHttpPostRequest(httppost));
        } catch (IOException e) {
            throw new TelegramApiException("Unable to send sticker", e);
        }
    }

    /**
     * Sends a file using Send Audio method (https://core.telegram.org/bots/api#sendaudio)
     * @param sendAudio Information to send
     * @return If success, the sent Message is returned
     * @throws TelegramApiException If there is any error sending the audio
     */
    @Override
    public final Message sendAudio(SendAudio sendAudio) throws TelegramApiException {
        assertParamNotNull(sendAudio, "sendAudio");
        sendAudio.validate();
        try {
            String url = getBaseUrl() + SendAudio.PATH;
            HttpPost httppost = configuredHttpPost(url);
            MultipartEntityBuilder builder = MultipartEntityBuilder.create();
            builder.setCharset(StandardCharsets.UTF_8);
            builder.addTextBody(SendAudio.CHATID_FIELD, sendAudio.getChatId(), TEXT_PLAIN_CONTENT_TYPE);
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
                builder.addTextBody(SendAudio.REPLYTOMESSAGEID_FIELD, sendAudio.getReplyToMessageId().toString(), TEXT_PLAIN_CONTENT_TYPE);
            }
            if (sendAudio.getPerformer() != null) {
                builder.addTextBody(SendAudio.PERFOMER_FIELD, sendAudio.getPerformer(), TEXT_PLAIN_CONTENT_TYPE);
            }
            if (sendAudio.getTitle() != null) {
                builder.addTextBody(SendAudio.TITLE_FIELD, sendAudio.getTitle(), TEXT_PLAIN_CONTENT_TYPE);
            }
            if(sendAudio.getDuration() != null){
                builder.addTextBody(SendAudio.DURATION_FIELD, sendAudio.getDuration().toString(), TEXT_PLAIN_CONTENT_TYPE);
            }
            if (sendAudio.getDisableNotification() != null) {
                builder.addTextBody(SendAudio.DISABLENOTIFICATION_FIELD, sendAudio.getDisableNotification().toString(), TEXT_PLAIN_CONTENT_TYPE);
            }
            if (sendAudio.getCaption() != null) {
                builder.addTextBody(SendAudio.CAPTION_FIELD, sendAudio.getCaption(), TEXT_PLAIN_CONTENT_TYPE);
            }
            HttpEntity multipart = builder.build();
            httppost.setEntity(multipart);


            return sendAudio.deserializeResponse(sendHttpPostRequest(httppost));
        } catch (IOException e) {
            throw new TelegramApiException("Unable to send sticker", e);
        }
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
        assertParamNotNull(sendVoice, "sendVoice");
        sendVoice.validate();
        try {
            String url = getBaseUrl() + SendVoice.PATH;
            HttpPost httppost = configuredHttpPost(url);
            MultipartEntityBuilder builder = MultipartEntityBuilder.create();
            builder.setCharset(StandardCharsets.UTF_8);
            builder.addTextBody(SendVoice.CHATID_FIELD, sendVoice.getChatId(), TEXT_PLAIN_CONTENT_TYPE);
            if (sendVoice.isNewVoice()) {
                if (sendVoice.getNewVoiceFile() != null) {
                    builder.addBinaryBody(SendVoice.VOICE_FIELD, sendVoice.getNewVoiceFile());
                } else if (sendVoice.getNewVoiceStream() != null) {
                    builder.addBinaryBody(SendVoice.VOICE_FIELD, sendVoice.getNewVoiceStream(), ContentType.APPLICATION_OCTET_STREAM, sendVoice.getVoiceName());
                } else {
                    builder.addBinaryBody(SendVoice.VOICE_FIELD, new java.io.File(sendVoice.getVoice()), ContentType.create("audio/ogg"), sendVoice.getVoiceName());
                }
            } else {
                builder.addTextBody(SendVoice.VOICE_FIELD, sendVoice.getVoice(), TEXT_PLAIN_CONTENT_TYPE);
            }
            if (sendVoice.getReplyMarkup() != null) {
                builder.addTextBody(SendVoice.REPLYMARKUP_FIELD, objectMapper.writeValueAsString(sendVoice.getReplyMarkup()), TEXT_PLAIN_CONTENT_TYPE);
            }
            if (sendVoice.getReplyToMessageId() != null) {
                builder.addTextBody(SendVoice.REPLYTOMESSAGEID_FIELD, sendVoice.getReplyToMessageId().toString(), TEXT_PLAIN_CONTENT_TYPE);
            }
            if (sendVoice.getDisableNotification() != null) {
                builder.addTextBody(SendVoice.DISABLENOTIFICATION_FIELD, sendVoice.getDisableNotification().toString(), TEXT_PLAIN_CONTENT_TYPE);
            }
            if (sendVoice.getDuration() != null) {
                builder.addTextBody(SendVoice.DURATION_FIELD, sendVoice.getDuration().toString(), TEXT_PLAIN_CONTENT_TYPE);
            }
            if (sendVoice.getCaption() != null) {
                builder.addTextBody(SendVoice.CAPTION_FIELD, sendVoice.getCaption(), TEXT_PLAIN_CONTENT_TYPE);
            }
            HttpEntity multipart = builder.build();
            httppost.setEntity(multipart);

            return sendVoice.deserializeResponse(sendHttpPostRequest(httppost));
        } catch (IOException e) {
            throw new TelegramApiException("Unable to send voice", e);
        }
    }

    @Override
    public Boolean setChatPhoto(SetChatPhoto setChatPhoto) throws TelegramApiException {
        assertParamNotNull(setChatPhoto, "setChatPhoto");
        setChatPhoto.validate();

        try {
            String url = getBaseUrl() + SetChatPhoto.PATH;
            HttpPost httppost = configuredHttpPost(url);

            MultipartEntityBuilder builder = MultipartEntityBuilder.create();
            builder.setCharset(StandardCharsets.UTF_8);
            builder.addTextBody(SetChatPhoto.CHATID_FIELD, setChatPhoto.getChatId(), TEXT_PLAIN_CONTENT_TYPE);
            if (setChatPhoto.getPhoto() != null) {
                builder.addBinaryBody(SetChatPhoto.PHOTO_FIELD, setChatPhoto.getPhoto());
            } else if (setChatPhoto.getPhotoStream() != null) {
                builder.addBinaryBody(SetChatPhoto.PHOTO_FIELD, setChatPhoto.getPhotoStream(), ContentType.APPLICATION_OCTET_STREAM, setChatPhoto.getPhotoName());
            }
            HttpEntity multipart = builder.build();
            httppost.setEntity(multipart);

            return setChatPhoto.deserializeResponse(sendHttpPostRequest(httppost));
        } catch (IOException e) {
            throw new TelegramApiException("Unable to set chat photo", e);
        }
    }


    @Override
    public Boolean addStickerToSet(AddStickerToSet addStickerToSet) throws TelegramApiException {
        assertParamNotNull(addStickerToSet, "addStickerToSet");
        addStickerToSet.validate();
        try {
            String url = getBaseUrl() + AddStickerToSet.PATH;
            HttpPost httppost = configuredHttpPost(url);
            MultipartEntityBuilder builder = MultipartEntityBuilder.create();
            builder.setCharset(StandardCharsets.UTF_8);
            builder.addTextBody(AddStickerToSet.USERID_FIELD, addStickerToSet.getUserId().toString(), TEXT_PLAIN_CONTENT_TYPE);
            builder.addTextBody(AddStickerToSet.NAME_FIELD, addStickerToSet.getName(), TEXT_PLAIN_CONTENT_TYPE);
            builder.addTextBody(AddStickerToSet.EMOJIS_FIELD, addStickerToSet.getEmojis(), TEXT_PLAIN_CONTENT_TYPE);
            if (addStickerToSet.isNewPngSticker()) {
                if (addStickerToSet.getPngStickerFile() != null) {
                    builder.addBinaryBody(AddStickerToSet.PNGSTICKER_FIELD, addStickerToSet.getPngStickerFile());
                } else if (addStickerToSet.getPngStickerStream() != null) {
                    builder.addBinaryBody(AddStickerToSet.PNGSTICKER_FIELD, addStickerToSet.getPngStickerStream(), ContentType.APPLICATION_OCTET_STREAM, addStickerToSet.getPngStickerName());
                } else {
                    builder.addBinaryBody(AddStickerToSet.PNGSTICKER_FIELD, new java.io.File(addStickerToSet.getPngSticker()), ContentType.create("image/png"), addStickerToSet.getPngStickerName());
                }
            } else {
                builder.addTextBody(AddStickerToSet.PNGSTICKER_FIELD, addStickerToSet.getPngSticker(), TEXT_PLAIN_CONTENT_TYPE);
            }
            if (addStickerToSet.getMaskPosition() != null) {
                builder.addTextBody(AddStickerToSet.MASKPOSITION_FIELD, objectMapper.writeValueAsString(addStickerToSet.getMaskPosition()), TEXT_PLAIN_CONTENT_TYPE);
            }
            HttpEntity multipart = builder.build();
            httppost.setEntity(multipart);

            return addStickerToSet.deserializeResponse(sendHttpPostRequest(httppost));
        } catch (IOException e) {
            throw new TelegramApiException("Unable to add sticker to set", e);
        }
    }

    @Override
    public Boolean createNewStickerSet(CreateNewStickerSet createNewStickerSet) throws TelegramApiException {
        assertParamNotNull(createNewStickerSet, "createNewStickerSet");
        createNewStickerSet.validate();
        try {
            String url = getBaseUrl() + CreateNewStickerSet.PATH;
            HttpPost httppost = configuredHttpPost(url);
            MultipartEntityBuilder builder = MultipartEntityBuilder.create();
            builder.setCharset(StandardCharsets.UTF_8);
            builder.addTextBody(CreateNewStickerSet.USERID_FIELD, createNewStickerSet.getUserId().toString(), TEXT_PLAIN_CONTENT_TYPE);
            builder.addTextBody(CreateNewStickerSet.NAME_FIELD, createNewStickerSet.getName(), TEXT_PLAIN_CONTENT_TYPE);
            builder.addTextBody(CreateNewStickerSet.TITLE_FIELD, createNewStickerSet.getTitle(), TEXT_PLAIN_CONTENT_TYPE);
            builder.addTextBody(CreateNewStickerSet.EMOJIS_FIELD, createNewStickerSet.getEmojis(), TEXT_PLAIN_CONTENT_TYPE);
            builder.addTextBody(CreateNewStickerSet.CONTAINSMASKS_FIELD, createNewStickerSet.getContainsMasks().toString(), TEXT_PLAIN_CONTENT_TYPE);
            if (createNewStickerSet.isNewPngSticker()) {
                if (createNewStickerSet.getPngStickerFile() != null) {
                    builder.addBinaryBody(CreateNewStickerSet.PNGSTICKER_FIELD, createNewStickerSet.getPngStickerFile());
                } else if (createNewStickerSet.getPngStickerStream() != null) {
                    builder.addBinaryBody(CreateNewStickerSet.PNGSTICKER_FIELD, createNewStickerSet.getPngStickerStream(), ContentType.APPLICATION_OCTET_STREAM, createNewStickerSet.getPngStickerName());
                } else {
                    builder.addBinaryBody(CreateNewStickerSet.PNGSTICKER_FIELD, new java.io.File(createNewStickerSet.getPngSticker()), ContentType.create("image/png"), createNewStickerSet.getPngStickerName());
                }
            } else {
                builder.addTextBody(CreateNewStickerSet.PNGSTICKER_FIELD, createNewStickerSet.getPngSticker(), TEXT_PLAIN_CONTENT_TYPE);
            }
            if (createNewStickerSet.getMaskPosition() != null) {
                builder.addTextBody(CreateNewStickerSet.MASKPOSITION_FIELD, objectMapper.writeValueAsString(createNewStickerSet.getMaskPosition()), TEXT_PLAIN_CONTENT_TYPE);
            }
            HttpEntity multipart = builder.build();
            httppost.setEntity(multipart);

            return createNewStickerSet.deserializeResponse(sendHttpPostRequest(httppost));
        } catch (IOException e) {
            throw new TelegramApiException("Unable to create new sticker set", e);
        }
    }

    @Override
    public File uploadStickerFile(UploadStickerFile uploadStickerFile) throws TelegramApiException {
        assertParamNotNull(uploadStickerFile, "uploadStickerFile");
        uploadStickerFile.validate();
        try {
            String url = getBaseUrl() + UploadStickerFile.PATH;
            HttpPost httppost = configuredHttpPost(url);
            MultipartEntityBuilder builder = MultipartEntityBuilder.create();
            builder.setCharset(StandardCharsets.UTF_8);
            builder.addTextBody(UploadStickerFile.USERID_FIELD, uploadStickerFile.getUserId().toString(), TEXT_PLAIN_CONTENT_TYPE);
            if (uploadStickerFile.getNewPngStickerFile() != null) {
                builder.addBinaryBody(UploadStickerFile.PNGSTICKER_FIELD, uploadStickerFile.getNewPngStickerFile());
            } else if (uploadStickerFile.getNewPngStickerStream() != null) {
                builder.addBinaryBody(UploadStickerFile.PNGSTICKER_FIELD, uploadStickerFile.getNewPngStickerStream(), ContentType.APPLICATION_OCTET_STREAM, uploadStickerFile.getNewPngStickerName());
            }
            HttpEntity multipart = builder.build();
            httppost.setEntity(multipart);

            return uploadStickerFile.deserializeResponse(sendHttpPostRequest(httppost));
        } catch (IOException e) {
            throw new TelegramApiException("Unable to upload new sticker file", e);
        }
    }

    // Simplified methods

    @Override
    protected final <T extends Serializable, Method extends BotApiMethod<T>, Callback extends SentCallback<T>> void sendApiMethodAsync(Method method, Callback callback) {
        //noinspection Convert2Lambda
        exe.submit(new Runnable() {
            @Override
            public void run() {
                try {
                    String responseContent = sendMethodRequest(method);
                    try {
                        callback.onResult(method, method.deserializeResponse(responseContent));
                    } catch (TelegramApiRequestException e) {
                        callback.onError(method, e);
                    }
                } catch (IOException | TelegramApiValidationException e) {
                    callback.onException(method, e);
                }

            }
        });
    }

    @Override
    protected final <T extends Serializable, Method extends BotApiMethod<T>> T sendApiMethod(Method method) throws TelegramApiException {
        try {
            String responseContent = sendMethodRequest(method);
            return method.deserializeResponse(responseContent);
        } catch (IOException e) {
            throw new TelegramApiException("Unable to execute " + method.getMethod() + " method", e);
        }
    }

    private <T> Runnable getDownloadFileAsyncJob(T fileIdentifier, DownloadFileCallback<T> callback, String url, String tempFileName) {
        //noinspection Convert2Lambda
        return new Runnable() {
            @Override
            public void run() {
                try {
                    callback.onResult(fileIdentifier, downloadToTemporaryFile(url, tempFileName));
                } catch (MalformedURLException e) {
                    callback.onException(fileIdentifier, new TelegramApiException("Wrong url for file: " + url));
                } catch (IOException e) {
                    callback.onException(fileIdentifier, new TelegramApiRequestException("Error downloading the file", e));
                }
            }
        };
    }

    private java.io.File downloadToTemporaryFileWrappingExceptions(String url, String tempFileName) throws TelegramApiException {
        try {
            return downloadToTemporaryFile(url, tempFileName);
        } catch (MalformedURLException e) {
            throw new TelegramApiException("Wrong url for file: " + url);
        } catch (IOException e) {
            throw new TelegramApiRequestException("Error downloading the file", e);
        }
    }

    private java.io.File downloadToTemporaryFile(String url, String tempFileName) throws IOException {
        java.io.File output = java.io.File.createTempFile(tempFileName, ".tmp");
        FileUtils.copyURLToFile(new URL(url), output);
        return output;
    }

    private <T extends Serializable, Method extends BotApiMethod<T>> String sendMethodRequest(Method method) throws TelegramApiValidationException, IOException {
        method.validate();
        String url = getBaseUrl() + method.getMethod();
        HttpPost httppost = configuredHttpPost(url);
        httppost.addHeader("charset", StandardCharsets.UTF_8.name());
        httppost.setEntity(new StringEntity(objectMapper.writeValueAsString(method), ContentType.APPLICATION_JSON));
        return sendHttpPostRequest(httppost);
    }

    private String sendHttpPostRequest(HttpPost httppost) throws IOException {
        try (CloseableHttpResponse response = httpclient.execute(httppost)) {
            HttpEntity ht = response.getEntity();
            BufferedHttpEntity buf = new BufferedHttpEntity(ht);
            return EntityUtils.toString(buf, StandardCharsets.UTF_8);
        }
    }

    private HttpPost configuredHttpPost(String url) {
        HttpPost httppost = new HttpPost(url);
        httppost.setConfig(requestConfig);
        return httppost;
    }

    protected String getBaseUrl() {
        return options.getBaseUrl() + getBotToken() + "/";
    }

    private void assertParamNotNull(Object param, String paramName) throws TelegramApiException {
        if (param == null) {
            throw new TelegramApiException("Parameter " + paramName + " can not be null");
        }
    }
}
