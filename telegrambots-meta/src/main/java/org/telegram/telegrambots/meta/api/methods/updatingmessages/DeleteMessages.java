package org.telegram.telegrambots.meta.api.methods.updatingmessages;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;
import lombok.Singular;
import lombok.ToString;
import lombok.experimental.Tolerate;
import org.telegram.telegrambots.meta.api.methods.botapimethods.BotApiMethodBoolean;
import org.telegram.telegrambots.meta.exceptions.TelegramApiValidationException;

import java.util.List;

/**
 * @author Ruben Bermudez
 * @version 7.0
 *
 * Use this method to delete multiple messages simultaneously.
 *
 * If some of the specified messages can't be found, they are skipped.
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
public class DeleteMessages extends BotApiMethodBoolean {
    private static final String PATH = "deleteMessages";

    private static final String CHAT_ID_FIELD = "chat_id";
    private static final String MESSAGE_IDS_FIELD = "message_ids";

    /**
     * Unique identifier for the target chat or username of the target channel
     * (in the format @channelusername)
     */
    @JsonProperty(CHAT_ID_FIELD)
    @NonNull
    private String chatId;
    /**
     * Identifiers of 1-100 messages to delete.
     * See deleteMessage for limitations on which messages can be deleted
     */
    @JsonProperty(MESSAGE_IDS_FIELD)
    @NonNull
    @Singular
    private List<Integer> messageIds;

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
        if (messageIds.isEmpty() || messageIds.size() > 100) {
            throw new TelegramApiValidationException("MessageIds parameter items count must be between 1 and 100", this);
        }
    }

    public static class DeleteMessagesBuilder {

        @Tolerate
        public DeleteMessagesBuilder chatId(@NonNull Long chatId) {
            this.chatId = chatId.toString();
            return this;
        }
    }
}
