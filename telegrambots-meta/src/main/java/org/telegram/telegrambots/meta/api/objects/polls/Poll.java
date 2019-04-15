package org.telegram.telegrambots.meta.api.objects.polls;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.telegram.telegrambots.meta.api.interfaces.BotApiObject;

import java.util.List;

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
    private static final String ISCLOSED_FIELD = "is_closed";

    @JsonProperty(ID_FIELD)
    private String id; ///< Unique poll identifier
    @JsonProperty(QUESTION_FIELD)
    private String question; ///< Poll question, 1-255 characters
    @JsonProperty(OPTIONS_FIELD)
    private List<PollOption> options; ///< List of poll options
    @JsonProperty(ISCLOSED_FIELD)
    private Boolean isClosed; ///< True, if the poll is closed

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

    @Override
    public String toString() {
        return "Poll{" +
                "id='" + id + '\'' +
                ", question='" + question + '\'' +
                ", options=" + options +
                ", isClosed=" + isClosed +
                '}';
    }
}
