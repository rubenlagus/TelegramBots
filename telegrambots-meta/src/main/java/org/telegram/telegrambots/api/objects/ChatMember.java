package org.telegram.telegrambots.api.objects;

import com.fasterxml.jackson.annotation.JsonProperty;

import org.telegram.telegrambots.api.interfaces.BotApiObject;

/**
 * @author Ruben Bermudez
 * @version 1.0
 * @brief This object contains information about one member of the chat.
 * @date 20 of May of 2016
 */
public class ChatMember implements BotApiObject {
    private static final String USER_FIELD = "user";
    private static final String STATUS_FIELD = "status";

    @JsonProperty(USER_FIELD)
    private User user; ///< Information about the user
    @JsonProperty(STATUS_FIELD)
    private String status; ///< The member's status in the chat. Can be “creator”, “administrator”, “member”, “left” or “kicked”

    public ChatMember() {
        super();
    }

    public User getUser() {
        return user;
    }

    public String getStatus() {
        return status;
    }

    @Override
    public String toString() {
        return "ChatMember{" +
                "user=" + user +
                ", status='" + status + '\'' +
                '}';
    }
}
