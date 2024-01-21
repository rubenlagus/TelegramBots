package org.telegram.telegrambots.meta.api.methods.groupadministration;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
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
 * @version 5.5
 * Use this method to unban a previously banned channel chat in a supergroup or channel.
 * The bot must be an administrator for this to work and must have the appropriate administrator rights.
 * Returns True on success.
 *
 */
@EqualsAndHashCode(callSuper = false)
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@SuperBuilder
@Jacksonized
public class UnbanChatSenderChat extends BotApiMethodBoolean {
    public static final String PATH = "unbanChatSenderChat";

    private static final String CHATID_FIELD = "chat_id";
    private static final String SENDER_CHAT_ID_FIELD = "sender_chat_id";

    @JsonProperty(CHATID_FIELD)
    @NonNull
    private String chatId; ///< Required. Unique identifier for the chat to send the message to (Or username for channels)
    @JsonProperty(SENDER_CHAT_ID_FIELD)
    @NonNull
    private Long senderChatId; ///< Required. Unique identifier of the target sender chat

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
            throw new TelegramApiValidationException("ChatId can't be empty", this);
        }
        if (senderChatId == 0) {
            throw new TelegramApiValidationException("SenderChatId can't be null or 0", this);
        }
    }

    public static abstract class UnbanChatSenderChatBuilder<C extends UnbanChatSenderChat, B extends UnbanChatSenderChatBuilder<C, B>> extends BotApiMethodBooleanBuilder<C, B> {
        @Tolerate
        public UnbanChatSenderChatBuilder<C, B> chatId(@NonNull Long chatId) {
            this.chatId = chatId.toString();
            return this;
        }
    }
}
