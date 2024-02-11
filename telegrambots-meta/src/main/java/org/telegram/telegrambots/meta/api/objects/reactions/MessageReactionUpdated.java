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
import org.telegram.telegrambots.meta.api.objects.User;

import java.util.List;

/**
 * @author Ruben Bermudez
 * @version 7.0
 * This object represents a change of a reaction on a message performed by a user.
 */
@JsonDeserialize
@EqualsAndHashCode(callSuper = false)
@Getter
@Setter
@ToString
@NoArgsConstructor(force = true)
@AllArgsConstructor
@Builder
public class MessageReactionUpdated implements BotApiObject {
    private static final String CHAT_FIELD = "chat";
    private static final String MESSAGE_ID_FIELD = "message_id";
    private static final String USER_FIELD = "user";
    private static final String ACTOR_CHAT_FIELD = "actor_chat";
    private static final String DATE_FIELD = "date";
    private static final String OLD_REACTION_FIELD = "old_reaction";
    private static final String NEW_REACTION_FIELD = "new_reaction";

    /**
     * The chat containing the message the user reacted to
     */
    @JsonProperty(CHAT_FIELD)
    private Chat chat;
    /**
     * Unique identifier of the message inside the chat
     */
    @JsonProperty(MESSAGE_ID_FIELD)
    private Integer messageId;
    /**
     * Optional.
     * The user that changed the reaction, if the user isn't anonymous
     */
    @JsonProperty(USER_FIELD)
    private User user;
    /**
     * Optional.
     * The chat on behalf of which the reaction was changed, if the user is anonymous
     */
    @JsonProperty(ACTOR_CHAT_FIELD)
    private Chat actorChat;
    /**
     * Date of the change in Unix time
     */
    @JsonProperty(DATE_FIELD)
    private Integer date;
    /**
     * Previous list of reaction types that were set by the user
     */
    @JsonProperty(OLD_REACTION_FIELD)
    private List<ReactionType> oldReaction;
    /**
     * New list of reaction types that have been set by the user
     */
    @JsonProperty(NEW_REACTION_FIELD)
    private List<ReactionType> newReaction;
}
