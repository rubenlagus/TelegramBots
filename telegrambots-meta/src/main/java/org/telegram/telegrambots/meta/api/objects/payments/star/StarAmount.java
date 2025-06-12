package org.telegram.telegrambots.meta.api.objects.payments.star;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.telegram.telegrambots.meta.api.interfaces.BotApiObject;
import org.telegram.telegrambots.meta.api.interfaces.Validable;
import org.telegram.telegrambots.meta.exceptions.TelegramApiValidationException;

/**
 * @author Ruben Bermudez
 * @version 9.0
 *
 * Describes an amount of Telegram Stars.
 */
@EqualsAndHashCode(callSuper = false)
@Getter
@Setter
@ToString
@NoArgsConstructor
@RequiredArgsConstructor
@AllArgsConstructor
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class StarAmount implements BotApiObject, Validable {
    private static final String AMOUNT_FIELD = "amount";
    private static final String NANOSTAR_AMOUNT_FIELD = "nanostar_amount";

    /**
     * Integer amount of Telegram Stars, rounded to 0; can be negative
     */
    @JsonProperty(AMOUNT_FIELD)
    @NonNull
    private Integer amount;

    /**
     * Optional.
     * The number of 1/1000000000 shares of Telegram Stars; from -999999999 to 999999999;
     * can be negative if and only if amount is non-positive
     */
    @JsonProperty(NANOSTAR_AMOUNT_FIELD)
    private Integer nanostarAmount;

    @Override
    public void validate() throws TelegramApiValidationException {
        if (nanostarAmount != null) {
            if (nanostarAmount < -999999999 || nanostarAmount > 999999999) {
                throw new TelegramApiValidationException("NanostarAmount must be between -999999999 and 999999999", this);
            }

            // Check if nanostarAmount is negative only when the amount is non-positive
            if (nanostarAmount < 0 && amount > 0) {
                throw new TelegramApiValidationException("NanostarAmount can be negative only if amount is non-positive", this);
            }
        }
    }
}