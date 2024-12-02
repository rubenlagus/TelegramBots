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
import org.telegram.telegrambots.meta.api.methods.ParseMode;
import org.telegram.telegrambots.meta.api.methods.botapimethods.BotApiMethodMessage;
import org.telegram.telegrambots.meta.api.objects.LinkPreviewOptions;
import org.telegram.telegrambots.meta.api.objects.MessageEntity;
import org.telegram.telegrambots.meta.api.objects.ReplyParameters;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;
import org.telegram.telegrambots.meta.exceptions.TelegramApiValidationException;
import org.telegram.telegrambots.meta.util.Validations;

import java.util.List;

/**
 * @author Ruben Bermudez
 * @version 1.0
 * Use this method to send text messages. On success, the sent Message is returned.
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
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SendMessage extends BotApiMethodMessage {
    public static final String PATH = "sendmessage";

    private static final String CHAT_ID_FIELD = "chat_id";
    private static final String MESSAGE_THREAD_ID_FIELD = "message_thread_id";
    private static final String TEXT_FIELD = "text";
    private static final String PARSE_MODE_FIELD = "parse_mode";
    private static final String DISABLE_WEB_PAGE_PREVIEW_FIELD = "disable_web_page_preview";
    private static final String DISABLE_NOTIFICATION_FIELD = "disable_notification";
    private static final String REPLY_TO_MESSAGE_ID_FIELD = "reply_to_message_id";
    private static final String REPLY_MARKUP_FIELD = "reply_markup";
    private static final String ENTITIES_FIELD = "entities";
    private static final String ALLOW_SENDING_WITHOUT_REPLY_FIELD = "allow_sending_without_reply";
    private static final String PROTECT_CONTENT_FIELD = "protect_content";
    private static final String LINK_PREVIEW_OPTIONS_FIELD = "link_preview_options";
    private static final String REPLY_PARAMETERS_FIELD = "reply_parameters";
    private static final String BUSINESS_CONNECTION_ID_FIELD = "business_connection_id";
    private static final String MESSAGE_EFFECT_ID_FIELD = "message_effect_id";
    private static final String ALLOW_PAID_BROADCAST_FIELD = "allow_paid_broadcast";

    @JsonProperty(CHAT_ID_FIELD)
    @NonNull
    private String chatId; ///< Unique identifier for the chat to send the message to (Or username for channels)
    /**
     * Unique identifier for the target message thread (topic) of the forum;
     * for forum supergroups only
     */
    @JsonProperty(MESSAGE_THREAD_ID_FIELD)
    private Integer messageThreadId;
    @JsonProperty(TEXT_FIELD)
    @NonNull
    private String text; ///< Text of the message to be sent
    @JsonProperty(PARSE_MODE_FIELD)
    private String parseMode; ///< Optional. Send Markdown, if you want Telegram apps to show bold, italic and URL text in your bot's message.
    @JsonProperty(DISABLE_WEB_PAGE_PREVIEW_FIELD)
    private Boolean disableWebPagePreview; ///< Optional. Disables link previews for links in this message
    @JsonProperty(DISABLE_NOTIFICATION_FIELD)
    private Boolean disableNotification; ///< Optional. Sends the message silently. Users will receive a notification with no sound.
    @JsonProperty(REPLY_TO_MESSAGE_ID_FIELD)
    private Integer replyToMessageId; ///< Optional. If the message is a reply, ID of the original message
    /**
     * Optional
     * Additional interface options.
     * A JSON-serialized object for an inline keyboard, custom reply keyboard, instructions to remove a reply keyboard
     * or to force a reply from the user
     */
    @JsonProperty(REPLY_MARKUP_FIELD)
    private ReplyKeyboard replyMarkup;
    @JsonProperty(ENTITIES_FIELD)
    private List<MessageEntity> entities; ///< Optional. List of special entities that appear in message text, which can be specified instead of parse_mode
    @JsonProperty(ALLOW_SENDING_WITHOUT_REPLY_FIELD)
    private Boolean allowSendingWithoutReply; ///< Optional	Pass True, if the message should be sent even if the specified replied-to message is not found
    @JsonProperty(PROTECT_CONTENT_FIELD)
    private Boolean protectContent; ///< Optional. Protects the contents of sent messages from forwarding and saving
    /**
     * Optional
     * Link preview generation options for the message
     */
    @JsonProperty(LINK_PREVIEW_OPTIONS_FIELD)
    private LinkPreviewOptions linkPreviewOptions;
    /**
     * Optional
     * Description of the message to reply to
     */
    @JsonProperty(REPLY_PARAMETERS_FIELD)
    private ReplyParameters replyParameters;
    /**
     * Optional.
     * Unique identifier of the business connection on behalf of which the message will be sent
     */
    @JsonProperty(BUSINESS_CONNECTION_ID_FIELD)
    private String businessConnectionId;
    /**
     * Optional
     * Unique identifier of the message effect to be added to the message
     */
    @JsonProperty(MESSAGE_EFFECT_ID_FIELD)
    private String messageEffectId;
    /**
     * Optional
     * Pass True to allow up to 1000 messages per second, ignoring broadcasting limits for a fee of 0.1 Telegram Stars per message.
     * The relevant Stars will be withdrawn from the bot's balance
     */
    @JsonProperty(ALLOW_PAID_BROADCAST_FIELD)
    private Boolean allowPaidBroadcast;

    @Tolerate
    public void setChatId(@NonNull Long chatId) {
        this.chatId = chatId.toString();
    }

    public void disableWebPagePreview() {
        disableWebPagePreview = true;
    }

    public void enableWebPagePreview() {
        disableWebPagePreview = null;
    }

    public void enableNotification() {
        this.disableNotification = null;
    }

    public void disableNotification() {
        this.disableNotification = true;
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

    public void enableMarkdownV2(boolean enable) {
        if (enable) {
            this.parseMode = ParseMode.MARKDOWNV2;
        } else {
            this.parseMode = null;
        }
    }

    @Override
    public String getMethod() {
        return PATH;
    }

    @Override
    public void validate() throws TelegramApiValidationException {
        Validations.requiredChatId(chatId, this);
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
        if (replyParameters != null) {
            replyParameters.validate();
        }
    }

    public static abstract class SendMessageBuilder<C extends SendMessage, B extends SendMessageBuilder<C, B>> extends BotApiMethodMessageBuilder<C, B> {
        @Tolerate
        public SendMessageBuilder<C, B> chatId(@NonNull Long chatId) {
            this.chatId = chatId.toString();
            return this;
        }
    }
}
