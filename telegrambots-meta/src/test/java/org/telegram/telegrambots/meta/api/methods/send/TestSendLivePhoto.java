package org.telegram.telegrambots.meta.api.methods.send;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.telegram.telegrambots.meta.api.objects.InputFile;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

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

    @Test
    public void testSendLivePhotoExposesEphemeralParametersToMultipartBuilder() {
        SendMediaBotMethod<?> sendLivePhoto = SendLivePhoto.builder()
                .chatId("123456789")
                .livePhoto(new InputFile("live_photo_id"))
                .photo(new InputFile("photo_id"))
                .receiverUserId(98765L)
                .callbackQueryId("query_id")
                .build();

        assertEquals(98765L, sendLivePhoto.getReceiverUserId());
        assertEquals("query_id", sendLivePhoto.getCallbackQueryId());
    }

    @Test
    public void testSendLivePhotoSerializesEphemeralParameters() throws IOException {
        SendLivePhoto sendLivePhoto = SendLivePhoto.builder()
                .chatId("123456789")
                .livePhoto(new InputFile("live_photo_id"))
                .photo(new InputFile("photo_id"))
                .receiverUserId(98765L)
                .callbackQueryId("query_id")
                .build();

        String json = new ObjectMapper().writeValueAsString(sendLivePhoto);

        assertTrue(json.contains("\"receiver_user_id\":98765"), json);
        assertTrue(json.contains("\"callback_query_id\":\"query_id\""), json);
    }
}
