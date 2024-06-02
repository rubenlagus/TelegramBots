package org.telegram.telegrambots.meta.api.methods.send;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.telegram.telegrambots.meta.api.objects.media.InputMediaPhoto;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class TestSendMediaGroup {
    private ObjectMapper mapper;

    @BeforeEach
    void setUp() {
        mapper = new ObjectMapper();
    }

    @Test
    public void test() {
        try {
            String expectedJson = "{\"chatId\":\"12345\",\"messageThreadId\":null,\"medias\":[{\"media\":\"attach://321.png\",\"caption_entities\":[],\"type\":\"photo\"}," +
                    "{\"media\":\"attach://123.png\",\"caption_entities\":[],\"type\":\"photo\"}],\"replyToMessageId\":null,\"disableNotification\":null," +
                    "\"allowSendingWithoutReply\":null,\"protectContent\":null,\"replyParameters\":null,\"businessConnectionId\":null," +
                    "\"method\":\"sendMediaGroup\"}";
            InputStream is = new ByteArrayInputStream("RandomFileContent".getBytes());
            InputStream is2 = new ByteArrayInputStream("RandomFileContent2".getBytes());

            SendMediaGroup sendMediaGroup = SendMediaGroup
                    .builder()
                    .chatId("12345")
                    .media(InputMediaPhoto.builder().media(is, "321.png").build())
                    .media(InputMediaPhoto.builder().media(is2, "123.png").build())
                    .build();

            String json = mapper.writeValueAsString(sendMediaGroup);
            assertEquals(expectedJson, json);
        } catch (Exception e) {
            fail(e);
        }
    }
}
