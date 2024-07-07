package org.telegram.telegrambots.meta.api.objects.chat;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import lombok.extern.jackson.Jacksonized;
import org.telegram.telegrambots.meta.api.interfaces.BotApiObject;

/**
 * This object represents a Telegram chat with a user or a group
 * @author Ruben Bermudez
 * @version 7.3
 */
@SuppressWarnings("WeakerAccess")
@EqualsAndHashCode(callSuper = false)
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SuperBuilder
@Jacksonized
@JsonIgnoreProperties(ignoreUnknown = true)
public class Chat implements BotApiObject {
    private static final String ID_FIELD = "id";
    private static final String TYPE_FIELD = "type";
    private static final String TITLE_FIELD = "title";
    private static final String USERNAME_FIELD = "username";
    private static final String FIRSTNAME_FIELD = "first_name";
    private static final String LASTNAME_FIELD = "last_name";
    private static final String ISFORUM_FIELD  = "is_forum";

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
     * True, if the supergroup chat is a forum (has topics enabled)
     */
    @JsonProperty(ISFORUM_FIELD)
    private Boolean isForum;

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
