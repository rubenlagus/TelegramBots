package org.telegram.telegrambots.meta.api.objects.payments;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;
import lombok.Singular;
import lombok.ToString;
import org.telegram.telegrambots.meta.api.interfaces.BotApiObject;
import org.telegram.telegrambots.meta.api.interfaces.Validable;
import org.telegram.telegrambots.meta.exceptions.TelegramApiValidationException;

import java.util.List;

/**
 * @author Ruben Bermudez
 * @version 1.0
 *
 * This object represents one shipping option.
 */
@EqualsAndHashCode(callSuper = false)
@Getter
@Setter
@ToString
@NoArgsConstructor(force = true)
@AllArgsConstructor
@Builder
public class ShippingOption implements Validable, BotApiObject {
    private static final String ID_FIELD = "id";
    private static final String TITLE_FIELD = "title";
    private static final String PRICES_FIELD = "prices";

    @JsonProperty(ID_FIELD)
    @NonNull
    private String id; ///< Shipping option identifier
    @JsonProperty(TITLE_FIELD)
    @NonNull
    private String title; ///< Option title
    @JsonProperty(PRICES_FIELD)
    @NonNull
    @Singular
    private List<LabeledPrice> prices; ///< List of price portions

    @Override
    public void validate() throws TelegramApiValidationException {
        if (id == null || id.isEmpty()) {
            throw new TelegramApiValidationException("Id parameter can't be empty", this);
        }
        if (title == null || title.isEmpty()) {
            throw new TelegramApiValidationException("Title parameter can't be empty", this);
        }
        if (prices == null || prices.isEmpty()) {
            throw new TelegramApiValidationException("Prices parameter can't be empty", this);
        }
        for (LabeledPrice price : prices) {
            price.validate();
        }
    }
}
