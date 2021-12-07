package org.telegram.telegrambots.meta.api.objects.replykeyboard;

import org.junit.jupiter.api.Test;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiValidationException;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * @author Ruben Bermudez
 * @version 1.0
 */
public class ReplyKeyboardMarkupTest {
    @Test
    public void testReplyKeyboardMarkupAllDefaults() {
        ReplyKeyboardMarkup replyKeyboardMarkup = ReplyKeyboardMarkup
                .builder()
                .build();
        assertDoesNotThrow(replyKeyboardMarkup::validate);
    }

    @Test
    public void testReplyKeyboardMarkupAllFieldSet() {
        ReplyKeyboardMarkup replyKeyboardMarkup = ReplyKeyboardMarkup
                .builder()
                .keyboard(Collections.singletonList(new KeyboardRow(Collections.singletonList(KeyboardButton
                        .builder()
                        .text("ButtonText")
                        .build()))))
                .resizeKeyboard(true)
                .oneTimeKeyboard(true)
                .selective(true)
                .inputFieldPlaceholder("Placeholder")
                .build();
        assertDoesNotThrow(replyKeyboardMarkup::validate);
    }

    @Test
    public void testReplyKeyboardMarkupEmptyInputPlaceholder() {
        ReplyKeyboardMarkup replyKeyboardMarkup = ReplyKeyboardMarkup
                .builder()
                .keyboard(Collections.singletonList(new KeyboardRow(Collections.singletonList(KeyboardButton
                        .builder()
                        .text("ButtonText")
                        .build()))))
                .resizeKeyboard(true)
                .oneTimeKeyboard(true)
                .selective(true)
                .inputFieldPlaceholder("")
                .build();
        Throwable thrown = assertThrows(TelegramApiValidationException.class, replyKeyboardMarkup::validate);
        assertEquals("InputFieldPlaceholder must be between 1 and 64 characters", thrown.getMessage());
    }

    @Test
    public void testReplyKeyboardMarkupOver64InputPlaceholder() {
        ReplyKeyboardMarkup replyKeyboardMarkup = ReplyKeyboardMarkup
                .builder()
                .keyboard(Collections.singletonList(new KeyboardRow(Collections.singletonList(KeyboardButton
                        .builder()
                        .text("ButtonText")
                        .build()))))
                .resizeKeyboard(true)
                .oneTimeKeyboard(true)
                .selective(true)
                .inputFieldPlaceholder("D%YNdVJe/6xUGR_)W]rVLx_,g/9:ZX7+XiiM-*nPm{(g5gEtv![te6H[;tWAT&Z4K")
                .build();
        Throwable thrown = assertThrows(TelegramApiValidationException.class, replyKeyboardMarkup::validate);
        assertEquals("InputFieldPlaceholder must be between 1 and 64 characters", thrown.getMessage());
    }
}
