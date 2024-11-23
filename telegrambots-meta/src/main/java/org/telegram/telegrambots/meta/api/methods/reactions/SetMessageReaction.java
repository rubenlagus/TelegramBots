package org.telegram.telegrambots.meta.api.methods.reactions;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import lombok.experimental.Tolerate;
import lombok.extern.jackson.Jacksonized;
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
 *
 * @apiNote Bots can't use paid reactions
 */
@SuppressWarnings("unused")
@EqualsAndHashCode(callSuper = false)
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Jacksonized
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
     * @apiNote Paid reactions can't be used by bots.
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
                if (ReactionType.PAID_TYPE.equals(reactionType.getType())) {
                    throw new TelegramApiValidationException("ReactionType can't be paid", this);
                }
                reactionType.validate();
            }
        }
    }

    public static abstract class SetMessageReactionBuilder<C extends SetMessageReaction, B extends SetMessageReactionBuilder<C, B>> extends BotApiMethodBooleanBuilder<C, B> {
        @Tolerate
        public SetMessageReactionBuilder<C, B> chatId(@NonNull Long chatId) {
            this.chatId = chatId.toString();
            return this;
        }
    }
}
