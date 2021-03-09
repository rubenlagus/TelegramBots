package org.telegram.telegrambots.meta.api.objects;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.telegram.telegrambots.meta.api.interfaces.BotApiObject;

/**
 * @author Ruben Bermudez
 * @version 5.1
 * This object represents changes in the status of a chat member.
 */
@EqualsAndHashCode(callSuper = false)
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ChatMemberUpdated implements BotApiObject {
    private static final String CHAT_FIELD = "chat";
    private static final String FROM_FIELD = "from";
    private static final String DATE_FIELD = "date";
    private static final String OLDCHATMEMBER_FIELD = "old_chat_member";
    private static final String NEWCHATMEMBER_FIELD = "new_chat_member";
    private static final String INVITELINK_FIELD = "invite_link";

    @JsonProperty(CHAT_FIELD)
    private Chat chat; ///< Chat the user belongs to
    @JsonProperty(FROM_FIELD)
    private User from; ///< Performer of the action, which resulted in the change
    @JsonProperty(DATE_FIELD)
    private Integer date; ///< Date the change was done in Unix time
    @JsonProperty(OLDCHATMEMBER_FIELD)
    private ChatMember oldChatMember; ///< Previous information about the chat member
    @JsonProperty(NEWCHATMEMBER_FIELD)
    private ChatMember newChatMember; ///< New information about the chat member
    @JsonProperty(INVITELINK_FIELD)
    private ChatInviteLink inviteLink; ///< Optional. Chat invite link, which was used by the user to join the chat; for joining by invite link events only.

}
