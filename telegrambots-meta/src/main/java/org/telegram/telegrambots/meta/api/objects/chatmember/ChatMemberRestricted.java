package org.telegram.telegrambots.meta.api.objects.chatmember;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.telegram.telegrambots.meta.api.objects.User;

/**
 * @author Ruben Bermudez
 * @version 5.3
 *
 * Represents a chat member that is under certain restrictions in the chat. Supergroups only.
 */
@JsonDeserialize
@EqualsAndHashCode(callSuper = false)
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChatMemberRestricted implements ChatMember {
    public static final String STATUS = "restricted";

    private static final String STATUS_FIELD = "status";
    private static final String USER_FIELD = "user";
    private static final String ISMEMBER_FIELD = "is_member";
    private static final String CANSENDMESSAGES_FIELD = "can_send_messages";
    private static final String CANSENDAUDIOS_FIELD = "can_send_audios";
    private static final String CANSENDDOCUMENTS_FIELD = "can_send_documents";
    private static final String CANSENDPHOTOS_FIELD = "can_send_photos";
    private static final String CANSENDVIDEOS_FIELD = "can_send_videos";
    private static final String CANSENDVIDEONOTES_FIELD = "can_send_video_notes";
    private static final String CANSENDVOICENOTES_FIELD = "can_send_voice_notes";
    private static final String CANSENDPOLLS_FIELD = "can_send_polls";
    private static final String CANSENDOTHERMESSAGES_FIELD = "can_send_other_messages";
    private static final String CANADDWEBPAGEPREVIEWS_FIELD = "can_add_web_page_previews";
    private static final String CANCHANGEINFO_FIELD = "can_change_info";
    private static final String CANINVITEUSERS_FIELD = "can_invite_users";
    private static final String CANPINMESSAGES_FIELD = "can_pin_messages";
    private static final String CANMANAGETOPICS_FIELD = "can_manage_topics";
    private static final String UNTILDATE_FIELD = "until_date";

    private static final String CANSENDMEDIAMESSAGES_FIELD = "can_send_media_messages";

    /**
     * The member's status in the chat, always “restricted”
     */
    @JsonProperty(STATUS_FIELD)
    private final String status = STATUS;
    /**
     * Information about the user
     */
    @JsonProperty(USER_FIELD)
    private User user;
    /**
     * True, if the user is a member of the chat at the moment of the request
     */
    @JsonProperty(ISMEMBER_FIELD)
    private Boolean isMember;
    /**
     * True, if the user is allowed to send text messages, contacts, giveaways, giveaway winners, invoices, locations and venues
     */
    @JsonProperty(CANSENDMESSAGES_FIELD)
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
     * True, if the user is allowed to send polls
     */
    @JsonProperty(CANSENDPOLLS_FIELD)
    private Boolean canSendPolls;
    /**
     * True, if the user is allowed to send animations, games, stickers and use inline bots
     */
    @JsonProperty(CANSENDOTHERMESSAGES_FIELD)
    private Boolean canSendOtherMessages;
    /**
     * True, if the user is allowed to add web page previews to their messages
     */
    @JsonProperty(CANADDWEBPAGEPREVIEWS_FIELD)
    private Boolean canAddWebpagePreviews;
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
     * True, if the user is allowed to pin messages; groups and supergroups only
     */
    @JsonProperty(CANPINMESSAGES_FIELD)
    private Boolean canPinMessages;
    /**
     * True, if the user is allowed to create forum topics
     */
    @JsonProperty(CANMANAGETOPICS_FIELD)
    private Boolean canManageTopics;
    /**
     * Date when restrictions will be lifted for this user; unix time
     */
    @JsonProperty(UNTILDATE_FIELD)
    private Integer untilDate;

    /**
     * True, if the user is allowed to send audios, documents, photos, videos, video notes and voice notes
     * @deprecated Use individual permissions instead
     */
    @JsonProperty(CANSENDMEDIAMESSAGES_FIELD)
    @Deprecated
    private Boolean canSendMediaMessages;
}
