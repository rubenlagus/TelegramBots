package org.telegram.telegrambots.meta.api.objects.message;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import tools.jackson.databind.json.JsonMapper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;

public class TestMaybeInaccessibleMessage {
    private JsonMapper mapper;

    @BeforeEach
    void setUp() {
        mapper = new JsonMapper();
    }

    @Test
    public void testInaccessibleMessage() {
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
