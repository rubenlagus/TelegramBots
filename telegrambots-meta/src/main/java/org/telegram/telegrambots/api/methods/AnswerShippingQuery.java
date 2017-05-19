package org.telegram.telegrambots.api.methods;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.type.TypeReference;
import org.telegram.telegrambots.api.objects.payments.ShippingOption;
import org.telegram.telegrambots.api.objects.replykeyboard.ApiResponse;
import org.telegram.telegrambots.exceptions.TelegramApiRequestException;
import org.telegram.telegrambots.exceptions.TelegramApiValidationException;

import java.io.IOException;
import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * @author Ruben Bermudez
 * @version 1.0
 *
 * If you sent an invoice requesting a shipping address and the parameter flexible was specified,
 * the Bot API will send an Update with a shipping_query field to the bot.
 * Use this method to reply to shipping queries.
 *
 * On success, True is returned
 */
public class AnswerShippingQuery extends BotApiMethod<Boolean> {
    public static final String PATH = "answerShippingQuery";

    private static final String SHIPPING_QUERY_ID_FIELD = "shipping_query_id";
    private static final String OK_FIELD = "ok";
    private static final String SHIPPING_OPTIONS_FIELD = "shipping_options";
    private static final String ERROR_MESSAGE_FIELD = "error_message";

    @JsonProperty(SHIPPING_QUERY_ID_FIELD)
    private String shippingQueryId; ///< Unique identifier for the query to be answered
    @JsonProperty(OK_FIELD)
    private Boolean ok; ///< Specify True if delivery to the specified address is possible and False if there are any problems
    @JsonProperty(SHIPPING_OPTIONS_FIELD)
    private List<ShippingOption> shippingOptions; ///< Optional. Required if ok is True. A JSON-serialized array of available shipping options.
    @JsonProperty(ERROR_MESSAGE_FIELD)
    private String errorMessage; ///< Optional. Required if ok is False. Error message in human readable form that explains why it is impossible to complete the order (e.g. "Sorry, delivery to your desired address is unavailable').

    /**
     * Creates an empty answer shipping query
     */
    public AnswerShippingQuery() {
        super();
    }

    /**
     * Creates an answer shipping query with mandatory parameters
     * @param shippingQueryId Unique identifier for the query to be answered
     * @param ok Specify True if delivery to the specified address is possible and False if there are any problems
     */
    public AnswerShippingQuery(String shippingQueryId, Boolean ok) {
        this.shippingQueryId = checkNotNull(shippingQueryId);
        this.ok = checkNotNull(ok);
    }

    public String getShippingQueryId() {
        return shippingQueryId;
    }

    public AnswerShippingQuery setShippingQueryId(String shippingQueryId) {
        this.shippingQueryId = checkNotNull(shippingQueryId);
        return this;
    }

    public Boolean getOk() {
        return ok;
    }

    public AnswerShippingQuery setOk(Boolean ok) {
        this.ok = checkNotNull(ok);
        return this;
    }

    public List<ShippingOption> getShippingOptions() {
        return shippingOptions;
    }

    public AnswerShippingQuery setShippingOptions(List<ShippingOption> shippingOptions) {
        this.shippingOptions = shippingOptions;
        return this;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public AnswerShippingQuery setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
        return this;
    }

    @Override
    public void validate() throws TelegramApiValidationException {
        if (shippingQueryId == null || shippingQueryId.isEmpty()) {
            throw new TelegramApiValidationException("ShippingQueryId can't be empty", this);
        }
        if (ok == null) {
            throw new TelegramApiValidationException("Ok can't be null", this);
        }
        if (ok) {
            if (shippingOptions == null || shippingOptions.isEmpty()) {
                throw new TelegramApiValidationException("ShippingOptions array can't be empty if ok", this);
            }
            for (ShippingOption shippingOption : shippingOptions) {
                shippingOption.validate();
            }
        } else {
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
                throw new TelegramApiRequestException("Error answering shipping query", result);
            }
        } catch (IOException e) {
            throw new TelegramApiRequestException("Unable to deserialize response", e);
        }
    }

    @Override
    public String toString() {
        return "AnswerShippingQuery{" +
                "shippingQueryId='" + shippingQueryId + '\'' +
                ", ok=" + ok +
                ", shippingOptions=" + shippingOptions +
                ", errorMessage='" + errorMessage + '\'' +
                '}';
    }
}
