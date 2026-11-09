package org.telegram.telegrambots.meta.api.objects.games;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import tools.jackson.databind.json.JsonMapper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.fail;

public class TestAnimation {
    private JsonMapper mapper;

    @BeforeEach
    void setUp() {
        mapper = new JsonMapper();
    }

    @Test
    public void testDeserialization() {
        try {
            String text = "{\n" +
                    "                \"file_name\": \"video.mp4\",\n" +
                    "                \"mime_type\": \"video/mp4\",\n" +
                    "                \"duration\": 6,\n" +
                    "                \"width\": 320,\n" +
                    "                \"height\": 180,\n" +
                    "                \"thumbnail\": {\n" +
                    "                    \"file_id\": \"FILEID\",\n" +
                    "                    \"file_unique_id\": \"FILEID\",\n" +
                    "                    \"file_size\": 6209,\n" +
                    "                    \"width\": 320,\n" +
                    "                    \"height\": 180\n" +
                    "                },\n" +
                    "                \"thumb\": {\n" +
                    "                    \"file_id\": \"FILEID\",\n" +
                    "                    \"file_unique_id\": \"FILEID\",\n" +
                    "                    \"file_size\": 6209,\n" +
                    "                    \"width\": 320,\n" +
                    "                    \"height\": 180\n" +
                    "                },\n" +
                    "                \"file_id\": \"FILEID\",\n" +
                    "                \"file_unique_id\": \"FILEID\",\n" +
                    "                \"file_size\": 411406\n" +
                    "            }";

            Animation animation = mapper.readValue(text, Animation.class);
            assertEquals("video.mp4", animation.getFileName());
            assertNotNull(animation.getThumbnail());
            assertEquals("FILEID", animation.getThumbnail().getFileId());
        } catch (Exception e) {
            fail(e);
        }
    }
}
