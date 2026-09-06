package org.telegram.telegrambots.meta.api.methods.updatingmessages;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.telegram.telegrambots.meta.api.objects.MessageEntity;
import org.telegram.telegrambots.meta.api.objects.ReplyParameters;
import org.telegram.telegrambots.meta.api.objects.media.InputMediaPhoto;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardRow;
import org.telegram.telegrambots.meta.api.objects.richtext.InputRichMessage;
import org.telegram.telegrambots.meta.api.methods.botapimethods.PartialBotApiMethod;
import org.telegram.telegrambots.meta.exceptions.TelegramApiRequestException;
import org.telegram.telegrambots.meta.exceptions.TelegramApiValidationException;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * @author Ruben Bermudez
 * @version 10.3
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
    public void testEditEphemeralMessageMediaSerialization() throws IOException {
        EditEphemeralMessageMedia method = EditEphemeralMessageMedia.builder()
                .chatId(-100123L)
                .receiverUserId(67890L)
                .ephemeralMessageId(7)
                .media(new InputMediaPhoto("photoFileId"))
                .build();

        assertDoesNotThrow(method::validate);
        assertEquals("editEphemeralMessageMedia", method.getMethod());

        String json = mapper.writeValueAsString(method);
        assertTrue(json.contains("\"chat_id\":\"-100123\""), json);
        assertTrue(json.contains("\"receiver_user_id\":67890"), json);
        assertTrue(json.contains("\"ephemeral_message_id\":7"), json);
        assertTrue(json.contains("\"media\":\"photoFileId\""), json);
    }

    @Test
    public void testEditEphemeralMessageCaptionSerialization() throws IOException {
        EditEphemeralMessageCaption method = EditEphemeralMessageCaption.builder()
                .chatId(-100123L)
                .receiverUserId(67890L)
                .ephemeralMessageId(7)
                .caption("New caption")
                .parseMode("HTML")
                .build();

        assertDoesNotThrow(method::validate);
        assertEquals("editEphemeralMessageCaption", method.getMethod());

        String json = mapper.writeValueAsString(method);
        assertTrue(json.contains("\"chat_id\":\"-100123\""), json);
        assertTrue(json.contains("\"receiver_user_id\":67890"), json);
        assertTrue(json.contains("\"ephemeral_message_id\":7"), json);
        assertTrue(json.contains("\"caption\":\"New caption\""), json);
    }

    @Test
    public void testEditEphemeralMessageCaptionRejectsParseModeWithEntities() {
        EditEphemeralMessageCaption method = EditEphemeralMessageCaption.builder()
                .chatId("12345")
                .receiverUserId(67890L)
                .ephemeralMessageId(7)
                .caption("New caption")
                .parseMode("HTML")
                .captionEntity(MessageEntity.builder().type("bold").offset(0).length(3).build())
                .build();

        assertThrows(TelegramApiValidationException.class, method::validate);
    }

    @Test
    public void testEditEphemeralMessageReplyMarkupSerialization() throws IOException {
        EditEphemeralMessageReplyMarkup method = EditEphemeralMessageReplyMarkup.builder()
                .chatId(-100123L)
                .receiverUserId(67890L)
                .ephemeralMessageId(7)
                .replyMarkup(InlineKeyboardMarkup.builder()
                        .keyboardRow(new InlineKeyboardRow(
                                InlineKeyboardButton.builder().text("Click").callbackData("data").build()))
                        .build())
                .build();

        assertDoesNotThrow(method::validate);
        assertEquals("editEphemeralMessageReplyMarkup", method.getMethod());

        String json = mapper.writeValueAsString(method);
        assertTrue(json.contains("\"chat_id\":\"-100123\""), json);
        assertTrue(json.contains("\"receiver_user_id\":67890"), json);
        assertTrue(json.contains("\"ephemeral_message_id\":7"), json);
        assertTrue(json.contains("\"reply_markup\""), json);
    }

    @Test
    public void testEditEphemeralMessageReplyMarkupRejectsEmptyChatId() {
        EditEphemeralMessageReplyMarkup method = EditEphemeralMessageReplyMarkup.builder()
                .chatId("")
                .receiverUserId(67890L)
                .ephemeralMessageId(7)
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

    @Test
    public void testEditEphemeralMessageTextWithRichMessage() throws IOException {
        EditEphemeralMessageText method = EditEphemeralMessageText.builder()
                .chatId(1L)
                .receiverUserId(2L)
                .ephemeralMessageId(3)
                .richMessage(InputRichMessage.builder().html("<p>hi</p>").build())
                .build();

        assertDoesNotThrow(method::validate);
        String json = mapper.writeValueAsString(method);
        assertTrue(json.contains("\"rich_message\""), json);
        assertFalse(json.contains("\"text\""), json);
        assertFalse(json.contains("\"entities\""), json);
    }

    @Test
    public void testEditEphemeralMessageTextWithTextOnly() {
        EditEphemeralMessageText method = EditEphemeralMessageText.builder()
                .chatId(1L)
                .receiverUserId(2L)
                .ephemeralMessageId(3)
                .text("hi")
                .build();

        assertDoesNotThrow(method::validate);
    }

    @Test
    public void testEditEphemeralMessageTextRejectsBothTextAndRichMessage() {
        EditEphemeralMessageText method = EditEphemeralMessageText.builder()
                .chatId(1L)
                .receiverUserId(2L)
                .ephemeralMessageId(3)
                .text("hi")
                .richMessage(InputRichMessage.builder().html("<p>hi</p>").build())
                .build();

        assertThrows(TelegramApiValidationException.class, method::validate);
    }

    @Test
    public void testEditEphemeralMessageTextRejectsNeither() {
        EditEphemeralMessageText method = EditEphemeralMessageText.builder()
                .chatId(1L)
                .receiverUserId(2L)
                .ephemeralMessageId(3)
                .build();

        assertThrows(TelegramApiValidationException.class, method::validate);
    }

    @Test
    public void testEditEphemeralMessageCaptionShowCaptionAboveMedia() throws IOException {
        EditEphemeralMessageCaption method = EditEphemeralMessageCaption.builder()
                .chatId(1L)
                .receiverUserId(2L)
                .ephemeralMessageId(3)
                .caption("cap")
                .showCaptionAboveMedia(true)
                .build();

        assertTrue(mapper.writeValueAsString(method).contains("\"show_caption_above_media\":true"));
    }

    @Test
    public void testEditEphemeralMessageMediaIsPartialMethod() {
        EditEphemeralMessageMedia method = EditEphemeralMessageMedia.builder()
                .chatId(1L)
                .receiverUserId(2L)
                .ephemeralMessageId(3)
                .media(new InputMediaPhoto("file-id"))
                .build();

        assertInstanceOf(PartialBotApiMethod.class, method);
        assertEquals("editEphemeralMessageMedia", method.getMethod());
    }

    @Test
    public void testEditEphemeralMessageMediaDeserializesBooleanResponse() throws TelegramApiRequestException {
        EditEphemeralMessageMedia method = EditEphemeralMessageMedia.builder()
                .chatId(1L)
                .receiverUserId(2L)
                .ephemeralMessageId(3)
                .media(new InputMediaPhoto("file-id"))
                .build();

        assertTrue(method.deserializeResponse("{\"ok\":true,\"result\":true}"));
    }

    @Test
    public void testEditEphemeralMessageMediaAcceptsNewFileUpload() {
        EditEphemeralMessageMedia method = EditEphemeralMessageMedia.builder()
                .chatId(1L)
                .receiverUserId(2L)
                .ephemeralMessageId(3)
                .media(new InputMediaPhoto(new java.io.File("pom.xml"), "photo.jpg"))
                .build();

        assertDoesNotThrow(method::validate);
    }
}
