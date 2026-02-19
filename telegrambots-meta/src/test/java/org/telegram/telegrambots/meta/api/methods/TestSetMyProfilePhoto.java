package org.telegram.telegrambots.meta.api.methods;

import org.junit.jupiter.api.Test;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.photo.input.InputProfilePhoto;
import org.telegram.telegrambots.meta.api.objects.photo.input.InputProfilePhotoStatic;
import org.telegram.telegrambots.meta.exceptions.TelegramApiRequestException;
import org.telegram.telegrambots.meta.exceptions.TelegramApiValidationException;

import java.io.File;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

/**
 * @author Ruben Bermudez
 * @version 9.4
 */
public class TestSetMyProfilePhoto {

    @Test
    public void testSetMyProfilePhotoMethod() {
        InputProfilePhoto photo = InputProfilePhotoStatic.builder()
                .photo(new InputFile(new File("photo.jpg")))
                .build();
        SetMyProfilePhoto setMyProfilePhoto = SetMyProfilePhoto.builder()
                .photo(photo)
                .build();

        assertEquals("setMyProfilePhoto", setMyProfilePhoto.getMethod());
        assertNotNull(setMyProfilePhoto.getPhoto());
    }

    @Test
    public void testSetMyProfilePhotoValidation() throws TelegramApiValidationException {
        InputProfilePhoto photo = InputProfilePhotoStatic.builder()
                .photo(new InputFile(new File("photo.jpg")))
                .build();
        SetMyProfilePhoto setMyProfilePhoto = SetMyProfilePhoto.builder()
                .photo(photo)
                .build();

        // Should not throw exception
        setMyProfilePhoto.validate();
    }

    @Test
    public void testSetMyProfilePhotoValidationFailsWithoutPhoto() {
        SetMyProfilePhoto setMyProfilePhoto = SetMyProfilePhoto.builder()
                .build();

        assertThrows(TelegramApiValidationException.class, setMyProfilePhoto::validate);
    }

    @Test
    public void testSetMyProfilePhotoDeserializeValidResponse() {
        String responseText = "{\"ok\":true,\"result\":true}";

        InputProfilePhoto photo = InputProfilePhotoStatic.builder()
                .photo(new InputFile(new File("photo.jpg")))
                .build();
        SetMyProfilePhoto setMyProfilePhoto = SetMyProfilePhoto.builder()
                .photo(photo)
                .build();

        try {
            Boolean result = setMyProfilePhoto.deserializeResponse(responseText);
            assertNotNull(result);
            assertTrue(result);
        } catch (TelegramApiRequestException e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void testSetMyProfilePhotoErrorResponse() {
        String responseText = "{\"ok\":false,\"error_code\":400,\"description\":\"Bad Request: invalid photo\"}";

        InputProfilePhoto photo = InputProfilePhotoStatic.builder()
                .photo(new InputFile(new File("photo.jpg")))
                .build();
        SetMyProfilePhoto setMyProfilePhoto = SetMyProfilePhoto.builder()
                .photo(photo)
                .build();

        try {
            setMyProfilePhoto.deserializeResponse(responseText);
            fail();
        } catch (TelegramApiRequestException e) {
            assertEquals(400, e.getErrorCode());
            assertEquals("Bad Request: invalid photo", e.getApiResponse());
        }
    }
}
