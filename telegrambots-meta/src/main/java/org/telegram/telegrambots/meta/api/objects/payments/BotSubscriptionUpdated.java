package org.telegram.telegrambots.meta.api.objects.payments;

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
import org.telegram.telegrambots.meta.api.interfaces.BotApiObject;
import org.telegram.telegrambots.meta.api.objects.User;

/**
 * @author Ruben Bermudez
 * @version 10.2
 * This object contains information about changes to a user payment subscription toward the current bot.
 */
@EqualsAndHashCode(callSuper = false)
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@SuperBuilder
@Jacksonized
@JsonIgnoreProperties(ignoreUnknown = true)
public class BotSubscriptionUpdated implements BotApiObject {
    private static final String USER_FIELD = "user";
    private static final String INVOICE_PAYLOAD_FIELD = "invoice_payload";
    private static final String STATE_FIELD = "state";

    /**
     * User who subscribed for payments toward the bot
     */
    @JsonProperty(USER_FIELD)
    @NonNull
    private User user;

    /**
     * Bot-specified invoice payload
     */
    @JsonProperty(INVOICE_PAYLOAD_FIELD)
    @NonNull
    private String invoicePayload;

    /**
     * The new state of the subscription. Currently, it can be one of "canceled" if the user canceled
     * the subscription, "active" if the user re-enabled a previously canceled subscription,
     * or "failed" if payment for the subscription failed.
     */
    @JsonProperty(STATE_FIELD)
    @NonNull
    private String state;
}
