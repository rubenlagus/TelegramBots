package org.telegram.telegrambots.meta.api.objects.message;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import tools.jackson.databind.json.JsonMapper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNull;

public class TestMaybeInaccessibleMessage {
    private static final String CHAT = "\"chat\": {\n" +
            "        \"last_name\": \"Test Lastname\",\n" +
            "        \"id\": 1111111,\n" +
            "        \"type\": \"private\",\n" +
            "        \"first_name\": \"Test Firstname\",\n" +
            "        \"username\": \"Testusername\"\n" +
            "    }";

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
                "    " + CHAT + "\n" +
                "}";

        MaybeInaccessibleMessage message = mapper.readValue(text, MaybeInaccessibleMessage.class);
        assertInstanceOf(InaccessibleMessage.class, message);

        String text2 = mapper.writeValueAsString(message);

        MaybeInaccessibleMessage message2 = mapper.readValue(text2, MaybeInaccessibleMessage.class);
        assertInstanceOf(InaccessibleMessage.class, message2);

        assertEquals(message, message2);
    }

    @Test
    public void testAccessibleMessage() {
        String text = "{\n" +
                "    \"date\": 1700000000,\n" +
                "    \"message_id\": 123456,\n" +
                "    \"text\": \"Hello\",\n" +
                "    " + CHAT + "\n" +
                "}";

        MaybeInaccessibleMessage message = mapper.readValue(text, MaybeInaccessibleMessage.class);
        assertInstanceOf(Message.class, message);

        String text2 = mapper.writeValueAsString(message);

        MaybeInaccessibleMessage message2 = mapper.readValue(text2, MaybeInaccessibleMessage.class);
        assertInstanceOf(Message.class, message2);

        assertEquals(message, message2);
    }

    @Test
    public void testMessageWithoutDate() {
        String text = "{\n" +
                "    \"message_id\": 123456,\n" +
                "    " + CHAT + "\n" +
                "}";

        MaybeInaccessibleMessage message = mapper.readValue(text, MaybeInaccessibleMessage.class);

        assertInstanceOf(Message.class, message);
        assertNull(message.getDate());
    }

    @Test
    public void testMessageWithNullDate() {
        String text = "{\n" +
                "    \"date\": null,\n" +
                "    \"message_id\": 123456,\n" +
                "    " + CHAT + "\n" +
                "}";

        MaybeInaccessibleMessage message = mapper.readValue(text, MaybeInaccessibleMessage.class);

        assertInstanceOf(Message.class, message);
        assertNull(message.getDate());
    }

    @Test
    public void testEmptyObject() {
        MaybeInaccessibleMessage message = mapper.readValue("{}", MaybeInaccessibleMessage.class);

        assertInstanceOf(Message.class, message);
    }

    @Test
    public void testInaccessibleMessageWithDateAfterOtherFields() {
        String text = "{\n" +
                "    \"message_id\": 123456,\n" +
                "    \"text\": \"Hello\",\n" +
                "    \"date\": 0,\n" +
                "    " + CHAT + "\n" +
                "}";

        MaybeInaccessibleMessage message = mapper.readValue(text, MaybeInaccessibleMessage.class);

        assertInstanceOf(InaccessibleMessage.class, message);
        assertEquals(123456, message.getMessageId().intValue());
    }

    @Test
    public void testInaccessibleMessageWithDateAsLastField() {
        String text = "{\n" +
                "    \"message_id\": 123456,\n" +
                "    " + CHAT + ",\n" +
                "    \"date\": 0\n" +
                "}";

        MaybeInaccessibleMessage message = mapper.readValue(text, MaybeInaccessibleMessage.class);

        assertInstanceOf(InaccessibleMessage.class, message);
        assertEquals(123456, message.getMessageId().intValue());
    }

    @Test
    public void testInaccessibleMessageWithDateAsString() {
        String text = "{\n" +
                "    \"date\": \"0\",\n" +
                "    \"message_id\": 123456,\n" +
                "    " + CHAT + "\n" +
                "}";

        MaybeInaccessibleMessage message = mapper.readValue(text, MaybeInaccessibleMessage.class);

        assertInstanceOf(InaccessibleMessage.class, message);
    }

    @Test
    public void testMessageDeserializedDirectly() {
        String text = "{\n" +
                "    \"date\": 0,\n" +
                "    \"message_id\": 123456,\n" +
                "    " + CHAT + "\n" +
                "}";

        Message message = mapper.readValue(text, Message.class);

        assertEquals(123456, message.getMessageId().intValue());
        assertEquals(0, message.getDate().intValue());
    }

    @Test
    public void testNestedInaccessibleMessage() {
        CallbackQuery callbackQuery = mapper.readValue(callbackQuery(
                "{\n" +
                        "    \"date\": 0,\n" +
                        "    \"message_id\": 123456,\n" +
                        "    " + CHAT + "\n" +
                        "}"), CallbackQuery.class);

        assertInstanceOf(InaccessibleMessage.class, callbackQuery.getMessage());
    }

    @Test
    public void testNestedAccessibleMessage() {
        CallbackQuery callbackQuery = mapper.readValue(callbackQuery(
                "{\n" +
                        "    \"date\": 1700000000,\n" +
                        "    \"message_id\": 123456,\n" +
                        "    " + CHAT + "\n" +
                        "}"), CallbackQuery.class);

        assertInstanceOf(Message.class, callbackQuery.getMessage());
    }

    private String callbackQuery(String message) {
        return "{\n" +
                "    \"id\": \"4382bfdwdsb323b2d9\",\n" +
                "    \"chat_instance\": \"-1234567890123456789\",\n" +
                "    \"message\": " + message + "\n" +
                "}";
    }
}
