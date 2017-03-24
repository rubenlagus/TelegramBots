package org.telegram.telegrambots.api.methods.updatingmessages;




import org.telegram.telegrambots.api.methods.BotApiMethod;
import org.telegram.telegrambots.api.methods.ParseMode;
import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.api.objects.replykeyboard.ApiResponse;
import org.telegram.telegrambots.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.exceptions.TelegramApiRequestException;
import org.telegram.telegrambots.exceptions.TelegramApiValidationException;
import org.telegram.telegrambots.myclasses.TypeReference;

import java.io.IOException;
import java.io.Serializable;
import java.util.Objects;

/**
 * @author Ruben Bermudez
 * @version 1.0
 * @brief Use this method to edit text messages sent by the bot or via the bot (for inline bots). On
 * success, if edited message is sent by the bot, the edited Message is returned, otherwise True is returned.
 * @date 10 of April of 2016
 */
public class EditMessageText extends BotApiMethod<Serializable> {
    public static final String PATH = "editmessagetext";

    private static final String CHATID_FIELD = "chat_id";
    private static final String MESSAGEID_FIELD = "message_id";
    private static final String INLINE_MESSAGE_ID_FIELD = "inline_message_id";
    private static final String TEXT_FIELD = "text";
    private static final String PARSE_MODE_FIELD = "parse_mode";
    private static final String DISABLE_WEB_PREVIEW_FIELD = "disable_web_page_preview";
    private static final String REPLYMARKUP_FIELD = "reply_markup";

    /**
     * Required if inline_message_id is not specified. Unique identifier for the chat to send the
     * message to (Or username for channels)
     */

    private String chat_id;
    /**
     * Required if inline_message_id is not specified. Unique identifier of the sent message
     */

    private Integer message_id;
    /**
     * Required if chat_id and message_id are not specified. Identifier of the inline message
     */

    private String inline_message_id;
    /**
     * New text of the message
     */

    private String text;
    /**
     * Optional. Send Markdown or HTML, if you want Telegram apps to show bold, italic, fixed-width
     * text or inline URLs in your bot's message.
     */

    private String parse_mode;

    private Boolean disable_web_page_preview; ///< Optional. Disables link previews for links in this message

    private InlineKeyboardMarkup reply_markup; ///< Optional. A JSON-serialized object for an inline keyboard.

    public EditMessageText() {
        super();
    }

    public String getChatId() {
        return chat_id;
    }

    public EditMessageText setChatId(String chat_id) {
        this.chat_id = chat_id;
        return this;
    }

    public EditMessageText setChatId(Long chat_id) {
        Objects.requireNonNull(chat_id);
        this.chat_id = chat_id.toString();
        return this;
    }

    public Integer getMessageId() {
        return message_id;
    }

    public EditMessageText setMessageId(Integer messageId) {
        this.message_id = messageId;
        return this;
    }

    public String getInlineMessageId() {
        return inline_message_id;
    }

    public EditMessageText setInlineMessageId(String inlineMessageId) {
        this.inline_message_id = inlineMessageId;
        return this;
    }

    public String getText() {
        return text;
    }

    public EditMessageText setText(String text) {
        this.text = text;
        return this;
    }

    public InlineKeyboardMarkup getReplyMarkup() {
        return reply_markup;
    }

    public EditMessageText setReplyMarkup(InlineKeyboardMarkup replyMarkup) {
        this.reply_markup = replyMarkup;
        return this;
    }

    public EditMessageText disableWebPagePreview() {
        disable_web_page_preview = true;
        return this;
    }

    public EditMessageText enableWebPagePreview() {
        disable_web_page_preview = null;
        return this;
    }

    public EditMessageText enableMarkdown(boolean enable) {
        if (enable) {
            this.parse_mode = ParseMode.MARKDOWN;
        } else {
            this.parse_mode = null;
        }
        return this;
    }

    public EditMessageText enableHtml(boolean enable) {
        if (enable) {
            this.parse_mode = ParseMode.HTML;
        } else {
            this.parse_mode = null;
        }
        return this;
    }


    public EditMessageText setParseMode(String parseMode) {
        this.parse_mode = parseMode;
        return this;
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
        if (inline_message_id == null) {
            if (chat_id == null) {
                throw new TelegramApiValidationException("ChatId parameter can't be empty if inlineMessageId is not present", this);
            }
            if (message_id == null) {
                throw new TelegramApiValidationException("MessageId parameter can't be empty if inlineMessageId is not present", this);
            }
        } else {
            if (chat_id != null) {
                throw new TelegramApiValidationException("ChatId parameter must be empty if inlineMessageId is provided", this);
            }
            if (message_id != null) {
                throw new TelegramApiValidationException("MessageId parameter must be empty if inlineMessageId is provided", this);
            }
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
        return "EditMessageText{" +
                "chatId=" + chat_id +
                ", messageId=" + message_id +
                ", inlineMessageId=" + inline_message_id +
                ", text=" + text +
                ", parseMode=" + parse_mode +
                ", disableWebPagePreview=" + disable_web_page_preview+
                ", replyMarkup=" + reply_markup +
                '}';
    }
}
