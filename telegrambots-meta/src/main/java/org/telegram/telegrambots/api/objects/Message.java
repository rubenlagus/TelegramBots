package org.telegram.telegrambots.api.objects;

import com.fasterxml.jackson.annotation.JsonProperty;

import org.telegram.telegrambots.api.interfaces.BotApiObject;
import org.telegram.telegrambots.api.objects.games.Game;

import java.util.List;

/**
 * @author Ruben Bermudez
 * @version 1.0
 * @brief This object represents a message.
 * @date 20 of June of 2015
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
    private static final String AUDIO_FIELD = "audio";
    private static final String DOCUMENT_FIELD = "document";
    private static final String PHOTO_FIELD = "photo";
    private static final String STICKER_FIELD = "sticker";
    private static final String VIDEO_FIELD = "video";
    private static final String CONTACT_FIELD = "contact";
    private static final String LOCATION_FIELD = "location";
    private static final String VENUE_FIELD = "venue";
    private static final String PINNED_MESSAGE_FIELD = "pinned_message";
    private static final String NEWCHATMEMBER_FIELD = "new_chat_member";
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
    @JsonProperty(PINNED_MESSAGE_FIELD)
    private Message pinnedMessage; ///< Optional. Specified message was pinned. Note that the Message object in this field will not contain further reply_to_message fields even if it is itself a reply.
    @JsonProperty(NEWCHATMEMBER_FIELD)
    private User newChatMember; ///< Optional. A new member was added to the group, information about them (this member may be bot itself)
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
        entities.forEach(x -> x.computeText(text));
        return entities;
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

    public Video getVideo() {
        return video;
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

    public User getNewChatMember() {
        return newChatMember;
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

    public boolean hasDocument() {
        return this.document != null;
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
                ", audio=" + audio +
                ", document=" + document +
                ", photo=" + photo +
                ", sticker=" + sticker +
                ", video=" + video +
                ", contact=" + contact +
                ", location=" + location +
                ", venue=" + venue +
                ", pinnedMessage=" + pinnedMessage +
                ", newChatMember=" + newChatMember +
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
                '}';
    }
}
