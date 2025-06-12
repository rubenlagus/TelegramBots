package org.telegram.telegrambots.meta.api.objects.stories.input;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.telegram.telegrambots.meta.exceptions.TelegramApiValidationException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;

/**
 * @author Ruben Bermudez
 * @version 9.0
 */
public class TestInputStoryContentVideo {
    private final ObjectMapper mapper = new ObjectMapper();

    @Test
    public void testJsonSerialization() {
        try {
            String expectedJson = "{\"video\":\"test_video.mp4\",\"duration\":30.5,\"cover_frame_timestamp\":10.5,\"is_animation\":true,\"type\":\"video\"}";
            
            InputStoryContentVideo inputStoryContentVideo = InputStoryContentVideo.builder()
                    .video("test_video.mp4")
                    .duration(30.5f)
                    .coverFrameTimestamp(10.5f)
                    .isAnimation(true)
                    .build();

            String json = mapper.writeValueAsString(inputStoryContentVideo);
            assertEquals(expectedJson, json);
            
            InputStoryContentVideo deserialized = mapper.readValue(json, InputStoryContentVideo.class);
            assertEquals(inputStoryContentVideo.getVideo(), deserialized.getVideo());
            assertEquals(inputStoryContentVideo.getDuration(), deserialized.getDuration());
            assertEquals(inputStoryContentVideo.getCoverFrameTimestamp(), deserialized.getCoverFrameTimestamp());
            assertEquals(inputStoryContentVideo.getIsAnimation(), deserialized.getIsAnimation());
            assertEquals(inputStoryContentVideo.getType(), deserialized.getType());
        } catch (Exception e) {
            fail(e);
        }
    }

    @Test
    public void testValidation() {
        // Valid case
        try {
            InputStoryContentVideo validContent = InputStoryContentVideo.builder()
                    .video("valid_video.mp4")
                    .duration(30.0f)
                    .build();
            validContent.validate();
        } catch (TelegramApiValidationException e) {
            fail("Validation should not fail for valid content");
        }

        // Invalid case - empty video
        assertThrows(TelegramApiValidationException.class, () -> {
            InputStoryContentVideo invalidContent = InputStoryContentVideo.builder()
                    .video("")
                    .build();
            invalidContent.validate();
        });

        // Invalid case - duration out of range (negative)
        assertThrows(TelegramApiValidationException.class, () -> {
            InputStoryContentVideo invalidContent = InputStoryContentVideo.builder()
                    .video("valid_video.mp4")
                    .duration(-1.0f)
                    .build();
            invalidContent.validate();
        });

        // Invalid case - duration out of range (too high)
        assertThrows(TelegramApiValidationException.class, () -> {
            InputStoryContentVideo invalidContent = InputStoryContentVideo.builder()
                    .video("valid_video.mp4")
                    .duration(61.0f)
                    .build();
            invalidContent.validate();
        });
    }
}
