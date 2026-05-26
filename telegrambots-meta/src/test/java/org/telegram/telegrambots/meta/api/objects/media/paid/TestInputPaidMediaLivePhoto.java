package org.telegram.telegrambots.meta.api.objects.media.paid;

import org.junit.jupiter.api.Test;
import org.telegram.telegrambots.meta.exceptions.TelegramApiValidationException;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * @author Ruben Bermudez
 * @version 10.0
 */
public class TestInputPaidMediaLivePhoto {

    @Test
    public void testInputPaidMediaLivePhotoType() {
        InputPaidMediaLivePhoto media = new InputPaidMediaLivePhoto("media_id", "photo_id");

        assertEquals("live_photo", media.getType());
    }

    @Test
    public void testInputPaidMediaLivePhotoConstructor() {
        InputPaidMediaLivePhoto media = new InputPaidMediaLivePhoto("media_id", "photo_id");

        assertEquals("media_id", media.getMedia());
        assertEquals("photo_id", media.getPhoto());
    }

    @Test
    public void testInputPaidMediaLivePhotoValidation() {
        InputPaidMediaLivePhoto media = new InputPaidMediaLivePhoto("media_id", "photo_id");

        assertDoesNotThrow(media::validate);
    }

    @Test
    public void testInputPaidMediaLivePhotoValidationMissingMedia() {
        assertThrows(NullPointerException.class, () -> new InputPaidMediaLivePhoto(null, "photo_id"));
    }

    @Test
    public void testInputPaidMediaLivePhotoValidationMissingPhoto() {
        assertThrows(NullPointerException.class, () -> new InputPaidMediaLivePhoto("media_id", null));
    }

    @Test
    public void testInputPaidMediaLivePhotoValidationEmptyPhoto() {
        InputPaidMediaLivePhoto media = InputPaidMediaLivePhoto.builder()
                .media("media_id")
                .photo("")
                .build();

        assertThrows(TelegramApiValidationException.class, media::validate);
    }

    @Test
    public void testInputPaidMediaLivePhotoBuilderWithAllFields() {
        InputPaidMediaLivePhoto media = InputPaidMediaLivePhoto.builder()
                .media("media_id")
                .photo("photo_id")
                .build();

        assertEquals("media_id", media.getMedia());
        assertEquals("photo_id", media.getPhoto());
        assertDoesNotThrow(media::validate);
    }
}
