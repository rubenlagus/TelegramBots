package org.telegram.telegrambots.meta.api.methods.forum;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Tolerate;
import org.telegram.telegrambots.meta.api.methods.botapimethods.BotApiMethodBoolean;
import org.telegram.telegrambots.meta.exceptions.TelegramApiValidationException;

/**
 * @author Ruben Bermudez
 * @version 6.8
 * Use this method to clear the list of pinned messages in a General forum topic.
 * The bot must be an administrator in the chat for this to work and must have the
 * can_pin_messages administrator right in the supergroup.
 *
 * Returns True on success.
 */
@EqualsAndHashCode(callSuper = false)
@Getter
@Setter
@ToString
@NoArgsConstructor(force = true)
@AllArgsConstructor
@Builder
public class UnpinAllGeneralForumTopicMessages extends BotApiMethodBoolean {
    public static final String PATH = "unpinAllGeneralForumTopicMessages";

    private static final String CHAT_ID_FIELD = "chat_id";

    /**
     * Unique identifier for the target chat or username
     * of the target supergroup (in the format @supergroupusername)
     */
    @JsonProperty(CHAT_ID_FIELD)
    @NonNull
    private String chatId;

    @Tolerate
    public void setChatId(@NonNull Long chatId) {
        this.chatId = chatId.toString();
    }

    @Override
    public void validate() throws TelegramApiValidationException {
        if (chatId.isEmpty()) {
            throw new TelegramApiValidationException("ChatId can't be empty", this);
        }
    }

    @Override
    public String getMethod() {
        return PATH;
    }

    public static class UnpinAllGeneralForumTopicMessagesBuilder {

        @Tolerate
        public UnpinAllGeneralForumTopicMessagesBuilder chatId(@NonNull Long chatId) {
            this.chatId = chatId.toString();
            return this;
        }
    }
}
