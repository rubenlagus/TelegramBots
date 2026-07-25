package org.telegram.telegrambots.meta.api.methods.updatingmessages;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.telegram.telegrambots.meta.api.objects.ReplyParameters;
import org.telegram.telegrambots.meta.api.objects.media.InputMediaPhoto;
import org.telegram.telegrambots.meta.exceptions.TelegramApiValidationException;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * @author Ruben Bermudez
 * @version 10.2
 */
public class TestEphemeralMessageMethods {
    private ObjectMapper mapper;

    @BeforeEach
    void setUp() {
        mapper = new ObjectMapper();
    }

    @Test
    public void testMethodPaths() {
        assertEquals("editEphemeralMessageText", EditEphemeralMessageText.PATH);
        assertEquals("editEphemeralMessageMedia", EditEphemeralMessageMedia.PATH);
        assertEquals("editEphemeralMessageCaption", EditEphemeralMessageCaption.PATH);
        assertEquals("editEphemeralMessageReplyMarkup", EditEphemeralMessageReplyMarkup.PATH);
        assertEquals("deleteEphemeralMessage", DeleteEphemeralMessage.PATH);
    }

    @Test
    public void testEditEphemeralMessageTextSerialization() throws IOException {
        EditEphemeralMessageText method = EditEphemeralMessageText.builder()
                .chatId(12345L)
                .receiverUserId(67890L)
                .ephemeralMessageId(7)
                .text("Updated")
                .build();

        assertDoesNotThrow(method::validate);
        assertEquals("editEphemeralMessageText", method.getMethod());

        String json = mapper.writeValueAsString(method);
        assertTrue(json.contains("\"chat_id\":\"12345\""), json);
        assertTrue(json.contains("\"receiver_user_id\":67890"), json);
        assertTrue(json.contains("\"ephemeral_message_id\":7"), json);
        assertTrue(json.contains("\"text\":\"Updated\""), json);
    }

    @Test
    public void testEditEphemeralMessageTextRejectsEmptyText() {
        EditEphemeralMessageText method = EditEphemeralMessageText.builder()
                .chatId("12345")
                .receiverUserId(67890L)
                .ephemeralMessageId(7)
                .text("")
                .build();

        assertThrows(TelegramApiValidationException.class, method::validate);
    }

    @Test
    public void testEditEphemeralMessageMediaValidatesMedia() {
        EditEphemeralMessageMedia method = EditEphemeralMessageMedia.builder()
                .chatId("12345")
                .receiverUserId(67890L)
                .ephemeralMessageId(7)
                .media(new InputMediaPhoto(""))
                .build();

        assertThrows(TelegramApiValidationException.class, method::validate);
    }

    @Test
    public void testDeleteEphemeralMessageSerialization() throws IOException {
        DeleteEphemeralMessage method = DeleteEphemeralMessage.builder()
                .chatId(-100123L)
                .receiverUserId(67890L)
                .ephemeralMessageId(3)
                .build();

        assertDoesNotThrow(method::validate);

        String json = mapper.writeValueAsString(method);
        assertTrue(json.contains("\"chat_id\":\"-100123\""), json);
        assertTrue(json.contains("\"ephemeral_message_id\":3"), json);
    }

    @Test
    public void testDeleteEphemeralMessageRejectsEmptyChatId() {
        DeleteEphemeralMessage method = DeleteEphemeralMessage.builder()
                .chatId("")
                .receiverUserId(67890L)
                .ephemeralMessageId(3)
                .build();

        assertThrows(TelegramApiValidationException.class, method::validate);
    }

    @Test
    public void testReplyParametersAcceptsEphemeralMessageIdAlone() throws IOException {
        ReplyParameters parameters = ReplyParameters.builder()
                .ephemeralMessageId(9)
                .build();

        assertDoesNotThrow(parameters::validate);

        String json = mapper.writeValueAsString(parameters);
        assertTrue(json.contains("\"ephemeral_message_id\":9"), json);
    }

    @Test
    public void testReplyParametersStillAcceptsMessageIdAlone() {
        assertDoesNotThrow(new ReplyParameters(5)::validate);
    }

    @Test
    public void testReplyParametersRejectsNeitherId() {
        ReplyParameters parameters = ReplyParameters.builder().build();

        TelegramApiValidationException ex =
                assertThrows(TelegramApiValidationException.class, parameters::validate);
        assertTrue(ex.getMessage().contains("Either messageId or ephemeralMessageId"), ex.getMessage());
    }
}
