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
    public static final String MESSAGEID_FIELD = "message_id";
    @JsonProperty(MESSAGEID_FIELD)
    private Integer messageId; ///< Integer	Unique message identifier
    public static final String FROM_FIELD = "from";
    @JsonProperty(FROM_FIELD)
    private User from; ///< Optional. Sender, can be empty for messages sent to channels
    public static final String DATE_FIELD = "date";
    @JsonProperty(DATE_FIELD)
    private Integer date; ///< Date the message was sent in Unix time
    public static final String CHAT_FIELD = "chat";
    @JsonProperty(CHAT_FIELD)
    private Chat chat; ///< Conversation the message belongs to
    public static final String FORWARDFROM_FIELD = "forward_from";
    @JsonProperty(FORWARDFROM_FIELD)
    private User forwardFrom; ///< Optional. For forwarded messages, sender of the original message
    public static final String FORWARDDATE_FIELD = "forward_date";
    @JsonProperty(FORWARDDATE_FIELD)
    private Integer forwardDate; ///< Optional. For forwarded messages, date the original message was sent
    public static final String TEXT_FIELD = "text";
    @JsonProperty(TEXT_FIELD)
    private String text; ///< Optional. For text messages, the actual UTF-8 text of the message
    public static final String AUDIO_FIELD = "audio";
    @JsonProperty(AUDIO_FIELD)
    private Audio audio; ///< Optional. Message is an audio file, information about the file
    public static final String DOCUMENT_FIELD = "document";
    @JsonProperty(DOCUMENT_FIELD)
    private Document document; ///< Optional. Message is a general file, information about the file
    public static final String PHOTO_FIELD = "photo";
    @JsonProperty(PHOTO_FIELD)
    private List<PhotoSize> photo; ///< Optional. Message is a photo, available sizes of the photo
    public static final String STICKER_FIELD = "sticker";
    @JsonProperty(STICKER_FIELD)
    private Sticker sticker; ///< Optional. Message is a sticker, information about the sticker
    public static final String VIDEO_FIELD = "video";
    @JsonProperty(VIDEO_FIELD)
    private Video video; ///< Optional. Message is a video, information about the video
    public static final String CONTACT_FIELD = "contact";
    @JsonProperty(CONTACT_FIELD)
    private Contact contact; ///< Optional. Message is a shared contact, information about the contact
    public static final String LOCATION_FIELD = "location";
    @JsonProperty(LOCATION_FIELD)
    private Location location; ///< Optional. Message is a shared location, information about the location
    public static final String NEWCHATPARTICIPANT_FIELD = "new_chat_participant";
    @JsonProperty(NEWCHATPARTICIPANT_FIELD)
    private User newChatParticipant; ///< Optional. A new member was added to the group, information about them (this member may be bot itself)
    public static final String LEFTCHATPARTICIPANT_FIELD = "left_chat_participant";
    @JsonProperty(LEFTCHATPARTICIPANT_FIELD)
    private User leftChatParticipant; ///< Optional. A member was removed from the group, information about them (this member may be bot itself)
    public static final String NEWCHATTITLE_FIELD = "new_chat_title";
    @JsonProperty(NEWCHATTITLE_FIELD)
    private String newChatTitle; ///< Optional. A chat title was changed to this value
    public static final String NEWCHATPHOTO_FIELD = "new_chat_photo";
    @JsonProperty(NEWCHATPHOTO_FIELD)
    private List<PhotoSize> newChatPhoto; ///< Optional. A chat photo was change to this value
    public static final String DELETECHATPHOTO_FIELD = "delete_chat_photo";
    @JsonProperty(DELETECHATPHOTO_FIELD)
    private Boolean deleteChatPhoto; ///< Optional. Informs that the chat photo was deleted
    public static final String GROUPCHATCREATED_FIELD = "group_chat_created";
    @JsonProperty(GROUPCHATCREATED_FIELD)
    private Boolean groupchatCreated; ///< Optional. Informs that the group has been created
    public static final String REPLYTOMESSAGE_FIELD = "reply_to_message";
    @JsonProperty(REPLYTOMESSAGE_FIELD)
    private Message replyToMessage;
    public static final String VOICE_FIELD = "voice";
    @JsonProperty(VOICE_FIELD)
    private Voice voice; ///< Optional. Message is a voice message, information about the file
    public static final String SUPERGROUPCREATED_FIELD = "supergroup_chat_created";
    @JsonProperty(SUPERGROUPCREATED_FIELD)
    private Boolean superGroupCreated; ///< Optional. Informs that the supergroup has been created
    public static final String CHANNELCHATCREATED_FIELD = "channel_chat_created";
    @JsonProperty(CHANNELCHATCREATED_FIELD)
    private Boolean channelChatCreated; ///< Optional. Informs that the channel has been created
    public static final String MIGRATETOCHAT_FIELD = "migrate_to_chat_id";
    @JsonProperty(MIGRATETOCHAT_FIELD)
    private Long migrateToChatId; ///< Optional. The chat has been migrated to a chat with specified identifier, not exceeding 1e13 by absolute value
    public static final String MIGRATEFROMCHAT_FIELD = "migrate_from_chat_id";
    @JsonProperty(MIGRATEFROMCHAT_FIELD)
    private Long migrateFromChatId; ///< Optional. The chat has been migrated from a chat with specified identifier, not exceeding 1e13 by absolute value

    public Message() {
        super();
    }

    public Message(JSONObject jsonObject) {
        super();
        this.messageId = jsonObject.getInt(MESSAGEID_FIELD);
        if (jsonObject.has(FROM_FIELD)) {
            this.from = new User(jsonObject.getJSONObject(FROM_FIELD));
        }
        this.date = jsonObject.getInt(DATE_FIELD);
        this.chat = new Chat(jsonObject.getJSONObject(CHAT_FIELD));
        if (jsonObject.has(FORWARDFROM_FIELD)) {
            this.forwardFrom = new User(jsonObject.getJSONObject(FORWARDFROM_FIELD));
        }
        if (jsonObject.has(FORWARDDATE_FIELD)) {
            this.forwardDate = jsonObject.getInt(FORWARDDATE_FIELD);
        }
        if (jsonObject.has(TEXT_FIELD)) {
            this.text = jsonObject.getString(TEXT_FIELD);
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
        if (jsonObject.has(VOICE_FIELD)) {
            this.voice = new Voice(jsonObject.getJSONObject(VOICE_FIELD));
        }
        if (jsonObject.has(NEWCHATPARTICIPANT_FIELD)) {
            this.newChatParticipant = new User(jsonObject.getJSONObject(NEWCHATPARTICIPANT_FIELD));
        }
        if (jsonObject.has(LEFTCHATPARTICIPANT_FIELD)) {
            this.leftChatParticipant = new User(jsonObject.getJSONObject(LEFTCHATPARTICIPANT_FIELD));
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
    }

    public Integer getMessageId() {
        return messageId;
    }

    public void setMessageId(Integer messageId) {
        this.messageId = messageId;
    }

    public User getFrom() {
        return from;
    }

    public void setFrom(User from) {
        this.from = from;
    }

    public Integer getDate() {
        return date;
    }

    public void setDate(Integer date) {
        this.date = date;
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

    public Chat getChat() {
        return chat;
    }

    public void setChat(Chat chat) {
        this.chat = chat;
    }

    public User getForwardFrom() {
        return forwardFrom;
    }

    public void setForwardFrom(User forwardFrom) {
        this.forwardFrom = forwardFrom;
    }

    public Integer getForwardDate() {
        return forwardDate;
    }

    public void setForwardDate(Integer forwardDate) {
        this.forwardDate = forwardDate;
    }

    public boolean hasText() {
        return text != null && !text.isEmpty();
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Audio getAudio() {
        return audio;
    }

    public void setAudio(Audio audio) {
        this.audio = audio;
    }

    public boolean hasDocument() {
        return this.document != null;
    }

    public Document getDocument() {
        return document;
    }

    public void setDocument(Document document) {
        this.document = document;
    }

    public List<PhotoSize> getPhoto() {
        return photo;
    }

    public void setPhoto(List<PhotoSize> photo) {
        this.photo = photo;
    }

    public Sticker getSticker() {
        return sticker;
    }

    public void setSticker(Sticker sticker) {
        this.sticker = sticker;
    }

    public Video getVideo() {
        return video;
    }

    public void setVideo(Video video) {
        this.video = video;
    }

    public Contact getContact() {
        return contact;
    }

    public void setContact(Contact contact) {
        this.contact = contact;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public User getNewChatParticipant() {
        return newChatParticipant;
    }

    public void setNewChatParticipant(User newChatParticipant) {
        this.newChatParticipant = newChatParticipant;
    }

    public User getLeftChatParticipant() {
        return leftChatParticipant;
    }

    public void setLeftChatParticipant(User leftChatParticipant) {
        this.leftChatParticipant = leftChatParticipant;
    }

    public String getNewChatTitle() {
        return newChatTitle;
    }

    public void setNewChatTitle(String newChatTitle) {
        this.newChatTitle = newChatTitle;
    }

    public List<PhotoSize> getNewChatPhoto() {
        return newChatPhoto;
    }

    public void setNewChatPhoto(List<PhotoSize> newChatPhoto) {
        this.newChatPhoto = newChatPhoto;
    }

    public Boolean getDeleteChatPhoto() {
        return deleteChatPhoto;
    }

    public void setDeleteChatPhoto(Boolean deleteChatPhoto) {
        this.deleteChatPhoto = deleteChatPhoto;
    }

    public Boolean getGroupchatCreated() {
        return groupchatCreated;
    }

    public void setGroupchatCreated(Boolean groupchatCreated) {
        this.groupchatCreated = groupchatCreated;
    }

    public boolean hasReplayMessage() {
        return replyToMessage != null;
    }

    public Message getReplyToMessage() {
        return replyToMessage;
    }

    public void setReplyToMessage(Message replyToMessage) {
        this.replyToMessage = replyToMessage;
    }

    public boolean isReply() {
        return this.replyToMessage != null;
    }

    public boolean hasLocation() {
        return location != null;
    }

    public Voice getVoice() {
        return voice;
    }

    public void setVoice(Voice voice) {
        this.voice = voice;
    }

    public Boolean getSuperGroupCreated() {
        return superGroupCreated;
    }

    public void setSuperGroupCreated(Boolean superGroupCreated) {
        this.superGroupCreated = superGroupCreated;
    }

    public Boolean getChannelChatCreated() {
        return channelChatCreated;
    }

    public void setChannelChatCreated(Boolean channelChatCreated) {
        this.channelChatCreated = channelChatCreated;
    }

    public Long getMigrateToChatId() {
        return migrateToChatId;
    }

    public void setMigrateToChatId(Long migrateToChatId) {
        this.migrateToChatId = migrateToChatId;
    }

    public Long getMigrateFromChatId() {
        return migrateFromChatId;
    }

    public void setMigrateFromChatId(Long migrateFromChatId) {
        this.migrateFromChatId = migrateFromChatId;
    }

    @Override
    public void serialize(JsonGenerator gen, SerializerProvider serializers) throws IOException {
        gen.writeStartObject();

        gen.writeNumberField(MESSAGEID_FIELD, messageId);
        gen.writeObjectField(FROM_FIELD, from);
        gen.writeNumberField(DATE_FIELD, date);
        gen.writeObjectField(CHAT_FIELD, chat);

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
        if (voice != null) {
            gen.writeObjectField(VOICE_FIELD, voice);
        }
        if (newChatParticipant != null) {
            gen.writeObjectField(NEWCHATPARTICIPANT_FIELD, newChatParticipant);
        }
        if (leftChatParticipant != null) {
            gen.writeObjectField(LEFTCHATPARTICIPANT_FIELD, leftChatParticipant);
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
                ", forwardDate=" + forwardDate +
                ", text='" + text + '\'' +
                ", audio=" + audio +
                ", document=" + document +
                ", photo=" + photo +
                ", sticker=" + sticker +
                ", video=" + video +
                ", contact=" + contact +
                ", location=" + location +
                ", newChatParticipant=" + newChatParticipant +
                ", leftChatParticipant=" + leftChatParticipant +
                ", newChatTitle='" + newChatTitle + '\'' +
                ", newChatPhoto=" + newChatPhoto +
                ", deleteChatPhoto=" + deleteChatPhoto +
                ", groupchatCreated=" + groupchatCreated +
                ", replyToMessage=" + replyToMessage +
                ", voice=" + voice +
                ", superGroupCreated=" + superGroupCreated +
                ", channelChatCreated=" + channelChatCreated +
                ", migrateToChatId=" + migrateToChatId +
                ", migrateFromChatId=" + migrateFromChatId +
                '}';
    }
}
