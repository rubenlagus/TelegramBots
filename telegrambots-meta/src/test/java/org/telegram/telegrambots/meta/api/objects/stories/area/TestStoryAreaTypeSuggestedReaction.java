package org.telegram.telegrambots.meta.api.objects.stories.area;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.telegram.telegrambots.meta.api.objects.reactions.ReactionType;
import org.telegram.telegrambots.meta.api.objects.reactions.ReactionTypeEmoji;
import org.telegram.telegrambots.meta.exceptions.TelegramApiValidationException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

/**
 * @author Ruben Bermudez
 * @version 9.0
 */
public class TestStoryAreaTypeSuggestedReaction {
    private final ObjectMapper mapper = new ObjectMapper();

    @Test
    public void testJsonSerialization() {
        try {
            String expectedJson = "{\"reaction_type\":{\"type\":\"emoji\",\"emoji\":\"👍\"},\"is_dark\":true,\"is_flipped\":true,\"type\":\"suggested_reaction\"}";
            
            ReactionType reactionType = new ReactionTypeEmoji("👍");
            StoryAreaTypeSuggestedReaction storyAreaTypeSuggestedReaction = StoryAreaTypeSuggestedReaction.builder()
                    .reactionType(reactionType)
                    .isDark(true)
                    .isFlipped(true)
                    .build();

            String json = mapper.writeValueAsString(storyAreaTypeSuggestedReaction);
            assertEquals(expectedJson, json);
            
            StoryAreaTypeSuggestedReaction deserialized = mapper.readValue(json, StoryAreaTypeSuggestedReaction.class);
            assertEquals(storyAreaTypeSuggestedReaction.getReactionType().getType(), deserialized.getReactionType().getType());
            assertEquals(storyAreaTypeSuggestedReaction.getIsDark(), deserialized.getIsDark());
            assertEquals(storyAreaTypeSuggestedReaction.getIsFlipped(), deserialized.getIsFlipped());
            assertEquals(storyAreaTypeSuggestedReaction.getType(), deserialized.getType());
        } catch (Exception e) {
            fail(e);
        }
    }

    @Test
    public void testValidation() {
        // Valid case
        try {
            ReactionType reactionType = new ReactionTypeEmoji("👍");
            StoryAreaTypeSuggestedReaction validArea = StoryAreaTypeSuggestedReaction.builder()
                    .reactionType(reactionType)
                    .build();
            validArea.validate();
        } catch (TelegramApiValidationException e) {
            fail("Validation should not fail for valid area");
        }
    }
}
