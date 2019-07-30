package org.telegram.telegrambots.meta.test;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.telegram.telegrambots.meta.api.objects.ApiResponse;
import org.telegram.telegrambots.meta.api.objects.Audio;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.Document;
import org.telegram.telegrambots.meta.api.objects.EntityType;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.MessageEntity;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.api.objects.Voice;
import org.telegram.telegrambots.meta.api.objects.inlinequery.ChosenInlineQuery;
import org.telegram.telegrambots.meta.api.objects.inlinequery.InlineQuery;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * @author Ruben Bermudez
 * @version 1.0
 */
class TestDeserialization {
    private ObjectMapper mapper;

    @BeforeEach
    void setUp() {
        mapper = new ObjectMapper();
    }

    @Test
    void TestUpdateDeserialization() throws Exception {
        Update update = mapper.readValue(TelegramBotsHelper.GetUpdate(), Update.class);
        assertUpdate(update);
    }

    @Test
    void TestUpdateDeserializationWithInlineKeyboard() throws Exception {
        Update update = mapper.readValue(TelegramBotsHelper.GetUpdateWithMessageInCallbackQuery(), Update.class);
        assertNotNull(update);
        assertNotNull(update.getCallbackQuery());
        assertNotNull(update.getCallbackQuery().getMessage());
        assertNotNull(update.getCallbackQuery().getMessage().getReplyMarkup());
    }

    @Test
    void TestResponseWithoutErrorDeserialization() throws IOException {
        ApiResponse<ArrayList<Update>> result = mapper.readValue(TelegramBotsHelper.GetResponseWithoutError(), new TypeReference<ApiResponse<ArrayList<Update>>>(){});
        assertNotNull(result);
        assertTrue(result.getOk());
        assertEquals(1, result.getResult().size());
        assertUpdate(result.getResult().get(0));
    }

    @Test
    void TestResponseWithErrorDeserialization() throws IOException {
        ApiResponse<ArrayList<Update>> result = mapper.readValue(TelegramBotsHelper.GetResponseWithError(), new TypeReference<ApiResponse<ArrayList<Update>>>(){});
        assertNotNull(result);
        assertFalse(result.getOk());
        assertEquals(Integer.valueOf(400), result.getErrorCode());
        assertEquals("Error descriptions", result.getErrorDescription());
        assertNotNull(result.getParameters());
        assertEquals(Long.valueOf(12345), result.getParameters().getMigrateToChatId());
        assertEquals(Integer.valueOf(12), result.getParameters().getRetryAfter());
    }

    private void assertUpdate(Update update) {
        assertNotNull(update);
        assertEquals((Integer) 10000, update.getUpdateId());
        assertEditedMessage(update.getEditedMessage());
        assertCallbackQuery(update.getCallbackQuery());
        assertInlineQuery(update.getInlineQuery());
        assertChosenInlineQuery(update.getChosenInlineQuery());
        assertMessage(update.getMessage());
    }

    private void assertMessage(Message message) {
        assertNotNull(message);
        assertEquals(Integer.valueOf(1441645532), message.getDate());
        assertEquals(Integer.valueOf(1365), message.getMessageId());
        assertEquals(Integer.valueOf(1441645550), message.getForwardDate());
        assertEquals("Bold and italics", message.getText());
        assertPrivateChat(message.getChat());
        assertFromUser(message.getFrom());
        assertForwardFrom(message.getForwardFrom());
        assertReplyToMessage(message.getReplyToMessage());
        assertEntities(message.getEntities());
        assertAudio(message.getAudio());
        assertVoice(message.getVoice());
        assertDocument(message.getDocument());
    }

    private void assertDocument(Document document) {
        assertNotNull(document);
        assertEquals("AwADBAADbXXXXXXXXXXXGBdhD2l6_XX", document.getFileId());
        assertEquals("Testfile.pdf", document.getFileName());
        assertEquals("application/pdf", document.getMimeType());
        assertEquals(Integer.valueOf(536392), document.getFileSize());
    }

    private void assertVoice(Voice voice) {
        assertNotNull(voice);
        assertEquals("AwADBAADbXXXXXXXXXXXGBdhD2l6_XX", voice.getFileId());
        assertEquals(Integer.valueOf(5), voice.getDuration());
        assertEquals("audio/ogg", voice.getMimeType());
        assertEquals(Integer.valueOf(23000), voice.getFileSize());
    }

    private void assertAudio(Audio audio) {
        assertNotNull(audio);
        assertEquals("AwADBAADbXXXXXXXXXXXGBdhD2l6_XX", audio.getFileId());
        assertEquals(Integer.valueOf(243), audio.getDuration());
        assertEquals("audio/mpeg", audio.getMimeType());
        assertEquals(Integer.valueOf(3897500), audio.getFileSize());
        assertEquals("Testmusicfile", audio.getTitle());
    }

    private void assertEntities(List<MessageEntity> entities) {
        assertNotNull(entities);
        assertEquals(2, entities.size());
        assertEquals(EntityType.ITALIC, entities.get(0).getType());
        assertEquals(Integer.valueOf(9), entities.get(0).getOffset());
        assertEquals(Integer.valueOf(7), entities.get(0).getLength());
        assertEquals("italics", entities.get(0).getText());
        assertEquals(EntityType.BOLD, entities.get(1).getType());
        assertEquals(Integer.valueOf(0), entities.get(1).getOffset());
        assertEquals(Integer.valueOf(4), entities.get(1).getLength());
        assertEquals("Bold", entities.get(1).getText());
    }

    private void assertReplyToMessage(Message replyToMessage) {
        assertNotNull(replyToMessage);
        assertEquals(Integer.valueOf(1441645000), replyToMessage.getDate());
        assertEquals(Integer.valueOf(1334), replyToMessage.getMessageId());
        assertEquals("Original", replyToMessage.getText());
        assertNotNull(replyToMessage.getChat());
        assertEquals("ReplyLastname", replyToMessage.getChat().getLastName());
        assertEquals("ReplyFirstname", replyToMessage.getChat().getFirstName());
        assertEquals("Testusername", replyToMessage.getChat().getUserName());
        assertEquals(Long.valueOf(1111112), replyToMessage.getChat().getId());
    }

    private void assertForwardFrom(User forwardFrom) {
        assertNotNull(forwardFrom);
        assertEquals("ForwardLastname", forwardFrom.getLastName());
        assertEquals("ForwardFirstname", forwardFrom.getFirstName());
        assertEquals(Integer.valueOf(222222), forwardFrom.getId());
    }

    private void assertPrivateChat(Chat chat) {
        assertNotNull(chat);
        assertEquals(Long.valueOf(1111111), chat.getId());
        assertTrue(chat.isUserChat());
        assertEquals("Test Lastname", chat.getLastName());
        assertEquals("Test Firstname", chat.getFirstName());
        assertEquals("Testusername", chat.getUserName());
    }

    private void assertChosenInlineQuery(ChosenInlineQuery chosenInlineQuery) {
        assertNotNull(chosenInlineQuery);
        assertEquals("12", chosenInlineQuery.getResultId());
        assertEquals("inline query", chosenInlineQuery.getQuery());
        assertEquals("1234csdbsk4839", chosenInlineQuery.getInlineMessageId());
        assertFromUser(chosenInlineQuery.getFrom());
    }

    private void assertInlineQuery(InlineQuery inlineQuery) {
        assertNotNull(inlineQuery);
        assertEquals("134567890097", inlineQuery.getId());
        assertEquals("inline query", inlineQuery.getQuery());
        assertEquals("offset", inlineQuery.getOffset());
        assertFromUser(inlineQuery.getFrom());
        assertNotNull(inlineQuery.getLocation());
        assertEquals(Float.valueOf("0.234242534"), inlineQuery.getLocation().getLatitude());
        assertEquals(Float.valueOf("0.234242534"), inlineQuery.getLocation().getLongitude());
    }

    private void assertCallbackQuery(CallbackQuery callbackQuery) {
        assertNotNull(callbackQuery);
        assertEquals("4382bfdwdsb323b2d9", callbackQuery.getId());
        assertEquals("Data from button callback", callbackQuery.getData());
        assertEquals("1234csdbsk4839", callbackQuery.getInlineMessageId());
        assertFromUser(callbackQuery.getFrom());
    }

    private void assertEditedMessage(Message message) {
        assertEquals((Integer) 1441645532, message.getDate());
        assertEquals((Integer) 1441646600, message.getEditDate());
        assertEquals((Integer) 1365, message.getMessageId());
        assertEquals("Edited text", message.getText());
        assertChannelChat(message.getChat());
        assertFromUser(message.getFrom());
    }

    private void assertFromUser(User from) {
        assertNotNull(from);
        assertEquals((Integer) 1111111, from.getId());
        assertEquals("Test Lastname", from.getLastName());
        assertEquals("Test Firstname", from.getFirstName());
        assertEquals("Testusername", from.getUserName());
    }

    private void assertChannelChat(Chat chat) {
        assertNotNull(chat);
        assertEquals(Long.valueOf(-10000000000L), chat.getId());
        assertTrue(chat.isChannelChat());
        assertEquals("Test channel", chat.getTitle());
    }
}