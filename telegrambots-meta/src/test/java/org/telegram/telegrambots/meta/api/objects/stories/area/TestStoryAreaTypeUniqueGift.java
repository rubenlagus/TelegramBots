package org.telegram.telegrambots.meta.api.objects.stories.area;

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
public class TestStoryAreaTypeUniqueGift {
    private final ObjectMapper mapper = new ObjectMapper();

    @Test
    public void testJsonSerialization() {
        try {
            String expectedJson = "{\"name\":\"unique_gift_name\",\"type\":\"unique_gift\"}";
            
            StoryAreaTypeUniqueGift storyAreaTypeUniqueGift = StoryAreaTypeUniqueGift.builder()
                    .name("unique_gift_name")
                    .build();

            String json = mapper.writeValueAsString(storyAreaTypeUniqueGift);
            assertEquals(expectedJson, json);
            
            StoryAreaTypeUniqueGift deserialized = mapper.readValue(json, StoryAreaTypeUniqueGift.class);
            assertEquals(storyAreaTypeUniqueGift.getName(), deserialized.getName());
            assertEquals(storyAreaTypeUniqueGift.getType(), deserialized.getType());
        } catch (Exception e) {
            fail(e);
        }
    }

    @Test
    public void testValidation() {
        // Valid case
        try {
            StoryAreaTypeUniqueGift validArea = StoryAreaTypeUniqueGift.builder()
                    .name("valid_gift_name")
                    .build();
            validArea.validate();
        } catch (TelegramApiValidationException e) {
            fail("Validation should not fail for valid area");
        }

        // Invalid case - empty name
        assertThrows(TelegramApiValidationException.class, () -> {
            StoryAreaTypeUniqueGift invalidArea = StoryAreaTypeUniqueGift.builder()
                    .name("")
                    .build();
            invalidArea.validate();
        });
    }
}
