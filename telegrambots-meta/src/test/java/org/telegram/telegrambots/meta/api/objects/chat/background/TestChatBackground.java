package org.telegram.telegrambots.meta.api.objects.chat.background;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.telegram.telegrambots.meta.api.objects.Document;
import org.telegram.telegrambots.meta.api.objects.chat.background.type.BackgroundTypeChatTheme;
import org.telegram.telegrambots.meta.api.objects.chat.background.type.BackgroundTypeFill;
import org.telegram.telegrambots.meta.api.objects.chat.background.type.BackgroundTypePattern;
import org.telegram.telegrambots.meta.api.objects.chat.background.type.BackgroundTypeWallpaper;
import org.telegram.telegrambots.meta.api.objects.chat.background.type.fill.BackgroundFillFreeformGradient;
import org.telegram.telegrambots.meta.api.objects.chat.background.type.fill.BackgroundFillGradient;
import org.telegram.telegrambots.meta.api.objects.chat.background.type.fill.BackgroundFillSolid;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestChatBackground {
    private ObjectMapper mapper;

    @BeforeEach
    void setUp() {
        mapper = new ObjectMapper();
    }

    @Test
    public void testBackgroundTypeFillSolid() throws IOException {
        ChatBackground chatBackground = ChatBackground
                .builder()
                .type(BackgroundTypeFill
                        .builder()
                        .darkThemeDimming(100)
                        .fill(BackgroundFillSolid
                                .builder()
                                .color(123)
                                .build())
                        .build())
                .build();

        String resultChatBackground = mapper.writeValueAsString(chatBackground);
        assertEquals(chatBackground, mapper.readValue(resultChatBackground, ChatBackground.class));
    }

    @Test
    public void testBackgroundTypeFillGradient() throws IOException {
        ChatBackground chatBackground = ChatBackground
                .builder()
                .type(BackgroundTypeFill
                        .builder()
                        .darkThemeDimming(100)
                        .fill(BackgroundFillGradient
                                .builder()
                                .topColor(100)
                                .bottomColor(50)
                                .rotationAngle(45)
                                .build())
                        .build())
                .build();

        String resultChatBackground = mapper.writeValueAsString(chatBackground);
        assertEquals(chatBackground, mapper.readValue(resultChatBackground, ChatBackground.class));
    }

    @Test
    public void testBackgroundTypeFillFreeformGradient() throws IOException {
        ChatBackground chatBackground = ChatBackground
                .builder()
                .type(BackgroundTypeFill
                        .builder()
                        .darkThemeDimming(100)
                        .fill(BackgroundFillFreeformGradient
                                .builder()
                                .colors(List.of(100, 50, 34))
                                .build())
                        .build())
                .build();

        String resultChatBackground = mapper.writeValueAsString(chatBackground);
        assertEquals(chatBackground, mapper.readValue(resultChatBackground, ChatBackground.class));
    }

    @Test
    public void testBackgroundTypeChatTheme() throws IOException {
        ChatBackground chatBackground = ChatBackground
                .builder()
                .type(BackgroundTypeChatTheme
                        .builder()
                        .themeName("TestTheme")
                        .build())
                .build();

        String resultChatBackground = mapper.writeValueAsString(chatBackground);
        assertEquals(chatBackground, mapper.readValue(resultChatBackground, ChatBackground.class));
    }

    @Test
    public void testBackgroundTypeWallpaper() throws IOException {
        ChatBackground chatBackground = ChatBackground
                .builder()
                .type(BackgroundTypeWallpaper
                        .builder()
                        .document(Document
                                .builder()
                                .fileId("TestFile")
                                .fileId("TestFileId")
                                .build())
                        .darkThemeDimming(80)
                        .isBlurred(true)
                        .isMoving(true)
                        .build())
                .build();

        String resultChatBackground = mapper.writeValueAsString(chatBackground);
        assertEquals(chatBackground, mapper.readValue(resultChatBackground, ChatBackground.class));
    }

    @Test
    public void testBackgroundTypePathen() throws IOException {
        ChatBackground chatBackground = ChatBackground
                .builder()
                .type(BackgroundTypePattern
                        .builder()
                        .document(Document
                                .builder()
                                .fileId("TestFile")
                                .fileId("TestFileId")
                                .build())
                        .fill(BackgroundFillFreeformGradient
                                .builder()
                                .colors(List.of(100, 50, 34))
                                .build())
                        .intensity(50)
                        .isInverted(true)
                        .isMoving(true)
                        .build())
                .build();

        String resultChatBackground = mapper.writeValueAsString(chatBackground);
        assertEquals(chatBackground, mapper.readValue(resultChatBackground, ChatBackground.class));
    }
}
