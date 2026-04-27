package org.telegram.telegrambots.meta.api.methods.webapp;

import org.junit.jupiter.api.Test;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButtonRequestManagedBot;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author Ruben Bermudez
 * @version 9.6
 */
public class TestSavePreparedKeyboardButton {

    @Test
    public void testSavePreparedKeyboardButtonGetPath() {
        KeyboardButton button = KeyboardButton.builder()
                .text("Create Bot")
                .requestManagedBot(KeyboardButtonRequestManagedBot.builder()
                        .requestId(1)
                        .build())
                .build();

        SavePreparedKeyboardButton savePreparedKeyboardButton = SavePreparedKeyboardButton.builder()
                .userId(123456789L)
                .button(button)
                .build();

        assertEquals("savePreparedKeyboardButton", savePreparedKeyboardButton.getMethod());
    }

    @Test
    public void testSavePreparedKeyboardButtonValidation() {
        KeyboardButton button = KeyboardButton.builder()
                .text("Create Bot")
                .requestManagedBot(KeyboardButtonRequestManagedBot.builder()
                        .requestId(1)
                        .build())
                .build();

        SavePreparedKeyboardButton savePreparedKeyboardButton = SavePreparedKeyboardButton.builder()
                .userId(123456789L)
                .button(button)
                .build();

        assertDoesNotThrow(savePreparedKeyboardButton::validate);
    }

    @Test
    public void testSavePreparedKeyboardButtonFields() {
        KeyboardButton button = KeyboardButton.builder()
                .text("Create Bot")
                .requestManagedBot(KeyboardButtonRequestManagedBot.builder()
                        .requestId(1)
                        .suggestedName("My Bot")
                        .suggestedUsername("mybot")
                        .build())
                .build();

        SavePreparedKeyboardButton savePreparedKeyboardButton = SavePreparedKeyboardButton.builder()
                .userId(123456789L)
                .button(button)
                .build();

        assertEquals(123456789L, savePreparedKeyboardButton.getUserId());
        assertEquals(button, savePreparedKeyboardButton.getButton());
    }
}
