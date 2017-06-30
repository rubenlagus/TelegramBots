package org.telegram.telegrambots.api.objects;

import com.fasterxml.jackson.annotation.JsonProperty;

import org.telegram.telegrambots.api.interfaces.BotApiObject;

/**
 * @author Ruben Bermudez
 * @version 1.0
 * @brief This object contains information about one member of the chat.
 * @date 20 of May of 2016
 */
public class ChatMember implements BotApiObject {
    private static final String USER_FIELD = "user";
    private static final String STATUS_FIELD = "status";
    private static final String UNTILDATE_FIELD = "until_date";
    private static final String CANBEEDITED_FIELD = "can_be_edited";
    private static final String CANCHANGEINFORMATION_FIELD = "can_change_information";
    private static final String CANPOSTMESSAGES_FIELD = "can_post_messages";
    private static final String CANEDITMESSAGES_FIELD = "can_edit_messages";
    private static final String CANDELETEMESSAGES_FIELD = "can_delete_messages";
    private static final String CANINVITEUSERS_FIELD = "can_invite_users";
    private static final String CANRESTRICTUSERS_FIELD = "can_restrict_users";
    private static final String CANPINMESSAGES_FIELD = "can_pin_messages";
    private static final String CANPROMOTEMEMBERS_FIELD = "can_promote_members";
    private static final String CANSENDMESSAGES_FIELD = "can_send_messages";
    private static final String CANSENDMEDIAMESSAGES_FIELD = "can_send_media_messages";
    private static final String CANSENDOTHERMESSAGES_FIELD = "can_send_other_messages";
    private static final String CANADDWEBPAGEPREVIEWS_FIELD = "can_add_web_page_previews";

    @JsonProperty(USER_FIELD)
    private User user; ///< Information about the user
    @JsonProperty(STATUS_FIELD)
    private String status; ///< The member's status in the chat. Can be “creator”, “administrator”, “member”,  “restricted”, “left” or “kicked”
    @JsonProperty(UNTILDATE_FIELD)
    private Integer untilDate; ///< Optional. Resticted and kicked only. Date when restrictions will be lifted for this user, unix time
    @JsonProperty(CANBEEDITED_FIELD)
    private Boolean canBeEdited; ///< Optional. Administrators only. True, if the bot is allowed to edit administrator privileges of that user
    @JsonProperty(CANCHANGEINFORMATION_FIELD)
    private Boolean canChangeInformation; ///< Optional. Administrators only. True, if the administrator can change the chat title, photo and other settings
    @JsonProperty(CANPOSTMESSAGES_FIELD)
    private Boolean canPostMessages; ///< Optional. Administrators only. True, if the administrator can post in the channel, channels only
    @JsonProperty(CANEDITMESSAGES_FIELD)
    private Boolean canEditMessages; ///< Optional. Administrators only. True, if the administrator can edit messages of other users, channels only
    @JsonProperty(CANDELETEMESSAGES_FIELD)
    private Boolean canDeleteMessages; ///< Optional. Administrators only. True, if the administrator can delete messages of other users
    @JsonProperty(CANINVITEUSERS_FIELD)
    private Boolean canInviteUsers; ///< Optional. Administrators only. True, if the administrator can invite new users to the chat
    @JsonProperty(CANRESTRICTUSERS_FIELD)
    private Boolean canRestrictUsers; ///< Optional. Administrators only. True, if the administrator can restrict, ban or unban chat members
    @JsonProperty(CANPINMESSAGES_FIELD)
    private Boolean canPinMessages; ///< Optional. Administrators only. True, if the administrator can pin messages, supergroups only
    @JsonProperty(CANPROMOTEMEMBERS_FIELD)
    private Boolean canPromoteMembers; ///< Optional. Administrators only. True, if the administrator can add new administrators with a subset of his own privileges or demote administrators that it has promoted, directly or indirectly (promoted by administators that were appointed by the bot)
    @JsonProperty(CANSENDMESSAGES_FIELD)
    private Boolean canSendMessages; ///< Optional. Restricted only. True, if the user can send text messages, contacts, locations and venues
    @JsonProperty(CANSENDMEDIAMESSAGES_FIELD)
    private Boolean canSendMediaMessages; ///< Optional. Restricted only. True, if the user can send audios, documents, photos, videos, video notes and voice notes, implies can_send_messages
    @JsonProperty(CANSENDOTHERMESSAGES_FIELD)
    private Boolean canSendOtherMessages; ///< Optional. Restricted only. True, if the user can send animations, games, stickers and use inline bots, implies can_send_media_messages
    @JsonProperty(CANADDWEBPAGEPREVIEWS_FIELD)
    private Boolean canAddWebPagePreviews; ///< Optional. Restricted only. True, if user may add web page previews Э to his messages, implies can_send_messages

    public ChatMember() {
        super();
    }

    public User getUser() {
        return user;
    }

    public String getStatus() {
        return status;
    }

    public Integer getUntilDate() {
        return untilDate;
    }

    public Boolean getCanBeEdited() {
        return canBeEdited;
    }

    public Boolean getCanChangeInformation() {
        return canChangeInformation;
    }

    public Boolean getCanPostMessages() {
        return canPostMessages;
    }

    public Boolean getCanEditMessages() {
        return canEditMessages;
    }

    public Boolean getCanDeleteMessages() {
        return canDeleteMessages;
    }

    public Boolean getCanInviteUsers() {
        return canInviteUsers;
    }

    public Boolean getCanRestrictUsers() {
        return canRestrictUsers;
    }

    public Boolean getCanPinMessages() {
        return canPinMessages;
    }

    public Boolean getCanPromoteMembers() {
        return canPromoteMembers;
    }

    public Boolean getCanSendMessages() {
        return canSendMessages;
    }

    public Boolean getCanSendMediaMessages() {
        return canSendMediaMessages;
    }

    public Boolean getCanSendOtherMessages() {
        return canSendOtherMessages;
    }

    public Boolean getCanAddWebPagePreviews() {
        return canAddWebPagePreviews;
    }

    @Override
    public String toString() {
        return "ChatMember{" +
                "user=" + user +
                ", status='" + status + '\'' +
                ", untilDate=" + untilDate +
                ", canBeEdited=" + canBeEdited +
                ", canChangeInformation=" + canChangeInformation +
                ", canPostMessages=" + canPostMessages +
                ", canEditMessages=" + canEditMessages +
                ", canDeleteMessages=" + canDeleteMessages +
                ", canInviteUsers=" + canInviteUsers +
                ", canRestrictUsers=" + canRestrictUsers +
                ", canPinMessages=" + canPinMessages +
                ", canPromoteMembers=" + canPromoteMembers +
                ", canSendMessages=" + canSendMessages +
                ", canSendMediaMessages=" + canSendMediaMessages +
                ", canSendOtherMessages=" + canSendOtherMessages +
                ", canAddWebPagePreviews=" + canAddWebPagePreviews +
                '}';
    }
}
