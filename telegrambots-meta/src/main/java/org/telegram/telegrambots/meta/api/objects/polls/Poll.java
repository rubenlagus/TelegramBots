package org.telegram.telegrambots.meta.api.objects.polls;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.telegram.telegrambots.meta.api.interfaces.BotApiObject;
import org.telegram.telegrambots.meta.api.objects.MessageEntity;

import java.util.List;
import java.util.Objects;

/**
 * @author Ruben Bermudez
 * @version 4.2
 *
 * This object contains information about a poll.
 */
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

    public Poll() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public List<PollOption> getOptions() {
        return options;
    }

    public void setOptions(List<PollOption> options) {
        this.options = options;
    }

    public Boolean getClosed() {
        return isClosed;
    }

    public void setClosed(Boolean closed) {
        isClosed = closed;
    }

    public Integer getTotalVoterCount() {
        return totalVoterCount;
    }

    public void setTotalVoterCount(Integer totalVoterCount) {
        this.totalVoterCount = totalVoterCount;
    }

    public Boolean getAnonymous() {
        return isAnonymous;
    }

    public void setAnonymous(Boolean anonymous) {
        isAnonymous = anonymous;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Boolean getAllowMultipleAnswers() {
        return allowMultipleAnswers;
    }

    public void setAllowMultipleAnswers(Boolean allowMultipleAnswers) {
        this.allowMultipleAnswers = allowMultipleAnswers;
    }

    public Integer getCorrectOptionId() {
        return correctOptionId;
    }

    public void setCorrectOptionId(Integer correctOptionId) {
        this.correctOptionId = correctOptionId;
    }

    public Integer getOpenPeriod() {
        return openPeriod;
    }

    public Integer getCloseDate() {
        return closeDate;
    }

    public String getExplanation() {
        return explanation;
    }

    public List<MessageEntity> getExplanationEntities() {
        return explanationEntities;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Poll)) return false;
        Poll poll = (Poll) o;
        return Objects.equals(id, poll.id) &&
                Objects.equals(question, poll.question) &&
                Objects.equals(options, poll.options) &&
                Objects.equals(totalVoterCount, poll.totalVoterCount) &&
                Objects.equals(isClosed, poll.isClosed) &&
                Objects.equals(isAnonymous, poll.isAnonymous) &&
                Objects.equals(type, poll.type) &&
                Objects.equals(allowMultipleAnswers, poll.allowMultipleAnswers) &&
                Objects.equals(correctOptionId, poll.correctOptionId) &&
                Objects.equals(openPeriod, poll.openPeriod) &&
                Objects.equals(closeDate, poll.closeDate) &&
                Objects.equals(explanation, poll.explanation) &&
                Objects.equals(explanationEntities, poll.explanationEntities);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, question, options, totalVoterCount, isClosed, isAnonymous, type, allowMultipleAnswers, correctOptionId, openPeriod, closeDate, explanation, explanationEntities);
    }

    @Override
    public String toString() {
        return "Poll{" +
                "id='" + id + '\'' +
                ", question='" + question + '\'' +
                ", options=" + options +
                ", totalVoterCount=" + totalVoterCount +
                ", isClosed=" + isClosed +
                ", isAnonymous=" + isAnonymous +
                ", type='" + type + '\'' +
                ", allowMultipleAnswers=" + allowMultipleAnswers +
                ", correctOptionId=" + correctOptionId +
                ", openPeriod=" + openPeriod +
                ", closeDate=" + closeDate +
                ", explanation='" + explanation + '\'' +
                ", explanationEntities=" + explanationEntities +
                '}';
    }
}
