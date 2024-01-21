package org.telegram.telegrambots.meta.api.methods.boosts;

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
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.boost.UserChatBoosts;
import org.telegram.telegrambots.meta.exceptions.TelegramApiRequestException;
import org.telegram.telegrambots.meta.exceptions.TelegramApiValidationException;

/**
 * @author Ruben Bermudez
 * @version 7.0
 * Use this method to get the list of boosts added to a chat by a user. Requires administrator rights in the chat.
 * Returns a UserChatBoosts object.
 */
@EqualsAndHashCode(callSuper = false)
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GetUserChatBoosts extends BotApiMethod<UserChatBoosts> {
    public static final String PATH = "getUserChatBoosts";

    private static final String CHAT_ID_FIELD = "chat_id";
    private static final String USER_ID_FIELD = "user_id";

    /**
     * Unique identifier for the chat or username of the channel (in the format @channelusername)
     */
    @NonNull
    @JsonProperty(CHAT_ID_FIELD)
    private String chatId;
    /**
     * Unique identifier of the target user
     */
    @NonNull
    @JsonProperty(USER_ID_FIELD)
    private Long userId;

    @Override
    public String getMethod() {
        return PATH;
    }

    @Override
    public UserChatBoosts deserializeResponse(String answer) throws TelegramApiRequestException {
        return deserializeResponse(answer, UserChatBoosts.class);
    }

    @Override
    public void validate() throws TelegramApiValidationException {
        if (chatId.isEmpty()) {
            throw new TelegramApiValidationException("ChatId parameter can't be empty string", this);
        }
        if (userId <= 0) {
            throw new TelegramApiValidationException("userId can't be empty", this);
        }
    }

    public static class GetUserChatBoostsBuilder {

        @Tolerate
        public GetUserChatBoosts.GetUserChatBoostsBuilder chatId(@NonNull Long chatId) {
            this.chatId = chatId.toString();
            return this;
        }
    }
}
