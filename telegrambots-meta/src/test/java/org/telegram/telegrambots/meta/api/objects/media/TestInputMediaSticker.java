package org.telegram.telegrambots.meta.api.objects.media;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

/**
 * @author Ruben Bermudez
 * @version 10.0
 */
public class TestInputMediaSticker {

    @Test
    public void testInputMediaStickerType() {
        InputMediaSticker media = new InputMediaSticker("sticker_file_id");

        assertEquals("sticker", media.getType());
    }

    @Test
    public void testInputMediaStickerConstructorWithMedia() {
        InputMediaSticker media = new InputMediaSticker("sticker_file_id");

        assertEquals("sticker_file_id", media.getMedia());
    }

    @Test
    public void testInputMediaStickerValidation() {
        InputMediaSticker media = new InputMediaSticker("sticker_file_id");

        assertDoesNotThrow(media::validate);
    }

    @Test
    public void testInputMediaStickerWithEmoji() {
        InputMediaSticker media = new InputMediaSticker("sticker_file_id", "😀");

        assertEquals("sticker_file_id", media.getMedia());
        assertEquals("😀", media.getEmoji());
    }

    @Test
    public void testInputMediaStickerWithoutEmoji() {
        InputMediaSticker media = new InputMediaSticker("sticker_file_id");

        assertNull(media.getEmoji());
    }

    @Test
    public void testInputMediaStickerBuilderWithEmoji() {
        InputMediaSticker media = InputMediaSticker.builder()
                .media("sticker_file_id")
                .emoji("😀")
                .build();

        assertEquals("sticker_file_id", media.getMedia());
        assertEquals("😀", media.getEmoji());
        assertDoesNotThrow(media::validate);
    }
}
