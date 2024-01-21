package org.telegram.telegrambots.meta.api.objects.giveaway;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.telegram.telegrambots.meta.api.interfaces.BotApiObject;
import org.telegram.telegrambots.meta.api.objects.Chat;

import java.util.List;

/**
 * @author Ruben Bermudez
 * @version 7.0
 * This object represents a message about a scheduled giveaway.
 */
@JsonDeserialize
@EqualsAndHashCode(callSuper = false)
@Getter
@Setter
@ToString
@NoArgsConstructor(force = true)
public class Giveaway implements BotApiObject {
    public static final String CHATS_FIELD = "chats";
    public static final String WINNERS_SELECTION_DATE_FIELD = "winners_selection_date";
    public static final String WINNER_COUNT_FIELD = "winner_count";
    public static final String ONLY_NEW_MEMBERS_FIELD = "only_new_members";
    public static final String HAS_PUBLIC_WINNERS_FIELD = "has_public_winners";
    public static final String PRIZE_DESCRIPTION_FIELD = "prize_description";
    public static final String COUNTRY_CODES_FIELD = "country_codes";
    public static final String PREMIUM_SUBSCRIPTION_MONTH_COUNT_FIELD = "premium_subscription_month_count";

    /**
     * The list of chats which the user must join to participate in the giveaway
     */
    @JsonProperty(CHATS_FIELD)
    private List<Chat> chats;
    /**
     * Point in time (Unix timestamp) when winners of the giveaway will be selected
     */
    @JsonProperty(WINNERS_SELECTION_DATE_FIELD)
    private Integer winnersSelectionDate;
    /**
     * The number of users which are supposed to be selected as winners of the giveaway
     */
    @JsonProperty(WINNER_COUNT_FIELD)
    private Integer winnerCount;
    /**
     * Optional.
     * True, if only users who join the chats after the giveaway started should be eligible to win
     */
    @JsonProperty(ONLY_NEW_MEMBERS_FIELD)
    private Boolean onlyNewMembers;
    /**
     * Optional.
     * True, if the list of giveaway winners will be visible to everyone
     */
    @JsonProperty(HAS_PUBLIC_WINNERS_FIELD)
    private Boolean hasPublicWinners;
    /**
     * Optional.
     * Description of additional giveaway prize
     */
    @JsonProperty(PRIZE_DESCRIPTION_FIELD)
    private String prizeDescription;
    /**
     * Optional.
     * A list of two-letter ISO 3166-1 alpha-2 country codes indicating the countries from which eligible
     * users for the giveaway must come.
     * If empty, then all users can participate in the giveaway.
     * Users with a phone number that was bought on Fragment can always participate in giveaways.
     */
    @JsonProperty(COUNTRY_CODES_FIELD)
    private List<String> countryCodes;
    /**
     * Optional.
     * The number of months the Telegram Premium subscription won from the giveaway will be active for
     */
    @JsonProperty(PREMIUM_SUBSCRIPTION_MONTH_COUNT_FIELD)
    private Integer premiumSubscriptionMonthCount;

}
