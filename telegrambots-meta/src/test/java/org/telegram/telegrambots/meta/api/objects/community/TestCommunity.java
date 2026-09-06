package org.telegram.telegrambots.meta.api.objects.community;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * @author Ruben Bermudez
 * @version 10.2
 */
public class TestCommunity {
    private ObjectMapper mapper;

    @BeforeEach
    void setUp() {
        mapper = new ObjectMapper();
    }

    @Test
    public void testCommunityDeserialization() throws IOException {
        Community community = mapper.readValue("{\"id\":123456789,\"name\":\"Java Developers\"}", Community.class);

        assertEquals(123456789L, community.getId());
        assertEquals("Java Developers", community.getName());
    }

    @Test
    public void testCommunityKeepsIdsBeyond32Bits() throws IOException {
        Community community = mapper.readValue("{\"id\":4503599627370496,\"name\":\"Big\"}", Community.class);

        assertEquals(4503599627370496L, community.getId());
    }

    @Test
    public void testCommunitySerialization() throws IOException {
        Community community = Community.builder()
                .id(123456789L)
                .name("Java Developers")
                .build();

        String json = mapper.writeValueAsString(community);

        assertTrue(json.contains("\"id\":123456789"), json);
        assertTrue(json.contains("\"name\":\"Java Developers\""), json);
    }

    @Test
    public void testCommunityChatAddedDeserialization() throws IOException {
        CommunityChatAdded chatAdded = mapper.readValue(
                "{\"community\":{\"id\":123456789,\"name\":\"Java Developers\"}}", CommunityChatAdded.class);

        assertNotNull(chatAdded.getCommunity());
        assertEquals(123456789L, chatAdded.getCommunity().getId());
        assertEquals("Java Developers", chatAdded.getCommunity().getName());
    }

    @Test
    public void testCommunityChatAddedSerialization() throws IOException {
        CommunityChatAdded chatAdded = CommunityChatAdded.builder()
                .community(Community.builder().id(1L).name("Small").build())
                .build();

        String json = mapper.writeValueAsString(chatAdded);

        assertTrue(json.contains("\"community\""), json);
        assertTrue(json.contains("\"name\":\"Small\""), json);
    }

    @Test
    public void testCommunityChatRemovedDeserializesEmptyObject() throws IOException {
        CommunityChatRemoved chatRemoved = mapper.readValue("{}", CommunityChatRemoved.class);

        assertNotNull(chatRemoved);
    }
}
