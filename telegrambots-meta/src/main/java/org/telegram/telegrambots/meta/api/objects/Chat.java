package org.telegram.telegrambots.meta.api.objects;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.telegram.telegrambots.meta.api.interfaces.BotApiObject;
import org.telegram.telegrambots.meta.api.objects.reactions.ReactionType;

import java.util.List;

/**
 * This object represents a Telegram chat with an user or a group
 * @author Ruben Bermudez
 * @version 6.1
 */
@SuppressWarnings("WeakerAccess")
@EqualsAndHashCode(callSuper = false)
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@NoArgsConstructor(force = true)
@AllArgsConstructor
public class Chat implements BotApiObject {

    private static final String ID_FIELD = "id";
    private static final String TYPE_FIELD = "type";
    private static final String TITLE_FIELD = "title";
    private static final String USERNAME_FIELD = "username";
    private static final String FIRSTNAME_FIELD = "first_name";
    private static final String LASTNAME_FIELD = "last_name";
    private static final String BIO_FIELD = "bio";
    private static final String PHOTO_FIELD = "photo";
    private static final String DESCRIPTION_FIELD = "description";
    private static final String INVITELINK_FIELD = "invite_link";
    private static final String PINNEDMESSAGE_FIELD = "pinned_message";
    private static final String STICKERSETNAME_FIELD = "sticker_set_name";
    private static final String CANSETSTICKERSET_FIELD = "can_set_sticker_set";
    private static final String PERMISSIONS_FIELD = "permissions";
    private static final String SLOWMODEDELAY_FIELD = "slow_mode_delay";
    private static final String LINKEDCHATID_FIELD = "linked_chat_id";
    private static final String LOCATION_FIELD = "location";
    private static final String MESSAGEAUTODELETETIME_FIELD = "message_auto_delete_time";
    private static final String HASPRIVATEFORWARDS_FIELD = "has_private_forwards";
    private static final String HASPROTECTEDCONTENT_FIELD = "has_protected_content";
    private static final String JOINTOSENDMESSAGES_FIELD  = "join_to_send_messages";
    private static final String JOINBYREQUEST_FIELD  = "join_by_request";
    private static final String HASRESTRICTEDVOICEANDVIDEOMESSAGES_FIELD  = "has_restricted_voice_and_video_messages";
    private static final String ISFORUM_FIELD  = "is_forum";
    private static final String ACTIVEUSERNAMES_FIELD  = "active_usernames";
    private static final String HASAGGRESSIVEANTISPAMENABLED_FIELD  = "has_aggressive_anti_spam_enabled";
    private static final String HASHIDDENMEMBERS_FIELD  = "has_hidden_members";
    private static final String AVAILABLE_REACTIONS_FIELD  = "available_reactions";
    private static final String ACCENT_COLOR_ID_FIELD  = "accent_color_id";
    private static final String BACKGROUND_CUSTOM_EMOJI_ID_FIELD  = "background_custom_emoji_id";
    private static final String PROFILE_ACCENT_COLOR_ID_FIELD  = "profile_accent_color_id";
    private static final String PROFILE_BACKGROUND_CUSTOM_EMOJI_ID_FIELD  = "profile_background_custom_emoji_id";
    private static final String HAS_VISIBLE_HISTORY_FIELD  = "has_visible_history";
    private static final String EMOJI_STATUS_CUSTOM_ID_FIELD  = "emoji_status_custom_emoji_id";
    private static final String EMOJI_STATUS_EXPIRATION_DATE_FIELD  = "emoji_status_expiration_date";


    private static final String USERCHATTYPE = "private";
    private static final String GROUPCHATTYPE = "group";
    private static final String CHANNELCHATTYPE = "channel";
    private static final String SUPERGROUPCHATTYPE  = "supergroup";

    /**
     * Unique identifier for this chat.
     * This number may be greater than 32 bits and some programming languages may
     * have difficulty/silent defects in interpreting it. But it smaller than 52 bits,
     * so a signed 64 bit integer or double-precision float type are safe for storing this identifier.
     */
    @JsonProperty(ID_FIELD)
    @NonNull
    private Long id;
    /**
     * Type of the chat, one of “private”, “group” or “channel” or "supergroup"
     */
    @JsonProperty(TYPE_FIELD)
    @NonNull
    private String type;
    /**
     * Optional.
     * Title of the chat, only for channels and group chat
     */
    @JsonProperty(TITLE_FIELD)
    private String title;
    /**
     * Optional
     * Username of the chat, only for private chats and channels if available
     */
    @JsonProperty(FIRSTNAME_FIELD)
    private String firstName;
    /**
     * Optional.
     * Interlocutor's first name for private chats
     */
    @JsonProperty(LASTNAME_FIELD)
    private String lastName;
    /**
     * Optional.
     * Interlocutor's last name for private chats
     */
    @JsonProperty(USERNAME_FIELD)
    private String userName;
    /**
     * Optional.
     * Chat photo. Returned only in getChat.
     */
    @JsonProperty(PHOTO_FIELD)
    private ChatPhoto photo;
    /**
     * Optional.
     * Description, for groups, supergroups and channel chats. Returned only in getChat.
     */
    @JsonProperty(DESCRIPTION_FIELD)
    private String description;
    /**
     * Optional.
     * Primary invite link, for groups, supergroups and channel chats. Returned only in getChat.
     */
    @JsonProperty(INVITELINK_FIELD)
    private String inviteLink;
    /**
     * Optional.
     * The most recent pinned message (by sending date). Returned only in getChat.
     */
    @JsonProperty(PINNEDMESSAGE_FIELD)
    private Message pinnedMessage;
    /**
     * Optional.
     * For supergroups, name of Group sticker set. Returned only in getChat.
     */
    @JsonProperty(STICKERSETNAME_FIELD)
    private String stickerSetName;
    /**
     * Optional.
     * True, if the bot can change group the sticker set. Returned only in getChat.
     */
    @JsonProperty(CANSETSTICKERSET_FIELD)
    private Boolean canSetStickerSet;
    /**
     * Optional.
     * Default chat member permissions, for groups and supergroups. Returned only in getChat.
     */
    @JsonProperty(PERMISSIONS_FIELD)
    private ChatPermissions permissions;
    /**
     * Optional.
     * For supergroups, the minimum allowed delay between consecutive messages sent by each unpriviledged user.
     * Returned only in getChat.
     */
    @JsonProperty(SLOWMODEDELAY_FIELD)
    private Integer slowModeDelay;
    /**
     * Optional.
     * Bio of the other party in a private chat. Returned only in getChat.
     */
    @JsonProperty(BIO_FIELD)
    private String bio;
    /**
     * Optional.
     * Unique identifier for the linked chat,
     * i.e. the discussion group identifier for a channel and vice versa; for supergroups and channel chats.
     * This identifier may be greater than 32 bits and some programming
     * languages may have difficulty/silent defects in interpreting it.
     * But it is smaller than 52 bits, so a signed 64 bit integer or
     * double-precision float type are safe for storing this identifier.
     * Returned only in getChat.
     */
    @JsonProperty(LINKEDCHATID_FIELD)
    private Long linkedChatId;
    /**
     * Optional. For supergroups, the location to which the supergroup is connected. Returned only in getChat.
     */
    @JsonProperty(LOCATION_FIELD)
    private ChatLocation location;
    /**
     * Optional. The time after which all messages sent to the chat will be automatically deleted; in seconds. Returned only in getChat.
     */
    @JsonProperty(MESSAGEAUTODELETETIME_FIELD)
    private Integer messageAutoDeleteTime;
    /**
     * Optional.
     * True, if privacy settings of the other party in the private chat allows to use tg://user?id=<user_id> links only in chats with the user.
     * Returned only in getChat.
     */
    @JsonProperty(HASPRIVATEFORWARDS_FIELD)
    private Boolean hasPrivateForwards;
    /**
     * Optional.
     * True, if messages from the chat can't be forwarded to other chats.
     * Returned only in getChat.
     */
    @JsonProperty(HASPROTECTEDCONTENT_FIELD)
    private Boolean HasProtectedContent;
    /**
     * Optional.
     * True, if users need to join the supergroup before they can send messages.
     * Returned only in getChat.
     */
    @JsonProperty(JOINTOSENDMESSAGES_FIELD)
    private Boolean joinToSendMessages;
    /**
     * Optional.
     * True, if all users directly joining the supergroup need to be approved by supergroup administrators.
     * Returned only in getChat.
     */
    @JsonProperty(JOINBYREQUEST_FIELD)
    private Boolean joinByRequest;
    /**
     * Optional.
     * True, if the privacy settings of the other party restrict sending voice and video note messages in the private chat.
     * Returned only in getChat.
     */
    @JsonProperty(HASRESTRICTEDVOICEANDVIDEOMESSAGES_FIELD)
    private Boolean hasRestrictedVoiceAndVideoMessages;
    /**
     * Optional.
     * True, if the supergroup chat is a forum (has topics enabled)
     */
    @JsonProperty(ISFORUM_FIELD)
    private Boolean isForum;
    /**
     * Optional.
     * If non-empty, the list of all active chat usernames; for private chats, supergroups and channels.
     * Returned only in getChat.
     */
    @JsonProperty(ACTIVEUSERNAMES_FIELD)
    private List<String> activeUsernames;
    /**
     * Optional.
     * Custom emoji identifier of the emoji status of the chat or the other party in a private chat.
     * Returned only in getChat.
     */
    @JsonProperty(EMOJI_STATUS_CUSTOM_ID_FIELD)
    private String emojiStatusCustomEmojiId;
    /**
     * Optional.
     * True, if aggressive anti-spam checks are enabled in the supergroup.
     * The field is only available to chat administrators.
     * Returned only in getChat.
     */
    @JsonProperty(HASAGGRESSIVEANTISPAMENABLED_FIELD)
    private Boolean hasAggressiveAntiSpamEnabled;
    /**
     * Optional.
     * True, if non-administrators can only get the list of bots and administrators in the chat.
     * Returned only in getChat.
     */
    @JsonProperty(HASHIDDENMEMBERS_FIELD)
    private Boolean hasHiddenMembers;
    /**
     * Optional.
     * Expiration date of the emoji status of the other party in a private chat, if any.
     * Returned only in getChat.
     */
    @JsonProperty(EMOJI_STATUS_EXPIRATION_DATE_FIELD)
    private Boolean emojiStatusExpirationDate;
    /**
     * Optional.
     * List of available reactions allowed in the chat.
     * If omitted, then all emoji reactions are allowed.
     * Returned only in getChat.
     */
    @JsonProperty(AVAILABLE_REACTIONS_FIELD)
    private List<ReactionType> availableReactions;
    /**
     * Optional.
     * Identifier of the accent color for the chat name and backgrounds of the chat photo, reply header, and link preview.
     * See accent colors for more details.
     * Returned only in getChat.
     * Always returned in getChat.
     */
    @JsonProperty(ACCENT_COLOR_ID_FIELD)
    private Integer accentColorId;
    /**
     * Optional.
     * Custom emoji identifier of emoji chosen by the chat for the reply header and link preview background.
     * Returned only in getChat.
     */
    @JsonProperty(BACKGROUND_CUSTOM_EMOJI_ID_FIELD)
    private String backgroundCustomEmojiId;
    /**
     * Optional.
     * Identifier of the accent color for the chat's profile background.
     * See profile accent colors for more details.
     * Returned only in getChat.
     */
    @JsonProperty(PROFILE_ACCENT_COLOR_ID_FIELD)
    private Boolean profileAccentColorId;
    /**
     * Optional.
     * Custom emoji identifier of the emoji chosen by the chat for its profile background.
     * Returned only in getChat.
     */
    @JsonProperty(PROFILE_BACKGROUND_CUSTOM_EMOJI_ID_FIELD)
    private Boolean profileBackgroundCustomEmojiId;
    /**
     * Optional.
     * True, if new chat members will have access to old messages; available only to chat administrators.
     * Returned only in getChat.
     */
    @JsonProperty(HAS_VISIBLE_HISTORY_FIELD)
    private Boolean hasVisibleHistory;

    @JsonIgnore
    public Boolean isGroupChat() {
        return GROUPCHATTYPE.equals(type);
    }

    @JsonIgnore
    public Boolean isChannelChat() {
        return CHANNELCHATTYPE.equals(type);
    }

    @JsonIgnore
    public Boolean isUserChat() {
        return USERCHATTYPE.equals(type);
    }

    @JsonIgnore
    public Boolean isSuperGroupChat() {
        return SUPERGROUPCHATTYPE.equals(type);
    }
}
