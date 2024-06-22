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
import org.telegram.telegrambots.meta.api.objects.payments.withdrawalstate.RevenueWithdrawalState;

/**
 * @author Ruben Bermudez
 * @version 7.5
 *
 * Describes a withdrawal transaction with Fragment.
 */

@EqualsAndHashCode(callSuper = false)
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Jacksonized
@JsonIgnoreProperties(ignoreUnknown = true)
@SuperBuilder
public class TransactionPartnerFragment implements TransactionPartner {
    private static final String TYPE_FIELD = "type";
    private static final String WITHDRAWAL_STATE_FIELD = "withdrawal_state";

    /**
     * Type of the transaction partner, always “fragment”
     */
    @JsonProperty(TYPE_FIELD)
    private final String type = "fragment";
    /**
     * Optional.
     * State of the transaction if the transaction is outgoing
     */
    @JsonProperty(WITHDRAWAL_STATE_FIELD)
    private RevenueWithdrawalState withdrawalState;
}
