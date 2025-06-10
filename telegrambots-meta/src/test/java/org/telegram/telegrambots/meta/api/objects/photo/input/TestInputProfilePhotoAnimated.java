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
public class TestInputProfilePhotoAnimated {

    @Test
    public void testInputProfilePhotoAnimatedType() {
        InputProfilePhotoAnimated profilePhoto = InputProfilePhotoAnimated.builder()
                .animation(new InputFile("test-photo-id"))
                .build();

        assertEquals("animated", profilePhoto.getType());
    }

    @Test
    public void testInputProfilePhotoAnimatedValidation() {
        InputProfilePhotoAnimated profilePhoto = InputProfilePhotoAnimated.builder()
                .animation(new InputFile("test-photo-id"))
                .build();

        // Should not throw an exception
        try {
            profilePhoto.validate();
        } catch (TelegramApiValidationException e) {
            fail();
        }

        // Test with empty file ID
        InputProfilePhotoAnimated invalidProfilePhoto = InputProfilePhotoAnimated.builder()
                .animation(new InputFile(""))
                .build();

        assertThrows(TelegramApiValidationException.class, invalidProfilePhoto::validate);
    }

    @Test
    public void testInputProfilePhotoAnimatedGetters() {
        InputProfilePhotoAnimated profilePhoto = InputProfilePhotoAnimated.builder()
                .animation(new InputFile("test-file-id"))
                .build();

        assertEquals("test-file-id", profilePhoto.getAnimation().getAttachName());
    }
}
