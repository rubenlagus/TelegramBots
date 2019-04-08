package org.telegram.telegrambots.meta.api.objects.polls;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.telegram.telegrambots.meta.api.interfaces.BotApiObject;

/**
 * @author Ruben Bermudez
 * @version 4.2
 *
 * This object contains information about one answer option in a poll.
 */
public class PollOption implements BotApiObject {
    private static final String TEXT_FIELD = "text";
    private static final String VOTERCOUNT_FIELD = "voter_count";

    @JsonProperty(TEXT_FIELD)
    private String text; ///< Option text, 1-100 characters
    @JsonProperty(VOTERCOUNT_FIELD)
    private Integer voterCount; ///< Number of users that voted for this option

    public PollOption() {
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Integer getVoterCount() {
        return voterCount;
    }

    public void setVoterCount(Integer voterCount) {
        this.voterCount = voterCount;
    }

    @Override
    public String toString() {
        return "PollOption{" +
                "text='" + text + '\'' +
                ", voterCount=" + voterCount +
                '}';
    }
}
