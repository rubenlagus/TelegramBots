package org.telegram.telegrambots.meta.api.objects.payments.paidmedia;

import org.junit.jupiter.api.Test;
import org.telegram.telegrambots.meta.api.objects.LivePhoto;
import org.telegram.telegrambots.meta.exceptions.TelegramApiValidationException;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * @author Ruben Bermudez
 * @version 10.0
 */
public class TestPaidMediaLivePhoto {

    @Test
    public void testPaidMediaLivePhotoType() {
        PaidMediaLivePhoto paidMedia = new PaidMediaLivePhoto();

        assertEquals("live_photo", paidMedia.getType());
    }

    @Test
    public void testPaidMediaLivePhotoValidation() {
        LivePhoto livePhoto = new LivePhoto();
        PaidMediaLivePhoto paidMedia = new PaidMediaLivePhoto(livePhoto);

        assertDoesNotThrow(paidMedia::validate);
    }

    @Test
    public void testPaidMediaLivePhotoValidationMissingLivePhoto() {
        PaidMediaLivePhoto paidMedia = new PaidMediaLivePhoto();

        assertThrows(TelegramApiValidationException.class, paidMedia::validate);
    }

    @Test
    public void testPaidMediaLivePhotoWithLivePhoto() {
        LivePhoto livePhoto = new LivePhoto();
        PaidMediaLivePhoto paidMedia = PaidMediaLivePhoto.builder()
                .livePhoto(livePhoto)
                .build();

        assertEquals(livePhoto, paidMedia.getLivePhoto());
        assertEquals("live_photo", paidMedia.getType());
    }
}
