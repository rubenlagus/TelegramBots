package org.telegram.telegrambots.meta.api.objects.media;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * @author Ruben Bermudez
 * @version 10.2
 */
public class TestInputMediaVoiceNote {
    private ObjectMapper mapper;

    @BeforeEach
    void setUp() {
        mapper = new ObjectMapper();
    }

    @Test
    public void testInputMediaVoiceNoteType() {
        InputMediaVoiceNote media = new InputMediaVoiceNote("voice_file_id");

        assertEquals("voice_note", media.getType());
        assertEquals("voice_file_id", media.getMedia());
        assertDoesNotThrow(media::validate);
    }

    @Test
    public void testInputMediaVoiceNoteDurationIsOptional() {
        InputMediaVoiceNote media = new InputMediaVoiceNote("voice_file_id");

        assertNull(media.getDuration());
    }

    @Test
    public void testInputMediaVoiceNoteSerialization() throws IOException {
        InputMediaVoiceNote media = InputMediaVoiceNote.builder()
                .media("voice_file_id")
                .caption("A voice note")
                .duration(42)
                .build();

        String json = mapper.writeValueAsString(media);

        assertTrue(json.contains("\"type\":\"voice_note\""), json);
        assertTrue(json.contains("\"media\":\"voice_file_id\""), json);
        assertTrue(json.contains("\"caption\":\"A voice note\""), json);
        assertTrue(json.contains("\"duration\":42"), json);
    }

    @Test
    public void testInputMediaVoiceNoteDeserializesThroughInputMedia() throws IOException {
        InputMedia media = mapper.readValue(
                "{\"type\":\"voice_note\",\"media\":\"voice_file_id\",\"duration\":42}", InputMedia.class);

        InputMediaVoiceNote voiceNote = assertInstanceOf(InputMediaVoiceNote.class, media);
        assertEquals("voice_file_id", voiceNote.getMedia());
        assertEquals(42, voiceNote.getDuration());
    }
}
