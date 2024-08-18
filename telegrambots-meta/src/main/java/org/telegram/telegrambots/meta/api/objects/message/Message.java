package org.telegram.telegrambots.meta.api.objects.message;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import lombok.extern.jackson.Jacksonized;
import org.telegram.telegrambots.meta.api.objects.Audio;
import org.telegram.telegrambots.meta.api.objects.ChatShared;
import org.telegram.telegrambots.meta.api.objects.Contact;
import org.telegram.telegrambots.meta.api.objects.Dice;
import org.telegram.telegrambots.meta.api.objects.Document;
import org.telegram.telegrambots.meta.api.objects.EntityType;
import org.telegram.telegrambots.meta.api.objects.ExternalReplyInfo;
import org.telegram.telegrambots.meta.api.objects.LinkPreviewOptions;
import org.telegram.telegrambots.meta.api.objects.Location;
import org.telegram.telegrambots.meta.api.objects.MessageAutoDeleteTimerChanged;
import org.telegram.telegrambots.meta.api.objects.MessageEntity;
import org.telegram.telegrambots.meta.api.objects.PhotoSize;
import org.telegram.telegrambots.meta.api.objects.ProximityAlertTriggered;
import org.telegram.telegrambots.meta.api.objects.TextQuote;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.api.objects.UserShared;
import org.telegram.telegrambots.meta.api.objects.UsersShared;
import org.telegram.telegrambots.meta.api.objects.Venue;
import org.telegram.telegrambots.meta.api.objects.Video;
import org.telegram.telegrambots.meta.api.objects.VideoNote;
import org.telegram.telegrambots.meta.api.objects.Voice;
import org.telegram.telegrambots.meta.api.objects.WriteAccessAllowed;
import org.telegram.telegrambots.meta.api.objects.boost.ChatBoostAdded;
import org.telegram.telegrambots.meta.api.objects.chat.Chat;
import org.telegram.telegrambots.meta.api.objects.chat.background.ChatBackground;
import org.telegram.telegrambots.meta.api.objects.forum.ForumTopicClosed;
import org.telegram.telegrambots.meta.api.objects.forum.ForumTopicCreated;
import org.telegram.telegrambots.meta.api.objects.forum.ForumTopicEdited;
import org.telegram.telegrambots.meta.api.objects.forum.ForumTopicReopened;
import org.telegram.telegrambots.meta.api.objects.forum.GeneralForumTopicHidden;
import org.telegram.telegrambots.meta.api.objects.forum.GeneralForumTopicUnhidden;
import org.telegram.telegrambots.meta.api.objects.games.Animation;
import org.telegram.telegrambots.meta.api.objects.games.Game;
import org.telegram.telegrambots.meta.api.objects.giveaway.Giveaway;
import org.telegram.telegrambots.meta.api.objects.giveaway.GiveawayCompleted;
import org.telegram.telegrambots.meta.api.objects.giveaway.GiveawayCreated;
import org.telegram.telegrambots.meta.api.objects.giveaway.GiveawayWinners;
import org.telegram.telegrambots.meta.api.objects.messageorigin.MessageOrigin;
import org.telegram.telegrambots.meta.api.objects.passport.PassportData;
import org.telegram.telegrambots.meta.api.objects.payments.Invoice;
import org.telegram.telegrambots.meta.api.objects.payments.RefundedPayment;
import org.telegram.telegrambots.meta.api.objects.payments.SuccessfulPayment;
import org.telegram.telegrambots.meta.api.objects.payments.paidmedia.PaidMediaInfo;
import org.telegram.telegrambots.meta.api.objects.polls.Poll;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.stickers.Sticker;
import org.telegram.telegrambots.meta.api.objects.stories.Story;
import org.telegram.telegrambots.meta.api.objects.videochat.VideoChatEnded;
import org.telegram.telegrambots.meta.api.objects.videochat.VideoChatParticipantsInvited;
import org.telegram.telegrambots.meta.api.objects.videochat.VideoChatScheduled;
import org.telegram.telegrambots.meta.api.objects.videochat.VideoChatStarted;
import org.telegram.telegrambots.meta.api.objects.webapp.WebAppData;

import java.util.ArrayList;
import java.util.List;

/**
 * This object represents a message.
 * @author Ruben Bermudez
 * @version 1.0
 */
@EqualsAndHashCode(callSuper = false)
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Jacksonized
@JsonIgnoreProperties(ignoreUnknown = true)
public class Message implements MaybeInaccessibleMessage {
    private static final String MESSAGE_ID_FIELD = "message_id";
    private static final String MESSAGE_THREAD_ID_FIELD = "message_thread_id";
    private static final String FROM_FIELD = "from";
    private static final String DATE_FIELD = "date";
    private static final String CHAT_FIELD = "chat";
    private static final String FORWARD_FROM_FIELD = "forward_from";
    private static final String FORWARD_FROM_CHAT_FIELD = "forward_from_chat";
    private static final String FORWARD_DATE_FIELD = "forward_date";
    private static final String TEXT_FIELD = "text";
    private static final String ENTITIES_FIELD = "entities";
    private static final String CAPTION_ENTITIES_FIELD = "caption_entities";
    private static final String AUDIO_FIELD = "audio";
    private static final String DOCUMENT_FIELD = "document";
    private static final String PHOTO_FIELD = "photo";
    private static final String STICKER_FIELD = "sticker";
    private static final String VIDEO_FIELD = "video";
    private static final String CONTACT_FIELD = "contact";
    private static final String LOCATION_FIELD = "location";
    private static final String VENUE_FIELD = "venue";
    private static final String ANIMATION_FIELD = "animation";
    private static final String PINNED_MESSAGE_FIELD = "pinned_message";
    private static final String NEW_CHAT_MEMBERS_FIELD = "new_chat_members";
    private static final String LEFT_CHAT_MEMBER_FIELD = "left_chat_member";
    private static final String NEW_CHAT_TITLE_FIELD = "new_chat_title";
    private static final String NEW_CHAT_PHOTO_FIELD = "new_chat_photo";
    private static final String DELETE_CHAT_PHOTO_FIELD = "delete_chat_photo";
    private static final String GROUP_CHAT_CREATED_FIELD = "group_chat_created";
    private static final String REPLY_TO_MESSAGE_FIELD = "reply_to_message";
    private static final String VOICE_FIELD = "voice";
    private static final String CAPTION_FIELD = "caption";
    private static final String SUPER_GROUP_CREATED_FIELD = "supergroup_chat_created";
    private static final String CHANNEL_CHAT_CREATED_FIELD = "channel_chat_created";
    private static final String MIGRATE_TO_CHAT_FIELD = "migrate_to_chat_id";
    private static final String MIGRATE_FROM_CHAT_FIELD = "migrate_from_chat_id";
    private static final String EDIT_DATE_FIELD = "edit_date";
    private static final String GAME_FIELD = "game";
    private static final String FORWARD_FROM_MESSAGE_ID_FIELD = "forward_from_message_id";
    private static final String INVOICE_FIELD = "invoice";
    private static final String SUCCESSFUL_PAYMENT_FIELD = "successful_payment";
    private static final String VIDEO_NOTE_FIELD = "video_note";
    private static final String AUTHOR_SIGNATURE_FIELD = "author_signature";
    private static final String FORWARD_SIGNATURE_FIELD = "forward_signature";
    private static final String MEDIA_GROUP_ID_FIELD = "media_group_id";
    private static final String CONNECTED_WEBSITE_FIELD = "connected_website";
    private static final String PASSPORT_DATA_FIELD = "passport_data";
    private static final String FORWARD_SENDER_NAME_FIELD = "forward_sender_name";
    private static final String POLL_FIELD = "poll";
    private static final String REPLY_MARKUP_FIELD = "reply_markup";
    private static final String DICE_FIELD = "dice";
    private static final String VIA_BOT_FIELD = "via_bot";
    private static final String SENDER_CHAT_FIELD = "sender_chat";
    private static final String PROXIMITY_ALERT_TRIGGERED_FIELD = "proximity_alert_triggered";
    private static final String MESSAGE_AUTO_DELETE_TIMER_CHANGED_FIELD = "message_auto_delete_timer_changed";
    private static final String IS_AUTOMATIC_FORWARD_FIELD = "is_automatic_forward";
    private static final String HAS_PROTECTED_CONTENT_FIELD = "has_protected_content";
    private static final String WEB_APP_DATA_FIELD = "web_app_data";
    private static final String VIDEO_CHAT_SCHEDULED_FIELD = "video_chat_scheduled";
    private static final String VIDEO_CHAT_STARTED_FIELD = "video_chat_started";
    private static final String VIDEO_CHAT_ENDED_FIELD = "video_chat_ended";
    private static final String VIDEO_CHAT_PARTICIPANTS_INVITED_FIELD = "video_chat_participants_invited";
    private static final String IS_TOPIC_MESSAGE_FIELD = "is_topic_message";
    private static final String FORUM_TOPIC_CREATED_FIELD = "forum_topic_created";
    private static final String FORUM_TOPIC_CLOSED_FIELD = "forum_topic_closed";
    private static final String FORUM_TOPIC_REOPENED_FIELD = "forum_topic_reopened";
    private static final String FORUM_TOPIC_EDITED_FIELD = "forum_topic_edited";
    private static final String GENERAL_FORUM_TOPIC_HIDDEN_FIELD = "general_forum_topic_hidden";
    private static final String GENERAL_FORUM_TOPIC_UNHIDDEN_FIELD = "general_forum_topic_unhidden";
    private static final String WRITE_ACCESS_ALLOWED_FIELD = "write_access_allowed";
    private static final String HAS_MEDIA_SPOILER_FIELD = "has_media_spoiler";
    private static final String USER_SHARED_FIELD = "user_shared";
    private static final String CHAT_SHARED_FIELD = "chat_shared";
    private static final String STORY_FIELD = "story";
    private static final String EXTERNAL_REPLY_FIELD = "external_reply";
    private static final String FORWARD_ORIGIN_FIELD = "forward_origin";
    private static final String LINK_PREVIEW_OPTIONS_FIELD = "link_preview_options";
    private static final String QUOTE_FIELD = "quote";
    private static final String USERS_SHARED_FIELD = "users_shared";
    private static final String GIVEAWAY_CREATED_FIELD = "giveaway_created";
    private static final String GIVEAWAY_FIELD = "giveaway";
    private static final String GIVEAWAY_WINNERS_FIELD = "giveaway_winners";
    private static final String GIVEAWAY_COMPLETED_FIELD = "giveaway_completed";
    private static final String REPLY_TO_STORY_FIELD = "reply_to_story";
    private static final String BOOST_ADDED_FIELD = "boost_added";
    private static final String SENDER_BOOST_COUNT_FIELD = "sender_boost_count";
    private static final String BUSINESS_CONNECTION_ID_FIELD = "business_connection_id";
    private static final String SENDER_BUSINESS_BOT_FIELD = "sender_business_bot";
    private static final String IS_FROM_OFFLINE_FIELD = "is_from_offline";
    private static final String CHAT_BACKGROUND_SET_FIELD = "chat_background_set";
    private static final String EFFECT_ID_FIELD = "effect_id";
    private static final String SHOW_CAPTION_ABOVE_MEDIA_FIELD = "show_caption_above_media";
    private static final String PAID_MEDIA_FIELD = "paid_media";
    private static final String REFUNDED_PAYMENT_FIELD = "refunded_payment";

    /**
     * Integer	Unique message identifier
     */
    @JsonProperty(MESSAGE_ID_FIELD)
    private Integer messageId;
    /**
     * Optional.
     * Unique identifier of a message thread or a forum topic to which the message belongs;
     * for supergroups only
     */
    @JsonProperty(MESSAGE_THREAD_ID_FIELD)
    private Integer messageThreadId;
    /**
     * Optional.
     * Sender of the message; may be empty for messages sent to channels.
     * For backward compatibility, if the message was sent on behalf of a chat, the field contains a fake sender user in non-channel chats
     */
    @JsonProperty(FROM_FIELD)
    private User from;
    /**
     * Date the message was sent in Unix time. It is always a positive number, representing a valid date.
     */
    @JsonProperty(DATE_FIELD)
    private Integer date;
    /**
     * Conversation the message belongs to
     */
    @JsonProperty(CHAT_FIELD)
    private Chat chat;
    /**
     * Optional.
     * For forwarded messages, sender of the original message
     */
    @JsonProperty(FORWARD_FROM_FIELD)
    private User forwardFrom;  
    /**
     * Optional.
     * For messages forwarded from channels or from anonymous administrators, information about the original sender chat
     */
    @JsonProperty(FORWARD_FROM_CHAT_FIELD)
    private Chat forwardFromChat;
    /**
     * Optional.
     * For forwarded messages, date the original message was sent
     */
    @JsonProperty(FORWARD_DATE_FIELD)
    private Integer forwardDate;
    /**
     * Optional.
     * For text messages, the actual UTF-8 text of the message
     */
    @JsonProperty(TEXT_FIELD)
    private String text;  
    /**
     * Optional.
     * For text messages, special entities like usernames, URLs,
     * bot commands, etc. that appear in the text
     */
    @JsonProperty(ENTITIES_FIELD)
    private List<MessageEntity> entities;
    /**
     * Optional.
     * For messages with a caption, special entities like usernames,
     * URLs, bot commands, etc. that appear in the caption
     */
    @JsonProperty(CAPTION_ENTITIES_FIELD)
    private List<MessageEntity> captionEntities;
    /**
     * Optional.
     * Message is an audio file, information about the file
     */
    @JsonProperty(AUDIO_FIELD)
    private Audio audio;
    /**
     * Optional.
     * Message is a general file, information about the file
     */
    @JsonProperty(DOCUMENT_FIELD)
    private Document document;
    /**
     * Optional.
     * Message is a photo, available sizes of the photo
     */
    @JsonProperty(PHOTO_FIELD)
    private List<PhotoSize> photo;
    /**
     * Optional.
     * Message is a sticker, information about the sticker
     */
    @JsonProperty(STICKER_FIELD)
    private Sticker sticker;
    /**
     * Optional.
     * Message is a video, information about the video
     */
    @JsonProperty(VIDEO_FIELD)
    private Video video;
    /**
     * Optional.
     * Message is a shared contact, information about the contact
     */
    @JsonProperty(CONTACT_FIELD)
    private Contact contact;
    /**
     * Optional.
     * Message is a shared location, information about the location
     */
    @JsonProperty(LOCATION_FIELD)
    private Location location;
    /**
     * Optional.
     * Message is a venue, information about the venue
     */
    @JsonProperty(VENUE_FIELD)
    private Venue venue;
    /**
     * Optional.
     * Message is an animation, information about the animation.
     * For backward compatibility, when this field is set, the document field will be also set
     */
    @JsonProperty(ANIMATION_FIELD)
    private Animation animation;
    /**
     * Optional.
     * Specified message was pinned. Note that the Message object in this field will not contain further reply_to_message fields even if it is itself a reply.
     */
    @JsonProperty(PINNED_MESSAGE_FIELD)
    private MaybeInaccessibleMessage pinnedMessage;
    /**
     * Optional.
     * New members were added to the group or supergroup, information about them (the bot itself may be one of these members)
     */
    @JsonProperty(NEW_CHAT_MEMBERS_FIELD)
    private List<User> newChatMembers;
    /**
     * Optional.
     * A member was removed from the group, information about them (this member may be bot itself)
     */
    @JsonProperty(LEFT_CHAT_MEMBER_FIELD)
    private User leftChatMember;  
    /**
     * Optional.
     * A chat title was changed to this value
     */
    @JsonProperty(NEW_CHAT_TITLE_FIELD)
    private String newChatTitle;  
    /**
     * Optional.
     * A chat photo was change to this value
     */
    @JsonProperty(NEW_CHAT_PHOTO_FIELD)
    private List<PhotoSize> newChatPhoto;
    /**
     * Optional.
     * Informs that the chat photo was deleted
     */
    @JsonProperty(DELETE_CHAT_PHOTO_FIELD)
    private Boolean deleteChatPhoto;  
    /**
     * Optional.
     * Informs that the group has been created
     */
    @JsonProperty(GROUP_CHAT_CREATED_FIELD)
    private Boolean groupchatCreated;
    /**
     * Optional.
     * For replies in the same chat and message thread, the original message.
     * Note that the Message object in this field will not contain further reply_to_message fields even if it itself is a reply.
     */
    @JsonProperty(REPLY_TO_MESSAGE_FIELD)
    private Message replyToMessage;
    /**
     * Optional.
     * Message is a voice message, information about the file
     */
    @JsonProperty(VOICE_FIELD)
    private Voice voice;
    /**
     * Optional.
     * Caption for the document, photo or video, 0-200 characters
     */
    @JsonProperty(CAPTION_FIELD)
    private String caption;  
    /**
     * Optional. Service message: the supergroup has been created.
     * This field can‘t be received in a message coming through updates,
     * because bot can’t be a member of a supergroup when it is created.
     * It can only be found in reply_to_message
     * if someone replies to a very first message in a directly created supergroup.
     */
    @JsonProperty(SUPER_GROUP_CREATED_FIELD)
    private Boolean superGroupCreated;
    /**
     * Optional. Service message: the channel has been created.
     * This field can‘t be received in a message coming through updates,
     * because bot can’t be a member of a channel when it is created.
     * It can only be found in reply_to_message if someone
     * replies to a very first message in a channel.
     */
    @JsonProperty(CHANNEL_CHAT_CREATED_FIELD)
    private Boolean channelChatCreated;
    /**
     * Optional. The group has been migrated to a supergroup with the specified identifier.
     * This number may be greater than 32 bits and some programming languages
     * may have difficulty/silent defects in interpreting it.
     * But it smaller than 52 bits, so a signed 64 bit integer or double-precision
     * float type are safe for storing this identifier.
     */
    @JsonProperty(MIGRATE_TO_CHAT_FIELD)
    private Long migrateToChatId;  
    /**
     * Optional. The supergroup has been migrated from a group with the specified identifier.
     * This number may be greater than 32 bits and some programming languages
     * may have difficulty/silent defects in interpreting it.
     * But it smaller than 52 bits, so a signed 64 bit integer or double-precision
     * float type are safe for storing this identifier.
     */
    @JsonProperty(MIGRATE_FROM_CHAT_FIELD)
    private Long migrateFromChatId;
    /**
     * Optional.
     * Date the message was last edited in Unix time
     */
    @JsonProperty(EDIT_DATE_FIELD)
    private Integer editDate;
    /**
     * Optional.
     * Message is a game, information about the game
     */
    @JsonProperty(GAME_FIELD)
    private Game game;  
    /**
     * Optional.
     * For forwarded channel posts, identifier of the original message in the channel
     */
    @JsonProperty(FORWARD_FROM_MESSAGE_ID_FIELD)
    private Integer forwardFromMessageId;
    /**
     * Optional.
     * Message is an invoice for a payment, information about the invoice.
     */
    @JsonProperty(INVOICE_FIELD)
    private Invoice invoice;  
    /**
     * Optional.
     * Message is a service message about a successful payment, information about the payment.
     */
    @JsonProperty(SUCCESSFUL_PAYMENT_FIELD)
    private SuccessfulPayment successfulPayment;
    /**
     * Optional.
     * Message is a video note, information about the video message
     */
    @JsonProperty(VIDEO_NOTE_FIELD)
    private VideoNote videoNote;
    /**
     * Optional.
     * Signature of the post author for messages in channels, or the custom title of an anonymous group administrator
     */
    @JsonProperty(AUTHOR_SIGNATURE_FIELD)
    private String authorSignature;
    /**
     * Optional.
     * Post author signature for messages forwarded from channel chats
     */
    @JsonProperty(FORWARD_SIGNATURE_FIELD)
    private String forwardSignature;
    /**
     * Optional.
     * The unique identifier of a media message group this message belongs to
     */
    @JsonProperty(MEDIA_GROUP_ID_FIELD)
    private String mediaGroupId;  
    /**
     * Optional.
     * The domain name of the website on which the user has logged in
     */
    @JsonProperty(CONNECTED_WEBSITE_FIELD)
    private String connectedWebsite;  
    /**
     * Optional.
     * Telegram Passport data
     */
    @JsonProperty(PASSPORT_DATA_FIELD)
    private PassportData passportData; 
    /**
     * Optional.
     * Sender's name for messages forwarded from users who disallow adding a link to their account in forwarded messages.
     */
    @JsonProperty(FORWARD_SENDER_NAME_FIELD)
    private String forwardSenderName;
    /**
     * Optional.
     * Message is a native poll, information about the poll
     */
    @JsonProperty(POLL_FIELD)
    private Poll poll;  
    /**
     * Inline keyboard attached to the message.
     *
     * @apiNote login_url buttons are represented as ordinary url buttons.
     */
    @JsonProperty(REPLY_MARKUP_FIELD)
    private InlineKeyboardMarkup replyMarkup;
    /**
     * Optional.
     * Message is a dice with random value from 1 to 6
     */
    @JsonProperty(DICE_FIELD)
    private Dice dice;
    /**
     * Optional.
     * Bot through which the message was sent
     */
    @JsonProperty(VIA_BOT_FIELD)
    private User viaBot;
    /**
     * Optional.
     * Sender of the message when sent on behalf of a chat.
     * For example, the supergroup itself for messages sent by its anonymous administrators or a linked channel
     * for messages automatically forwarded to the channel's discussion group.
     * For backward compatibility, if the message was sent on behalf of a chat,
     * the field from contains a fake sender user in non-channel chats.
     */
    @JsonProperty(SENDER_CHAT_FIELD)
    private Chat senderChat;
    /**
     * Optional.
     * Service message.
     * A user in the chat triggered another user's proximity alert while sharing Live Location.
     */
    @JsonProperty(PROXIMITY_ALERT_TRIGGERED_FIELD)
    private ProximityAlertTriggered proximityAlertTriggered;
    /**
     * Optional.
     * Service message: auto-delete timer settings changed in the chat
     */
    @JsonProperty(MESSAGE_AUTO_DELETE_TIMER_CHANGED_FIELD)
    private MessageAutoDeleteTimerChanged messageAutoDeleteTimerChanged;
    /**
     * Optional.
     * True, if the message is a channel post that was automatically forwarded to the connected discussion group
     */
    @JsonProperty(IS_AUTOMATIC_FORWARD_FIELD)
    private Boolean isAutomaticForward;
    /**
     * Optional.
     * True, if the message can't be forwarded
     */
    @JsonProperty(HAS_PROTECTED_CONTENT_FIELD)
    private Boolean hasProtectedContent;
    /**
     * Optional.
     * Service message: data sent by a web app
     */
    @JsonProperty(WEB_APP_DATA_FIELD)
    private WebAppData webAppData;  
    /**
     * Optional.
     * Service message: video chat started
     */
    @JsonProperty(VIDEO_CHAT_STARTED_FIELD)
    private VideoChatStarted videoChatStarted;
    /**
     * Optional.
     * Service message: video chat ended
     */
    @JsonProperty(VIDEO_CHAT_ENDED_FIELD)
    private VideoChatEnded videoChatEnded;  
    /**
     * Optional.
     * Service message: new participants invited to a video chat
     */
    @JsonProperty(VIDEO_CHAT_PARTICIPANTS_INVITED_FIELD)
    private VideoChatParticipantsInvited videoChatParticipantsInvited;
    /**
     * Optional.
     * Service message: video chat scheduled
     */
    @JsonProperty(VIDEO_CHAT_SCHEDULED_FIELD)
    private VideoChatScheduled videoChatScheduled;
    /**
     * Optional.
     * True, if the message is sent to a forum topic
     */
    @JsonProperty(IS_TOPIC_MESSAGE_FIELD)
    private Boolean isTopicMessage;
    /**
     * Optional.
     * Service message: forum topic created
     */
    @JsonProperty(FORUM_TOPIC_CREATED_FIELD)
    private ForumTopicCreated forumTopicCreated;
    /**
     * Optional.
     * Service message: forum topic closed
     */
    @JsonProperty(FORUM_TOPIC_CLOSED_FIELD)
    private ForumTopicClosed forumTopicClosed;
    /**
     * Optional.
     * Service message: forum topic reopened
     */
    @JsonProperty(FORUM_TOPIC_REOPENED_FIELD)
    private ForumTopicReopened forumTopicReopened;
    /**
     * Optional.
     * Service message: forum topic edited
     */
    @JsonProperty(FORUM_TOPIC_EDITED_FIELD)
    private ForumTopicEdited forumTopicEdited;
    /**
     * Optional.
     * Service message: General forum topic hidden
     */
    @JsonProperty(GENERAL_FORUM_TOPIC_HIDDEN_FIELD)
    private GeneralForumTopicHidden generalForumTopicHidden;
    /**
     * Optional.
     * Service message: General forum topic unhidden
     */
    @JsonProperty(GENERAL_FORUM_TOPIC_UNHIDDEN_FIELD)
    private GeneralForumTopicUnhidden generalForumTopicUnhidden;
    /**
     * 	Optional.
     * 	Service message: the user allowed the bot to write messages after adding it to the attachment or side menu,
     * 	launching a Web App from a link, or accepting an explicit request from a Web App sent by the method requestWriteAccess
     */
    @JsonProperty(WRITE_ACCESS_ALLOWED_FIELD)
    private WriteAccessAllowed writeAccessAllowed;
    /**
     * Optional.
     * True, if the message media is covered by a spoiler animation
     */
    @JsonProperty(HAS_MEDIA_SPOILER_FIELD)
    private Boolean hasMediaSpoiler;
    /**
     * Optional.
     * Service message: a user was shared with the bot
     */
    @JsonProperty(USER_SHARED_FIELD)
    private UserShared userShared;
    /**
     * Optional.
     * Service message: a chat was shared with the bot
     */
    @JsonProperty(CHAT_SHARED_FIELD)
    private ChatShared chatShared;
    /**
     * Optional.
     * Message is a forwarded story
     */
    @JsonProperty(STORY_FIELD)
    private Story story;
    /**
     * Optional.
     * Information about the message that is being replied to, which may come from another chat or forum topic
     */
    @JsonProperty(EXTERNAL_REPLY_FIELD)
    private ExternalReplyInfo externalReplyInfo;
    /**
     * Optional.
     * Information about the original message for forwarded messages
     */
    @JsonProperty(FORWARD_ORIGIN_FIELD)
    private MessageOrigin forwardOrigin;
    /**
     * Optional.
     * Options used for link preview generation for the message, if it is a text message and link preview options were changed
     */
    @JsonProperty(LINK_PREVIEW_OPTIONS_FIELD)
    private LinkPreviewOptions linkPreviewOptions;
    /**
     * Optional.
     * For replies that quote part of the original message, the quoted part of the message
     */
    @JsonProperty(QUOTE_FIELD)
    private TextQuote quote;
    /**
     * Optional.
     * Service message: users were shared with the bot
     */
    @JsonProperty(USERS_SHARED_FIELD)
    private UsersShared usersShared;
    /**
     * Optional.
     * Service message: a scheduled giveaway was created
     */
    @JsonProperty(GIVEAWAY_CREATED_FIELD)
    private GiveawayCreated giveawayCreated;
    /**
     * Optional.
     * The message is a scheduled giveaway message
     */
    @JsonProperty(GIVEAWAY_FIELD)
    private Giveaway giveaway;
    /**
     * Optional.
     * A giveaway with public winners was completed
     */
    @JsonProperty(GIVEAWAY_WINNERS_FIELD)
    private GiveawayWinners giveawayWinners;
    /**
     * Optional.
     * Service message: a giveaway without public winners was completed
     */
    @JsonProperty(GIVEAWAY_COMPLETED_FIELD)
    private GiveawayCompleted giveawayCompleted;
    /**
     * Optional.
     * For replies to a story, the original message
     */
    @JsonProperty(REPLY_TO_STORY_FIELD)
    private Story replyToStory;
    /**
     * Optional.
     * The message is a service message about a user boosting the chat
     */
    @JsonProperty(BOOST_ADDED_FIELD)
    private ChatBoostAdded boostAdded;
    /**
     * Optional.
     * If the sender of the message boosted the chat, the number of boosts added by the user
     */
    @JsonProperty(SENDER_BOOST_COUNT_FIELD)
    private Integer senderBoostCount;
    /**
     * Optional.
     * Unique identifier of the business connection from which the message was received.
     * If non-empty, the message belongs to a chat of the corresponding business account that is independent of any
     * potential bot chat which might share the same identifier.
     */
    @JsonProperty(BUSINESS_CONNECTION_ID_FIELD)
    private String businessConnectionId;
    /**
     * Optional.
     * The bot that actually sent the message on behalf of the business account.
     * Available only for outgoing messages sent on behalf of the connected business account.
     */
    @JsonProperty(SENDER_BUSINESS_BOT_FIELD)
    private User senderBusinessBot;
    /**
     * Optional.
     * True, if the message was sent by an implicit action, for example, as an away or a greeting business message,
     * or as a scheduled message
     */
    @JsonProperty(IS_FROM_OFFLINE_FIELD)
    private Boolean isFromOffline;
    /**
     * Optional.
     * Service message: chat background set
     */
    @JsonProperty(CHAT_BACKGROUND_SET_FIELD)
    private ChatBackground chatBackgroundSet;
    /**
     * Optional.
     * Unique identifier of the message effect added to the message
     */
    @JsonProperty(EFFECT_ID_FIELD)
    private String effectId;
    /**
     * Optional.
     * True, if the caption must be shown above the message media
     */
    @JsonProperty(SHOW_CAPTION_ABOVE_MEDIA_FIELD)
    private Boolean showCaptionAboveMedia;
    /**
     * 	Optional.
     * 	Message contains paid media; information about the paid media
     */
    @JsonProperty(PAID_MEDIA_FIELD)
    private PaidMediaInfo paidMedia;
    /**
     * 	Optional.
     * 	Message is a service message about a refunded payment, information about the payment.
     */
    @JsonProperty(REFUNDED_PAYMENT_FIELD)
    private RefundedPayment refundedPayment;

    public List<MessageEntity> getEntities() {
        if (entities != null) {
            entities.forEach(x -> x.computeText(text));
        }
        return entities;
    }

    public List<MessageEntity> getCaptionEntities() {
        if (captionEntities != null) {
            captionEntities.forEach(x -> x.computeText(caption));
        }
        return captionEntities;
    }

    @JsonIgnore
    public List<User> getNewChatMembers() {
        return newChatMembers == null ? new ArrayList<>() : newChatMembers;
    }

    @JsonIgnore
    public boolean hasSticker() {
        return sticker != null;
    }

    @JsonIgnore
    public boolean isTopicMessage() {
        return isTopicMessage != null && isTopicMessage;
    }

    @JsonIgnore
    @Override
    public boolean isGroupMessage() {
        return chat.isGroupChat();
    }

    @JsonIgnore
    @Override
    public boolean isUserMessage() {
        return chat.isUserChat();
    }

    @JsonIgnore
    public boolean isChannelMessage() {
        return chat.isChannelChat();
    }

    @JsonIgnore
    @Override
    public boolean isSuperGroupMessage() {
        return chat.isSuperGroupChat();
    }

    @JsonIgnore
    @Override
    public Long getChatId() {
        return chat.getId();
    }

    @JsonIgnore
    public boolean hasText() {
        return text != null && !text.isEmpty();
    }

    @JsonIgnore
    public boolean isCommand() {
        if (hasText() && entities != null) {
            for (MessageEntity entity : entities) {
                if (entity != null && entity.getOffset() == 0 &&
                        EntityType.BOTCOMMAND.equals(entity.getType())) {
                    return true;
                }
            }
        }
        return false;
    }

    @JsonIgnore
    public boolean hasDocument() {
        return this.document != null;
    }

    @JsonIgnore
    public boolean hasVideo() {
        return this.video != null;
    }

    @JsonIgnore
    public boolean hasAudio(){
        return this.audio != null;
    }

    @JsonIgnore
    public boolean hasVoice(){
        return this.voice != null;
    }

    @JsonIgnore
    public boolean isReply() {
        return this.replyToMessage != null;
    }

    @JsonIgnore
    public boolean hasLocation() {
        return location != null;
    }

    @JsonIgnore
    public boolean hasGame() {
        return game != null;
    }

    @JsonIgnore
    public boolean hasEntities() {
        return entities != null && !entities.isEmpty();
    }

    @JsonIgnore
    public boolean hasPhoto() {
        return photo != null && !photo.isEmpty();
    }

    @JsonIgnore
    public boolean hasInvoice() {
        return invoice != null;
    }

    @JsonIgnore
    public boolean hasSuccessfulPayment() {
        return successfulPayment != null;
    }

    @JsonIgnore
    public boolean hasContact() {
        return contact != null;
    }

    @JsonIgnore
    public boolean hasVideoNote() {
        return videoNote != null;
    }

    @JsonIgnore
    public boolean hasPassportData() {
        return passportData != null;
    }

    @JsonIgnore
    public boolean hasAnimation() {
        return animation != null;
    }

    @JsonIgnore
    public boolean hasPoll() {
        return poll != null;
    }

    @JsonIgnore
    public boolean hasDice() {
        return dice != null;
    }

    @JsonIgnore
    public boolean hasViaBot() {
        return viaBot != null;
    }

    @JsonIgnore
    public boolean hasReplyMarkup() {
        return replyMarkup != null;
    }

    @JsonIgnore
    public boolean hasMessageAutoDeleteTimerChanged() {
        return messageAutoDeleteTimerChanged != null;
    }

    @JsonIgnore
    public boolean hasWebAppData() {
        return webAppData != null;
    }

    @JsonIgnore
    public boolean hasVideoChatStarted() {
        return videoChatStarted != null;
    }

    @JsonIgnore
    public boolean hasVideoChatEnded() {
        return videoChatEnded != null;
    }

    @JsonIgnore
    public boolean hasVideoChatScheduled() {
        return videoChatScheduled != null;
    }

    @JsonIgnore
    public boolean hasVideoChatParticipantsInvited() {
        return videoChatParticipantsInvited != null;
    }

    @JsonIgnore
    public boolean hasForumTopicCreated() {
        return forumTopicCreated != null;
    }

    @JsonIgnore
    public boolean hasForumTopicClosed() {
        return forumTopicClosed != null;
    }

    @JsonIgnore
    public boolean hasForumTopicReopened() {
        return forumTopicReopened != null;
    }

    @JsonIgnore
    public boolean hasUserShared() {
        return userShared != null;
    }

    @JsonIgnore
    public boolean hasChatShared() {
        return chatShared != null;
    }

    @JsonIgnore
    public boolean hasStory() {
        return story != null;
    }

    @JsonIgnore
    public boolean hasWriteAccessAllowed() {
        return writeAccessAllowed != null;
    }

    @JsonIgnore
    public boolean hasReplyToStory() {
        return replyToStory != null;
    }

    @JsonIgnore
    public boolean hasBoostAdded() {
        return boostAdded != null;
    }

    @JsonIgnore
    public boolean hasPaidMedia() {
        return paidMedia != null;
    }

    @JsonIgnore
    public boolean hasCaption() {
        return caption != null;
    }
}
