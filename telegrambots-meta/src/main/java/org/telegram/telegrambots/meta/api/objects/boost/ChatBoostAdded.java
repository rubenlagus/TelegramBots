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
 * @version 7.1
 * This object represents a service message about a user boosting a chat.
 */
@EqualsAndHashCode(callSuper = false)
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Jacksonized
public class ChatBoostAdded implements BotApiObject {
    private static final String BOOST_COUNT_FIELD = "boost_count";

    /**
     * Number of boosts added by the user
     */
    @JsonProperty(BOOST_COUNT_FIELD)
    private Integer boostCount;
}
