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
public class BanChatMemberTest {
    @Test
    public void testBanChatMemberWithAllSet() {
        BanChatMember banChatMember = BanChatMember
                .builder()
                .chatId(12345L)
                .userId(12345L)
                .untilDate(1000)
                .revokeMessages(true)
                .build();
        assertEquals("banChatMember", banChatMember.getMethod());
        assertDoesNotThrow(banChatMember::validate);
    }

    @Test
    public void testBanChatMemberWithEmptyChatId() {
        BanChatMember banChatMember = BanChatMember
                .builder()
                .chatId("")
                .userId(12345L)
                .untilDate(1000)
                .revokeMessages(true)
                .build();
        assertEquals("banChatMember", banChatMember.getMethod());
        Throwable thrown = assertThrows(TelegramApiValidationException.class, banChatMember::validate);
        assertEquals("ChatId can't be empty", thrown.getMessage());
    }

    @Test
    public void testBanChatMemberWithEmptyUserId() {
        BanChatMember banChatMember = BanChatMember
                .builder()
                .chatId("12345")
                .userId(0L)
                .untilDate(1000)
                .revokeMessages(true)
                .build();
        assertEquals("banChatMember", banChatMember.getMethod());
        Throwable thrown = assertThrows(TelegramApiValidationException.class, banChatMember::validate);
        assertEquals("UserId can't be null or 0", thrown.getMessage());
    }
}
