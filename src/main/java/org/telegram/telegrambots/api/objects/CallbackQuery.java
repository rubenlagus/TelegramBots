package org.telegram.telegrambots.api.objects;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.jsontype.TypeSerializer;

import org.json.JSONObject;
import org.telegram.telegrambots.api.interfaces.IBotApiObject;

import java.io.IOException;

/**
 * @author Ruben Bermudez
 * @version 1.0
 * @brief This object represents an incoming callback query from a callback button in an inline
 * keyboard. If the button that originated the query was attached to a message sent by the bot, the
 * field message will be presented. If the button was attached to a message sent via the bot (in
 * inline mode), the field inline_message_id will be presented.
 * @date 10 of April of 2016
 */
public class CallbackQuery implements IBotApiObject {

    private static final String ID_FIELD = "id";
    private static final String FROM_FIELD = "from";
    private static final String MESSAGE_FIELD = "message";
    private static final String INLINE_MESSAGE_ID_FIELD = "inline_message_id";
    private static final String DATA_FIELD = "data";
    @JsonProperty(ID_FIELD)
    private String id; ///< Unique identifier for this query
    @JsonProperty(FROM_FIELD)
    private User from; ///< Sender
    @JsonProperty(MESSAGE_FIELD)
    /**
     * Optional.
     * Message with the callback button that originated the query.
     *
     * @note The message content and message date will not be available if the message is too old
     */
    private Message message;
    @JsonProperty(INLINE_MESSAGE_ID_FIELD)
    private String inlineMessageId; ///< Optional. Identifier of the message sent via the bot in inline mode, that originated the query
    @JsonProperty(DATA_FIELD)
    /**
     * Data associated with the callback button.
     * @note Be aware that a bad client can send arbitrary data in this field
     */
    private String data;

    public CallbackQuery() {
        super();
    }

    public CallbackQuery(JSONObject jsonObject) {
        super();
        this.id = jsonObject.getString(ID_FIELD);
        this.from = new User(jsonObject.getJSONObject(FROM_FIELD));
        if (jsonObject.has(MESSAGE_FIELD)) {
            this.message = new Message(jsonObject.getJSONObject(MESSAGE_FIELD));
        }
        if (jsonObject.has(INLINE_MESSAGE_ID_FIELD)) {
            this.inlineMessageId = jsonObject.getString(INLINE_MESSAGE_ID_FIELD);
        }
        data = jsonObject.getString(DATA_FIELD);
    }

    public String getId() {
        return this.id;
    }

    public User getFrom() {
        return this.from;
    }

    public Message getMessage() {
        return this.message;
    }

    public String getInlineMessageId() {
        return this.inlineMessageId;
    }

    public String getData() {
        return data;
    }

    @Override
    public void serialize(JsonGenerator gen, SerializerProvider serializers) throws IOException {
        gen.writeStartObject();
        gen.writeStringField(ID_FIELD, id);
        gen.writeObjectField(FROM_FIELD, from);
        if (message != null) {
            gen.writeObjectField(MESSAGE_FIELD, message);
        }
        if (inlineMessageId != null) {
            gen.writeStringField(INLINE_MESSAGE_ID_FIELD, inlineMessageId);
        }
        gen.writeStringField(DATA_FIELD, data);
        gen.writeEndObject();
        gen.flush();
    }

    @Override
    public void serializeWithType(JsonGenerator gen, SerializerProvider serializers, TypeSerializer typeSer) throws IOException {
        serialize(gen, serializers);
    }

    @Override
    public String toString() {
        return "CallbackQuery{" +
                "id='" + id + '\'' +
                ", from='" + from + '\'' +
                ", message='" + message + '\'' +
                ", inlineMessageId='" + inlineMessageId + '\'' +
                ", data=" + data +
                '}';
    }
}
