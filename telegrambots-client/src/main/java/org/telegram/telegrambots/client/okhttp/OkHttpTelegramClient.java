package org.telegram.telegrambots.client.okhttp;

import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.Headers;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import org.telegram.telegrambots.client.AbstractTelegramClient;
import org.telegram.telegrambots.client.TelegramMultipartBuilder;
import org.telegram.telegrambots.client.ThrowingConsumer;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.PartialBotApiMethod;
import org.telegram.telegrambots.meta.api.methods.groupadministration.SetChatPhoto;
import org.telegram.telegrambots.meta.api.methods.send.SendAnimation;
import org.telegram.telegrambots.meta.api.methods.send.SendAudio;
import org.telegram.telegrambots.meta.api.methods.send.SendDocument;
import org.telegram.telegrambots.meta.api.methods.send.SendMediaBotMethod;
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
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.media.InputMedia;
import org.telegram.telegrambots.meta.api.objects.media.InputMediaAnimation;
import org.telegram.telegrambots.meta.api.objects.media.InputMediaAudio;
import org.telegram.telegrambots.meta.api.objects.media.InputMediaDocument;
import org.telegram.telegrambots.meta.api.objects.media.InputMediaVideo;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.IOException;
import java.io.Serializable;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;

public class OkHttpTelegramClient extends AbstractTelegramClient {
    final OkHttpClient client;
    private final String botToken;
    private final String baseUrl;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public OkHttpTelegramClient(OkHttpClient client, String botToken, String baseUrl) {
        this.client = Objects.requireNonNull(client);
        this.botToken = Objects.requireNonNull(botToken);
        this.baseUrl = Objects.requireNonNull(baseUrl);
    }

    public OkHttpTelegramClient(OkHttpClient client, String botToken) {
        this(client, botToken, "https://api.telegram.org");
    }

    public OkHttpTelegramClient(String botToken, String baseUrl) {
        this(new OkHttpClient.Builder().build(), botToken, baseUrl);
    }

    public OkHttpTelegramClient(String botToken) {
        this(new OkHttpClient.Builder().build(), botToken);
    }

    @Override
    public <T extends Serializable, Method extends BotApiMethod<T>> CompletableFuture<T> executeAsync(Method method) throws TelegramApiException {
        //Intellij is a bit too optimistic with here
        //noinspection ConstantConditions
        if (method == null) {
            throw new TelegramApiException("Parameter method can not be null");
        }
        method.validate();

        try {
            String url = buildUrl(method.getMethod());
            String body = objectMapper.writeValueAsString(method);
            Headers headers = new Headers.Builder()
                    .add("charset", StandardCharsets.UTF_8.name())
                    .add("content-type", "application/json").build();

            Request request = new Request.Builder()
                    .url(url)
                    .headers(headers)
                    .post(RequestBody.create(body, MediaType.parse("application/json")))
                    .build();

            return sendRequest(method, request);
        } catch (IOException e) {
            throw new TelegramApiException("Unable to execute " + method.getMethod() + " method", e);
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
                builder.addInputFile(sendDocument.getThumb(), SendDocument.THUMBNAIL_FIELD, false);
                builder.addPart(SendDocument.THUMBNAIL_FIELD, sendDocument.getThumb().getAttachName());
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
                .addPart(SendVideo.HASSPOILER_FIELD, sendVideo.getHasSpoiler())
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
    public CompletableFuture<Message> executeAsync(SendAnimation sendAnimation) {
        return executeMediaMethod(sendAnimation, builder -> {
                    builder.addPart(SendAnimation.DURATION_FIELD, sendAnimation.getDuration())
                            .addPart(SendAnimation.WIDTH_FIELD, sendAnimation.getWidth())
                            .addPart(SendAnimation.HEIGHT_FIELD, sendAnimation.getHeight())
                            .addPart(SendAnimation.CAPTION_FIELD, sendAnimation.getCaption())
                            .addJsonPart(SendAnimation.CAPTION_ENTITIES_FIELD, sendAnimation.getCaptionEntities())
                            .addPart(SendAnimation.PARSEMODE_FIELD, sendAnimation.getParseMode())
                            .addJsonPart(SendPhoto.REPLYMARKUP_FIELD, sendAnimation.getReplyMarkup());

                    if (sendAnimation.getThumb() != null) {
                        builder.addInputFile(sendAnimation.getThumb(), SendAnimation.THUMBNAIL_FIELD, false);
                        builder.addPart(SendDocument.THUMBNAIL_FIELD, sendAnimation.getThumb().getAttachName());
                    }
                }

        );
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
        try {
            assertParamNotNull(addStickerToSet, "addStickerToSet");
            addStickerToSet.validate();

            String url = buildUrl(AddStickerToSet.PATH);

            TelegramMultipartBuilder builder = new TelegramMultipartBuilder(objectMapper);

            builder.addPart(AddStickerToSet.USERID_FIELD, addStickerToSet.getUserId())
                    .addPart(AddStickerToSet.NAME_FIELD, addStickerToSet.getName())
                    .addPart(AddStickerToSet.EMOJIS_FIELD, addStickerToSet.getEmojis())
                    .addInputFile(addStickerToSet.getPngSticker(), AddStickerToSet.PNGSTICKER_FIELD, true)
                    .addInputFile(addStickerToSet.getTgsSticker(), AddStickerToSet.TGSSTICKER_FIELD, true)
                    .addInputFile(addStickerToSet.getWebmSticker(), AddStickerToSet.WEBMSTICKER_FIELD, true)
                    .addJsonPart(AddStickerToSet.MASKPOSITION_FIELD, addStickerToSet.getMaskPosition());

            Request httpPost = new Request.Builder().url(url).post(builder.build()).build();
            return sendRequest(addStickerToSet, httpPost);
        } catch (TelegramApiException e) {
            return CompletableFuture.failedFuture(e);
        } catch (IOException e) {
            return CompletableFuture.failedFuture(new TelegramApiException("Unable to execute " + addStickerToSet.getMethod(), e));
        }
    }

    @Override
    public CompletableFuture<Boolean> executeAsync(SetStickerSetThumb setStickerSetThumb) {
        try {
            assertParamNotNull(setStickerSetThumb, "setStickerSetThumb");
            setStickerSetThumb.validate();

            String url = buildUrl(AddStickerToSet.PATH);

            TelegramMultipartBuilder builder = new TelegramMultipartBuilder(objectMapper);

            builder.addPart(SetStickerSetThumb.USERID_FIELD, setStickerSetThumb.getUserId())
                    .addPart(SetStickerSetThumb.NAME_FIELD, setStickerSetThumb.getName())
                    .addPart(SetStickerSetThumb.THUMB_FIELD, setStickerSetThumb.getThumb());

            Request httpPost = new Request.Builder().url(url).post(builder.build()).build();
            return sendRequest(setStickerSetThumb, httpPost);
        } catch (TelegramApiException e) {
            return CompletableFuture.failedFuture(e);
        }
    }

    @Override
    public CompletableFuture<Boolean> executeAsync(CreateNewStickerSet createNewStickerSet) {
        try {
            assertParamNotNull(createNewStickerSet, "createNewStickerSet");
            createNewStickerSet.validate();

            String url = buildUrl(AddStickerToSet.PATH);

            TelegramMultipartBuilder builder = new TelegramMultipartBuilder(objectMapper);

            builder.addPart(CreateNewStickerSet.USERID_FIELD, createNewStickerSet.getUserId())
                    .addPart(CreateNewStickerSet.NAME_FIELD, createNewStickerSet.getName())
                    .addPart(CreateNewStickerSet.EMOJIS_FIELD, createNewStickerSet.getEmojis())
                    .addPart(CreateNewStickerSet.TITLE_FIELD, createNewStickerSet.getTitle())
                    .addPart(CreateNewStickerSet.STICKERTYPE_FIELD, createNewStickerSet.getStickerType())
                    .addInputFile(createNewStickerSet.getPngSticker(), CreateNewStickerSet.PNGSTICKER_FIELD, true)
                    .addInputFile(createNewStickerSet.getTgsSticker(), CreateNewStickerSet.TGSSTICKER_FIELD, true)
                    .addInputFile(createNewStickerSet.getWebmSticker(), CreateNewStickerSet.WEBMSTICKER_FIELD, true)
                    .addJsonPart(CreateNewStickerSet.MASKPOSITION_FIELD, createNewStickerSet.getMaskPosition());

            Request httpPost = new Request.Builder().url(url).post(builder.build()).build();
            return sendRequest(createNewStickerSet, httpPost);
        } catch (TelegramApiException e) {
            return CompletableFuture.failedFuture(e);
        } catch (IOException e) {
            return CompletableFuture.failedFuture(new TelegramApiException("Unable to execute " + createNewStickerSet.getMethod(), e));
        }
    }

    @Override
    public CompletableFuture<File> executeAsync(UploadStickerFile uploadStickerFile) {
        try {
            assertParamNotNull(uploadStickerFile, "uploadStickerFile");
            uploadStickerFile.validate();

            String url = buildUrl(AddStickerToSet.PATH);

            TelegramMultipartBuilder builder = new TelegramMultipartBuilder(objectMapper);

            builder.addPart(UploadStickerFile.USERID_FIELD, uploadStickerFile.getUserId())
                    .addInputFile(uploadStickerFile.getPngSticker(), CreateNewStickerSet.PNGSTICKER_FIELD, true);

            Request httpPost = new Request.Builder().url(url).post(builder.build()).build();
            return sendRequest(uploadStickerFile, httpPost);
        } catch (TelegramApiException e) {
            return CompletableFuture.failedFuture(e);
        } catch (IOException e) {
            return CompletableFuture.failedFuture(new TelegramApiException("Unable to execute " + uploadStickerFile.getMethod(), e));
        }
    }

    @Override
    public CompletableFuture<Serializable> executeAsync(EditMessageMedia editMessageMedia) {
        try {
            assertParamNotNull(editMessageMedia, "editMessageMedia");
            editMessageMedia.validate();

            String url = buildUrl(AddStickerToSet.PATH);

            TelegramMultipartBuilder builder = new TelegramMultipartBuilder(objectMapper);

            builder.addPart(EditMessageMedia.CHATID_FIELD, editMessageMedia.getChatId())
                    .addPart(EditMessageMedia.MESSAGEID_FIELD, editMessageMedia.getMessageId())
                    .addPart(EditMessageMedia.INLINE_MESSAGE_ID_FIELD, editMessageMedia.getInlineMessageId())
                    .addJsonPart(EditMessageMedia.REPLYMARKUP_FIELD, editMessageMedia.getReplyMarkup());

            addInputData(builder, editMessageMedia.getMedia(), EditMessageMedia.MEDIA_FIELD, true);

            Request httpPost = new Request.Builder().url(url).post(builder.build()).build();
            return sendRequest(editMessageMedia, httpPost);
        } catch (TelegramApiException e) {
            return CompletableFuture.failedFuture(e);
        } catch (IOException e) {
            return CompletableFuture.failedFuture(new TelegramApiException("Unable to execute " + editMessageMedia.getMethod(), e));
        }
    }

    private <T extends Serializable, Method extends PartialBotApiMethod<T>> OkHttpFutureCallback<T, Method> sendRequest(
            Method method, Request request
    ) {
        OkHttpFutureCallback<T, Method> callback = new OkHttpFutureCallback<>(method);
        client.newCall(request).enqueue(callback);

        return callback;
    }

    /**
     * execute a SendMediaBotMethod and adds the following fields:
     * <ul>
     *     <li>chat_id</li>
     *     <li>chat_id</li>
     *     <li>message_thread_id</li>
     *     <li>reply_to_message_id</li>
     *     <li>disable_notification</li>
     *     <li>protect_content</li>
     *     <li>allow_sending_without_reply</li>
     * </ul>
     *
     * @param method the method so execute
     * @param setup  a lambda to add additional fields to the multipart request
     */
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

            builder.addInputFile(method.getFile(), method.getFileField(), true);

            builder.addPart(SendMediaBotMethod.CHATID_FIELD, method.getChatId())
                    .addPart(SendMediaBotMethod.MESSAGETHREADID_FIELD, method.getMessageThreadId())
                    .addPart(SendMediaBotMethod.REPLYTOMESSAGEID_FIELD, method.getReplyToMessageId())
                    .addPart(SendMediaBotMethod.DISABLENOTIFICATION_FIELD, method.getDisableNotification())
                    .addPart(SendMediaBotMethod.PROTECTCONTENT_FIELD, method.getProtectContent())
                    .addPart(SendMediaBotMethod.ALLOWSENDINGWITHOUTREPLY_FIELD, method.getAllowSendingWithoutReply());

            setup.accept(builder);

            Request httpPost = new Request.Builder().url(url).post(builder.build()).build();

            return sendRequest(method, httpPost);
        } catch (TelegramApiException e) {
            return CompletableFuture.failedFuture(e);
        } catch (IOException e) {
            return CompletableFuture.failedFuture(new TelegramApiException("Unable to execute " + method.getMethod(), e));
        }
    }

    private String buildUrl(String methodName) {
        return baseUrl + "/bot" + botToken + "/" + methodName;
    }

    private void addInputData(TelegramMultipartBuilder builder, InputMedia media, String mediaField, boolean addField) throws IOException {
        if (media.isNewMedia()) {
            builder.addMedia(media);
        }

        if (media instanceof InputMediaAudio) {
            InputMediaAudio audio = (InputMediaAudio) media;
            if (audio.getThumb() != null) {
                builder.addInputFile(audio.getThumb(), InputMediaAudio.THUMBNAIL_FIELD, false);
            }
        } else if (media instanceof InputMediaDocument) {
            InputMediaDocument document = (InputMediaDocument) media;
            if (document.getThumb() != null) {
                builder.addInputFile(document.getThumb(), InputMediaDocument.THUMBNAIL_FIELD, false);
            }
        } else if (media instanceof InputMediaVideo) {
            InputMediaVideo video = (InputMediaVideo) media;
            if (video.getThumb() != null) {
                builder.addInputFile(video.getThumb(), InputMediaVideo.THUMBNAIL_FIELD, false);
            }
        } else if (media instanceof InputMediaAnimation) {
            InputMediaAnimation animation = (InputMediaAnimation) media;
            if (animation.getThumb() != null) {
                builder.addInputFile(animation.getThumb(), InputMediaAnimation.THUMBNAIL_FIELD, false);
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
