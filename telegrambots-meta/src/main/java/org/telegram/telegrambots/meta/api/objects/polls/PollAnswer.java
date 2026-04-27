package org.telegram.telegrambots.meta.api.objects.polls;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.telegram.telegrambots.meta.api.interfaces.BotApiObject;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.api.objects.chat.Chat;

import java.util.List;

/**
 * @author Ruben Bermudez
 * @version 4.7
 *
 * This object represents an answer of a user in a non-anonymous poll.
 */
@EqualsAndHashCode(callSuper = false)
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class PollAnswer implements BotApiObject {
    private static final String POLL_ID_FIELD = "poll_id";
    private static final String USER_FIELD = "user";
    private static final String OPTION_IDS_FIELD = "option_ids";
    private static final String VOTER_CHAT_FIELD = "voter_chat";
    private static final String OPTION_PERSISTENT_IDS_FIELD = "option_persistent_ids";

    /**
     * Unique poll identifier
     */
    @JsonProperty(POLL_ID_FIELD)
    private String pollId;
    /**
     * The user, who changed the answer to the poll
     */
    @JsonProperty(USER_FIELD)
    private User user;
    /**
     * Optional.
     * The chat that changed the answer to the poll, if the voter is anonymous
     */
    @JsonProperty(OPTION_IDS_FIELD)
    private List<Integer> optionIds;
    /**
     * Optional.
     * The chat that changed the answer to the poll, if the voter is anonymous
     */
    @JsonProperty(VOTER_CHAT_FIELD)
    private Chat voterChat;
    /**
     * Persistent identifiers of the chosen answer options.
     * May be empty if the vote was retracted.
     */
    @JsonProperty(OPTION_PERSISTENT_IDS_FIELD)
    private List<String> optionPersistentIds;
}
