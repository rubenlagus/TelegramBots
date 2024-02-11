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
 * Use this method to change the description of a group, supergroup or channel.
 * The bot must be an administrator in the chat for this to work and must have the appropriate admin rights.
 * Returns True on success.
 * @author Ruben Bermudez
 * @version 4.4
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
    private static final String CANSENDAUDIOS_FIELD = "can_send_audios";
    private static final String CANSENDDOCUMENTS_FIELD = "can_send_documents";
    private static final String CANSENDPHOTOS_FIELD = "can_send_photos";
    private static final String CANSENDVIDEOS_FIELD = "can_send_videos";
    private static final String CANSENDVIDEONOTES_FIELD = "can_send_video_notes";
    private static final String CANSENDVOICENOTES_FIELD = "can_send_voice_notes";
    private static final String CAN_SEND_POLLS_FIELD = "can_send_polls";
    private static final String CAN_SEND_OTHER_MESSAGES_FIELD = "can_send_other_messages";
    private static final String CAN_ADD_WEB_PAGE_PREVIEWS_FIELD = "can_add_web_page_previews";
    private static final String CAN_CHANGE_INFO_FIELD = "can_change_info";
    private static final String CAN_INVITE_USERS_FIELD = "can_invite_users";
    private static final String CAN_PIN_MESSAGES_FIELD = "can_pin_messages";
    private static final String CANMANAGETOPICS_FIELD = "can_manage_topics";

    private static final String CAN_SEND_MEDIA_MESSAGES_FIELD = "can_send_media_messages";

    /**
     * Optional.
     * True, if the user is allowed to send text messages, contacts, giveaways, giveaway winners, invoices, locations and venues
     */
    @JsonProperty(CAN_SEND_MESSAGES_FIELD)
    private Boolean canSendMessages;
    /**
     * True, if the user is allowed to send audios
     */
    @JsonProperty(CANSENDAUDIOS_FIELD)
    private Boolean canSendAudios;
    /**
     * True, if the user is allowed to send documents
     */
    @JsonProperty(CANSENDDOCUMENTS_FIELD)
    private Boolean canSendDocuments;
    /**
     * True, if the user is allowed to send photos
     */
    @JsonProperty(CANSENDPHOTOS_FIELD)
    private Boolean canSendPhotos;
    /**
     * True, if the user is allowed to send videos
     */
    @JsonProperty(CANSENDVIDEOS_FIELD)
    private Boolean canSendVideos;
    /**
     * True, if the user is allowed to send video notes
     */
    @JsonProperty(CANSENDVIDEONOTES_FIELD)
    private Boolean canSendVideoNotes;
    /**
     * Optional. True, if the user is allowed to send voice notes
     */
    @JsonProperty(CANSENDVOICENOTES_FIELD)
    private Boolean canSendVoiceNotes;
    /**
     * Optional.
     * True, if the user is allowed to send polls, implies can_send_messages
     */
    @JsonProperty(CAN_SEND_POLLS_FIELD)
    private Boolean canSendPolls;
    /**
     * Optional.
     * True, if the user is allowed to send animations, games, stickers and use inline bots, implies can_send_media_messages
     */
    @JsonProperty(CAN_SEND_OTHER_MESSAGES_FIELD)
    private Boolean canSendOtherMessages;
    /**
     * Optional.
     * True, if the user is allowed to add web page previews to their messages, implies can_send_media_messages
     */
    @JsonProperty(CAN_ADD_WEB_PAGE_PREVIEWS_FIELD)
    private Boolean canAddWebPagePreviews;
    /**
     * Optional.
     * True, if the user is allowed to change the chat title, photo and other settings. Ignored in public supergroups
     */
    @JsonProperty(CAN_CHANGE_INFO_FIELD)
    private Boolean canChangeInfo;
    /**
     * Optional.
     * True, if the user is allowed to invite new users to the chat
     */
    @JsonProperty(CAN_INVITE_USERS_FIELD)
    private Boolean canInviteUsers;
    /**
     * Optional.
     * True, if the user is allowed to pin messages. Ignored in public supergroups
     */
    @JsonProperty(CAN_PIN_MESSAGES_FIELD)
    private Boolean canPinMessages;
    /**
     * Optional. True, if the user is allowed to create forum topics.
     * If omitted defaults to the value of can_pin_messages
     */
    @JsonProperty(CANMANAGETOPICS_FIELD)
    private Boolean canManageTopics;

    /**
     * Optional.
     * True, if the user is allowed to send audios, documents, photos, videos, video notes and voice notes, implies can_send_messages
     * @deprecated Use individual permissions
     */
    @JsonProperty(CAN_SEND_MEDIA_MESSAGES_FIELD)
    @Deprecated
    private Boolean canSendMediaMessages;
}
