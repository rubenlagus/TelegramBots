package org.telegram.telegrambots.meta.api.objects.payments;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import lombok.extern.jackson.Jacksonized;
import org.telegram.telegrambots.meta.api.interfaces.BotApiObject;

/**
 * @author Ruben Bermudez
 * @version 7.7
 *
 * This object contains basic information about a refunded payment.
 */
@EqualsAndHashCode(callSuper = false)
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Jacksonized
@JsonIgnoreProperties(ignoreUnknown = true)
public class RefundedPayment implements BotApiObject {
    private static final String CURRENCY_FIELD = "currency";
    private static final String TOTAL_AMOUNT_FIELD = "total_amount";
    private static final String INVOICE_PAYLOAD_FIELD = "invoice_payload";
    private static final String TELEGRAM_PAYMENT_CHARGE_ID_FIELD = "telegram_payment_charge_id";
    private static final String PROVIDER_PAYMENT_CHARGE_ID_FIELD = "provider_payment_charge_id";

    /**
     * Three-letter ISO 4217 currency code, or “XTR” for payments in Telegram Stars.
     * Currently, always “XTR”
     */
    @JsonProperty(CURRENCY_FIELD)
    @NonNull
    private String currency;
    /**
     * Total refunded price in the smallest units of the currency (integer, not float/double).
     * For example, for a price of US$ 1.45, total_amount = 145.
     * See the exp parameter in currencies.json, it shows the number of digits past the decimal point
     * for each currency (2 for the majority of currencies).
     */
    @JsonProperty(TOTAL_AMOUNT_FIELD)
    @NonNull
    private Integer totalAmount;
    /**
     * Bot-specified invoice payload
     */
    @JsonProperty(INVOICE_PAYLOAD_FIELD)
    @NonNull
    private String invoicePayload;
    /**
     * Telegram payment identifier
     */
    @JsonProperty(TELEGRAM_PAYMENT_CHARGE_ID_FIELD)
    @NonNull
    private String telegramPaymentChargeId;
    /**
     * Optional.
     * Provider payment identifier
     */
    @JsonProperty(PROVIDER_PAYMENT_CHARGE_ID_FIELD)
    private String providerPaymentChargeId;
}
