package org.telegram.telegrambots.meta.api.objects.photo.input;

import org.junit.jupiter.api.Test;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.exceptions.TelegramApiValidationException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;

/**
 * @author Ruben Bermudez
 * @version 9.0
 */
public class TestInputProfilePhoto {

    @Test
    public void testInputProfilePhotoStaticType() {
        InputFile inputFile = new InputFile("test-photo-id");
        InputProfilePhotoStatic profilePhoto = InputProfilePhotoStatic.builder()
                .photo(inputFile)
                .build();

        assertEquals("static", profilePhoto.getType());
    }

    @Test
    public void testInputProfilePhotoStaticValidation() {
        InputFile inputFile = new InputFile("test-photo-id");
        InputProfilePhotoStatic profilePhoto = InputProfilePhotoStatic.builder()
                .photo(inputFile)
                .build();

        // Should not throw an exception
        try {
            profilePhoto.validate();
        } catch (TelegramApiValidationException e) {
            fail();
        }

        // Test with empty photo ID
        InputFile invalidInputFile = new InputFile("");
        InputProfilePhotoStatic invalidProfilePhoto = InputProfilePhotoStatic.builder()
                .photo(invalidInputFile)
                .build();

        assertThrows(TelegramApiValidationException.class, invalidProfilePhoto::validate);
    }
}
