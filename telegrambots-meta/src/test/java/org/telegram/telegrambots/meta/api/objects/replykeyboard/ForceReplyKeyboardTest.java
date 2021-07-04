package org.telegram.telegrambots.meta.api.objects.replykeyboard;

import org.junit.jupiter.api.Test;
import org.telegram.telegrambots.meta.exceptions.TelegramApiValidationException;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * @author Ruben Bermudez
 * @version 1.0
 */
public class ForceReplyKeyboardTest {
    @Test
    public void testForceReplyDefaultFields() {
        ForceReplyKeyboard forceReplyKeyboard = ForceReplyKeyboard
                .builder()
                .build();
        assertDoesNotThrow(forceReplyKeyboard::validate);
        forceReplyKeyboard = new ForceReplyKeyboard();
        assertDoesNotThrow(forceReplyKeyboard::validate);
    }

    @Test
    public void testForceReplyAllFieldSet() {
        ForceReplyKeyboard forceReplyKeyboard = ForceReplyKeyboard
                .builder()
                .forceReply(true)
                .selective(true)
                .inputFieldPlaceholder("Placeholder")
                .build();
        assertDoesNotThrow(forceReplyKeyboard::validate);
    }

    @Test
    public void testForceReplyEmptyInputPlaceholder() {
        ForceReplyKeyboard forceReplyKeyboard = ForceReplyKeyboard
                .builder()
                .forceReply(true)
                .selective(true)
                .inputFieldPlaceholder("")
                .build();
        Throwable thrown = assertThrows(TelegramApiValidationException.class, forceReplyKeyboard::validate);
        assertEquals("InputFieldPlaceholder must be between 1 and 64 characters", thrown.getMessage());
    }


    @Test
    public void testForceReplyOver64InputPlaceholder() {
        ForceReplyKeyboard forceReplyKeyboard = ForceReplyKeyboard
                .builder()
                .forceReply(true)
                .selective(true)
                .inputFieldPlaceholder("D%YNdVJe/6xUGR_)W]rVLx_,g/9:ZX7+XiiM-*nPm{(g5gEtv![te6H[;tWAT&Z4K")
                .build();
        Throwable thrown = assertThrows(TelegramApiValidationException.class, forceReplyKeyboard::validate);
        assertEquals("InputFieldPlaceholder must be between 1 and 64 characters", thrown.getMessage());
    }
}
