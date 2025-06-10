package org.telegram.telegrambots.meta.api.objects.photo;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

/**
 * @author Ruben Bermudez
 * @version 9.0
 */
public class TestPhotoSize {

    @Test
    public void testPhotoSizeConstructor() {
        PhotoSize photoSize = PhotoSize.builder()
                .fileId("test-file-id")
                .fileUniqueId("test-file-unique-id")
                .width(800)
                .height(600)
                .build();

        assertEquals("test-file-id", photoSize.getFileId());
        assertEquals("test-file-unique-id", photoSize.getFileUniqueId());
        assertEquals(800, photoSize.getWidth());
        assertEquals(600, photoSize.getHeight());
        assertNull(photoSize.getFileSize());
    }

    @Test
    public void testPhotoSizeWithAllFields() {
        PhotoSize photoSize = PhotoSize.builder()
                .fileId("test-file-id")
                .fileUniqueId("test-file-unique-id")
                .width(800)
                .height(600)
                .fileSize(1024)
                .build();

        assertEquals("test-file-id", photoSize.getFileId());
        assertEquals("test-file-unique-id", photoSize.getFileUniqueId());
        assertEquals(800, photoSize.getWidth());
        assertEquals(600, photoSize.getHeight());
        assertEquals(1024, photoSize.getFileSize());
    }

    @Test
    public void testPhotoSizeSettersAndGetters() {
        PhotoSize photoSize = PhotoSize.builder()
                .fileId("initial-file-id")
                .fileUniqueId("initial-file-unique-id")
                .width(800)
                .height(600)
                .build();

        assertEquals("initial-file-id", photoSize.getFileId());
        assertEquals("initial-file-unique-id", photoSize.getFileUniqueId());
        assertEquals(800, photoSize.getWidth());
        assertEquals(600, photoSize.getHeight());
        assertNull(photoSize.getFileSize());

        // Test setters
        photoSize.setFileId("updated-file-id");
        photoSize.setFileUniqueId("updated-file-unique-id");
        photoSize.setWidth(1024);
        photoSize.setHeight(768);
        photoSize.setFileSize(2048);

        assertEquals("updated-file-id", photoSize.getFileId());
        assertEquals("updated-file-unique-id", photoSize.getFileUniqueId());
        assertEquals(1024, photoSize.getWidth());
        assertEquals(768, photoSize.getHeight());
        assertEquals(2048, photoSize.getFileSize());
    }
}
