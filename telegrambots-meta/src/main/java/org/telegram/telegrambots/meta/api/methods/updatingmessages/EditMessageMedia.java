package org.telegram.telegrambots.meta.api.methods.updatingmessages;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.type.TypeReference;
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
import org.telegram.telegrambots.meta.api.objects.ApiResponse;
import org.telegram.telegrambots.meta.api.objects.media.InputMedia;
import org.telegram.telegrambots.meta.api.objects.message.Message;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.exceptions.TelegramApiRequestException;
import org.telegram.telegrambots.meta.exceptions.TelegramApiValidationException;

import java.io.IOException;
import java.io.Serializable;

/**
 * @author Ruben Bermudez
 * @version 4.0.0
 * Use this method to edit animation, audio, document, photo, or video messages.
 * If a message is part of a message album, then it can be edited only to an audio for audio albums,
 * only to a document for document albums and to a photo or a video otherwise.
 * When an inline message is edited, a new file can't be uploaded; use a previously uploaded file via its file_id or
 * specify a URL.
 * On success, if the edited message is not an inline message, the edited Message is returned, otherwise True is returned.
 * @apiNote  Note that business messages that were not sent by the bot and do not contain an inline
 * keyboard can only be edited within 48 hours from the time they were sent.
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
public class EditMessageMedia extends PartialBotApiMethod<Serializable> {
    public static final String PATH = "editMessageMedia";

    public static final String CHAT_ID_FIELD = "chat_id";
    public static final String MESSAGE_ID_FIELD = "message_id";
    public static final String INLINE_MESSAGE_ID_FIELD = "inline_message_id";
    public static final String MEDIA_FIELD = "media";
    public static final String REPLY_MARKUP_FIELD = "reply_markup";
    public static final String BUSINESS_CONNECTION_ID_FIELD = "business_connection_id";

    /**
     * Required if inline_message_id is not specified. Unique identifier for the chat to send the
     * message to (Or username for channels)
     */
    @JsonProperty(CHAT_ID_FIELD)
    private String chatId;
    /**
     * Required if inline_message_id is not specified. Unique identifier of the sent message
     */
    @JsonProperty(MESSAGE_ID_FIELD)
    private Integer messageId;
    /**
     * Required if chat_id and message_id are not specified. Identifier of the inline message
     */
    @JsonProperty(INLINE_MESSAGE_ID_FIELD)
    private String inlineMessageId;
    /**
     * A JSON-serialized object for a new media content of the message
     */
    @NonNull
    @JsonProperty(MEDIA_FIELD)
    private InputMedia media;
    /**
     * Optional
     * Unique identifier of the business connection on behalf of which the message to be edited was sent
     */
    @JsonProperty(REPLY_MARKUP_FIELD)
    private InlineKeyboardMarkup replyMarkup;
    /**
     * Optional
     * Unique identifier of the business connection on behalf of which the message to be edited was sent
     */
    @JsonProperty(BUSINESS_CONNECTION_ID_FIELD)
    private String businessConnectionId;

    @Tolerate
    public void setChatId(Long chatId) {
        this.chatId = chatId == null ? null : chatId.toString();
    }

    @Override
    public String getMethod() {
        return PATH;
    }

    @Override
    public Serializable deserializeResponse(String answer) throws TelegramApiRequestException {
        try {
            ApiResponse<Message> result = OBJECT_MAPPER.readValue(answer,
                    new TypeReference<ApiResponse<Message>>(){});
            if (result.getOk()) {
                return result.getResult();
            } else {
                throw new TelegramApiRequestException("Error editing message text", result);
            }
        } catch (IOException e) {
            try {
                ApiResponse<Boolean> result = OBJECT_MAPPER.readValue(answer,
                        new TypeReference<ApiResponse<Boolean>>() {
                        });
                if (result.getOk()) {
                    return result.getResult();
                } else {
                    throw new TelegramApiRequestException("Error editing message text", result);
                }
            } catch (IOException e2) {
                throw new TelegramApiRequestException("Unable to deserialize response", e);
            }
        }
    }

    @Override
    public void validate() throws TelegramApiValidationException {
        if (inlineMessageId == null) {
            if (chatId == null || chatId.isEmpty()) {
                throw new TelegramApiValidationException("ChatId parameter can't be empty if inlineMessageId is not present", this);
            }
            if (messageId == null) {
                throw new TelegramApiValidationException("MessageId parameter can't be empty if inlineMessageId is not present", this);
            }
        } else {
            if (chatId != null) {
                throw new TelegramApiValidationException("ChatId parameter must be empty if inlineMessageId is provided", this);
            }
            if (messageId != null) {
                throw new TelegramApiValidationException("MessageId parameter must be empty if inlineMessageId is provided", this);
            }
        }

        media.validate();

        if (replyMarkup != null) {
            replyMarkup.validate();
        }
    }

    public static abstract class EditMessageMediaBuilder<C extends EditMessageMedia, B extends EditMessageMediaBuilder<C, B>> extends PartialBotApiMethodBuilder<Serializable, C, B> {
        @Tolerate
        public EditMessageMediaBuilder<C, B> chatId(Long chatId) {
            this.chatId = chatId == null ? null : chatId.toString();
            return this;
        }
    }
}
