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
 * @version 3.1
 * Use this method to promote or demote a user in a supergroup or a channel.
 * The bot must be an administrator in the chat for this to work and must have the appropriate admin rights.
 * Pass False for all boolean parameters to demote a user. Returns True on success.
 *
 */
@EqualsAndHashCode(callSuper = false)
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@NoArgsConstructor(force = true)
@AllArgsConstructor
@Builder
public class PromoteChatMember extends BotApiMethodBoolean {
    public static final String PATH = "promoteChatMember";

    private static final String CHATID_FIELD = "chat_id";
    private static final String USER_ID_FIELD = "user_id";
    private static final String CANCHANGEINFORMATION_FIELD = "can_change_info";
    private static final String CANPOSTMESSAGES_FIELD = "can_post_messages";
    private static final String CANEDITMESSAGES_FIELD = "can_edit_messages";
    private static final String CANDELETEMESSAGES_FIELD = "can_delete_messages";
    private static final String CANINVITEUSERS_FIELD = "can_invite_users";
    private static final String CANRESTRICTMEMBERS_FIELD = "can_restrict_members";
    private static final String CANPINMESSAGES_FIELD = "can_pin_messages";
    private static final String CANPROMOTEMEMBERS_FIELD = "can_promote_members";
    private static final String ISANONYMOUS_FIELD = "is_anonymous";
    private static final String CANMANAGECHAT_FIELD = "can_manage_chat";
    private static final String CANMANAGEVIDEOCHATS_FIELD = "can_manage_video_chats";
    private static final String CANMANAGETOPICS_FIELD = "can_manage_topics";

    @JsonProperty(CHATID_FIELD)
    @NonNull
    private String chatId; ///< Required. Unique identifier for the chat to send the message to (Or username for channels)
    @JsonProperty(USER_ID_FIELD)
    @NonNull
    private Long userId; ///< Required. Unique identifier of the target user
    @JsonProperty(CANCHANGEINFORMATION_FIELD)
    private Boolean canChangeInformation; ///< Optional. Pass True, if the administrator can change chat title, photo and other settings
    @JsonProperty(CANPOSTMESSAGES_FIELD)
    private Boolean canPostMessages; ///< Optional. Pass True, if the administrator can create channel posts, channels only
    @JsonProperty(CANEDITMESSAGES_FIELD)
    private Boolean canEditMessages; ///< Optional. Pass True, if the administrator can edit messages of other users, channels only
    @JsonProperty(CANDELETEMESSAGES_FIELD)
    private Boolean canDeleteMessages; ///< Optional. Pass True, if the administrator can delete messages of other users
    @JsonProperty(CANINVITEUSERS_FIELD)
    private Boolean canInviteUsers; ///< Optional. Pass True, if the administrator can invite new users to the chat
    @JsonProperty(CANRESTRICTMEMBERS_FIELD)
    private Boolean canRestrictMembers; ///< Optional. Pass True, if the administrator can restrict, ban or unban chat members
    @JsonProperty(CANPINMESSAGES_FIELD)
    private Boolean canPinMessages; ///< Optional. Pass True, if the administrator can pin messages
    @JsonProperty(CANPROMOTEMEMBERS_FIELD)
    private Boolean canPromoteMembers; ///< Optional. Pass True, if the administrator can add new administrators with a subset of his own privileges or demote administrators that he has promoted, directly or indirectly (promoted by administators that were appointed by the him)
    @JsonProperty(ISANONYMOUS_FIELD)
    private Boolean isAnonymous; ///< Optional. Pass True, if the administrator's presence in the chat is hidden
    /**
     * Optional
     *
     * Pass True, if the administrator can access the chat event log, chat statistics, message statistics in channels,
     * see channel members, see anonymous administrators in supergoups and ignore slow mode.
     *
     * Implied by any other administrator privilege
     */
    @JsonProperty(CANMANAGECHAT_FIELD)
    private Boolean canManageChat;
    /**
     * Optional
     * Pass True, if the administrator can manage video chats
     */
    @JsonProperty(CANMANAGEVIDEOCHATS_FIELD)
    private Boolean canManageVideoChats;
    /**
     * Optional.
     * True, if the user is allowed to create, rename, close, and reopen forum topics; supergroups only
     */
    @JsonProperty(CANMANAGETOPICS_FIELD)
    private Boolean canManageTopics;

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
        if (userId == 0L) {
            throw new TelegramApiValidationException("UserId can't be empty", this);
        }
    }

    public static class PromoteChatMemberBuilder {

        @Tolerate
        public PromoteChatMemberBuilder chatId(@NonNull Long chatId) {
            this.chatId = chatId.toString();
            return this;
        }

    }
}
