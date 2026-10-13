package org.telegram.telegrambots.meta.api.methods.send;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * @author Ruben Bermudez
 * @version 10.3
 */
public class TestSendMessageDraft {
    private final ObjectMapper mapper = new ObjectMapper();

    @Test
    public void testCanStopAndKeepOnStopSerialize() throws IOException {
        SendMessageDraft method = SendMessageDraft.builder()
                .chatId(1L)
                .draftId(7)
                .text("hi")
                .canStop(true)
                .keepOnStop(true)
                .build();

        String json = mapper.writeValueAsString(method);

        assertTrue(json.contains("\"can_stop\":true"), json);
        assertTrue(json.contains("\"keep_on_stop\":true"), json);
    }

    @Test
    public void testCanStopAndKeepOnStopOmittedWhenUnset() throws IOException {
        SendMessageDraft method = SendMessageDraft.builder()
                .chatId(1L)
                .draftId(7)
                .text("hi")
                .build();

        String json = mapper.writeValueAsString(method);

        assertFalse(json.contains("can_stop"), json);
        assertFalse(json.contains("keep_on_stop"), json);
    }
}
