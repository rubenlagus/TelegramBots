package org.telegram.telegrambots.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.*;
import org.jetbrains.annotations.NotNull;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.PartialBotApiMethod;
import org.telegram.telegrambots.meta.api.methods.groupadministration.SetChatPhoto;
import org.telegram.telegrambots.meta.api.methods.send.*;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.io.IOException;
import java.io.Serializable;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class TelegramClient {
    @Nonnull
    private final OkHttpClient client;
    @Nonnull
    private final String botToken;
    @Nonnull
    private final String baseUrl;
    @Nonnull
    private final ObjectMapper objectMapper = new ObjectMapper();

    TelegramClient(@Nonnull OkHttpClient client, @Nonnull String botToken, @Nonnull String baseUrl) {
        this.client = Objects.requireNonNull(client);
        this.botToken = Objects.requireNonNull(botToken);
        this.baseUrl = Objects.requireNonNull(baseUrl);
    }

    TelegramClient(@Nonnull OkHttpClient client, @Nonnull String botToken) {
        this(client, botToken, "https://api.telegram.org");
    }

    TelegramClient(@Nonnull String botToken, @Nonnull String baseUrl) {
        this(new OkHttpClient.Builder().build(), botToken, baseUrl);
    }

    TelegramClient(@Nonnull String botToken) {
        this(new OkHttpClient.Builder().build(), botToken);
    }

    public <T extends Serializable, Method extends BotApiMethod<T>> T execute(@Nonnull Method method) throws TelegramApiException {
        try {
            return executeAsync(method).get();
        } catch (Exception e) {
            throw mapException(e, method.getMethod());
        }
    }

    private TelegramApiException mapException(Exception e, String method) {
        if (e instanceof ExecutionException) {
            if (e.getCause() instanceof TelegramApiException) {
                return (TelegramApiException) e.getCause();
            } else {
                return new TelegramApiException("Unable to execute" + method + "method", e.getCause());
            }
        } else {
            return new TelegramApiException("Unable to execute" + method + "method", e.getCause());
        }
    }

    public <T extends Serializable, Method extends BotApiMethod<T>> CompletableFuture<T> executeAsync(@Nonnull Method method) throws TelegramApiException {
        //Intellij is a bit too optimistic with @NonNull here
        //noinspection ConstantConditions
        if (method == null) {
            throw new TelegramApiException("Parameter method can not be null");
        }
        method.validate();

        try {
            String url = buildUrl(method.getMethod());
            String body = objectMapper.writeValueAsString(method);
            Map<String, String> headers = Map.ofEntries(
                    Map.entry("charset", StandardCharsets.UTF_8.name()),
                    Map.entry("content-type", "application/json")
            );

            Request request = new Request.Builder()
                    .url(url)
                    .headers(Headers.of(headers))
                    .post(RequestBody.create(body, MediaType.parse("application/json")))
                    .build();

            return sendRequest(method, request);
        } catch (IOException e) {
            throw new TelegramApiException("Unable to execute " + method.getMethod() + " method", e);
        }
    }

    public CompletableFuture<Message> executeAsync(SendDocument sendDocument) throws TelegramApiException {
        assertParamNotNull(sendDocument, "sendDocument");

        sendDocument.validate();
        try {
            String url = buildUrl(SendDocument.PATH);

            TelegramMultipartBuilder builder = new TelegramMultipartBuilder(objectMapper);

            addInputFile(builder, sendDocument.getDocument(), SendDocument.DOCUMENT_FIELD, true);

            builder.addPart(SendDocument.CHATID_FIELD, sendDocument.getChatId())
                    .addPart(SendDocument.REPLYMARKUP_FIELD, sendDocument.getReplyMarkup())
                    .addPart(SendDocument.REPLYTOMESSAGEID_FIELD, sendDocument.getReplyToMessageId())
                    .addPart(SendDocument.CAPTION_FIELD, sendDocument.getCaption())
                    .addPart(SendDocument.PARSEMODE_FIELD, sendDocument.getParseMode())
                    .addPart(SendDocument.DISABLENOTIFICATION_FIELD, sendDocument.getDisableNotification())
                    .addPart(SendDocument.PROTECTCONTENT_FIELD, sendDocument.getProtectContent())
                    .addPart(SendDocument.ALLOWSENDINGWITHOUTREPLY_FIELD, sendDocument.getAllowSendingWithoutReply())
                    .addPart(SendDocument.DISABLECONTENTTYPEDETECTION_FIELD, sendDocument.getDisableContentTypeDetection())
                    .addPart(SendDocument.CAPTION_ENTITIES_FIELD, sendDocument.getCaptionEntities());

            if (sendDocument.getThumb() != null) {
                addInputFile(builder, sendDocument.getThumb(), SendDocument.THUMB_FIELD, false);
                builder.addPart(SendDocument.THUMB_FIELD, sendDocument.getThumb().getAttachName());
            }

            Request httpPost = new Request.Builder().url(url).post(builder.build()).build();

            return sendRequest(sendDocument, httpPost);
        } catch (IOException e) {
            throw new TelegramApiException("Unable to send document", e);
        }
    }

    public CompletableFuture<Message> executeAsync(SendPhoto sendPhoto) throws TelegramApiException {
        assertParamNotNull(sendPhoto, "sendPhoto");

        sendPhoto.validate();
        try {
            String url = buildUrl(SendPhoto.PATH);

            TelegramMultipartBuilder builder = new TelegramMultipartBuilder(objectMapper);

            addInputFile(builder, sendPhoto.getPhoto(), SendPhoto.PHOTO_FIELD, true);

            builder.addPart(SendDocument.CHATID_FIELD, sendPhoto.getChatId())
                    .addJsonPart(SendPhoto.REPLYMARKUP_FIELD, sendPhoto.getReplyMarkup())
                    .addPart(SendPhoto.REPLYTOMESSAGEID_FIELD, sendPhoto.getReplyToMessageId())
                    .addPart(SendPhoto.CAPTION_FIELD, sendPhoto.getCaption())
                    .addPart(SendPhoto.PARSEMODE_FIELD, sendPhoto.getParseMode())
                    .addPart(SendPhoto.DISABLENOTIFICATION_FIELD, sendPhoto.getDisableNotification())
                    .addPart(SendPhoto.ALLOWSENDINGWITHOUTREPLY_FIELD, sendPhoto.getAllowSendingWithoutReply())
                    .addPart(SendPhoto.PROTECTCONTENT_FIELD, sendPhoto.getProtectContent())
                    .addJsonPart(SendPhoto.CAPTION_ENTITIES_FIELD, sendPhoto.getCaptionEntities());

            Request httpPost = new Request.Builder().url(url).post(builder.build()).build();

            return sendRequest(sendPhoto, httpPost);
        } catch (IOException e) {
            throw new TelegramApiException("Unable to send photo", e);
        }
    }

    public CompletableFuture<Message> executeAsync(SendVideo sendVideo) throws TelegramApiException {
        assertParamNotNull(sendVideo, "sendVideo");

        sendVideo.validate();
        try {
            String url = buildUrl(SendVideo.PATH);

            TelegramMultipartBuilder builder = new TelegramMultipartBuilder(objectMapper);

            addInputFile(builder, sendVideo.getVideo(), SendVideo.VIDEO_FIELD, true);

            builder.addPart(SendVideo.CHATID_FIELD, sendVideo.getChatId())
                    .addJsonPart(SendVideo.REPLYMARKUP_FIELD, sendVideo.getReplyMarkup())
                    .addPart(SendVideo.REPLYTOMESSAGEID_FIELD, sendVideo.getReplyToMessageId())
                    .addPart(SendVideo.CAPTION_FIELD, sendVideo.getCaption())
                    .addPart(SendVideo.PARSEMODE_FIELD, sendVideo.getParseMode())
                    .addPart(SendVideo.SUPPORTSSTREAMING_FIELD, sendVideo.getSupportsStreaming())
                    .addPart(SendVideo.DURATION_FIELD, sendVideo.getDuration())
                    .addPart(SendVideo.WIDTH_FIELD, sendVideo.getWidth())
                    .addPart(SendVideo.HEIGHT_FIELD, sendVideo.getHeight())
                    .addPart(SendVideo.DISABLENOTIFICATION_FIELD, sendVideo.getDisableNotification())
                    .addPart(SendVideo.PROTECTCONTENT_FIELD, sendVideo.getProtectContent())
                    .addPart(SendVideo.ALLOWSENDINGWITHOUTREPLY_FIELD, sendVideo.getAllowSendingWithoutReply())
                    .addJsonPart(SendVideo.CAPTION_ENTITIES_FIELD, sendVideo.getCaptionEntities());

            if (sendVideo.getThumb() != null) {
                addInputFile(builder, sendVideo.getThumb(), SendVideo.THUMB_FIELD, false);
                builder.addPart(SendVideo.THUMB_FIELD, sendVideo.getThumb().getAttachName());
            }

            Request httpPost = new Request.Builder().url(url).post(builder.build()).build();
            return sendRequest(sendVideo, httpPost);
        } catch (IOException e) {
            throw new TelegramApiException("Unable to send video", e);
        }
    }

    public final CompletableFuture<Message> executeAsync(SendVideoNote sendVideoNote) throws TelegramApiException {
        assertParamNotNull(sendVideoNote, "sendVideoNote");

        sendVideoNote.validate();
        try {
            String url = buildUrl(SendVideoNote.PATH);

            TelegramMultipartBuilder builder = new TelegramMultipartBuilder(objectMapper);

            addInputFile(builder, sendVideoNote.getVideoNote(), SendVideoNote.VIDEONOTE_FIELD, true);

            builder.addPart(SendVideoNote.CHATID_FIELD, sendVideoNote.getChatId())
                    .addJsonPart(SendVideoNote.REPLYMARKUP_FIELD, sendVideoNote.getReplyMarkup())
                    .addPart(SendVideoNote.REPLYTOMESSAGEID_FIELD, sendVideoNote.getReplyToMessageId())
                    .addPart(SendVideoNote.DURATION_FIELD, sendVideoNote.getDuration())
                    .addPart(SendVideoNote.LENGTH_FIELD, sendVideoNote.getLength())
                    .addPart(SendVideoNote.DISABLENOTIFICATION_FIELD, sendVideoNote.getDisableNotification())
                    .addPart(SendVideoNote.PROTECTCONTENT_FIELD, sendVideoNote.getProtectContent())
                    .addPart(SendVideoNote.ALLOWSENDINGWITHOUTREPLY_FIELD, sendVideoNote.getAllowSendingWithoutReply());

            if (sendVideoNote.getThumb() != null) {
                addInputFile(builder, sendVideoNote.getThumb(), SendVideoNote.THUMB_FIELD, false);
                builder.addPart(SendVideoNote.THUMB_FIELD, sendVideoNote.getThumb().getAttachName());
            }

            Request httpPost = new Request.Builder().url(url).post(builder.build()).build();
            return sendRequest(sendVideoNote, httpPost);
        } catch (IOException e) {
            throw new TelegramApiException("Unable to send video note", e);
        }
    }

    public CompletableFuture<Message> executeAsync(SendSticker sendSticker) throws TelegramApiException {
        assertParamNotNull(sendSticker, "sendSticker");

        sendSticker.validate();
        try {
            String url = buildUrl(SendSticker.PATH);

            TelegramMultipartBuilder builder = new TelegramMultipartBuilder(objectMapper);

            addInputFile(builder, sendSticker.getSticker(), SendSticker.STICKER_FIELD, true);

            builder.addPart(SendSticker.CHATID_FIELD, sendSticker.getChatId())
                    .addJsonPart(SendSticker.REPLYMARKUP_FIELD, sendSticker.getReplyMarkup())
                    .addPart(SendSticker.REPLYTOMESSAGEID_FIELD, sendSticker.getReplyToMessageId())
                    .addPart(SendSticker.DISABLENOTIFICATION_FIELD, sendSticker.getDisableNotification())
                    .addPart(SendSticker.PROTECTCONTENT_FIELD, sendSticker.getProtectContent())
                    .addPart(SendSticker.ALLOWSENDINGWITHOUTREPLY_FIELD, sendSticker.getAllowSendingWithoutReply());


            Request httpPost = new Request.Builder().url(url).post(builder.build()).build();
            return sendRequest(sendSticker, httpPost);
        } catch (IOException e) {
            throw new TelegramApiException("Unable to send sticker", e);
        }
    }

    public final CompletableFuture<Message> executeAsync(SendAudio sendAudio) throws TelegramApiException {
        assertParamNotNull(sendAudio, "sendAudio");
        sendAudio.validate();
        try {
            String url = buildUrl(SendSticker.PATH);

            TelegramMultipartBuilder builder = new TelegramMultipartBuilder(objectMapper);

            addInputFile(builder, sendAudio.getAudio(), SendAudio.AUDIO_FIELD, true);

            builder.addPart(SendAudio.CHATID_FIELD, sendAudio.getChatId())
                    .addJsonPart(SendAudio.REPLYMARKUP_FIELD, sendAudio.getReplyMarkup())
                    .addPart(SendAudio.REPLYTOMESSAGEID_FIELD, sendAudio.getReplyToMessageId())
                    .addPart(SendAudio.PERFOMER_FIELD, sendAudio.getPerformer())
                    .addPart(SendAudio.TITLE_FIELD, sendAudio.getTitle())
                    .addPart(SendAudio.DURATION_FIELD, sendAudio.getDuration())
                    .addPart(SendAudio.DISABLENOTIFICATION_FIELD, sendAudio.getDisableNotification())
                    .addPart(SendAudio.CAPTION_FIELD, sendAudio.getCaption())
                    .addPart(SendAudio.PARSEMODE_FIELD, sendAudio.getParseMode())
                    .addPart(SendAudio.ALLOWSENDINGWITHOUTREPLY_FIELD, sendAudio.getAllowSendingWithoutReply())
                    .addPart(SendAudio.PROTECTCONTENT_FIELD, sendAudio.getProtectContent())
                    .addJsonPart(SendAudio.CAPTION_ENTITIES_FIELD, sendAudio.getCaptionEntities());

            if (sendAudio.getThumb() != null) {
                addInputFile(builder, sendAudio.getThumb(), SendAudio.THUMB_FIELD, false);
                builder.addPart(SendAudio.THUMB_FIELD, sendAudio.getThumb().getAttachName());
            }
            Request httpPost = new Request.Builder().url(url).post(builder.build()).build();
            return sendRequest(sendAudio, httpPost);
        } catch (IOException e) {
            throw new TelegramApiException("Unable to send audio", e);
        }
    }

    public final CompletableFuture<Message> executeAsync(SendVoice sendVoice) throws TelegramApiException {
        assertParamNotNull(sendVoice, "sendVoice");
        sendVoice.validate();
        try {
            String url = buildUrl(SendVideoNote.PATH);

            TelegramMultipartBuilder builder = new TelegramMultipartBuilder(objectMapper);

            addInputFile(builder, sendVoice.getVoice(), SendVoice.VOICE_FIELD, true);

            builder.addPart(SendVoice.CHATID_FIELD, sendVoice.getChatId())
                    .addJsonPart(SendVoice.REPLYMARKUP_FIELD, sendVoice.getReplyMarkup())
                    .addPart(SendVoice.REPLYTOMESSAGEID_FIELD, sendVoice.getReplyToMessageId())
                    .addPart(SendVoice.DISABLENOTIFICATION_FIELD, sendVoice.getDisableNotification())
                    .addPart(SendVoice.DURATION_FIELD, sendVoice.getDuration())
                    .addPart(SendVoice.CAPTION_FIELD, sendVoice.getCaption())
                    .addPart(SendVoice.PARSEMODE_FIELD, sendVoice.getParseMode())
                    .addPart(SendVoice.ALLOWSENDINGWITHOUTREPLY_FIELD, sendVoice.getAllowSendingWithoutReply())
                    .addPart(SendVoice.PROTECTCONTENT_FIELD, sendVoice.getProtectContent())
                    .addJsonPart(SendVoice.CAPTION_ENTITIES_FIELD, sendVoice.getCaptionEntities());

            Request httpPost = new Request.Builder().url(url).post(builder.build()).build();
            return sendRequest(sendVoice, httpPost);
        } catch (IOException e) {
            throw new TelegramApiException("Unable to send voice", e);
        }
    }

    public CompletableFuture<Boolean> executeAsync(SetChatPhoto setChatPhoto) throws TelegramApiException {
        assertParamNotNull(setChatPhoto, "setChatPhoto");
        setChatPhoto.validate();

        try {
            String url = buildUrl(SetChatPhoto.PATH);

            TelegramMultipartBuilder builder = new TelegramMultipartBuilder(objectMapper);

            builder.addPart(SetChatPhoto.CHATID_FIELD, setChatPhoto.getChatId());

            addInputFile(builder, setChatPhoto.getPhoto(), SetChatPhoto.PHOTO_FIELD, false);

            Request httpPost = new Request.Builder().url(url).post(builder.build()).build();
            return sendRequest(setChatPhoto, httpPost);
        } catch (IOException e) {
            throw new TelegramApiException("Unable to set chat photo", e);
        }
    }

    @NotNull
    private <T extends Serializable, Method extends PartialBotApiMethod<T>> OkHttpFutureCallback<T, Method> sendRequest(
            @NotNull Method method, @NotNull Request request
    ) {
        OkHttpFutureCallback<T, Method> callback = new OkHttpFutureCallback<>(method);
        client.newCall(request).enqueue(callback);

        return callback;
    }

    @Nonnull
    private String buildUrl(@Nonnull String methodName) {
        return baseUrl + "/bot" + botToken + "/" + methodName;
    }

    private void addInputFile(
            @NotNull TelegramMultipartBuilder builder,
            @Nullable InputFile file,
            @NotNull String fileField,
            boolean addField
    ) throws IOException {
        if (file == null) return;

        if (file.isNew()) {
            RequestBody body = null;
            if (file.getNewMediaFile() != null) {
                body = RequestBody.create(file.getNewMediaFile(), MediaType.parse("application/octet-stream"));
            } else if (file.getNewMediaStream() != null) {
                body = RequestBody.create(file.getNewMediaStream().readAllBytes(), MediaType.parse("application/octet-stream")
                );
            }
            if (body != null) {
                builder.internalBuilder.addFormDataPart(file.getMediaName(), file.getMediaName(), body);
            }
        }

        if (addField) {
            builder.internalBuilder.addFormDataPart(fileField, file.getAttachName());
        }
    }

    private void assertParamNotNull(Object param, String paramName) throws TelegramApiException {
        if (param == null) {
            throw new TelegramApiException("Parameter " + paramName + " can not be null");
        }
    }

}
