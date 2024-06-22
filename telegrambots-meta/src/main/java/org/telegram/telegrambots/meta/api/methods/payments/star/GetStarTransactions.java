package org.telegram.telegrambots.meta.api.methods.payments.star;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import lombok.extern.jackson.Jacksonized;
import org.telegram.telegrambots.meta.api.methods.botapimethods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.payments.star.StarTransactions;
import org.telegram.telegrambots.meta.exceptions.TelegramApiRequestException;
import org.telegram.telegrambots.meta.exceptions.TelegramApiValidationException;

/**
 * @author Ruben Bermudez
 * @version 7.5
 *
 * Returns the bot's Telegram Star transactions in chronological order.
 * On success, returns a StarTransactions object.
 */
@EqualsAndHashCode(callSuper = false)
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@SuperBuilder
@Jacksonized
@JsonIgnoreProperties(ignoreUnknown = true)
public class GetStarTransactions extends BotApiMethod<StarTransactions> {
    public static final String PATH = "getStarTransactions";

    private static final String OFFSET_FIELD = "offset";
    private static final String LIMIT_FIELD = "limit";

    /**
     * Optional
     * Number of transactions to skip in the response
     */
    @JsonProperty(OFFSET_FIELD)
    private Integer offset;
    /**
     * Optional
     * The maximum number of transactions to be retrieved.
     * Values between 1-100 are accepted.
     * Defaults to 100.
     */
    @JsonProperty(LIMIT_FIELD)
    private Integer limit;

    @Override
    public StarTransactions deserializeResponse(String answer) throws TelegramApiRequestException {
        return deserializeResponse(answer, StarTransactions.class);
    }

    @Override
    public String getMethod() {
        return PATH;
    }

    @Override
    public void validate() throws TelegramApiValidationException {
        if (limit != null && (limit > 100 || limit < 0)) {
            throw new TelegramApiValidationException("Limit parameters must be between 0 and 100", this);
        }
    }
}
