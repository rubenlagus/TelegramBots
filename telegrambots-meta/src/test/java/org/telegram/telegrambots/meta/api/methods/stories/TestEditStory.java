package org.telegram.telegrambots.meta.api.methods.stories;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.telegram.telegrambots.meta.api.objects.MessageEntity;
import org.telegram.telegrambots.meta.api.objects.stories.StoryArea;
import org.telegram.telegrambots.meta.api.objects.stories.StoryAreaPosition;
import org.telegram.telegrambots.meta.api.objects.stories.area.StoryAreaTypeLink;
import org.telegram.telegrambots.meta.api.objects.stories.input.InputStoryContentPhoto;
import org.telegram.telegrambots.meta.exceptions.TelegramApiValidationException;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;

/**
 * @author Ruben Bermudez
 * @version 9.0
 */
public class TestEditStory {
    private final ObjectMapper mapper = new ObjectMapper();

    @Test
    public void testJsonSerialization() {
        try {
            InputStoryContentPhoto content = InputStoryContentPhoto.builder()
                    .photo("test_photo.jpg")
                    .build();
            
            MessageEntity entity = new MessageEntity("bold", 0, 4);
            
            StoryArea area = StoryArea
                    .builder()
                    .type(new StoryAreaTypeLink("https://t.me/test"))
                    .position(StoryAreaPosition
                            .builder()
                            .xPercentage(0.1f)
                            .yPercentage(0.2f)
                            .widthPercentage(0.3f)
                            .heightPercentage(0.4f)
                            .rotationAngle(0.0f)
                            .cornerRadiusPercentage(0.0f)
                            .build())
                    .build();
            
            String expectedJson = "{\"business_connection_id\":\"12345\",\"story_id\":67890,\"content\":{\"photo\":" +
                    "\"test_photo.jpg\",\"type\":\"photo\"},\"caption\":\"Test Caption\",\"parse_mode\":\"HTML\"," +
                    "\"caption_entities\":[{\"type\":\"bold\",\"offset\":0,\"length\":4}],\"areas\":[{\"position\":" +
                    "{\"x_percentage\":0.1,\"y_percentage\":0.2,\"width_percentage\":0.3,\"height_percentage\":0.4," +
                    "\"rotation_angle\":0.0,\"corner_radius_percentage\":0.0},\"type\":{\"url\":\"https://t.me/test\"," +
                    "\"type\":\"link\"}}],\"method\":\"editStory\"}";
            
            EditStory editStory = EditStory.builder()
                    .businessConnectionId("12345")
                    .storyId(67890)
                    .content(content)
                    .caption("Test Caption")
                    .parseMode("HTML")
                    .captionEntities(Collections.singletonList(entity))
                    .areas(Collections.singletonList(area))
                    .build();

            String json = mapper.writeValueAsString(editStory);
            assertEquals(expectedJson, json);
        } catch (Exception e) {
            fail(e);
        }
    }

    @Test
    public void testValidation() {
        InputStoryContentPhoto validContent = InputStoryContentPhoto.builder()
                .photo("test_photo.jpg")
                .build();

        StoryArea validArea = StoryArea
                .builder()
                .type(new StoryAreaTypeLink("https://t.me/test"))
                .position(StoryAreaPosition
                        .builder()
                        .xPercentage(0.1f)
                        .yPercentage(0.2f)
                        .widthPercentage(0.3f)
                        .heightPercentage(0.4f)
                        .rotationAngle(0.0f)
                        .cornerRadiusPercentage(0.0f)
                        .build())
                .build();
        
        // Valid case
        try {
            EditStory validMethod = EditStory.builder()
                    .businessConnectionId("12345")
                    .storyId(67890)
                    .content(validContent)
                    .caption("Test Caption")
                    .areas(Collections.singletonList(validArea))
                    .build();
            validMethod.validate();
        } catch (TelegramApiValidationException e) {
            fail("Validation should not fail for valid method");
        }

        // Invalid case - empty businessConnectionId
        assertThrows(TelegramApiValidationException.class, () -> {
            EditStory invalidMethod = EditStory.builder()
                    .businessConnectionId("")
                    .storyId(67890)
                    .content(validContent)
                    .build();
            invalidMethod.validate();
        });
        
        // Invalid case - caption too long
        assertThrows(TelegramApiValidationException.class, () -> {
            StringBuilder longCaption = new StringBuilder();
            for (int i = 0; i < 2049; i++) {
                longCaption.append("a");
            }
            
            EditStory invalidMethod = EditStory.builder()
                    .businessConnectionId("12345")
                    .storyId(67890)
                    .content(validContent)
                    .caption(longCaption.toString())
                    .build();
            invalidMethod.validate();
        });
    }
}
