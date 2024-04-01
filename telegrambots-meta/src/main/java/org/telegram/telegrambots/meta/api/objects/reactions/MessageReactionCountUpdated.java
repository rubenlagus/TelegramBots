package org.telegram.telegrambots.meta.api.objects.reactions;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import lombok.extern.jackson.Jacksonized;
import org.telegram.telegrambots.meta.api.interfaces.BotApiObject;
import org.telegram.telegrambots.meta.api.objects.Chat;

import java.util.List;

/**
 * @author Ruben Bermudez
 * @version 7.0
 * This object represents reaction changes on a message with anonymous reactions.
 */

@EqualsAndHashCode(callSuper = false)
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@SuperBuilder
@Jacksonized
@JsonIgnoreProperties(ignoreUnknown = true)
public class MessageReactionCountUpdated implements BotApiObject {
    private static final String CHAT_FIELD = "chat";
    private static final String MESSAGE_ID_FIELD = "message_id";
    private static final String DATE_FIELD = "date";
    private static final String REACTIONS_FIELD = "reactions";

    /**
     * The chat containing the message
     */
    @JsonProperty(CHAT_FIELD)
    private Chat chat;
    /**
     * Unique message identifier inside the chat
     */
    @JsonProperty(MESSAGE_ID_FIELD)
    private Integer messageId;
    /**
     * Date of the change in Unix time
     */
    @JsonProperty(DATE_FIELD)
    private Integer date;
    /**
     * List of reactions that are present on the message
     */
    @JsonProperty(REACTIONS_FIELD)
    private List<ReactionCount> reactions;
}
