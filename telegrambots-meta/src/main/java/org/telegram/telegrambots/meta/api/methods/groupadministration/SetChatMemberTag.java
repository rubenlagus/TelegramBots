package org.telegram.telegrambots.meta.api.methods.groupadministration;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
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
import org.telegram.telegrambots.meta.util.Validations;

/**
 * @author Ruben Bermudez
 * @version 9.5
 * Use this method to set a tag for a regular member in a group or a supergroup.
 * The bot must be an administrator in the chat for this to work and must have the "can_manage_tags" administrator right.
 * Returns True on success.
 */
@EqualsAndHashCode(callSuper = false)
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@SuperBuilder
@Jacksonized
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SetChatMemberTag extends BotApiMethodBoolean {
    public static final String PATH = "setChatMemberTag";

    private static final String CHATID_FIELD = "chat_id";
    private static final String USERID_FIELD = "user_id";
    private static final String TAG_FIELD = "tag";

    @JsonProperty(CHATID_FIELD)
    @NonNull
    private String chatId; ///< Unique identifier for the target chat or username of the target supergroup (in the format @supergroupusername)
    @JsonProperty(USERID_FIELD)
    @NonNull
    private Long userId; ///< Unique identifier of the target user
    @JsonProperty(TAG_FIELD)
    private String tag; ///< Optional. New tag for the member; 0-16 characters, emoji are not allowed

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
        Validations.requiredUserId(userId, this);
    }

    public static abstract class SetChatMemberTagBuilder<C extends SetChatMemberTag, B extends SetChatMemberTagBuilder<C, B>> extends BotApiMethodBooleanBuilder<C, B> {
        @Tolerate
        public SetChatMemberTagBuilder<C, B> chatId(@NonNull Long chatId) {
            this.chatId = chatId.toString();
            return this;
        }
    }
}
