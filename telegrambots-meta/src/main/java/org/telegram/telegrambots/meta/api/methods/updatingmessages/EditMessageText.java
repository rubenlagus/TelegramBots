package org.telegram.telegrambots.meta.api.methods.updatingmessages;

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
import org.telegram.telegrambots.meta.api.methods.ParseMode;
import org.telegram.telegrambots.meta.api.methods.botapimethods.BotApiMethodSerializable;
import org.telegram.telegrambots.meta.api.objects.LinkPreviewOptions;
import org.telegram.telegrambots.meta.api.objects.MessageEntity;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.exceptions.TelegramApiRequestException;
import org.telegram.telegrambots.meta.exceptions.TelegramApiValidationException;

import java.io.Serializable;
import java.util.List;

/**
 * @author Ruben Bermudez
 * @version 1.0
 * Use this method to edit text and game messages.
 * On success, if the edited message is not an inline message, the edited Message is returned, otherwise True is returned.
 * @apiNote Note that business messages that were not sent by the bot and do not contain an inline keyboard
 * can only be edited within 48 hours from the time they were sent.
 */
@SuppressWarnings("unused")
@EqualsAndHashCode(callSuper = false)
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Jacksonized
public class EditMessageText extends BotApiMethodSerializable {
    public static final String PATH = "editmessagetext";

    private static final String CHAT_ID_FIELD = "chat_id";
    private static final String MESSAGE_ID_FIELD = "message_id";
    private static final String INLINE_MESSAGE_ID_FIELD = "inline_message_id";
    private static final String TEXT_FIELD = "text";
    private static final String PARSE_MODE_FIELD = "parse_mode";
    private static final String DISABLE_WEB_PREVIEW_FIELD = "disable_web_page_preview";
    private static final String REPLY_MARKUP_FIELD = "reply_markup";
    private static final String ENTITIES_FIELD = "entities";
    private static final String LINK_PREVIEW_OPTIONS_FIELD = "link_preview_options";
    private static final String BUSINESS_CONNECTION_ID_FIELD = "business_connection_id";

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
     * New text of the message
     */
    @JsonProperty(TEXT_FIELD)
    @NonNull
    private String text;
    /**
     * Optional. Send Markdown or HTML, if you want Telegram apps to show bold, italic, fixed-width
     * text or inline URLs in your bot's message.
     */
    @JsonProperty(PARSE_MODE_FIELD)
    private String parseMode;
    /**
     * Optional.
     * Disables link previews for links in this message
     */
    @JsonProperty(DISABLE_WEB_PREVIEW_FIELD)
    private Boolean disableWebPagePreview;
    /**
     * Optional.
     * A JSON-serialized object for an inline keyboard.
     */
    @JsonProperty(REPLY_MARKUP_FIELD)
    private InlineKeyboardMarkup replyMarkup;
    /**
     * Optional.
     * List of special entities that appear in message text, which can be specified instead of parse_mode
     */
    @JsonProperty(ENTITIES_FIELD)
    private List<MessageEntity> entities;
    /**
     * Optional
     * Link preview generation options for the message
     */
    @JsonProperty(LINK_PREVIEW_OPTIONS_FIELD)
    private LinkPreviewOptions linkPreviewOptions;
    /**
     * Optional
     * Unique identifier of the business connection on behalf of which the message to be edited was sent
     */
    @JsonProperty(BUSINESS_CONNECTION_ID_FIELD)
    private String businessConnectionId;

    public void disableWebPagePreview() {
        disableWebPagePreview = true;
    }

    public void enableWebPagePreview() {
        disableWebPagePreview = null;
    }

    public void enableMarkdown(boolean enable) {
        if (enable) {
            this.parseMode = ParseMode.MARKDOWN;
        } else {
            this.parseMode = null;
        }
    }

    public void enableHtml(boolean enable) {
        if (enable) {
            this.parseMode = ParseMode.HTML;
        } else {
            this.parseMode = null;
        }
    }

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
        return deserializeResponseMessageOrBoolean(answer);
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
        if (text.isEmpty()) {
            throw new TelegramApiValidationException("Text parameter can't be empty", this);
        }
        if (parseMode != null && (entities != null && !entities.isEmpty()) ) {
            throw new TelegramApiValidationException("Parse mode can't be enabled if Entities are provided", this);
        }
        if (replyMarkup != null) {
            replyMarkup.validate();
        }
        if (linkPreviewOptions != null) {
            linkPreviewOptions.validate();
        }
    }

    public static abstract class EditMessageTextBuilder<C extends EditMessageText, B extends EditMessageTextBuilder<C, B>> extends BotApiMethodSerializableBuilder<C, B> {
        @Tolerate
        public EditMessageTextBuilder<C, B> chatId(Long chatId) {
            this.chatId = chatId == null ? null : chatId.toString();
            return this;
        }
    }
}
