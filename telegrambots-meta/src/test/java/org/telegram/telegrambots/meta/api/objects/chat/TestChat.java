package org.telegram.telegrambots.meta.api.objects.chat;

import org.junit.jupiter.api.Test;
import org.telegram.telegrambots.meta.api.objects.User;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

/**
 * @author Ruben Bermudez
 * @version 10.1
 */
public class TestChat {

    @Test
    public void testChatGuardBotField() {
        User guardBot = User.builder()
                .id(99999L)
                .firstName("GuardBot")
                .isBot(true)
                .build();

        Chat chat = Chat.builder()
                .id(12345L)
                .type("supergroup")
                .guardBot(guardBot)
                .build();

        assertNotNull(chat.getGuardBot());
        assertEquals(99999L, chat.getGuardBot().getId());
        assertEquals("GuardBot", chat.getGuardBot().getFirstName());
    }

    @Test
    public void testChatGuardBotNullByDefault() {
        Chat chat = Chat.builder()
                .id(12345L)
                .type("supergroup")
                .build();

        assertNull(chat.getGuardBot());
    }
}
