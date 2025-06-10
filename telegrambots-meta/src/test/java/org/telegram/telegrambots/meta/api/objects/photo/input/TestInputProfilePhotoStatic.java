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
public class TestInputProfilePhotoStatic {

    @Test
    public void testInputProfilePhotoStaticType() {
        InputProfilePhotoStatic profilePhoto = InputProfilePhotoStatic.builder()
                .photo(new InputFile("test-photo-id"))
                .build();

        assertEquals("static", profilePhoto.getType());
    }

    @Test
    public void testInputProfilePhotoStaticValidation() {
        InputProfilePhotoStatic profilePhoto = InputProfilePhotoStatic.builder()
                .photo(new InputFile("test-photo-id"))
                .build();

        // Should not throw an exception
        try {
            profilePhoto.validate();
        } catch (TelegramApiValidationException e) {
            fail();
        }

        // Test with empty photo ID
        InputProfilePhotoStatic invalidProfilePhoto = InputProfilePhotoStatic.builder()
                .photo(new InputFile(""))
                .build();

        assertThrows(TelegramApiValidationException.class, invalidProfilePhoto::validate);
    }
    
    @Test
    public void testInputProfilePhotoStaticGetters() {
        InputProfilePhotoStatic profilePhoto = InputProfilePhotoStatic.builder()
                .photo(new InputFile("test-photo-id"))
                .build();
        
        assertEquals("test-photo-id", profilePhoto.getPhoto().getAttachName());
    }
    
    @Test
    public void testInputProfilePhotoStaticSetters() {
        InputProfilePhotoStatic profilePhoto = InputProfilePhotoStatic.builder()
                .photo(new InputFile("initial-photo-id"))
                .build();
        
        assertEquals("initial-photo-id", profilePhoto.getPhoto().getAttachName());
        
        // Test setter
        profilePhoto.setPhoto(new InputFile("updated-photo-id"));
        assertEquals("updated-photo-id", profilePhoto.getPhoto().getAttachName());
    }
}
