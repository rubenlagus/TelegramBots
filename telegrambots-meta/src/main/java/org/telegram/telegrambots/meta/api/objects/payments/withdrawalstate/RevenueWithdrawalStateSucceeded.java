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
 * The withdrawal succeeded.
 */

@EqualsAndHashCode(callSuper = false)
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Jacksonized
@JsonIgnoreProperties(ignoreUnknown = true)
@SuperBuilder
public class RevenueWithdrawalStateSucceeded implements RevenueWithdrawalState {
    private static final String TYPE_FIELD = "type";
    private static final String DATE_FIELD = "date";
    private static final String URL_FIELD = "url";

    /**
     * Type of the state, always “succeeded”
     */
    @JsonProperty(TYPE_FIELD)
    private final String type = "succeeded";
    /**
     * Date the withdrawal was completed in Unix time
     */
    @JsonProperty(DATE_FIELD)
    private Integer date;
    /**
     * An HTTPS URL that can be used to see transaction details
     */
    @JsonProperty(URL_FIELD)
    private String url;


}
