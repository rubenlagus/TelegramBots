package org.telegram.telegrambots.api.methods;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.type.TypeReference;

import org.telegram.telegrambots.api.objects.inlinequery.result.InlineQueryResult;
import org.telegram.telegrambots.api.objects.replykeyboard.ApiResponse;
import org.telegram.telegrambots.exceptions.TelegramApiRequestException;
import org.telegram.telegrambots.exceptions.TelegramApiValidationException;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

/**
 * @author Ruben Bermudez
 * @version 1.0
 * Use this method to send answers to an inline query. On success, True is returned.
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

    @JsonProperty(INLINEQUERYID_FIELD)
    private String inlineQueryId; ///< Unique identifier for answered query
    @JsonProperty(RESULTS_FIELD)
    private List<InlineQueryResult> results; ///< A JSON-serialized array of results for the inline query
    @JsonProperty(CACHETIME_FIELD)
    private Integer cacheTime; ///< Optional	The maximum amount of time the result of the inline query may be cached on the server
    @JsonProperty(ISPERSONAL_FIELD)
    private Boolean isPersonal; ///< Pass True, if results may be cached on the server side only for the user that sent the query. By default, results may be returned to any user who sends the same query
    @JsonProperty(NEXTOFFSET_FIELD)
    private String nextOffset; ///< Optional. Pass the offset that a client should send in the next query with the same text to receive more results. Pass an empty string if there are no more results or if you don‘t support pagination. Offset length can’t exceed 64 bytes.
    @JsonProperty(SWITCH_PM_TEXT_FIELD)
    private String switchPmText; ///< Optional. If passed, clients will display a button with specified text that switches the user to a private chat with the bot and sends the bot a start message with the parameter switch_pm_parameter
    @JsonProperty(SWITCH_PM_PARAMETER_FIELD)
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

    @JsonIgnore
    public AnswerInlineQuery setResults(InlineQueryResult... results) {
        this.results = Arrays.asList(results);
        return this;
    }

    public Integer getCacheTime() {
        return cacheTime;
    }

    public AnswerInlineQuery setCacheTime(Integer cacheTime) {
        this.cacheTime = cacheTime;
        return this;
    }

    public Boolean isPersonal() {
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
    public void validate() throws TelegramApiValidationException {
        if (inlineQueryId == null) {
            throw new TelegramApiValidationException("InlineQueryId can't be empty", this);
        }
        if (results == null) {
            throw new TelegramApiValidationException("Results array can't be null", this);
        }
        for (InlineQueryResult result : results) {
            result.validate();
        }
    }

    @Override
    public String getMethod() {
        return PATH;
    }

    @Override
    public Boolean deserializeResponse(String answer) throws TelegramApiRequestException {
        try {
            ApiResponse<Boolean> result = OBJECT_MAPPER.readValue(answer,
                    new TypeReference<ApiResponse<Boolean>>(){});
            if (result.getOk()) {
                return result.getResult();
            } else {
                throw new TelegramApiRequestException("Error answering inline query", result);
            }
        } catch (IOException e) {
            throw new TelegramApiRequestException("Unable to deserialize response", e);
        }
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
