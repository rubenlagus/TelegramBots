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
public class TestInputMediaLivePhoto {

    @Test
    public void testInputMediaLivePhotoType() {
        InputMediaLivePhoto media = new InputMediaLivePhoto("media_id", "photo_id");

        assertEquals("live_photo", media.getType());
    }

    @Test
    public void testInputMediaLivePhotoConstructor() {
        InputMediaLivePhoto media = new InputMediaLivePhoto("media_id", "photo_id");

        assertEquals("media_id", media.getMedia());
        assertEquals("photo_id", media.getPhoto());
    }

    @Test
    public void testInputMediaLivePhotoValidation() {
        InputMediaLivePhoto media = new InputMediaLivePhoto("media_id", "photo_id");

        assertDoesNotThrow(media::validate);
    }

    @Test
    public void testInputMediaLivePhotoValidationMissingPhoto() {
        assertThrows(NullPointerException.class, () -> new InputMediaLivePhoto("media_id", null));
    }

    @Test
    public void testInputMediaLivePhotoValidationEmptyPhoto() {
        InputMediaLivePhoto media = InputMediaLivePhoto.builder()
                .media("media_id")
                .photo("")
                .build();

        assertThrows(TelegramApiValidationException.class, media::validate);
    }

    @Test
    public void testInputMediaLivePhotoWithOptionalParams() {
        InputMediaLivePhoto media = InputMediaLivePhoto.builder()
                .media("media_id")
                .photo("photo_id")
                .hasSpoiler(true)
                .showCaptionAboveMedia(true)
                .build();

        assertEquals("media_id", media.getMedia());
        assertEquals("photo_id", media.getPhoto());
        assertEquals(Boolean.TRUE, media.getHasSpoiler());
        assertEquals(Boolean.TRUE, media.getShowCaptionAboveMedia());
    }

    @Test
    public void testInputMediaLivePhotoWithoutOptionalParams() {
        InputMediaLivePhoto media = new InputMediaLivePhoto("media_id", "photo_id");

        assertNull(media.getHasSpoiler());
        assertNull(media.getShowCaptionAboveMedia());
    }
}
