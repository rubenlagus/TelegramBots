package org.telegram.telegrambots.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.MultipartBody;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.Location;
import org.telegram.telegrambots.meta.api.objects.media.InputMediaDocument;
import org.telegram.telegrambots.meta.api.objects.stickers.InputSticker;

import java.io.File;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.fail;

public class TestTelegramMultipartBuilder {
    private TelegramMultipartBuilder multipartBuilder;

    @BeforeEach
    public void setUp() {
        multipartBuilder = new TelegramMultipartBuilder(new ObjectMapper());
    }

    @Test
    public void testAddStringPart() {
        MultipartBody result = multipartBuilder.addPart("TestPart", "TestValue").build();

        assertNotNull(result);
        assertEquals(1, result.size());
    }

    @Test
    public void testAddObjectPart() {
        MultipartBody result = multipartBuilder.addPart("TestPart", 10000).build();

        assertNotNull(result);
        assertEquals(1, result.size());
    }

    @Test
    public void testAddJsonPart() {
        Location location = new Location();
        location.setLongitude(1000d);
        location.setLatitude(1000d);

        try {
            MultipartBody result = multipartBuilder.addJsonPart("TestPart", location).build();

            assertNotNull(result);
            assertEquals(1, result.size());
        } catch (Exception e) {
            fail(e);
        }
    }

    @Test
    public void testAddInputFileWithoutField() {
        ClassLoader classLoader = getClass().getClassLoader();
        File file = new File(classLoader.getResource("test_file.txt").getFile());
        InputFile inputFile = new InputFile(file, "test_file.txt");

        try {
            MultipartBody result = multipartBuilder.addInputFile("testField", inputFile, false).build();
            assertNotNull(result);
            assertEquals(1, result.size());
        } catch (Exception e) {
            fail(e);
        }
    }

    @Test
    public void testAddInputFileAsStreamWithoutField() {
        ClassLoader classLoader = getClass().getClassLoader();
        File file = new File(classLoader.getResource("test_file.txt").getFile());

        try {
            InputStream fileStream = Files.newInputStream(file.toPath());
            InputFile inputFile = new InputFile(fileStream, "test_file.txt");
            MultipartBody result = multipartBuilder.addInputFile("testField", inputFile, false).build();
            assertNotNull(result);
            assertEquals(1, result.size());
        } catch (Exception e) {
            fail(e);
        }
    }

    @Test
    public void testAddInputFileWithField() {
        ClassLoader classLoader = getClass().getClassLoader();
        File file = new File(classLoader.getResource("test_file.txt").getFile());
        InputFile inputFile = new InputFile(file, "test_file.txt");

        try {
            MultipartBody result = multipartBuilder.addInputFile("testField", inputFile, true).build();
            assertNotNull(result);
            assertEquals(2, result.size());
        } catch (Exception e) {
            fail(e);
        }
    }

    @Test
    public void testAddInputMedia() {
        ClassLoader classLoader = getClass().getClassLoader();
        File file = new File(classLoader.getResource("test_file.txt").getFile());
        InputMediaDocument inputMedia = new InputMediaDocument();
        inputMedia.setMedia(file, "test_file.txt");

        try {
            MultipartBody result = multipartBuilder.addMedia(inputMedia).build();
            assertNotNull(result);
            assertEquals(1, result.size());
        } catch (Exception e) {
            fail(e);
        }
    }

    @Test
    public void testAddInputMediaAsStream() {
        ClassLoader classLoader = getClass().getClassLoader();
        File file = new File(classLoader.getResource("test_file.txt").getFile());
        InputMediaDocument inputMedia = new InputMediaDocument();

        try {
            InputStream fileStream = Files.newInputStream(file.toPath());
            inputMedia.setMedia(fileStream, "test_file.txt");
            MultipartBody result = multipartBuilder.addMedia(inputMedia).build();
            assertNotNull(result);
            assertEquals(1, result.size());
        } catch (Exception e) {
            fail(e);
        }
    }

    @Test
    public void testAddStickerSet() {
        ClassLoader classLoader = getClass().getClassLoader();
        File file = new File(classLoader.getResource("test_file.txt").getFile());
        InputFile inputFile = new InputFile(file, "test_file.txt");
        InputSticker inputSticker = InputSticker.builder()
                .sticker(inputFile)
                .emoji("A")
                .build();
        try {
            MultipartBody result = multipartBuilder.addInputStickers("testField", List.of(inputSticker)).build();
            assertNotNull(result);
            assertEquals(2, result.size());
        } catch (Exception e) {
            fail(e);
        }
    }
}
