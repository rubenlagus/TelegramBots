package org.telegram.telegrambots.client.jetty;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.NonNull;
import lombok.SneakyThrows;
import org.apache.commons.io.FileUtils;
import org.eclipse.jetty.client.CompletableResponseListener;
import org.eclipse.jetty.client.HttpClient;
import org.eclipse.jetty.client.InputStreamResponseListener;
import org.eclipse.jetty.client.Request;
import org.eclipse.jetty.client.StringRequestContent;
import org.telegram.telegrambots.client.AbstractTelegramClient;
import org.telegram.telegrambots.client.ThrowingConsumer;
import org.telegram.telegrambots.meta.TelegramUrl;
import org.telegram.telegrambots.meta.api.methods.botapimethods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.botapimethods.PartialBotApiMethod;
import org.telegram.telegrambots.meta.api.methods.business.SetBusinessAccountProfilePhoto;
import org.telegram.telegrambots.meta.api.methods.groupadministration.SetChatPhoto;
import org.telegram.telegrambots.meta.api.methods.send.SendAnimation;
import org.telegram.telegrambots.meta.api.methods.send.SendAudio;
import org.telegram.telegrambots.meta.api.methods.send.SendDocument;
import org.telegram.telegrambots.meta.api.methods.send.SendMediaBotMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMediaGroup;
import org.telegram.telegrambots.meta.api.methods.send.SendPaidMedia;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.methods.send.SendSticker;
import org.telegram.telegrambots.meta.api.methods.send.SendVideo;
import org.telegram.telegrambots.meta.api.methods.send.SendVideoNote;
import org.telegram.telegrambots.meta.api.methods.send.SendVoice;
import org.telegram.telegrambots.meta.api.methods.stickers.AddStickerToSet;
import org.telegram.telegrambots.meta.api.methods.stickers.CreateNewStickerSet;
import org.telegram.telegrambots.meta.api.methods.stickers.ReplaceStickerInSet;
import org.telegram.telegrambots.meta.api.methods.stickers.SetStickerSetThumbnail;
import org.telegram.telegrambots.meta.api.methods.stickers.UploadStickerFile;
import org.telegram.telegrambots.meta.api.methods.updates.SetWebhook;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageMedia;
import org.telegram.telegrambots.meta.api.objects.File;
import org.telegram.telegrambots.meta.api.objects.media.InputMedia;
import org.telegram.telegrambots.meta.api.objects.media.InputMediaAnimation;
import org.telegram.telegrambots.meta.api.objects.media.InputMediaAudio;
import org.telegram.telegrambots.meta.api.objects.media.InputMediaDocument;
import org.telegram.telegrambots.meta.api.objects.media.InputMediaVideo;
import org.telegram.telegrambots.meta.api.objects.media.paid.InputPaidMedia;
import org.telegram.telegrambots.meta.api.objects.media.paid.InputPaidMediaVideo;
import org.telegram.telegrambots.meta.api.objects.message.Message;
import org.telegram.telegrambots.meta.api.objects.photo.input.InputProfilePhotoAnimated;
import org.telegram.telegrambots.meta.api.objects.photo.input.InputProfilePhotoStatic;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.exceptions.TelegramApiRequestException;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;

/**
 * @author Valeriy Kucherenko
 * @since 05.09.2024
 */
public class JettyTelegramClient extends AbstractTelegramClient {
    private final HttpClient client;
    private final String botToken;
    private final TelegramUrl telegramUrl;
    private final ObjectMapper objectMapper;

    public JettyTelegramClient(@NonNull ObjectMapper objectMapper, @NonNull HttpClient client,
                               @NonNull String botToken, @NonNull TelegramUrl telegramUrl) {
        this.objectMapper = objectMapper;
        this.client = client;
        this.botToken = botToken;
        this.telegramUrl = telegramUrl;
    }

    public JettyTelegramClient(HttpClient client, String botToken, TelegramUrl telegramUrl) {
        this(new ObjectMapper(), client, botToken, telegramUrl);
    }

    public JettyTelegramClient(HttpClient client, String botToken) {
        this(client, botToken, TelegramUrl.DEFAULT_URL);
    }

    public JettyTelegramClient(String botToken, TelegramUrl telegramUrl) {
        this(createHttpClient(), botToken, telegramUrl);
    }

    public JettyTelegramClient(String botToken) {
        this(createHttpClient(), botToken);
    }

    @Override
    public <T extends Serializable, Method extends BotApiMethod<T>> CompletableFuture<T> executeAsync(Method method) throws TelegramApiException {
        if (method == null) {
            throw new TelegramApiException("Parameter method can not be null");
        }
        method.validate();

        try {
            URI url = buildUrl(method.getMethod());
            String body = objectMapper.writeValueAsString(method);

            Request request = client.POST(url)
                    .headers(httpFields -> {
                        httpFields.add("charset", StandardCharsets.UTF_8.name());
                        httpFields.add("content-type", "application/json");
                    })
                    .body(new StringRequestContent("application/json", body));

            return sendRequest(method, request);
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
                    .addPart(SendDocument.BUSINESS_CONNECTION_ID_FIELD, sendDocument.getBusinessConnectionId())
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
                .addPart(SendPhoto.BUSINESS_CONNECTION_ID_FIELD, sendPhoto.getBusinessConnectionId())
                .addPart(SendPhoto.SHOW_CAPTION_ABOVE_MEDIA_FIELD, sendPhoto.getShowCaptionAboveMedia())
                .addJsonPart(SendPhoto.CAPTION_ENTITIES_FIELD, sendPhoto.getCaptionEntities()));
    }

    @Override
    public CompletableFuture<Boolean> executeAsync(SetBusinessAccountProfilePhoto setBusinessAccountProfilePhoto) {
        try {
            assertParamNotNull(setBusinessAccountProfilePhoto, "setBusinessAccountProfilePhoto");

            setBusinessAccountProfilePhoto.validate();

            URI url = buildUrl(setBusinessAccountProfilePhoto.getMethod());

            JettyMultipartBuilder builder = new JettyMultipartBuilder(objectMapper);

            builder.addPart(SetBusinessAccountProfilePhoto.BUSINESS_CONNECTION_ID_FIELD, setBusinessAccountProfilePhoto.getBusinessConnectionId())
                    .addPart(SetBusinessAccountProfilePhoto.IS_PUBLIC_FIELD, setBusinessAccountProfilePhoto.getIsPublic());

            if (InputProfilePhotoStatic.TYPE.equals(setBusinessAccountProfilePhoto.getPhoto().getType())) {
                InputProfilePhotoStatic photo = (InputProfilePhotoStatic) setBusinessAccountProfilePhoto.getPhoto();
                builder.addJsonPart(SetBusinessAccountProfilePhoto.PHOTO_FIELD, photo)
                        .addInputFile(InputProfilePhotoStatic.PHOTO_FIELD, photo.getPhoto(), false);
            } else if (InputProfilePhotoAnimated.TYPE.equals(setBusinessAccountProfilePhoto.getPhoto().getType())) {
                InputProfilePhotoAnimated photo = (InputProfilePhotoAnimated) setBusinessAccountProfilePhoto.getPhoto();
                builder.addJsonPart(SetBusinessAccountProfilePhoto.PHOTO_FIELD, photo)
                        .addInputFile(InputProfilePhotoAnimated.ANIMATION_FIELD, photo.getAnimation(), false);
            }

            Request httpPost = client.POST(url).body(builder.build());

            return sendRequest(setBusinessAccountProfilePhoto, httpPost);
        } catch (TelegramApiException e) {
            return CompletableFuture.failedFuture(e);
        } catch (IOException e) {
            return CompletableFuture.failedFuture(new TelegramApiException("Unable to execute " + setBusinessAccountProfilePhoto.getMethod(), e));
        }
    }

    @Override
    public CompletableFuture<Boolean> executeAsync(SetWebhook setWebhook) {
        try {
            assertParamNotNull(setWebhook, "setWebhook");

            setWebhook.validate();

            URI url = buildUrl(setWebhook.getMethod());

            JettyMultipartBuilder builder = new JettyMultipartBuilder(objectMapper);

            builder.addPart(SetWebhook.URL_FIELD, setWebhook.getUrl())
                    .addPart(SetWebhook.MAX_CONNECTIONS_FIELD, setWebhook.getMaxConnections())
                    .addJsonPart(SetWebhook.ALLOWED_UPDATES_FIELD, setWebhook.getAllowedUpdates())
                    .addPart(SetWebhook.IP_ADDRESS_FIELD, setWebhook.getIpAddress())
                    .addPart(SetWebhook.DROP_PENDING_UPDATES_FIELD, setWebhook.getDropPendingUpdates())
                    .addPart(SetWebhook.SECRET_TOKEN_FIELD, setWebhook.getSecretToken());

            builder.addInputFile(SetWebhook.CERTIFICATE_FIELD, setWebhook.getCertificate(), true);

            Request httpPost = client.POST(url).body(builder.build());

            return sendRequest(setWebhook, httpPost);
        } catch (TelegramApiException e) {
            return CompletableFuture.failedFuture(e);
        } catch (IOException e) {
            return CompletableFuture.failedFuture(new TelegramApiException("Unable to execute " + setWebhook.getMethod(), e));
        }
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
                    .addPart(SendVideo.BUSINESS_CONNECTION_ID_FIELD, sendVideo.getBusinessConnectionId())
                    .addPart(SendVideo.SHOW_CAPTION_ABOVE_MEDIA_FIELD, sendVideo.getShowCaptionAboveMedia())
                    .addPart(SendVideo.START_TIMESTAMP_FIELD, sendVideo.getStartTimestamp())
                    .addJsonPart(SendVideo.CAPTION_ENTITIES_FIELD, sendVideo.getCaptionEntities());

            if (sendVideo.getThumbnail() != null) {
                builder.addInputFile(SendVideo.THUMBNAIL_FIELD, sendVideo.getThumbnail(), false);
                builder.addPart(SendVideo.THUMBNAIL_FIELD, sendVideo.getThumbnail().getAttachName());
            }
            if (sendVideo.getCover() != null) {
                builder.addInputFile(SendVideo.COVER_FIELD, sendVideo.getCover(), false);
                builder.addPart(SendVideo.COVER_FIELD, sendVideo.getCover().getAttachName());
            }
        });
    }

    @Override
    public CompletableFuture<Message> executeAsync(SendVideoNote sendVideoNote) {
        return executeMediaMethod(sendVideoNote, builder -> {
            builder
                    .addPart(SendVideoNote.DURATION_FIELD, sendVideoNote.getDuration())
                    .addPart(SendVideoNote.LENGTH_FIELD, sendVideoNote.getLength())
                    .addPart(SendVideoNote.BUSINESS_CONNECTION_ID_FIELD, sendVideoNote.getBusinessConnectionId());

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
                builder -> builder
                        .addPart(SendSticker.EMOJI_FIELD, sendSticker.getEmoji())
                        .addPart(SendSticker.BUSINESS_CONNECTION_ID_FIELD, sendSticker.getBusinessConnectionId())
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
                    .addPart(SendAudio.BUSINESS_CONNECTION_ID_FIELD, sendAudio.getBusinessConnectionId())
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
                .addPart(SendVoice.BUSINESS_CONNECTION_ID_FIELD, sendVoice.getBusinessConnectionId())
                .addPart(SendVoice.SHOW_CAPTION_ABOVE_MEDIA_FIELD, sendVoice.getShowCaptionAboveMedia())
                .addJsonPart(SendVoice.CAPTION_ENTITIES_FIELD, sendVoice.getCaptionEntities()));
    }

    @Override
    public CompletableFuture<List<Message>> executeAsync(SendPaidMedia sendPaidMedia) {
        try {
            assertParamNotNull(sendPaidMedia, "sendPaidMedia");
            sendPaidMedia.validate();

            URI url = buildUrl(sendPaidMedia.getMethod());

            JettyMultipartBuilder builder = new JettyMultipartBuilder(objectMapper);

            addPaidInputData(builder, SendPaidMedia.MEDIA_FIELD, sendPaidMedia.getMedia());

            builder.addPart(SendPaidMedia.CHAT_ID_FIELD, sendPaidMedia.getChatId())
                    .addPart(SendPaidMedia.STAR_COUNT_FIELD, sendPaidMedia.getStarCount())
                    .addPart(SendPaidMedia.CAPTION_FIELD, sendPaidMedia.getCaption())
                    .addPart(SendPaidMedia.PARSE_MODE_FIELD, sendPaidMedia.getParseMode())
                    .addPart(SendPaidMedia.SHOW_CAPTION_ABOVE_MEDIA_FIELD, sendPaidMedia.getShowCaptionAboveMedia())
                    .addPart(SendPaidMedia.DISABLE_NOTIFICATION_FIELD, sendPaidMedia.getDisableNotification())
                    .addPart(SendPaidMedia.PROTECT_CONTENT_FIELD, sendPaidMedia.getProtectContent())
                    .addPart(SendPaidMedia.BUSINESS_CONNECTION_ID_FIELD, sendPaidMedia.getBusinessConnectionId())
                    .addPart(SendPaidMedia.PAYLOAD_FIELD, sendPaidMedia.getPayload())
                    .addPart(SendPaidMedia.ALLOW_PAID_BROADCAST_FIELD, sendPaidMedia.getAllowPaidBroadcast())
                    .addJsonPart(SendPaidMedia.CAPTION_ENTITIES_FIELD, sendPaidMedia.getCaptionEntities())
                    .addJsonPart(SendPaidMedia.REPLY_MARKUP_FIELD, sendPaidMedia.getReplyMarkup())
                    .addJsonPart(SendPaidMedia.REPLY_PARAMETERS_FIELD, sendPaidMedia.getReplyParameters());


            Request httpPost = client.POST(url).body(builder.build());

            //For some reason java is having problem with casting ArrayList to List here
            return sendRequest(sendPaidMedia, httpPost).thenApply(list -> list);
        } catch (TelegramApiException e) {
            return CompletableFuture.failedFuture(e);
        } catch (IOException e) {
            return CompletableFuture.failedFuture(new TelegramApiException("Unable to execute " + sendPaidMedia.getMethod(), e));
        }
    }

    @Override
    public CompletableFuture<List<Message>> executeAsync(SendMediaGroup sendMediaGroup) {
        try {
            assertParamNotNull(sendMediaGroup, "sendMediaGroup");
            sendMediaGroup.validate();

            URI url = buildUrl(sendMediaGroup.getMethod());

            JettyMultipartBuilder builder = new JettyMultipartBuilder(objectMapper);

            addInputData(builder, SendMediaGroup.MEDIA_FIELD, sendMediaGroup.getMedias());

            builder.addPart(SendMediaGroup.CHAT_ID_FIELD, sendMediaGroup.getChatId())
                    .addPart(SendMediaGroup.DISABLE_NOTIFICATION_FIELD, sendMediaGroup.getDisableNotification())
                    .addPart(SendMediaGroup.REPLY_TO_MESSAGE_ID_FIELD, sendMediaGroup.getReplyToMessageId())
                    .addPart(SendMediaGroup.MESSAGE_THREAD_ID_FIELD, sendMediaGroup.getMessageThreadId())
                    .addPart(SendMediaGroup.ALLOW_SENDING_WITHOUT_REPLY_FIELD, sendMediaGroup.getAllowSendingWithoutReply())
                    .addPart(SendMediaGroup.PROTECT_CONTENT_FIELD, sendMediaGroup.getProtectContent())
                    .addPart(SendMediaGroup.BUSINESS_CONNECTION_ID_FIELD, sendMediaGroup.getBusinessConnectionId())
                    .addPart(SendMediaGroup.MESSAGE_EFFECT_ID_FIELD, sendMediaGroup.getMessageEffectId())
                    .addPart(SendMediaGroup.ALLOW_PAID_BROADCAST_FIELD, sendMediaGroup.getAllowSendingWithoutReply())
                    .addPart(SendMediaGroup.DIRECT_MESSAGES_TOPIC_ID_FIELD, sendMediaGroup.getDirectMessagesTopicId())
                    .addJsonPart(SendMediaGroup.REPLY_MARKUP_FIELD, sendMediaGroup.getReplyMarkup())
                    .addJsonPart(SendMediaGroup.REPLY_PARAMETERS_FIELD, sendMediaGroup.getReplyParameters());


            Request httpPost = client.POST(url).body(builder.build());

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
                            .addPart(SendAnimation.PARSE_MODE_FIELD, sendAnimation.getParseMode())
                            .addPart(SendAnimation.HAS_SPOILER_FIELD, sendAnimation.getHasSpoiler())
                            .addPart(SendAnimation.BUSINESS_CONNECTION_ID_FIELD, sendAnimation.getBusinessConnectionId())
                            .addPart(SendAnimation.SHOW_CAPTION_ABOVE_MEDIA_FIELD, sendAnimation.getShowCaptionAboveMedia())
                            .addJsonPart(SendAnimation.REPLY_MARKUP_FIELD, sendAnimation.getReplyMarkup());

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

            URI url = buildUrl(setChatPhoto.getMethod());

            JettyMultipartBuilder builder = new JettyMultipartBuilder(objectMapper);

            builder.addPart(SetChatPhoto.CHATID_FIELD, setChatPhoto.getChatId());

            builder.addInputFile(SetChatPhoto.PHOTO_FIELD, setChatPhoto.getPhoto(), false);

            Request httpPost = client.POST(url).body(builder.build());
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

            URI url = buildUrl(addStickerToSet.getMethod());

            JettyMultipartBuilder builder = new JettyMultipartBuilder(objectMapper);

            builder.addPart(AddStickerToSet.USERID_FIELD, addStickerToSet.getUserId())
                    .addPart(AddStickerToSet.NAME_FIELD, addStickerToSet.getName());

            builder.addInputStickers(AddStickerToSet.STICKER_FIELD, Collections.singletonList(addStickerToSet.getSticker()));

            Request httpPost = client.POST(url).body(builder.build());
            return sendRequest(addStickerToSet, httpPost);
        } catch (TelegramApiException e) {
            return CompletableFuture.failedFuture(e);
        } catch (IOException e) {
            return CompletableFuture.failedFuture(new TelegramApiException("Unable to execute " + addStickerToSet.getMethod(), e));
        }
    }

    @Override
    public CompletableFuture<Boolean> executeAsync(ReplaceStickerInSet replaceStickerInSet) {
        try {
            assertParamNotNull(replaceStickerInSet, "replaceStickerInSet");
            replaceStickerInSet.validate();

            URI url = buildUrl(replaceStickerInSet.getMethod());

            JettyMultipartBuilder builder = new JettyMultipartBuilder(objectMapper);

            builder.addPart(ReplaceStickerInSet.USERID_FIELD, replaceStickerInSet.getUserId())
                    .addPart(ReplaceStickerInSet.OLD_STICKER_FIELD, replaceStickerInSet.getOldSticker())
                    .addPart(ReplaceStickerInSet.NAME_FIELD, replaceStickerInSet.getName());

            builder.addInputStickers(ReplaceStickerInSet.STICKER_FIELD, Collections.singletonList(replaceStickerInSet.getSticker()));

            Request httpPost = client.POST(url).body(builder.build());
            return sendRequest(replaceStickerInSet, httpPost);
        } catch (TelegramApiException e) {
            return CompletableFuture.failedFuture(e);
        } catch (IOException e) {
            return CompletableFuture.failedFuture(new TelegramApiException("Unable to execute " + replaceStickerInSet.getMethod(), e));
        }
    }

    @Override
    public CompletableFuture<Boolean> executeAsync(SetStickerSetThumbnail setStickerSetThumbnail) {
        try {
            assertParamNotNull(setStickerSetThumbnail, "setStickerSetThumbail");
            setStickerSetThumbnail.validate();

            URI url = buildUrl(setStickerSetThumbnail.getMethod());

            JettyMultipartBuilder builder = new JettyMultipartBuilder(objectMapper);

            builder.addPart(SetStickerSetThumbnail.USER_ID_FIELD, setStickerSetThumbnail.getUserId())
                    .addPart(SetStickerSetThumbnail.NAME_FIELD, setStickerSetThumbnail.getName())
                    .addPart(SetStickerSetThumbnail.FORMAT_FIELD, setStickerSetThumbnail.getFormat())
                    .addPart(SetStickerSetThumbnail.THUMBNAIL_FIELD, setStickerSetThumbnail.getThumbnail());

            Request httpPost = client.POST(url).body(builder.build());
            return sendRequest(setStickerSetThumbnail, httpPost);
        } catch (TelegramApiException e) {
            return CompletableFuture.failedFuture(e);
        }
    }

    @Override
    public CompletableFuture<Boolean> executeAsync(CreateNewStickerSet createNewStickerSet) {
        try {
            assertParamNotNull(createNewStickerSet, "createNewStickerSet");
            createNewStickerSet.validate();

            URI url = buildUrl(createNewStickerSet.getMethod());

            JettyMultipartBuilder builder = new JettyMultipartBuilder(objectMapper);

            builder.addPart(CreateNewStickerSet.USER_ID_FIELD, createNewStickerSet.getUserId())
                    .addPart(CreateNewStickerSet.NAME_FIELD, createNewStickerSet.getName())
                    .addPart(CreateNewStickerSet.TITLE_FIELD, createNewStickerSet.getTitle())
                    .addPart(CreateNewStickerSet.STICKER_TYPE_FIELD, createNewStickerSet.getStickerType())
                    .addPart(CreateNewStickerSet.NEEDS_REPAINTING_FIELD, createNewStickerSet.getNeedsRepainting())
                    .addInputStickers(CreateNewStickerSet.STICKERS_FIELD, createNewStickerSet.getStickers());

            Request httpPost = client.POST(url).body(builder.build());
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

            URI url = buildUrl(uploadStickerFile.getMethod());

            JettyMultipartBuilder builder = new JettyMultipartBuilder(objectMapper);

            builder
                    .addPart(UploadStickerFile.USERID_FIELD, uploadStickerFile.getUserId())
                    .addPart(UploadStickerFile.STICKER_FORMAT_FIELD, uploadStickerFile.getStickerFormat())
                    .addInputFile(UploadStickerFile.STICKER_FIELD, uploadStickerFile.getSticker(), true);

            Request httpPost = client.POST(url).body(builder.build());
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

            URI url = buildUrl(editMessageMedia.getMethod());

            JettyMultipartBuilder builder = new JettyMultipartBuilder(objectMapper);

            builder.addPart(EditMessageMedia.CHAT_ID_FIELD, editMessageMedia.getChatId())
                    .addPart(EditMessageMedia.MESSAGE_ID_FIELD, editMessageMedia.getMessageId())
                    .addPart(EditMessageMedia.INLINE_MESSAGE_ID_FIELD, editMessageMedia.getInlineMessageId())
                    .addPart(EditMessageMedia.BUSINESS_CONNECTION_ID_FIELD, editMessageMedia.getBusinessConnectionId())
                    .addJsonPart(EditMessageMedia.REPLY_MARKUP_FIELD, editMessageMedia.getReplyMarkup());

            addInputData(builder, EditMessageMedia.MEDIA_FIELD, editMessageMedia.getMedia(), true);

            Request httpPost = client.POST(url).body(builder.build());
            return sendRequest(editMessageMedia, httpPost);
        } catch (TelegramApiException e) {
            return CompletableFuture.failedFuture(e);
        } catch (IOException e) {
            return CompletableFuture.failedFuture(new TelegramApiException("Unable to execute " + editMessageMedia.getMethod(), e));
        }
    }

    @Override
    public CompletableFuture<java.io.File> downloadFileAsync(File file) {
        return downloadFileAsStreamAsync(file).thenApply(stream -> {
            try {
                java.io.File outputFile = getTempFile();
                FileUtils.copyInputStreamToFile(stream, outputFile);
                return outputFile;
            } catch (IOException e) {
                throw new RuntimeException("Unable to write file to disk", e);
            } catch (TelegramApiException e) {
                throw new RuntimeException(e);
            }
        });
    }

    @Override
    public CompletableFuture<InputStream> downloadFileAsStreamAsync(File file) {
        try {
            if (file == null) {
                throw new TelegramApiException("Parameter file can not be null");
            }

            InputStreamResponseListener listener = new InputStreamResponseListener();
            client.newRequest(file.getFileUrl(botToken)).send(listener);
            return CompletableFuture.completedFuture(listener.getInputStream());
        } catch (TelegramApiException e) {
            return CompletableFuture.failedFuture(e);
        }
    }

    private <T extends Serializable, Method extends PartialBotApiMethod<T>> CompletableFuture<T> sendRequest(
            Method method, Request request
    ) {
        var future = new CompletableResponseListener(request).send();
        return future.thenApply(response -> {
            try {
                return method.deserializeResponse(response.getContentAsString());
            } catch (TelegramApiRequestException e) {
                throw new CompletionException(e);
            }
        });
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
            ThrowingConsumer<JettyMultipartBuilder, IOException> setup
    ) {
        try {
            assertParamNotNull(method, "method");
            assertParamNotNull(setup, "setup");

            method.validate();

            URI url = buildUrl(method.getMethod());

            JettyMultipartBuilder builder = new JettyMultipartBuilder(objectMapper);

            builder.addInputFile(method.getFileField(), method.getFile(), true);

            builder.addPart(SendMediaBotMethod.CHAT_ID_FIELD, method.getChatId())
                    .addPart(SendMediaBotMethod.MESSAGE_THREAD_ID_FIELD, method.getMessageThreadId())
                    .addPart(SendMediaBotMethod.REPLY_TO_MESSAGE_ID_FIELD, method.getReplyToMessageId())
                    .addPart(SendMediaBotMethod.DISABLE_NOTIFICATION_FIELD, method.getDisableNotification())
                    .addPart(SendMediaBotMethod.PROTECT_CONTENT_FIELD, method.getProtectContent())
                    .addPart(SendMediaBotMethod.ALLOW_SENDING_WITHOUT_REPLY_FIELD, method.getAllowSendingWithoutReply())
                    .addPart(SendMediaBotMethod.MESSAGE_EFFECT_ID_FIELD, method.getMessageEffectId())
                    .addPart(SendMediaBotMethod.DIRECT_MESSAGES_TOPIC_ID_FIELD, method.getDirectMessagesTopicId())
                    .addPart(SendMediaBotMethod.SUGGESTED_POST_PARAMETERS_FIELD, method.getSuggestedPostParameters())
                    .addJsonPart(SendMediaBotMethod.REPLY_PARAMETERS_FIELD, method.getReplyParameters())
                    .addJsonPart(SendMediaBotMethod.REPLY_MARKUP_FIELD, method.getReplyMarkup());

            setup.accept(builder);

            Request httpPost = client.POST(url).body(builder.build());

            return sendRequest(method, httpPost);
        } catch (TelegramApiException e) {
            return CompletableFuture.failedFuture(e);
        } catch (IOException e) {
            return CompletableFuture.failedFuture(new TelegramApiException("Unable to execute " + method.getMethod(), e));
        }
    }

    @SneakyThrows(URISyntaxException.class)
    private URI buildUrl(String methodPath) {
        String path = "/bot" + botToken;
        if (telegramUrl.isTestServer()) {
            path += "/test";
        }
        path += "/" + methodPath;

        URI uri = new URI(telegramUrl.getSchema(), null, telegramUrl.getHost(), telegramUrl.getPort(),
                path, null, null);
        return uri;
    }

    private void addInputData(JettyMultipartBuilder builder, String mediaField, InputMedia media, boolean addField) throws IOException {
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
            if (video.getCover() != null) {
                builder.addInputFile(InputMediaVideo.COVER_FIELD, video.getCover(), false);
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

    private void addInputData(JettyMultipartBuilder builder, String mediaField, InputPaidMedia media, boolean addField) throws IOException {
        if (media.isNewMedia()) {
            builder.addMedia(media);
        }

        if (media instanceof InputPaidMediaVideo video) {
            if (video.getThumbnail() != null) {
                builder.addInputFile(InputPaidMediaVideo.THUMBNAIL_FIELD, video.getThumbnail(), false);
            }
            if (video.getCover() != null) {
                builder.addInputFile(InputPaidMediaVideo.COVER_FIELD, video.getCover(), false);
            }
        }

        if (addField) {
            builder.addJsonPart(mediaField, media);
        }
    }

    private void addPaidInputData(JettyMultipartBuilder builder, String mediaField, List<InputPaidMedia> media) throws IOException {
        for (InputPaidMedia inputMedia : media) {
            addInputData(builder, null, inputMedia, false);
        }

        builder.addJsonPart(mediaField, media);
    }

    private void addInputData(JettyMultipartBuilder builder, String mediaField, List<InputMedia> media) throws IOException {
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

    private java.io.File getTempFile() throws TelegramApiException {
        try {
            return java.io.File.createTempFile(Long.toString(System.currentTimeMillis()), ".tmp");
        } catch (IOException e) {
            throw new TelegramApiException("Error downloading file", e);
        }
    }

    @SneakyThrows
    private static HttpClient createHttpClient() {
        HttpClient httpClient = new HttpClient();
        httpClient.start();
        return httpClient;
    }
}
