package org.telegram.telegrambots.meta.api.objects.media;

import org.junit.jupiter.api.Test;
import org.telegram.telegrambots.meta.api.objects.polls.input.InputPollOptionMedia;
import org.telegram.telegrambots.meta.exceptions.TelegramApiValidationException;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * @author Ruben Bermudez
 * @version 10.1
 */
public class TestInputMediaLink {

    @Test
    public void testInputMediaLinkType() {
        InputMediaLink media = InputMediaLink.builder()
                .url("https://example.com")
                .build();

        assertEquals("link", media.getType());
    }

    @Test
    public void testInputMediaLinkTypeConstant() {
        assertEquals("link", InputMediaLink.TYPE);
    }

    @Test
    public void testInputMediaLinkUrl() {
        InputMediaLink media = InputMediaLink.builder()
                .url("https://example.com")
                .build();

        assertEquals("https://example.com", media.getUrl());
    }

    @Test
    public void testInputMediaLinkValidation() {
        InputMediaLink media = InputMediaLink.builder()
                .url("https://example.com")
                .build();

        assertDoesNotThrow(media::validate);
    }

    @Test
    public void testInputMediaLinkValidationEmptyUrl() {
        InputMediaLink media = InputMediaLink.builder()
                .url("")
                .build();

        assertThrows(TelegramApiValidationException.class, media::validate);
    }

    @Test
    public void testInputMediaLinkImplementsInputPollOptionMedia() {
        InputMediaLink media = InputMediaLink.builder()
                .url("https://example.com")
                .build();

        assertInstanceOf(InputPollOptionMedia.class, media);
    }
}
