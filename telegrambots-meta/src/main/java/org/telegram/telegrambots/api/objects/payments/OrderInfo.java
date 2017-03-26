package org.telegram.telegrambots.api.objects.payments;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.telegram.telegrambots.api.interfaces.BotApiObject;

/**
 * @author Ruben Bermudez
 * @version 1.0
 *
 * This object represents information about an order.
 */
public class OrderInfo implements BotApiObject {
    private static final String NAME_FIELD = "name";
    private static final String PHONE_NUMBER_FIELD = "phone_number";
    private static final String EMAIL_FIELD = "email";
    private static final String SHIPPING_ADDRESS_FIELD = "shipping_address";

    @JsonProperty(NAME_FIELD)
    private String name; ///< Optional. User name
    @JsonProperty(PHONE_NUMBER_FIELD)
    private String phoneNumber; ///< Optional. User's phone number
    @JsonProperty(EMAIL_FIELD)
    private String email; ///< Optional. User email
    @JsonProperty(SHIPPING_ADDRESS_FIELD)
    private ShippingAddress shippingAddress; ///< Optional. First line for the address

    public OrderInfo() {
        super();
    }

    public String getName() {
        return name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public ShippingAddress getShippingAddress() {
        return shippingAddress;
    }

    @Override
    public String toString() {
        return "OrderInfo{" +
                "name='" + name + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", email='" + email + '\'' +
                ", shippingAddress=" + shippingAddress +
                '}';
    }
}
