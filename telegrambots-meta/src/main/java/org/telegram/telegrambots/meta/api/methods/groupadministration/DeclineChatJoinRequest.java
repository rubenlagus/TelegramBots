package org.telegram.telegrambots.meta.api.methods.groupadministration;

import com.fasterxml.jackson.annotation.JsonProperty;
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
import org.telegram.telegrambots.meta.exceptions.TelegramApiValidationException;

/**
 * @author Ruben Bermudez
 * @version 5.4
 * Use this method to decline a chat join request.
 * The bot must be an administrator in the chat for this to work and must have the can_invite_users admin right.
 * Returns True on success.
 */
@EqualsAndHashCode(callSuper = false)
@Getter
@Setter
@ToString
@NoArgsConstructor(force = true)
@RequiredArgsConstructor
@Builder
public class DeclineChatJoinRequest extends BotApiMethodBoolean {
    public static final String PATH = "declineChatJoinRequest";

    private static final String CHATID_FIELD = "chat_id";
    private static final String USERID_FIELD = "user_id";

    @JsonProperty(CHATID_FIELD)
    @NonNull
    private String chatId; ///< Required. Unique identifier for the target chat or username of the target channel (in the format @channelusername)
    @JsonProperty(USERID_FIELD)
    @NonNull
    private Long userId; ///< Required. Unique identifier of the target user

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
        if (userId == 0) {
            throw new TelegramApiValidationException("UserId can't be null or 0", this);
        }
    }

    public static class DeclineChatJoinRequestBuilder {

        @Tolerate
        public DeclineChatJoinRequestBuilder chatId(@NonNull Long chatId) {
            this.chatId = chatId.toString();
            return this;
        }
    }
}
