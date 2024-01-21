package org.telegram.telegrambots.meta.api.methods.forum;

import com.fasterxml.jackson.annotation.JsonProperty;
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

/**
 * @author Ruben Bermudez
 * @version 6.3
 * Use this method to clear the list of pinned messages in a forum topic.
 * The bot must be an administrator in the chat for this to work and must
 * have the can_pin_messages administrator right in the supergroup.
 *
 * Returns True on success.
 */
@EqualsAndHashCode(callSuper = false)
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@SuperBuilder
@Jacksonized
public class UnpinAllForumTopicMessages extends BotApiMethodBoolean {
    public static final String PATH = "unpinAllForumTopicMessages";

    private static final String CHATID_FIELD = "chat_id";
    private static final String MESSAGETHREADID_FIELD = "message_thread_id";

    /**
     * Unique identifier for the target chat or username
     * of the target supergroup (in the format @supergroupusername)
     */
    @JsonProperty(CHATID_FIELD)
    @NonNull
    private String chatId;
    /**
     * Unique identifier for the target message thread of the forum topic
     */
    @JsonProperty(MESSAGETHREADID_FIELD)
    @NonNull
    private Integer messageThreadId;

    @Tolerate
    public void setChatId(@NonNull Long chatId) {
        this.chatId = chatId.toString();
    }

    @Override
    public void validate() throws TelegramApiValidationException {
        if (chatId.isEmpty()) {
            throw new TelegramApiValidationException("ChatId can't be empty", this);
        }
        if (messageThreadId <= 0) {
            throw new TelegramApiValidationException("Message Thread Id can't be empty", this);
        }
    }

    @Override
    public String getMethod() {
        return PATH;
    }

    public static abstract class UnpinAllForumTopicMessagesBuilder<C extends UnpinAllForumTopicMessages, B extends UnpinAllForumTopicMessagesBuilder<C, B>> extends BotApiMethodBooleanBuilder<C, B> {
        @Tolerate
        public UnpinAllForumTopicMessagesBuilder<C, B> chatId(@NonNull Long chatId) {
            this.chatId = chatId.toString();
            return this;
        }
    }
}
