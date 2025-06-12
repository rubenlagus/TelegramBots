package org.telegram.telegrambots.meta.api.objects.stories.area;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.telegram.telegrambots.meta.api.interfaces.BotApiObject;
import org.telegram.telegrambots.meta.api.objects.location.LocationAddress;
import org.telegram.telegrambots.meta.exceptions.TelegramApiValidationException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;

/**
 * @author Ruben Bermudez
 * @version 9.0
 */
public class TestStoryAreaTypeLocation {

    @Test
    public void testStoryAreaTypeLocationType() {
        LocationAddress address = Mockito.mock(LocationAddress.class);
        
        StoryAreaTypeLocation storyAreaTypeLocation = StoryAreaTypeLocation.builder()
                .latitude(55.7558f)
                .longitude(37.6173f)
                .address(address)
                .build();

        assertEquals("location", storyAreaTypeLocation.getType());
    }

    @Test
    public void testStoryAreaTypeLocationValidation() {
        LocationAddress address = Mockito.mock(LocationAddress.class);
        
        StoryAreaTypeLocation storyAreaTypeLocation = StoryAreaTypeLocation.builder()
                .latitude(55.7558f)
                .longitude(37.6173f)
                .address(address)
                .build();

        // Should not throw an exception when address validation passes
        try {
            Mockito.doNothing().when(address).validate();
            storyAreaTypeLocation.validate();

            // Should throw exception when address validation fails
            Mockito.doThrow(new TelegramApiValidationException("Invalid address", (BotApiObject) null))
                    .when(address).validate();
        } catch (TelegramApiValidationException e) {
            fail();
        }

        assertThrows(TelegramApiValidationException.class, storyAreaTypeLocation::validate);
    }

    @Test
    public void testStoryAreaTypeLocationGetters() {
        LocationAddress address = Mockito.mock(LocationAddress.class);
        
        StoryAreaTypeLocation storyAreaTypeLocation = StoryAreaTypeLocation.builder()
                .latitude(55.7558f)
                .longitude(37.6173f)
                .address(address)
                .build();

        assertEquals(Float.valueOf(55.7558f), storyAreaTypeLocation.getLatitude());
        assertEquals(Float.valueOf(37.6173f), storyAreaTypeLocation.getLongitude());
        assertEquals(address, storyAreaTypeLocation.getAddress());
    }
}
