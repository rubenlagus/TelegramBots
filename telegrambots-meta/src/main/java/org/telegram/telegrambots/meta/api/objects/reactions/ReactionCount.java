package org.telegram.telegrambots.meta.api.objects.reactions;

import com.fasterxml.jackson.annotation.JsonProperty;
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
 * Represents a reaction added to a message along with the number of times it was added.
 */

@EqualsAndHashCode(callSuper = false)
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@SuperBuilder
@Jacksonized
public class ReactionCount implements BotApiObject {
    private static final String TYPE_FIELD = "type";
    private static final String TOTAL_COUNT_FIELD = "total_count";

    /**
     * Type of the reaction
     */
    @JsonProperty(TYPE_FIELD)
    private ReactionType type;
    /**
     * Number of times the reaction was added
     */
    @JsonProperty(TOTAL_COUNT_FIELD)
    private Integer totalCount;
}
