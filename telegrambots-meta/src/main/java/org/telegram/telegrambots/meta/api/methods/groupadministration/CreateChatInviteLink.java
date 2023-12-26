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
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.ChatInviteLink;
import org.telegram.telegrambots.meta.exceptions.TelegramApiRequestException;
import org.telegram.telegrambots.meta.exceptions.TelegramApiValidationException;

/**
 * @author Ruben Bermudez
 * @version 5.1
 *
 * Use this method to create an additional invite link for a chat.
 *
 * The bot must be an administrator in the chat for this to work and must have the appropriate admin rights.
 *
 * The link can be revoked using the method revokeChatInviteLink. Returns the new invite link as ChatInviteLink object.
 */
@EqualsAndHashCode(callSuper = false)
@Getter
@Setter
@ToString
@NoArgsConstructor(force = true)
@AllArgsConstructor
@RequiredArgsConstructor
@Builder
public class CreateChatInviteLink extends BotApiMethod<ChatInviteLink> {
    public static final String PATH = "createChatInviteLink";

    private static final String CHATID_FIELD = "chat_id";
    private static final String EXPIREDATE_FIELD = "expire_date";
    private static final String MEMBERLIMIT_FIELD = "member_limit";
    private static final String NAME_FIELD = "name";
    private static final String CREATESJOINREQUEST_FIELD = "creates_join_request";

    @JsonProperty(CHATID_FIELD)
    @NonNull
    private String chatId; ///< Unique identifier for the target chat or username of the target channel (in the format @channelusername)
    @JsonProperty(EXPIREDATE_FIELD)
    private Integer expireDate; ///< Optional. Point in time (Unix timestamp) when the link will expire
    /**
     * Optional.
     *
     * Maximum number of users that can be members of the chat simultaneously after joining the chat via this invite link; 1-99999
     */
    @JsonProperty(MEMBERLIMIT_FIELD)
    private Integer memberLimit; ///< Optional.	Invite link name; 0-32 characters
    @JsonProperty(NAME_FIELD)
    private String name; ///< Optional.	Invite link name; 0-32 characters
    /**
     * Optional.
     *
     * True, if users joining the chat via the link need to be approved by chat administrators.
     * If True, member_limit can't be specified
     */
    @JsonProperty(CREATESJOINREQUEST_FIELD)
    private Boolean createsJoinRequest;

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
        if (chatId.isEmpty()) {
            throw new TelegramApiValidationException("ChatId can't be empty", this);
        }
        if (name != null && name.length() > 32) {
            throw new TelegramApiValidationException("Name must be between 0 and 32 characters", this);
        }
        if (createsJoinRequest != null && memberLimit != null) {
            throw new TelegramApiValidationException("MemberLimit can not be used with CreatesJoinRequest field", this);
        }
        if (memberLimit != null && (memberLimit < 1 || memberLimit > 99999)) {
            throw new TelegramApiValidationException("MemberLimit must be between 1 and 99999", this);
        }
    }

    public static class CreateChatInviteLinkBuilder {

        @Tolerate
        public CreateChatInviteLinkBuilder chatId(@NonNull Long chatId) {
            this.chatId = chatId.toString();
            return this;
        }
    }
}
