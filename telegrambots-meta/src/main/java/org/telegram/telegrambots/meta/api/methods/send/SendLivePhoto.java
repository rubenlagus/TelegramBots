package org.telegram.telegrambots.meta.api.methods.send;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import lombok.experimental.Tolerate;
import lombok.extern.jackson.Jacksonized;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.MessageEntity;
import org.telegram.telegrambots.meta.api.objects.ReplyParameters;
import org.telegram.telegrambots.meta.api.objects.message.Message;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;
import org.telegram.telegrambots.meta.api.objects.suggestedpost.SuggestedPostParameters;
import org.telegram.telegrambots.meta.exceptions.TelegramApiRequestException;
import org.telegram.telegrambots.meta.exceptions.TelegramApiValidationException;
import org.telegram.telegrambots.meta.util.Validations;

import java.util.List;

/**
 * @author Ruben Bermudez
 * @version 10.0
 * Use this method to send live photos. On success, the sent Message is returned.
 */
@EqualsAndHashCode(callSuper = false)
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Jacksonized
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SendLivePhoto extends SendMediaBotMethod<Message> {
    public static final String PATH = "sendLivePhoto";

    public static final String LIVE_PHOTO_FIELD = "live_photo";
    public static final String PHOTO_FIELD = "photo";
    public static final String CAPTION_FIELD = "caption";
    public static final String PARSE_MODE_FIELD = "parse_mode";
    public static final String CAPTION_ENTITIES_FIELD = "caption_entities";
    public static final String SHOW_CAPTION_ABOVE_MEDIA_FIELD = "show_caption_above_media";
    public static final String HAS_SPOILER_FIELD = "has_spoiler";
    public static final String BUSINESS_CONNECTION_ID_FIELD = "business_connection_id";

    @JsonProperty(BUSINESS_CONNECTION_ID_FIELD)
    private String businessConnectionId;
    @JsonProperty(CHAT_ID_FIELD)
    @NonNull
    private String chatId;
    @JsonProperty(MESSAGE_THREAD_ID_FIELD)
    private Integer messageThreadId;
    @JsonProperty(DIRECT_MESSAGES_TOPIC_ID_FIELD)
    private Integer directMessagesTopicId;
    @JsonProperty(LIVE_PHOTO_FIELD)
    @NonNull
    private InputFile livePhoto;
    @JsonProperty(PHOTO_FIELD)
    @NonNull
    private InputFile photo;
    @JsonProperty(CAPTION_FIELD)
    private String caption;
    @JsonProperty(PARSE_MODE_FIELD)
    private String parseMode;
    @JsonProperty(CAPTION_ENTITIES_FIELD)
    private List<MessageEntity> captionEntities;
    @JsonProperty(SHOW_CAPTION_ABOVE_MEDIA_FIELD)
    private Boolean showCaptionAboveMedia;
    @JsonProperty(HAS_SPOILER_FIELD)
    private Boolean hasSpoiler;
    @JsonProperty(DISABLE_NOTIFICATION_FIELD)
    private Boolean disableNotification;
    @JsonProperty(PROTECT_CONTENT_FIELD)
    private Boolean protectContent;
    @JsonProperty(ALLOW_PAID_BROADCAST_FIELD)
    private Boolean allowPaidBroadcast;
    @JsonProperty(MESSAGE_EFFECT_ID_FIELD)
    private String messageEffectId;
    @JsonProperty(SUGGESTED_POST_PARAMETERS_FIELD)
    private SuggestedPostParameters suggestedPostParameters;
    @JsonProperty(REPLY_PARAMETERS_FIELD)
    private ReplyParameters replyParameters;
    @JsonProperty(REPLY_MARKUP_FIELD)
    private ReplyKeyboard replyMarkup;

    @Tolerate
    public void setChatId(@NonNull Long chatId) {
        this.chatId = chatId.toString();
    }

    @Override
    public String getMethod() {
        return PATH;
    }

    @Override
    public InputFile getFile() {
        return livePhoto;
    }

    @Override
    public String getFileField() {
        return LIVE_PHOTO_FIELD;
    }

    @Override
    public Message deserializeResponse(String answer) throws TelegramApiRequestException {
        return deserializeResponse(answer, Message.class);
    }

    @Override
    public void validate() throws TelegramApiValidationException {
        Validations.requiredChatId(chatId, this);
        if (livePhoto == null) {
            throw new TelegramApiValidationException("LivePhoto can't be null", this);
        }
        if (photo == null) {
            throw new TelegramApiValidationException("Photo can't be null", this);
        }
        if (replyMarkup != null) {
            replyMarkup.validate();
        }
        if (replyParameters != null) {
            replyParameters.validate();
        }
    }

    public static abstract class SendLivePhotoBuilder<C extends SendLivePhoto, B extends SendLivePhotoBuilder<C, B>> extends SendMediaBotMethodBuilder<Message, C, B> {
        @Tolerate
        public SendLivePhotoBuilder<C, B> chatId(@NonNull Long chatId) {
            this.chatId = chatId.toString();
            return this;
        }
    }
}
