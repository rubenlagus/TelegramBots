package org.telegram.telegrambots.meta.api.methods.business;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.telegram.telegrambots.meta.api.interfaces.BotApiObject;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.photo.input.InputProfilePhoto;
import org.telegram.telegrambots.meta.api.objects.photo.input.InputProfilePhotoStatic;
import org.telegram.telegrambots.meta.exceptions.TelegramApiValidationException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;

/**
 * @author Ruben Bermudez
 * @version 9.0
 */
public class TestSetBusinessAccountProfilePhoto {

    @Test
    public void testSetBusinessAccountProfilePhotoGetPath() {
        InputProfilePhoto photo = InputProfilePhotoStatic.builder()
                .photo(new InputFile("test-photo-id"))
                .build();

        SetBusinessAccountProfilePhoto setBusinessAccountProfilePhoto = SetBusinessAccountProfilePhoto.builder()
                .businessConnectionId("test-connection-id")
                .photo(photo)
                .build();

        assertEquals("setBusinessAccountProfilePhoto", setBusinessAccountProfilePhoto.getMethod());
    }

    @Test
    public void testSetBusinessAccountProfilePhotoValidation() {
        InputProfilePhoto photo = InputProfilePhotoStatic.builder()
                .photo(new InputFile("test-photo-id"))
                .build();

        SetBusinessAccountProfilePhoto setBusinessAccountProfilePhoto = SetBusinessAccountProfilePhoto.builder()
                .businessConnectionId("test-connection-id")
                .photo(photo)
                .build();

        // Should not throw an exception
        try {
            setBusinessAccountProfilePhoto.validate();
        } catch (TelegramApiValidationException e) {
            fail();
        }

        // Test with empty business connection ID
        SetBusinessAccountProfilePhoto invalidSetBusinessAccountProfilePhoto = SetBusinessAccountProfilePhoto.builder()
                .businessConnectionId("")
                .photo(photo)
                .build();

        assertThrows(TelegramApiValidationException.class, invalidSetBusinessAccountProfilePhoto::validate);

        // Test with invalid photo
        InputProfilePhoto invalidPhoto = Mockito.mock(InputProfilePhoto.class);
        try {
            Mockito.doThrow(new TelegramApiValidationException("Invalid photo", (BotApiObject) null))
                    .when(invalidPhoto).validate();
        } catch (TelegramApiValidationException e) {
            fail();
        }

        SetBusinessAccountProfilePhoto withInvalidPhoto = SetBusinessAccountProfilePhoto.builder()
                .businessConnectionId("test-connection-id")
                .photo(invalidPhoto)
                .build();

        assertThrows(TelegramApiValidationException.class, withInvalidPhoto::validate);
    }

    @Test
    public void testSetBusinessAccountProfilePhotoWithIsPublic() {
        InputProfilePhoto photo = InputProfilePhotoStatic.builder()
                .photo(new InputFile("test-photo-id"))
                .build();

        SetBusinessAccountProfilePhoto setBusinessAccountProfilePhoto = SetBusinessAccountProfilePhoto.builder()
                .businessConnectionId("test-connection-id")
                .photo(photo)
                .isPublic(true)
                .build();

        assertEquals(true, setBusinessAccountProfilePhoto.getIsPublic());
        assertEquals("test-connection-id", setBusinessAccountProfilePhoto.getBusinessConnectionId());
        assertEquals(photo, setBusinessAccountProfilePhoto.getPhoto());
    }
}
