package org.telegram.telegrambots.meta.api.methods.stories;

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
public class TestDeleteStory {
    private final ObjectMapper mapper = new ObjectMapper();

    @Test
    public void testJsonSerialization() {
        try {
            String expectedJson = "{\"business_connection_id\":\"12345\",\"story_id\":67890,\"method\":\"deleteStory\"}";
            
            DeleteStory deleteStory = DeleteStory.builder()
                    .businessConnectionId("12345")
                    .storyId(67890)
                    .build();

            String json = mapper.writeValueAsString(deleteStory);
            assertEquals(expectedJson, json);
        } catch (Exception e) {
            fail(e);
        }
    }

    @Test
    public void testValidation() {
        // Valid case
        try {
            DeleteStory validMethod = DeleteStory.builder()
                    .businessConnectionId("12345")
                    .storyId(67890)
                    .build();
            validMethod.validate();
        } catch (TelegramApiValidationException e) {
            fail("Validation should not fail for valid method");
        }

        // Invalid case - empty businessConnectionId
        assertThrows(TelegramApiValidationException.class, () -> {
            DeleteStory invalidMethod = DeleteStory.builder()
                    .businessConnectionId("")
                    .storyId(67890)
                    .build();
            invalidMethod.validate();
        });
    }
}
