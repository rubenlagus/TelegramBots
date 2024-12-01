package org.telegram.telegrambots.meta.api.methods.payments.star;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import lombok.extern.jackson.Jacksonized;
import org.telegram.telegrambots.meta.api.methods.botapimethods.BotApiMethodBoolean;
import org.telegram.telegrambots.meta.exceptions.TelegramApiValidationException;
import org.telegram.telegrambots.meta.util.Validations;

/**
 * @author Ruben Bermudez
 * @version 8.0
 *
 * Allows the bot to cancel or re-enable extension of a subscription paid in Telegram Stars.
 * Returns True on success.
 */
@EqualsAndHashCode(callSuper = false)
@Getter
@Setter
@ToString
@AllArgsConstructor
@SuperBuilder
@Jacksonized
@JsonIgnoreProperties(ignoreUnknown = true)
public class EditUserStarSubscription extends BotApiMethodBoolean {
    public static final String PATH = "editUserStarSubscription";

    private static final String USER_ID_FIELD = "user_id";
    private static final String TELEGRAM_PAYMENT_CHARGE_ID_FIELD = "telegram_payment_charge_id";
    private static final String IS_CANCELED_FIELD = "is_canceled";

    /**
     * Identifier of the user whose subscription will be edited
     */
    @JsonProperty(USER_ID_FIELD)
    @NonNull
    private Long userId;
    /**
     * Telegram payment identifier for the subscription
     */
    @JsonProperty(TELEGRAM_PAYMENT_CHARGE_ID_FIELD)
    @NonNull
    private String telegramPaymentChargeId;
    /**
     * Pass True to cancel extension of the user subscription;
     * the subscription must be active up to the end of the current subscription period.
     * Pass False to allow the user to re-enable a subscription that was previously canceled by the bot.
     */
    @JsonProperty(IS_CANCELED_FIELD)
    @NonNull
    private Boolean isCanceled;

    @Override
    public String getMethod() {
        return PATH;
    }

    @Override
    public void validate() throws TelegramApiValidationException {
        Validations.requiredUserId(userId, this);
        if (telegramPaymentChargeId.isEmpty()) {
            throw new TelegramApiValidationException("TelegramPaymentChargeId can´t be empty", this);
        }
    }
}
