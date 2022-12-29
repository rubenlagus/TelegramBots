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

public class OkHttpTelegramClient implements TelegramClient {
    @Nonnull
    private final OkHttpClient client;
    @Nonnull
    private final String botToken;
    @Nonnull
    private final String baseUrl;
    @Nonnull
    private final ObjectMapper objectMapper = new ObjectMapper();

    OkHttpTelegramClient(@Nonnull OkHttpClient client, @Nonnull String botToken, @Nonnull String baseUrl) {
        this.client = Objects.requireNonNull(client);
        this.botToken = Objects.requireNonNull(botToken);
        this.baseUrl = Objects.requireNonNull(baseUrl);
    }

    OkHttpTelegramClient(@Nonnull OkHttpClient client, @Nonnull String botToken) {
        this(client, botToken, "https://api.telegram.org");
    }

    OkHttpTelegramClient(@Nonnull String botToken, @Nonnull String baseUrl) {
        this(new OkHttpClient.Builder().build(), botToken, baseUrl);
    }

    OkHttpTelegramClient(@Nonnull String botToken) {
        this(new OkHttpClient.Builder().build(), botToken);
    }


    //There are warnings due to "unchecked casts" to CompletableFuture<T> since it uses a generic type.
    // The cast is checked to succeed since the type of Method is checked at runtime
    @SuppressWarnings("unchecked")
    @Override
    public <T extends Serializable, Method extends PartialBotApiMethod<T>> CompletableFuture<T> executeAsync(Method method) throws TelegramApiException {
        if (method instanceof BotApiMethod)
            return executeBotApiMethodAsync((BotApiMethod<T>) method);

        if (method instanceof SendDocument)
            return (CompletableFuture<T>) executeAsync((SendDocument) method);

        if (method instanceof SendPhoto)
            return (CompletableFuture<T>) executeAsync((SendPhoto) method);

        if (method instanceof SendVideo)
            return (CompletableFuture<T>) executeAsync((SendVideo) method);

        if (method instanceof SendVideoNote)
            return (CompletableFuture<T>) executeAsync((SendVideoNote) method);

        if (method instanceof SendSticker)
            return (CompletableFuture<T>) executeAsync((SendSticker) method);

        if (method instanceof SendAudio)
            return (CompletableFuture<T>) executeAsync((SendAudio) method);

        if (method instanceof SendVoice)
            return (CompletableFuture<T>) executeAsync((SendVoice) method);

        throw new TelegramApiException("Unsupported Method" + method.getMethod());
    }

    @Override
    public <T extends Serializable, Method extends PartialBotApiMethod<T>> T execute(Method method) throws TelegramApiException {
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

    protected <T extends Serializable, Method extends BotApiMethod<T>> CompletableFuture<T> executeBotApiMethodAsync(@Nonnull Method method) throws TelegramApiException {
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

    private TelegramMultipartBuilder mapSendMediaBotMethod(TelegramMultipartBuilder builder, SendMediaBotMethod<?> method) throws IOException {
        addInputFile(builder, method.getFile(), method.getFileField(), true);

        builder.addPart(SendMediaBotMethod.CHATID_FIELD, method.getChatId())
                .addPart(SendMediaBotMethod.MESSAGETHREADID_FIELD, method.getMessageThreadId())
                .addPart(SendMediaBotMethod.REPLYTOMESSAGEID_FIELD, method.getReplyToMessageId())
                .addPart(SendMediaBotMethod.DISABLENOTIFICATION_FIELD, method.getDisableNotification())
                .addPart(SendMediaBotMethod.PROTECTCONTENT_FIELD, method.getProtectContent())
                .addPart(SendMediaBotMethod.ALLOWSENDINGWITHOUTREPLY_FIELD, method.getAllowSendingWithoutReply());

        return builder;
    }

    private <T extends Serializable, Method extends SendMediaBotMethod<T>> CompletableFuture<T> executeMediaMethod(
            Method method,
            ThrowingConsumer<TelegramMultipartBuilder, IOException> setup
    ) throws TelegramApiException {
        assertParamNotNull(method, "method");
        assertParamNotNull(setup, "setup");

        method.validate();

        try {
            String url = buildUrl(SendDocument.PATH);

            TelegramMultipartBuilder builder = new TelegramMultipartBuilder(objectMapper);

            mapSendMediaBotMethod(builder, method);
            setup.accept(builder);

            Request httpPost = new Request.Builder().url(url).post(builder.build()).build();

            return sendRequest(method, httpPost);
        } catch (IOException e) {
            throw new TelegramApiException("Unable to send document", e);
        }
    }

    public CompletableFuture<Message> executeAsync(SendDocument sendDocument) throws TelegramApiException {
        return executeMediaMethod(sendDocument, builder -> {
            builder.addPart(SendDocument.REPLYMARKUP_FIELD, sendDocument.getReplyMarkup())
                    .addPart(SendDocument.CAPTION_FIELD, sendDocument.getCaption())
                    .addPart(SendDocument.PARSEMODE_FIELD, sendDocument.getParseMode())
                    .addPart(SendDocument.DISABLECONTENTTYPEDETECTION_FIELD, sendDocument.getDisableContentTypeDetection())
                    .addJsonPart(SendDocument.CAPTION_ENTITIES_FIELD, sendDocument.getCaptionEntities());

            if (sendDocument.getThumb() != null) {
                addInputFile(builder, sendDocument.getThumb(), SendDocument.THUMB_FIELD, false);
                builder.addPart(SendDocument.THUMB_FIELD, sendDocument.getThumb().getAttachName());
            }
        });
    }

    public CompletableFuture<Message> executeAsync(SendPhoto sendPhoto) throws TelegramApiException {
        assertParamNotNull(sendPhoto, "sendPhoto");

        sendPhoto.validate();
        try {
            String url = buildUrl(SendPhoto.PATH);

            TelegramMultipartBuilder builder = new TelegramMultipartBuilder(objectMapper);

            mapSendMediaBotMethod(builder, sendPhoto)
                    .addJsonPart(SendPhoto.REPLYMARKUP_FIELD, sendPhoto.getReplyMarkup())
                    .addPart(SendPhoto.CAPTION_FIELD, sendPhoto.getCaption())
                    .addPart(SendPhoto.PARSEMODE_FIELD, sendPhoto.getParseMode())
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

            mapSendMediaBotMethod(builder, sendVideo)
                    .addJsonPart(SendVideo.REPLYMARKUP_FIELD, sendVideo.getReplyMarkup())
                    .addPart(SendVideo.CAPTION_FIELD, sendVideo.getCaption())
                    .addPart(SendVideo.PARSEMODE_FIELD, sendVideo.getParseMode())
                    .addPart(SendVideo.SUPPORTSSTREAMING_FIELD, sendVideo.getSupportsStreaming())
                    .addPart(SendVideo.DURATION_FIELD, sendVideo.getDuration())
                    .addPart(SendVideo.WIDTH_FIELD, sendVideo.getWidth())
                    .addPart(SendVideo.HEIGHT_FIELD, sendVideo.getHeight())
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

    public CompletableFuture<Message> executeAsync(SendVideoNote sendVideoNote) throws TelegramApiException {
        assertParamNotNull(sendVideoNote, "sendVideoNote");

        sendVideoNote.validate();
        try {
            String url = buildUrl(SendVideoNote.PATH);

            TelegramMultipartBuilder builder = new TelegramMultipartBuilder(objectMapper);

            mapSendMediaBotMethod(builder, sendVideoNote)
                    .addJsonPart(SendVideoNote.REPLYMARKUP_FIELD, sendVideoNote.getReplyMarkup())
                    .addPart(SendVideoNote.DURATION_FIELD, sendVideoNote.getDuration())
                    .addPart(SendVideoNote.LENGTH_FIELD, sendVideoNote.getLength());

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

    protected CompletableFuture<Message> executeAsync(SendSticker sendSticker) throws TelegramApiException {
        assertParamNotNull(sendSticker, "sendSticker");

        sendSticker.validate();
        try {
            String url = buildUrl(SendSticker.PATH);

            TelegramMultipartBuilder builder = new TelegramMultipartBuilder(objectMapper);

            mapSendMediaBotMethod(builder, sendSticker)
                    .addJsonPart(SendSticker.REPLYMARKUP_FIELD, sendSticker.getReplyMarkup());

            Request httpPost = new Request.Builder().url(url).post(builder.build()).build();
            return sendRequest(sendSticker, httpPost);
        } catch (IOException e) {
            throw new TelegramApiException("Unable to send sticker", e);
        }
    }

    protected CompletableFuture<Message> executeAsync(SendAudio sendAudio) throws TelegramApiException {
        assertParamNotNull(sendAudio, "sendAudio");
        sendAudio.validate();
        try {
            String url = buildUrl(SendSticker.PATH);

            TelegramMultipartBuilder builder = new TelegramMultipartBuilder(objectMapper);

            mapSendMediaBotMethod(builder, sendAudio)
                    .addJsonPart(SendAudio.REPLYMARKUP_FIELD, sendAudio.getReplyMarkup())
                    .addPart(SendAudio.PERFOMER_FIELD, sendAudio.getPerformer())
                    .addPart(SendAudio.TITLE_FIELD, sendAudio.getTitle())
                    .addPart(SendAudio.DURATION_FIELD, sendAudio.getDuration())
                    .addPart(SendAudio.CAPTION_FIELD, sendAudio.getCaption())
                    .addPart(SendAudio.PARSEMODE_FIELD, sendAudio.getParseMode())
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

    protected CompletableFuture<Message> executeAsync(SendVoice sendVoice) throws TelegramApiException {
        assertParamNotNull(sendVoice, "sendVoice");
        sendVoice.validate();
        try {
            String url = buildUrl(SendVideoNote.PATH);

            TelegramMultipartBuilder builder = new TelegramMultipartBuilder(objectMapper);

            mapSendMediaBotMethod(builder, sendVoice)
                    .addJsonPart(SendVoice.REPLYMARKUP_FIELD, sendVoice.getReplyMarkup())
                    .addPart(SendVoice.DURATION_FIELD, sendVoice.getDuration())
                    .addPart(SendVoice.CAPTION_FIELD, sendVoice.getCaption())
                    .addPart(SendVoice.PARSEMODE_FIELD, sendVoice.getParseMode())
                    .addJsonPart(SendVoice.CAPTION_ENTITIES_FIELD, sendVoice.getCaptionEntities());

            Request httpPost = new Request.Builder().url(url).post(builder.build()).build();
            return sendRequest(sendVoice, httpPost);
        } catch (IOException e) {
            throw new TelegramApiException("Unable to send voice", e);
        }
    }

    protected CompletableFuture<Boolean> executeAsync(SetChatPhoto setChatPhoto) throws TelegramApiException {
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
