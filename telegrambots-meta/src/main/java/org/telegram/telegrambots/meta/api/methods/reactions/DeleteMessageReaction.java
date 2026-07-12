package org.telegram.telegrambots.meta.api.methods.reactions;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
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
import org.telegram.telegrambots.meta.exceptions.TelegramApiValidationException;
import org.telegram.telegrambots.meta.util.Validations;

/**
 * @author Ruben Bermudez
 * @version 10.0
 * Use this method to remove a reaction from a message in a group or a supergroup chat.
 * The bot must have the 'can_delete_messages' administrator right in the chat.
 * Returns True on success.
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
@JsonInclude(JsonInclude.Include.NON_NULL)
public class DeleteMessageReaction extends BotApiMethodBoolean {
    public static final String PATH = "deleteMessageReaction";

    private static final String CHAT_ID_FIELD = "chat_id";
    private static final String MESSAGE_ID_FIELD = "message_id";
    private static final String USER_ID_FIELD = "user_id";
    private static final String ACTOR_CHAT_ID_FIELD = "actor_chat_id";

    /**
     * Unique identifier for the target chat or username of the target supergroup (in the format @username)
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
     * Identifier of the user whose reaction will be removed, if the reaction was added by a user
     */
    @JsonProperty(USER_ID_FIELD)
    private Long userId;
    /**
     * Optional
     * Identifier of the chat whose reaction will be removed, if the reaction was added by a chat
     */
    @JsonProperty(ACTOR_CHAT_ID_FIELD)
    private Long actorChatId;

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
        Validations.requiredChatId(chatId, this);
        if (messageId == null) {
            throw new TelegramApiValidationException("MessageId can't be null", this);
        }
    }

    public static abstract class DeleteMessageReactionBuilder<C extends DeleteMessageReaction, B extends DeleteMessageReactionBuilder<C, B>> extends BotApiMethodBooleanBuilder<C, B> {
        @Tolerate
        public DeleteMessageReactionBuilder<C, B> chatId(@NonNull Long chatId) {
            this.chatId = chatId.toString();
            return this;
        }
    }
}
