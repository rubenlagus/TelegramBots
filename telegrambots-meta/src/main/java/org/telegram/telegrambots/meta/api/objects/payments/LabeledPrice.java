package org.telegram.telegrambots.meta.api.objects.payments;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import lombok.extern.jackson.Jacksonized;
import org.telegram.telegrambots.meta.api.interfaces.BotApiObject;
import org.telegram.telegrambots.meta.api.interfaces.Validable;
import org.telegram.telegrambots.meta.exceptions.TelegramApiValidationException;

/**
 * @author Ruben Bermudez
 * @version 1.0
 * This object represents a portion of goods price.
 */
@EqualsAndHashCode(callSuper = false)
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@SuperBuilder
@Jacksonized
public class LabeledPrice implements Validable, BotApiObject {
    private static final String LABEL_FIELD = "label";
    private static final String AMOUNT_FIELD = "amount";

    /**
     * Portion label
     */
    @JsonProperty(LABEL_FIELD)
    @NonNull
    private String label;
    /**
     * Price of the product in the smallest units of the currency (integer, not float/double).
     * For example, for a price of US$ 1.45 pass amount = 145.
     */
    @JsonProperty(AMOUNT_FIELD)
    @NonNull
    private Integer amount;

    @Override
    public void validate() throws TelegramApiValidationException {
        if (label.isEmpty()) {
            throw new TelegramApiValidationException("Label parameter can't be empty", this);
        }
        if (amount < 0) {
            throw new TelegramApiValidationException("Amount parameter can't be empty", this);
        }
    }
}
