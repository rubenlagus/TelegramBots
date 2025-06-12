package org.telegram.telegrambots.meta.api.objects.media.paid;

import org.junit.jupiter.api.Test;
import org.telegram.telegrambots.meta.api.objects.InputFile;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

/**
 * @author Ruben Bermudez
 * @version 7.5
 */
public class TestInputPaidMedia {

    @Test
    public void testInputPaidMediaPhotoType() {
        InputPaidMediaPhoto inputPaidMediaPhoto = new InputPaidMediaPhoto("test-media-id");
        assertEquals("photo", inputPaidMediaPhoto.getType());
    }

    @Test
    public void testInputPaidMediaPhotoConstructor() {
        InputPaidMediaPhoto inputPaidMediaPhoto = new InputPaidMediaPhoto("test-media-id");
        assertEquals("test-media-id", inputPaidMediaPhoto.getMedia());
    }

    @Test
    public void testInputPaidMediaVideoType() {
        InputPaidMediaVideo inputPaidMediaVideo = new InputPaidMediaVideo("test-media-id");
        assertEquals("photo", inputPaidMediaVideo.getType());
    }

    @Test
    public void testInputPaidMediaVideoConstructor() {
        InputPaidMediaVideo inputPaidMediaVideo = new InputPaidMediaVideo("test-media-id");
        assertEquals("test-media-id", inputPaidMediaVideo.getMedia());
    }

    @Test
    public void testInputPaidMediaVideoWithOptionalParams() {
        InputFile thumbnail = new InputFile("thumbnail-id");
        InputFile cover = new InputFile("cover-id");
        
        InputPaidMediaVideo inputPaidMediaVideo = InputPaidMediaVideo.builder()
                .media("test-media-id")
                .width(640)
                .height(480)
                .duration(30)
                .supportsStreaming(true)
                .thumbnail(thumbnail)
                .startTimestamp(5)
                .cover(cover)
                .build();

        assertEquals("test-media-id", inputPaidMediaVideo.getMedia());
        assertEquals(Integer.valueOf(640), inputPaidMediaVideo.getWidth());
        assertEquals(Integer.valueOf(480), inputPaidMediaVideo.getHeight());
        assertEquals(Integer.valueOf(30), inputPaidMediaVideo.getDuration());
        assertEquals(Boolean.TRUE, inputPaidMediaVideo.getSupportsStreaming());
        assertEquals(thumbnail, inputPaidMediaVideo.getThumbnail());
        assertEquals(Integer.valueOf(5), inputPaidMediaVideo.getStartTimestamp());
        assertEquals(cover, inputPaidMediaVideo.getCover());
    }

    @Test
    public void testInputPaidMediaVideoWithoutOptionalParams() {
        InputPaidMediaVideo inputPaidMediaVideo = new InputPaidMediaVideo("test-media-id");

        assertEquals("test-media-id", inputPaidMediaVideo.getMedia());
        assertNull(inputPaidMediaVideo.getWidth());
        assertNull(inputPaidMediaVideo.getHeight());
        assertNull(inputPaidMediaVideo.getDuration());
        assertNull(inputPaidMediaVideo.getSupportsStreaming());
        assertNull(inputPaidMediaVideo.getThumbnail());
        assertNull(inputPaidMediaVideo.getStartTimestamp());
        assertNull(inputPaidMediaVideo.getCover());
    }
}
