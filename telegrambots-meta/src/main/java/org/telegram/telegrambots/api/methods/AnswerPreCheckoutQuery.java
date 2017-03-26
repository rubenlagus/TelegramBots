package org.telegram.telegrambots.api.methods;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.type.TypeReference;
import org.telegram.telegrambots.api.objects.replykeyboard.ApiResponse;
import org.telegram.telegrambots.exceptions.TelegramApiRequestException;
import org.telegram.telegrambots.exceptions.TelegramApiValidationException;

import java.io.IOException;

import static com.google.common.base.Preconditions.checkNotNull;

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
public class AnswerPreCheckoutQuery extends BotApiMethod<Boolean> {
    public static final String PATH = "answerPreCheckoutQuery";

    private static final String PRE_CHECKOUT_QUERY_ID_FIELD = "pre_checkout_query_id";
    private static final String OK_FIELD = "ok";
    private static final String ERROR_MESSAGE_FIELD = "error_message";

    @JsonProperty(PRE_CHECKOUT_QUERY_ID_FIELD)
    private String preCheckoutQueryId; ///< Unique identifier for the query to be answered
    @JsonProperty(OK_FIELD)
    private Boolean ok; ///< Specify True if everything is alright (goods are available, etc.) and the bot is ready to proceed with the order. Use False if there are any problems.
    @JsonProperty(ERROR_MESSAGE_FIELD)
    private String errorMessage; ///< Optional. Required if ok is False. Error message in human readable form that explains the reason for failure to proceed with the checkout

    /**
     * Creates an empty answer pre-checkout query
     */
    public AnswerPreCheckoutQuery() {
        super();
    }

    /**
     * Creates an answer pre-checkout query with mandatory parameters
     * @param preCheckoutQueryId Unique identifier for the query to be answered
     * @param ok Specify True if delivery to the specified address is possible and False if there are any problems
     */
    public AnswerPreCheckoutQuery(String preCheckoutQueryId, Boolean ok) {
        this.preCheckoutQueryId = checkNotNull(preCheckoutQueryId);
        this.ok = checkNotNull(ok);
    }

    public String getPreCheckoutQueryId() {
        return preCheckoutQueryId;
    }

    public AnswerPreCheckoutQuery setPreCheckoutQueryId(String preCheckoutQueryId) {
        this.preCheckoutQueryId = checkNotNull(preCheckoutQueryId);
        return this;
    }

    public Boolean getOk() {
        return ok;
    }

    public AnswerPreCheckoutQuery setOk(Boolean ok) {
        this.ok = checkNotNull(ok);
        return this;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public AnswerPreCheckoutQuery setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
        return this;
    }

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

    @Override
    public String toString() {
        return "AnswerPreCheckoutQuery{" +
                "preCheckoutQueryId='" + preCheckoutQueryId + '\'' +
                ", ok=" + ok +
                ", errorMessage='" + errorMessage + '\'' +
                '}';
    }
}
