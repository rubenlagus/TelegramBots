package org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons;

import org.junit.jupiter.api.Test;
import org.telegram.telegrambots.meta.exceptions.TelegramApiValidationException;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * @author Ruben Bermudez
 * @version 9.4
 */
public class TestKeyboardButton {

    @Test
    public void testKeyboardButtonWithIconAndStyle() {
        KeyboardButton button = KeyboardButton.builder()
                .text("Test Button")
                .iconCustomEmojiId("5368324170671202286")
                .style("primary")
                .build();

        assertNotNull(button);
        assertEquals("Test Button", button.getText());
        assertEquals("5368324170671202286", button.getIconCustomEmojiId());
        assertEquals("primary", button.getStyle());
        assertDoesNotThrow(button::validate);
    }

    @Test
    public void testKeyboardButtonWithAllStyleTypes() {
        KeyboardButton dangerButton = KeyboardButton.builder()
                .text("Danger")
                .style("danger")
                .build();
        assertEquals("danger", dangerButton.getStyle());

        KeyboardButton successButton = KeyboardButton.builder()
                .text("Success")
                .style("success")
                .build();
        assertEquals("success", successButton.getStyle());

        KeyboardButton primaryButton = KeyboardButton.builder()
                .text("Primary")
                .style("primary")
                .build();
        assertEquals("primary", primaryButton.getStyle());
    }

    @Test
    public void testKeyboardButtonValidation() throws TelegramApiValidationException {
        KeyboardButton button = KeyboardButton.builder()
                .text("Valid Button")
                .iconCustomEmojiId("12345")
                .style("success")
                .build();

        assertDoesNotThrow(button::validate);
    }
}
