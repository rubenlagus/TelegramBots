package org.telegram.telegrambots.meta.api.objects.polls;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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
import org.telegram.telegrambots.meta.api.objects.MessageEntity;

import java.util.List;

/**
 * @author Ruben Bermudez
 * @version 4.2
 *
 * This object contains information about one answer option in a poll.
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
public class PollOption implements BotApiObject {
    private static final String TEXT_FIELD = "text";
    private static final String VOTER_COUNT_FIELD = "voter_count";
    private static final String TEXT_ENTITIES_FIELD = "text_entities";

    /**
     * Option text, 1-100 characters
     */
    @JsonProperty(TEXT_FIELD)
    private String text;
    /**
     * Number of users that voted for this option
     */
    @JsonProperty(VOTER_COUNT_FIELD)
    private Integer voterCount;
    /**
     * Optional.
     * Special entities that appear in the option text.
     * Currently, only custom emoji entities are allowed in poll option texts
     */
    @JsonProperty(TEXT_ENTITIES_FIELD)
    private List<MessageEntity> textEntities;
}
