package org.telegram.telegrambots.meta.api.objects;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import lombok.extern.jackson.Jacksonized;
import org.telegram.telegrambots.meta.api.objects.chat.Chat;
import org.telegram.telegrambots.meta.api.objects.chatmember.ChatMember;

/**
 * @author Ruben Bermudez
 * @version 5.1
 * This object represents changes in the status of a chat member.
 * @deprecated Use {{@link org.telegram.telegrambots.meta.api.objects.chatmember.ChatMemberUpdated}}
 */
@EqualsAndHashCode(callSuper = false)
@Getter
@Setter
@ToString
@SuperBuilder
@Jacksonized
@JsonIgnoreProperties(ignoreUnknown = true)
@Deprecated
public class ChatMemberUpdated extends org.telegram.telegrambots.meta.api.objects.chatmember.ChatMemberUpdated {
    public ChatMemberUpdated(Chat chat, User from, Integer date, ChatMember oldChatMember, ChatMember newChatMember,
                             ChatInviteLink inviteLink, Boolean viaChatFolderInviteLink) {
        super(chat, from, date, oldChatMember, newChatMember, inviteLink, viaChatFolderInviteLink,null);
    }

    public ChatMemberUpdated() {
        super();
    }
}
