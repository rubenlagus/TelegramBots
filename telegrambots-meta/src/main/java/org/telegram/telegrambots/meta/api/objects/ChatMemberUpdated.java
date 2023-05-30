package org.telegram.telegrambots.meta.api.objects;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.telegram.telegrambots.meta.api.interfaces.BotApiObject;
import org.telegram.telegrambots.meta.api.objects.chatmember.ChatMember;

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
    private static final String OLD_CHAT_MEMBER_FIELD = "old_chat_member";
    private static final String NEW_CHAT_MEMBER_FIELD = "new_chat_member";
    private static final String INVITE_LINK_FIELD = "invite_link";
    private static final String VIA_CHAT_FOLDER_INVITE_LINK_FIELD = "via_chat_folder_invite_link";

    /**
     * Chat the user belongs to
     */
    @JsonProperty(CHAT_FIELD)
    private Chat chat;
    /**
     * Performer of the action, which resulted in the change
     */
    @JsonProperty(FROM_FIELD)
    private User from;
    /**
     * Date the change was done in Unix time
     */
    @JsonProperty(DATE_FIELD)
    private Integer date;
    /**
     * Previous information about the chat member
     */
    @JsonProperty(OLD_CHAT_MEMBER_FIELD)
    private ChatMember oldChatMember;
    /**
     * New information about the chat member
     */
    @JsonProperty(NEW_CHAT_MEMBER_FIELD)
    private ChatMember newChatMember;
    /**
     * Optional.
     * Chat invite link, which was used by the user to join the chat; for joining by invite link events only.
     */
    @JsonProperty(INVITE_LINK_FIELD)
    private ChatInviteLink inviteLink;

    /**
     * Optional.
     * True, if the user joined the chat via a chat folder invite link
     */
    @JsonProperty(VIA_CHAT_FOLDER_INVITE_LINK_FIELD)
    private Boolean viaChatFolderInviteLink;

}
