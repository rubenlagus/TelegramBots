package org.telegram.telegrambots.meta.api.objects;

import org.junit.jupiter.api.Test;
import org.telegram.telegrambots.meta.api.objects.chat.Chat;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

/**
 * @author Ruben Bermudez
 * @version 10.1
 */
public class TestChatJoinRequest {

    @Test
    public void testChatJoinRequestQueryIdField() {
        Chat chat = Chat.builder().id(12345L).type("supergroup").build();
        User user = User.builder().id(67890L).firstName("TestUser").isBot(false).build();

        ChatJoinRequest request = new ChatJoinRequest();
        request.setChat(chat);
        request.setUser(user);
        request.setDate(1234567890);
        request.setQueryId("query_abc_123");

        assertEquals("query_abc_123", request.getQueryId());
    }

    @Test
    public void testChatJoinRequestQueryIdNullByDefault() {
        ChatJoinRequest request = new ChatJoinRequest();

        assertNull(request.getQueryId());
    }
}
