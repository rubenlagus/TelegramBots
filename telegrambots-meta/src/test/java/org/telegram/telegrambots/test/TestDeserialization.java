package org.telegram.telegrambots.test;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.telegram.telegrambots.api.objects.Audio;
import org.telegram.telegrambots.api.objects.CallbackQuery;
import org.telegram.telegrambots.api.objects.Chat;
import org.telegram.telegrambots.api.objects.Document;
import org.telegram.telegrambots.api.objects.EntityType;
import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.api.objects.MessageEntity;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.api.objects.User;
import org.telegram.telegrambots.api.objects.Voice;
import org.telegram.telegrambots.api.objects.inlinequery.ChosenInlineQuery;
import org.telegram.telegrambots.api.objects.inlinequery.InlineQuery;
import org.telegram.telegrambots.api.objects.replykeyboard.ApiResponse;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Ruben Bermudez
 * @version 1.0
 */
public class TestDeserialization {
    private ObjectMapper mapper;

    @Before
    public void setUp() throws Exception {
        mapper = new ObjectMapper();
    }

    @Test
    public void TestUpdateDeserialization() throws Exception {
        Update update = mapper.readValue(TelegramBotsHelper.GetUpdate(), Update.class);
        assertUpdate(update);
    }

    @Test
    public void TestResponseWithoutErrorDeserialization() throws IOException {
        ApiResponse<ArrayList<Update>> result = mapper.readValue(TelegramBotsHelper.GetResponseWithoutError(), new TypeReference<ApiResponse<ArrayList<Update>>>(){});
        Assert.assertNotNull(result);
        Assert.assertTrue(result.getOk());
        Assert.assertEquals(1, result.getResult().size());
        assertUpdate(result.getResult().get(0));
    }

    @Test
    public void TestResponseWithErrorDeserialization() throws IOException {
        ApiResponse<ArrayList<Update>> result = mapper.readValue(TelegramBotsHelper.GetResponseWithError(), new TypeReference<ApiResponse<ArrayList<Update>>>(){});
        Assert.assertNotNull(result);
        Assert.assertFalse(result.getOk());
        Assert.assertEquals(Integer.valueOf(400), result.getErrorCode());
        Assert.assertEquals("Error descriptions", result.getErrorDescription());
        Assert.assertNotNull(result.getParameters());
        Assert.assertEquals(Integer.valueOf(12345), result.getParameters().getMigrateToChatId());
        Assert.assertEquals(Integer.valueOf(12), result.getParameters().getRetryAfter());
    }

    private void assertUpdate(Update update) {
        Assert.assertNotNull(update);
        Assert.assertEquals((Integer) 10000, update.getUpdateId());
        assertEditedMessage(update.getEditedMessage());
        assertCallbackQuery(update.getCallbackQuery());
        assertInlineQuery(update.getInlineQuery());
        assertChosenInlineQuery(update.getChosenInlineQuery());
        assertMessage(update.getMessage());
    }

    private void assertMessage(Message message) {
        Assert.assertNotNull(message);
        Assert.assertEquals(Integer.valueOf(1441645532), message.getDate());
        Assert.assertEquals(Integer.valueOf(1365), message.getMessageId());
        Assert.assertEquals(Integer.valueOf(1441645550), message.getForwardDate());
        Assert.assertEquals("Bold and italics", message.getText());
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
        Assert.assertNotNull(document);
        Assert.assertEquals("AwADBAADbXXXXXXXXXXXGBdhD2l6_XX", document.getFileId());
        Assert.assertEquals("Testfile.pdf", document.getFileName());
        Assert.assertEquals("application/pdf", document.getMimeType());
        Assert.assertEquals(Integer.valueOf(536392), document.getFileSize());
    }

    private void assertVoice(Voice voice) {
        Assert.assertNotNull(voice);
        Assert.assertEquals("AwADBAADbXXXXXXXXXXXGBdhD2l6_XX", voice.getFileId());
        Assert.assertEquals(Integer.valueOf(5), voice.getDuration());
        Assert.assertEquals("audio/ogg", voice.getMimeType());
        Assert.assertEquals(Integer.valueOf(23000), voice.getFileSize());
    }

    private void assertAudio(Audio audio) {
        Assert.assertNotNull(audio);
        Assert.assertEquals("AwADBAADbXXXXXXXXXXXGBdhD2l6_XX", audio.getFileId());
        Assert.assertEquals(Integer.valueOf(243), audio.getDuration());
        Assert.assertEquals("audio/mpeg", audio.getMimeType());
        Assert.assertEquals(Integer.valueOf(3897500), audio.getFileSize());
        Assert.assertEquals("Testmusicfile", audio.getTitle());
    }

    private void assertEntities(List<MessageEntity> entities) {
        Assert.assertNotNull(entities);
        Assert.assertEquals(2, entities.size());
        Assert.assertEquals(EntityType.ITALIC, entities.get(0).getType());
        Assert.assertEquals(Integer.valueOf(9), entities.get(0).getOffset());
        Assert.assertEquals(Integer.valueOf(7), entities.get(0).getLength());
        Assert.assertEquals("italics", entities.get(0).getText());
        Assert.assertEquals(EntityType.BOLD, entities.get(1).getType());
        Assert.assertEquals(Integer.valueOf(0), entities.get(1).getOffset());
        Assert.assertEquals(Integer.valueOf(4), entities.get(1).getLength());
        Assert.assertEquals("Bold", entities.get(1).getText());
    }

    private void assertReplyToMessage(Message replyToMessage) {
        Assert.assertNotNull(replyToMessage);
        Assert.assertEquals(Integer.valueOf(1441645000), replyToMessage.getDate());
        Assert.assertEquals(Integer.valueOf(1334), replyToMessage.getMessageId());
        Assert.assertEquals("Original", replyToMessage.getText());
        Assert.assertNotNull(replyToMessage.getChat());
        Assert.assertEquals("ReplyLastname", replyToMessage.getChat().getLastName());
        Assert.assertEquals("ReplyFirstname", replyToMessage.getChat().getFirstName());
        Assert.assertEquals("Testusername", replyToMessage.getChat().getUserName());
        Assert.assertEquals(Long.valueOf(1111112), replyToMessage.getChat().getId());
    }

    private void assertForwardFrom(User forwardFrom) {
        Assert.assertNotNull(forwardFrom);
        Assert.assertEquals("ForwardLastname", forwardFrom.getLastName());
        Assert.assertEquals("ForwardFirstname", forwardFrom.getFirstName());
        Assert.assertEquals(Integer.valueOf(222222), forwardFrom.getId());
    }

    private void assertPrivateChat(Chat chat) {
        Assert.assertNotNull(chat);
        Assert.assertEquals(Long.valueOf(1111111), chat.getId());
        Assert.assertTrue(chat.isUserChat());
        Assert.assertEquals("Test Lastname", chat.getLastName());
        Assert.assertEquals("Test Firstname", chat.getFirstName());
        Assert.assertEquals("Testusername", chat.getUserName());
    }

    private void assertChosenInlineQuery(ChosenInlineQuery chosenInlineQuery) {
        Assert.assertNotNull(chosenInlineQuery);
        Assert.assertEquals("12", chosenInlineQuery.getResultId());
        Assert.assertEquals("inline query", chosenInlineQuery.getQuery());
        Assert.assertEquals("1234csdbsk4839", chosenInlineQuery.getInlineMessageId());
        assertFromUser(chosenInlineQuery.getFrom());
    }

    private void assertInlineQuery(InlineQuery inlineQuery) {
        Assert.assertNotNull(inlineQuery);
        Assert.assertEquals("134567890097", inlineQuery.getId());
        Assert.assertEquals("inline query", inlineQuery.getQuery());
        Assert.assertEquals("offset", inlineQuery.getOffset());
        assertFromUser(inlineQuery.getFrom());
        Assert.assertNotNull(inlineQuery.getLocation());
        Assert.assertEquals(Float.valueOf("0.234242534"), inlineQuery.getLocation().getLatitude());
        Assert.assertEquals(Float.valueOf("0.234242534"), inlineQuery.getLocation().getLongitude());
    }

    private void assertCallbackQuery(CallbackQuery callbackQuery) {
        Assert.assertNotNull(callbackQuery);
        Assert.assertEquals("4382bfdwdsb323b2d9", callbackQuery.getId());
        Assert.assertEquals("Data from button callback", callbackQuery.getData());
        Assert.assertEquals("1234csdbsk4839", callbackQuery.getInlineMessageId());
        assertFromUser(callbackQuery.getFrom());
    }

    private void assertEditedMessage(Message message) {
        Assert.assertEquals((Integer) 1441645532, message.getDate());
        Assert.assertEquals((Integer) 1441646600, message.getEditDate());
        Assert.assertEquals((Integer) 1365, message.getMessageId());
        Assert.assertEquals("Edited text", message.getText());
        assertChannelChat(message.getChat());
        assertFromUser(message.getFrom());
    }

    private void assertFromUser(User from) {
        Assert.assertNotNull(from);
        Assert.assertEquals((Integer) 1111111, from.getId());
        Assert.assertEquals("Test Lastname", from.getLastName());
        Assert.assertEquals("Test Firstname", from.getFirstName());
        Assert.assertEquals("Testusername", from.getUserName());
    }

    private void assertChannelChat(Chat chat) {
        Assert.assertNotNull(chat);
        Assert.assertEquals(Long.valueOf(-10000000000L), chat.getId());
        Assert.assertTrue(chat.isChannelChat());
        Assert.assertEquals("Test channel", chat.getTitle());
    }
}
