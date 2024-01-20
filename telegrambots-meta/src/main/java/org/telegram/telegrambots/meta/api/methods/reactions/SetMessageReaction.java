package org.telegram.telegrambots.meta.api.methods.reactions;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Tolerate;
import org.telegram.telegrambots.meta.api.methods.botapimethods.BotApiMethodBoolean;
import org.telegram.telegrambots.meta.api.objects.reactions.ReactionType;
import org.telegram.telegrambots.meta.exceptions.TelegramApiValidationException;

import java.util.List;

/**
 * @author Ruben Bermudez
 * @version 1.0
 * Use this method to change the chosen reactions on a message.
 * Service messages can't be reacted to.
 * Automatically forwarded messages from a channel to its discussion group have the same available reactions as messages
 * in the channel.
 * In albums, bots must react to the first message.
 *
 * Returns True on success.
 */
@SuppressWarnings("unused")
@EqualsAndHashCode(callSuper = false)
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@NoArgsConstructor(force = true)
@AllArgsConstructor
@Builder
public class SetMessageReaction extends BotApiMethodBoolean {
    public static final String PATH = "setMessageReaction";

    private static final String CHAT_ID_FIELD = "chat_id";
    private static final String MESSAGE_ID_FIELD = "message_id";
    private static final String REACTION_FIELD = "reaction";
    private static final String IS_BIG_FIELD = "is_big";

    /**
     * Unique identifier for the target chat or username of the target channel (in the format @channelusername)
     */
    @JsonProperty(CHAT_ID_FIELD)
    @NonNull
    private String chatId;
    /**
     * Identifier of the target message
     */
    @JsonProperty(MESSAGE_ID_FIELD)
    @NonNull
    private Integer messageId;
    /**
     * Optional
     * New list of reaction types to set on the message.
     * Currently, as non-premium users, bots can set up to one reaction per message.
     * A custom emoji reaction can be used if it is either already present on the message or explicitly allowed by chat administrators.
     */
    @JsonProperty(REACTION_FIELD)
    private List<ReactionType> reactionTypes;
    /**
     * Optional
     * Pass True to set the reaction with a big animation
     */
    @JsonProperty(IS_BIG_FIELD)
    private Boolean isBig;


    @Tolerate
    public void setChatId(@NonNull Long chatId) {
        this.chatId = chatId.toString();
    }

    @Override
    public String getMethod() {
        return PATH;
    }

    @Override
    public void validate() throws TelegramApiValidationException {
        if (chatId.isEmpty()) {
            throw new TelegramApiValidationException("ChatId parameter can't be empty", this);
        }
        if (reactionTypes != null) {
            for (ReactionType reactionType : reactionTypes) {
                reactionType.validate();
            }
        }
    }

    public static class SetMessageReactionBuilder {
        @Tolerate
        public SetMessageReactionBuilder chatId(@NonNull Long chatId) {
            this.chatId = chatId.toString();
            return this;
        }
    }
}
