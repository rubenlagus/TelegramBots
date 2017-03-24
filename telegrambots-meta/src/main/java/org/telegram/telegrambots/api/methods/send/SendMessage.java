package org.telegram.telegrambots.api.methods.send;



import org.telegram.telegrambots.api.methods.BotApiMethod;
import org.telegram.telegrambots.api.methods.ParseMode;
import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.api.objects.replykeyboard.ApiResponse;
import org.telegram.telegrambots.api.objects.replykeyboard.ReplyKeyboard;
import org.telegram.telegrambots.exceptions.TelegramApiRequestException;
import org.telegram.telegrambots.exceptions.TelegramApiValidationException;
import org.telegram.telegrambots.myclasses.TypeReference;

import java.io.IOException;
import java.util.Objects;

/**
 * @author Ruben Bermudez
 * @version 1.0
 * @brief Use this method to send text messages. On success, the sent Message is returned.
 * @date 20 of June of 2015
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


    private String chat_id; ///< Unique identifier for the chat to send the message to (Or username for channels)

    private String text; ///< Text of the message to be sent

    private String parse_mode; ///< Optional. Send Markdown, if you want Telegram apps to show bold, italic and URL text in your bot's message.

    private Boolean disable_web_page_preview; ///< Optional. Disables link previews for links in this message
    /**
     * Optional. Sends the message silently. iOS users will not receive a notification, Android
     * users will receive a notification with no sound. Other apps coming soon
     */

    private Boolean disable_notification;

    private Integer reply_to_message_id; ///< Optional. If the message is a reply, ID of the original message

    private ReplyKeyboard reply_markup; ///< Optional. JSON-serialized object for a custom reply keyboard

    public SendMessage() {
        super();
    }

    public String getChatId() {
        return chat_id;
    }

    public SendMessage setChatId(String chat_id) {
        this.chat_id = chat_id;
        return this;
    }

    public SendMessage setChatId(Long chat_id) {
        Objects.requireNonNull(chat_id);
        this.chat_id = chat_id.toString();
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
        return reply_to_message_id;
    }

    public SendMessage setReplyToMessageId(Integer replyToMessageId) {
        this.reply_to_message_id = replyToMessageId;
        return this;
    }

    public ReplyKeyboard getReplyMarkup() {
        return reply_markup;
    }

    public SendMessage setReplyMarkup(ReplyKeyboard replyMarkup) {
        this.reply_markup = replyMarkup;
        return this;
    }

    public Boolean getDisableWebPagePreview() {
        return disable_web_page_preview;
    }

    public Boolean getDisableNotification() {
        return disable_notification;
    }

    public SendMessage disableWebPagePreview() {
        disable_web_page_preview = true;
        return this;
    }

    public SendMessage enableWebPagePreview() {
        disable_web_page_preview = null;
        return this;
    }

    public SendMessage enableNotification() {
        this.disable_notification = null;
        return this;
    }

    public SendMessage disableNotification() {
        this.disable_notification = true;
        return this;
    }

    public SendMessage setParseMode(String parseMode) {
        this.parse_mode = parseMode;
        return this;
    }

    public SendMessage enableMarkdown(boolean enable) {
        if (enable) {
            this.parse_mode = ParseMode.MARKDOWN;
        } else {
            this.parse_mode = null;
        }
        return this;
    }

    public SendMessage enableHtml(boolean enable) {
        if (enable) {
            this.parse_mode = ParseMode.HTML;
        } else {
            this.parse_mode = null;
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
        if (chat_id == null) {
            throw new TelegramApiValidationException("chat_id parameter can't be empty", this);
        }
        if (text == null || text.isEmpty()) {
            throw new TelegramApiValidationException("Text parameter can't be empty", this);
        }
        if (reply_markup != null) {
            reply_markup.validate();
        }
    }

    @Override
    public String toString() {
        return "SendMessage{" +
                "chat_id='" + chat_id + '\'' +
                ", text='" + text + '\'' +
                ", parseMode='" + parse_mode + '\'' +
                ", disableWebPagePreview=" + disable_web_page_preview +
                ", replyToMessageId=" + reply_to_message_id +
                ", replyMarkup=" + reply_markup +
                '}';
    }
}
