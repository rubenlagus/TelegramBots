package org.telegram.telegrambots.meta.api.objects.commands.scope;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import org.telegram.telegrambots.meta.api.interfaces.BotApiObject;
import org.telegram.telegrambots.meta.api.interfaces.Validable;
import org.telegram.telegrambots.meta.api.objects.commands.scope.serialization.BotCommandScopeDeserializer;

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
@JsonDeserialize(using = BotCommandScopeDeserializer.class)
public interface BotCommandScope extends BotApiObject, Validable {
    String getType();
}
