package org.telegram.telegrambots.meta.api.objects;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

/**
 * @author Ruben Bermudez
 * @version 9.4
 */
public class TestVideo {

    @Test
    public void testVideoWithQualities() {
        VideoQuality quality1080 = VideoQuality.builder()
                .fileId("file_1080")
                .fileUniqueId("unique_1080")
                .width(1920)
                .height(1080)
                .codec("h264")
                .build();

        VideoQuality quality720 = VideoQuality.builder()
                .fileId("file_720")
                .fileUniqueId("unique_720")
                .width(1280)
                .height(720)
                .codec("h264")
                .build();

        List<VideoQuality> qualities = Arrays.asList(quality1080, quality720);

        Video video = Video.builder()
                .fileId("main_file_id")
                .fileUniqueId("main_unique_id")
                .width(1920)
                .height(1080)
                .duration(120)
                .qualities(qualities)
                .build();

        assertNotNull(video);
        assertEquals("main_file_id", video.getFileId());
        assertNotNull(video.getQualities());
        assertEquals(2, video.getQualities().size());
        assertEquals(1920, video.getQualities().get(0).getWidth());
        assertEquals(1080, video.getQualities().get(0).getHeight());
        assertEquals(1280, video.getQualities().get(1).getWidth());
        assertEquals(720, video.getQualities().get(1).getHeight());
    }

    @Test
    public void testVideoWithoutQualities() {
        Video video = Video.builder()
                .fileId("test_file_id")
                .fileUniqueId("test_unique_id")
                .width(1280)
                .height(720)
                .duration(60)
                .build();

        assertNotNull(video);
        assertEquals("test_file_id", video.getFileId());
        assertEquals(1280, video.getWidth());
        assertEquals(720, video.getHeight());
        assertEquals(60, video.getDuration());
        assertNull(video.getQualities());
    }
}
