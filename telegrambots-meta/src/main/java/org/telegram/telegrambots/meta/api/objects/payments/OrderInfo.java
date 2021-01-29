package org.telegram.telegrambots.meta.api.objects.payments;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.telegram.telegrambots.meta.api.interfaces.BotApiObject;

/**
 * @author Ruben Bermudez
 * @version 1.0
 *
 * This object represents information about an order.
 */
@EqualsAndHashCode(callSuper = false)
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
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
}
