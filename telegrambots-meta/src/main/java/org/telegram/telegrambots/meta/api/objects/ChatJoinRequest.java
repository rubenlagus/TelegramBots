package org.telegram.telegrambots.meta.api.objects;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.telegram.telegrambots.meta.api.interfaces.BotApiObject;

/**
 * @author Ruben Bermudez
 * @version 5.4
 *
 * Represents a join request sent to a chat.
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


    @JsonProperty(CHAT_FIELD)
    private Chat chat; ///< Chat to which the request was sent
    @JsonProperty(FROM_FIELD)
    private User user; ///< User that sent the join request
    @JsonProperty(DATE_FIELD)
    private Integer date; ///< Date the request was sent in Unix time
    @JsonProperty(BIO_FIELD)
    private String bio; ///< Optional. Bio of the user.
    @JsonProperty(INVITELINK_FIELD)
    private ChatInviteLink inviteLink; ///< Optional. Chat invite link that was used by the user to send the join request
}
