package org.telegram.telegrambots.meta.api.objects.stories.area;

import org.junit.jupiter.api.Test;
import org.telegram.telegrambots.meta.exceptions.TelegramApiValidationException;
import tools.jackson.databind.JsonNode;
import tools.jackson.databind.json.JsonMapper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;

/**
 * @author Ruben Bermudez
 * @version 9.0
 */
public class TestStoryAreaTypeLink {
    private final JsonMapper mapper = new JsonMapper();

    @Test
    public void testJsonSerialization() {
        try {
            JsonNode expectedJson = mapper.readTree("{\"url\":\"https://t.me/test\",\"type\":\"link\"}");
            
            StoryAreaTypeLink storyAreaTypeLink = StoryAreaTypeLink.builder()
                    .url("https://t.me/test")
                    .build();

            JsonNode json = mapper.valueToTree(storyAreaTypeLink);
            assertEquals(expectedJson, json);
            
            StoryAreaTypeLink deserialized = mapper.treeToValue(json, StoryAreaTypeLink.class);
            assertEquals(storyAreaTypeLink.getUrl(), deserialized.getUrl());
            assertEquals(storyAreaTypeLink.getType(), deserialized.getType());
        } catch (Exception e) {
            fail(e);
        }
    }

    @Test
    public void testValidation() {
        // Valid case
        try {
            StoryAreaTypeLink validArea = StoryAreaTypeLink.builder()
                    .url("https://t.me/valid")
                    .build();
            validArea.validate();
        } catch (TelegramApiValidationException e) {
            fail("Validation should not fail for valid area");
        }

        // Invalid case - empty URL
        assertThrows(TelegramApiValidationException.class, () -> {
            StoryAreaTypeLink invalidArea = StoryAreaTypeLink.builder()
                    .url("")
                    .build();
            invalidArea.validate();
        });
    }
}
