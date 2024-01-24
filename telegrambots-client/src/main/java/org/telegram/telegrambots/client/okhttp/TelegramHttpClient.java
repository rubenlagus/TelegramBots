package org.telegram.telegrambots.client.okhttp;

import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
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
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;

public abstract class TelegramHttpClient extends AbstractTelegramClient {
    private final String botToken;
    private final String baseUrl;
    private final ObjectMapper objectMapper;

    public TelegramHttpClient(ObjectMapper objectMapper, String botToken, String baseUrl) {
        this.objectMapper = Objects.requireNonNull(objectMapper);
        this.botToken = Objects.requireNonNull(botToken);
        this.baseUrl = Objects.requireNonNull(baseUrl);
    }

    public TelegramHttpClient(ObjectMapper objectMapper, String botToken) {
        this(objectMapper, botToken, "https://api.telegram.org");
    }

    @Override
    public <T extends Serializable, Method extends BotApiMethod<T>> CompletableFuture<T> executeAsync(Method method) throws TelegramApiException {
        if (method == null) {
            throw new TelegramApiException("Parameter method can not be null");
        }
        method.validate();

        try {
            String body = objectMapper.writeValueAsString(method);
            return sendRequest(method, RequestBody.create(body, MediaType.parse("application/json")));
        } catch (IOException e) {
            throw new TelegramApiException("Unable to execute " + method.getMethod() + " method", e);
        }
    }

    @Override
    public CompletableFuture<Message> executeAsync(SendDocument sendDocument) {
        return executeMediaMethod(sendDocument, builder -> {
            builder.addPart(SendDocument.CAPTION_FIELD, sendDocument.getCaption())
                    .addPart(SendDocument.PARSEMODE_FIELD, sendDocument.getParseMode())
                    .addPart(SendDocument.DISABLECONTENTTYPEDETECTION_FIELD, sendDocument.getDisableContentTypeDetection())
                    .addJsonPart(SendDocument.CAPTION_ENTITIES_FIELD, sendDocument.getCaptionEntities());

            if (sendDocument.getThumbnail() != null) {
                builder.addInputFile(SendDocument.THUMBNAIL_FIELD, sendDocument.getThumbnail(), false);
                builder.addPart(SendDocument.THUMBNAIL_FIELD, sendDocument.getThumbnail().getAttachName());
            }
        });
    }

    @Override
    public CompletableFuture<Message> executeAsync(SendPhoto sendPhoto) {
        return executeMediaMethod(sendPhoto, builder -> builder
                .addPart(SendPhoto.CAPTION_FIELD, sendPhoto.getCaption())
                .addPart(SendPhoto.PARSE_MODE_FIELD, sendPhoto.getParseMode())
                .addPart(SendPhoto.HAS_SPOILER_FIELD, sendPhoto.getHasSpoiler())
                .addJsonPart(SendPhoto.CAPTION_ENTITIES_FIELD, sendPhoto.getCaptionEntities()));
    }

    @Override
    public CompletableFuture<Message> executeAsync(SendVideo sendVideo) {
        return executeMediaMethod(sendVideo, builder -> {
            builder
                    .addPart(SendVideo.CAPTION_FIELD, sendVideo.getCaption())
                    .addPart(SendVideo.PARSE_MODE_FIELD, sendVideo.getParseMode())
                    .addPart(SendVideo.SUPPORTS_STREAMING_FIELD, sendVideo.getSupportsStreaming())
                    .addPart(SendVideo.DURATION_FIELD, sendVideo.getDuration())
                    .addPart(SendVideo.WIDTH_FIELD, sendVideo.getWidth())
                    .addPart(SendVideo.HEIGHT_FIELD, sendVideo.getHeight())
                    .addPart(SendVideo.HAS_SPOILER_FIELD, sendVideo.getHasSpoiler())
                    .addJsonPart(SendVideo.CAPTION_ENTITIES_FIELD, sendVideo.getCaptionEntities());

            if (sendVideo.getThumbnail() != null) {
                builder.addInputFile(SendVideo.THUMBNAIL_FIELD, sendVideo.getThumbnail(), false);
                builder.addPart(SendVideo.THUMBNAIL_FIELD, sendVideo.getThumbnail().getAttachName());
            }
        });
    }

    @Override
    public CompletableFuture<Message> executeAsync(SendVideoNote sendVideoNote) {
        return executeMediaMethod(sendVideoNote, builder -> {
            builder
                    .addPart(SendVideoNote.DURATION_FIELD, sendVideoNote.getDuration())
                    .addPart(SendVideoNote.LENGTH_FIELD, sendVideoNote.getLength());

            if (sendVideoNote.getThumbnail() != null) {
                builder.addInputFile(SendVideoNote.THUMBNAIL_FIELD, sendVideoNote.getThumbnail(), false);
                builder.addPart(SendVideoNote.THUMBNAIL_FIELD, sendVideoNote.getThumbnail().getAttachName());
            }
        });
    }

    @Override
    public CompletableFuture<Message> executeAsync(SendSticker sendSticker) {
        return executeMediaMethod(
                sendSticker,
                builder -> builder.addPart(SendSticker.EMOJI_FIELD, sendSticker.getEmoji())
        );
    }

    @Override
    public CompletableFuture<Message> executeAsync(SendAudio sendAudio) {
        return executeMediaMethod(sendAudio, builder -> {
            builder
                    .addPart(SendAudio.PERFORMER_FIELD, sendAudio.getPerformer())
                    .addPart(SendAudio.TITLE_FIELD, sendAudio.getTitle())
                    .addPart(SendAudio.DURATION_FIELD, sendAudio.getDuration())
                    .addPart(SendAudio.CAPTION_FIELD, sendAudio.getCaption())
                    .addPart(SendAudio.PARSE_MODE_FIELD, sendAudio.getParseMode())
                    .addJsonPart(SendAudio.CAPTION_ENTITIES_FIELD, sendAudio.getCaptionEntities());

            if (sendAudio.getThumbnail() != null) {
                builder.addInputFile(SendAudio.THUMBNAIL_FIELD, sendAudio.getThumbnail(), false);
                builder.addPart(SendAudio.THUMBNAIL_FIELD, sendAudio.getThumbnail().getAttachName());
            }
        });
    }

    @Override
    public CompletableFuture<Message> executeAsync(SendVoice sendVoice) {
        return executeMediaMethod(sendVoice, builder -> builder
                .addPart(SendVoice.DURATION_FIELD, sendVoice.getDuration())
                .addPart(SendVoice.CAPTION_FIELD, sendVoice.getCaption())
                .addPart(SendVoice.PARSE_MODE_FIELD, sendVoice.getParseMode())
                .addJsonPart(SendVoice.CAPTION_ENTITIES_FIELD, sendVoice.getCaptionEntities()));
    }

    @Override
    public CompletableFuture<List<Message>> executeAsync(SendMediaGroup sendMediaGroup) {
        try {
            assertParamNotNull(sendMediaGroup, "sendMediaGroup");
            sendMediaGroup.validate();

            TelegramMultipartBuilder builder = new TelegramMultipartBuilder(objectMapper);

            addInputData(builder, SendMediaGroup.MEDIA_FIELD, sendMediaGroup.getMedias());

            builder.addPart(SendMediaGroup.CHAT_ID_FIELD, sendMediaGroup.getChatId())
                    .addPart(SendMediaGroup.DISABLE_NOTIFICATION_FIELD, sendMediaGroup.getDisableNotification())
                    .addPart(SendMediaGroup.REPLY_TO_MESSAGE_ID_FIELD, sendMediaGroup.getReplyToMessageId())
                    .addPart(SendMediaGroup.MESSAGE_THREAD_ID_FIELD, sendMediaGroup.getMessageThreadId())
                    .addPart(SendMediaGroup.ALLOW_SENDING_WITHOUT_REPLY_FIELD, sendMediaGroup.getAllowSendingWithoutReply())
                    .addPart(SendMediaGroup.PROTECT_CONTENT_FIELD, sendMediaGroup.getProtectContent())
                    .addJsonPart(SendMediaGroup.REPLY_PARAMETERS_FIELD, sendMediaGroup.getReplyParameters());


            //For some reason java is having problem with casting ArrayList to List here
            return sendRequest(sendMediaGroup, builder.build()).thenApply(list -> list);
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
                            .addPart(SendAnimation.PARSE_MODE_FIELD, sendAnimation.getParseMode())
                            .addPart(SendAnimation.HAS_SPOILER_FIELD, sendAnimation.getHasSpoiler())
                            .addJsonPart(SendPhoto.REPLY_MARKUP_FIELD, sendAnimation.getReplyMarkup());

                    if (sendAnimation.getThumbnail() != null) {
                        builder.addInputFile(SendAnimation.THUMBNAIL_FIELD, sendAnimation.getThumbnail(), false);
                        builder.addPart(SendAnimation.THUMBNAIL_FIELD, sendAnimation.getThumbnail().getAttachName());
                    }
                }

        );
    }

    @Override
    public CompletableFuture<Boolean> executeAsync(SetChatPhoto setChatPhoto) {
        try {
            assertParamNotNull(setChatPhoto, "setChatPhoto");
            setChatPhoto.validate();

            TelegramMultipartBuilder builder = new TelegramMultipartBuilder(objectMapper);

            builder.addPart(SetChatPhoto.CHATID_FIELD, setChatPhoto.getChatId());

            builder.addInputFile(SetChatPhoto.PHOTO_FIELD, setChatPhoto.getPhoto(), false);

            return sendRequest(setChatPhoto, builder.build());
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

            TelegramMultipartBuilder builder = new TelegramMultipartBuilder(objectMapper);

            builder.addPart(AddStickerToSet.USERID_FIELD, addStickerToSet.getUserId())
                    .addPart(AddStickerToSet.NAME_FIELD, addStickerToSet.getName());

            builder.addInputStickers(AddStickerToSet.STICKER_FIELD, Collections.singletonList(addStickerToSet.getSticker()));

            return sendRequest(addStickerToSet, builder.build());
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

            TelegramMultipartBuilder builder = new TelegramMultipartBuilder(objectMapper);

            builder.addPart(SetStickerSetThumb.USER_ID_FIELD, setStickerSetThumb.getUserId())
                    .addPart(SetStickerSetThumb.NAME_FIELD, setStickerSetThumb.getName())
                    .addPart(SetStickerSetThumb.THUMBNAIL_FIELD, setStickerSetThumb.getThumbnail());

            return sendRequest(setStickerSetThumb, builder.build());
        } catch (TelegramApiException e) {
            return CompletableFuture.failedFuture(e);
        }
    }

    @Override
    public CompletableFuture<Boolean> executeAsync(CreateNewStickerSet createNewStickerSet) {
        try {
            assertParamNotNull(createNewStickerSet, "createNewStickerSet");
            createNewStickerSet.validate();

            TelegramMultipartBuilder builder = new TelegramMultipartBuilder(objectMapper);

            builder.addPart(CreateNewStickerSet.USER_ID_FIELD, createNewStickerSet.getUserId())
                    .addPart(CreateNewStickerSet.NAME_FIELD, createNewStickerSet.getName())
                    .addPart(CreateNewStickerSet.TITLE_FIELD, createNewStickerSet.getTitle())
                    .addPart(CreateNewStickerSet.STICKER_TYPE_FIELD, createNewStickerSet.getStickerType())
                    .addPart(CreateNewStickerSet.STICKER_FORMAT_FIELD, createNewStickerSet.getStickerFormat())
                    .addPart(CreateNewStickerSet.NEEDS_REPAINTING_FIELD, createNewStickerSet.getNeedsRepainting())
                    .addInputStickers(CreateNewStickerSet.STICKERS_FIELD, createNewStickerSet.getStickers());

            return sendRequest(createNewStickerSet, builder.build());
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

            TelegramMultipartBuilder builder = new TelegramMultipartBuilder(objectMapper);

            builder
                    .addPart(UploadStickerFile.USERID_FIELD, uploadStickerFile.getUserId())
                    .addPart(UploadStickerFile.STICKER_FORMAT_FIELD, uploadStickerFile.getStickerFormat())
                    .addInputFile(UploadStickerFile.STICKER_FIELD, uploadStickerFile.getSticker(), true);

            return sendRequest(uploadStickerFile, builder.build());
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

            TelegramMultipartBuilder builder = new TelegramMultipartBuilder(objectMapper);

            builder.addPart(EditMessageMedia.CHAT_ID_FIELD, editMessageMedia.getChatId())
                    .addPart(EditMessageMedia.MESSAGE_ID_FIELD, editMessageMedia.getMessageId())
                    .addPart(EditMessageMedia.INLINE_MESSAGE_ID_FIELD, editMessageMedia.getInlineMessageId())
                    .addJsonPart(EditMessageMedia.REPLY_MARKUP_FIELD, editMessageMedia.getReplyMarkup());

            addInputData(builder, EditMessageMedia.MEDIA_FIELD, editMessageMedia.getMedia(), true);

            return sendRequest(editMessageMedia, builder.build());
        } catch (TelegramApiException e) {
            return CompletableFuture.failedFuture(e);
        } catch (IOException e) {
            return CompletableFuture.failedFuture(new TelegramApiException("Unable to execute " + editMessageMedia.getMethod(), e));
        }
    }

    protected abstract <T extends Serializable, Method extends PartialBotApiMethod<T>> CompletableFuture<T> sendRequest(
            Method method, RequestBody body
    ) throws TelegramApiException;

    protected abstract <T extends Serializable, Method extends PartialBotApiMethod<T>> CompletableFuture<T> sendRequest(
            Method method, MultipartBody body
    ) throws TelegramApiException;

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

            TelegramMultipartBuilder builder = new TelegramMultipartBuilder(objectMapper);

            builder.addInputFile(method.getFileField(), method.getFile(), true);

            builder.addPart(SendMediaBotMethod.CHAT_ID_FIELD, method.getChatId())
                    .addPart(SendMediaBotMethod.MESSAGE_THREAD_ID_FIELD, method.getMessageThreadId())
                    .addPart(SendMediaBotMethod.REPLY_TO_MESSAGE_ID_FIELD, method.getReplyToMessageId())
                    .addPart(SendMediaBotMethod.DISABLE_NOTIFICATION_FIELD, method.getDisableNotification())
                    .addPart(SendMediaBotMethod.PROTECT_CONTENT_FIELD, method.getProtectContent())
                    .addPart(SendMediaBotMethod.ALLOW_SENDING_WITHOUT_REPLY_FIELD, method.getAllowSendingWithoutReply())
                    .addJsonPart(SendMediaBotMethod.REPLY_PARAMETERS_FIELD, method.getReplyParameters())
                    .addJsonPart(SendMediaBotMethod.REPLY_MARKUP_FIELD, method.getReplyMarkup());

            setup.accept(builder);

            return sendRequest(method, builder.build());
        } catch (TelegramApiException e) {
            return CompletableFuture.failedFuture(e);
        } catch (IOException e) {
            return CompletableFuture.failedFuture(new TelegramApiException("Unable to execute " + method.getMethod(), e));
        }
    }

    protected String buildUrl(String methodName) {
        return baseUrl + "/bot" + botToken + "/" + methodName;
    }

    private void addInputData(TelegramMultipartBuilder builder, String mediaField, InputMedia media, boolean addField) throws IOException {
        if (media.isNewMedia()) {
            builder.addMedia(media);
        }

        if (media instanceof InputMediaAudio audio) {
            if (audio.getThumbnail() != null) {
                builder.addInputFile(InputMediaAudio.THUMBNAIL_FIELD, audio.getThumbnail(), false);
            }
        } else if (media instanceof InputMediaDocument document) {
            if (document.getThumbnail() != null) {
                builder.addInputFile(InputMediaDocument.THUMBNAIL_FIELD, document.getThumbnail(), false);
            }
        } else if (media instanceof InputMediaVideo video) {
            if (video.getThumbnail() != null) {
                builder.addInputFile(InputMediaVideo.THUMBNAIL_FIELD, video.getThumbnail(), false);
            }
        } else if (media instanceof InputMediaAnimation animation) {
            if (animation.getThumbnail() != null) {
                builder.addInputFile(InputMediaAnimation.THUMBNAIL_FIELD, animation.getThumbnail(), false);
            }
        }

        if (addField) {
            builder.addJsonPart(mediaField, media);
        }
    }

    private void addInputData(TelegramMultipartBuilder builder, String mediaField, List<InputMedia> media) throws IOException {
        for (InputMedia inputMedia : media) {
            addInputData(builder, null, inputMedia, false);
        }

        builder.addJsonPart(mediaField, media);
    }

    private void assertParamNotNull(Object param, String paramName) throws TelegramApiException {
        if (param == null) {
            throw new TelegramApiException("Parameter " + paramName + " can not be null");
        }
    }

}
