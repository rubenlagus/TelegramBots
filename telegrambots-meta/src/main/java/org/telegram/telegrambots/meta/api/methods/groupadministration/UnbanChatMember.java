package org.telegram.telegrambots.meta.api.methods.groupadministration;

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
import org.telegram.telegrambots.meta.exceptions.TelegramApiValidationException;

/**
 * @author Ruben Bermudez
 * @version 1.0
 * Use this method to unban a previously kicked user in a supergroup or channel.
 * Returns True on success.
 *
 * @apiNote The user will not return to the group or channel automatically, but will be able to join via link, etc.
 * @apiNote The bot must be an administrator for this to work.
 * @apiNote By default, this method guarantees that after the call the user is not a member of the chat,
 * but will be able to join it.
 * @apiNote So if the user is a member of the chat they will also be removed from the chat. If you don't want this, use the parameter only_if_banned.
 */
@EqualsAndHashCode(callSuper = false)
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@NoArgsConstructor(force = true)
@AllArgsConstructor
@Builder
public class UnbanChatMember extends BotApiMethodBoolean {
    public static final String PATH = "unbanchatmember";

    private static final String CHATID_FIELD = "chat_id";
    private static final String USERID_FIELD = "user_id";
    private static final String ONLYISBANNED_FIELD = "only_if_banned";

    @JsonProperty(CHATID_FIELD)
    @NonNull
    private String chatId; ///< Required. Unique identifier for the chat to send the message to (Or username for channels)
    @JsonProperty(USERID_FIELD)
    @NonNull
    private Long userId; ///< Required. Unique identifier of the target user
    @JsonProperty(ONLYISBANNED_FIELD)
    private Boolean onlyIfBanned; ///< Optional. Do nothing if the user is not banned

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
    }

    public static class UnbanChatMemberBuilder {

        @Tolerate
        public UnbanChatMemberBuilder chatId(@NonNull Long chatId) {
            this.chatId = chatId.toString();
            return this;
        }
    }
}
