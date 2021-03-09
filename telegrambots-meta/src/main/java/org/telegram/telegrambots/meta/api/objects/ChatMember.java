package org.telegram.telegrambots.meta.api.objects;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.telegram.telegrambots.meta.api.interfaces.BotApiObject;

import java.time.Instant;

/**
 * @author Ruben Bermudez
 * @version 1.0
 * This object contains information about one member of the chat.
 */
@EqualsAndHashCode(callSuper = false)
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ChatMember implements BotApiObject {
    private static final String USER_FIELD = "user";
    private static final String STATUS_FIELD = "status";
    private static final String UNTILDATE_FIELD = "until_date";
    private static final String CANBEEDITED_FIELD = "can_be_edited";
    private static final String CANCHANGEINFO_FIELD = "can_change_info";
    private static final String CANPOSTMESSAGES_FIELD = "can_post_messages";
    private static final String CANEDITMESSAGES_FIELD = "can_edit_messages";
    private static final String CANDELETEMESSAGES_FIELD = "can_delete_messages";
    private static final String CANINVITEUSERS_FIELD = "can_invite_users";
    private static final String CANRESTRICTMEMBERS_FIELD = "can_restrict_members";
    private static final String CANPINMESSAGES_FIELD = "can_pin_messages";
    private static final String CANPROMOTEMEMBERS_FIELD = "can_promote_members";
    private static final String CANSENDMESSAGES_FIELD = "can_send_messages";
    private static final String CANSENDMEDIAMESSAGES_FIELD = "can_send_media_messages";
    private static final String CANSENDOTHERMESSAGES_FIELD = "can_send_other_messages";
    private static final String CANADDWEBPAGEPREVIEWS_FIELD = "can_add_web_page_previews";
    private static final String CAN_SEND_POLLS_FIELD = "can_send_polls";
    private static final String ISMEMBER_FIELD = "is_member";
    private static final String CUSTOMTITLE_FIELD = "custom_title";
    private static final String ISANONYMOUS_FIELD = "is_anonymous";
    private static final String CANMANAGECHAT_FIELD = "can_manage_chat";
    private static final String CANMANAGEVOICECHATS_FIELD = "can_manage_voice_chats";

    @JsonProperty(USER_FIELD)
    private User user; ///< Information about the user
    @JsonProperty(STATUS_FIELD)
    private String status; ///< The member's status in the chat. Can be “creator”, “administrator”, “member”, “restricted”, “left” or “kicked”
    @JsonProperty(UNTILDATE_FIELD)
    private Integer untilDate; ///< Optional. Restricted and kicked only. Date when restrictions will be lifted for this user, unix time
    @JsonProperty(CANBEEDITED_FIELD)
    private Boolean canBeEdited; ///< Optional. Administrators only. True, if the bot is allowed to edit administrator privileges of that user
    @JsonProperty(CANCHANGEINFO_FIELD)
    private Boolean canChangeInfo; ///< Optional. Administrators and restricted only. True, if the administrator can change the chat title, photo and other settings
    @JsonProperty(CANPOSTMESSAGES_FIELD)
    private Boolean canPostMessages; ///< Optional. Administrators only. True, if the administrator can post in the channel, channels only
    @JsonProperty(CANEDITMESSAGES_FIELD)
    private Boolean canEditMessages; ///< Optional. Administrators only. True, if the administrator can edit messages of other users and can pin messages, channels only
    @JsonProperty(CANDELETEMESSAGES_FIELD)
    private Boolean canDeleteMessages; ///< Optional. Administrators only. True, if the administrator can delete messages of other users
    @JsonProperty(CANINVITEUSERS_FIELD)
    private Boolean canInviteUsers; ///< Optional. Administrators and restricted only. True, if the administrator can invite new users to the chat
    @JsonProperty(CANRESTRICTMEMBERS_FIELD)
    private Boolean canRestrictMembers; ///< Optional. Administrators only. True, if the administrator can restrict, ban or unban chat members
    @JsonProperty(CANPINMESSAGES_FIELD)
    private Boolean canPinMessages; ///< Optional. Administrators and restricted only. True, if the administrator can pin messages, groups and supergroups only
    @JsonProperty(CANPROMOTEMEMBERS_FIELD)
    private Boolean canPromoteMembers; ///< Optional. Administrators only. True, if the administrator can add new administrators with a subset of his own privileges or demote administrators that it has promoted, directly or indirectly (promoted by administrators that were appointed by the bot)
    @JsonProperty(CANSENDMESSAGES_FIELD)
    private Boolean canSendMessages; ///< Optional. Restricted only. True, if the user is allowed to send text messages, contacts, locations and venues
    @JsonProperty(CANSENDMEDIAMESSAGES_FIELD)
    private Boolean canSendMediaMessages; ///< Optional. Restricted only. True, if the user is allowed to send audios, documents, photos, videos, video notes and voice notes, implies can_send_messages
    @JsonProperty(CANSENDOTHERMESSAGES_FIELD)
    private Boolean canSendOtherMessages; ///< Optional. Restricted only. True, if the user is allowed to send animations, games, stickers and use inline bots, implies can_send_media_messages
    @JsonProperty(CANADDWEBPAGEPREVIEWS_FIELD)
    private Boolean canAddWebPagePreviews; ///< Optional. Restricted only. True, if the user is allowed to add web page previews to his messages
    @JsonProperty(CAN_SEND_POLLS_FIELD)
    private Boolean canSendPolls; ///< Optional. Restricted only. True, if the user is allowed to send polls.
    @JsonProperty(ISMEMBER_FIELD)
    private Boolean isMember; ///< True, if the user is a member of the chat at the moment of the request. For example, it can be false for the chat creator or for a restricted user.
    @JsonProperty(CUSTOMTITLE_FIELD)
    private String customTitle; ///< Optional. Owner and administrators only. Custom title for this user
    @JsonProperty(ISANONYMOUS_FIELD)
    private Boolean isAnonymous; ///< Optional. Owner and administrators only. True, if the user's presence in the chat is hidden
    /**
     * Optional.
     *
     * Administrators only.
     *
     * True, if the administrator can access the chat event log, chat statistics, message statistics in channels,
     * see channel members, see anonymous administrators in supergoups and ignore slow mode.
     *
     * Implied by any other administrator privilege
     */
    @JsonProperty(CANMANAGECHAT_FIELD)
    private Boolean canManageChat;
    /**
     * Optional.
     *
     * Administrators only.
     *
     * True, if the administrator can manage voice chats; groups and supergroups only
     */
    @JsonProperty(CANMANAGEVOICECHATS_FIELD)
    private Boolean canManageVoiceChats;

    public Instant getUntilDateAsInstant() {
        if (untilDate == null) {
            return null;
        }
        return Instant.ofEpochSecond(untilDate);
    }
}
