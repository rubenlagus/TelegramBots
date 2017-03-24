package org.telegram.telegrambots.api.methods;



import org.telegram.telegrambots.api.objects.inlinequery.result.InlineQueryResult;
import org.telegram.telegrambots.api.objects.replykeyboard.ApiResponse;
import org.telegram.telegrambots.exceptions.TelegramApiRequestException;
import org.telegram.telegrambots.exceptions.TelegramApiValidationException;
import org.telegram.telegrambots.myclasses.TypeReference;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

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


    private String inline_query_id; ///< Unique identifier for answered query

    private List<InlineQueryResult> results; ///< A JSON-serialized array of results for the inline query

    private Integer cache_time; ///< Optional	The maximum amount of time the result of the inline query may be cached on the server

    private Boolean is_personal; ///< Pass True, if results may be cached on the server side only for the user that sent the query. By default, results may be returned to any user who sends the same query

    private String next_offset; ///< Optional. Pass the offset that a client should send in the next query with the same text to receive more results. Pass an empty string if there are no more results or if you don‘t support pagination. Offset length can’t exceed 64 bytes.

    private String switch_pm_text; ///< Optional. If passed, clients will display a button with specified text that switches the user to a private chat with the bot and sends the bot a start message with the parameter switch_pm_parameter

    private String switch_pm_parameter; ///< Optional. Parameter for the start message sent to the bot when user presses the switch button

    public AnswerInlineQuery() {
        super();
    }

    public String getInlineQueryId() {
        return inline_query_id;
    }

    public AnswerInlineQuery setInlineQueryId(String inlineQueryId) {
        this.inline_query_id = inlineQueryId;
        return this;
    }

    public List<InlineQueryResult> getResults() {
        return results;
    }

    public AnswerInlineQuery setResults(List<InlineQueryResult> results) {
        this.results = results;
        return this;
    }


    public AnswerInlineQuery setResults(InlineQueryResult... results) {
        this.results = Arrays.asList(results);
        return this;
    }

    public Integer getCacheTime() {
        return cache_time;
    }

    public AnswerInlineQuery setCacheTime(Integer cacheTime) {
        this.cache_time = cacheTime;
        return this;
    }

    public Boolean isPersonal() {
        return is_personal;
    }

    public AnswerInlineQuery setPersonal(Boolean personal) {
        is_personal = personal;
        return this;
    }

    public String getNextOffset() {
        return next_offset;
    }

    public AnswerInlineQuery setNextOffset(String nextOffset) {
        this.next_offset = nextOffset;
        return this;
    }

    public String getSwitchPmText() {
        return switch_pm_text;
    }

    public AnswerInlineQuery setSwitchPmText(String switchPmText) {
        this.switch_pm_text = switchPmText;
        return this;
    }

    public String getSwitchPmParameter() {
        return switch_pm_parameter;
    }

    public AnswerInlineQuery setSwitchPmParameter(String switchPmParameter) {
        this.switch_pm_parameter = switchPmParameter;
        return this;
    }

    @Override
    public void validate() throws TelegramApiValidationException {
        if (inline_query_id == null || inline_query_id.isEmpty()) {
            throw new TelegramApiValidationException("InlineQueryId can't be empty", this);
        }
        if (results == null) {
            throw new TelegramApiValidationException("Results array can't be null", this);
        }
        if (switch_pm_text != null) {
            if (switch_pm_text.isEmpty()) {
                throw new TelegramApiValidationException("SwitchPmText can't be empty", this);
            }
            if (switch_pm_parameter == null || switch_pm_parameter.isEmpty()) {
                throw new TelegramApiValidationException("SwitchPmParameter can't be empty if switchPmText is present", this);
            }
            if (switch_pm_parameter.length() > 64) {
                throw new TelegramApiValidationException("SwitchPmParameter can't be longer than 64 chars", this);
            }
            if (!Pattern.matches("[A-Za-z0-9_]+", switch_pm_parameter.trim() )) {
                throw new TelegramApiValidationException("SwitchPmParameter only allows A-Z, a-z, 0-9 and _ characters", this);
            }
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
                "inlineQueryId='" + inline_query_id + '\'' +
                ", results=" + results +
                ", cacheTime=" + cache_time +
                ", isPersonal=" + is_personal +
                ", switchPmText=" + switch_pm_text +
                ", switchPmParameter=" + switch_pm_parameter +
                ", nextOffset='" + next_offset + '\'' +
                '}';
    }
}
