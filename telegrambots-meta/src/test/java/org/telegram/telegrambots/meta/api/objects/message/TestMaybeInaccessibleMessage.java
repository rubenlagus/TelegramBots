package org.telegram.telegrambots.meta.api.objects.message;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;

public class TestMaybeInaccessibleMessage {
    private ObjectMapper mapper;

    @BeforeEach
    void setUp() {
        mapper = new ObjectMapper();
    }

    @Test
    public void testInaccessibleMessage() throws JsonProcessingException {
        String text = "{\n" +
                "    \"date\": 0,\n" +
                "    \"message_id\": 123456,\n" +
                "    \"chat\": {\n" +
                "        \"last_name\": \"Test Lastname\",\n" +
                "        \"id\": 1111111,\n" +
                "        \"type\": \"private\",\n" +
                "        \"first_name\": \"Test Firstname\",\n" +
                "        \"username\": \"Testusername\"\n" +
                "    }\n" +
                "}";

        MaybeInaccessibleMessage message = mapper.readValue(text, MaybeInaccessibleMessage.class);
        assertInstanceOf(InaccessibleMessage.class, message);

        String text2 = mapper.writeValueAsString(message);

        MaybeInaccessibleMessage message2 = mapper.readValue(text2, MaybeInaccessibleMessage.class);
        assertInstanceOf(InaccessibleMessage.class, message);

        assertEquals(message, message2);
    }
}
