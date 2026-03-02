package org.telegram.telegrambots.meta.api.objects.chat;

import org.junit.jupiter.api.Test;
import org.telegram.telegrambots.meta.api.objects.User;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

/**
 * @author Ruben Bermudez
 * @version 9.4
 */
public class TestChatOwnerLeft {

    @Test
    public void testChatOwnerLeftWithNewOwner() {
        User newOwner = User.builder()
                .id(12345L)
                .firstName("John")
                .isBot(false)
                .build();

        ChatOwnerLeft chatOwnerLeft = ChatOwnerLeft.builder()
                .newOwner(newOwner)
                .build();

        assertNotNull(chatOwnerLeft);
        assertNotNull(chatOwnerLeft.getNewOwner());
        assertEquals(12345L, chatOwnerLeft.getNewOwner().getId());
        assertEquals("John", chatOwnerLeft.getNewOwner().getFirstName());
    }

    @Test
    public void testChatOwnerLeftWithoutNewOwner() {
        ChatOwnerLeft chatOwnerLeft = ChatOwnerLeft.builder()
                .build();

        assertNotNull(chatOwnerLeft);
        assertNull(chatOwnerLeft.getNewOwner());
    }
}
