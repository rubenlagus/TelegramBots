package org.telegram.telegrambots.meta.api.objects.commands.scope;

import org.junit.jupiter.api.Test;
import org.telegram.telegrambots.meta.exceptions.TelegramApiValidationException;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * @author Ruben Bermudez
 * @version 1.0
 */
public class BotCommandScopeTest {
    @Test
    public void testBotCommandScopeDefault() {
        BotCommandScopeDefault botCommandScope = BotCommandScopeDefault.builder().build();
        assertEquals("default", botCommandScope.getType());
        assertDoesNotThrow(botCommandScope::validate);
    }

    @Test
    public void testBotCommandScopeAllChatAdministrators() {
        BotCommandScopeAllChatAdministrators botCommandScope = BotCommandScopeAllChatAdministrators.builder().build();
        assertEquals("all_chat_administrators", botCommandScope.getType());
        assertDoesNotThrow(botCommandScope::validate);
    }

    @Test
    public void testBotCommandScopeAllGroupChats() {
        BotCommandScopeAllGroupChats botCommandScope = BotCommandScopeAllGroupChats.builder().build();
        assertEquals("all_group_chats", botCommandScope.getType());
        assertDoesNotThrow(botCommandScope::validate);
    }

    @Test
    public void testBotCommandScopeAllPrivateChats() {
        BotCommandScopeAllPrivateChats botCommandScope = BotCommandScopeAllPrivateChats.builder().build();
        assertEquals("all_private_chats", botCommandScope.getType());
        assertDoesNotThrow(botCommandScope::validate);
    }

    @Test
    public void testBotCommandScopeChatWithValidChatIId() {
        BotCommandScopeChat botCommandScope = BotCommandScopeChat
                .builder()
                .chatId(12345L)
                .build();
        assertEquals("chat", botCommandScope.getType());
        assertDoesNotThrow(botCommandScope::validate);
    }

    @Test
    public void testBotCommandScopeChatWithEmptyChatId() {
        BotCommandScopeChat botCommandScope = BotCommandScopeChat
                .builder()
                .chatId("")
                .build();
        assertEquals("chat", botCommandScope.getType());
        Throwable thrown = assertThrows(TelegramApiValidationException.class, botCommandScope::validate);
        assertEquals("ChatId parameter can't be empty", thrown.getMessage());
    }


    @Test
    public void testBotCommandScopeChatAdministratorsWithValidChatIId() {
        BotCommandScopeChatAdministrators botCommandScope = BotCommandScopeChatAdministrators
                .builder()
                .chatId("12345")
                .build();
        assertEquals("chat_administrators", botCommandScope.getType());
        assertDoesNotThrow(botCommandScope::validate);
    }

    @Test
    public void testBotCommandScopeChatAdministratorsWithEmptyChatId() {
        BotCommandScopeChatAdministrators botCommandScope = BotCommandScopeChatAdministrators
                .builder()
                .chatId("")
                .build();
        assertEquals("chat_administrators", botCommandScope.getType());
        Throwable thrown = assertThrows(TelegramApiValidationException.class, botCommandScope::validate);
        assertEquals("ChatId parameter can't be empty", thrown.getMessage());
    }

    @Test
    public void testBotCommandScopeChatMemberWithValidSetUp() {
        BotCommandScopeChatMember botCommandScope = BotCommandScopeChatMember
                .builder()
                .chatId(12345L)
                .userId(12345L)
                .build();
        assertEquals("chat_member", botCommandScope.getType());
        assertDoesNotThrow(botCommandScope::validate);
    }

    @Test
    public void testBotCommandScopeChatMemberWithEmptyChatId() {
        BotCommandScopeChatMember botCommandScope = BotCommandScopeChatMember
                .builder()
                .chatId("")
                .userId(12345L)
                .build();
        assertEquals("chat_member", botCommandScope.getType());
        Throwable thrown = assertThrows(TelegramApiValidationException.class, botCommandScope::validate);
        assertEquals("ChatId parameter can't be empty", thrown.getMessage());
    }

    @Test
    public void testBotCommandScopeChatMemberWith0UserId() {
        BotCommandScopeChatMember botCommandScope = BotCommandScopeChatMember
                .builder()
                .chatId("12345")
                .userId(0L)
                .build();
        assertEquals("chat_member", botCommandScope.getType());
        Throwable thrown = assertThrows(TelegramApiValidationException.class, botCommandScope::validate);
        assertEquals("UserId parameter can't be empty", thrown.getMessage());
    }
}
