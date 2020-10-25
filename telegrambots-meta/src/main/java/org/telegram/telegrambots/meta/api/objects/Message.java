package org.telegram.telegrambots.meta.api.objects;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.telegram.telegrambots.meta.api.interfaces.BotApiObject;
import org.telegram.telegrambots.meta.api.objects.games.Animation;
import org.telegram.telegrambots.meta.api.objects.games.Game;
import org.telegram.telegrambots.meta.api.objects.passport.PassportData;
import org.telegram.telegrambots.meta.api.objects.payments.Invoice;
import org.telegram.telegrambots.meta.api.objects.payments.SuccessfulPayment;
import org.telegram.telegrambots.meta.api.objects.polls.Poll;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.stickers.Sticker;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Ruben Bermudez
 * @version 1.0
 * This object represents a message.
 */
public class Message implements BotApiObject {
    private static final String MESSAGEID_FIELD = "message_id";
    private static final String FROM_FIELD = "from";
    private static final String DATE_FIELD = "date";
    private static final String CHAT_FIELD = "chat";
    private static final String FORWARDFROM_FIELD = "forward_from";
    private static final String FORWARDFROMCHAT_FIELD = "forward_from_chat";
    private static final String FORWARDDATE_FIELD = "forward_date";
    private static final String TEXT_FIELD = "text";
    private static final String ENTITIES_FIELD = "entities";
    private static final String CAPTIONENTITIES_FIELD = "caption_entities";
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
    private static final String NEWCHATMEMBERS_FIELD = "new_chat_members";
    private static final String LEFTCHATMEMBER_FIELD = "left_chat_member";
    private static final String NEWCHATTITLE_FIELD = "new_chat_title";
    private static final String NEWCHATPHOTO_FIELD = "new_chat_photo";
    private static final String DELETECHATPHOTO_FIELD = "delete_chat_photo";
    private static final String GROUPCHATCREATED_FIELD = "group_chat_created";
    private static final String REPLYTOMESSAGE_FIELD = "reply_to_message";
    private static final String VOICE_FIELD = "voice";
    private static final String CAPTION_FIELD = "caption";
    private static final String SUPERGROUPCREATED_FIELD = "supergroup_chat_created";
    private static final String CHANNELCHATCREATED_FIELD = "channel_chat_created";
    private static final String MIGRATETOCHAT_FIELD = "migrate_to_chat_id";
    private static final String MIGRATEFROMCHAT_FIELD = "migrate_from_chat_id";
    private static final String EDITDATE_FIELD = "edit_date";
    private static final String GAME_FIELD = "game";
    private static final String FORWARDFROMMESSAGEID_FIELD = "forward_from_message_id";
    private static final String INVOICE_FIELD = "invoice";
    private static final String SUCCESSFUL_PAYMENT_FIELD = "successful_payment";
    private static final String VIDEO_NOTE_FIELD = "video_note";
    private static final String AUTHORSIGNATURE_FIELD = "author_signature";
    private static final String FORWARDSIGNATURE_FIELD = "forward_signature";
    private static final String MEDIAGROUPID_FIELD = "media_group_id";
    private static final String CONNECTEDWEBSITE_FIELD = "connected_website";
    private static final String PASSPORTDATA_FIELD = "passport_data";
    private static final String FORWARDSENDERNAME_FIELD = "forward_sender_name";
    private static final String POLL_FIELD = "poll";
    private static final String REPLY_MARKUP_FIELD = "reply_markup";
    private static final String DICE_FIELD = "dice";
    private static final String VIABOT_FIELD = "via_bot";

    @JsonProperty(MESSAGEID_FIELD)
    private Integer messageId; ///< Integer	Unique message identifier
    @JsonProperty(FROM_FIELD)
    private User from; ///< Optional. Sender, can be empty for messages sent to channels
    @JsonProperty(DATE_FIELD)
    private Integer date; ///< Optional. Date the message was sent in Unix time
    @JsonProperty(CHAT_FIELD)
    private Chat chat; ///< Conversation the message belongs to
    @JsonProperty(FORWARDFROM_FIELD)
    private User forwardFrom; ///< Optional. For forwarded messages, sender of the original message
    @JsonProperty(FORWARDFROMCHAT_FIELD)
    private Chat forwardFromChat; ///< Optional. For messages forwarded from a channel, information about the original channel
    @JsonProperty(FORWARDDATE_FIELD)
    private Integer forwardDate; ///< Optional. For forwarded messages, date the original message was sent
    @JsonProperty(TEXT_FIELD)
    private String text; ///< Optional. For text messages, the actual UTF-8 text of the message
    /**
     * Optional. For text messages, special entities like usernames, URLs,
     * bot commands, etc. that appear in the text
     */
    @JsonProperty(ENTITIES_FIELD)
    private List<MessageEntity> entities;
    /**
     * Optional. For messages with a caption, special entities like usernames,
     * URLs, bot commands, etc. that appear in the caption
     */
    @JsonProperty(CAPTIONENTITIES_FIELD)
    private List<MessageEntity> captionEntities;
    @JsonProperty(AUDIO_FIELD)
    private Audio audio; ///< Optional. Message is an audio file, information about the file
    @JsonProperty(DOCUMENT_FIELD)
    private Document document; ///< Optional. Message is a general file, information about the file
    @JsonProperty(PHOTO_FIELD)
    private List<PhotoSize> photo; ///< Optional. Message is a photo, available sizes of the photo
    @JsonProperty(STICKER_FIELD)
    private Sticker sticker; ///< Optional. Message is a sticker, information about the sticker
    @JsonProperty(VIDEO_FIELD)
    private Video video; ///< Optional. Message is a video, information about the video
    @JsonProperty(CONTACT_FIELD)
    private Contact contact; ///< Optional. Message is a shared contact, information about the contact
    @JsonProperty(LOCATION_FIELD)
    private Location location; ///< Optional. Message is a shared location, information about the location
    @JsonProperty(VENUE_FIELD)
    private Venue venue; ///< Optional. Message is a venue, information about the venue
    /**
     * Optional. Message is an animation, information about the animation.
     * For backward compatibility, when this field is set, the document field will be also set
     */
    @JsonProperty(ANIMATION_FIELD)
    private Animation animation;
    @JsonProperty(PINNED_MESSAGE_FIELD)
    private Message pinnedMessage; ///< Optional. Specified message was pinned. Note that the Message object in this field will not contain further reply_to_message fields even if it is itself a reply.
    @JsonProperty(NEWCHATMEMBERS_FIELD)
    private List<User> newChatMembers; ///< Optional. New members were added to the group or supergroup, information about them (the bot itself may be one of these members)
    @JsonProperty(LEFTCHATMEMBER_FIELD)
    private User leftChatMember; ///< Optional. A member was removed from the group, information about them (this member may be bot itself)
    @JsonProperty(NEWCHATTITLE_FIELD)
    private String newChatTitle; ///< Optional. A chat title was changed to this value
    @JsonProperty(NEWCHATPHOTO_FIELD)
    private List<PhotoSize> newChatPhoto; ///< Optional. A chat photo was change to this value
    @JsonProperty(DELETECHATPHOTO_FIELD)
    private Boolean deleteChatPhoto; ///< Optional. Informs that the chat photo was deleted
    @JsonProperty(GROUPCHATCREATED_FIELD)
    private Boolean groupchatCreated; ///< Optional. Informs that the group has been created
    @JsonProperty(REPLYTOMESSAGE_FIELD)
    private Message replyToMessage;
    @JsonProperty(VOICE_FIELD)
    private Voice voice; ///< Optional. Message is a voice message, information about the file
    @JsonProperty(CAPTION_FIELD)
    private String caption; ///< Optional. Caption for the document, photo or video, 0-200 characters
    /**
     * Optional. Service message: the supergroup has been created.
     * This field can‘t be received in a message coming through updates,
     * because bot can’t be a member of a supergroup when it is created.
     * It can only be found in reply_to_message
     * if someone replies to a very first message in a directly created supergroup.
     */
    @JsonProperty(SUPERGROUPCREATED_FIELD)
    private Boolean superGroupCreated;
    /**
     * Optional. Service message: the channel has been created.
     * This field can‘t be received in a message coming through updates,
     * because bot can’t be a member of a channel when it is created.
     * It can only be found in reply_to_message if someone
     * replies to a very first message in a channel.
     */
    @JsonProperty(CHANNELCHATCREATED_FIELD)
    private Boolean channelChatCreated;
    /**
     * Optional. The group has been migrated to a supergroup with the specified identifier.
     * This number may be greater than 32 bits and some programming languages
     * may have difficulty/silent defects in interpreting it.
     * But it smaller than 52 bits, so a signed 64 bit integer or double-precision
     * float type are safe for storing this identifier.
     */
    @JsonProperty(MIGRATETOCHAT_FIELD)
    private Long migrateToChatId; ///< Optional. The chat has been migrated to a chat with specified identifier, not exceeding 1e13 by absolute value
    /**
     * Optional. The supergroup has been migrated from a group with the specified identifier.
     * This number may be greater than 32 bits and some programming languages
     * may have difficulty/silent defects in interpreting it.
     * But it smaller than 52 bits, so a signed 64 bit integer or double-precision
     * float type are safe for storing this identifier.
     */
    @JsonProperty(MIGRATEFROMCHAT_FIELD)
    private Long migrateFromChatId; ///< Optional. The chat has been migrated from a chat with specified identifier, not exceeding 1e13 by absolute value
    @JsonProperty(EDITDATE_FIELD)
    private Integer editDate; ///< Optional. Date the message was last edited in Unix time
    @JsonProperty(GAME_FIELD)
    private Game game; ///< Optional. Message is a game, information about the game
    @JsonProperty(FORWARDFROMMESSAGEID_FIELD)
    private Integer forwardFromMessageId; ///< Optional. For forwarded channel posts, identifier of the original message in the channel
    @JsonProperty(INVOICE_FIELD)
    private Invoice invoice; ///< Optional. Message is an invoice for a payment, information about the invoice.
    @JsonProperty(SUCCESSFUL_PAYMENT_FIELD)
    private SuccessfulPayment successfulPayment; ///< Optional. Message is a service message about a successful payment, information about the payment.
    @JsonProperty(VIDEO_NOTE_FIELD)
    private VideoNote videoNote; ///< Optional. Message is a video note, information about the video message
    @JsonProperty(AUTHORSIGNATURE_FIELD)
    private String authorSignature; ///< Optional. Post author signature for posts in channel chats
    @JsonProperty(FORWARDSIGNATURE_FIELD)
    private String forwardSignature; ///< Optional. Post author signature for messages forwarded from channel chats
    @JsonProperty(MEDIAGROUPID_FIELD)
    private String mediaGroupId; ///< Optional. The unique identifier of a media message group this message belongs to
    @JsonProperty(CONNECTEDWEBSITE_FIELD)
    private String connectedWebsite; ///< Optional. The domain name of the website on which the user has logged in
    @JsonProperty(PASSPORTDATA_FIELD)
    private PassportData passportData; ///< Optional. Telegram Passport data
    @JsonProperty(FORWARDSENDERNAME_FIELD)
    private String forwardSenderName; ///< Optional. Sender's name for messages forwarded from users who disallow adding a link to their account in forwarded messages.
    @JsonProperty(POLL_FIELD)
    private Poll poll; ///< Optional. Message is a native poll, information about the poll
    /**
     * Inline keyboard attached to the message.
     *
     * @apiNote login_url buttons are represented as ordinary url buttons.
     */
    @JsonProperty(REPLY_MARKUP_FIELD)
    private InlineKeyboardMarkup replyMarkup;
    @JsonProperty(DICE_FIELD)
    private Dice dice; // Optional. Message is a dice with random value from 1 to 6
    @JsonProperty(VIABOT_FIELD)
    private User viaBot; // Optional. Bot through which the message was sent
    public Message() {
        super();
    }

    public Integer getMessageId() {
        return messageId;
    }

    public User getFrom() {
        return from;
    }

    public Integer getDate() {
        return date;
    }

    public Chat getChat() {
        return chat;
    }

    public User getForwardFrom() {
        return forwardFrom;
    }

    public Integer getForwardDate() {
        return forwardDate;
    }

    public String getText() {
        return text;
    }

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

    public Audio getAudio() {
        return audio;
    }

    public Document getDocument() {
        return document;
    }

    public List<PhotoSize> getPhoto() {
        return photo;
    }

    public Sticker getSticker() {
        return sticker;
    }

    public boolean hasSticker() {
        return sticker != null;
    }

    public Video getVideo() {
        return video;
    }

    public Animation getAnimation() {
        return animation;
    }

    public Contact getContact() {
        return contact;
    }

    public Location getLocation() {
        return location;
    }

    public Venue getVenue() {
        return venue;
    }

    public Message getPinnedMessage() {
        return pinnedMessage;
    }

    public List<User> getNewChatMembers() {
        return newChatMembers == null ? new ArrayList<>() : newChatMembers;
    }

    public User getLeftChatMember() {
        return leftChatMember;
    }

    public String getNewChatTitle() {
        return newChatTitle;
    }

    public List<PhotoSize> getNewChatPhoto() {
        return newChatPhoto;
    }

    public Boolean getDeleteChatPhoto() {
        return deleteChatPhoto;
    }

    public Boolean getGroupchatCreated() {
        return groupchatCreated;
    }

    public Message getReplyToMessage() {
        return replyToMessage;
    }

    public Voice getVoice() {
        return voice;
    }

    public String getCaption() {
        return caption;
    }

    public Boolean getSuperGroupCreated() {
        return superGroupCreated;
    }

    public Boolean getChannelChatCreated() {
        return channelChatCreated;
    }

    public Long getMigrateToChatId() {
        return migrateToChatId;
    }

    public Long getMigrateFromChatId() {
        return migrateFromChatId;
    }

    public Integer getForwardFromMessageId() {
        return forwardFromMessageId;
    }

    public boolean isGroupMessage() {
        return chat.isGroupChat();
    }

    public boolean isUserMessage() {
        return chat.isUserChat();
    }

    public boolean isChannelMessage() {
        return chat.isChannelChat();
    }

    public boolean isSuperGroupMessage() {
        return chat.isSuperGroupChat();
    }

    public Long getChatId() {
        return chat.getId();
    }

    public boolean hasText() {
        return text != null && !text.isEmpty();
    }

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

    public boolean hasCaption() {
        return this.caption != null;
    }

    public boolean hasDocument() {
        return this.document != null;
    }

    public boolean hasVideo() {
        return this.video != null;
    }

    public boolean hasAudio(){
        return this.audio != null;
    }

    public boolean hasVoice(){
        return this.voice != null;
    }

    public boolean isReply() {
        return this.replyToMessage != null;
    }

    public boolean hasLocation() {
        return location != null;
    }

    public Chat getForwardFromChat() {
        return forwardFromChat;
    }

    public Integer getEditDate() {
        return editDate;
    }

    public Game getGame() {
        return game;
    }

    private boolean hasGame() {
        return game != null;
    }

    public boolean hasEntities() {
        return entities != null && !entities.isEmpty();
    }

    public boolean hasPhoto() {
        return photo != null && !photo.isEmpty();
    }

    public boolean hasInvoice() {
        return invoice != null;
    }

    public boolean hasSuccessfulPayment() {
        return successfulPayment != null;
    }

    public boolean hasContact() {
        return contact != null;
    }

    public Invoice getInvoice() {
        return invoice;
    }

    public SuccessfulPayment getSuccessfulPayment() {
        return successfulPayment;
    }

    public VideoNote getVideoNote() {
        return videoNote;
    }

    public boolean hasVideoNote() {
        return videoNote != null;
    }

    public String getAuthorSignature() {
        return authorSignature;
    }

    public String getForwardSignature() {
        return forwardSignature;
    }

    public String getMediaGroupId() {
        return mediaGroupId;
    }

    public String getConnectedWebsite() {
        return connectedWebsite;
    }

    public PassportData getPassportData() {
        return passportData;
    }

    public boolean hasPassportData() {
        return passportData != null;
    }

    public boolean hasAnimation() {
        return animation != null;
    }

    public String getForwardSenderName() {
        return forwardSenderName;
    }

    public void setForwardSenderName(String forwardSenderName) {
        this.forwardSenderName = forwardSenderName;
    }

    public boolean hasPoll() {
        return poll != null;
    }

    public Poll getPoll() {
        return poll;
    }

    public Dice getDice() {
        return dice;
    }

    public boolean hasDice() {
        return dice != null;
    }

    public User getViaBot() {
        return viaBot;
    }

    public boolean hasViaBot() {
        return viaBot != null;
    }

    public boolean hasReplyMarkup() {
        return replyMarkup != null;
    }

    public InlineKeyboardMarkup getReplyMarkup() {
        return replyMarkup;
    }

    @Override
    public String toString() {
        return "Message{" +
                "messageId=" + messageId +
                ", from=" + from +
                ", date=" + date +
                ", chat=" + chat +
                ", forwardFrom=" + forwardFrom +
                ", forwardFromChat=" + forwardFromChat +
                ", forwardDate=" + forwardDate +
                ", text='" + text + '\'' +
                ", entities=" + entities +
                ", captionEntities=" + captionEntities +
                ", audio=" + audio +
                ", document=" + document +
                ", photo=" + photo +
                ", sticker=" + sticker +
                ", video=" + video +
                ", contact=" + contact +
                ", location=" + location +
                ", venue=" + venue +
                ", animation=" + animation +
                ", pinnedMessage=" + pinnedMessage +
                ", newChatMembers=" + newChatMembers +
                ", leftChatMember=" + leftChatMember +
                ", newChatTitle='" + newChatTitle + '\'' +
                ", newChatPhoto=" + newChatPhoto +
                ", deleteChatPhoto=" + deleteChatPhoto +
                ", groupchatCreated=" + groupchatCreated +
                ", replyToMessage=" + replyToMessage +
                ", voice=" + voice +
                ", caption='" + caption + '\'' +
                ", superGroupCreated=" + superGroupCreated +
                ", channelChatCreated=" + channelChatCreated +
                ", migrateToChatId=" + migrateToChatId +
                ", migrateFromChatId=" + migrateFromChatId +
                ", editDate=" + editDate +
                ", game=" + game +
                ", forwardFromMessageId=" + forwardFromMessageId +
                ", invoice=" + invoice +
                ", successfulPayment=" + successfulPayment +
                ", videoNote=" + videoNote +
                ", authorSignature='" + authorSignature + '\'' +
                ", forwardSignature='" + forwardSignature + '\'' +
                ", mediaGroupId='" + mediaGroupId + '\'' +
                ", connectedWebsite='" + connectedWebsite + '\'' +
                ", passportData=" + passportData +
                ", forwardSenderName='" + forwardSenderName + '\'' +
                ", poll=" + poll +
                ", replyMarkup=" + replyMarkup +
                ", dice=" + dice +
                ", viaBot=" + viaBot +
                '}';
    }
}
