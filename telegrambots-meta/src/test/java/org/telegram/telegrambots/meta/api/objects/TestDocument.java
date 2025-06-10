package org.telegram.telegrambots.meta.api.objects;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.telegram.telegrambots.meta.api.objects.photo.PhotoSize;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class TestDocument {
    private ObjectMapper mapper;

    @BeforeEach
    void setUp() {
        mapper = new ObjectMapper();
    }

    @Test
    public void testSerialization() {
        String expected = "{" +
                "\"file_id\":\"docFileId\"," +
                "\"file_unique_id\":\"docFileUniqueId\"," +
                "\"thumbnail\":{\"file_id\":\"fileId\",\"file_unique_id\":\"fileUniqueId\",\"width\":200,\"height\":100,\"file_size\":150}" +
                "}";
        Document document = Document
                .builder()
                .fileId("docFileId")
                .fileUniqueId("docFileUniqueId")
                .thumbnail(PhotoSize
                        .builder()
                        .fileId("fileId")
                        .fileUniqueId("fileUniqueId")
                        .height(100)
                        .width(200)
                        .fileSize(150)
                        .build()
                )
                .build();

        try {
            String serialized = mapper.writeValueAsString(document);
            assertEquals(expected, serialized);
        } catch (Exception e) {
            fail(e);
        }
    }

    @Test
    public void testDeserialization() {
        try {
            String text = "{" +
                    "\"file_id\":\"docFileId\"," +
                    "\"file_unique_id\":\"docFileUniqueId\"," +
                    "\"random\":\"random\"," +
                    "\"thumbnail\":{\"file_id\":\"fileId\",\"file_unique_id\":\"fileUniqueId\",\"width\":200,\"height\":100,\"file_size\":150}" +
                    "}";

            Document expected = Document
                    .builder()
                    .fileId("docFileId")
                    .fileUniqueId("docFileUniqueId")
                    .thumbnail(PhotoSize
                            .builder()
                            .fileId("fileId")
                            .fileUniqueId("fileUniqueId")
                            .height(100)
                            .width(200)
                            .fileSize(150)
                            .build()
                    )
                    .build();

            Document document = mapper.readValue(text, Document.class);
            assertEquals(expected, document);
        } catch (Exception e) {
            fail(e);
        }
    }
}
