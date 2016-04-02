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
 * @brief This object represents an incoming inline query.
 * When the user sends an empty query, your bot could return some default or trending results.
 * @date 01 of January of 2016
 */
public class InlineQuery implements IBotApiObject {
    public static final String ID_FIELD = "id";
    @JsonProperty(ID_FIELD)
    private String id; ///< Unique identifier for this query
    public static final String FROM_FIELD = "from";
    @JsonProperty(FROM_FIELD)
    private User from; ///< Sender
    public static final String QUERY_FIELD = "query";
    @JsonProperty(QUERY_FIELD)
    private String query; ///< Text of the query
    public static final String OFFSET_FIELD = "offset";
    @JsonProperty(OFFSET_FIELD)
    private String offset; ///< Offset of the results to be returned, can be controlled by the bot

    public InlineQuery() {
        super();
    }

    public InlineQuery(JSONObject jsonObject) {
        super();
        this.id = jsonObject.getString(ID_FIELD);
        this.from = new User(jsonObject.getJSONObject(FROM_FIELD));
        this.query = jsonObject.getString(QUERY_FIELD);
        this.offset = jsonObject.getString(OFFSET_FIELD);
    }

    @Override
    public void serialize(JsonGenerator gen, SerializerProvider serializers) throws IOException {
        gen.writeStartObject();
        gen.writeStringField(ID_FIELD, id);
        gen.writeObjectField(FROM_FIELD, from);
        gen.writeStringField(QUERY_FIELD, query);
        gen.writeStringField(OFFSET_FIELD, offset);
        gen.writeEndObject();
        gen.flush();
    }

    public String getId() {
        return id;
    }

    public User getFrom() {
        return from;
    }

    public String getQuery() {
        return query;
    }

    public String getOffset() {
        return offset;
    }

    @Override
    public void serializeWithType(JsonGenerator gen, SerializerProvider serializers, TypeSerializer typeSer) throws IOException {
        serialize(gen, serializers);
    }

    @Override
    public String toString() {
        return "InlineQuery{" +
                "id='" + id + '\'' +
                ", from=" + from +
                ", query='" + query + '\'' +
                ", offset='" + offset + '\'' +
                '}';
    }
}

