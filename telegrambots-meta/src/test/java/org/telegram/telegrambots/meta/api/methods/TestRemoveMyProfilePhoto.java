package org.telegram.telegrambots.meta.api.methods;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author Ruben Bermudez
 * @version 9.4
 */
public class TestRemoveMyProfilePhoto {

    @Test
    public void testRemoveMyProfilePhotoMethod() {
        RemoveMyProfilePhoto removeMyProfilePhoto = RemoveMyProfilePhoto.builder()
                .build();

        assertEquals("removeMyProfilePhoto", removeMyProfilePhoto.getMethod());
    }

    @Test
    public void testRemoveMyProfilePhotoValidation() {
        RemoveMyProfilePhoto removeMyProfilePhoto = RemoveMyProfilePhoto.builder()
                .build();

        // Should not throw exception as no parameters are required
        assertDoesNotThrow(removeMyProfilePhoto::validate);
    }
}
