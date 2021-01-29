package org.telegram.telegrambots.meta.api.objects.polls;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.telegram.telegrambots.meta.api.interfaces.BotApiObject;
import org.telegram.telegrambots.meta.api.objects.MessageEntity;

import java.util.List;

/**
 * @author Ruben Bermudez
 * @version 4.2
 *
 * This object contains information about a poll.
 */
@EqualsAndHashCode(callSuper = false)
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Poll implements BotApiObject {
    private static final String ID_FIELD = "id";
    private static final String QUESTION_FIELD = "question";
    private static final String OPTIONS_FIELD = "options";
    private static final String TOTALVOTERCOUNT_FIELD = "total_voter_count";
    private static final String ISCLOSED_FIELD = "is_closed";
    private static final String ISANONYMOUS_FIELD = "is_anonymous";
    private static final String TYPE_FIELD = "type";
    private static final String ALLOWSMULTIPLEANSWERS_FIELD = "allows_multiple_answers";
    private static final String CORRECTOPTIONID_FIELD = "correct_option_id";
    private static final String OPENPERIOD_FIELD = "open_period";
    private static final String CLOSEDATE_FIELD = "close_date";
    private static final String EXPLANATION_FIELD = "explanation";
    private static final String EXPLANATIONENTITIES_FIELD = "explanation_entities";

    @JsonProperty(ID_FIELD)
    private String id; ///< Unique poll identifier
    @JsonProperty(QUESTION_FIELD)
    private String question; ///< Poll question, 1-255 characters
    @JsonProperty(OPTIONS_FIELD)
    private List<PollOption> options; ///< List of poll options
    @JsonProperty(TOTALVOTERCOUNT_FIELD)
    private Integer totalVoterCount; ///< Total number of users that voted in the poll
    @JsonProperty(ISCLOSED_FIELD)
    private Boolean isClosed; ///< True, if the poll is closed
    @JsonProperty(ISANONYMOUS_FIELD)
    private Boolean isAnonymous; ///< True, if the poll is closed
    @JsonProperty(TYPE_FIELD)
    private String type; ///< Poll type, currently can be “regular” or “quiz”
    @JsonProperty(ALLOWSMULTIPLEANSWERS_FIELD)
    private Boolean allowMultipleAnswers; ///< True, if the poll allows multiple answers
    /**
     * Optional. 0-based identifier of the correct answer option.
     *
     * @apiNote Available only for polls in the quiz mode,
     * which are closed or was sent (not forwarded) to the private chat with the bot.
     */
    @JsonProperty(CORRECTOPTIONID_FIELD)
    private Integer correctOptionId; ///< True, if the poll allows multiple answers
    @JsonProperty(OPENPERIOD_FIELD)
    private Integer openPeriod; ///< Optional. Amount of time in seconds the poll will be active after creation
    @JsonProperty(CLOSEDATE_FIELD)
    private Integer closeDate; ///< Optional. Point in time (Unix timestamp) when the poll will be automatically closed
    @JsonProperty(EXPLANATION_FIELD)
    private String explanation; ///< Optional. Text that is shown when a user chooses an incorrect answer or taps on the lamp icon in a quiz-style poll, 0-200 characters
    @JsonProperty(EXPLANATIONENTITIES_FIELD)
    private List<MessageEntity> explanationEntities; ///< Optional. Special entities like usernames, URLs, bot commands, etc. that appear in the explanation
}
