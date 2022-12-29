package org.telegram.telegrambots.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.*;
import org.jetbrains.annotations.NotNull;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.PartialBotApiMethod;
import org.telegram.telegrambots.meta.api.methods.groupadministration.SetChatPhoto;
import org.telegram.telegrambots.meta.api.methods.send.*;
import org.telegram.telegrambots.meta.api.methods.stickers.AddStickerToSet;
import org.telegram.telegrambots.meta.api.methods.stickers.CreateNewStickerSet;
import org.telegram.telegrambots.meta.api.methods.stickers.SetStickerSetThumb;
import org.telegram.telegrambots.meta.api.methods.stickers.UploadStickerFile;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageMedia;
import org.telegram.telegrambots.meta.api.objects.File;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.media.*;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import javax.annotation.Nonnull;
import java.io.IOException;
import java.io.Serializable;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;

public class OkHttpTelegramClient extends AbstractTelegramClient {
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

    @Override
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

    private TelegramMultipartBuilder mapSendMediaBotMethod(TelegramMultipartBuilder builder, SendMediaBotMethod<?> method) throws IOException {
        builder.addInputFile(method.getFile(), method.getFileField(), true);

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
    ) {
        try {
            assertParamNotNull(method, "method");
            assertParamNotNull(setup, "setup");

            method.validate();

            String url = buildUrl(SendDocument.PATH);

            TelegramMultipartBuilder builder = new TelegramMultipartBuilder(objectMapper);

            mapSendMediaBotMethod(builder, method);
            setup.accept(builder);

            Request httpPost = new Request.Builder().url(url).post(builder.build()).build();

            return sendRequest(method, httpPost);
        } catch (TelegramApiException e) {
            return CompletableFuture.failedFuture(e);
        } catch (IOException e) {
            return CompletableFuture.failedFuture(new TelegramApiException("Unable to execute " + method.getMethod(), e));
        }
    }

    @Override
    public CompletableFuture<Message> executeAsync(SendDocument sendDocument) {
        return executeMediaMethod(sendDocument, builder -> {
            builder.addPart(SendDocument.REPLYMARKUP_FIELD, sendDocument.getReplyMarkup())
                    .addPart(SendDocument.CAPTION_FIELD, sendDocument.getCaption())
                    .addPart(SendDocument.PARSEMODE_FIELD, sendDocument.getParseMode())
                    .addPart(SendDocument.DISABLECONTENTTYPEDETECTION_FIELD, sendDocument.getDisableContentTypeDetection())
                    .addJsonPart(SendDocument.CAPTION_ENTITIES_FIELD, sendDocument.getCaptionEntities());

            if (sendDocument.getThumb() != null) {
                builder.addInputFile(sendDocument.getThumb(), SendDocument.THUMB_FIELD, false);
                builder.addPart(SendDocument.THUMB_FIELD, sendDocument.getThumb().getAttachName());
            }
        });
    }

    @Override
    public CompletableFuture<Message> executeAsync(SendPhoto sendPhoto) {
        return executeMediaMethod(sendPhoto, builder -> builder
                .addJsonPart(SendPhoto.REPLYMARKUP_FIELD, sendPhoto.getReplyMarkup())
                .addPart(SendPhoto.CAPTION_FIELD, sendPhoto.getCaption())
                .addPart(SendPhoto.PARSEMODE_FIELD, sendPhoto.getParseMode())
                .addJsonPart(SendPhoto.CAPTION_ENTITIES_FIELD, sendPhoto.getCaptionEntities()));
    }

    @Override
    public CompletableFuture<Message> executeAsync(SendVideo sendVideo) {
        return executeMediaMethod(sendVideo, builder -> builder
                .addJsonPart(SendVideo.REPLYMARKUP_FIELD, sendVideo.getReplyMarkup())
                .addPart(SendVideo.CAPTION_FIELD, sendVideo.getCaption())
                .addPart(SendVideo.PARSEMODE_FIELD, sendVideo.getParseMode())
                .addPart(SendVideo.SUPPORTSSTREAMING_FIELD, sendVideo.getSupportsStreaming())
                .addPart(SendVideo.DURATION_FIELD, sendVideo.getDuration())
                .addPart(SendVideo.WIDTH_FIELD, sendVideo.getWidth())
                .addPart(SendVideo.HEIGHT_FIELD, sendVideo.getHeight())
                .addJsonPart(SendVideo.CAPTION_ENTITIES_FIELD, sendVideo.getCaptionEntities()));
    }

    @Override
    public CompletableFuture<Message> executeAsync(SendVideoNote sendVideoNote) {
        return executeMediaMethod(sendVideoNote, builder -> builder
                .addJsonPart(SendVideoNote.REPLYMARKUP_FIELD, sendVideoNote.getReplyMarkup())
                .addPart(SendVideoNote.DURATION_FIELD, sendVideoNote.getDuration())
                .addPart(SendVideoNote.LENGTH_FIELD, sendVideoNote.getLength()));
    }

    @Override
    public CompletableFuture<Message> executeAsync(SendSticker sendSticker) {
        return executeMediaMethod(
                sendSticker,
                builder -> builder.addJsonPart(SendSticker.REPLYMARKUP_FIELD, sendSticker.getReplyMarkup())
        );
    }

    @Override
    public CompletableFuture<Message> executeAsync(SendAudio sendAudio) {
        return executeMediaMethod(sendAudio, builder -> builder
                .addJsonPart(SendAudio.REPLYMARKUP_FIELD, sendAudio.getReplyMarkup())
                .addPart(SendAudio.PERFOMER_FIELD, sendAudio.getPerformer())
                .addPart(SendAudio.TITLE_FIELD, sendAudio.getTitle())
                .addPart(SendAudio.DURATION_FIELD, sendAudio.getDuration())
                .addPart(SendAudio.CAPTION_FIELD, sendAudio.getCaption())
                .addPart(SendAudio.PARSEMODE_FIELD, sendAudio.getParseMode())
                .addJsonPart(SendAudio.CAPTION_ENTITIES_FIELD, sendAudio.getCaptionEntities()));
    }

    @Override
    public CompletableFuture<Message> executeAsync(SendVoice sendVoice) {
        return executeMediaMethod(sendVoice, builder -> builder
                .addJsonPart(SendVoice.REPLYMARKUP_FIELD, sendVoice.getReplyMarkup())
                .addPart(SendVoice.DURATION_FIELD, sendVoice.getDuration())
                .addPart(SendVoice.CAPTION_FIELD, sendVoice.getCaption())
                .addPart(SendVoice.PARSEMODE_FIELD, sendVoice.getParseMode())
                .addJsonPart(SendVoice.CAPTION_ENTITIES_FIELD, sendVoice.getCaptionEntities()));
    }

    @Override
    public CompletableFuture<List<Message>> executeAsync(SendMediaGroup sendMediaGroup) {
        try {
            assertParamNotNull(sendMediaGroup, "sendMediaGroup");
            sendMediaGroup.validate();

            String url = buildUrl(SendDocument.PATH);

            TelegramMultipartBuilder builder = new TelegramMultipartBuilder(objectMapper);

            addInputData(builder, sendMediaGroup.getMedias(), SendMediaGroup.MEDIA_FIELD);

            builder.addPart(SendMediaGroup.CHATID_FIELD, sendMediaGroup.getChatId())
                    .addPart(SendMediaGroup.DISABLENOTIFICATION_FIELD, sendMediaGroup.getDisableNotification())
                    .addPart(SendMediaGroup.REPLYTOMESSAGEID_FIELD, sendMediaGroup.getReplyToMessageId())
                    .addPart(SendMediaGroup.MESSAGETHREADID_FIELD, sendMediaGroup.getMessageThreadId())
                    .addPart(SendMediaGroup.ALLOWSENDINGWITHOUTREPLY_FIELD, sendMediaGroup.getAllowSendingWithoutReply())
                    .addPart(SendMediaGroup.PROTECTCONTENT_FIELD, sendMediaGroup.getProtectContent());


            Request httpPost = new Request.Builder().url(url).post(builder.build()).build();

            //For some reason java is having problem with casting ArrayList to List here
            return sendRequest(sendMediaGroup, httpPost).thenApply(list -> list);
        } catch (TelegramApiException e) {
            return CompletableFuture.failedFuture(e);
        } catch (IOException e) {
            return CompletableFuture.failedFuture(new TelegramApiException("Unable to execute " + sendMediaGroup.getMethod(), e));
        }
    }

    @Override
    public CompletableFuture<Boolean> executeAsync(SetChatPhoto setChatPhoto) {
        try {
            assertParamNotNull(setChatPhoto, "setChatPhoto");
            setChatPhoto.validate();

            String url = buildUrl(SetChatPhoto.PATH);

            TelegramMultipartBuilder builder = new TelegramMultipartBuilder(objectMapper);

            builder.addPart(SetChatPhoto.CHATID_FIELD, setChatPhoto.getChatId());

            builder.addInputFile(setChatPhoto.getPhoto(), SetChatPhoto.PHOTO_FIELD, false);

            Request httpPost = new Request.Builder().url(url).post(builder.build()).build();
            return sendRequest(setChatPhoto, httpPost);
        } catch (TelegramApiException e) {
            return CompletableFuture.failedFuture(e);
        } catch (IOException e) {
            return CompletableFuture.failedFuture(new TelegramApiException("Unable to execute " + setChatPhoto.getMethod(), e));
        }
    }

    @Override
    public CompletableFuture<Boolean> executeAsync(AddStickerToSet addStickerToSet) {
        //TODO
        return null;
    }

    @Override
    public CompletableFuture<Boolean> executeAsync(SetStickerSetThumb setStickerSetThumb) {
        //TODO
        return null;
    }

    @Override
    public CompletableFuture<Boolean> executeAsync(CreateNewStickerSet createNewStickerSet) {
        //TODO
        return null;
    }

    @Override
    public CompletableFuture<File> executeAsync(UploadStickerFile uploadStickerFile) {
        //TODO
        return null;
    }

    @Override
    public CompletableFuture<Serializable> executeAsync(EditMessageMedia editMessageMedia) {
        //TODO
        return null;
    }

    @Override
    public CompletableFuture<Message> executeAsync(SendAnimation sendAnimation) {
        //TODO
        return null;
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

    private void addInputData(TelegramMultipartBuilder builder, InputMedia media, String mediaField, boolean addField) throws IOException {
        if (media.isNewMedia()) {
            builder.addMedia(media);
        }

        if (media instanceof InputMediaAudio) {
            InputMediaAudio audio = (InputMediaAudio) media;
            if (audio.getThumb() != null) {
                builder.addInputFile(audio.getThumb(), InputMediaAudio.THUMB_FIELD, false);
            }
        } else if (media instanceof InputMediaDocument) {
            InputMediaDocument document = (InputMediaDocument) media;
            if (document.getThumb() != null) {
                builder.addInputFile(document.getThumb(), InputMediaDocument.THUMB_FIELD, false);
            }
        } else if (media instanceof InputMediaVideo) {
            InputMediaVideo video = (InputMediaVideo) media;
            if (video.getThumb() != null) {
                builder.addInputFile(video.getThumb(), InputMediaVideo.THUMB_FIELD, false);
            }
        } else if (media instanceof InputMediaAnimation) {
            InputMediaAnimation animation = (InputMediaAnimation) media;
            if (animation.getThumb() != null) {
                builder.addInputFile(animation.getThumb(), InputMediaAnimation.THUMB_FIELD, false);
            }
        }

        if (addField) {
            builder.addJsonPart(mediaField, media);
        }
    }

    private void addInputData(TelegramMultipartBuilder builder, List<InputMedia> media, String mediaField) throws IOException {
        for (InputMedia inputMedia : media) {
            addInputData(builder, inputMedia, null, false);
        }

        builder.addJsonPart(mediaField, media);
    }

    private void assertParamNotNull(Object param, String paramName) throws TelegramApiException {
        if (param == null) {
            throw new TelegramApiException("Parameter " + paramName + " can not be null");
        }
    }

}
