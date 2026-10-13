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
import org.telegram.telegrambots.meta.api.objects.LinkPreviewOptions;
import org.telegram.telegrambots.meta.api.objects.MessageEntity;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.richtext.InputRichMessage;
import org.telegram.telegrambots.meta.exceptions.TelegramApiValidationException;
import org.telegram.telegrambots.meta.util.Validations;

import java.util.List;

/**
 * @author Ruben Bermudez
 * @version 10.3
 * Use this method to edit an ephemeral text or rich message.
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
public class EditEphemeralMessageText extends BotApiMethodBoolean {
    public static final String PATH = "editEphemeralMessageText";

    private static final String CHAT_ID_FIELD = "chat_id";
    private static final String RECEIVER_USER_ID_FIELD = "receiver_user_id";
    private static final String EPHEMERAL_MESSAGE_ID_FIELD = "ephemeral_message_id";
    private static final String TEXT_FIELD = "text";
    private static final String RICH_MESSAGE_FIELD = "rich_message";
    private static final String PARSE_MODE_FIELD = "parse_mode";
    private static final String ENTITIES_FIELD = "entities";
    private static final String LINK_PREVIEW_OPTIONS_FIELD = "link_preview_options";
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
     * Optional. New text of the message, 1-4096 characters after entity parsing;
     * required if richMessage isn't specified
     */
    @JsonProperty(TEXT_FIELD)
    private String text;
    /**
     * Optional. New rich content of the message; required if text isn't specified
     */
    @JsonProperty(RICH_MESSAGE_FIELD)
    private InputRichMessage richMessage;
    /**
     * Optional. Mode for parsing entities in the message text
     */
    @JsonProperty(PARSE_MODE_FIELD)
    private String parseMode;
    /**
     * Optional. A JSON-serialized list of special entities that appear in message text,
     * which can be specified instead of parseMode
     */
    @JsonProperty(ENTITIES_FIELD)
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @Singular
    private List<MessageEntity> entities;
    /**
     * Optional. Link preview generation options for the message
     */
    @JsonProperty(LINK_PREVIEW_OPTIONS_FIELD)
    private LinkPreviewOptions linkPreviewOptions;
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
        if (text == null && richMessage == null) {
            throw new TelegramApiValidationException("One of Text or RichMessage parameter is required", this);
        }
        if (text != null && richMessage != null) {
            throw new TelegramApiValidationException("Only one of Text or RichMessage can be provided", this);
        }
        if (text != null && text.isEmpty()) {
            throw new TelegramApiValidationException("Text parameter can't be empty", this);
        }
        if (parseMode != null && (entities != null && !entities.isEmpty())) {
            throw new TelegramApiValidationException("Parse mode can't be enabled if Entities are provided", this);
        }
        if (richMessage != null) {
            richMessage.validate();
        }
        if (linkPreviewOptions != null) {
            linkPreviewOptions.validate();
        }
        if (replyMarkup != null) {
            replyMarkup.validate();
        }
    }

    public static abstract class EditEphemeralMessageTextBuilder<C extends EditEphemeralMessageText, B extends EditEphemeralMessageTextBuilder<C, B>> extends BotApiMethodBooleanBuilder<C, B> {
        @Tolerate
        public EditEphemeralMessageTextBuilder<C, B> chatId(@NonNull Long chatId) {
            this.chatId = chatId.toString();
            return this;
        }
    }
}
