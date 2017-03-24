package org.telegram.telegrambots.api.objects;



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


    private Integer message_id; ///< Integer	Unique message identifier

    private User from; ///< Optional. Sender, can be empty for messages sent to channels

    private Integer date; ///< Optional. Date the message was sent in Unix time

    private Chat chat; ///< Conversation the message belongs to

    private User forward_from; ///< Optional. For forwarded messages, sender of the original message

    private Chat forward_from_chat; ///< Optional. For messages forwarded from a channel, information about the original channel

    private Integer forward_date; ///< Optional. For forwarded messages, date the original message was sent

    private String text; ///< Optional. For text messages, the actual UTF-8 text of the message
    /**
     * Optional. For text messages, special entities like usernames, URLs,
     * bot commands, etc. that appear in the text
     */

    private List<MessageEntity> entities;

    private Audio audio; ///< Optional. Message is an audio file, information about the file

    private Document document; ///< Optional. Message is a general file, information about the file

    private List<PhotoSize> photo; ///< Optional. Message is a photo, available sizes of the photo

    private Sticker sticker; ///< Optional. Message is a sticker, information about the sticker

    private Video video; ///< Optional. Message is a video, information about the video

    private Contact contact; ///< Optional. Message is a shared contact, information about the contact

    private Location location; ///< Optional. Message is a shared location, information about the location

    private Venue venue; ///< Optional. Message is a venue, information about the venue

    private Message pinned_message; ///< Optional. Specified message was pinned. Note that the Message object in this field will not contain further reply_to_message fields even if it is itself a reply.

    private User new_chat_member; ///< Optional. A new member was added to the group, information about them (this member may be bot itself)

    private User left_chat_member; ///< Optional. A member was removed from the group, information about them (this member may be bot itself)

    private String new_chat_title; ///< Optional. A chat title was changed to this value

    private List<PhotoSize> new_chat_photo; ///< Optional. A chat photo was change to this value

    private Boolean delete_chat_photo; ///< Optional. Informs that the chat photo was deleted

    private Boolean groupchat_created; ///< Optional. Informs that the group has been created

    private Message reply_to_message;

    private Voice voice; ///< Optional. Message is a voice message, information about the file

    private String caption; ///< Optional. Caption for the document, photo or video, 0-200 characters
    /**
     * Optional. Service message: the supergroup has been created.
     * This field can‘t be received in a message coming through updates,
     * because bot can’t be a member of a supergroup when it is created.
     * It can only be found in reply_to_message
     * if someone replies to a very first message in a directly created supergroup.
     */

    private Boolean supergroup_chat_created;
    /**
     * Optional. Service message: the channel has been created.
     * This field can‘t be received in a message coming through updates,
     * because bot can’t be a member of a channel when it is created.
     * It can only be found in reply_to_message if someone
     * replies to a very first message in a channel.
     */

    private Boolean channel_chat_created;
    /**
     * Optional. The group has been migrated to a supergroup with the specified identifier.
     * This number may be greater than 32 bits and some programming languages
     * may have difficulty/silent defects in interpreting it.
     * But it smaller than 52 bits, so a signed 64 bit integer or double-precision
     * float type are safe for storing this identifier.
     */

    private Long migrate_to_chat_id; ///< Optional. The chat has been migrated to a chat with specified identifier, not exceeding 1e13 by absolute value
    /**
     * Optional. The supergroup has been migrated from a group with the specified identifier.
     * This number may be greater than 32 bits and some programming languages
     * may have difficulty/silent defects in interpreting it.
     * But it smaller than 52 bits, so a signed 64 bit integer or double-precision
     * float type are safe for storing this identifier.
     */

    private Long migrate_from_chat_id; ///< Optional. The chat has been migrated from a chat with specified identifier, not exceeding 1e13 by absolute value

    private Integer edit_date; ///< Optional. Date the message was last edited in Unix time

    private Game game; ///< Optional. Message is a game, information about the game

    private Integer forward_from_message_id; ///< Optional. For forwarded channel posts, identifier of the original message in the channel

    public Message() {
        super();
    }

    public Integer getMessageId() {
        return message_id;
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
        return forward_from;
    }

    public Integer getForwardDate() {
        return forward_date;
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
        return pinned_message;
    }

    public User getNewChatMember() {
        return new_chat_member;
    }

    public User getLeftChatMember() {
        return left_chat_member;
    }

    public String getNewChatTitle() {
        return new_chat_title;
    }

    public List<PhotoSize> getNewChatPhoto() {
        return new_chat_photo;
    }

    public Boolean getDeleteChatPhoto() {
        return delete_chat_photo;
    }

    public Boolean getGroupchatCreated() {
        return groupchat_created;
    }

    public Message getReplyToMessage() {
        return reply_to_message;
    }

    public Voice getVoice() {
        return voice;
    }

    public String getCaption() {
        return caption;
    }

    public Boolean getSuperGroupCreated() {
        return supergroup_chat_created;
    }

    public Boolean getChannelChatCreated() {
        return channel_chat_created;
    }

    public Long getMigrateToChatId() {
        return migrate_to_chat_id;
    }

    public Long getMigrateFromChatId() {
        return migrate_from_chat_id;
    }

    public Integer getForwardFromMessageId() {
        return forward_from_message_id;
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
        return this.reply_to_message != null;
    }

    public boolean hasLocation() {
        return location != null;
    }

    public Chat getForwardFromChat() {
        return forward_from_chat;
    }

    public Integer getEditDate() {
        return edit_date;
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
                "messageId=" + message_id +
                ", from=" + from +
                ", date=" + date +
                ", chat=" + chat +
                ", forwardFrom=" + forward_from +
                ", forwardFromChat=" + forward_from_chat +
                ", forwardDate=" + forward_date +
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
                ", pinnedMessage=" + pinned_message +
                ", newChatMember=" + new_chat_member +
                ", leftChatMember=" + left_chat_member +
                ", newChatTitle='" + new_chat_title + '\'' +
                ", newChatPhoto=" + new_chat_photo +
                ", deleteChatPhoto=" + delete_chat_photo +
                ", groupchatCreated=" + groupchat_created +
                ", replyToMessage=" + reply_to_message +
                ", voice=" + voice +
                ", caption='" + caption + '\'' +
                ", superGroupCreated=" + supergroup_chat_created +
                ", channelChatCreated=" + channel_chat_created +
                ", migrateToChatId=" + migrate_to_chat_id +
                ", migrateFromChatId=" + migrate_from_chat_id +
                ", editDate=" + edit_date +
                ", game=" + game +
                ", forwardFromMessageId=" + forward_from_message_id +
                '}';
    }
}
