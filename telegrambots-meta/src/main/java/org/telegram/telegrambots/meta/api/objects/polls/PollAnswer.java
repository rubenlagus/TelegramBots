package org.telegram.telegrambots.meta.api.objects.polls;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.telegram.telegrambots.meta.api.interfaces.BotApiObject;
import org.telegram.telegrambots.meta.api.objects.User;

import java.util.List;
import java.util.Objects;

/**
 * @author Ruben Bermudez
 * @version 4.7
 *
 * This object represents an answer of a user in a non-anonymous poll.
 */
public class PollAnswer implements BotApiObject {
    private static final String POLLID_FIELD = "poll_id";
    private static final String USER_FIELD = "user";
    private static final String OPTIONIDS_FIELD = "option_ids";

    @JsonProperty(POLLID_FIELD)
    private String pollId; ///< Unique poll identifier
    @JsonProperty(USER_FIELD)
    private User user; ///< The user, who changed the answer to the poll
    @JsonProperty(OPTIONIDS_FIELD)
    private List<Integer> optionIds; ///< 0-based identifiers of answer options, chosen by the user. May be empty if the user retracted their vote.

    public PollAnswer() {
    }

    public String getPollId() {
        return pollId;
    }

    public void setPollId(String pollId) {
        this.pollId = pollId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<Integer> getOptionIds() {
        return optionIds;
    }

    public void setOptionIds(List<Integer> optionIds) {
        this.optionIds = optionIds;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PollAnswer)) return false;
        PollAnswer that = (PollAnswer) o;
        return Objects.equals(pollId, that.pollId) &&
                Objects.equals(user, that.user) &&
                Objects.equals(optionIds, that.optionIds);
    }

    @Override
    public int hashCode() {
        return Objects.hash(pollId, user, optionIds);
    }

    @Override
    public String toString() {
        return "PollAnswer{" +
                "pollId='" + pollId + '\'' +
                ", user=" + user +
                ", optionIds=" + optionIds +
                '}';
    }
}
