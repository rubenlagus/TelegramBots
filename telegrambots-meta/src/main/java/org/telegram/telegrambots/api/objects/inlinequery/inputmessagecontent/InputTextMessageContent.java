package org.telegram.telegrambots.api.objects.inlinequery.inputmessagecontent;



import org.telegram.telegrambots.api.methods.ParseMode;
import org.telegram.telegrambots.exceptions.TelegramApiValidationException;

/**
 * @author Ruben Bermudez
 * @version 1.0
 * @brief Represents the content of a text message to be sent as the result of an inline query.
 * @date 10 of April of 2016
 */
public class InputTextMessageContent implements InputMessageContent {

    private static final String MESSAGETEXT_FIELD = "message_text";
    private static final String PARSEMODE_FIELD = "parse_mode";
    private static final String DISABLEWEBPAGEPREVIEW_FIELD = "disable_web_page_preview";


    private String message_text; ///< Text of a message to be sent, 1-4096 characters

    private String parse_mode; ///< Optional. Send Markdown or HTML, if you want Telegram apps to show bold, italic, fixed-width text or inline URLs in your bot's message.

    private Boolean disable_web_page_preview; ///< Optional. Disables link previews for links in the sent message

    public InputTextMessageContent() {
        super();
    }

    public String getMessageText() {
        return message_text;
    }

    public InputTextMessageContent setMessageText(String messageText) {
        this.message_text = messageText;
        return this;
    }

    public String getParseMode() {
        return parse_mode;
    }

    public InputTextMessageContent setParseMode(String parseMode) {
        this.parse_mode = parseMode;
        return this;
    }

    public Boolean getDisableWebPagePreview() {
        return disable_web_page_preview;
    }

    public InputTextMessageContent setDisableWebPagePreview(Boolean disableWebPagePreview) {
        this.disable_web_page_preview = disableWebPagePreview;
        return this;
    }

    public InputTextMessageContent enableMarkdown(boolean enable) {
        if (enable) {
            this.parse_mode = ParseMode.MARKDOWN;
        } else {
            this.parse_mode= null;
        }
        return this;
    }

    public InputTextMessageContent enableHtml(boolean enable) {
        if (enable) {
            this.parse_mode = ParseMode.HTML;
        } else {
            this.parse_mode = null;
        }
        return this;
    }

    public InputTextMessageContent disableWebPagePreview() {
        disable_web_page_preview = true;
        return this;
    }

    public InputTextMessageContent enableWebPagePreview() {
        disable_web_page_preview = null;
        return this;
    }

    @Override
    public void validate() throws TelegramApiValidationException {
        if (message_text == null || message_text.isEmpty()) {
            throw new TelegramApiValidationException("MessageText parameter can't be empty", this);
        }
    }

    @Override
    public String toString() {
        return "InputTextMessageContent{" +
                ", messageText='" + message_text + '\'' +
                ", parseMode='" +parse_mode + '\'' +
                ", disableWebPagePreview=" + disable_web_page_preview +
                '}';
    }
}
