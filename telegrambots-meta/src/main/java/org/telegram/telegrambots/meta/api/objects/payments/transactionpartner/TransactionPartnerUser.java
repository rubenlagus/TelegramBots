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
import org.telegram.telegrambots.meta.api.objects.gifts.Gift;
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
    private static final String TRANSACTION_TYPE_FIELD = "transaction_type";
    private static final String USER_FIELD = "user";
    private static final String AFFILIATE_FIELD = "affiliate";
    private static final String INVOICE_PAYLOAD_FIELD = "invoice_payload";
    private static final String SUBSCRIPTION_PERIOD_FIELD = "subscription_period";
    private static final String PAID_MEDIA_FIELD = "paid_media";
    private static final String PAID_MEDIA_PAYLOAD_FIELD = "paid_media_payload";
    private static final String GIFT_FIELD = "gift";
    private static final String PREMIUM_SUBSCRIPTION_DURATION_FIELD = "premium_subscription_duration";

    /**
     * Type of the transaction partner, always “user”
     */
    @JsonProperty(TYPE_FIELD)
    @NonNull
    private final String type = "user";
    /**
     * Type of the transaction, currently one of “invoice_payment” for payments via invoices, “paid_media_payment”
     * for payments for paid media, “gift_purchase” for gifts sent by the bot, “premium_purchase”
     * for Telegram Premium subscriptions gifted by the bot, “business_account_transfer”
     * for direct transfers from managed business accounts
     */
    @JsonProperty(TRANSACTION_TYPE_FIELD)
    @NonNull
    private String transactionType;
    /**
     * Information about the user
     */
    @JsonProperty(USER_FIELD)
    @NonNull
    private User user;
    /**
     * Optional.
     * Information about the affiliate that received a commission via this transaction.
     * Can be available only for “invoice_payment” and “paid_media_payment” transactions.
     */
    @JsonProperty(AFFILIATE_FIELD)
    private AffiliateInfo affiliate;
    /**
     * Optional.
     * Bot-specified invoice payload. Can be available only for “invoice_payment” transactions.
     */
    @JsonProperty(INVOICE_PAYLOAD_FIELD)
    private String invoicePayload;
    /**
     * Optional.
     * The duration of the paid subscription.
     * Can be available only for “invoice_payment” transactions.
     */
    @JsonProperty(SUBSCRIPTION_PERIOD_FIELD)
    private Integer subscriptionPeriod;
    /**
     * Optional.
     * Information about the paid media bought by the user; for “paid_media_payment” transactions only
     */
    @JsonProperty(PAID_MEDIA_FIELD)
    private List<PaidMedia> paidMedia;
    /**
     * Optional.
     *  Bot-specified paid media payload. Can be available only for “paid_media_payment” transactions.
     */
    @JsonProperty(PAID_MEDIA_PAYLOAD_FIELD)
    private String paidMediaPayload;
    /**
     * Optional.
     * The gift sent to the user by the bot; for “gift_purchase” transactions only
     */
    @JsonProperty(GIFT_FIELD)
    private Gift gift;
    /**
     * Optional.
     * Number of months the gifted Telegram Premium subscription will be active for; for “premium_purchase” transactions only
     */
    @JsonProperty(PREMIUM_SUBSCRIPTION_DURATION_FIELD)
    private Integer premiumSubscriptionDuration;

}
