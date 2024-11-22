package org.telegram.telegrambots.meta.api.methods;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.Singular;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import lombok.extern.jackson.Jacksonized;
import org.telegram.telegrambots.meta.api.methods.botapimethods.BotApiMethodBoolean;
import org.telegram.telegrambots.meta.api.objects.inlinequery.result.InlineQueryResult;
import org.telegram.telegrambots.meta.api.objects.inlinequery.result.InlineQueryResultsButton;
import org.telegram.telegrambots.meta.exceptions.TelegramApiValidationException;

import java.util.List;

/**
 * @author Ruben Bermudez
 * @version 1.0
 * Use this method to send answers to an inline query. On success, True is returned.
 */
@EqualsAndHashCode(callSuper = false)
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Jacksonized
public class AnswerInlineQuery extends BotApiMethodBoolean {
    public static final String PATH = "answerInlineQuery";

    private static final String INLINEQUERYID_FIELD = "inline_query_id";
    private static final String RESULTS_FIELD = "results";
    private static final String CACHETIME_FIELD = "cache_time";
    private static final String ISPERSONAL_FIELD = "is_personal";
    private static final String NEXTOFFSET_FIELD = "next_offset";
    private static final String SWITCH_PM_TEXT_FIELD = "switch_pm_text";
    private static final String SWITCH_PM_PARAMETER_FIELD = "switch_pm_parameter";
    private static final String BUTTON_FIELD = "button";

    @JsonProperty(INLINEQUERYID_FIELD)
    @NonNull
    private String inlineQueryId; ///< Unique identifier for answered query
    @JsonProperty(RESULTS_FIELD)
    @Singular
    @NonNull
    private List<InlineQueryResult> results; ///< A JSON-serialized array of results for the inline query
    @JsonProperty(CACHETIME_FIELD)
    private Integer cacheTime; ///< Optional	The maximum amount of time the result of the inline query may be cached on the server
    @JsonProperty(ISPERSONAL_FIELD)
    private Boolean isPersonal; ///< Pass True, if results may be cached on the server side only for the user that sent the query. By default, results may be returned to any user who sends the same query
    @JsonProperty(NEXTOFFSET_FIELD)
    private String nextOffset; ///< Optional. Pass the offset that a client should send in the next query with the same text to receive more results. Pass an empty string if there are no more results or if you don‘t support pagination. Offset length can’t exceed 64 bytes.

    /**
     * Optional.
     * A JSON serialized object describing a button to be shown above inline query results
     */
    @JsonProperty(BUTTON_FIELD)
    private InlineQueryResultsButton button;

    @Override
    public void validate() throws TelegramApiValidationException {
        if (inlineQueryId.isEmpty()) {
            throw new TelegramApiValidationException("InlineQueryId can't be empty", this);
        }
        for (InlineQueryResult result : results) {
            result.validate();
        }

        if (button != null) {
            button.validate();
        }
    }

    @Override
    public String getMethod() {
        return PATH;
    }
}
