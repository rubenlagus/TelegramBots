package org.telegram.telegrambots.bots;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpEntity;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.util.EntityUtils;
import org.telegram.telegrambots.facilities.TelegramHttpClientBuilder;
import org.telegram.telegrambots.facilities.filedownloader.TelegramFileDownloader;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.groupadministration.SetChatPhoto;
import org.telegram.telegrambots.meta.api.methods.send.SendAnimation;
import org.telegram.telegrambots.meta.api.methods.send.SendAudio;
import org.telegram.telegrambots.meta.api.methods.send.SendDocument;
import org.telegram.telegrambots.meta.api.methods.send.SendMediaGroup;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.methods.send.SendSticker;
import org.telegram.telegrambots.meta.api.methods.send.SendVideo;
import org.telegram.telegrambots.meta.api.methods.send.SendVideoNote;
import org.telegram.telegrambots.meta.api.methods.send.SendVoice;
import org.telegram.telegrambots.meta.api.methods.stickers.AddStickerToSet;
import org.telegram.telegrambots.meta.api.methods.stickers.CreateNewStickerSet;
import org.telegram.telegrambots.meta.api.methods.stickers.SetStickerSetThumb;
import org.telegram.telegrambots.meta.api.methods.stickers.UploadStickerFile;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageMedia;
import org.telegram.telegrambots.meta.api.objects.File;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.media.InputMedia;
import org.telegram.telegrambots.meta.api.objects.media.InputMediaAnimation;
import org.telegram.telegrambots.meta.api.objects.media.InputMediaAudio;
import org.telegram.telegrambots.meta.api.objects.media.InputMediaDocument;
import org.telegram.telegrambots.meta.api.objects.media.InputMediaVideo;
import org.telegram.telegrambots.meta.api.objects.stickers.InputSticker;
import org.telegram.telegrambots.meta.bots.AbsSender;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.exceptions.TelegramApiRequestException;
import org.telegram.telegrambots.meta.exceptions.TelegramApiValidationException;
import org.telegram.telegrambots.meta.updateshandlers.DownloadFileCallback;
import org.telegram.telegrambots.meta.updateshandlers.SentCallback;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.telegram.telegrambots.Constants.SOCKET_TIMEOUT;

/**
 * @author Ruben Bermudez
 * @version 1.0
 * Implementation of all the methods needed to interact with Telegram Servers
 */
@SuppressWarnings({"unused", "SameParameterValue"})
@Slf4j
public abstract class DefaultAbsSender extends AbsSender {
    private static final ContentType TEXT_PLAIN_CONTENT_TYPE = ContentType.create("text/plain", StandardCharsets.UTF_8);

    protected final ExecutorService exe;
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final DefaultBotOptions options;
    private final CloseableHttpClient httpClient;
    private final RequestConfig requestConfig;
    private final TelegramFileDownloader telegramFileDownloader;
    private final String botToken;

    /**
     * If this is used getBotToken has to be overridden in order to return the bot token!
     * @deprecated Overwriting the getBotToken() method is deprecated. Use the constructor instead
     */
    @Deprecated
    protected DefaultAbsSender(DefaultBotOptions options) {
        this(options, null);
    }

    protected DefaultAbsSender(DefaultBotOptions options, String botToken) {
        super();
        this.botToken = botToken;

        this.exe = Executors.newFixedThreadPool(options.getMaxThreads());
        this.options = options;

        httpClient = TelegramHttpClientBuilder.build(options);
        this.telegramFileDownloader = new TelegramFileDownloader(httpClient, this::getBotToken);
        configureHttpContext();

        final RequestConfig configFromOptions = options.getRequestConfig();
        if (configFromOptions != null) {
            this.requestConfig = configFromOptions;
        } else {
            this.requestConfig = RequestConfig.copy(RequestConfig.custom().build())
                    .setSocketTimeout(SOCKET_TIMEOUT)
                    .setConnectTimeout(SOCKET_TIMEOUT)
                    .setConnectionRequestTimeout(SOCKET_TIMEOUT).build();
        }
    }

    /**
     * Returns the token of the bot to be able to perform Telegram Api Requests
     * @return Token of the bot
     * @deprecated Overriding this method is deprecated. Pass to constructor instead
     */
    @Deprecated
    public String getBotToken() {
        return botToken;
    }

    public final DefaultBotOptions getOptions() {
        return options;
    }

    public String getBaseUrl() {
        return options.getBaseUrl() + getBotToken() + "/";
    }

    // Send Requests

    public final java.io.File downloadFile(String filePath) throws TelegramApiException {
        return telegramFileDownloader.downloadFile(filePath);
    }

    public final java.io.File downloadFile(File file) throws TelegramApiException {
        return telegramFileDownloader.downloadFile(file);
    }

    public final java.io.File downloadFile(File file, java.io.File outputFile) throws TelegramApiException {
        return telegramFileDownloader.downloadFile(file, outputFile);
    }

    public final java.io.File downloadFile(String filePath, java.io.File outputFile) throws TelegramApiException {
        return telegramFileDownloader.downloadFile(filePath, outputFile);
    }

    public final void downloadFileAsync(String filePath, DownloadFileCallback<String> callback) throws TelegramApiException {
        telegramFileDownloader.downloadFileAsync(filePath, callback);
    }

    public final void downloadFileAsync(File file, DownloadFileCallback<File> callback) throws TelegramApiException {
        telegramFileDownloader.downloadFileAsync(file, callback);
    }

    public final InputStream downloadFileAsStream(String filePath) throws TelegramApiException {
        return telegramFileDownloader.downloadFileAsStream(filePath);
    }

    public final InputStream downloadFileAsStream(File file) throws TelegramApiException {
        return telegramFileDownloader.downloadFileAsStream(file);
    }

    // Specific Send Requests

    @Override
    public final Message execute(SendDocument sendDocument) throws TelegramApiException {
        assertParamNotNull(sendDocument, "sendDocument");

        sendDocument.validate();
        try {
            String url = getBaseUrl() + SendDocument.PATH;
            HttpPost httppost = configuredHttpPost(url);

            MultipartEntityBuilder builder = MultipartEntityBuilder.create();
            builder.setLaxMode();
            builder.setCharset(StandardCharsets.UTF_8);
            builder.addTextBody(SendDocument.CHATID_FIELD, sendDocument.getChatId(), TEXT_PLAIN_CONTENT_TYPE);

            addInputFile(builder, sendDocument.getDocument(), SendDocument.DOCUMENT_FIELD, true);

            if (sendDocument.getReplyMarkup() != null) {
                builder.addTextBody(SendDocument.REPLYMARKUP_FIELD, objectMapper.writeValueAsString(sendDocument.getReplyMarkup()), TEXT_PLAIN_CONTENT_TYPE);
            }
            if (sendDocument.getReplyToMessageId() != null) {
                builder.addTextBody(SendDocument.REPLYTOMESSAGEID_FIELD, sendDocument.getReplyToMessageId().toString(), TEXT_PLAIN_CONTENT_TYPE);
            }
            if (sendDocument.getMessageThreadId() != null) {
                builder.addTextBody(SendDocument.MESSAGETHREADID_FIELD, sendDocument.getMessageThreadId().toString(), TEXT_PLAIN_CONTENT_TYPE);
            }
            if (sendDocument.getCaption() != null) {
                builder.addTextBody(SendDocument.CAPTION_FIELD, sendDocument.getCaption(), TEXT_PLAIN_CONTENT_TYPE);
                if (sendDocument.getParseMode() != null) {
                    builder.addTextBody(SendDocument.PARSEMODE_FIELD, sendDocument.getParseMode(), TEXT_PLAIN_CONTENT_TYPE);
                }
            }
            if (sendDocument.getDisableNotification() != null) {
                builder.addTextBody(SendDocument.DISABLENOTIFICATION_FIELD, sendDocument.getDisableNotification().toString(), TEXT_PLAIN_CONTENT_TYPE);
            }
            if (sendDocument.getProtectContent() != null) {
                builder.addTextBody(SendDocument.PROTECTCONTENT_FIELD, sendDocument.getProtectContent().toString(), TEXT_PLAIN_CONTENT_TYPE);
            }

            if (sendDocument.getAllowSendingWithoutReply() != null) {
                builder.addTextBody(SendDocument.ALLOWSENDINGWITHOUTREPLY_FIELD, sendDocument.getAllowSendingWithoutReply().toString(), TEXT_PLAIN_CONTENT_TYPE);
            }
            if (sendDocument.getDisableContentTypeDetection() != null) {
                builder.addTextBody(SendDocument.DISABLECONTENTTYPEDETECTION_FIELD, sendDocument.getDisableContentTypeDetection().toString(), TEXT_PLAIN_CONTENT_TYPE);
            }
            if (sendDocument.getCaptionEntities() != null) {
                builder.addTextBody(SendDocument.CAPTION_ENTITIES_FIELD, objectMapper.writeValueAsString(sendDocument.getCaptionEntities()), TEXT_PLAIN_CONTENT_TYPE);
            }
            if (sendDocument.getThumbnail() != null) {
                addInputFile(builder, sendDocument.getThumbnail(), SendDocument.THUMBNAIL_FIELD, false);
                builder.addTextBody(SendDocument.THUMBNAIL_FIELD, sendDocument.getThumbnail().getAttachName(), TEXT_PLAIN_CONTENT_TYPE);
            }
            if (sendDocument.getReplyParameters() != null) {
                builder.addTextBody(SendDocument.REPLY_PARAMETERS_FIELD, objectMapper.writeValueAsString(sendDocument.getReplyParameters()), TEXT_PLAIN_CONTENT_TYPE);
            }

            HttpEntity multipart = builder.build();
            httppost.setEntity(multipart);

            return sendDocument.deserializeResponse(sendHttpPostRequest(httppost));
        } catch (IOException e) {
            throw new TelegramApiException("Unable to send document", e);
        }
    }

    @Override
    public final Message execute(SendPhoto sendPhoto) throws TelegramApiException {
        assertParamNotNull(sendPhoto, "sendPhoto");

        sendPhoto.validate();
        try {
            String url = getBaseUrl() + SendPhoto.PATH;
            HttpPost httppost = configuredHttpPost(url);

            MultipartEntityBuilder builder = MultipartEntityBuilder.create();
            builder.setLaxMode();
            builder.setCharset(StandardCharsets.UTF_8);
            builder.addTextBody(SendPhoto.CHATID_FIELD, sendPhoto.getChatId(), TEXT_PLAIN_CONTENT_TYPE);
            addInputFile(builder, sendPhoto.getPhoto(), SendPhoto.PHOTO_FIELD, true);

            if (sendPhoto.getReplyMarkup() != null) {
                builder.addTextBody(SendPhoto.REPLYMARKUP_FIELD, objectMapper.writeValueAsString(sendPhoto.getReplyMarkup()), TEXT_PLAIN_CONTENT_TYPE);
            }
            if (sendPhoto.getReplyToMessageId() != null) {
                builder.addTextBody(SendPhoto.REPLYTOMESSAGEID_FIELD, sendPhoto.getReplyToMessageId().toString(), TEXT_PLAIN_CONTENT_TYPE);
            }
            if (sendPhoto.getMessageThreadId() != null) {
                builder.addTextBody(SendPhoto.MESSAGETHREADID_FIELD, sendPhoto.getMessageThreadId().toString(), TEXT_PLAIN_CONTENT_TYPE);
            }
            if (sendPhoto.getCaption() != null) {
                builder.addTextBody(SendPhoto.CAPTION_FIELD, sendPhoto.getCaption(), TEXT_PLAIN_CONTENT_TYPE);
                if (sendPhoto.getParseMode() != null) {
                    builder.addTextBody(SendPhoto.PARSEMODE_FIELD, sendPhoto.getParseMode(), TEXT_PLAIN_CONTENT_TYPE);
                }
            }
            if (sendPhoto.getDisableNotification() != null) {
                builder.addTextBody(SendPhoto.DISABLENOTIFICATION_FIELD, sendPhoto.getDisableNotification().toString(), TEXT_PLAIN_CONTENT_TYPE);
            }
            if (sendPhoto.getAllowSendingWithoutReply() != null) {
                builder.addTextBody(SendPhoto.ALLOWSENDINGWITHOUTREPLY_FIELD, sendPhoto.getAllowSendingWithoutReply().toString(), TEXT_PLAIN_CONTENT_TYPE);
            }
            if (sendPhoto.getProtectContent() != null) {
                builder.addTextBody(SendPhoto.PROTECTCONTENT_FIELD, sendPhoto.getProtectContent().toString(), TEXT_PLAIN_CONTENT_TYPE);
            }
            if (sendPhoto.getCaptionEntities() != null) {
                builder.addTextBody(SendPhoto.CAPTION_ENTITIES_FIELD, objectMapper.writeValueAsString(sendPhoto.getCaptionEntities()), TEXT_PLAIN_CONTENT_TYPE);
            }
            if (sendPhoto.getHasSpoiler() != null) {
                builder.addTextBody(SendPhoto.HASSPOILER_FIELD, objectMapper.writeValueAsString(sendPhoto.getHasSpoiler()), TEXT_PLAIN_CONTENT_TYPE);
            }
            if (sendPhoto.getReplyParameters() != null) {
                builder.addTextBody(SendPhoto.REPLY_PARAMETERS_FIELD, objectMapper.writeValueAsString(sendPhoto.getReplyParameters()), TEXT_PLAIN_CONTENT_TYPE);
            }
            HttpEntity multipart = builder.build();
            httppost.setEntity(multipart);

            return sendPhoto.deserializeResponse(sendHttpPostRequest(httppost));
        } catch (IOException e) {
            throw new TelegramApiException("Unable to send photo", e);
        }
    }

    @Override
    public final Message execute(SendVideo sendVideo) throws TelegramApiException {
        assertParamNotNull(sendVideo, "sendVideo");

        sendVideo.validate();
        try {
            String url = getBaseUrl() + SendVideo.PATH;
            HttpPost httppost = configuredHttpPost(url);

            MultipartEntityBuilder builder = MultipartEntityBuilder.create();
            builder.setLaxMode();
            builder.setCharset(StandardCharsets.UTF_8);
            builder.addTextBody(SendVideo.CHATID_FIELD, sendVideo.getChatId(), TEXT_PLAIN_CONTENT_TYPE);
            addInputFile(builder, sendVideo.getVideo(), SendVideo.VIDEO_FIELD, true);

            if (sendVideo.getReplyMarkup() != null) {
                builder.addTextBody(SendVideo.REPLYMARKUP_FIELD, objectMapper.writeValueAsString(sendVideo.getReplyMarkup()), TEXT_PLAIN_CONTENT_TYPE);
            }
            if (sendVideo.getReplyToMessageId() != null) {
                builder.addTextBody(SendVideo.REPLYTOMESSAGEID_FIELD, sendVideo.getReplyToMessageId().toString(), TEXT_PLAIN_CONTENT_TYPE);
            }
            if (sendVideo.getMessageThreadId() != null) {
                builder.addTextBody(SendVideo.MESSAGETHREADID_FIELD, sendVideo.getMessageThreadId().toString(), TEXT_PLAIN_CONTENT_TYPE);
            }
            if (sendVideo.getCaption() != null) {
                builder.addTextBody(SendVideo.CAPTION_FIELD, sendVideo.getCaption(), TEXT_PLAIN_CONTENT_TYPE);
                if (sendVideo.getParseMode() != null) {
                    builder.addTextBody(SendVideo.PARSEMODE_FIELD, sendVideo.getParseMode(), TEXT_PLAIN_CONTENT_TYPE);
                }
            }
            if (sendVideo.getSupportsStreaming() != null) {
                builder.addTextBody(SendVideo.SUPPORTSSTREAMING_FIELD, sendVideo.getSupportsStreaming().toString(), TEXT_PLAIN_CONTENT_TYPE);
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
            if (sendVideo.getProtectContent() != null) {
                builder.addTextBody(SendVideo.PROTECTCONTENT_FIELD, sendVideo.getProtectContent().toString(), TEXT_PLAIN_CONTENT_TYPE);
            }
            if (sendVideo.getThumbnail() != null) {
                addInputFile(builder, sendVideo.getThumbnail(), SendVideo.THUMBNAIL_FIELD, false);
                builder.addTextBody(SendVideo.THUMBNAIL_FIELD, sendVideo.getThumbnail().getAttachName(), TEXT_PLAIN_CONTENT_TYPE);
            }
            if (sendVideo.getAllowSendingWithoutReply() != null) {
                builder.addTextBody(SendVideo.ALLOWSENDINGWITHOUTREPLY_FIELD, sendVideo.getAllowSendingWithoutReply().toString(), TEXT_PLAIN_CONTENT_TYPE);
            }
            if (sendVideo.getCaptionEntities() != null) {
                builder.addTextBody(SendVideo.CAPTION_ENTITIES_FIELD, objectMapper.writeValueAsString(sendVideo.getCaptionEntities()), TEXT_PLAIN_CONTENT_TYPE);
            }
            if (sendVideo.getHasSpoiler() != null) {
                builder.addTextBody(SendVideo.HASSPOILER_FIELD, objectMapper.writeValueAsString(sendVideo.getHasSpoiler()), TEXT_PLAIN_CONTENT_TYPE);
            }
            if (sendVideo.getReplyParameters() != null) {
                builder.addTextBody(SendVideo.REPLY_PARAMETERS_FIELD, objectMapper.writeValueAsString(sendVideo.getReplyParameters()), TEXT_PLAIN_CONTENT_TYPE);
            }
            HttpEntity multipart = builder.build();
            httppost.setEntity(multipart);

            return sendVideo.deserializeResponse(sendHttpPostRequest(httppost));
        } catch (IOException e) {
            throw new TelegramApiException("Unable to send video", e);
        }
    }

    @Override
    public final Message execute(SendVideoNote sendVideoNote) throws TelegramApiException {
        assertParamNotNull(sendVideoNote, "sendVideoNote");

        sendVideoNote.validate();
        try {
            String url = getBaseUrl() + SendVideoNote.PATH;
            HttpPost httppost = configuredHttpPost(url);

            MultipartEntityBuilder builder = MultipartEntityBuilder.create();
            builder.setLaxMode();
            builder.setCharset(StandardCharsets.UTF_8);
            builder.addTextBody(SendVideoNote.CHATID_FIELD, sendVideoNote.getChatId(), TEXT_PLAIN_CONTENT_TYPE);
            addInputFile(builder, sendVideoNote.getVideoNote(), SendVideoNote.VIDEONOTE_FIELD, true);

            if (sendVideoNote.getReplyMarkup() != null) {
                builder.addTextBody(SendVideoNote.REPLYMARKUP_FIELD, objectMapper.writeValueAsString(sendVideoNote.getReplyMarkup()), TEXT_PLAIN_CONTENT_TYPE);
            }
            if (sendVideoNote.getReplyToMessageId() != null) {
                builder.addTextBody(SendVideoNote.REPLYTOMESSAGEID_FIELD, sendVideoNote.getReplyToMessageId().toString(), TEXT_PLAIN_CONTENT_TYPE);
            }
            if (sendVideoNote.getMessageThreadId() != null) {
                builder.addTextBody(SendVideoNote.MESSAGETHREADID_FIELD, sendVideoNote.getMessageThreadId().toString(), TEXT_PLAIN_CONTENT_TYPE);
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
            if (sendVideoNote.getProtectContent() != null) {
                builder.addTextBody(SendVideoNote.PROTECTCONTENT_FIELD, sendVideoNote.getProtectContent().toString(), TEXT_PLAIN_CONTENT_TYPE);
            }
            if (sendVideoNote.getThumbnail() != null) {
                addInputFile(builder, sendVideoNote.getThumbnail(), SendVideoNote.THUMBNAIL_FIELD, false);
                builder.addTextBody(SendVideoNote.THUMBNAIL_FIELD, sendVideoNote.getThumbnail().getAttachName(), TEXT_PLAIN_CONTENT_TYPE);
            }
            if (sendVideoNote.getAllowSendingWithoutReply() != null) {
                builder.addTextBody(SendVideoNote.ALLOWSENDINGWITHOUTREPLY_FIELD, sendVideoNote.getAllowSendingWithoutReply().toString(), TEXT_PLAIN_CONTENT_TYPE);
            }
            if (sendVideoNote.getReplyParameters() != null) {
                builder.addTextBody(SendVideoNote.REPLY_PARAMETERS_FIELD, objectMapper.writeValueAsString(sendVideoNote.getReplyParameters()), TEXT_PLAIN_CONTENT_TYPE);
            }
            HttpEntity multipart = builder.build();
            httppost.setEntity(multipart);


            return sendVideoNote.deserializeResponse(sendHttpPostRequest(httppost));
        } catch (IOException e) {
            throw new TelegramApiException("Unable to send video note", e);
        }
    }

    @Override
    public final Message execute(SendSticker sendSticker) throws TelegramApiException {
        assertParamNotNull(sendSticker, "sendSticker");

        sendSticker.validate();
        try {
            String url = getBaseUrl() + SendSticker.PATH;
            HttpPost httppost = configuredHttpPost(url);

            MultipartEntityBuilder builder = MultipartEntityBuilder.create();
            builder.setLaxMode();
            builder.setCharset(StandardCharsets.UTF_8);
            builder.addTextBody(SendSticker.CHATID_FIELD, sendSticker.getChatId(), TEXT_PLAIN_CONTENT_TYPE);
            addInputFile(builder, sendSticker.getSticker(), SendSticker.STICKER_FIELD, true);

            if (sendSticker.getReplyMarkup() != null) {
                builder.addTextBody(SendSticker.REPLYMARKUP_FIELD, objectMapper.writeValueAsString(sendSticker.getReplyMarkup()), TEXT_PLAIN_CONTENT_TYPE);
            }
            if (sendSticker.getReplyToMessageId() != null) {
                builder.addTextBody(SendSticker.REPLYTOMESSAGEID_FIELD, sendSticker.getReplyToMessageId().toString(), TEXT_PLAIN_CONTENT_TYPE);
            }
            if (sendSticker.getMessageThreadId() != null) {
                builder.addTextBody(SendSticker.MESSAGETHREADID_FIELD, sendSticker.getMessageThreadId().toString(), TEXT_PLAIN_CONTENT_TYPE);
            }
            if (sendSticker.getDisableNotification() != null) {
                builder.addTextBody(SendSticker.DISABLENOTIFICATION_FIELD, sendSticker.getDisableNotification().toString(), TEXT_PLAIN_CONTENT_TYPE);
            }
            if (sendSticker.getProtectContent() != null) {
                builder.addTextBody(SendSticker.PROTECTCONTENT_FIELD, sendSticker.getProtectContent().toString(), TEXT_PLAIN_CONTENT_TYPE);
            }
            if (sendSticker.getAllowSendingWithoutReply() != null) {
                builder.addTextBody(SendSticker.ALLOWSENDINGWITHOUTREPLY_FIELD, sendSticker.getAllowSendingWithoutReply().toString(), TEXT_PLAIN_CONTENT_TYPE);
            }
            if (sendSticker.getEmoji() != null) {
                builder.addTextBody(SendSticker.EMOJI_FIELD, sendSticker.getEmoji(), TEXT_PLAIN_CONTENT_TYPE);
            }
            if (sendSticker.getReplyParameters() != null) {
                builder.addTextBody(SendSticker.REPLY_PARAMETERS_FIELD, objectMapper.writeValueAsString(sendSticker.getReplyParameters()), TEXT_PLAIN_CONTENT_TYPE);
            }
            HttpEntity multipart = builder.build();
            httppost.setEntity(multipart);

            return sendSticker.deserializeResponse(sendHttpPostRequest(httppost));
        } catch (IOException e) {
            throw new TelegramApiException("Unable to send sticker", e);
        }
    }

    /**
     * Sends a file using Send Audio method (<a href="https://core.telegram.org/bots/api#sendaudio">sendAudio</a>)
     * @param sendAudio Information to send
     * @return If success, the Message sent is returned
     * @throws TelegramApiException If there is any error sending the audio
     */
    @Override
    public final Message execute(SendAudio sendAudio) throws TelegramApiException {
        assertParamNotNull(sendAudio, "sendAudio");
        sendAudio.validate();
        try {
            String url = getBaseUrl() + SendAudio.PATH;
            HttpPost httppost = configuredHttpPost(url);
            MultipartEntityBuilder builder = MultipartEntityBuilder.create();
            builder.setLaxMode();
            builder.setCharset(StandardCharsets.UTF_8);
            builder.addTextBody(SendAudio.CHATID_FIELD, sendAudio.getChatId(), TEXT_PLAIN_CONTENT_TYPE);
            addInputFile(builder, sendAudio.getAudio(), SendAudio.AUDIO_FIELD, true);

            if (sendAudio.getReplyMarkup() != null) {
                builder.addTextBody(SendAudio.REPLYMARKUP_FIELD, objectMapper.writeValueAsString(sendAudio.getReplyMarkup()), TEXT_PLAIN_CONTENT_TYPE);
            }
            if (sendAudio.getReplyToMessageId() != null) {
                builder.addTextBody(SendAudio.REPLYTOMESSAGEID_FIELD, sendAudio.getReplyToMessageId().toString(), TEXT_PLAIN_CONTENT_TYPE);
            }
            if (sendAudio.getMessageThreadId() != null) {
                builder.addTextBody(SendAudio.MESSAGETHREADID_FIELD, sendAudio.getMessageThreadId().toString(), TEXT_PLAIN_CONTENT_TYPE);
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
                if (sendAudio.getParseMode() != null) {
                    builder.addTextBody(SendAudio.PARSEMODE_FIELD, sendAudio.getParseMode(), TEXT_PLAIN_CONTENT_TYPE);
                }
            }
            if (sendAudio.getThumbnail() != null) {
                addInputFile(builder, sendAudio.getThumbnail(), SendAudio.THUMBNAIL_FIELD, false);
                builder.addTextBody(SendAudio.THUMBNAIL_FIELD, sendAudio.getThumbnail().getAttachName(), TEXT_PLAIN_CONTENT_TYPE);
            }
            if (sendAudio.getAllowSendingWithoutReply() != null) {
                builder.addTextBody(SendAudio.ALLOWSENDINGWITHOUTREPLY_FIELD, sendAudio.getAllowSendingWithoutReply().toString(), TEXT_PLAIN_CONTENT_TYPE);
            }
            if (sendAudio.getProtectContent() != null) {
                builder.addTextBody(SendAudio.PROTECTCONTENT_FIELD, sendAudio.getProtectContent().toString(), TEXT_PLAIN_CONTENT_TYPE);
            }
            if (sendAudio.getCaptionEntities() != null) {
                builder.addTextBody(SendAudio.CAPTION_ENTITIES_FIELD, objectMapper.writeValueAsString(sendAudio.getCaptionEntities()), TEXT_PLAIN_CONTENT_TYPE);
            }
            if (sendAudio.getReplyParameters() != null) {
                builder.addTextBody(SendAudio.REPLY_PARAMETERS_FIELD, objectMapper.writeValueAsString(sendAudio.getReplyParameters()), TEXT_PLAIN_CONTENT_TYPE);
            }
            HttpEntity multipart = builder.build();
            httppost.setEntity(multipart);


            return sendAudio.deserializeResponse(sendHttpPostRequest(httppost));
        } catch (IOException e) {
            throw new TelegramApiException("Unable to send audio", e);
        }
    }

    /**
     * Sends a voice note using Send Voice method (<a href="https://core.telegram.org/bots/api#sendvoice">sendVoice</a>)
     * For this to work, your audio must be in an .ogg file encoded with OPUS
     * @param sendVoice Information to send
     * @return If success, the Message sent is returned
     * @throws TelegramApiException If there is any error sending the audio
     */
    @Override
    public final Message execute(SendVoice sendVoice) throws TelegramApiException {
        assertParamNotNull(sendVoice, "sendVoice");
        sendVoice.validate();
        try {
            String url = getBaseUrl() + SendVoice.PATH;
            HttpPost httppost = configuredHttpPost(url);
            MultipartEntityBuilder builder = MultipartEntityBuilder.create();
            builder.setLaxMode();
            builder.setCharset(StandardCharsets.UTF_8);
            builder.addTextBody(SendVoice.CHATID_FIELD, sendVoice.getChatId(), TEXT_PLAIN_CONTENT_TYPE);
            addInputFile(builder, sendVoice.getVoice(), SendVoice.VOICE_FIELD, true);

            if (sendVoice.getReplyMarkup() != null) {
                builder.addTextBody(SendVoice.REPLYMARKUP_FIELD, objectMapper.writeValueAsString(sendVoice.getReplyMarkup()), TEXT_PLAIN_CONTENT_TYPE);
            }
            if (sendVoice.getReplyToMessageId() != null) {
                builder.addTextBody(SendVoice.REPLYTOMESSAGEID_FIELD, sendVoice.getReplyToMessageId().toString(), TEXT_PLAIN_CONTENT_TYPE);
            }
            if (sendVoice.getMessageThreadId() != null) {
                builder.addTextBody(SendVoice.MESSAGETHREADID_FIELD, sendVoice.getMessageThreadId().toString(), TEXT_PLAIN_CONTENT_TYPE);
            }
            if (sendVoice.getDisableNotification() != null) {
                builder.addTextBody(SendVoice.DISABLENOTIFICATION_FIELD, sendVoice.getDisableNotification().toString(), TEXT_PLAIN_CONTENT_TYPE);
            }
            if (sendVoice.getDuration() != null) {
                builder.addTextBody(SendVoice.DURATION_FIELD, sendVoice.getDuration().toString(), TEXT_PLAIN_CONTENT_TYPE);
            }
            if (sendVoice.getCaption() != null) {
                builder.addTextBody(SendVoice.CAPTION_FIELD, sendVoice.getCaption(), TEXT_PLAIN_CONTENT_TYPE);
                if (sendVoice.getParseMode() != null) {
                    builder.addTextBody(SendVoice.PARSEMODE_FIELD, sendVoice.getParseMode(), TEXT_PLAIN_CONTENT_TYPE);
                }
            }
            if (sendVoice.getAllowSendingWithoutReply() != null) {
                builder.addTextBody(SendVoice.ALLOWSENDINGWITHOUTREPLY_FIELD, sendVoice.getAllowSendingWithoutReply().toString(), TEXT_PLAIN_CONTENT_TYPE);
            }
            if (sendVoice.getProtectContent() != null) {
                builder.addTextBody(SendVoice.PROTECTCONTENT_FIELD, sendVoice.getProtectContent().toString(), TEXT_PLAIN_CONTENT_TYPE);
            }
            if (sendVoice.getCaptionEntities() != null) {
                builder.addTextBody(SendVoice.CAPTION_ENTITIES_FIELD, objectMapper.writeValueAsString(sendVoice.getCaptionEntities()), TEXT_PLAIN_CONTENT_TYPE);
            }
            if (sendVoice.getReplyParameters() != null) {
                builder.addTextBody(SendVoice.REPLY_PARAMETERS_FIELD, objectMapper.writeValueAsString(sendVoice.getReplyParameters()), TEXT_PLAIN_CONTENT_TYPE);
            }
            HttpEntity multipart = builder.build();
            httppost.setEntity(multipart);

            return sendVoice.deserializeResponse(sendHttpPostRequest(httppost));
        } catch (IOException e) {
            throw new TelegramApiException("Unable to send voice", e);
        }
    }

    @Override
    public Boolean execute(SetChatPhoto setChatPhoto) throws TelegramApiException {
        assertParamNotNull(setChatPhoto, "setChatPhoto");
        setChatPhoto.validate();

        try {
            String url = getBaseUrl() + SetChatPhoto.PATH;
            HttpPost httppost = configuredHttpPost(url);

            MultipartEntityBuilder builder = MultipartEntityBuilder.create();
            builder.setLaxMode();
            builder.setCharset(StandardCharsets.UTF_8);
            builder.addTextBody(SetChatPhoto.CHATID_FIELD, setChatPhoto.getChatId(), TEXT_PLAIN_CONTENT_TYPE);
            InputFile photo = setChatPhoto.getPhoto();
            if (photo.getNewMediaFile() != null) {
                builder.addBinaryBody(SetChatPhoto.PHOTO_FIELD, photo.getNewMediaFile());
            } else if (photo.getNewMediaStream() != null) {
                builder.addBinaryBody(SetChatPhoto.PHOTO_FIELD, photo.getNewMediaStream(), ContentType.APPLICATION_OCTET_STREAM, photo.getMediaName());
            }
            HttpEntity multipart = builder.build();
            httppost.setEntity(multipart);

            return setChatPhoto.deserializeResponse(sendHttpPostRequest(httppost));
        } catch (IOException e) {
            throw new TelegramApiException("Unable to set chat photo", e);
        }
    }

    @Override
    public List<Message> execute(SendMediaGroup sendMediaGroup) throws TelegramApiException {
        assertParamNotNull(sendMediaGroup, "sendMediaGroup");
        sendMediaGroup.validate();

        try {
            String url = getBaseUrl() + SendMediaGroup.PATH;
            HttpPost httppost = configuredHttpPost(url);

            MultipartEntityBuilder builder = MultipartEntityBuilder.create();
            builder.setLaxMode();
            builder.setCharset(StandardCharsets.UTF_8);
            builder.addTextBody(SendMediaGroup.CHATID_FIELD, sendMediaGroup.getChatId(), TEXT_PLAIN_CONTENT_TYPE);
            addInputData(builder, sendMediaGroup.getMedias(), SendMediaGroup.MEDIA_FIELD);

            if (sendMediaGroup.getDisableNotification() != null) {
                builder.addTextBody(SendMediaGroup.DISABLENOTIFICATION_FIELD, sendMediaGroup.getDisableNotification().toString(), TEXT_PLAIN_CONTENT_TYPE);
            }

            if (sendMediaGroup.getReplyToMessageId() != null) {
                builder.addTextBody(SendMediaGroup.REPLYTOMESSAGEID_FIELD, sendMediaGroup.getReplyToMessageId().toString(), TEXT_PLAIN_CONTENT_TYPE);
            }
            if (sendMediaGroup.getMessageThreadId() != null) {
                builder.addTextBody(SendMediaGroup.MESSAGETHREADID_FIELD, sendMediaGroup.getMessageThreadId().toString(), TEXT_PLAIN_CONTENT_TYPE);
            }
            if (sendMediaGroup.getAllowSendingWithoutReply() != null) {
                builder.addTextBody(SendMediaGroup.ALLOWSENDINGWITHOUTREPLY_FIELD, sendMediaGroup.getAllowSendingWithoutReply().toString(), TEXT_PLAIN_CONTENT_TYPE);
            }
            if (sendMediaGroup.getProtectContent() != null) {
                builder.addTextBody(SendMediaGroup.PROTECTCONTENT_FIELD, sendMediaGroup.getProtectContent().toString(), TEXT_PLAIN_CONTENT_TYPE);
            }
            if (sendMediaGroup.getReplyParameters() != null) {
                builder.addTextBody(SendMediaGroup.REPLY_PARAMETERS_FIELD, objectMapper.writeValueAsString(sendMediaGroup.getReplyParameters()), TEXT_PLAIN_CONTENT_TYPE);
            }

            HttpEntity multipart = builder.build();
            httppost.setEntity(multipart);

            return sendMediaGroup.deserializeResponse(sendHttpPostRequest(httppost));
        } catch (IOException e) {
            throw new TelegramApiException("Unable to set chat photo", e);
        }
    }

    @Override
    public Boolean execute(AddStickerToSet addStickerToSet) throws TelegramApiException {
        assertParamNotNull(addStickerToSet, "addStickerToSet");
        addStickerToSet.validate();
        try {
            String url = getBaseUrl() + AddStickerToSet.PATH;
            HttpPost httppost = configuredHttpPost(url);
            MultipartEntityBuilder builder = MultipartEntityBuilder.create();
            builder.setLaxMode();
            builder.setCharset(StandardCharsets.UTF_8);
            builder.addTextBody(AddStickerToSet.USERID_FIELD, addStickerToSet.getUserId().toString(), TEXT_PLAIN_CONTENT_TYPE);
            builder.addTextBody(AddStickerToSet.NAME_FIELD, addStickerToSet.getName(), TEXT_PLAIN_CONTENT_TYPE);

            if (addStickerToSet.getSticker() != null) {
                addStickers(builder, Collections.singletonList(addStickerToSet.getSticker()), AddStickerToSet.STICKER_FIELD);
            } else {
                // Support Deprecated
                builder.addTextBody(AddStickerToSet.EMOJIS_FIELD, addStickerToSet.getEmojis(), TEXT_PLAIN_CONTENT_TYPE);
                if (addStickerToSet.getPngSticker() != null) {
                    addInputFile(builder, addStickerToSet.getPngSticker(), AddStickerToSet.PNGSTICKER_FIELD, true);
                } else if (addStickerToSet.getTgsSticker() != null) {
                    addInputFile(builder, addStickerToSet.getTgsSticker(), AddStickerToSet.TGSSTICKER_FIELD, true);
                } else {
                    addInputFile(builder, addStickerToSet.getWebmSticker(), AddStickerToSet.WEBMSTICKER_FIELD, true);
                }

                if (addStickerToSet.getMaskPosition() != null) {
                    builder.addTextBody(AddStickerToSet.MASKPOSITION_FIELD, objectMapper.writeValueAsString(addStickerToSet.getMaskPosition()), TEXT_PLAIN_CONTENT_TYPE);
                }
            }
            HttpEntity multipart = builder.build();
            httppost.setEntity(multipart);

            return addStickerToSet.deserializeResponse(sendHttpPostRequest(httppost));
        } catch (IOException e) {
            throw new TelegramApiException("Unable to add sticker to set", e);
        }
    }

    @Override
    public Boolean execute(SetStickerSetThumb setStickerSetThumb) throws TelegramApiException {
        assertParamNotNull(setStickerSetThumb, "setStickerSetThumb");
        setStickerSetThumb.validate();
        try {
            String url = getBaseUrl() + SetStickerSetThumb.PATH;
            HttpPost httppost = configuredHttpPost(url);
            MultipartEntityBuilder builder = MultipartEntityBuilder.create();
            builder.setLaxMode();
            builder.setCharset(StandardCharsets.UTF_8);
            builder.addTextBody(SetStickerSetThumb.USERID_FIELD, setStickerSetThumb.getUserId().toString(), TEXT_PLAIN_CONTENT_TYPE);
            builder.addTextBody(SetStickerSetThumb.NAME_FIELD, setStickerSetThumb.getName(), TEXT_PLAIN_CONTENT_TYPE);
            if (setStickerSetThumb.getThumb() != null) {
                addInputFile(builder, setStickerSetThumb.getThumb(), SetStickerSetThumb.THUMB_FIELD, true);
            }
            HttpEntity multipart = builder.build();
            httppost.setEntity(multipart);

            return setStickerSetThumb.deserializeResponse(sendHttpPostRequest(httppost));
        } catch (IOException e) {
            throw new TelegramApiException("Unable to set sticker set thumb", e);
        }
    }

    @Override
    public Boolean execute(CreateNewStickerSet createNewStickerSet) throws TelegramApiException {
        assertParamNotNull(createNewStickerSet, "createNewStickerSet");
        createNewStickerSet.validate();
        try {
            String url = getBaseUrl() + CreateNewStickerSet.PATH;
            HttpPost httppost = configuredHttpPost(url);
            MultipartEntityBuilder builder = MultipartEntityBuilder.create();
            builder.setLaxMode();
            builder.setCharset(StandardCharsets.UTF_8);
            builder.addTextBody(CreateNewStickerSet.USERID_FIELD, createNewStickerSet.getUserId().toString(), TEXT_PLAIN_CONTENT_TYPE);
            builder.addTextBody(CreateNewStickerSet.NAME_FIELD, createNewStickerSet.getName(), TEXT_PLAIN_CONTENT_TYPE);
            builder.addTextBody(CreateNewStickerSet.TITLE_FIELD, createNewStickerSet.getTitle(), TEXT_PLAIN_CONTENT_TYPE);
            builder.addTextBody(CreateNewStickerSet.STICKER_FORMAT_FIELD, createNewStickerSet.getStickerFormat(), TEXT_PLAIN_CONTENT_TYPE);
            builder.addTextBody(CreateNewStickerSet.STICKERTYPE_FIELD, createNewStickerSet.getStickerType(), TEXT_PLAIN_CONTENT_TYPE);
            if (createNewStickerSet.getNeedsRepainting() != null) {
                builder.addTextBody(CreateNewStickerSet.NEEDS_REPAINTING_FIELD, createNewStickerSet.getNeedsRepainting().toString(), TEXT_PLAIN_CONTENT_TYPE);
            }
            if (!createNewStickerSet.getStickers().isEmpty()) {
                addStickers(builder, createNewStickerSet.getStickers(), CreateNewStickerSet.STICKERS_FIELD);
            }

            // Support Deprecated
            builder.addTextBody(CreateNewStickerSet.EMOJIS_FIELD, createNewStickerSet.getEmojis(), TEXT_PLAIN_CONTENT_TYPE);
            if (createNewStickerSet.getPngSticker() != null) {
                addInputFile(builder, createNewStickerSet.getPngSticker(), CreateNewStickerSet.PNGSTICKER_FIELD, true);
            } else if (createNewStickerSet.getTgsSticker() != null)  {
                addInputFile(builder, createNewStickerSet.getTgsSticker(), CreateNewStickerSet.TGSSTICKER_FIELD, true);
            } else if (createNewStickerSet.getWebmSticker() != null) {
                addInputFile(builder, createNewStickerSet.getWebmSticker(), CreateNewStickerSet.WEBMSTICKER_FIELD, true);
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
    public File execute(UploadStickerFile uploadStickerFile) throws TelegramApiException {
        assertParamNotNull(uploadStickerFile, "uploadStickerFile");
        uploadStickerFile.validate();
        try {
            String url = getBaseUrl() + UploadStickerFile.PATH;
            HttpPost httppost = configuredHttpPost(url);
            MultipartEntityBuilder builder = MultipartEntityBuilder.create();
            builder.setLaxMode();
            builder.setCharset(StandardCharsets.UTF_8);
            builder.addTextBody(UploadStickerFile.USERID_FIELD, uploadStickerFile.getUserId().toString(), TEXT_PLAIN_CONTENT_TYPE);
            builder.addTextBody(UploadStickerFile.STICKER_FORMAT_FIELD, uploadStickerFile.getStickerFormat(), TEXT_PLAIN_CONTENT_TYPE);
            addInputFile(builder, uploadStickerFile.getSticker(), UploadStickerFile.STICKER_FIELD, true);
            HttpEntity multipart = builder.build();
            httppost.setEntity(multipart);

            return uploadStickerFile.deserializeResponse(sendHttpPostRequest(httppost));
        } catch (IOException e) {
            throw new TelegramApiException("Unable to upload new sticker file", e);
        }
    }

    @Override
    public Serializable execute(EditMessageMedia editMessageMedia) throws TelegramApiException {
        assertParamNotNull(editMessageMedia, "editMessageMedia");
        editMessageMedia.validate();
        try {
            String url = getBaseUrl() + EditMessageMedia.PATH;
            HttpPost httppost = configuredHttpPost(url);
            MultipartEntityBuilder builder = MultipartEntityBuilder.create();
            builder.setLaxMode();
            builder.setCharset(StandardCharsets.UTF_8);
            if (editMessageMedia.getInlineMessageId() == null) {
                builder.addTextBody(EditMessageMedia.CHATID_FIELD, editMessageMedia.getChatId(), TEXT_PLAIN_CONTENT_TYPE);
                builder.addTextBody(EditMessageMedia.MESSAGEID_FIELD, editMessageMedia.getMessageId().toString(), TEXT_PLAIN_CONTENT_TYPE);

            } else {
                builder.addTextBody(EditMessageMedia.INLINE_MESSAGE_ID_FIELD, editMessageMedia.getInlineMessageId(), TEXT_PLAIN_CONTENT_TYPE);
            }
            if (editMessageMedia.getReplyMarkup() != null) {
                builder.addTextBody(EditMessageMedia.REPLYMARKUP_FIELD, objectMapper.writeValueAsString(editMessageMedia.getReplyMarkup()), TEXT_PLAIN_CONTENT_TYPE);
            }

            addInputData(builder, editMessageMedia.getMedia(), EditMessageMedia.MEDIA_FIELD, true);

            HttpEntity multipart = builder.build();
            httppost.setEntity(multipart);

            return editMessageMedia.deserializeResponse(sendHttpPostRequest(httppost));
        } catch (IOException e) {
            throw new TelegramApiException("Unable to edit message media", e);
        }
    }

    @Override
    public Message execute(SendAnimation sendAnimation) throws TelegramApiException {
        assertParamNotNull(sendAnimation, "sendAnimation");
        sendAnimation.validate();
        try {
            String url = getBaseUrl() + SendAnimation.PATH;
            HttpPost httppost = configuredHttpPost(url);
            MultipartEntityBuilder builder = MultipartEntityBuilder.create();
            builder.setLaxMode();
            builder.setCharset(StandardCharsets.UTF_8);
            builder.addTextBody(SendAnimation.CHATID_FIELD, sendAnimation.getChatId(), TEXT_PLAIN_CONTENT_TYPE);
            addInputFile(builder, sendAnimation.getAnimation(), SendAnimation.ANIMATION_FIELD, true);

            if (sendAnimation.getReplyMarkup() != null) {
                builder.addTextBody(SendAnimation.REPLYMARKUP_FIELD, objectMapper.writeValueAsString(sendAnimation.getReplyMarkup()), TEXT_PLAIN_CONTENT_TYPE);
            }
            if (sendAnimation.getReplyToMessageId() != null) {
                builder.addTextBody(SendAnimation.REPLYTOMESSAGEID_FIELD, sendAnimation.getReplyToMessageId().toString(), TEXT_PLAIN_CONTENT_TYPE);
            }
            if (sendAnimation.getMessageThreadId() != null) {
                builder.addTextBody(SendAnimation.MESSAGETHREADID_FIELD, sendAnimation.getMessageThreadId().toString(), TEXT_PLAIN_CONTENT_TYPE);
            }
            if (sendAnimation.getDisableNotification() != null) {
                builder.addTextBody(SendAnimation.DISABLENOTIFICATION_FIELD, sendAnimation.getDisableNotification().toString(), TEXT_PLAIN_CONTENT_TYPE);
            }
            if (sendAnimation.getDuration() != null) {
                builder.addTextBody(SendAnimation.DURATION_FIELD, sendAnimation.getDuration().toString(), TEXT_PLAIN_CONTENT_TYPE);
            }
            if (sendAnimation.getWidth() != null) {
                builder.addTextBody(SendAnimation.WIDTH_FIELD, sendAnimation.getWidth().toString(), TEXT_PLAIN_CONTENT_TYPE);
            }
            if (sendAnimation.getHeight() != null) {
                builder.addTextBody(SendAnimation.HEIGHT_FIELD, sendAnimation.getHeight().toString(), TEXT_PLAIN_CONTENT_TYPE);
            }
            if (sendAnimation.getThumbnail() != null) {
                addInputFile(builder, sendAnimation.getThumbnail(), SendAnimation.THUMBNAIL_FIELD, false);
                builder.addTextBody(SendAnimation.THUMBNAIL_FIELD, sendAnimation.getThumbnail().getAttachName(), TEXT_PLAIN_CONTENT_TYPE);
            }

            if (sendAnimation.getCaption() != null) {
                builder.addTextBody(SendAnimation.CAPTION_FIELD, sendAnimation.getCaption(), TEXT_PLAIN_CONTENT_TYPE);
                if (sendAnimation.getParseMode() != null) {
                    builder.addTextBody(SendAnimation.PARSEMODE_FIELD, sendAnimation.getParseMode(), TEXT_PLAIN_CONTENT_TYPE);
                }
            }
            if (sendAnimation.getAllowSendingWithoutReply() != null) {
                builder.addTextBody(SendAnimation.ALLOWSENDINGWITHOUTREPLY_FIELD, sendAnimation.getAllowSendingWithoutReply().toString(), TEXT_PLAIN_CONTENT_TYPE);
            }
            if (sendAnimation.getProtectContent() != null) {
                builder.addTextBody(SendAnimation.PROTECTCONTENT_FIELD, sendAnimation.getProtectContent().toString(), TEXT_PLAIN_CONTENT_TYPE);
            }
            if (sendAnimation.getCaptionEntities() != null) {
                builder.addTextBody(SendAnimation.CAPTION_ENTITIES_FIELD, objectMapper.writeValueAsString(sendAnimation.getCaptionEntities()), TEXT_PLAIN_CONTENT_TYPE);
            }
            if (sendAnimation.getHasSpoiler() != null) {
                builder.addTextBody(SendAnimation.HASSPOILER_FIELD, objectMapper.writeValueAsString(sendAnimation.getHasSpoiler()), TEXT_PLAIN_CONTENT_TYPE);
            }
            if (sendAnimation.getReplyParameters() != null) {
                builder.addTextBody(SendAnimation.REPLY_PARAMETERS_FIELD, objectMapper.writeValueAsString(sendAnimation.getReplyParameters()), TEXT_PLAIN_CONTENT_TYPE);
            }
            HttpEntity multipart = builder.build();
            httppost.setEntity(multipart);

            return sendAnimation.deserializeResponse(sendHttpPostRequest(httppost));
        } catch (IOException e) {
            throw new TelegramApiException("Unable to edit message media", e);
        }
    }

    // Async Methods

    @Override
    public CompletableFuture<Message> executeAsync(SendDocument sendDocument) {
        CompletableFuture<Message> completableFuture = new CompletableFuture<>();
        exe.submit(() -> {
            try {
                completableFuture.complete(execute(sendDocument));
            } catch (TelegramApiException e) {
                completableFuture.completeExceptionally(e);
            }
        });
        return completableFuture;
    }

    @Override
    public CompletableFuture<Message> executeAsync(SendPhoto sendPhoto) {
        CompletableFuture<Message> completableFuture = new CompletableFuture<>();
        exe.submit(() -> {
            try {
                completableFuture.complete(execute(sendPhoto));
            } catch (TelegramApiException e) {
                completableFuture.completeExceptionally(e);
            }
        });
        return completableFuture;
    }

    @Override
    public CompletableFuture<Message> executeAsync(SendVideo sendVideo) {
        CompletableFuture<Message> completableFuture = new CompletableFuture<>();
        exe.submit(() -> {
            try {
                completableFuture.complete(execute(sendVideo));
            } catch (TelegramApiException e) {
                completableFuture.completeExceptionally(e);
            }
        });
        return completableFuture;
    }

    @Override
    public CompletableFuture<Message> executeAsync(SendVideoNote sendVideoNote) {
        CompletableFuture<Message> completableFuture = new CompletableFuture<>();
        exe.submit(() -> {
            try {
                completableFuture.complete(execute(sendVideoNote));
            } catch (TelegramApiException e) {
                completableFuture.completeExceptionally(e);
            }
        });
        return completableFuture;
    }

    @Override
    public CompletableFuture<Message> executeAsync(SendSticker sendSticker) {
        CompletableFuture<Message> completableFuture = new CompletableFuture<>();
        exe.submit(() -> {
            try {
                completableFuture.complete(execute(sendSticker));
            } catch (TelegramApiException e) {
                completableFuture.completeExceptionally(e);
            }
        });
        return completableFuture;
    }

    @Override
    public CompletableFuture<Message> executeAsync(SendAudio sendAudio) {
        CompletableFuture<Message> completableFuture = new CompletableFuture<>();
        exe.submit(() -> {
            try {
                completableFuture.complete(execute(sendAudio));
            } catch (TelegramApiException e) {
                completableFuture.completeExceptionally(e);
            }
        });
        return completableFuture;
    }

    @Override
    public CompletableFuture<Message> executeAsync(SendVoice sendVoice) {
        CompletableFuture<Message> completableFuture = new CompletableFuture<>();
        exe.submit(() -> {
            try {
                completableFuture.complete(execute(sendVoice));
            } catch (TelegramApiException e) {
                completableFuture.completeExceptionally(e);
            }
        });
        return completableFuture;
    }

    @Override
    public CompletableFuture<List<Message>> executeAsync(SendMediaGroup sendMediaGroup) {
        CompletableFuture<List<Message>> completableFuture = new CompletableFuture<>();
        exe.submit(() -> {
            try {
                completableFuture.complete(execute(sendMediaGroup));
            } catch (TelegramApiException e) {
                completableFuture.completeExceptionally(e);
            }
        });
        return completableFuture;
    }

    @Override
    public CompletableFuture<Boolean> executeAsync(SetChatPhoto setChatPhoto) {
        CompletableFuture<Boolean> completableFuture = new CompletableFuture<>();
        exe.submit(() -> {
            try {
                completableFuture.complete(execute(setChatPhoto));
            } catch (TelegramApiException e) {
                completableFuture.completeExceptionally(e);
            }
        });
        return completableFuture;
    }

    @Override
    public CompletableFuture<Boolean> executeAsync(AddStickerToSet addStickerToSet) {
        CompletableFuture<Boolean> completableFuture = new CompletableFuture<>();
        exe.submit(() -> {
            try {
                completableFuture.complete(execute(addStickerToSet));
            } catch (TelegramApiException e) {
                completableFuture.completeExceptionally(e);
            }
        });
        return completableFuture;
    }

    @Override
    public CompletableFuture<Boolean> executeAsync(SetStickerSetThumb setStickerSetThumb) {
        CompletableFuture<Boolean> completableFuture = new CompletableFuture<>();
        exe.submit(() -> {
            try {
                completableFuture.complete(execute(setStickerSetThumb));
            } catch (TelegramApiException e) {
                completableFuture.completeExceptionally(e);
            }
        });
        return completableFuture;
    }

    @Override
    public CompletableFuture<Boolean> executeAsync(CreateNewStickerSet createNewStickerSet) {
        CompletableFuture<Boolean> completableFuture = new CompletableFuture<>();
        exe.submit(() -> {
            try {
                completableFuture.complete(execute(createNewStickerSet));
            } catch (TelegramApiException e) {
                completableFuture.completeExceptionally(e);
            }
        });
        return completableFuture;
    }

    @Override
    public CompletableFuture<File> executeAsync(UploadStickerFile uploadStickerFile) {
        CompletableFuture<File> completableFuture = new CompletableFuture<>();
        exe.submit(() -> {
            try {
                completableFuture.complete(execute(uploadStickerFile));
            } catch (TelegramApiException e) {
                completableFuture.completeExceptionally(e);
            }
        });
        return completableFuture;
    }

    @Override
    public CompletableFuture<Serializable> executeAsync(EditMessageMedia editMessageMedia) {
        CompletableFuture<Serializable> completableFuture = new CompletableFuture<>();
        exe.submit(() -> {
            try {
                completableFuture.complete(execute(editMessageMedia));
            } catch (TelegramApiException e) {
                completableFuture.completeExceptionally(e);
            }
        });
        return completableFuture;
    }

    @Override
    public CompletableFuture<Message> executeAsync(SendAnimation sendAnimation) {
        CompletableFuture<Message> completableFuture = new CompletableFuture<>();
        exe.submit(() -> {
            try {
                completableFuture.complete(execute(sendAnimation));
            } catch (TelegramApiException e) {
                completableFuture.completeExceptionally(e);
            }
        });
        return completableFuture;
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
    protected <T extends Serializable, Method extends BotApiMethod<T>> CompletableFuture<T> sendApiMethodAsync(Method method) {
        CompletableFuture<T> completableFuture = new CompletableFuture<>();
        exe.submit(() -> {
            try {
                String responseContent = sendMethodRequest(method);
                completableFuture.complete(method.deserializeResponse(responseContent));
            } catch (IOException | TelegramApiValidationException | TelegramApiRequestException e) {
                completableFuture.completeExceptionally(e);
            }
        });
        return completableFuture;
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

    // Private methods

    private void configureHttpContext() {

        if (options.getProxyType() != DefaultBotOptions.ProxyType.NO_PROXY) {
            InetSocketAddress socketAddress = new InetSocketAddress(options.getProxyHost(), options.getProxyPort());
            options.getHttpContext().setAttribute("socketAddress", socketAddress);
        }

        if (options.getProxyType() == DefaultBotOptions.ProxyType.SOCKS4) {
            options.getHttpContext().setAttribute("socksVersion", 4);
        }
        if (options.getProxyType() == DefaultBotOptions.ProxyType.SOCKS5) {
            options.getHttpContext().setAttribute("socksVersion", 5);
        }

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
        try (CloseableHttpResponse response = httpClient.execute(httppost, options.getHttpContext())) {
            return EntityUtils.toString(response.getEntity(), StandardCharsets.UTF_8);
        }
    }

    private HttpPost configuredHttpPost(String url) {
        HttpPost httppost = new HttpPost(url);
        httppost.setConfig(requestConfig);
        return httppost;
    }

    private void addInputData(MultipartEntityBuilder builder, InputMedia media, String mediaField, boolean addField) throws JsonProcessingException {
        if (media.isNewMedia()) {
            if (media.getNewMediaFile() != null) {
                builder.addBinaryBody(media.getMediaName(), media.getNewMediaFile(), ContentType.APPLICATION_OCTET_STREAM, media.getMediaName());
            } else if (media.getNewMediaStream() != null) {
                builder.addBinaryBody(media.getMediaName(), media.getNewMediaStream(), ContentType.APPLICATION_OCTET_STREAM, media.getMediaName());
            }
        }

        if (media instanceof InputMediaAudio) {
            InputMediaAudio audio = (InputMediaAudio) media;
            if (audio.getThumbnail() != null) {
                addInputFile(builder, audio.getThumbnail(), InputMediaAudio.THUMBNAIL_FIELD, false);
            }
        } else if (media instanceof InputMediaDocument) {
            InputMediaDocument document = (InputMediaDocument) media;
            if (document.getThumbnail() != null) {
                addInputFile(builder, document.getThumbnail(), InputMediaDocument.THUMBNAIL_FIELD, false);
            }
        } else if (media instanceof InputMediaVideo) {
            InputMediaVideo video = (InputMediaVideo) media;
            if (video.getThumbnail() != null) {
                addInputFile(builder, video.getThumbnail(), InputMediaVideo.THUMBNAIL_FIELD, false);
            }
        } else if (media instanceof InputMediaAnimation) {
            InputMediaAnimation animation = (InputMediaAnimation) media;
            if (animation.getThumbnail() != null) {
                addInputFile(builder, animation.getThumbnail(), InputMediaAnimation.THUMBNAIL_FIELD, false);
            }
        }

        if (addField) {
            builder.addTextBody(mediaField, objectMapper.writeValueAsString(media), TEXT_PLAIN_CONTENT_TYPE);
        }
    }

    private void addInputData(MultipartEntityBuilder builder, List<InputMedia> media, String mediaField) throws JsonProcessingException {
        for (InputMedia inputMedia : media) {
            addInputData(builder, inputMedia, null, false);
        }

        builder.addTextBody(mediaField, objectMapper.writeValueAsString(media), TEXT_PLAIN_CONTENT_TYPE);
    }

    private void addStickers(MultipartEntityBuilder builder, List<InputSticker> stickers, String stickersField) throws JsonProcessingException {
        for (InputSticker sticker : stickers) {
            addInputFile(builder, sticker.getSticker(), null, false);
        }

        builder.addTextBody(stickersField, objectMapper.writeValueAsString(stickers), TEXT_PLAIN_CONTENT_TYPE);
    }

    private void addInputFile(MultipartEntityBuilder builder, InputFile file, String fileField, boolean addField) {
        if (file.isNew()) {
            if (file.getNewMediaFile() != null) {
                builder.addBinaryBody(file.getMediaName(), file.getNewMediaFile(), ContentType.APPLICATION_OCTET_STREAM, file.getMediaName());
            } else if (file.getNewMediaStream() != null) {
                builder.addBinaryBody(file.getMediaName(), file.getNewMediaStream(), ContentType.APPLICATION_OCTET_STREAM, file.getMediaName());
            }
        }

        if (addField) {
            builder.addTextBody(fileField, file.getAttachName(), TEXT_PLAIN_CONTENT_TYPE);
        }
    }

    private void assertParamNotNull(Object param, String paramName) throws TelegramApiException {
        if (param == null) {
            throw new TelegramApiException("Parameter " + paramName + " can not be null");
        }
    }
}
