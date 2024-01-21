package org.telegram.telegrambots.meta.api.objects.giveaway;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.telegram.telegrambots.meta.api.interfaces.BotApiObject;
import org.telegram.telegrambots.meta.api.objects.Message;

/**
 * @author Ruben Bermudez
 * @version 7.0
 * This object represents a service message about the completion of a giveaway without public winners.
 */
@JsonDeserialize
@EqualsAndHashCode(callSuper = false)
@Getter
@Setter
@ToString
@NoArgsConstructor(force = true)
public class GiveawayCompleted implements BotApiObject {
    public static final String WINNER_COUNT_FIELD = "winner_count";
    public static final String UNCLAIMED_PRIZE_COUNT_FIELD = "unclaimed_prize_count";
    public static final String GIVEAWAY_MESSAGE_FIELD = "giveaway_message";

    /**
     * Number of winners in the giveaway
     */
    @JsonProperty(WINNER_COUNT_FIELD)
    private Integer winnerCount;
    /**
     * Optional.
     * Number of undistributed prizes
     */
    @JsonProperty(UNCLAIMED_PRIZE_COUNT_FIELD)
    private Integer unclaimedPrizeCount;
    /**
     * Optional.
     * Message with the giveaway that was completed, if it wasn't deleted
     */
    @JsonProperty(GIVEAWAY_MESSAGE_FIELD)
    private Message giveawayMessage;
}
