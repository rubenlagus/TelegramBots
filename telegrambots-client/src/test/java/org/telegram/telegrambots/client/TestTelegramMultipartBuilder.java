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
        try {
            Location location = new Location(1000d, 1000d);
            MultipartBody result = multipartBuilder.addJsonPart("TestPart", location).build();
            assertNotNull(result);
            assertEquals(1, result.size());
        } catch (Exception e) {
            fail(e);
        }
    }

    @Test
    public void testAddInputFileWithoutField() {
        try {
            ClassLoader classLoader = getClass().getClassLoader();
            File file = new File(classLoader.getResource("test_file.txt").getFile());
            InputFile inputFile = new InputFile(file, "test_file.txt");
            MultipartBody result = multipartBuilder.addInputFile("testField", inputFile, false).build();
            assertNotNull(result);
            assertEquals(1, result.size());
        } catch (Exception e) {
            fail(e);
        }
    }

    @Test
    public void testAddInputFileAsStreamWithoutField() {
        try {
            ClassLoader classLoader = getClass().getClassLoader();
            File file = new File(classLoader.getResource("test_file.txt").getFile());
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
        try {
            ClassLoader classLoader = getClass().getClassLoader();
            File file = new File(classLoader.getResource("test_file.txt").getFile());
            InputFile inputFile = new InputFile(file, "test_file.txt");
            MultipartBody result = multipartBuilder.addInputFile("testField", inputFile, true).build();
            assertNotNull(result);
            assertEquals(2, result.size());
        } catch (Exception e) {
            fail(e);
        }
    }

    @Test
    public void testAddInputMedia() {
        try {
            ClassLoader classLoader = getClass().getClassLoader();
            File file = new File(classLoader.getResource("test_file.txt").getFile());
            InputMediaDocument inputMedia = new InputMediaDocument(file, "test_file.txt");
            MultipartBody result = multipartBuilder.addMedia(inputMedia).build();
            assertNotNull(result);
            assertEquals(1, result.size());
        } catch (Exception e) {
            fail(e);
        }
    }

    @Test
    public void testAddInputMediaAsStream() {
        try {
            ClassLoader classLoader = getClass().getClassLoader();
            File file = new File(classLoader.getResource("test_file.txt").getFile());
            InputStream fileStream = Files.newInputStream(file.toPath());
            InputMediaDocument inputMedia = new InputMediaDocument(fileStream, "test_file.txt");
            MultipartBody result = multipartBuilder.addMedia(inputMedia).build();
            assertNotNull(result);
            assertEquals(1, result.size());
        } catch (Exception e) {
            fail(e);
        }
    }

    @Test
    public void testAddStickerSet() {
        try {
            ClassLoader classLoader = getClass().getClassLoader();
            File file = new File(classLoader.getResource("test_file.txt").getFile());
            InputFile inputFile = new InputFile(file, "test_file.txt");
            InputSticker inputSticker = InputSticker.builder()
                    .sticker(inputFile)
                    .emoji("A")
                    .format("static")
                    .build();
            MultipartBody result = multipartBuilder.addInputStickers("testField", List.of(inputSticker)).build();
            assertNotNull(result);
            assertEquals(2, result.size());
        } catch (Exception e) {
            fail(e);
        }
    }
}
