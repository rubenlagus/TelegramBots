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
 * @version 7.4
 *
 * Refunds a successful payment in Telegram Stars. Returns True on success.
 */
@EqualsAndHashCode(callSuper = false)
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class RefundStarPayment implements BotApiObject {
    private static final String USER_ID_FIELD = "user_id";
    private static final String TELEGRAM_PAYMENT_CHARGE_ID_FIELD = "telegram_payment_charge_id";

    /**
     * Identifier of the user whose payment will be refunded
     */
    @JsonProperty(USER_ID_FIELD)
    private Integer userId;
    /**
     * Telegram payment identifier
     */
    @JsonProperty(TELEGRAM_PAYMENT_CHARGE_ID_FIELD)
    private String telegramPaymentChargeId;
}
