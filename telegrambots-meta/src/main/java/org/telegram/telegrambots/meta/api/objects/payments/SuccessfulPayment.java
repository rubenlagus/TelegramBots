package org.telegram.telegrambots.meta.api.objects.payments;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import lombok.extern.jackson.Jacksonized;
import org.telegram.telegrambots.meta.api.interfaces.BotApiObject;

/**
 * @author Ruben Bermudez
 * @version 1.0
 *
 * This object contains basic information about a successful payment.
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
public class SuccessfulPayment implements BotApiObject {
    private static final String CURRENCY_FIELD = "currency";
    private static final String TOTAL_AMOUNT_FIELD = "total_amount";
    private static final String INVOICE_PAYLOAD_FIELD = "invoice_payload";
    private static final String SHIPPING_OPTION_ID_FIELD = "shipping_option_id";
    private static final String ORDER_INFO_FIELD = "order_info";
    private static final String TELEGRAM_PAYMENT_CHARGE_ID_FIELD = "telegram_payment_charge_id";
    private static final String PROVIDER_PAYMENT_CHARGE_ID_FIELD = "provider_payment_charge_id";
    private static final String SUBSCRIPTION_EXPIRATION_DATE_FIELD = "subscription_expiration_date";
    private static final String IS_RECURRING_FIELD = "is_recurring";
    private static final String IS_FIRST_RECURRING_FIELD = "is_first_recurring";

    /**
     * Three-letter ISO 4217 currency code, or “XTR” for payments in Telegram Stars
     */
    @JsonProperty(CURRENCY_FIELD)
    private String currency;
    /**
     * Total price in the smallest units of the currency (integer, not float/double).
     * For example, for a price of US$ 1.45 pass amount = 145.
     */
    @JsonProperty(TOTAL_AMOUNT_FIELD)
    private Integer totalAmount;
    /**
     * Bot specified invoice payload
     */
    @JsonProperty(INVOICE_PAYLOAD_FIELD)
    private String invoicePayload;
    /**
     *  Optional.
     *  Identifier of a chosen by user shipping option
     */
    @JsonProperty(SHIPPING_OPTION_ID_FIELD)
    private String shippingOptionId;
    /**
     * Optional.
     * Order info provided by the user
     */
    @JsonProperty(ORDER_INFO_FIELD)
    private OrderInfo orderInfo;
    /**
     * Telegram payment identifier
     */
    @JsonProperty(TELEGRAM_PAYMENT_CHARGE_ID_FIELD)
    private String telegramPaymentChargeId;
    /**
     * Provider payment identifier
     */
    @JsonProperty(PROVIDER_PAYMENT_CHARGE_ID_FIELD)
    private String providerPaymentChargeId;
    /**
     * Optional.
     * Expiration date of the subscription, in Unix time; for recurring payments only
     */
    @JsonProperty(SUBSCRIPTION_EXPIRATION_DATE_FIELD)
    private Integer subscriptionExpirationDate;
    /**
     * Optional.
     * True, if the payment is a recurring payment for a subscription
     */
    @JsonProperty(IS_RECURRING_FIELD)
    private Boolean isRecurring;
    /**
     * Optional.
     * True, if the payment is the first payment for a subscription
     */
    @JsonProperty(IS_FIRST_RECURRING_FIELD)
    private Boolean isFirstRecurring;
}
