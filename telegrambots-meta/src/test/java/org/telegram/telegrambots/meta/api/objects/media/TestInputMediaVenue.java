package org.telegram.telegrambots.meta.api.objects.media;

import org.junit.jupiter.api.Test;
import org.telegram.telegrambots.meta.exceptions.TelegramApiValidationException;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * @author Ruben Bermudez
 * @version 10.0
 */
public class TestInputMediaVenue {

    @Test
    public void testInputMediaVenueType() {
        InputMediaVenue media = new InputMediaVenue(51.5074, -0.1278, "Test Venue", "123 Test St");

        assertEquals("venue", media.getType());
    }

    @Test
    public void testInputMediaVenueConstructor() {
        InputMediaVenue media = new InputMediaVenue(51.5074, -0.1278, "Test Venue", "123 Test St");

        assertEquals(51.5074, media.getLatitude());
        assertEquals(-0.1278, media.getLongitude());
        assertEquals("Test Venue", media.getTitle());
        assertEquals("123 Test St", media.getAddress());
    }

    @Test
    public void testInputMediaVenueValidation() {
        InputMediaVenue media = new InputMediaVenue(51.5074, -0.1278, "Test Venue", "123 Test St");

        assertDoesNotThrow(media::validate);
    }

    @Test
    public void testInputMediaVenueValidationMissingLatitude() {
        InputMediaVenue media = new InputMediaVenue(null, -0.1278, "Test Venue", "123 Test St");

        assertThrows(TelegramApiValidationException.class, media::validate);
    }

    @Test
    public void testInputMediaVenueValidationMissingLongitude() {
        InputMediaVenue media = new InputMediaVenue(51.5074, null, "Test Venue", "123 Test St");

        assertThrows(TelegramApiValidationException.class, media::validate);
    }

    @Test
    public void testInputMediaVenueValidationMissingTitle() {
        InputMediaVenue media = new InputMediaVenue(51.5074, -0.1278, null, "123 Test St");

        assertThrows(TelegramApiValidationException.class, media::validate);
    }

    @Test
    public void testInputMediaVenueValidationMissingAddress() {
        InputMediaVenue media = new InputMediaVenue(51.5074, -0.1278, "Test Venue", null);

        assertThrows(TelegramApiValidationException.class, media::validate);
    }

    @Test
    public void testInputMediaVenueWithOptionalParams() {
        InputMediaVenue media = InputMediaVenue.builder()
                .media("venue")
                .latitude(51.5074)
                .longitude(-0.1278)
                .title("Test Venue")
                .address("123 Test St")
                .foursquareId("fsq_123")
                .foursquareType("arts_entertainment")
                .googlePlaceId("gp_123")
                .googlePlaceType("establishment")
                .build();

        assertDoesNotThrow(media::validate);
        assertEquals("fsq_123", media.getFoursquareId());
        assertEquals("arts_entertainment", media.getFoursquareType());
        assertEquals("gp_123", media.getGooglePlaceId());
        assertEquals("establishment", media.getGooglePlaceType());
    }

    @Test
    public void testInputMediaVenueWithoutOptionalParams() {
        InputMediaVenue media = new InputMediaVenue(51.5074, -0.1278, "Test Venue", "123 Test St");

        assertNull(media.getFoursquareId());
        assertNull(media.getFoursquareType());
        assertNull(media.getGooglePlaceId());
        assertNull(media.getGooglePlaceType());
    }
}
