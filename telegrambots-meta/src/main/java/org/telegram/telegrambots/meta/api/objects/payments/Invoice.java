package org.telegram.telegrambots.meta.api.objects.payments;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.telegram.telegrambots.meta.api.interfaces.BotApiObject;
import org.telegram.telegrambots.meta.api.objects.PhotoSize;

/**
 * @author Ruben Bermudez
 * @version 1.0
 *
 * This object contains basic information about an invoice.
 */
@EqualsAndHashCode(callSuper = false)
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Invoice implements BotApiObject {
    private static final String TITLE_FIELD = "title";
    private static final String DESCRIPTION_FIELD = "description";
    private static final String START_PARAMETER_FIELD = "start_parameter";
    private static final String CURRENCY_FIELD = "currency";
    private static final String TOTAL_AMOUNT_FIELD = "total_amount";
    private static final String PHOTO_FIELD = "photo";

    @JsonProperty(TITLE_FIELD)
    private String title; ///< Product name
    @JsonProperty(DESCRIPTION_FIELD)
    private String description; ///< Product description
    /**
     * 	Unique bot deep-linking parameter that can be used to generate this invoice; may be empty
     */
    @JsonProperty(START_PARAMETER_FIELD)
    private String startParameter;
    @JsonProperty(CURRENCY_FIELD)
    private String currency; ///< Three-letter ISO 4217 currency code
    /**
     * Total price in the smallest units of the currency (integer, not float/double).
     * For example, for a price of US$ 1.45 pass amount = 145.
     */
    @JsonProperty(TOTAL_AMOUNT_FIELD)
    private Integer totalAmount; ///< Goods total price in minimal quantity of the currency
    @JsonProperty(PHOTO_FIELD)
    private PhotoSize photo; ///< Optional. Goods photo
}
