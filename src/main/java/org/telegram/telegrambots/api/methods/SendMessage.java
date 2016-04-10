package org.telegram.telegrambots.api.methods;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.jsontype.TypeSerializer;

import org.json.JSONObject;
import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.api.objects.ReplyKeyboard;
import org.telegram.telegrambots.common.Const;

import java.io.IOException;

/**
 * @author Ruben Bermudez
 * @version 1.0
 * @brief Use this method to send text messages. On success, the sent Message is returned.
 * @date 20 of June of 2015
 */
public class SendMessage extends BotApiMethod<Message> {
    public static final String PATH = "sendmessage";

    public static final String CHATID_FIELD = "chat_id";
    private String chatId; ///< Unique identifier for the chat to send the message to (Or username for channels)
    public static final String TEXT_FIELD = "text";
    private String text; ///< Text of the message to be sent
    public static final String PARSEMODE_FIELD = "parse_mode";
    private String parseMode; ///< Optional. Send Markdown, if you want Telegram apps to show bold, italic and URL text in your bot's message.
    public static final String DISABLEWEBPAGEPREVIEW_FIELD = "disable_web_page_preview";
    private boolean disableWebPagePreview; ///< Optional. Disables link previews for links in this message
    public static final String DISABLENOTIFICATION_FIELD = "disable_notification";
    /**
     * Optional. Sends the message silently.
     * iOS users will not receive a notification,
     * Android users will receive a notification with no sound.
     * Other apps coming soon
     */
    private boolean disableNotification;
    public static final String REPLYTOMESSAGEID_FIELD = "reply_to_message_id";
    @Deprecated
    private Integer replayToMessageId; ///< Optional. If the message is a reply, ID of the original message
    private Integer replyToMessageId; ///< Optional. If the message is a reply, ID of the original message
    public static final String REPLYMARKUP_FIELD = "reply_markup";
    @Deprecated
    private ReplyKeyboard replayMarkup; ///< Optional. JSON-serialized object for a custom reply keyboard
    private ReplyKeyboard replyMarkup; ///< Optional. JSON-serialized object for a custom reply keyboard

    public SendMessage() {
        super();
    }

    public String getChatId() {
        return chatId;
    }

    public void setChatId(String chatId) {
        this.chatId = chatId;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Deprecated
    public Integer getReplayToMessageId() {
        return replyToMessageId;
    }

    @Deprecated
    public void setReplayToMessageId(Integer replayToMessageId) {
        this.replyToMessageId = replayToMessageId;
    }

    @Deprecated
    public ReplyKeyboard getReplayMarkup() {
        return replyMarkup;
    }

    @Deprecated
    public void setReplayMarkup(ReplyKeyboard replayMarkup) {
        this.replyMarkup = replayMarkup;
    }

    @Deprecated
    public Boolean getDisableWebPagePreview() {
        return disableWebPagePreview;
    }

    @Deprecated
    public void setDisableWebPagePreview(Boolean disableWebPagePreview) {
        this.disableWebPagePreview = (disableWebPagePreview != null && disableWebPagePreview.booleanValue());
    }

    @Deprecated
    public Boolean getDisableNotification() {
        return disableNotification;
    }

    @Deprecated
    public void enableNotification() {
        this.disableNotification = false;
    }

    public void disableNotification() {
        this.disableNotification = true;
    }

    @Deprecated
    public void enableMarkdown(boolean enable) {
        if (enable) {
            this.parseMode = "Markdown";
        } else {
            this.parseMode = null;
        }
    }

    @Deprecated
    public void enableHtml(boolean enable) {
        if (enable) {
            this.parseMode = "html";
        } else {
            this.parseMode = null;
        }
    }

    public Integer getReplyToMessageId() {
        return replyToMessageId;
    }

    public void setReplyToMessageId(Integer replayToMessageId) {
        this.replyToMessageId = replayToMessageId;
    }

    public ReplyKeyboard getReplyMarkup() {
        return replyMarkup;
    }

    public void setReplyMarkup(ReplyKeyboard replayMarkup) {
        this.replyMarkup = replayMarkup;
    }

    public boolean isDisableWebPagePreview() {
        return disableWebPagePreview;
    }

    public void disableWebPagePreview() {
        this.disableWebPagePreview = true;
    }

    public boolean isDisableNotification() {
        return disableNotification;
    }

    public void enableMarkdown() {
        this.parseMode = "Markdown";
    }

    public void enableHtml() {
        this.parseMode = "html";
    }

    @Override
    public JSONObject toJson() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put(CHATID_FIELD, chatId);
        jsonObject.put(TEXT_FIELD, text);
        if (parseMode != null) {
            jsonObject.put(PARSEMODE_FIELD, parseMode);
        }
        if (disableWebPagePreview) {
            jsonObject.put(DISABLEWEBPAGEPREVIEW_FIELD, true);
        }
        if (disableNotification) {
            jsonObject.put(DISABLENOTIFICATION_FIELD, true);
        }
        if (replyToMessageId != null) {
            jsonObject.put(REPLYTOMESSAGEID_FIELD, replyToMessageId);
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
        if (answer.getBoolean(Const.RESPONSE_FIELD_OK)) {
            return new Message(answer.getJSONObject(Const.RESPONSE_FIELD_RESULT));
        }
        return null;
    }

    @Override
    public void serialize(JsonGenerator gen, SerializerProvider serializers) throws IOException {
        gen.writeStartObject();
        gen.writeStringField(METHOD_FIELD, PATH);
        gen.writeStringField(CHATID_FIELD, chatId);
        gen.writeStringField(TEXT_FIELD, text);

        if (parseMode != null) {
            gen.writeStringField(PARSEMODE_FIELD, parseMode);
        }
        if (disableWebPagePreview) {
            gen.writeBooleanField(DISABLEWEBPAGEPREVIEW_FIELD, true);
        }
        if (disableNotification) {
            gen.writeBooleanField(DISABLENOTIFICATION_FIELD, true);
        }
        if (replyToMessageId != null) {
            gen.writeNumberField(REPLYTOMESSAGEID_FIELD, replyToMessageId);
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
        return "SendMessage{" +
               "chatId='" + chatId + '\'' +
               ", text='" + text + '\'' +
               ", parseMode='" + parseMode + '\'' +
               ", disableWebPagePreview=" + disableWebPagePreview +
               ", disableNotification=" + disableNotification +
               ", replyToMessageId=" + replyToMessageId +
               ", replyMarkup=" + replyMarkup +
               '}';
    }
}
