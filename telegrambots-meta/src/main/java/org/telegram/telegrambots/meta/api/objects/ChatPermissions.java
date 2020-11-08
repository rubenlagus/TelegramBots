package org.telegram.telegrambots.meta.api.objects;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.telegram.telegrambots.meta.api.interfaces.BotApiObject;

/**
 * @author Ruben Bermudez
 * @version 4.4
 * Use this method to change the description of a group, supergroup or channel.
 * The bot must be an administrator in the chat for this to work and must have the appropriate admin rights.
 * Returns True on success.
 */
@EqualsAndHashCode(callSuper = false)
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChatPermissions implements BotApiObject {
    private static final String CAN_SEND_MESSAGES_FIELD = "can_send_messages";
    private static final String CAN_SEND_MEDIA_MESSAGES_FIELD = "can_send_media_messages";
    private static final String CAN_SEND_POLLS_FIELD = "can_send_polls";
    private static final String CAN_SEND_OTHER_MESSAGES_FIELD = "can_send_other_messages";
    private static final String CAN_ADD_WEB_PAGE_PREVIEWS_FIELD = "can_add_web_page_previews";
    private static final String CAN_CHANGE_INFO_FIELD = "can_change_info";
    private static final String CAN_INVITE_USERS_FIELD = "can_invite_users";
    private static final String CAN_PIN_MESSAGES_FIELD = "can_pin_messages";

    @JsonProperty(CAN_SEND_MESSAGES_FIELD)
    private Boolean canSendMessages; ///< Optional. True, if the user is allowed to send text messages, contacts, locations and venues
    @JsonProperty(CAN_SEND_MEDIA_MESSAGES_FIELD)
    private Boolean canSendMediaMessages; ///< Optional. True, if the user is allowed to send audios, documents, photos, videos, video notes and voice notes, implies can_send_messages
    @JsonProperty(CAN_SEND_POLLS_FIELD)
    private Boolean canSendPolls; ///< Optional. True, if the user is allowed to send polls, implies can_send_messages
    @JsonProperty(CAN_SEND_OTHER_MESSAGES_FIELD)
    private Boolean canSendOtherMessages; ///< Optional. True, if the user is allowed to send animations, games, stickers and use inline bots, implies can_send_media_messages
    @JsonProperty(CAN_ADD_WEB_PAGE_PREVIEWS_FIELD)
    private Boolean canAddWebPagePreviews; ///< Optional. True, if the user is allowed to add web page previews to their messages, implies can_send_media_messages
    @JsonProperty(CAN_CHANGE_INFO_FIELD)
    private Boolean canChangeInfo; ///< Optional. True, if the user is allowed to change the chat title, photo and other settings. Ignored in public supergroups
    @JsonProperty(CAN_INVITE_USERS_FIELD)
    private Boolean canInviteUsers; ///< Optional. True, if the user is allowed to invite new users to the chat
    @JsonProperty(CAN_PIN_MESSAGES_FIELD)
    private Boolean canPinMessages; ///< Optional. True, if the user is allowed to pin messages. Ignored in public supergroups
}
