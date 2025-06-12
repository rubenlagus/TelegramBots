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
public class TestInputStoryContentPhoto {
    private final ObjectMapper mapper = new ObjectMapper();

    @Test
    public void testJsonSerialization() {
        try {
            String expectedJson = "{\"photo\":\"test_photo.jpg\",\"type\":\"photo\"}";
            
            InputStoryContentPhoto inputStoryContentPhoto = InputStoryContentPhoto.builder()
                    .photo("test_photo.jpg")
                    .build();

            String json = mapper.writeValueAsString(inputStoryContentPhoto);
            assertEquals(expectedJson, json);
            
            InputStoryContentPhoto deserialized = mapper.readValue(json, InputStoryContentPhoto.class);
            assertEquals(inputStoryContentPhoto.getPhoto(), deserialized.getPhoto());
            assertEquals(inputStoryContentPhoto.getType(), deserialized.getType());
        } catch (Exception e) {
            fail(e);
        }
    }

    @Test
    public void testValidation() {
        // Valid case
        try {
            InputStoryContentPhoto validContent = InputStoryContentPhoto.builder()
                    .photo("valid_photo.jpg")
                    .build();
            validContent.validate();
        } catch (TelegramApiValidationException e) {
            fail("Validation should not fail for valid content");
        }

        // Invalid case - empty photo
        assertThrows(TelegramApiValidationException.class, () -> {
            InputStoryContentPhoto invalidContent = InputStoryContentPhoto.builder()
                    .photo("")
                    .build();
            invalidContent.validate();
        });
    }
}
