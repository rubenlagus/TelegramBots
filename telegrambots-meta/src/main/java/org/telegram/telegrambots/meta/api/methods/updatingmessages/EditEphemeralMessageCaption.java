package org.telegram.telegrambots.meta.api.methods.updatingmessages;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.Singular;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import lombok.experimental.Tolerate;
import lombok.extern.jackson.Jacksonized;
import org.telegram.telegrambots.meta.api.methods.botapimethods.BotApiMethodBoolean;
import org.telegram.telegrambots.meta.api.objects.MessageEntity;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.exceptions.TelegramApiValidationException;
import org.telegram.telegrambots.meta.util.Validations;

import java.util.List;

/**
 * @author Ruben Bermudez
 * @version 10.3
 * Use this method to edit the caption of an ephemeral message.
 * Note that it is not guaranteed that the user will receive the message edit event,
 * especially if they are offline. On success, True is returned.
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
public class EditEphemeralMessageCaption extends BotApiMethodBoolean {
    public static final String PATH = "editEphemeralMessageCaption";

    private static final String CHAT_ID_FIELD = "chat_id";
    private static final String RECEIVER_USER_ID_FIELD = "receiver_user_id";
    private static final String EPHEMERAL_MESSAGE_ID_FIELD = "ephemeral_message_id";
    private static final String CAPTION_FIELD = "caption";
    private static final String PARSE_MODE_FIELD = "parse_mode";
    private static final String CAPTION_ENTITIES_FIELD = "caption_entities";
    private static final String SHOW_CAPTION_ABOVE_MEDIA_FIELD = "show_caption_above_media";
    private static final String REPLY_MARKUP_FIELD = "reply_markup";

    /**
     * Unique identifier for the target chat or username of the target supergroup
     * (in the format @supergroupusername)
     */
    @JsonProperty(CHAT_ID_FIELD)
    @NonNull
    private String chatId;
    /**
     * Identifier of the user who received the message
     */
    @JsonProperty(RECEIVER_USER_ID_FIELD)
    @NonNull
    private Long receiverUserId;
    /**
     * Identifier of the ephemeral message to edit
     */
    @JsonProperty(EPHEMERAL_MESSAGE_ID_FIELD)
    @NonNull
    private Integer ephemeralMessageId;
    /**
     * Optional. New caption of the message, 0-1024 characters after entities parsing
     */
    @JsonProperty(CAPTION_FIELD)
    private String caption;
    /**
     * Optional. Mode for parsing entities in the message caption
     */
    @JsonProperty(PARSE_MODE_FIELD)
    private String parseMode;
    /**
     * Optional. A JSON-serialized list of special entities that appear in the caption,
     * which can be specified instead of parseMode
     */
    @JsonProperty(CAPTION_ENTITIES_FIELD)
    @Singular
    private List<MessageEntity> captionEntities;
    /**
     * Optional. Pass True if the caption must be shown above the message media.
     *
     * @apiNote Supported only for animation, photo and video messages.
     */
    @JsonProperty(SHOW_CAPTION_ABOVE_MEDIA_FIELD)
    private Boolean showCaptionAboveMedia;
    /**
     * Optional. A JSON-serialized object for an inline keyboard
     */
    @JsonProperty(REPLY_MARKUP_FIELD)
    private InlineKeyboardMarkup replyMarkup;

    @Tolerate
    public void setChatId(@NonNull Long chatId) {
        this.chatId = chatId.toString();
    }

    @Override
    public String getMethod() {
        return PATH;
    }

    @Override
    public void validate() throws TelegramApiValidationException {
        Validations.requiredChatId(chatId, this);
        if (parseMode != null && (captionEntities != null && !captionEntities.isEmpty())) {
            throw new TelegramApiValidationException("Parse mode can't be enabled if Entities are provided", this);
        }
        if (replyMarkup != null) {
            replyMarkup.validate();
        }
    }

    public static abstract class EditEphemeralMessageCaptionBuilder<C extends EditEphemeralMessageCaption, B extends EditEphemeralMessageCaptionBuilder<C, B>> extends BotApiMethodBooleanBuilder<C, B> {
        @Tolerate
        public EditEphemeralMessageCaptionBuilder<C, B> chatId(@NonNull Long chatId) {
            this.chatId = chatId.toString();
            return this;
        }
    }
}
