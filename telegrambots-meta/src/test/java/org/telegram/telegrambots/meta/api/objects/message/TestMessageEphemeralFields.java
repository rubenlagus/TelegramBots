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
public class TestMessageEphemeralFields {
    private static final String BASE_MESSAGE = "\"message_id\":1,\"date\":1752451200,"
            + "\"chat\":{\"id\":-100123,\"type\":\"supergroup\"}";

    private ObjectMapper mapper;

    @BeforeEach
    void setUp() {
        mapper = new ObjectMapper();
    }

    @Test
    public void testReceiverUserDeserialization() throws IOException {
        Message message = mapper.readValue("{" + BASE_MESSAGE
                + ",\"receiver_user\":{\"id\":12345,\"is_bot\":false,\"first_name\":\"John\"}}", Message.class);

        assertTrue(message.hasReceiverUser());
        assertNotNull(message.getReceiverUser());
        assertEquals(12345L, message.getReceiverUser().getId());
        assertEquals("John", message.getReceiverUser().getFirstName());
    }

    @Test
    public void testEphemeralMessageIdDeserialization() throws IOException {
        Message message = mapper.readValue("{" + BASE_MESSAGE + ",\"ephemeral_message_id\":77}", Message.class);

        assertTrue(message.hasEphemeralMessageId());
        assertEquals(77, message.getEphemeralMessageId());
    }

    @Test
    public void testNonEphemeralMessage() throws IOException {
        Message message = mapper.readValue("{" + BASE_MESSAGE + "}", Message.class);

        assertFalse(message.hasReceiverUser());
        assertFalse(message.hasEphemeralMessageId());
    }
}
