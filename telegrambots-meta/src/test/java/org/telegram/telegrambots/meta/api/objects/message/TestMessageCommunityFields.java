package org.telegram.telegrambots.meta.api.objects.message;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * @author Ruben Bermudez
 * @version 10.2
 */
public class TestMessageCommunityFields {
    private static final String BASE_MESSAGE = "\"message_id\":1,\"date\":1752451200,"
            + "\"chat\":{\"id\":-100123,\"type\":\"supergroup\"}";

    private ObjectMapper mapper;

    @BeforeEach
    void setUp() {
        mapper = new ObjectMapper();
    }

    @Test
    public void testCommunityChatAddedDeserialization() throws IOException {
        Message message = mapper.readValue("{" + BASE_MESSAGE
                + ",\"community_chat_added\":{\"community\":{\"id\":987,\"name\":\"Java Developers\"}}}", Message.class);

        assertTrue(message.hasCommunityChatAdded());
        assertNotNull(message.getCommunityChatAdded());
        assertEquals(987L, message.getCommunityChatAdded().getCommunity().getId());
        assertEquals("Java Developers", message.getCommunityChatAdded().getCommunity().getName());
    }

    @Test
    public void testCommunityChatRemovedDeserialization() throws IOException {
        Message message = mapper.readValue("{" + BASE_MESSAGE + ",\"community_chat_removed\":{}}", Message.class);

        assertTrue(message.hasCommunityChatRemoved());
        assertNotNull(message.getCommunityChatRemoved());
    }

    @Test
    public void testMessageWithoutCommunityServiceMessages() throws IOException {
        Message message = mapper.readValue("{" + BASE_MESSAGE + "}", Message.class);

        assertFalse(message.hasCommunityChatAdded());
        assertFalse(message.hasCommunityChatRemoved());
    }
}
