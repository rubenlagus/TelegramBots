package org.telegram.telegrambots.meta.api.objects.payments.transactionpartner;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import lombok.extern.jackson.Jacksonized;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.api.objects.payments.paidmedia.PaidMedia;

import java.util.List;

/**
 * @author Ruben Bermudez
 * @version 7.5
 *
 * Describes a transaction with a user.
 */

@EqualsAndHashCode(callSuper = false)
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Jacksonized
@SuperBuilder
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TransactionPartnerUser implements TransactionPartner {
    private static final String TYPE_FIELD = "type";
    private static final String USER_FIELD = "user";
    private static final String INVOICE_PAYLOAD_FIELD = "invoice_payload";
    private static final String PAID_MEDIA_FIELD = "paid_media";
    private static final String PAID_MEDIA_PAYLOAD_FIELD = "paid_media_payload";
    private static final String GIFT_FIELD = "gift";
    private static final String SUBSCRIPTION_PERIOD_FIELD = "subscription_period";

    /**
     * Type of the transaction partner, always “user”
     */
    @JsonProperty(TYPE_FIELD)
    private final String type = "user";
    /**
     * Information about the user
     */
    @JsonProperty(USER_FIELD)
    @NonNull
    private User user;
    /**
     * Optional.
     * Bot-specified invoice payload
     */
    @JsonProperty(INVOICE_PAYLOAD_FIELD)
    private String invoicePayload;
    /**
     * Optional.
     * Information about the paid media bought by the user
     */
    @JsonProperty(PAID_MEDIA_FIELD)
    private List<PaidMedia> paidMedia;

    /**
     * Optional.
     * Bot-specified paid media payload
     */
    @JsonProperty(PAID_MEDIA_PAYLOAD_FIELD)
    private String paidMediaPayload;

    /**
     * Optional.
     * The gift sent to the user by the bot
     */
    @JsonProperty(GIFT_FIELD)
    private String gift;

    /**
     * Optional.
     * The duration of the paid subscription
     */
    @JsonProperty(SUBSCRIPTION_PERIOD_FIELD)
    private Integer subscriptionPeriod;
}
