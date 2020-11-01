package org.telegram.telegrambots.meta.api.methods;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.type.TypeReference;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.telegram.telegrambots.meta.api.objects.ApiResponse;
import org.telegram.telegrambots.meta.exceptions.TelegramApiRequestException;
import org.telegram.telegrambots.meta.exceptions.TelegramApiValidationException;

import java.io.IOException;

/**
 * @author Ruben Bermudez
 * @version 1.0
 *
 * Once the user has confirmed their payment and shipping details, the Bot API sends the final confirmation
 * in the form of an Update with the field pre_checkout_query. Use this method to respond to such pre-checkout queries.
 *
 * On success, True is returned
 *
 * @apiNote The Bot API must receive an answer within 10 seconds after the pre-checkout query was sent.
 */
@EqualsAndHashCode(callSuper = false)
@Getter
@Setter
@ToString
@NoArgsConstructor
@RequiredArgsConstructor
@AllArgsConstructor
@Builder
public class AnswerPreCheckoutQuery extends BotApiMethod<Boolean> {
    public static final String PATH = "answerPreCheckoutQuery";

    private static final String PRE_CHECKOUT_QUERY_ID_FIELD = "pre_checkout_query_id";
    private static final String OK_FIELD = "ok";
    private static final String ERROR_MESSAGE_FIELD = "error_message";

    @JsonProperty(PRE_CHECKOUT_QUERY_ID_FIELD)
    @NonNull
    private String preCheckoutQueryId; ///< Unique identifier for the query to be answered
    @JsonProperty(OK_FIELD)
    @NonNull
    private Boolean ok; ///< Specify True if everything is alright (goods are available, etc.) and the bot is ready to proceed with the order. Use False if there are any problems.
    @JsonProperty(ERROR_MESSAGE_FIELD)
    private String errorMessage; ///< Optional. Required if ok is False. Error message in human readable form that explains the reason for failure to proceed with the checkout

    @Override
    public void validate() throws TelegramApiValidationException {
        if (preCheckoutQueryId == null || preCheckoutQueryId.isEmpty()) {
            throw new TelegramApiValidationException("PreCheckoutQueryId can't be empty", this);
        }
        if (ok == null) {
            throw new TelegramApiValidationException("Ok can't be null", this);
        }
        if (!ok) {
            if (errorMessage == null || errorMessage.isEmpty()) {
                throw new TelegramApiValidationException("ErrorMessage can't be empty if not ok", this);
            }
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
                throw new TelegramApiRequestException("Error answering pre-checkout query", result);
            }
        } catch (IOException e) {
            throw new TelegramApiRequestException("Unable to deserialize response", e);
        }
    }
}
