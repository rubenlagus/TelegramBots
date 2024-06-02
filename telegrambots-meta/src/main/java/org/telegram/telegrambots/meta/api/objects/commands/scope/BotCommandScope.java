package org.telegram.telegrambots.meta.api.objects.commands.scope;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import org.telegram.telegrambots.meta.api.interfaces.BotApiObject;
import org.telegram.telegrambots.meta.api.interfaces.Validable;

/**
 * @author Ruben Bermudez
 * @version 5.3
 *
 * This object represents the scope to which bot commands are applied. Currently, the following 7 scopes are supported:
 *
 * BotCommandScopeDefault
 * BotCommandScopeAllPrivateChats
 * BotCommandScopeAllGroupChats
 * BotCommandScopeAllChatAdministrators
 * BotCommandScopeChat
 * BotCommandScopeChatAdministrators
 * BotCommandScopeChatMember
 *
 * The following algorithm is used to determine the list of commands for a particular user viewing the bot menu. The first list of commands which is set is returned:
 *
 * Commands in the chat with the bot
 *
 * botCommandScopeChat + language_code
 * botCommandScopeChat
 * botCommandScopeAllPrivateChats + language_code
 * botCommandScopeAllPrivateChats
 * botCommandScopeDefault + language_code
 * botCommandScopeDefault
 * Commands in group and supergroup chats
 *
 * botCommandScopeChatMember + language_code
 * botCommandScopeChatMember
 * botCommandScopeChatAdministrators + language_code (admins only)
 * botCommandScopeChatAdministrators (admins only)
 * botCommandScopeChat + language_code
 * botCommandScopeChat
 * botCommandScopeAllChatAdministrators + language_code (admins only)
 * botCommandScopeAllChatAdministrators (admins only)
 * botCommandScopeAllGroupChats + language_code
 * botCommandScopeAllGroupChats
 * botCommandScopeDefault + language_code
 * botCommandScopeDefault
 */
@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.EXISTING_PROPERTY,
        property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = BotCommandScopeDefault.class, name = "default"),
        @JsonSubTypes.Type(value = BotCommandScopeAllPrivateChats.class, name = "all_private_chats"),
        @JsonSubTypes.Type(value = BotCommandScopeAllGroupChats.class, name = "all_group_chats"),
        @JsonSubTypes.Type(value = BotCommandScopeAllChatAdministrators.class, name = "all_chat_administrators"),
        @JsonSubTypes.Type(value = BotCommandScopeChat.class, name = "chat"),
        @JsonSubTypes.Type(value = BotCommandScopeChatAdministrators.class, name = "chat_administrators"),
        @JsonSubTypes.Type(value = BotCommandScopeChatMember.class, name = "chat_member")
})
public interface BotCommandScope extends BotApiObject, Validable {
    String getType();
}
