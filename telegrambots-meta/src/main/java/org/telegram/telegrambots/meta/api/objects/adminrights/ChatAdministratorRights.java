package org.telegram.telegrambots.meta.api.objects.adminrights;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.telegram.telegrambots.meta.api.interfaces.BotApiObject;

/**
 * Represents rights of an administrator in a chat.
 * @author Ruben Bermudez
 * @version 6.0
 */
@EqualsAndHashCode(callSuper = false)
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChatAdministratorRights implements BotApiObject {

    private static final String ISANONYMOUS_FIELD = "is_anonymous";
    private static final String CANMANAGECHAT_FIELD = "can_manage_chat";
    private static final String CANDELETEMESSAGES_FIELD = "can_delete_messages";
    private static final String CANMANAGEVIDEOCHATS_FIELD = "can_manage_video_chats";
    private static final String CANRESTRICTMEMBERS_FIELD = "can_restrict_members";
    private static final String CANPROMOTEMEMBERS_FIELD = "can_promote_members";
    private static final String CANCHANGEINFO_FIELD = "can_change_info";
    private static final String CANINVITEUSERS_FIELD = "can_invite_users";
    private static final String CANPOSTMESSAGES_FIELD = "can_post_messages";
    private static final String CANEDITMESSAGES_FIELD = "can_edit_messages";
    private static final String CANPINMESSAGES_FIELD = "can_pin_messages";
    private static final String CANMANAGETOPICS_FIELD = "can_manage_topics";

    /**
     * True, if the user's presence in the chat is hidden
     */
    @JsonProperty(ISANONYMOUS_FIELD)
    private Boolean isAnonymous;
    /**
     * True, if the administrator can access the chat event log, chat statistics,
     * message statistics in channels, see channel members, see anonymous administrators
     * in supergroups and ignore slow mode.
     * Implied by any other administrator privilege
     */
    @JsonProperty(CANMANAGECHAT_FIELD)
    private Boolean canManageChat;
    /**
     * True, if the administrator can delete messages of other users
     */
    @JsonProperty(CANDELETEMESSAGES_FIELD)
    private Boolean canDeleteMessages;
    /**
     * True, if the administrator can manage video chats
     */
    @JsonProperty(CANMANAGEVIDEOCHATS_FIELD)
    private Boolean canManageVideoChats;
    /**
     * True, if the administrator can restrict, ban or unban chat members
     */
    @JsonProperty(CANRESTRICTMEMBERS_FIELD)
    private Boolean canRestrictMembers;
    /**
     * True, if the administrator can add new administrators with a subset of
     * their own privileges or demote administrators that he has promoted,
     * directly or indirectly (promoted by administrators that were appointed by the user)
     */
    @JsonProperty(CANPROMOTEMEMBERS_FIELD)
    private Boolean canPromoteMembers;
    /**
     * True, if the user is allowed to change the chat title, photo and other settings
     */
    @JsonProperty(CANCHANGEINFO_FIELD)
    private Boolean canChangeInfo;
    /**
     * True, if the user is allowed to invite new users to the chat
     */
    @JsonProperty(CANINVITEUSERS_FIELD)
    private Boolean canInviteUsers;
    /**
     * Optional.
     * True, if the administrator can post in the channel; channels only
     */
    @JsonProperty(CANPOSTMESSAGES_FIELD)
    private Boolean canPostMessages;
    /**
     * Optional.
     * True, if the administrator can edit messages of other users and can pin messages; channels only
     */
    @JsonProperty(CANEDITMESSAGES_FIELD)
    private Boolean canEditMessages;
    /**
     * Optional.
     * True, if the user is allowed to pin messages; groups and supergroups only
     */
    @JsonProperty(CANPINMESSAGES_FIELD)
    private Boolean canPinMessages;
    /**
     * Optional.
     * True, if the user is allowed to create, rename, close, and reopen forum topics; supergroups only
     */
    @JsonProperty(CANMANAGETOPICS_FIELD)
    private Boolean canManageTopics;
}
