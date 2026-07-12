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
import org.telegram.telegrambots.meta.api.objects.message.MaybeInaccessibleMessage;

import java.util.List;

/**
 * @author Ruben Bermudez
 * @version 9.6
 *
 * Describes a service message about an option added to a poll.
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
public class PollOptionAdded implements BotApiObject {
    private static final String POLL_MESSAGE_FIELD = "poll_message";
    private static final String OPTION_PERSISTENT_ID_FIELD = "option_persistent_id";
    private static final String OPTION_TEXT_FIELD = "option_text";
    private static final String OPTION_TEXT_ENTITIES_FIELD = "option_text_entities";

    /**
     * Optional.
     * Message containing the poll to which the option was added, if known.
     * Note that the Message object in this field will not contain the reply_to_message field even if it itself is a reply.
     */
    @JsonProperty(POLL_MESSAGE_FIELD)
    private MaybeInaccessibleMessage pollMessage;
    /**
     * Unique identifier of the added option
     */
    @JsonProperty(OPTION_PERSISTENT_ID_FIELD)
    private String optionPersistentId;
    /**
     * Option text
     */
    @JsonProperty(OPTION_TEXT_FIELD)
    private String optionText;
    /**
     * Optional.
     * Special entities that appear in the option_text
     */
    @JsonProperty(OPTION_TEXT_ENTITIES_FIELD)
    private List<MessageEntity> optionTextEntities;
}
