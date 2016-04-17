package org.telegram.telegrambots.api.methods;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.jsontype.TypeSerializer;

import org.json.JSONArray;
import org.json.JSONObject;
import org.telegram.telegrambots.Constants;
import org.telegram.telegrambots.api.objects.inlinequery.result.InlineQueryResult;

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

    private static final String INLINEQUERYID_FIELD = "inline_query_id";
    private static final String RESULTS_FIELD = "results";
    private static final String CACHETIME_FIELD = "cache_time";
    private static final String ISPERSONAL_FIELD = "is_personal";
    private static final String NEXTOFFSET_FIELD = "next_offset";
    private static final String SWITCH_PM_TEXT_FIELD = "switch_pm_text";
    private static final String SWITCH_PM_PARAMETER_FIELD = "switch_pm_parameter";
    private String inlineQueryId; ///< Unique identifier for answered query
    private List<InlineQueryResult> results; ///< A JSON-serialized array of results for the inline query
    private Integer cacheTime; ///< Optional	The maximum amount of time the result of the inline query may be cached on the server
    private Boolean isPersonal; ///< Pass True, if results may be cached on the server side only for the user that sent the query. By default, results may be returned to any user who sends the same query
    private String nextOffset; ///< Optional. Pass the offset that a client should send in the next query with the same text to receive more results. Pass an empty string if there are no more results or if you don‘t support pagination. Offset length can’t exceed 64 bytes.
    private String switchPmText; ///< Optional. If passed, clients will display a button with specified text that switches the user to a private chat with the bot and sends the bot a start message with the parameter switch_pm_parameter
    private String switchPmParameter; ///< Optional. Parameter for the start message sent to the bot when user presses the switch button

    public AnswerInlineQuery() {
        super();
    }

    public String getInlineQueryId() {
        return inlineQueryId;
    }

    public AnswerInlineQuery setInlineQueryId(String inlineQueryId) {
        this.inlineQueryId = inlineQueryId;
        return this;
    }

    public List<InlineQueryResult> getResults() {
        return results;
    }

    public AnswerInlineQuery setResults(List<InlineQueryResult> results) {
        this.results = results;
        return this;
    }

    public Integer getCacheTime() {
        return cacheTime;
    }

    public AnswerInlineQuery setCacheTime(Integer cacheTime) {
        this.cacheTime = cacheTime;
        return this;
    }

    public Boolean getPersonal() {
        return isPersonal;
    }

    public AnswerInlineQuery setPersonal(Boolean personal) {
        isPersonal = personal;
        return this;
    }

    public String getNextOffset() {
        return nextOffset;
    }

    public AnswerInlineQuery setNextOffset(String nextOffset) {
        this.nextOffset = nextOffset;
        return this;
    }

    public String getSwitchPmText() {
        return switchPmText;
    }

    public AnswerInlineQuery setSwitchPmText(String switchPmText) {
        this.switchPmText = switchPmText;
        return this;
    }

    public String getSwitchPmParameter() {
        return switchPmParameter;
    }

    public AnswerInlineQuery setSwitchPmParameter(String switchPmParameter) {
        this.switchPmParameter = switchPmParameter;
        return this;
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
        if (switchPmText != null) {
            jsonObject.put(SWITCH_PM_TEXT_FIELD, switchPmText);
        }
        if (switchPmParameter != null) {
            jsonObject.put(SWITCH_PM_PARAMETER_FIELD, switchPmParameter);
        }
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
        if (switchPmText != null) {
            gen.writeStringField(SWITCH_PM_TEXT_FIELD, switchPmText);
        }
        if (switchPmParameter != null) {
            gen.writeStringField(SWITCH_PM_PARAMETER_FIELD, switchPmParameter);
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
                ", switchPmText=" + switchPmText +
                ", switchPmParameter=" + switchPmParameter +
                ", nextOffset='" + nextOffset + '\'' +
                '}';
    }
}
