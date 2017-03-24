package org.telegram.telegrambots.api.objects;



import org.telegram.telegrambots.api.interfaces.BotApiObject;

/**
 * @author Ruben Bermudez
 * @version 1.0
 * @brief This object represents a Telegram chat with an user or a group
 * @date 24 of June of 2015
 */
public class Chat implements BotApiObject {

    private static final String ID_FIELD = "id";
    private static final String TYPE_FIELD = "type";
    private static final String TITLE_FIELD = "title";
    private static final String FIRSTNAME_FIELD = "first_name";
    private static final String LASTNAME_FIELD = "last_name";
    private static final String USERNAME_FIELD = "username";
    private static final String ALL_MEMBERS_ARE_ADMINISTRATORS_FIELD = "all_members_are_administrators";
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

    private Long id; ///< Unique identifier for this chat, not exciding 1e13 by absolute value

    private String type; ///< Type of the chat, one of “private”, “group” or “channel”

    private String title; ///< Optional. Title of the chat, only for channels and group chat

    private String first_name; ///< Optional. Username of the chat, only for private chats and channels if available

    private String last_name; ///< Optional. Interlocutor's first name for private chats

    private String user_name; ///< Optional. Interlocutor's last name for private chats
    /**
     * Optional. True if the group or supergroup has ‘All Members Are Admins’ enabled.
     */

    private Boolean all_members_are_administrators;

    public Chat() {
        super();
    }

    public Long getId() {
        return id;
    }

    public Boolean isGroupChat() {
        return GROUPCHATTYPE.equals(type);
    }

    public Boolean isChannelChat() {
        return CHANNELCHATTYPE.equals(type);
    }

    public Boolean isUserChat() {
        return USERCHATTYPE.equals(type);
    }

    public Boolean isSuperGroupChat() {
        return SUPERGROUPCHATTYPE.equals(type);
    }

    public String getTitle() {
        return title;
    }

    public String getFirstName() {
        return first_name;
    }

    public String getLastName() {
        return last_name;
    }

    public String getUserName() {
        return user_name;
    }

    public Boolean getAllMembersAreAdministrators() {
        return all_members_are_administrators;
    }

    @Override
    public String toString() {
        return "Chat{" +
                "id=" + id +
                ", type='" + type + '\'' +
                ", title='" + title + '\'' +
                ", firstName='" + first_name + '\'' +
                ", lastName='" + last_name + '\'' +
                ", userName='" + user_name + '\'' +
                ", allMembersAreAdministrators=" + all_members_are_administrators +
                '}';
    }
}
