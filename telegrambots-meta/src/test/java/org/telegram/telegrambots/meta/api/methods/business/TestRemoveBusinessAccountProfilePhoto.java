package org.telegram.telegrambots.meta.api.methods.business;

import org.junit.jupiter.api.Test;
import org.telegram.telegrambots.meta.exceptions.TelegramApiValidationException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;

/**
 * @author Ruben Bermudez
 * @version 9.0
 */
public class TestRemoveBusinessAccountProfilePhoto {

    @Test
    public void testRemoveBusinessAccountProfilePhotoGetPath() {
        RemoveBusinessAccountProfilePhoto removeBusinessAccountProfilePhoto = RemoveBusinessAccountProfilePhoto.builder()
                .businessConnectionId("test-connection-id")
                .build();

        assertEquals("removeBusinessAccountProfilePhoto", removeBusinessAccountProfilePhoto.getMethod());
    }

    @Test
    public void testRemoveBusinessAccountProfilePhotoValidation() {
        RemoveBusinessAccountProfilePhoto removeBusinessAccountProfilePhoto = RemoveBusinessAccountProfilePhoto.builder()
                .businessConnectionId("test-connection-id")
                .build();

        // Should not throw an exception
        try {
            removeBusinessAccountProfilePhoto.validate();
        } catch (TelegramApiValidationException e) {
            fail();
        }

        // Test with empty business connection ID
        RemoveBusinessAccountProfilePhoto invalidRemoveBusinessAccountProfilePhoto = RemoveBusinessAccountProfilePhoto.builder()
                .businessConnectionId("")
                .build();

        assertThrows(TelegramApiValidationException.class, invalidRemoveBusinessAccountProfilePhoto::validate);
    }

    @Test
    public void testRemoveBusinessAccountProfilePhotoWithIsPublic() {
        RemoveBusinessAccountProfilePhoto removeBusinessAccountProfilePhoto = RemoveBusinessAccountProfilePhoto.builder()
                .businessConnectionId("test-connection-id")
                .isPublic(true)
                .build();

        assertEquals(true, removeBusinessAccountProfilePhoto.getIsPublic());
        assertEquals("test-connection-id", removeBusinessAccountProfilePhoto.getBusinessConnectionId());
    }
}
