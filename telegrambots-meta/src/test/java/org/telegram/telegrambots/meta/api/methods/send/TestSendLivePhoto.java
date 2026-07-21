package org.telegram.telegrambots.meta.api.methods.send;

import org.junit.jupiter.api.Test;
import org.telegram.telegrambots.meta.api.objects.InputFile;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class TestSendLivePhoto {

    @Test
    public void testSendLivePhotoGetPath() {
        SendLivePhoto sendLivePhoto = SendLivePhoto.builder()
                .chatId("123456789")
                .livePhoto(new InputFile("live_photo_id"))
                .photo(new InputFile("photo_id"))
                .build();

        assertEquals("sendLivePhoto", sendLivePhoto.getMethod());
    }

    @Test
    public void testSendLivePhotoValidation() {
        SendLivePhoto sendLivePhoto = SendLivePhoto.builder()
                .chatId("123456789")
                .livePhoto(new InputFile("live_photo_id"))
                .photo(new InputFile("photo_id"))
                .build();

        assertDoesNotThrow(sendLivePhoto::validate);
    }

    @Test
    public void testSendLivePhotoValidationMissingChatId() {
        assertThrows(NullPointerException.class, () -> SendLivePhoto.builder()
                .livePhoto(new InputFile("live_photo_id"))
                .photo(new InputFile("photo_id"))
                .build());
    }

    @Test
    public void testSendLivePhotoValidationMissingLivePhoto() {
        assertThrows(NullPointerException.class, () -> SendLivePhoto.builder()
                .chatId("123456789")
                .photo(new InputFile("photo_id"))
                .build());
    }

    @Test
    public void testSendLivePhotoValidationMissingPhoto() {
        assertThrows(NullPointerException.class, () -> SendLivePhoto.builder()
                .chatId("123456789")
                .livePhoto(new InputFile("live_photo_id"))
                .build());
    }
}
