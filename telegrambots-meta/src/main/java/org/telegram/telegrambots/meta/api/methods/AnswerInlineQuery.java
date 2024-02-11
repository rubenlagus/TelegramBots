package org.telegram.telegrambots.meta.api.methods;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.Singular;
import lombok.ToString;
import lombok.extern.jackson.Jacksonized;
import org.telegram.telegrambots.meta.api.methods.botapimethods.BotApiMethodBoolean;
import org.telegram.telegrambots.meta.api.objects.inlinequery.result.InlineQueryResult;
import org.telegram.telegrambots.meta.api.objects.inlinequery.result.InlineQueryResultsButton;
import org.telegram.telegrambots.meta.exceptions.TelegramApiValidationException;

import java.util.List;
import java.util.regex.Pattern;

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
@NoArgsConstructor(force = true)
@AllArgsConstructor
@Builder
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
     * If passed, clients will display a button with specified text that switches the user to a private chat with the
     * bot and sends the bot a start message with the parameter switch_pm_parameter
     *
     * @deprecated Use {@link #setButton(InlineQueryResultsButton)}
     */
    @JsonProperty(SWITCH_PM_TEXT_FIELD)
    @Deprecated
    private String switchPmText;
    /**
     * Optional.
     * Parameter for the start message sent to the bot when user presses the switch button
     *
     * @deprecated Use {@link #setButton(InlineQueryResultsButton)}
     */
    @JsonProperty(SWITCH_PM_PARAMETER_FIELD)
    @Deprecated
    private String switchPmParameter;

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
        if (switchPmText != null) {
            if (switchPmText.isEmpty()) {
                throw new TelegramApiValidationException("SwitchPmText can't be empty", this);
            }
            if (switchPmParameter == null || switchPmParameter.isEmpty()) {
                throw new TelegramApiValidationException("SwitchPmParameter can't be empty if switchPmText is present", this);
            }
            if (switchPmParameter.length() > 64) {
                throw new TelegramApiValidationException("SwitchPmParameter can't be longer than 64 chars", this);
            }
            if (!Pattern.matches("[A-Za-z0-9_\\-]+", switchPmParameter.trim() )) {
                throw new TelegramApiValidationException("SwitchPmParameter only allows A-Z, a-z, 0-9, _ and - characters", this);
            }
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
