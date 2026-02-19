package org.telegram.telegrambots.webhook;

import org.junit.jupiter.api.Test;
import tools.jackson.databind.json.JsonMapper;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class Jackson3MapperTest {
    private static final io.javalin.json.JsonMapper JACKSON_3_MAPPER = new Jackson3Mapper();
    private static final JsonMapper JSON_MAPPER = new JsonMapper();
    private static final String JSON = """
                                       {
                                         "field1": "value1",
                                         "field2": 42
                                       }
                                       """;

    @Test
    void testFromJsonString() {
        var expected = new MyRecord("value1", 42);

        var actual = JACKSON_3_MAPPER.fromJsonString(JSON, MyRecord.class);

        assertEquals(expected, actual);
    }

    @Test
    void testToJsonString() {
        var obj = new MyRecord("value1", 42);
        var expected = JSON_MAPPER.readTree(JSON);

        var actual = JSON_MAPPER.readTree(JACKSON_3_MAPPER.toJsonString(obj, MyRecord.class));

        assertEquals(expected, actual);
    }

    @Test
    void testFromJsonStream() throws IOException {
        var expected = new MyRecord("value1", 42);

        try (var json = new ByteArrayInputStream(JSON.getBytes())) {
            var actual = JACKSON_3_MAPPER.fromJsonStream(json, MyRecord.class);

            assertEquals(expected, actual);
        }
    }

    @Test
    void testStringToJsonStream() throws IOException {
        try (var actual = JACKSON_3_MAPPER.toJsonStream(JSON, String.class)) {
            assertNotNull(actual);
            assertEquals(JSON, new String(actual.readAllBytes()));
        }
    }

    @Test
    void testObjectToJsonStream() throws IOException {
        var obj = new MyRecord("value1", 42);
        var expected = JSON_MAPPER.readTree(JSON);

        try (var actual = JACKSON_3_MAPPER.toJsonStream(obj, MyRecord.class)) {
            assertNotNull(actual);
            assertEquals(expected, JSON_MAPPER.readTree(actual));
        }
    }

    @Test
    void testWriteToOutputStream() throws IOException {
        var obj1 = new MyRecord("value1", 42);
        var obj2 = new MyRecord("value2", 43);
        var expected = JSON_MAPPER.readTree(
                """
                [
                  {
                    "field1": "value1",
                    "field2": 42
                  },
                  {
                    "field1": "value2",
                    "field2": 43
                  }
                ]"""
        );

        try (var outputStream = new ByteArrayOutputStream()) {
            JACKSON_3_MAPPER.writeToOutputStream(Stream.of(obj1, obj2), outputStream);

            var actual = JSON_MAPPER.readTree(outputStream.toByteArray());

            assertEquals(expected, actual);
        }
    }

    private record MyRecord(String field1, int field2) {}
}