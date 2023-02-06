package org.telegram.telegrambots.meta.api.objects;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.telegram.telegrambots.meta.api.interfaces.BotApiObject;

/**
 * Represents a join request sent to a chat.
 * @author Ruben Bermudez
 * @version 5.4
 */
@EqualsAndHashCode(callSuper = false)
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ChatJoinRequest implements BotApiObject {
    private static final String CHAT_FIELD = "chat";
    private static final String FROM_FIELD = "from";
    private static final String DATE_FIELD = "date";
    private static final String BIO_FIELD = "bio";
    private static final String INVITELINK_FIELD = "invite_link";
    private static final String USERCHATID_FIELD = "user_chat_id";

    /**
     * Chat to which the request was sent
     */
    @JsonProperty(CHAT_FIELD)
    private Chat chat;
    /**
     * User that sent the join request
     */
    @JsonProperty(FROM_FIELD)
    private User user;
    /**
     * Date the request was sent in Unix time
     */
    @JsonProperty(DATE_FIELD)
    private Integer date;
    /**
     * Optional.
     * Bio of the user.
     */
    @JsonProperty(BIO_FIELD)
    private String bio;
    /**
     * Optional.
     * Chat invite link that was used by the user to send the join request
     */
    @JsonProperty(INVITELINK_FIELD)
    private ChatInviteLink inviteLink;
    /**
     * Identifier of a private chat with the user who sent the join request.
     * This number may have more than 32 significant bits and some programming languages may have difficulty/silent defects in interpreting it.
     * But it has at most 52 significant bits, so a 64-bit integer or double-precision float type are safe for storing this identifier.
     * @apiNote The bot can use this identifier for 24 hours to send messages until the join request is processed, assuming no other administrator contacted the user.
     */
    @JsonProperty(USERCHATID_FIELD)
    private Long userChatId;
}
