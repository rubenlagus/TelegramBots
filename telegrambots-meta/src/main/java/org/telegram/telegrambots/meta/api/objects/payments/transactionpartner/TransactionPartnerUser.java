package org.telegram.telegrambots.meta.api.objects.payments.transactionpartner;

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
@JsonIgnoreProperties(ignoreUnknown = true)
@SuperBuilder
public class TransactionPartnerUser implements TransactionPartner {
    private static final String TYPE_FIELD = "type";
    private static final String USER_FIELD = "user";
    private static final String INVOICE_PAYLOAD_FIELD = "invoice_payload";
    private static final String PAID_MEDIA_FIELD = "paid_media";

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
}
