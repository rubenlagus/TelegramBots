package org.telegram.telegrambots.meta.api.methods.send;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.telegram.telegrambots.meta.api.objects.media.InputMediaPhoto;
import tools.jackson.databind.MapperFeature;
import tools.jackson.databind.json.JsonMapper;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class TestSendMediaGroup {
    private JsonMapper mapper;

    @BeforeEach
    void setUp() {
        mapper = JsonMapper.builder()
                .enable(MapperFeature.SORT_PROPERTIES_ALPHABETICALLY)
                .build();
    }

    @Test
    public void test() {
        try {
            String expectedJson = "{\"chatId\":\"12345\",\"medias\":[{\"caption_entities\":[],\"media\":\"attach://321.png\",\"type\":\"photo\"}," +
                    "{\"caption_entities\":[],\"media\":\"attach://123.png\",\"type\":\"photo\"}]," +
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
