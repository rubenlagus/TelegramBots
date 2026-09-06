package org.telegram.telegrambots.meta.api.objects;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.telegram.telegrambots.meta.api.methods.updates.AllowedUpdates;
import org.telegram.telegrambots.meta.api.objects.message.MessageGenerationStopped;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * @author Ruben Bermudez
 * @version 10.3
 */
public class TestMessageGenerationStopped {
    private ObjectMapper mapper;

    @BeforeEach
    void setUp() {
        mapper = new ObjectMapper();
    }

    @Test
    public void testAllowedUpdatesConstant() {
        assertEquals("stopped_message_generation", AllowedUpdates.STOPPEDMESSAGEGENERATION);
    }

    @Test
    public void testDeserializeOnUpdate() throws IOException {
        String json = "{\"update_id\":1,\"stopped_message_generation\":{"
                + "\"chat\":{\"id\":5,\"type\":\"private\"},\"message_thread_id\":9,\"draft_id\":42}}";

        Update update = mapper.readValue(json, Update.class);

        assertTrue(update.hasStoppedMessageGeneration());
        MessageGenerationStopped stopped = update.getStoppedMessageGeneration();
        assertEquals(5L, stopped.getChat().getId());
        assertEquals(9, stopped.getMessageThreadId());
        assertEquals(42, stopped.getDraftId());
    }

    @Test
    public void testMessageThreadIdIsOptional() throws IOException {
        String json = "{\"chat\":{\"id\":5,\"type\":\"private\"},\"draft_id\":42}";

        MessageGenerationStopped stopped = mapper.readValue(json, MessageGenerationStopped.class);

        assertNull(stopped.getMessageThreadId());
    }

    @Test
    public void testHasStoppedMessageGenerationFalseWhenAbsent() throws IOException {
        assertFalse(mapper.readValue("{\"update_id\":1}", Update.class).hasStoppedMessageGeneration());
    }
}
