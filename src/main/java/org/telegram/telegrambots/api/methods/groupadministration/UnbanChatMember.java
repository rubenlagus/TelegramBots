package org.telegram.telegrambots.api.methods.groupadministration;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.jsontype.TypeSerializer;

import org.json.JSONObject;
import org.telegram.telegrambots.Constants;
import org.telegram.telegrambots.api.methods.BotApiMethod;

import java.io.IOException;

/**
 * @author Ruben Bermudez
 * @version 1.0
 * @brief Use this method to unban a previously kicked user in a supergroup. The user will not
 * return to the group automatically, but will be able to join via link, etc. The bot must be an
 * administrator in the group for this to work. Returns True on success.
 * @date 10 of April of 2016
 */
public class UnbanChatMember extends BotApiMethod<Boolean> {
    public static final String PATH = "unbanchatmember";

    private static final String CHATID_FIELD = "chat_id";
    private static final String USER_ID_FIELD = "user_id";
    private String chatId; ///< Unique identifier for the chat to send the message to (Or username for channels)
    private Integer userId; ///< Unique identifier of the target user

    public UnbanChatMember() {
        super();
    }

    public String getChatId() {
        return chatId;
    }

    public UnbanChatMember setChatId(String chatId) {
        this.chatId = chatId;
        return this;
    }

    public Integer getUserId() {
        return userId;
    }

    public UnbanChatMember setUserId(Integer userId) {
        this.userId = userId;
        return this;
    }

    @Override
    public JSONObject toJson() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put(CHATID_FIELD, chatId);
        jsonObject.put(USER_ID_FIELD, userId);
        return jsonObject;
    }

    @Override
    public String getPath() {
        return PATH;
    }

    @Override
    public Boolean deserializeResponse(JSONObject answer) {
        if (answer.getBoolean(Constants.RESPONSEFIELDOK)) {
            return answer.getBoolean(Constants.RESPONSEFIELDRESULT);
        }
        return null;
    }

    @Override
    public void serialize(JsonGenerator gen, SerializerProvider serializers) throws IOException {
        gen.writeStartObject();
        gen.writeStringField(METHOD_FIELD, PATH);
        gen.writeStringField(CHATID_FIELD, chatId);
        gen.writeNumberField(USER_ID_FIELD, userId);
        gen.writeEndObject();
        gen.flush();
    }

    @Override
    public void serializeWithType(JsonGenerator gen, SerializerProvider serializers, TypeSerializer typeSer) throws IOException {
        serialize(gen, serializers);
    }

    @Override
    public String toString() {
        return "UnbanChatMember{" +
                "chatId='" + chatId + '\'' +
                ", userId='" + userId +
                '}';
    }
}
