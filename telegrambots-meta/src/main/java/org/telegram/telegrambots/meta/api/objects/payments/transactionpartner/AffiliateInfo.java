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
import org.telegram.telegrambots.meta.api.interfaces.BotApiObject;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.api.objects.chat.Chat;

/**
 * @author Ruben Bermudez
 * @version 8.1
 * Contains information about the affiliate that received a commission via this transaction.
 */
@EqualsAndHashCode(callSuper = false)
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@SuperBuilder
@Jacksonized
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AffiliateInfo implements BotApiObject {
    private static final String AFFILIATE_USER_FIELD = "affiliate_user";
    private static final String AFFILIATE_CHAT_FIELD = "affiliate_chat";
    private static final String COMMISSION_PER_MILLE_FIELD = "commission_per_mille";
    private static final String AMOUNT_FIELD = "amount";
    private static final String NANO_STAR_AMOUNT_FIELD = "nanostar_amount";

    /**
     * Optional.
     * The bot or the user that received an affiliate commission if it was received by a bot or a user
     */
    @JsonProperty(AFFILIATE_USER_FIELD)
    private User affiliateUser;
    /**
     * Optional.
     * The chat that received an affiliate commission if it was received by a chat
     */
    @JsonProperty(AFFILIATE_CHAT_FIELD)
    private Chat affiliateChat;
    /**
     * The number of Telegram Stars received by the affiliate for each 1000 Telegram Stars received by the bot from referred users
     */
    @JsonProperty(COMMISSION_PER_MILLE_FIELD)
    @NonNull
    private Integer commissionPerMille;
    /**
     * Integer amount of Telegram Stars received by the affiliate from the transaction, rounded to 0; can be negative for refunds
     */
    @JsonProperty(AMOUNT_FIELD)
    @NonNull
    private Integer amount;
    /**
     * Optional.
     * The number of 1/1000000000 shares of Telegram Stars received by the affiliate; from -999999999 to 999999999; can be negative for refunds
     */
    @JsonProperty(NANO_STAR_AMOUNT_FIELD)
    private Integer nanoStarAmount;
}
