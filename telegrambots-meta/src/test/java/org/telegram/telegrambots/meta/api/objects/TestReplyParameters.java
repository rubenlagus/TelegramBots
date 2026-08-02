package org.telegram.telegrambots.meta.api.objects;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.telegram.telegrambots.meta.exceptions.TelegramApiValidationException;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * @author Ruben Bermudez
 * @version 10.2
 */
public class TestReplyParameters {
    private ObjectMapper mapper;

    @BeforeEach
    void setUp() {
        mapper = new ObjectMapper();
    }

    @Test
    public void testMessageIdOnlyIsValid() {
        ReplyParameters replyParameters = ReplyParameters.builder().messageId(12).build();

        assertEquals(12, replyParameters.getMessageId());
        assertNull(replyParameters.getEphemeralMessageId());
        assertDoesNotThrow(replyParameters::validate);
    }

    @Test
    public void testEphemeralMessageIdOnlyIsValid() {
        ReplyParameters replyParameters = ReplyParameters.builder().ephemeralMessageId(77).build();

        assertNull(replyParameters.getMessageId());
        assertEquals(77, replyParameters.getEphemeralMessageId());
        assertDoesNotThrow(replyParameters::validate);
    }

    @Test
    public void testBothMessageIdAndEphemeralMessageIdAreValid() {
        ReplyParameters replyParameters = ReplyParameters.builder()
                .messageId(12)
                .ephemeralMessageId(77)
                .build();

        assertDoesNotThrow(replyParameters::validate);
    }

    @Test
    public void testMissingBothMessageIdsIsInvalid() {
        ReplyParameters replyParameters = ReplyParameters.builder().build();

        TelegramApiValidationException exception =
                assertThrows(TelegramApiValidationException.class, replyParameters::validate);
        assertTrue(exception.getMessage().contains("messageId"), exception.getMessage());
    }

    @Test
    public void testEmptyChatIdIsInvalid() {
        ReplyParameters replyParameters = ReplyParameters.builder()
                .messageId(12)
                .chatId("")
                .build();

        assertThrows(TelegramApiValidationException.class, replyParameters::validate);
    }

    @Test
    public void testEphemeralMessageIdSerialization() throws IOException {
        ReplyParameters replyParameters = ReplyParameters.builder().ephemeralMessageId(77).build();

        String json = mapper.writeValueAsString(replyParameters);

        assertTrue(json.contains("\"ephemeral_message_id\":77"), json);
    }

    @Test
    public void testEphemeralMessageIdDeserialization() throws IOException {
        ReplyParameters replyParameters =
                mapper.readValue("{\"ephemeral_message_id\":77}", ReplyParameters.class);

        assertNull(replyParameters.getMessageId());
        assertEquals(77, replyParameters.getEphemeralMessageId());
    }

    @Test
    public void testEphemeralMessageIdOmittedWhenNotSet() throws IOException {
        ReplyParameters replyParameters = ReplyParameters.builder().messageId(12).build();

        assertFalse(mapper.writeValueAsString(replyParameters).contains("ephemeral_message_id"));
    }
}
