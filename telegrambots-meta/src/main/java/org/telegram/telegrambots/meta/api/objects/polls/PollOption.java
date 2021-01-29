package org.telegram.telegrambots.meta.api.objects.polls;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.telegram.telegrambots.meta.api.interfaces.BotApiObject;

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
@NoArgsConstructor
@AllArgsConstructor
public class PollOption implements BotApiObject {
    private static final String TEXT_FIELD = "text";
    private static final String VOTERCOUNT_FIELD = "voter_count";

    @JsonProperty(TEXT_FIELD)
    private String text; ///< Option text, 1-100 characters
    @JsonProperty(VOTERCOUNT_FIELD)
    private Integer voterCount; ///< Number of users that voted for this option
}
