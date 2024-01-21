package org.telegram.telegrambots.meta.api.objects.commands.scope;

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
import org.telegram.telegrambots.meta.exceptions.TelegramApiValidationException;

/**
 * @author Ruben Bermudez
 * @version 5.3
 *
 * Represents the scope of bot commands, covering a specific chat.
 */
@EqualsAndHashCode(callSuper = false)
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@SuperBuilder
@Jacksonized
public class BotCommandScopeChat implements BotCommandScope {
    private static final String TYPE_FIELD = "type";
    private static final String CHATID_FIELD = "chat_id";

    /**
     * Scope type, must be chat
     */
    @JsonProperty(TYPE_FIELD)
    private final String type = "chat";
    /**
     * Unique identifier for the target chat or username of the target supergroup (in the format @supergroupusername)
     */
    @JsonProperty(CHATID_FIELD)
    @NonNull
    private String chatId;

    @Tolerate
    public void setChatId(@NonNull Long chatId) {
        this.chatId = chatId.toString();
    }

    @Override
    public void validate() throws TelegramApiValidationException {
        if (chatId.isEmpty()) {
            throw new TelegramApiValidationException("ChatId parameter can't be empty", this);
        }
    }

    public abstract static class BotCommandScopeChatBuilder<C extends BotCommandScopeChat, B extends BotCommandScopeChat.BotCommandScopeChatBuilder<C, B>> {
        @Tolerate
        public BotCommandScopeChatBuilder<C, B> chatId(@NonNull Long chatId) {
            this.chatId = chatId.toString();
            return this;
        }
    }
}
