package org.telegram.telegrambots.api.methods;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.jsontype.TypeSerializer;
import org.json.JSONArray;
import org.json.JSONObject;
import org.telegram.telegrambots.api.objects.InlineQueryResult;

import java.io.IOException;
import java.util.List;

/**
 * @author Ruben Bermudez
 * @version 1.0
 * @brief Use this method to send answers to an inline query. On success, True is returned.
 * @date 01 of January of 2016
 */
public class AnswerInlineQuery extends BotApiMethod<Boolean> {
    public static final String PATH = "answerInlineQuery";

    public static final String INLINEQUERYID_FIELD = "inline_query_id";
    private String inlineQueryId; ///< Unique identifier for answered query
    public static final String RESULTS_FIELD = "results";
    private List<InlineQueryResult> results; ///< A JSON-serialized array of results for the inline query
    public static final String CACHETIME_FIELD = "cache_time";
    private Integer cacheTime; ///< Optional	The maximum amount of time the result of the inline query may be cached on the server
    public static final String ISPERSONAL_FIELD = "is_personal";
    private Boolean isPersonal; ///< Pass True, if results may be cached on the server side only for the user that sent the query. By default, results may be returned to any user who sends the same query
    public static final String NEXTOFFSET_FIELD = "next_offset";
    private String nextOffset; ///< Optional	Pass the offset that a client should send in the next query with the same text to receive more results. Pass an empty string if there are no more results or if you don‘t support pagination. Offset length can’t exceed 64 bytes.

    public AnswerInlineQuery() {
        super();
    }

    public String getInlineQueryId() {
        return inlineQueryId;
    }

    public void setInlineQueryId(String inlineQueryId) {
        this.inlineQueryId = inlineQueryId;
    }

    public List<InlineQueryResult> getResults() {
        return results;
    }

    public void setResults(List<InlineQueryResult> results) {
        this.results = results;
    }

    public Integer getCacheTime() {
        return cacheTime;
    }

    public void setCacheTime(Integer cacheTime) {
        this.cacheTime = cacheTime;
    }

    public Boolean getPersonal() {
        return isPersonal;
    }

    public void setPersonal(Boolean personal) {
        isPersonal = personal;
    }

    public String getNextOffset() {
        return nextOffset;
    }

    public void setNextOffset(String nextOffset) {
        this.nextOffset = nextOffset;
    }

    @Override
    public JSONObject toJson() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put(INLINEQUERYID_FIELD, inlineQueryId);
        JSONArray JSONResults = new JSONArray();
        for (InlineQueryResult result: results) {
            JSONResults.put(result.toJson());
        }
        jsonObject.put(RESULTS_FIELD, JSONResults);
        if (cacheTime != null) {
            jsonObject.put(CACHETIME_FIELD, cacheTime);
        }
        if (isPersonal != null) {
            jsonObject.put(ISPERSONAL_FIELD, isPersonal);
        }
        if (nextOffset != null) {
            jsonObject.put(NEXTOFFSET_FIELD, nextOffset);
        }
        return jsonObject;
    }

    @Override
    public String getPath() {
        return PATH;
    }

    @Override
    public Boolean deserializeResponse(JSONObject answer) {
        if (answer.getBoolean("ok")) {
            return answer.getBoolean("result");
        }
        return null;
    }

    @Override
    public void serialize(JsonGenerator gen, SerializerProvider serializers) throws IOException {
        gen.writeStartObject();
        gen.writeStringField(METHOD_FIELD, PATH);
        gen.writeArrayFieldStart(RESULTS_FIELD);
        for (InlineQueryResult result: results) {
            gen.writeObject(result);
        }
        gen.writeEndArray();
        if (cacheTime != null) {
            gen.writeNumberField(CACHETIME_FIELD, cacheTime);
        }
        if (isPersonal != null) {
            gen.writeBooleanField(ISPERSONAL_FIELD, isPersonal);
        }
        if (nextOffset != null) {
            gen.writeStringField(NEXTOFFSET_FIELD, nextOffset);
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
        return "AnswerInlineQuery{" +
                "inlineQueryId='" + inlineQueryId + '\'' +
                ", results=" + results +
                ", cacheTime=" + cacheTime +
                ", isPersonal=" + isPersonal +
                ", nextOffset='" + nextOffset + '\'' +
                '}';
    }
}
