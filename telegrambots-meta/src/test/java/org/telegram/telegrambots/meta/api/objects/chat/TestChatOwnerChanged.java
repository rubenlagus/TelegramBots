package org.telegram.telegrambots.meta.api.objects.chat;

import org.junit.jupiter.api.Test;
import org.telegram.telegrambots.meta.api.objects.User;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * @author Ruben Bermudez
 * @version 9.4
 */
public class TestChatOwnerChanged {

    @Test
    public void testChatOwnerChanged() {
        User newOwner = User.builder()
                .id(67890L)
                .firstName("Jane")
                .lastName("Smith")
                .isBot(false)
                .build();

        ChatOwnerChanged chatOwnerChanged = ChatOwnerChanged.builder()
                .newOwner(newOwner)
                .build();

        assertNotNull(chatOwnerChanged);
        assertNotNull(chatOwnerChanged.getNewOwner());
        assertEquals(67890L, chatOwnerChanged.getNewOwner().getId());
        assertEquals("Jane", chatOwnerChanged.getNewOwner().getFirstName());
        assertEquals("Smith", chatOwnerChanged.getNewOwner().getLastName());
    }
}
