package org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * @author Ruben Bermudez
 * @version 9.4
 */
public class TestInlineKeyboardButton {

    @Test
    public void testInlineKeyboardButtonWithIconAndStyle() {
        InlineKeyboardButton button = InlineKeyboardButton.builder()
                .text("Test Button")
                .callbackData("test_data")
                .iconCustomEmojiId("5368324170671202286")
                .style("danger")
                .build();

        assertNotNull(button);
        assertEquals("Test Button", button.getText());
        assertEquals("test_data", button.getCallbackData());
        assertEquals("5368324170671202286", button.getIconCustomEmojiId());
        assertEquals("danger", button.getStyle());
        assertDoesNotThrow(button::validate);
    }

    @Test
    public void testInlineKeyboardButtonWithAllStyles() {
        InlineKeyboardButton dangerButton = InlineKeyboardButton.builder()
                .text("Delete")
                .callbackData("delete")
                .style("danger")
                .build();
        assertEquals("danger", dangerButton.getStyle());

        InlineKeyboardButton successButton = InlineKeyboardButton.builder()
                .text("Confirm")
                .callbackData("confirm")
                .style("success")
                .build();
        assertEquals("success", successButton.getStyle());

        InlineKeyboardButton primaryButton = InlineKeyboardButton.builder()
                .text("Action")
                .callbackData("action")
                .style("primary")
                .build();
        assertEquals("primary", primaryButton.getStyle());
    }

    @Test
    public void testInlineKeyboardButtonWithUrl() {
        InlineKeyboardButton button = InlineKeyboardButton.builder()
                .text("Visit Website")
                .url("https://example.com")
                .iconCustomEmojiId("12345")
                .style("primary")
                .build();

        assertEquals("Visit Website", button.getText());
        assertEquals("https://example.com", button.getUrl());
        assertEquals("12345", button.getIconCustomEmojiId());
        assertEquals("primary", button.getStyle());
    }
}
