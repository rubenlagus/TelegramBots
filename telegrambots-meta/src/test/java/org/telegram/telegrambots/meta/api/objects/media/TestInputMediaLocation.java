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
public class TestInputMediaLocation {

    @Test
    public void testInputMediaLocationType() {
        InputMediaLocation media = new InputMediaLocation(51.5074, -0.1278);

        assertEquals("location", media.getType());
    }

    @Test
    public void testInputMediaLocationConstructor() {
        InputMediaLocation media = new InputMediaLocation(51.5074, -0.1278);

        assertEquals(51.5074, media.getLatitude());
        assertEquals(-0.1278, media.getLongitude());
    }

    @Test
    public void testInputMediaLocationValidation() {
        InputMediaLocation media = new InputMediaLocation(51.5074, -0.1278);

        assertDoesNotThrow(media::validate);
    }

    @Test
    public void testInputMediaLocationValidationMissingLatitude() {
        InputMediaLocation media = new InputMediaLocation(null, -0.1278);

        assertThrows(TelegramApiValidationException.class, media::validate);
    }

    @Test
    public void testInputMediaLocationValidationMissingLongitude() {
        InputMediaLocation media = new InputMediaLocation(51.5074, null);

        assertThrows(TelegramApiValidationException.class, media::validate);
    }

    @Test
    public void testInputMediaLocationWithOptionalHorizontalAccuracy() {
        InputMediaLocation media = InputMediaLocation.builder()
                .media("location")
                .latitude(51.5074)
                .longitude(-0.1278)
                .horizontalAccuracy(100.0)
                .build();

        assertDoesNotThrow(media::validate);
        assertEquals(51.5074, media.getLatitude());
        assertEquals(-0.1278, media.getLongitude());
        assertEquals(100.0, media.getHorizontalAccuracy());
    }

    @Test
    public void testInputMediaLocationWithoutOptionalParams() {
        InputMediaLocation media = new InputMediaLocation(51.5074, -0.1278);

        assertNull(media.getHorizontalAccuracy());
    }
}
