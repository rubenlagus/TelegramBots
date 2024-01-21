package org.telegram.telegrambots.meta.api.objects.boost;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.telegram.telegrambots.meta.api.objects.User;

/**
 * @author Ruben Bermudez
 * @version 7.0
 * The boost was obtained by the creation of a Telegram Premium giveaway.
 * This boosts the chat 4 times for the duration of the corresponding Telegram Premium subscription.
 */
@JsonDeserialize
@EqualsAndHashCode(callSuper = false)
@Getter
@Setter
@ToString
@NoArgsConstructor(force = true)
@AllArgsConstructor
@Builder
public class ChatBoostSourceGiveaway implements ChatBoostSource {
    private static final String SOURCE_FIELD = "source";
    private static final String GIVEAWAY_MESSAGE_ID_FIELD = "giveaway_message_id";
    private static final String USER_FIELD = "user";
    private static final String IS_UNCLAIMED_FIELD = "is_unclaimed";

    /**
     * Source of the boost, always “giveaway”
     */
    @JsonProperty(SOURCE_FIELD)
    private String source;
    /**
     * Identifier of a message in the chat with the giveaway; the message could have been deleted already.
     * May be 0 if the message isn't sent yet.
     */
    @JsonProperty(GIVEAWAY_MESSAGE_ID_FIELD)
    private Integer giveawayMessageId;
    /**
     * Optional.
     * User that won the prize in the giveaway if any
     */
    @JsonProperty(USER_FIELD)
    private User user;
    /**
     * Optional.
     * True, if the giveaway was completed, but there was no user to win the prize
     */
    @JsonProperty(IS_UNCLAIMED_FIELD)
    private Boolean isUnclaimed;
}
