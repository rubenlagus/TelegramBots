package org.telegram.telegrambots.api.methods.updatingmessages;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.jsontype.TypeSerializer;

import org.json.JSONException;
import org.json.JSONObject;
import org.telegram.telegrambots.Constants;
import org.telegram.telegrambots.api.methods.BotApiMethod;
import org.telegram.telegrambots.api.methods.ParseMode;
import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.api.objects.replykeyboard.InlineKeyboardMarkup;

import java.io.IOException;

/**
 * @author Ruben Bermudez
 * @version 1.0
 * @brief Use this method to edit text messages sent by the bot or via the bot (for inline bots). On
 * success, the edited Message is returned.
 * @date 10 of April of 2016
 */
public class EditMessageText extends BotApiMethod<Message> {
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
    private String chatId;
    /**
     * Required if inline_message_id is not specified. Unique identifier of the sent message
     */
    private Integer messageId;
    /**
     * Required if chat_id and message_id are not specified. Identifier of the inline message
     */
    private String inlineMessageId;
    /**
     * New text of the message
     */
    private String text;
    /**
     * Optional. Send Markdown or HTML, if you want Telegram apps to show bold, italic, fixed-width
     * text or inline URLs in your bot's message.
     */
    private String parseMode;
    private Boolean disableWebPagePreview; ///< Optional. Disables link previews for links in this message
    private InlineKeyboardMarkup replyMarkup; ///< Optional. A JSON-serialized object for an inline keyboard.

    public EditMessageText() {
        super();
    }

    public String getChatId() {
        return chatId;
    }

    public EditMessageText setChatId(String chatId) {
        this.chatId = chatId;
        return this;
    }

    public Integer getMessageId() {
        return messageId;
    }

    public EditMessageText setMessageId(Integer messageId) {
        this.messageId = messageId;
        return this;
    }

    public String getInlineMessageId() {
        return inlineMessageId;
    }

    public EditMessageText setInlineMessageId(String inlineMessageId) {
        this.inlineMessageId = inlineMessageId;
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
        return replyMarkup;
    }

    public EditMessageText setReplyMarkup(InlineKeyboardMarkup replyMarkup) {
        this.replyMarkup = replyMarkup;
        return this;
    }

    public EditMessageText disableWebPagePreview() {
        disableWebPagePreview = true;
        return this;
    }

    public EditMessageText enableWebPagePreview() {
        disableWebPagePreview = null;
        return this;
    }

    public EditMessageText enableMarkdown(boolean enable) {
        if (enable) {
            this.parseMode = ParseMode.MARKDOWN;
        } else {
            this.parseMode = null;
        }
        return this;
    }

    public EditMessageText enableHtml(boolean enable) {
        if (enable) {
            this.parseMode = ParseMode.HTML;
        } else {
            this.parseMode = null;
        }
        return this;
    }

    @Override
    public JSONObject toJson() {
        JSONObject jsonObject = new JSONObject();
        if (inlineMessageId == null) {
            jsonObject.put(CHATID_FIELD, chatId);
            jsonObject.put(MESSAGEID_FIELD, messageId);
        } else {
            jsonObject.put(INLINE_MESSAGE_ID_FIELD, inlineMessageId);
        }
        jsonObject.put(TEXT_FIELD, text);
        if (parseMode != null) {
            jsonObject.put(PARSE_MODE_FIELD, parseMode);
        }
        if (disableWebPagePreview != null) {
            jsonObject.put(DISABLE_WEB_PREVIEW_FIELD, disableWebPagePreview);
        }
        if (replyMarkup != null) {
            jsonObject.put(REPLYMARKUP_FIELD, replyMarkup.toJson());
        }
        return jsonObject;
    }

    @Override
    public String getPath() {
        return PATH;
    }

    @Override
    public Message deserializeResponse(JSONObject answer) {
        if (answer.getBoolean(Constants.RESPONSEFIELDOK)) {
            try {
                return new Message(answer.getJSONObject(Constants.RESPONSEFIELDRESULT));
            } catch (JSONException e) {
                return new Message();
            }
        }
        return null;
    }

    @Override
    public void serialize(JsonGenerator gen, SerializerProvider serializers) throws IOException {
        gen.writeStartObject();
        gen.writeStringField(METHOD_FIELD, PATH);
        if (inlineMessageId == null) {
            gen.writeStringField(CHATID_FIELD, chatId);
            gen.writeNumberField(MESSAGEID_FIELD, messageId);
        } else {
            gen.writeStringField(INLINE_MESSAGE_ID_FIELD, inlineMessageId);
        }
        gen.writeStringField(TEXT_FIELD, text);
        if (parseMode != null) {
            gen.writeStringField(PARSE_MODE_FIELD, parseMode);
        }
        if (disableWebPagePreview != null) {
            gen.writeBooleanField(DISABLE_WEB_PREVIEW_FIELD, disableWebPagePreview);
        }
        if (replyMarkup != null) {
            gen.writeObjectField(REPLYMARKUP_FIELD, replyMarkup);
        }
        gen.writeEndObject();
        gen.flush();
    }

    @Override
    public void serializeWithType(JsonGenerator gen, SerializerProvider serializers, TypeSerializer typeSer) throws IOException {
        serialize(gen, serializers);
    }

    @Override
    public String toString() {
        return "EditMessageText{" +
                "chatId=" + chatId +
                ", messageId=" + messageId +
                ", inlineMessageId=" + inlineMessageId +
                ", text=" + text +
                ", parseMode=" + parseMode +
                ", disableWebPagePreview=" + disableWebPagePreview +
                ", replyMarkup=" + replyMarkup +
                '}';
    }
}
