package org.telegram.telegrambots.api.methods;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.jsontype.TypeSerializer;

import org.json.JSONObject;
import org.telegram.telegrambots.api.objects.Message;

import java.io.IOException;

/**
 * @author Ruben Bermudez
 * @version 1.0
 * @brief Use this method to send text messages. On success, the sent Message is returned.
 * @date 20 of June of 2015
 */
public class ForwardMessage extends BotApiMethod<Message> {
    public static final String PATH = "forwardmessage";

    public static final String CHATID_FIELD = "chat_id";
    private String chatId; ///< Unique identifier for the chat to send the message to (or username for channels)
    public static final String FROMCHATID_FIELD = "from_chat_id";
    private Integer fromChatId; ///< Unique identifier for the chat where the original message was sent — User or GroupChat id
    public static final String MESSAGEID_FIELD = "message_id";
    private Integer messageId; ///< Unique message identifier
    public static final String DISABLENOTIFICATION_FIELD = "disable_notification";
    /**
     * Optional. Sends the message silently.
     * iOS users will not receive a notification,
     * Android users will receive a notification with no sound.
     * Other apps coming soon
     */
    private Boolean disableNotification;

    public ForwardMessage() {
        super();
    }

    public String getChatId() {
        return chatId;
    }

    public void setChatId(String chatId) {
        this.chatId = chatId;
    }

    public Integer getFromChatId() {
        return fromChatId;
    }

    public void setFromChatId(Integer fromChatId) {
        this.fromChatId = fromChatId;
    }

    public Integer getMessageId() {
        return messageId;
    }

    public void setMessageId(Integer messageId) {
        this.messageId = messageId;
    }

    public Boolean getDisableNotification() {
        return disableNotification;
    }

    public void enableNotification() {
        this.disableNotification = false;
    }

    public void disableNotification() {
        this.disableNotification = true;
    }

    @Override
    public void serialize(JsonGenerator gen, SerializerProvider serializers) throws IOException {
        gen.writeStartObject();
        gen.writeStringField(METHOD_FIELD, PATH);
        gen.writeStringField(CHATID_FIELD, chatId);
        gen.writeNumberField(FROMCHATID_FIELD, fromChatId);
        gen.writeNumberField(MESSAGEID_FIELD, messageId);
        if (disableNotification != null) {
            gen.writeBooleanField(DISABLENOTIFICATION_FIELD, disableNotification);
        }
        gen.writeEndObject();
        gen.flush();
    }

    @Override
    public void serializeWithType(JsonGenerator gen, SerializerProvider serializers, TypeSerializer typeSer) throws IOException {
        serialize(gen, serializers);
    }

    @Override
    public JSONObject toJson() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put(CHATID_FIELD, chatId);
        jsonObject.put(FROMCHATID_FIELD, fromChatId);
        jsonObject.put(MESSAGEID_FIELD, messageId);
        if (disableNotification != null) {
            jsonObject.put(DISABLENOTIFICATION_FIELD, disableNotification);
        }
        return jsonObject;
    }

    @Override
    public String getPath() {
        return PATH;
    }

    @Override
    public Message deserializeResponse(JSONObject answer) {
        if (answer.getBoolean("ok")) {
            return new Message(answer.getJSONObject("result"));
        }
        return null;
    }

    @Override
    public String toString() {
        return "ForwardMessage{" +
                "chatId='" + chatId + '\'' +
                ", fromChatId=" + fromChatId +
                ", messageId=" + messageId +
                '}';
    }
}
