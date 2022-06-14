package org.telegram.telegrambots.meta.api.methods.groupadministration;

import org.junit.jupiter.api.Test;
import org.telegram.telegrambots.meta.exceptions.TelegramApiValidationException;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * @author Ruben Bermudez
 * @version 5.3
 */
public class GetChatMemberCountTest {
    @Test
    public void testGetChatMemberCountWithAllSet() {
        GetChatMemberCount getChatMemberCount = GetChatMemberCount
                .builder()
                .chatId(12345L)
                .build();
        assertEquals("getChatMemberCount", getChatMemberCount.getMethod());
        assertDoesNotThrow(getChatMemberCount::validate);
    }

    @Test
    public void testGetChatMemberCountWithEmptyChatId() {
        GetChatMemberCount getChatMemberCount = GetChatMemberCount
                .builder()
                .chatId("")
                .build();
        assertEquals("getChatMemberCount", getChatMemberCount.getMethod());
        Throwable thrown = assertThrows(TelegramApiValidationException.class, getChatMemberCount::validate);
        assertEquals("ChatId can't be empty", thrown.getMessage());
    }
}
