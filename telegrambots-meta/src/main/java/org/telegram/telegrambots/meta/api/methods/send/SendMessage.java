package org.telegram.telegrambots.meta.api.methods.send;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Tolerate;
import org.telegram.telegrambots.meta.api.methods.ParseMode;
import org.telegram.telegrambots.meta.api.methods.botapimethods.BotApiMethodMessage;
import org.telegram.telegrambots.meta.api.objects.LinkPreviewOptions;
import org.telegram.telegrambots.meta.api.objects.MessageEntity;
import org.telegram.telegrambots.meta.api.objects.ReplyParameters;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;
import org.telegram.telegrambots.meta.exceptions.TelegramApiValidationException;

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
@NoArgsConstructor(force = true)
@AllArgsConstructor
@Builder
public class SendMessage extends BotApiMethodMessage {
    public static final String PATH = "sendmessage";

    private static final String CHATID_FIELD = "chat_id";
    private static final String MESSAGETHREADID_FIELD = "message_thread_id";
    private static final String TEXT_FIELD = "text";
    private static final String PARSEMODE_FIELD = "parse_mode";
    private static final String DISABLEWEBPAGEPREVIEW_FIELD = "disable_web_page_preview";
    private static final String DISABLENOTIFICATION_FIELD = "disable_notification";
    private static final String REPLYTOMESSAGEID_FIELD = "reply_to_message_id";
    private static final String REPLYMARKUP_FIELD = "reply_markup";
    private static final String ENTITIES_FIELD = "entities";
    private static final String ALLOWSENDINGWITHOUTREPLY_FIELD = "allow_sending_without_reply";
    private static final String PROTECTCONTENT_FIELD = "protect_content";
    private static final String LINK_PREVIEW_OPTIONS_FIELD = "link_preview_options";
    private static final String REPLY_PARAMETERS_FIELD = "reply_parameters";

    @JsonProperty(CHATID_FIELD)
    @NonNull
    private String chatId; ///< Unique identifier for the chat to send the message to (Or username for channels)
    /**
     * Unique identifier for the target message thread (topic) of the forum;
     * for forum supergroups only
     */
    @JsonProperty(MESSAGETHREADID_FIELD)
    private Integer messageThreadId;
    @JsonProperty(TEXT_FIELD)
    @NonNull
    private String text; ///< Text of the message to be sent
    @JsonProperty(PARSEMODE_FIELD)
    private String parseMode; ///< Optional. Send Markdown, if you want Telegram apps to show bold, italic and URL text in your bot's message.
    @JsonProperty(DISABLEWEBPAGEPREVIEW_FIELD)
    private Boolean disableWebPagePreview; ///< Optional. Disables link previews for links in this message
    @JsonProperty(DISABLENOTIFICATION_FIELD)
    private Boolean disableNotification; ///< Optional. Sends the message silently. Users will receive a notification with no sound.
    @JsonProperty(REPLYTOMESSAGEID_FIELD)
    private Integer replyToMessageId; ///< Optional. If the message is a reply, ID of the original message
    @JsonProperty(REPLYMARKUP_FIELD)
    @JsonDeserialize()
    private ReplyKeyboard replyMarkup; ///< Optional. JSON-serialized object for a custom reply keyboard
    @JsonProperty(ENTITIES_FIELD)
    private List<MessageEntity> entities; ///< Optional. List of special entities that appear in message text, which can be specified instead of parse_mode
    @JsonProperty(ALLOWSENDINGWITHOUTREPLY_FIELD)
    private Boolean allowSendingWithoutReply; ///< Optional	Pass True, if the message should be sent even if the specified replied-to message is not found
    @JsonProperty(PROTECTCONTENT_FIELD)
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
        if (chatId.isEmpty()) {
            throw new TelegramApiValidationException("ChatId parameter can't be empty", this);
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
        if (replyParameters != null) {
            replyParameters.validate();
        }
    }

    public static class SendMessageBuilder {

        @Tolerate
        public SendMessageBuilder chatId(@NonNull Long chatId) {
            this.chatId = chatId.toString();
            return this;
        }
    }
}
