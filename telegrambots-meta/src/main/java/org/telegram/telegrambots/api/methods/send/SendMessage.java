package org.telegram.telegrambots.api.methods.send;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.type.TypeReference;

import org.telegram.telegrambots.api.methods.BotApiMethod;
import org.telegram.telegrambots.api.methods.ParseMode;
import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.api.objects.replykeyboard.ApiResponse;
import org.telegram.telegrambots.api.objects.replykeyboard.ReplyKeyboard;
import org.telegram.telegrambots.exceptions.TelegramApiRequestException;
import org.telegram.telegrambots.exceptions.TelegramApiValidationException;

import java.io.IOException;
import java.util.Objects;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * @author Ruben Bermudez
 * @version 1.0
 * Use this method to send text messages. On success, the sent Message is returned.
 */
public class SendMessage extends BotApiMethod<Message> {
    public static final String PATH = "sendmessage";

    private static final String CHATID_FIELD = "chat_id";
    private static final String TEXT_FIELD = "text";
    private static final String PARSEMODE_FIELD = "parse_mode";
    private static final String DISABLEWEBPAGEPREVIEW_FIELD = "disable_web_page_preview";
    private static final String DISABLENOTIFICATION_FIELD = "disable_notification";
    private static final String REPLYTOMESSAGEID_FIELD = "reply_to_message_id";
    private static final String REPLYMARKUP_FIELD = "reply_markup";

    @JsonProperty(CHATID_FIELD)
    private String chatId; ///< Unique identifier for the chat to send the message to (Or username for channels)
    @JsonProperty(TEXT_FIELD)
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
    private ReplyKeyboard replyMarkup; ///< Optional. JSON-serialized object for a custom reply keyboard

    public SendMessage() {
        super();
    }

    public SendMessage(String chatId, String text) {
        this.chatId = checkNotNull(chatId);
        this.text = checkNotNull(text);
    }

    public SendMessage(Long chatId, String text) {
        this.chatId = checkNotNull(chatId).toString();
        this.text = checkNotNull(text);
    }

    public String getChatId() {
        return chatId;
    }

    public SendMessage setChatId(String chatId) {
        this.chatId = chatId;
        return this;
    }

    public SendMessage setChatId(Long chatId) {
        Objects.requireNonNull(chatId);
        this.chatId = chatId.toString();
        return this;
    }

    public String getText() {
        return text;
    }

    public SendMessage setText(String text) {
        this.text = text;
        return this;
    }

    public Integer getReplyToMessageId() {
        return replyToMessageId;
    }

    public SendMessage setReplyToMessageId(Integer replyToMessageId) {
        this.replyToMessageId = replyToMessageId;
        return this;
    }

    public ReplyKeyboard getReplyMarkup() {
        return replyMarkup;
    }

    public SendMessage setReplyMarkup(ReplyKeyboard replyMarkup) {
        this.replyMarkup = replyMarkup;
        return this;
    }

    public Boolean getDisableWebPagePreview() {
        return disableWebPagePreview;
    }

    public Boolean getDisableNotification() {
        return disableNotification;
    }

    public SendMessage disableWebPagePreview() {
        disableWebPagePreview = true;
        return this;
    }

    public SendMessage enableWebPagePreview() {
        disableWebPagePreview = null;
        return this;
    }

    public SendMessage enableNotification() {
        this.disableNotification = null;
        return this;
    }

    public SendMessage disableNotification() {
        this.disableNotification = true;
        return this;
    }

    public SendMessage setParseMode(String parseMode) {
        this.parseMode = parseMode;
        return this;
    }

    public SendMessage enableMarkdown(boolean enable) {
        if (enable) {
            this.parseMode = ParseMode.MARKDOWN;
        } else {
            this.parseMode = null;
        }
        return this;
    }

    public SendMessage enableHtml(boolean enable) {
        if (enable) {
            this.parseMode = ParseMode.HTML;
        } else {
            this.parseMode = null;
        }
        return this;
    }

    @Override
    public String getMethod() {
        return PATH;
    }

    @Override
    public Message deserializeResponse(String answer) throws TelegramApiRequestException {
        try {
            ApiResponse<Message> result = OBJECT_MAPPER.readValue(answer,
                    new TypeReference<ApiResponse<Message>>(){});
            if (result.getOk()) {
                return result.getResult();
            } else {
                throw new TelegramApiRequestException("Error sending message", result);
            }
        } catch (IOException e) {
            throw new TelegramApiRequestException("Unable to deserialize response", e);
        }
    }

    @Override
    public void validate() throws TelegramApiValidationException {
        if (chatId == null) {
            throw new TelegramApiValidationException("ChatId parameter can't be empty", this);
        }
        if (text == null || text.isEmpty()) {
            throw new TelegramApiValidationException("Text parameter can't be empty", this);
        }
        if (replyMarkup != null) {
            replyMarkup.validate();
        }
    }

    @Override
    public String toString() {
        return "SendMessage{" +
                "chatId='" + chatId + '\'' +
                ", text='" + text + '\'' +
                ", parseMode='" + parseMode + '\'' +
                ", disableWebPagePreview=" + disableWebPagePreview +
                ", replyToMessageId=" + replyToMessageId +
                ", replyMarkup=" + replyMarkup +
                '}';
    }
}
