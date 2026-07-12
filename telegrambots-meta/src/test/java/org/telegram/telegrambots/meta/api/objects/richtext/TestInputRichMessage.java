package org.telegram.telegrambots.meta.api.objects.richtext;

import org.junit.jupiter.api.Test;
import org.telegram.telegrambots.meta.exceptions.TelegramApiValidationException;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * @author Ruben Bermudez
 * @version 10.1
 */
public class TestInputRichMessage {

    @Test
    public void testInputRichMessageWithHtml() {
        InputRichMessage message = InputRichMessage.builder()
                .html("<p>Hello</p>")
                .build();

        assertEquals("<p>Hello</p>", message.getHtml());
        assertNull(message.getMarkdown());
    }

    @Test
    public void testInputRichMessageWithMarkdown() {
        InputRichMessage message = InputRichMessage.builder()
                .markdown("**Hello**")
                .build();

        assertEquals("**Hello**", message.getMarkdown());
        assertNull(message.getHtml());
    }

    @Test
    public void testInputRichMessageWithIsRtl() {
        InputRichMessage message = InputRichMessage.builder()
                .html("<p>مرحبا</p>")
                .isRtl(true)
                .build();

        assertEquals("<p>مرحبا</p>", message.getHtml());
        assertTrue(message.getIsRtl());
    }

    @Test
    public void testInputRichMessageWithSkipEntityDetection() {
        InputRichMessage message = InputRichMessage.builder()
                .html("<p>https://example.com</p>")
                .skipEntityDetection(true)
                .build();

        assertTrue(message.getSkipEntityDetection());
    }

    @Test
    public void testInputRichMessageOptionalFieldsAreNull() {
        InputRichMessage message = InputRichMessage.builder()
                .html("<p>Hello</p>")
                .build();

        assertNull(message.getIsRtl());
        assertNull(message.getSkipEntityDetection());
    }

    @Test
    public void testInputRichMessageDefaultConstructor() {
        InputRichMessage message = new InputRichMessage();

        assertNull(message.getHtml());
        assertNull(message.getMarkdown());
        assertNull(message.getIsRtl());
        assertNull(message.getSkipEntityDetection());
    }

    @Test
    public void testValidateWithHtmlPasses() {
        InputRichMessage message = InputRichMessage.builder()
                .html("<p>Hello</p>")
                .build();

        assertDoesNotThrow(message::validate);
    }

    @Test
    public void testValidateWithMarkdownPasses() {
        InputRichMessage message = InputRichMessage.builder()
                .markdown("**Hello**")
                .build();

        assertDoesNotThrow(message::validate);
    }

    @Test
    public void testValidateWithNeitherHtmlNorMarkdownThrows() {
        InputRichMessage message = new InputRichMessage();

        TelegramApiValidationException ex = assertThrows(TelegramApiValidationException.class, message::validate);
        assertEquals("Either html or markdown parameter must be provided", ex.getMessage());
    }

    @Test
    public void testValidateWithBothHtmlAndMarkdownThrows() {
        InputRichMessage message = InputRichMessage.builder()
                .html("<p>Hello</p>")
                .markdown("**Hello**")
                .build();

        TelegramApiValidationException ex = assertThrows(TelegramApiValidationException.class, message::validate);
        assertEquals("Only one of html or markdown parameter can be provided", ex.getMessage());
    }

    @Test
    public void testValidateWithEmptyHtmlThrows() {
        InputRichMessage message = InputRichMessage.builder()
                .html("")
                .build();

        TelegramApiValidationException ex = assertThrows(TelegramApiValidationException.class, message::validate);
        assertTrue(ex.getMessage().contains("Either html or markdown"));
    }
}
