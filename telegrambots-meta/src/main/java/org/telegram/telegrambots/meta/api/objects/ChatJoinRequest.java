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
}
