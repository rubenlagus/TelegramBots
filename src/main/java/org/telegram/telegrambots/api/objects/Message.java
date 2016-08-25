package org.telegram.telegrambots.api.objects;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.jsontype.TypeSerializer;

import org.json.JSONArray;
import org.json.JSONObject;
import org.telegram.telegrambots.api.interfaces.IBotApiObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Ruben Bermudez
 * @version 1.0
 * @brief This object represents a message.
 * @date 20 of June of 2015
 */
public class Message implements IBotApiObject {
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
    @JsonProperty(ENTITIES_FIELD)
    /**
     * Optional. For text messages, special entities like usernames, URLs,
     * bot commands, etc. that appear in the text
     */
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
    @JsonProperty(SUPERGROUPCREATED_FIELD)
    /**
     * Optional. Service message: the supergroup has been created.
     * This field can‘t be received in a message coming through updates,
     * because bot can’t be a member of a supergroup when it is created.
     * It can only be found in reply_to_message
     * if someone replies to a very first message in a directly created supergroup.
     */
    private Boolean superGroupCreated;
    @JsonProperty(CHANNELCHATCREATED_FIELD)
    /**
     * Optional. Service message: the channel has been created.
     * This field can‘t be received in a message coming through updates,
     * because bot can’t be a member of a channel when it is created.
     * It can only be found in reply_to_message if someone
     * replies to a very first message in a channel.
     */
    private Boolean channelChatCreated;
    @JsonProperty(MIGRATETOCHAT_FIELD)
    /**
     * Optional. The group has been migrated to a supergroup with the specified identifier.
     * This number may be greater than 32 bits and some programming languages
     * may have difficulty/silent defects in interpreting it.
     * But it smaller than 52 bits, so a signed 64 bit integer or double-precision
     * float type are safe for storing this identifier.
     */
    private Long migrateToChatId; ///< Optional. The chat has been migrated to a chat with specified identifier, not exceeding 1e13 by absolute value
    @JsonProperty(MIGRATEFROMCHAT_FIELD)
    /**
     * Optional. The supergroup has been migrated from a group with the specified identifier.
     * This number may be greater than 32 bits and some programming languages
     * may have difficulty/silent defects in interpreting it.
     * But it smaller than 52 bits, so a signed 64 bit integer or double-precision
     * float type are safe for storing this identifier.
     */
    private Long migrateFromChatId; ///< Optional. The chat has been migrated from a chat with specified identifier, not exceeding 1e13 by absolute value
    @JsonProperty(EDITDATE_FIELD)
    private Integer editDate; ///< Optional. Date the message was last edited in Unix time

    public Message() {
        super();
    }

    public Message(JSONObject jsonObject) {
        super();
        this.messageId = jsonObject.getInt(MESSAGEID_FIELD);
        if (jsonObject.has(FROM_FIELD)) {
            this.from = new User(jsonObject.getJSONObject(FROM_FIELD));
        }
        if (jsonObject.has(DATE_FIELD)) {
            this.date = jsonObject.getInt(DATE_FIELD);
        }
        this.chat = new Chat(jsonObject.getJSONObject(CHAT_FIELD));
        if (jsonObject.has(FORWARDFROMCHAT_FIELD)) {
            this.forwardFromChat = new Chat(jsonObject.getJSONObject(FORWARDFROMCHAT_FIELD));
        }
        if (jsonObject.has(FORWARDFROM_FIELD)) {
            this.forwardFrom = new User(jsonObject.getJSONObject(FORWARDFROM_FIELD));
        }
        if (jsonObject.has(FORWARDDATE_FIELD)) {
            this.forwardDate = jsonObject.getInt(FORWARDDATE_FIELD);
        }
        if (jsonObject.has(TEXT_FIELD)) {
            this.text = jsonObject.getString(TEXT_FIELD);
        }
        if (jsonObject.has(ENTITIES_FIELD)) {
            this.entities = new ArrayList<>();
            JSONArray entities = jsonObject.getJSONArray(ENTITIES_FIELD);
            for (int i = 0; i < entities.length(); i++) {
                this.entities.add(new MessageEntity(entities.getJSONObject(i)));
            }
        }
        if (jsonObject.has(AUDIO_FIELD)) {
            this.audio = new Audio(jsonObject.getJSONObject(AUDIO_FIELD));
        }
        if (jsonObject.has(DOCUMENT_FIELD)) {
            this.document = new Document(jsonObject.getJSONObject(DOCUMENT_FIELD));
        }
        if (jsonObject.has(PHOTO_FIELD)) {
            this.photo = new ArrayList<>();
            JSONArray photos = jsonObject.getJSONArray(PHOTO_FIELD);
            for (int i = 0; i < photos.length(); i++) {
                this.photo.add(new PhotoSize(photos.getJSONObject(i)));
            }
        }
        if (jsonObject.has(STICKER_FIELD)) {
            this.sticker = new Sticker(jsonObject.getJSONObject(STICKER_FIELD));
        }
        if (jsonObject.has(VIDEO_FIELD)) {
            this.video = new Video(jsonObject.getJSONObject(VIDEO_FIELD));
        }
        if (jsonObject.has(CONTACT_FIELD)) {
            this.contact = new Contact(jsonObject.getJSONObject(CONTACT_FIELD));
        }
        if (jsonObject.has(LOCATION_FIELD)) {
            this.location = new Location(jsonObject.getJSONObject(LOCATION_FIELD));
        }
        if (jsonObject.has(VENUE_FIELD)) {
            venue = new Venue(jsonObject.getJSONObject(VENUE_FIELD));
        }
        if (jsonObject.has(PINNED_MESSAGE_FIELD)) {
            pinnedMessage = new Message(jsonObject.getJSONObject(PINNED_MESSAGE_FIELD));
        }
        if (jsonObject.has(VOICE_FIELD)) {
            this.voice = new Voice(jsonObject.getJSONObject(VOICE_FIELD));
        }
        if (jsonObject.has(CAPTION_FIELD)) {
            this.caption = jsonObject.getString(CAPTION_FIELD);
        }
        if (jsonObject.has(NEWCHATMEMBER_FIELD)) {
            this.newChatMember = new User(jsonObject.getJSONObject(NEWCHATMEMBER_FIELD));
        }
        if (jsonObject.has(LEFTCHATMEMBER_FIELD)) {
            this.leftChatMember = new User(jsonObject.getJSONObject(LEFTCHATMEMBER_FIELD));
        }
        if (jsonObject.has(REPLYTOMESSAGE_FIELD)) {
            this.replyToMessage = new Message(jsonObject.getJSONObject(REPLYTOMESSAGE_FIELD));
        }
        if (jsonObject.has(NEWCHATTITLE_FIELD)) {
            this.newChatTitle = jsonObject.getString(NEWCHATTITLE_FIELD);
        }
        if (jsonObject.has(NEWCHATPHOTO_FIELD)) {
            JSONArray photoArray = jsonObject.getJSONArray(NEWCHATPHOTO_FIELD);
            this.newChatPhoto = new ArrayList<>();
            for (int i = 0; i < photoArray.length(); i++) {
                this.newChatPhoto.add(new PhotoSize(photoArray.getJSONObject(i)));
            }
        }
        if (jsonObject.has(DELETECHATPHOTO_FIELD)) {
            this.deleteChatPhoto = true;
        }
        if (jsonObject.has(GROUPCHATCREATED_FIELD)) {
            this.groupchatCreated = true;
        }
        if (jsonObject.has(SUPERGROUPCREATED_FIELD)) {
            this.superGroupCreated = true;
        }
        if (jsonObject.has(CHANNELCHATCREATED_FIELD)) {
            this.channelChatCreated = true;
        }
        if (jsonObject.has(MIGRATETOCHAT_FIELD)) {
            this.migrateToChatId = jsonObject.getLong(MIGRATETOCHAT_FIELD);
        }
        if (jsonObject.has(MIGRATEFROMCHAT_FIELD)) {
            this.migrateFromChatId = jsonObject.getLong(MIGRATEFROMCHAT_FIELD);
        }
        if (jsonObject.has(EDITDATE_FIELD)) {
            editDate = jsonObject.getInt(EDITDATE_FIELD);
        }

        if (hasText() && entities != null) {
            entities.forEach(x -> x.computeText(text));
        }
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

    @Override
    public void serialize(JsonGenerator gen, SerializerProvider serializers) throws IOException {
        gen.writeStartObject();
        gen.writeNumberField(MESSAGEID_FIELD, messageId);
        if (from != null) {
            gen.writeObjectField(FROM_FIELD, from);
        }
        if (date != null) {
            gen.writeNumberField(DATE_FIELD, date);
        }
        gen.writeObjectField(CHAT_FIELD, chat);
        if (forwardFromChat != null) {
            gen.writeObjectField(FORWARDFROMCHAT_FIELD, forwardFromChat);
        }
        if (forwardFrom != null) {
            gen.writeObjectField(FORWARDFROM_FIELD, forwardFrom);
        }
        if (forwardDate != null) {
            gen.writeNumberField(FORWARDDATE_FIELD, forwardDate);
        }
        if (text != null) {
            gen.writeStringField(TEXT_FIELD, text);
        }
        if (audio != null) {
            gen.writeObjectField(AUDIO_FIELD, audio);
        }
        if (document != null) {
            gen.writeObjectField(DOCUMENT_FIELD, document);
        }
        if (photo != null && photo.size() > 0) {
            gen.writeArrayFieldStart(PHOTO_FIELD);
            for (PhotoSize photoSize : photo) {
                gen.writeObject(photoSize);
            }
            gen.writeEndArray();
        }
        if (sticker != null) {
            gen.writeObjectField(STICKER_FIELD, sticker);
        }
        if (video != null) {
            gen.writeObjectField(VIDEO_FIELD, video);
        }
        if (contact != null) {
            gen.writeObjectField(CONTACT_FIELD, contact);
        }
        if (location != null) {
            gen.writeObjectField(LOCATION_FIELD, location);
        }
        if (venue != null) {
            gen.writeObjectField(VENUE_FIELD, venue);
        }
        if (voice != null) {
            gen.writeObjectField(VOICE_FIELD, voice);
        }
        if (caption != null) {
            gen.writeObjectField(CAPTION_FIELD, caption);
        }
        if (newChatMember != null) {
            gen.writeObjectField(NEWCHATMEMBER_FIELD, newChatMember);
        }
        if (leftChatMember != null) {
            gen.writeObjectField(LEFTCHATMEMBER_FIELD, leftChatMember);
        }
        if (replyToMessage != null) {
            gen.writeObjectField(REPLYTOMESSAGE_FIELD, replyToMessage);
        }
        if (newChatTitle != null) {
            gen.writeStringField(NEWCHATTITLE_FIELD, newChatTitle);
        }
        if (newChatPhoto != null && newChatPhoto.size() > 0) {
            gen.writeArrayFieldStart(NEWCHATPHOTO_FIELD);
            for (PhotoSize photoSize: newChatPhoto) {
                gen.writeObject(photoSize);
            }
            gen.writeEndArray();
        }
        if (deleteChatPhoto != null) {
            gen.writeBooleanField(DELETECHATPHOTO_FIELD, deleteChatPhoto);
        }
        if (groupchatCreated != null) {
            gen.writeBooleanField(GROUPCHATCREATED_FIELD, groupchatCreated);
        }
        if (superGroupCreated != null) {
            gen.writeBooleanField(SUPERGROUPCREATED_FIELD, superGroupCreated);
        }
        if (channelChatCreated != null) {
            gen.writeBooleanField(CHANNELCHATCREATED_FIELD, channelChatCreated);
        }
        if (migrateToChatId != null) {
            gen.writeNumberField(MIGRATETOCHAT_FIELD, migrateToChatId);
        }
        if (migrateFromChatId != null) {
            gen.writeNumberField(MIGRATEFROMCHAT_FIELD, migrateFromChatId);
        }
        if (editDate != null) {
            gen.writeNumberField(EDITDATE_FIELD, editDate);
        }
        gen.writeEndObject();
        gen.flush();
    }

    @Override
    public void serializeWithType(JsonGenerator gen, SerializerProvider serializers, TypeSerializer typeSer) throws IOException {
        serialize(gen, serializers);
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
                ", audio=" + audio +
                ", document=" + document +
                ", photo=" + photo +
                ", sticker=" + sticker +
                ", video=" + video +
                ", contact=" + contact +
                ", location=" + location +
                ", venue=" + venue +
                ", newChatMember=" + newChatMember +
                ", leftChatMember=" + leftChatMember +
                ", newChatTitle='" + newChatTitle + '\'' +
                ", newChatPhoto=" + newChatPhoto +
                ", deleteChatPhoto=" + deleteChatPhoto +
                ", groupchatCreated=" + groupchatCreated +
                ", replyToMessage=" + replyToMessage +
                ", voice=" + voice +
                ", caption=" + caption +
                ", superGroupCreated=" + superGroupCreated +
                ", channelChatCreated=" + channelChatCreated +
                ", migrateToChatId=" + migrateToChatId +
                ", migrateFromChatId=" + migrateFromChatId +
                ", editDate=" + editDate +
                '}';
    }
}
