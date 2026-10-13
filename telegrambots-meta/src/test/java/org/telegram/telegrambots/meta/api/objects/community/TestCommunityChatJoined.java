package org.telegram.telegrambots.meta.api.objects.community;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.telegram.telegrambots.meta.api.objects.message.Message;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * @author Ruben Bermudez
 * @version 10.3
 */
public class TestCommunityChatJoined {
    private ObjectMapper mapper;

    @BeforeEach
    void setUp() {
        mapper = new ObjectMapper();
    }

    @Test
    public void testDeserializeOnMessage() throws IOException {
        String json = "{\"message_id\":1,\"date\":1700000000,\"chat\":{\"id\":1,\"type\":\"supergroup\"},"
                + "\"community_chat_joined\":{\"community\":{\"id\":7,\"name\":\"Devs\"}}}";

        Message message = mapper.readValue(json, Message.class);

        assertTrue(message.hasCommunityChatJoined());
        assertNotNull(message.getCommunityChatJoined());
        assertEquals("Devs", message.getCommunityChatJoined().getCommunity().getName());
    }

    @Test
    public void testHasCommunityChatJoinedFalseWhenAbsent() throws IOException {
        String json = "{\"message_id\":1,\"date\":1700000000,\"chat\":{\"id\":1,\"type\":\"supergroup\"}}";

        assertFalse(mapper.readValue(json, Message.class).hasCommunityChatJoined());
    }
}
