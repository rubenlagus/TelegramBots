package org.telegram.telegrambots.api.objects.inlinequery;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.jsontype.TypeSerializer;

import org.json.JSONObject;
import org.telegram.telegrambots.api.interfaces.IBotApiObject;
import org.telegram.telegrambots.api.objects.Location;
import org.telegram.telegrambots.api.objects.User;

import java.io.IOException;

/**
 * @author Ruben Bermudez
 * @version 1.0
 * @brief This object represents an incoming inline query. When the user sends an empty query, your
 * bot could return some default or trending results.
 * @date 01 of January of 2016
 */
public class InlineQuery implements IBotApiObject {
    private static final String ID_FIELD = "id";
    private static final String FROM_FIELD = "from";
    private static final String LOCATION_FIELD = "location";
    private static final String QUERY_FIELD = "query";
    private static final String OFFSET_FIELD = "offset";
    @JsonProperty(ID_FIELD)
    private String id; ///< Unique identifier for this query
    @JsonProperty(FROM_FIELD)
    private User from; ///< Sender
    @JsonProperty(LOCATION_FIELD)
    private Location location; ///< Optional. Sender location, only for bots that request user location
    @JsonProperty(QUERY_FIELD)
    private String query; ///< Text of the query
    @JsonProperty(OFFSET_FIELD)
    private String offset; ///< Offset of the results to be returned, can be controlled by the bot

    public InlineQuery() {
        super();
    }

    public InlineQuery(JSONObject jsonObject) {
        super();
        this.id = jsonObject.getString(ID_FIELD);
        this.from = new User(jsonObject.getJSONObject(FROM_FIELD));
        if (jsonObject.has(LOCATION_FIELD)) {
            location = new Location(jsonObject.getJSONObject(LOCATION_FIELD));
        }
        this.query = jsonObject.getString(QUERY_FIELD);
        this.offset = jsonObject.getString(OFFSET_FIELD);
    }

    @Override
    public void serialize(JsonGenerator gen, SerializerProvider serializers) throws IOException {
        gen.writeStartObject();
        gen.writeStringField(ID_FIELD, id);
        gen.writeObjectField(FROM_FIELD, from);
        if (location != null) {
            gen.writeObjectField(LOCATION_FIELD, location);
        }
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

    public Location getLocation() {
        return location;
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
                ", location=" + location +
                ", query='" + query + '\'' +
                ", offset='" + offset + '\'' +
                '}';
    }
}

