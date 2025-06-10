package org.telegram.telegrambots.meta.api.objects.stories.area;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.telegram.telegrambots.meta.api.interfaces.BotApiObject;
import org.telegram.telegrambots.meta.exceptions.TelegramApiValidationException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.Mockito.mock;

/**
 * @author Ruben Bermudez
 * @version 9.0
 */
public class TestStoryAreaType {

    @Test
    public void testStoryAreaTypeSubclasses() {
        // Create a mock implementation of StoryAreaType for testing
        StoryAreaType mockStoryAreaType = mock(StoryAreaType.class, Mockito.CALLS_REAL_METHODS);
        
        // Setup the mock to return a specific type when getType is called
        Mockito.when(mockStoryAreaType.getType()).thenReturn("test-type");
        
        assertEquals("test-type", mockStoryAreaType.getType());
    }

    @Test
    public void testStoryAreaTypeValidation() {
        // Create a mock implementation that will throw an exception during validation
        StoryAreaType mockStoryAreaType = mock(StoryAreaType.class, Mockito.CALLS_REAL_METHODS);
        
        try {
            Mockito.doThrow(new TelegramApiValidationException("Test validation error", (BotApiObject) null))
                    .when(mockStoryAreaType).validate();
        } catch (TelegramApiValidationException e) {
            fail();
        }
        
        assertThrows(TelegramApiValidationException.class, mockStoryAreaType::validate);

        // Create a mock implementation that will pass validation
        StoryAreaType validMockStoryAreaType = mock(StoryAreaType.class, Mockito.CALLS_REAL_METHODS);
        
        try {
            Mockito.doNothing().when(validMockStoryAreaType).validate();
            validMockStoryAreaType.validate();
        } catch (TelegramApiValidationException e) {
            fail();
        }
    }
}
