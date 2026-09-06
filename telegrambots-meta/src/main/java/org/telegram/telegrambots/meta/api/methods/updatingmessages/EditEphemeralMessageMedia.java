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
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import lombok.experimental.Tolerate;
import lombok.extern.jackson.Jacksonized;
import org.telegram.telegrambots.meta.api.methods.botapimethods.PartialBotApiMethod;
import org.telegram.telegrambots.meta.api.objects.media.InputMedia;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.exceptions.TelegramApiRequestException;
import org.telegram.telegrambots.meta.exceptions.TelegramApiValidationException;
import org.telegram.telegrambots.meta.util.Validations;

/**
 * @author Ruben Bermudez
 * @version 10.3
 * Use this method to edit the media of an ephemeral message.
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
public class EditEphemeralMessageMedia extends PartialBotApiMethod<Boolean> {
    public static final String PATH = "editEphemeralMessageMedia";

    public static final String CHAT_ID_FIELD = "chat_id";
    public static final String RECEIVER_USER_ID_FIELD = "receiver_user_id";
    public static final String EPHEMERAL_MESSAGE_ID_FIELD = "ephemeral_message_id";
    public static final String MEDIA_FIELD = "media";
    public static final String REPLY_MARKUP_FIELD = "reply_markup";

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
     * A JSON-serialized object for the new media content of the message
     */
    @JsonProperty(MEDIA_FIELD)
    @NonNull
    private InputMedia media;
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
    public Boolean deserializeResponse(String answer) throws TelegramApiRequestException {
        return deserializeResponse(answer, Boolean.class);
    }

    @Override
    public void validate() throws TelegramApiValidationException {
        Validations.requiredChatId(chatId, this);
        media.validate();
        if (replyMarkup != null) {
            replyMarkup.validate();
        }
    }

    public static abstract class EditEphemeralMessageMediaBuilder<C extends EditEphemeralMessageMedia, B extends EditEphemeralMessageMediaBuilder<C, B>> extends PartialBotApiMethodBuilder<Boolean, C, B> {
        @Tolerate
        public EditEphemeralMessageMediaBuilder<C, B> chatId(@NonNull Long chatId) {
            this.chatId = chatId.toString();
            return this;
        }
    }
}
