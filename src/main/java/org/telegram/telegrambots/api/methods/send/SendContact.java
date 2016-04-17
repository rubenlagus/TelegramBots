package org.telegram.telegrambots.api.methods.send;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.jsontype.TypeSerializer;

import org.json.JSONObject;
import org.telegram.telegrambots.Constants;
import org.telegram.telegrambots.api.methods.BotApiMethod;
import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.api.objects.replykeyboard.ReplyKeyboard;

import java.io.IOException;

/**
 * @author Ruben Bermudez
 * @version 1.0
 * @brief Use this method to send information about user contact. On success, the sent Message is
 * returned.
 * @date 10 of April of 2016
 */
public class SendContact extends BotApiMethod<Message> {
    public static final String PATH = "sendContact";

    public static final String CHATID_FIELD = "chat_id";
    public static final String PHONE_NUMBER_FIELD = "phone_number";
    public static final String FIRST_NAME_FIELD = "first_name";
    public static final String LAST_NAME_FIELD = "last_name";
    public static final String DISABLENOTIFICATION_FIELD = "disable_notification";
    public static final String REPLYTOMESSAGEID_FIELD = "reply_to_message_id";
    public static final String REPLYMARKUP_FIELD = "reply_markup";
    private String chatId; ///< Unique identifier for the chat to send the message to (Or username for channels)
    private String phoneNumber; ///< User's phone number
    private String firstName; ///< User's first name
    private String lastName; ///< Optional. User's last name
    /**
     * Optional. Sends the message silently. iOS users will not receive a notification, Android
     * users will receive a notification with no sound. Other apps coming soon
     */
    private Boolean disableNotification;
    private Integer replayToMessageId; ///< Optional. If the message is a reply, ID of the original message
    private ReplyKeyboard replayMarkup; ///< Optional. JSON-serialized object for a custom reply keyboard

    public String getChatId() {
        return chatId;
    }

    public SendContact setChatId(String chatId) {
        this.chatId = chatId;
        return this;
    }

    public Integer getReplayToMessageId() {
        return replayToMessageId;
    }

    public SendContact setReplayToMessageId(Integer replayToMessageId) {
        this.replayToMessageId = replayToMessageId;
        return this;
    }

    public ReplyKeyboard getReplayMarkup() {
        return replayMarkup;
    }

    public SendContact setReplayMarkup(ReplyKeyboard replayMarkup) {
        this.replayMarkup = replayMarkup;
        return this;
    }

    public Boolean getDisableNotification() {
        return disableNotification;
    }

    public SendContact enableNotification() {
        this.disableNotification = false;
        return this;
    }

    public SendContact disableNotification() {
        this.disableNotification = true;
        return this;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public SendContact setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
        return this;
    }

    public String getFirstName() {
        return firstName;
    }

    public SendContact setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public String getLastName() {
        return lastName;
    }

    public SendContact setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    @Override
    public String getPath() {
        return PATH;
    }

    @Override
    public Message deserializeResponse(JSONObject answer) {
        if (answer.getBoolean(Constants.RESPONSEFIELDOK)) {
            return new Message(answer.getJSONObject(Constants.RESPONSEFIELDRESULT));
        }
        return null;
    }

    @Override
    public JSONObject toJson() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put(CHATID_FIELD, chatId);
        jsonObject.put(PHONE_NUMBER_FIELD, phoneNumber);
        jsonObject.put(FIRST_NAME_FIELD, firstName);
        if (lastName != null) {
            jsonObject.put(LAST_NAME_FIELD, lastName);
        }
        if (disableNotification != null) {
            jsonObject.put(DISABLENOTIFICATION_FIELD, disableNotification);
        }
        if (replayToMessageId != null) {
            jsonObject.put(REPLYTOMESSAGEID_FIELD, replayToMessageId);
        }
        if (replayMarkup != null) {
            jsonObject.put(REPLYMARKUP_FIELD, replayMarkup.toJson());
        }

        return jsonObject;
    }

    @Override
    public void serialize(JsonGenerator gen, SerializerProvider serializers) throws IOException {
        gen.writeStartObject();
        gen.writeStringField(METHOD_FIELD, PATH);
        gen.writeStringField(CHATID_FIELD, chatId);
        gen.writeStringField(PHONE_NUMBER_FIELD, phoneNumber);
        gen.writeStringField(FIRST_NAME_FIELD, firstName);
        if (lastName != null) {
            gen.writeStringField(LAST_NAME_FIELD, lastName);
        }
        if (disableNotification != null) {
            gen.writeBooleanField(DISABLENOTIFICATION_FIELD, disableNotification);
        }
        if (replayToMessageId != null) {
            gen.writeNumberField(REPLYTOMESSAGEID_FIELD, replayToMessageId);
        }
        if (replayMarkup != null) {
            gen.writeObjectField(REPLYMARKUP_FIELD, replayMarkup);
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
        return "SendLocation{" +
                "chatId='" + chatId + '\'' +
                ", phoneNumber=" + phoneNumber +
                ", firstName=" + firstName +
                ", lastName=" + lastName +
                ", replayToMessageId=" + replayToMessageId +
                ", replayMarkup=" + replayMarkup +
                '}';
    }
}
