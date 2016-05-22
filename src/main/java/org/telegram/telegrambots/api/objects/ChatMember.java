package org.telegram.telegrambots.api.objects;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.jsontype.TypeSerializer;

import org.json.JSONObject;
import org.telegram.telegrambots.api.interfaces.IBotApiObject;

import java.io.IOException;

/**
 * @author Ruben Bermudez
 * @version 1.0
 * @brief This object contains information about one member of the chat.
 * @date 20 of May of 2016
 */
public class ChatMember implements IBotApiObject {
    private static final String USER_FIELD = "user";
    private static final String STATUS_FIELD = "status";

    private User user; ///< Information about the user
    private String status; ///< The member's status in the chat. Can be “creator”, “administrator”, “member”, “left” or “kicked”

    public ChatMember(JSONObject object) {
        user = new User(object.getJSONObject(USER_FIELD));
        status = object.getString(STATUS_FIELD);
    }

    public User getUser() {
        return user;
    }

    public String getStatus() {
        return status;
    }

    @Override
    public void serialize(JsonGenerator gen, SerializerProvider serializers) throws IOException {
        gen.writeStartObject();
        gen.writeObjectField(USER_FIELD, user);
        gen.writeStringField(STATUS_FIELD, status);
        gen.writeEndObject();
    }

    @Override
    public void serializeWithType(JsonGenerator gen, SerializerProvider serializers, TypeSerializer typeSer) throws IOException {
        serialize(gen, serializers);
    }

    @Override
    public String toString() {
        return "ChatMember{" +
                "user=" + user +
                ", status='" + status + '\'' +
                '}';
    }
}
