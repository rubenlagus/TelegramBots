package org.telegram.telegrambots.meta.api.objects.reactions;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.telegram.telegrambots.meta.api.interfaces.BotApiObject;
import org.telegram.telegrambots.meta.api.objects.Chat;

import java.util.List;

/**
 * @author Ruben Bermudez
 * @version 7.0
 * This object represents reaction changes on a message with anonymous reactions.
 */
@JsonDeserialize
@EqualsAndHashCode(callSuper = false)
@Getter
@Setter
@ToString
@NoArgsConstructor(force = true)
@AllArgsConstructor
@Builder
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
