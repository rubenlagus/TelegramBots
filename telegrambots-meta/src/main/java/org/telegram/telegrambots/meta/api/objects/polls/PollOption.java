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
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.api.objects.chat.Chat;

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
    private static final String PERSISTENT_ID_FIELD = "persistent_id";
    private static final String ADDED_BY_USER_FIELD = "added_by_user";
    private static final String ADDED_BY_CHAT_FIELD = "added_by_chat";
    private static final String ADDITION_DATE_FIELD = "addition_date";

    /**
     * Option text, 1-100 characters
     */
    @JsonProperty(TEXT_FIELD)
    private String text;
    /**
     * Number of users that voted for this option; may be 0 if unknown
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
    /**
     * Unique identifier of the option, persistent on option addition and deletion
     */
    @JsonProperty(PERSISTENT_ID_FIELD)
    private String persistentId;
    /**
     * Optional.
     * User who added the option; omitted if the option wasn't added by a user after poll creation
     */
    @JsonProperty(ADDED_BY_USER_FIELD)
    private User addedByUser;
    /**
     * Optional.
     * Chat that added the option; omitted if the option wasn't added by a chat after poll creation
     */
    @JsonProperty(ADDED_BY_CHAT_FIELD)
    private Chat addedByChat;
    /**
     * Optional.
     * Point in time (Unix timestamp) when the option was added; omitted if the option existed in the original poll
     */
    @JsonProperty(ADDITION_DATE_FIELD)
    private Integer additionDate;
}
