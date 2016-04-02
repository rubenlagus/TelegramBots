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
 * @brief This object represents a result of an inline query that was chosen by the user and sent to their chat partner.
 * @date 01 of January of 2016
 */
public class ChosenInlineQuery implements IBotApiObject {
    public static final String RESULTID_FIELD = "result_id";
    public static final String FROM_FIELD = "from";
    public static final String QUERY_FIELD = "query";

    @JsonProperty(RESULTID_FIELD)
    private String resultId; ///< The unique identifier for the result that was chosen.
    @JsonProperty(FROM_FIELD)
    private User from; ///< The user that chose the result.
    @JsonProperty(QUERY_FIELD)
    private String query; ///< The query that was used to obtain the result.

    public ChosenInlineQuery() {
        super();
    }

    public ChosenInlineQuery(JSONObject jsonObject) {
        super();
        this.resultId = jsonObject.getString(RESULTID_FIELD);
        this.from = new User(jsonObject.getJSONObject(FROM_FIELD));
        this.query = jsonObject.getString(QUERY_FIELD);
    }

    public String getResultId() {
        return resultId;
    }

    public User getFrom() {
        return from;
    }

    public String getQuery() {
        return query;
    }

    @Override
    public void serialize(JsonGenerator gen, SerializerProvider serializers) throws IOException {
        gen.writeStartObject();
        gen.writeStringField(RESULTID_FIELD, resultId);
        gen.writeObjectField(FROM_FIELD, from);
        gen.writeStringField(QUERY_FIELD, query);
        gen.writeEndObject();
        gen.flush();
    }

    @Override
    public void serializeWithType(JsonGenerator gen, SerializerProvider serializers, TypeSerializer typeSer) throws IOException {
        serialize(gen, serializers);
    }

    @Override
    public String toString() {
        return "ChosenInlineQuery{" +
                "resultId='" + resultId + '\'' +
                ", from=" + from +
                ", query='" + query + '\'' +
                '}';
    }
}
