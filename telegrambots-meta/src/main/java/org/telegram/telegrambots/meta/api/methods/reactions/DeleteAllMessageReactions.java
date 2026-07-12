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
 * Use this method to remove up to 10000 recent reactions in a group or a supergroup chat added by a given user or chat.
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
public class DeleteAllMessageReactions extends BotApiMethodBoolean {
    public static final String PATH = "deleteAllMessageReactions";

    private static final String CHAT_ID_FIELD = "chat_id";
    private static final String USER_ID_FIELD = "user_id";
    private static final String ACTOR_CHAT_ID_FIELD = "actor_chat_id";

    /**
     * Unique identifier for the target chat or username of the target supergroup (in the format @username)
     */
    @JsonProperty(CHAT_ID_FIELD)
    @NonNull
    private String chatId;
    /**
     * Optional
     * Identifier of the user whose reactions will be removed, if the reactions were added by a user
     */
    @JsonProperty(USER_ID_FIELD)
    private Long userId;
    /**
     * Optional
     * Identifier of the chat whose reactions will be removed, if the reactions were added by a chat
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
    }

    public static abstract class DeleteAllMessageReactionsBuilder<C extends DeleteAllMessageReactions, B extends DeleteAllMessageReactionsBuilder<C, B>> extends BotApiMethodBooleanBuilder<C, B> {
        @Tolerate
        public DeleteAllMessageReactionsBuilder<C, B> chatId(@NonNull Long chatId) {
            this.chatId = chatId.toString();
            return this;
        }
    }
}
