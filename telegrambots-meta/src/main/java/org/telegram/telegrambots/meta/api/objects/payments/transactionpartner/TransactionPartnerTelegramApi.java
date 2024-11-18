package org.telegram.telegrambots.meta.api.objects.payments.transactionpartner;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import lombok.extern.jackson.Jacksonized;

/**
 * @author Ruben Bermudez
 * @version 7.11
 *
 * Describes a transaction with payment for paid broadcasting.
 */

@EqualsAndHashCode(callSuper = false)
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Jacksonized
@JsonIgnoreProperties(ignoreUnknown = true)
@SuperBuilder
public class TransactionPartnerTelegramApi implements TransactionPartner {
    private static final String TYPE_FIELD = "type";
    private static final String REQUEST_COUNT_FIELD = "request_count";

    /**
     * Type of the transaction partner, always “telegram_api”
     */
    @JsonProperty(TYPE_FIELD)
    private final String type = "telegram_api";
    /**
     * The number of successful requests that exceeded regular limits and were therefore billed
     */
    @JsonProperty(REQUEST_COUNT_FIELD)
    private Integer requestCount;
}
