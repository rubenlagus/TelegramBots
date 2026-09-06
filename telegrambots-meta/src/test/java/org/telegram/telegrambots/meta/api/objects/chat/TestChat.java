package org.telegram.telegrambots.meta.api.objects.chat;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.api.objects.community.Community;

import java.io.IOException;

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

        ChatFullInfo chat = ChatFullInfo.builder()
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
        ChatFullInfo chat = ChatFullInfo.builder()
                .id(12345L)
                .type("supergroup")
                .build();

        assertNull(chat.getGuardBot());
    }

    @Test
    public void testChatFullInfoCommunityField() {
        ChatFullInfo chat = ChatFullInfo.builder()
                .id(12345L)
                .type("supergroup")
                .community(Community.builder().id(987L).name("Java Developers").build())
                .build();

        assertNotNull(chat.getCommunity());
        assertEquals(987L, chat.getCommunity().getId());
        assertEquals("Java Developers", chat.getCommunity().getName());
    }

    @Test
    public void testChatFullInfoCommunityDeserialization() throws IOException {
        ChatFullInfo chat = new ObjectMapper().readValue(
                "{\"id\":12345,\"type\":\"supergroup\",\"community\":{\"id\":987,\"name\":\"Java Developers\"}}",
                ChatFullInfo.class);

        assertNotNull(chat.getCommunity());
        assertEquals(987L, chat.getCommunity().getId());
    }

    @Test
    public void testChatFullInfoCommunityNullByDefault() {
        ChatFullInfo chat = ChatFullInfo.builder()
                .id(12345L)
                .type("supergroup")
                .build();

        assertNull(chat.getCommunity());
    }
}
