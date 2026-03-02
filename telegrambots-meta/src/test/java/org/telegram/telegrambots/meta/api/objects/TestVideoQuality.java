package org.telegram.telegrambots.meta.api.objects;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

/**
 * @author Ruben Bermudez
 * @version 9.4
 */
public class TestVideoQuality {

    @Test
    public void testVideoQualityBuilder() {
        VideoQuality videoQuality = VideoQuality.builder()
                .fileId("BQADBAADDgADqJaXRZBPSOjXUxPOAg")
                .fileUniqueId("AgADDgADqJaXRQ")
                .width(1920)
                .height(1080)
                .codec("h264")
                .fileSize(1024000L)
                .build();

        assertNotNull(videoQuality);
        assertEquals("BQADBAADDgADqJaXRZBPSOjXUxPOAg", videoQuality.getFileId());
        assertEquals("AgADDgADqJaXRQ", videoQuality.getFileUniqueId());
        assertEquals(1920, videoQuality.getWidth());
        assertEquals(1080, videoQuality.getHeight());
        assertEquals("h264", videoQuality.getCodec());
        assertEquals(1024000L, videoQuality.getFileSize());
    }

    @Test
    public void testVideoQualityWithoutFileSize() {
        VideoQuality videoQuality = VideoQuality.builder()
                .fileId("test_file_id")
                .fileUniqueId("test_unique_id")
                .width(1280)
                .height(720)
                .codec("h265")
                .build();

        assertNotNull(videoQuality);
        assertEquals("test_file_id", videoQuality.getFileId());
        assertEquals("test_unique_id", videoQuality.getFileUniqueId());
        assertEquals(1280, videoQuality.getWidth());
        assertEquals(720, videoQuality.getHeight());
        assertEquals("h265", videoQuality.getCodec());
        assertNull(videoQuality.getFileSize());
    }

    @Test
    public void testVideoQualityWithDifferentCodecs() {
        VideoQuality h264 = VideoQuality.builder()
                .fileId("file1")
                .fileUniqueId("unique1")
                .width(1920)
                .height(1080)
                .codec("h264")
                .build();
        assertEquals("h264", h264.getCodec());

        VideoQuality h265 = VideoQuality.builder()
                .fileId("file2")
                .fileUniqueId("unique2")
                .width(1920)
                .height(1080)
                .codec("h265")
                .build();
        assertEquals("h265", h265.getCodec());

        VideoQuality av01 = VideoQuality.builder()
                .fileId("file3")
                .fileUniqueId("unique3")
                .width(1920)
                .height(1080)
                .codec("av01")
                .build();
        assertEquals("av01", av01.getCodec());
    }
}
