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
import org.telegram.telegrambots.meta.api.objects.User;

import java.util.List;

/**
 * @author Ruben Bermudez
 * @version 7.0
 * This object represents a message about the completion of a giveaway with public winners.
 */
@JsonDeserialize
@EqualsAndHashCode(callSuper = false)
@Getter
@Setter
@ToString
@NoArgsConstructor(force = true)
public class GiveawayWinners implements BotApiObject {
    public static final String CHAT_FIELD = "chat";
    public static final String GIVEAWAY_MESSAGE_ID_FIELD = "giveaway_message_id";
    public static final String WINNERS_SELECTION_DATE_FIELD = "winners_selection_date";
    public static final String WINNER_COUNT_FIELD = "winner_count";
    public static final String WINNERS_FIELD = "winners";
    public static final String ADDITIONAL_CHAT_COUNT_FIELD = "additional_chat_count";
    public static final String PREMIUM_SUBSCRIPTION_MONTH_COUNT_FIELD = "premium_subscription_month_count";
    public static final String UNCLAIMED_PRIZE_COUNT_FIELD = "unclaimed_prize_count";
    public static final String ONLY_NEW_MEMBERS_FIELD = "only_new_members";
    public static final String WAS_REFUNDED_FIELD = "was_refunded";
    public static final String PRIZE_DESCRIPTION_FIELD = "prize_description";

    /**
     * The chat that created the giveaway
     */
    @JsonProperty(CHAT_FIELD)
    private Chat chat;
    /**
     * The chat that created the giveaway
     */
    @JsonProperty(GIVEAWAY_MESSAGE_ID_FIELD)
    private Integer giveawayMessageId;
    /**
     * Point in time (Unix timestamp) when winners of the giveaway were selected
     */
    @JsonProperty(WINNERS_SELECTION_DATE_FIELD)
    private Integer winnersSelectionDate;
    /**
     * Total number of winners in the giveaway
     */
    @JsonProperty(WINNER_COUNT_FIELD)
    private Integer winnerCount;
    /**
     * List of up to 100 winners of the giveaway
     */
    @JsonProperty(WINNERS_FIELD)
    private List<User> winners;
    /**
     * Optional.
     * The number of other chats the user had to join in order to be eligible for the giveaway
     */
    @JsonProperty(ADDITIONAL_CHAT_COUNT_FIELD)
    private Integer additionalChatCount;
    /**
     * Optional.
     * The number of months the Telegram Premium subscription won from the giveaway will be active for
     */
    @JsonProperty(PREMIUM_SUBSCRIPTION_MONTH_COUNT_FIELD)
    private Integer premiumSubscriptionMonthCount;
    /**
     * Optional.
     * Number of undistributed prizes
     */
    @JsonProperty(UNCLAIMED_PRIZE_COUNT_FIELD)
    private Integer unclaimedPrizeCount;
    /**
     * Optional.
     * True, if only users who had joined the chats after the giveaway started were eligible to win
     */
    @JsonProperty(ONLY_NEW_MEMBERS_FIELD)
    private Boolean onlyNewMembers;
    /**
     * Optional.
     * True, if the giveaway was canceled because the payment for it was refunded
     */
    @JsonProperty(WAS_REFUNDED_FIELD)
    private Boolean wasRefunded;
    /**
     * Optional.
     * Description of additional giveaway prize
     */
    @JsonProperty(PRIZE_DESCRIPTION_FIELD)
    private String prizeDescription;



}
