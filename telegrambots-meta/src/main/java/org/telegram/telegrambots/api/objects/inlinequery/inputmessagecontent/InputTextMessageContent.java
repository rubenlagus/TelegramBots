package org.telegram.telegrambots.api.objects.inlinequery.inputmessagecontent;

import com.fasterxml.jackson.annotation.JsonProperty;

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

    @JsonProperty(MESSAGETEXT_FIELD)
    private String messageText; ///< Text of a message to be sent, 1-4096 characters
    @JsonProperty(PARSEMODE_FIELD)
    private String parseMode; ///< Optional. Send Markdown or HTML, if you want Telegram apps to show bold, italic, fixed-width text or inline URLs in your bot's message.
    @JsonProperty(DISABLEWEBPAGEPREVIEW_FIELD)
    private Boolean disableWebPagePreview; ///< Optional. Disables link previews for links in the sent message

    public InputTextMessageContent() {
        super();
    }

    public String getMessageText() {
        return messageText;
    }

    public InputTextMessageContent setMessageText(String messageText) {
        this.messageText = messageText;
        return this;
    }

    public String getParseMode() {
        return parseMode;
    }

    public InputTextMessageContent setParseMode(String parseMode) {
        this.parseMode = parseMode;
        return this;
    }

    public Boolean getDisableWebPagePreview() {
        return disableWebPagePreview;
    }

    public InputTextMessageContent setDisableWebPagePreview(Boolean disableWebPagePreview) {
        this.disableWebPagePreview = disableWebPagePreview;
        return this;
    }

    public InputTextMessageContent enableMarkdown(boolean enable) {
        if (enable) {
            this.parseMode = ParseMode.MARKDOWN;
        } else {
            this.parseMode = null;
        }
        return this;
    }

    public InputTextMessageContent enableHtml(boolean enable) {
        if (enable) {
            this.parseMode = ParseMode.HTML;
        } else {
            this.parseMode = null;
        }
        return this;
    }

    public InputTextMessageContent disableWebPagePreview() {
        disableWebPagePreview = true;
        return this;
    }

    public InputTextMessageContent enableWebPagePreview() {
        disableWebPagePreview = null;
        return this;
    }

    @Override
    public void validate() throws TelegramApiValidationException {
        if (messageText == null || messageText.isEmpty()) {
            throw new TelegramApiValidationException("MessageText parameter can't be empty", this);
        }
    }

    @Override
    public String toString() {
        return "InputTextMessageContent{" +
                ", messageText='" + messageText + '\'' +
                ", parseMode='" + parseMode + '\'' +
                ", disableWebPagePreview=" + disableWebPagePreview +
                '}';
    }
}
