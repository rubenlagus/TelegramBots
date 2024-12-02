package org.telegram.telegrambots.meta.api.objects.boost;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
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
 * @version 7.0
 * The boost was obtained by the creation of a Telegram Premium or a Telegram Star giveaway.
 * This boosts the chat 4 times for the duration of the corresponding Telegram Premium subscription for
 * Telegram Premium giveaways and prize_star_count / 500 times for one year for Telegram Star giveaways.
 */
@EqualsAndHashCode(callSuper = false)
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Jacksonized
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ChatBoostSourceGiveaway implements ChatBoostSource {
    private static final String SOURCE_FIELD = "source";
    private static final String GIVEAWAY_MESSAGE_ID_FIELD = "giveaway_message_id";
    private static final String USER_FIELD = "user";
    private static final String IS_UNCLAIMED_FIELD = "is_unclaimed";
    private static final String PRIZE_STAR_COUNT_FIELD = "prize_star_count";

    /**
     * Source of the boost, always “giveaway”
     */
    @JsonProperty(SOURCE_FIELD)
    @Builder.Default
    private final String source = ChatBoostSource.GIVEAWAY_TYPE;
    /**
     * Identifier of a message in the chat with the giveaway; the message could have been deleted already.
     * May be 0 if the message isn't sent yet.
     */
    @JsonProperty(GIVEAWAY_MESSAGE_ID_FIELD)
    private Integer giveawayMessageId;
    /**
     * Optional.
     * User that won the prize in the giveaway if any; for Telegram Premium giveaways only
     */
    @JsonProperty(USER_FIELD)
    private User user;
    /**
     * Optional.
     * True, if the giveaway was completed, but there was no user to win the prize
     */
    @JsonProperty(IS_UNCLAIMED_FIELD)
    private Boolean isUnclaimed;
    /**
     * Optional.
     * The number of Telegram Stars to be split between giveaway winners; for Telegram Star giveaways only
     */
    @JsonProperty(PRIZE_STAR_COUNT_FIELD)
    private Integer prizeStarCount;
}
