package org.telegram.telegrambots.api.objects.payments;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.telegram.telegrambots.api.interfaces.InputBotApiObject;
import org.telegram.telegrambots.api.interfaces.Validable;
import org.telegram.telegrambots.exceptions.TelegramApiValidationException;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * @author Ruben Bermudez
 * @version 1.0
 * This object represents a portion of goods price.
 */
public class LabeledPrice implements InputBotApiObject, Validable {
    private static final String LABEL_FIELD = "label";
    private static final String AMOUNT_FIELD = "amount";

    @JsonProperty(LABEL_FIELD)
    private String label; ///< Portion label
    @JsonProperty(AMOUNT_FIELD)
    /**
     * Price of the product in the smallest units of the currency (integer, not float/double).
     * For example, for a price of US$ 1.45 pass amount = 145.
     */
    private Integer amount;

    /**
     * Builds an empty LabeledPrice
     */
    public LabeledPrice() {
        super();
    }

    /**
     * Builds a LabeledPrice with mandatory parameters
     * @param label Portion label
     * @param amount Currency amount in minimal quantity of the currency
     */
    public LabeledPrice(String label, Integer amount) {
        super();
        this.label = checkNotNull(label);
        this.amount = checkNotNull(amount);
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = checkNotNull(label);
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = checkNotNull(amount);
    }

    @Override
    public void validate() throws TelegramApiValidationException {
        if (label == null || label.isEmpty()) {
            throw new TelegramApiValidationException("Label parameter can't be empty", this);
        }
        if (amount == null) {
            throw new TelegramApiValidationException("Amount parameter can't be empty", this);
        }
    }

    @Override
    public String toString() {
        return "LabeledPrice{" +
                "label='" + label + '\'' +
                ", amount=" + amount +
                '}';
    }
}
