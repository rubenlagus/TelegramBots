package org.telegram.telegrambots.meta.api.objects.boost;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import lombok.extern.jackson.Jacksonized;
import org.telegram.telegrambots.meta.api.interfaces.BotApiObject;

/**
 * @author Ruben Bermudez
 * @version 7.0
 * This object contains information about a chat boost.
 */
@EqualsAndHashCode(callSuper = false)
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Jacksonized
public class ChatBoost implements BotApiObject {
    private static final String BOOST_ID_FIELD = "boost_id";
    private static final String ADD_DATE_FIELD = "add_date";
    private static final String EXPIRATION_DATE_FIELD = "expiration_date";
    private static final String SOURCE_FIELD = "source";

    /**
     * Unique identifier of the boost
     */
    @JsonProperty(BOOST_ID_FIELD)
    private String boostId;
    /**
     * Point in time (Unix timestamp) when the chat was boosted
     */
    @JsonProperty(ADD_DATE_FIELD)
    private Integer addDate;
    /**
     * Point in time (Unix timestamp) when the boost will automatically expire,
     * unless the booster's Telegram Premium subscription is prolonged
     */
    @JsonProperty(EXPIRATION_DATE_FIELD)
    private Integer expirationDate;
    /**
     * Source of the added boost
     */
    @JsonProperty(SOURCE_FIELD)
    private ChatBoostSource source;
}
