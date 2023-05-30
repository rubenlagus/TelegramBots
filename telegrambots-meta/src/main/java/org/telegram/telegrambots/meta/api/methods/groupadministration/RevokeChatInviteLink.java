package org.telegram.telegrambots.meta.api.methods.groupadministration;

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
import org.apache.commons.lang3.StringUtils;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.ChatInviteLink;
import org.telegram.telegrambots.meta.exceptions.TelegramApiRequestException;
import org.telegram.telegrambots.meta.exceptions.TelegramApiValidationException;

/**
 * @author Ruben Bermudez
 * @version 5.1
 *
 * Use this method to revoke an invite link created by the bot.
 *
 * If the primary link is revoked, a new link is automatically generated.
 *
 * The bot must be an administrator in the chat for this to work and must have the appropriate admin rights.
 *
 * Returns the revoked invite link as ChatInviteLink object.
 */
@EqualsAndHashCode(callSuper = false)
@Getter
@Setter
@ToString
@NoArgsConstructor(force = true)
@AllArgsConstructor
@Builder
public class RevokeChatInviteLink extends BotApiMethod<ChatInviteLink> {
    public static final String PATH = "revokeChatInviteLink";

    private static final String CHATID_FIELD = "chat_id";
    private static final String INVITELINK_FIELD = "invite_link";

    @JsonProperty(CHATID_FIELD)
    @NonNull
    private String chatId; ///< Unique identifier for the target chat or username of the target channel (in the format @channelusername)
    @JsonProperty(INVITELINK_FIELD)
    @NonNull
    private String inviteLink; ///< The invite link to revoke

    @Tolerate
    public void setChatId(@NonNull Long chatId) {
        this.chatId = chatId.toString();
    }

    @Override
    public String getMethod() {
        return PATH;
    }

    @Override
    public ChatInviteLink deserializeResponse(String answer) throws TelegramApiRequestException {
        return deserializeResponse(answer, ChatInviteLink.class);
    }

    @Override
    public void validate() throws TelegramApiValidationException {
        if (StringUtils.isEmpty(chatId)) {
            throw new TelegramApiValidationException("ChatId can't be empty", this);
        }
        if (StringUtils.isEmpty(inviteLink)) {
            throw new TelegramApiValidationException("InviteLink can't be empty", this);
        }
    }

    public static class RevokeChatInviteLinkBuilder {

        @Tolerate
        public RevokeChatInviteLinkBuilder chatId(@NonNull Long chatId) {
            this.chatId = chatId.toString();
            return this;
        }
    }
}
