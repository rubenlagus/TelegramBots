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
import org.telegram.telegrambots.meta.api.objects.User;

/**
 * @author Ruben Bermudez
 * @version 8.1
 *
 * Describes the affiliate program that issued the affiliate commission received via this transaction.
 */

@EqualsAndHashCode(callSuper = false)
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Jacksonized
@JsonIgnoreProperties(ignoreUnknown = true)
@SuperBuilder
public class TransactionPartnerAffiliateProgram implements TransactionPartner {
    private static final String TYPE_FIELD = "type";
    private static final String SPONSOR_USER_FIELD = "sponsor_user";
    private static final String COMMISSION_PER_MILLE_FIELD = "commission_per_mille";

    /**
     * Type of the transaction partner, always “affiliate_program”
     */
    @JsonProperty(TYPE_FIELD)
    private final String type = "affiliate_program";
    /**
     * Optional.
     * Information about the bot that sponsored the affiliate program
     */
    @JsonProperty(SPONSOR_USER_FIELD)
    private User sponsorUser;
    /**
     * The number of Telegram Stars received by the bot for each 1000 Telegram Stars received by the affiliate program sponsor from referred users
     */
    @JsonProperty(COMMISSION_PER_MILLE_FIELD)
    private Integer commissionPerMille;
}
