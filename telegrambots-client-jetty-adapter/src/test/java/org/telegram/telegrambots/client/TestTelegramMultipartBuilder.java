package org.telegram.telegrambots.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.eclipse.jetty.client.MultiPartRequestContent;
import org.eclipse.jetty.http.MultiPart;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.telegram.telegrambots.client.jetty.JettyMultipartBuilder;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.Location;
import org.telegram.telegrambots.meta.api.objects.media.InputMediaDocument;
import org.telegram.telegrambots.meta.api.objects.stickers.InputSticker;

import java.io.File;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.nio.file.Files;
import java.util.ArrayDeque;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class TestTelegramMultipartBuilder {
    private JettyMultipartBuilder multipartBuilder;
    private static final Field partsField;

    static {
        try {
            partsField = MultiPart.AbstractContentSource.class.getDeclaredField("parts");
        } catch (NoSuchFieldException e) {
            throw new RuntimeException(e);
        }
        partsField.setAccessible(true);
    }

    static int getPartsCount(MultiPartRequestContent multiPartRequestContent) {
        try {
            return ((ArrayDeque<?>) partsField.get(multiPartRequestContent)).size();
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    @BeforeEach
    public void setUp() {
        multipartBuilder = new JettyMultipartBuilder(new ObjectMapper());
    }

    @Test
    public void testAddStringPart() {
        MultiPartRequestContent result = multipartBuilder.addPart("TestPart", "TestValue").build();
        assertNotNull(result);
        assertEquals(1, getPartsCount(result));
    }

    @Test
    public void testAddObjectPart() {
        MultiPartRequestContent result = multipartBuilder.addPart("TestPart", 10000).build();
        assertNotNull(result);
        assertEquals(1, getPartsCount(result));
    }

    @Test
    public void testAddJsonPart() {
        try {
            Location location = new Location(1000d, 1000d);
            MultiPartRequestContent result = multipartBuilder.addJsonPart("TestPart", location).build();
            assertNotNull(result);
            assertEquals(1, getPartsCount(result));
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
            MultiPartRequestContent result = multipartBuilder.addInputFile("testField", inputFile, false).build();
            assertNotNull(result);
            assertEquals(1, getPartsCount(result));
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
            MultiPartRequestContent result = multipartBuilder.addInputFile("testField", inputFile, false).build();
            assertNotNull(result);
            assertEquals(1, getPartsCount(result));
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
            MultiPartRequestContent result = multipartBuilder.addInputFile("testField", inputFile, true).build();
            assertNotNull(result);
            assertEquals(2, getPartsCount(result));
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
            MultiPartRequestContent result = multipartBuilder.addMedia(inputMedia).build();
            assertNotNull(result);
            assertEquals(1, getPartsCount(result));
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
            MultiPartRequestContent result = multipartBuilder.addMedia(inputMedia).build();
            assertNotNull(result);
            assertEquals(1, getPartsCount(result));
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
            MultiPartRequestContent result = multipartBuilder.addInputStickers("testField", List.of(inputSticker)).build();
            assertNotNull(result);
            assertEquals(2, getPartsCount(result));
        } catch (Exception e) {
            fail(e);
        }
    }
}
