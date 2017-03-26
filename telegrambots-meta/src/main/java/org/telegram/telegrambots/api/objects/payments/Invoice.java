package org.telegram.telegrambots.api.objects.payments;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.telegram.telegrambots.api.interfaces.BotApiObject;
import org.telegram.telegrambots.api.objects.PhotoSize;

/**
 * @author Ruben Bermudez
 * @version 1.0
 *
 * This object contains basic information about an invoice.
 */
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
    @JsonProperty(START_PARAMETER_FIELD)
    private String startParameter; ///< Unique bot deep-linking parameter for generation of this invoice
    @JsonProperty(CURRENCY_FIELD)
    private String currency; ///< Three-letter ISO 4217 currency code
    @JsonProperty(TOTAL_AMOUNT_FIELD)
    /**
     * Total price in the smallest units of the currency (integer, not float/double).
     * For example, for a price of US$ 1.45 pass amount = 145.
     */
    private Integer totalAmount; ///< Goods total price in minimal quantity of the currency
    @JsonProperty(PHOTO_FIELD)
    private PhotoSize photo; ///< Optional. Goods photo

    public Invoice() {
        super();
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getStartParameter() {
        return startParameter;
    }

    public String getCurrency() {
        return currency;
    }

    public Integer getTotalAmount() {
        return totalAmount;
    }

    public PhotoSize getPhoto() {
        return photo;
    }

    @Override
    public String toString() {
        return "Invoice{" +
                "title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", startParameter='" + startParameter + '\'' +
                ", currency='" + currency + '\'' +
                ", totalAmount=" + totalAmount +
                ", photo=" + photo +
                '}';
    }
}
