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
 * This object contains information about a poll.
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
public class Poll implements BotApiObject {
    private static final String ID_FIELD = "id";
    private static final String QUESTION_FIELD = "question";
    private static final String OPTIONS_FIELD = "options";
    private static final String TOTAL_VOTER_COUNT_FIELD = "total_voter_count";
    private static final String IS_CLOSED_FIELD = "is_closed";
    private static final String IS_ANONYMOUS_FIELD = "is_anonymous";
    private static final String TYPE_FIELD = "type";
    private static final String ALLOWS_MULTIPLE_ANSWERS_FIELD = "allows_multiple_answers";
    private static final String CORRECT_OPTION_IDS_FIELD = "correct_option_ids";
    private static final String ALLOWS_REVOTING_FIELD = "allows_revoting";
    private static final String OPEN_PERIOD_FIELD = "open_period";
    private static final String CLOSE_DATE_FIELD = "close_date";
    private static final String EXPLANATION_FIELD = "explanation";
    private static final String EXPLANATION_ENTITIES_FIELD = "explanation_entities";
    private static final String QUESTION_ENTITIES_FIELD = "question_entities";
    private static final String DESCRIPTION_FIELD = "description";
    private static final String DESCRIPTION_ENTITIES_FIELD = "description_entities";
    private static final String MEDIA_FIELD = "media";
    private static final String EXPLANATION_MEDIA_FIELD = "explanation_media";
    private static final String MEMBERS_ONLY_FIELD = "members_only";
    private static final String COUNTRY_CODES_FIELD = "country_codes";

    /**
     * Unique poll identifier
     */
    @JsonProperty(ID_FIELD)
    private String id;
    /**
     * Poll question, 1-255 characters
     */
    @JsonProperty(QUESTION_FIELD)
    private String question;
    /**
     * List of poll options
     */
    @JsonProperty(OPTIONS_FIELD)
    private List<PollOption> options;
    /**
     * Total number of users that voted in the poll
     */
    @JsonProperty(TOTAL_VOTER_COUNT_FIELD)
    private Integer totalVoterCount;
    /**
     * True, if the poll is closed
     */
    @JsonProperty(IS_CLOSED_FIELD)
    private Boolean isClosed;
    /**
     * True, if the poll is closed
     */
    @JsonProperty(IS_ANONYMOUS_FIELD)
    private Boolean isAnonymous;
    /**
     * Poll type, currently can be “regular” or “quiz”
     */
    @JsonProperty(TYPE_FIELD)
    private String type;
    /**
     * True, if the poll allows multiple answers
     */
    @JsonProperty(ALLOWS_MULTIPLE_ANSWERS_FIELD)
    private Boolean allowMultipleAnswers;
    /**
     * Optional. Array of 0-based identifiers of the correct answer options.
     *
     * @apiNote Available only for polls in the quiz mode,
     * which are closed or was sent (not forwarded) to the private chat with the bot.
     */
    @JsonProperty(CORRECT_OPTION_IDS_FIELD)
    private List<Integer> correctOptionIds;
    /**
     * Optional.
     * True, if the poll allows to change the chosen answer options
     */
    @JsonProperty(ALLOWS_REVOTING_FIELD)
    private Boolean allowsRevoting;
    /**
     * Optional.
     * Amount of time in seconds the poll will be active after creation
     */
    @JsonProperty(OPEN_PERIOD_FIELD)
    private Integer openPeriod;
    /**
     * Optional.
     * Point in time (Unix timestamp) when the poll will be automatically closed
     */
    @JsonProperty(CLOSE_DATE_FIELD)
    private Integer closeDate;
    /**
     * Optional.
     * Text that is shown when a user chooses an incorrect answer or taps on the lamp icon in a quiz-style poll, 0-200 characters
     */
    @JsonProperty(EXPLANATION_FIELD)
    private String explanation;
    /**
     * Optional.
     * Special entities like usernames, URLs, bot commands, etc. that appear in the explanation
     */
    @JsonProperty(EXPLANATION_ENTITIES_FIELD)
    private List<MessageEntity> explanationEntities;
    /**
     * Optional.
     * Special entities that appear in the question.
     * Currently, only custom emoji entities are allowed in poll questions
     */
    @JsonProperty(QUESTION_ENTITIES_FIELD)
    private List<MessageEntity> questionEntities;
    /**
     * Optional.
     * Description of the poll; for polls inside the Message object only
     */
    @JsonProperty(DESCRIPTION_FIELD)
    private String description;
    /**
     * Optional.
     * Special entities like usernames, URLs, bot commands, etc. that appear in the description
     */
    @JsonProperty(DESCRIPTION_ENTITIES_FIELD)
    private List<MessageEntity> descriptionEntities;
    /**
     * Optional. Media added to the poll description; for polls inside the Message object only
     */
    @JsonProperty(MEDIA_FIELD)
    private PollMedia media;
    /**
     * Optional. Media added to the quiz explanation
     */
    @JsonProperty(EXPLANATION_MEDIA_FIELD)
    private PollMedia explanationMedia;
    /**
     * Optional. True if voting is limited to users who have been members of the chat where the poll was originally sent for more than 24 hours
     */
    @JsonProperty(MEMBERS_ONLY_FIELD)
    private Boolean membersOnly;
    /**
     * Optional. A list of two-letter ISO 3166-1 alpha-2 country codes indicating the countries from which users can vote in the poll.
     */
    @JsonProperty(COUNTRY_CODES_FIELD)
    private List<String> countryCodes;
}
