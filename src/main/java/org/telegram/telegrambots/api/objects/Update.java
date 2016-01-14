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
 * @brief This object represents an incoming update.
 * Only one of the optional parameters can be present in any given update.
 * @date 20 of June of 2015
 */
public class Update implements IBotApiObject {
    public static final String UPDATEID_FIELD = "update_id";
    @JsonProperty(UPDATEID_FIELD)
    private Integer updateId;
    public static final String MESSAGE_FIELD = "message";
    @JsonProperty(MESSAGE_FIELD)
    private Message message; ///< Optional. New incoming message of any kind — text, photo, sticker, etc.
    public static final String INLINEQUERY_FIELD = "inline_query";
    @JsonProperty(INLINEQUERY_FIELD)
    private InlineQuery inlineQuery; ///< Optional. New incoming inline query
    public static final String CHOSENINLINEQUERY_FIELD = "chosen_inline_result";
    @JsonProperty(CHOSENINLINEQUERY_FIELD)
    private ChosenInlineQuery chosenInlineQuery; ///< Optional. The result of a inline query that was chosen by a user and sent to their chat partner

    /*
    	ChosenInlineResult
     */

    public Update() {
        super();
    }

    public Update(JSONObject jsonObject) {
        super();
        this.updateId = jsonObject.getInt(UPDATEID_FIELD);
        if (jsonObject.has(MESSAGE_FIELD)) {
            this.message = new Message(jsonObject.getJSONObject(MESSAGE_FIELD));
        }
        if (jsonObject.has(INLINEQUERY_FIELD)) {
            this.inlineQuery = new InlineQuery(jsonObject.getJSONObject(INLINEQUERY_FIELD));
        }
        if (jsonObject.has(CHOSENINLINEQUERY_FIELD)) {
            this.chosenInlineQuery = new ChosenInlineQuery(jsonObject.getJSONObject(CHOSENINLINEQUERY_FIELD));
        }
    }

    public Integer getUpdateId() {
        return updateId;
    }

    public Message getMessage() {
        return message;
    }

    public InlineQuery getInlineQuery() {
        return inlineQuery;
    }

    public ChosenInlineQuery getChosenInlineQuery() {
        return chosenInlineQuery;
    }

    public boolean hasMessage() {
        return message != null;
    }

    public boolean hasInlineQuery() {
        return inlineQuery != null;
    }

    public boolean hasChosenInlineQuery() {
        return chosenInlineQuery != null;
    }

    @Override
    public void serialize(JsonGenerator gen, SerializerProvider serializers) throws IOException {
        gen.writeStartObject();
        gen.writeNumberField(UPDATEID_FIELD, updateId);
        if (message != null) {
            gen.writeObjectField(MESSAGE_FIELD, message);
        }
        if (inlineQuery != null) {
            gen.writeObjectField(INLINEQUERY_FIELD, inlineQuery);
        }
        if (chosenInlineQuery != null) {
            gen.writeObjectField(CHOSENINLINEQUERY_FIELD, chosenInlineQuery);
        }
        gen.writeEndObject();
        gen.flush();
    }

    @Override
    public void serializeWithType(JsonGenerator gen, SerializerProvider serializers, TypeSerializer typeSer) throws IOException {
        serialize(gen, serializers);
    }
}
