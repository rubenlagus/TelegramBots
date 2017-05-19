package org.telegram.telegrambots.api.objects.payments;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.telegram.telegrambots.api.interfaces.BotApiObject;
import org.telegram.telegrambots.api.objects.User;

/**
 * @author Ruben Bermudez
 * @version 1.0
 *
 * This object contains information about incoming pre-checkout query.
 */
public class PreCheckoutQuery implements BotApiObject {
    private static final String ID_FIELD = "id";
    private static final String FROM_FIELD = "from";
    private static final String CURRENCY_FIELD = "currency";
    private static final String TOTAL_AMOUNT_FIELD = "total_amount";
    private static final String INVOICE_PAYLOAD_FIELD = "invoice_payload";
    private static final String SHIPPING_OPTION_ID_FIELD = "shipping_option_id";
    private static final String ORDER_INFO_FIELD = "order_info";

    @JsonProperty(ID_FIELD)
    private String id; ///< Unique query identifier
    @JsonProperty(FROM_FIELD)
    private User from; ///< User who sent the query
    @JsonProperty(CURRENCY_FIELD)
    private String currency; ///< Three-letter ISO 4217 currency code
    @JsonProperty(TOTAL_AMOUNT_FIELD)
    /**
     * Total price in the smallest units of the currency (integer, not float/double).
     * For example, for a price of US$ 1.45 pass amount = 145.
     */
    private Integer totalAmount;
    @JsonProperty(INVOICE_PAYLOAD_FIELD)
    private String invoicePayload; ///< Bot specified invoice payload
    @JsonProperty(SHIPPING_OPTION_ID_FIELD)
    private String shippingOptionId; ///< Optional. Identifier of a chosen by user shipping option
    @JsonProperty(ORDER_INFO_FIELD)
    private OrderInfo orderInfo; ///< Optional. Order info provided by the user

    public PreCheckoutQuery() {
        super();
    }

    public String getId() {
        return id;
    }

    public User getFrom() {
        return from;
    }

    public String getCurrency() {
        return currency;
    }

    public Integer getTotalAmount() {
        return totalAmount;
    }

    public String getInvoicePayload() {
        return invoicePayload;
    }

    public String getShippingOptionId() {
        return shippingOptionId;
    }

    public OrderInfo getOrderInfo() {
        return orderInfo;
    }

    @Override
    public String toString() {
        return "PreCheckoutQuery{" +
                "id='" + id + '\'' +
                ", from=" + from +
                ", currency='" + currency + '\'' +
                ", totalAmount=" + totalAmount +
                ", invoicePayload='" + invoicePayload + '\'' +
                ", shippingOptionId='" + shippingOptionId + '\'' +
                ", orderInfo=" + orderInfo +
                '}';
    }
}
