package org.telegram.telegrambots.meta.api.objects.stories.area;

import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import org.junit.jupiter.api.Test;
import org.telegram.telegrambots.meta.exceptions.TelegramApiValidationException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;

/**
 * @author Ruben Bermudez
 * @version 9.0
 */
public class TestStoryAreaTypeWeather {
    private final ObjectMapper mapper = JsonMapper.builder()
            .enable(MapperFeature.SORT_PROPERTIES_ALPHABETICALLY)
            .build();

    @Test
    public void testJsonSerialization() {
        try {
            String expectedJson = "{\"background_color\":16777215,\"emoji\":\"☀️\",\"temperature\":25.5,\"type\":\"weather\"}";
            
            StoryAreaTypeWeather storyAreaTypeWeather = StoryAreaTypeWeather.builder()
                    .temperature(25.5f)
                    .emoji("☀️")
                    .backgroundColor(16777215)  // White color in integer
                    .build();

            String json = mapper.writeValueAsString(storyAreaTypeWeather);
            assertEquals(expectedJson, json);
            
            StoryAreaTypeWeather deserialized = mapper.readValue(json, StoryAreaTypeWeather.class);
            assertEquals(storyAreaTypeWeather.getTemperature(), deserialized.getTemperature());
            assertEquals(storyAreaTypeWeather.getEmoji(), deserialized.getEmoji());
            assertEquals(storyAreaTypeWeather.getBackgroundColor(), deserialized.getBackgroundColor());
            assertEquals(storyAreaTypeWeather.getType(), deserialized.getType());
        } catch (Exception e) {
            fail(e);
        }
    }

    @Test
    public void testValidation() {
        // Valid case
        try {
            StoryAreaTypeWeather validArea = StoryAreaTypeWeather.builder()
                    .temperature(25.5f)
                    .emoji("☀️")
                    .backgroundColor(16777215)
                    .build();
            validArea.validate();
        } catch (TelegramApiValidationException e) {
            fail("Validation should not fail for valid area");
        }

        // Invalid case - empty emoji
        assertThrows(TelegramApiValidationException.class, () -> {
            StoryAreaTypeWeather invalidArea = StoryAreaTypeWeather.builder()
                    .temperature(25.5f)
                    .emoji("")
                    .backgroundColor(16777215)
                    .build();
            invalidArea.validate();
        });
    }
}
