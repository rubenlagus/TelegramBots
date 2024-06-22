package org.telegram.telegrambots.meta.api.objects.payments.withdrawalstate;

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
 * @version 7.5
 *
 * The withdrawal failed and the transaction was refunded.
 */

@EqualsAndHashCode(callSuper = false)
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Jacksonized
@JsonIgnoreProperties(ignoreUnknown = true)
@SuperBuilder
public class RevenueWithdrawalStateFailed implements RevenueWithdrawalState {
    private static final String TYPE_FIELD = "type";

    /**
     * Type of the state, always “failed”
     */
    @JsonProperty(TYPE_FIELD)
    private final String type = "failed";


}
