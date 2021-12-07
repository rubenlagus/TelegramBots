package org.telegram.telegrambots.meta.api.objects.commands.scope;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;
import org.telegram.telegrambots.meta.exceptions.TelegramApiValidationException;

/**
 * @author Ruben Bermudez
 * @version 5.3
 *
 * Represents the scope of bot commands, covering a specific member of a group or supergroup chat.
 */
@JsonDeserialize
@EqualsAndHashCode(callSuper = false)
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BotCommandScopeChatMember implements BotCommandScope {
    private static final String TYPE_FIELD = "type";
    private static final String CHATID_FIELD = "chat_id";
    private static final String USERID_FIELD = "user_id";

    /**
     * Scope type, must be chat
     */
    @JsonProperty(TYPE_FIELD)
    private final String type = "chat_member";
    /**
     * Unique identifier for the target chat or username of the target supergroup (in the format @supergroupusername)
     */
    @JsonProperty(CHATID_FIELD)
    @NonNull
    private String chatId;
    /**
     * Unique identifier of the target user
     */
    @JsonProperty(USERID_FIELD)
    @NonNull
    private Long userId;

    @Override
    public void validate() throws TelegramApiValidationException {
        if (chatId == null || chatId.isEmpty()) {
            throw new TelegramApiValidationException("ChatId parameter can't be empty", this);
        }
        if (userId == null || userId == 0L) {
            throw new TelegramApiValidationException("UserId parameter can't be empty", this);
        }
    }
}
