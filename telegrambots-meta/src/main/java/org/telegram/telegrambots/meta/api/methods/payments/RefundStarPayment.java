package org.telegram.telegrambots.meta.api.methods.payments;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import lombok.extern.jackson.Jacksonized;
import org.telegram.telegrambots.meta.api.methods.botapimethods.BotApiMethodBoolean;
import org.telegram.telegrambots.meta.exceptions.TelegramApiValidationException;

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
@RequiredArgsConstructor
@SuperBuilder
@Jacksonized
@JsonIgnoreProperties(ignoreUnknown = true)
public class RefundStarPayment extends BotApiMethodBoolean {
    public static final String PATH = "refundStarPayment";

    private static final String USER_ID_FIELD = "user_id";
    private static final String TELEGRAM_PAYMENT_CHARGE_ID_FIELD = "telegram_payment_charge_id";

    /**
     * Identifier of the user whose payment will be refunded
     */
    @JsonProperty(USER_ID_FIELD)
    @NonNull
    private Long userId;
    /**
     * Telegram payment identifier
     */
    @JsonProperty(TELEGRAM_PAYMENT_CHARGE_ID_FIELD)
    @NonNull
    private String telegramPaymentChargeId;

    @Override
    public String getMethod() {
        return PATH;
    }

    @Override
    public void validate() throws TelegramApiValidationException {
        if (telegramPaymentChargeId.isEmpty()) {
            throw new TelegramApiValidationException("TelegramPaymentChargeId parameter can't be empty", this);
        }
    }
}
