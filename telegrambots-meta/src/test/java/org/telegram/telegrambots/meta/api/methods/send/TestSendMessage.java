package org.telegram.telegrambots.meta.api.methods.send;

import org.junit.jupiter.api.Test;
import org.telegram.telegrambots.meta.api.objects.MessageEntity;
import org.telegram.telegrambots.meta.api.objects.LinkPreviewOptions;
import org.telegram.telegrambots.meta.exceptions.TelegramApiValidationException;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.api.methods.ParseMode;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

class TestSendMessage {

    @Test
    void comparison() {
        SendMessage sm1 = SendMessage
                .builder()
                .chatId(1L)
                .text("Hello World")
                .build();
        SendMessage sm2 = SendMessage
                .builder()
                .chatId("1")
                .text("Hello World")
                .build();
        SendMessage disabledNotification = SendMessage
                .builder()
                .chatId("1")
                .text("Hello World")
                .disableNotification(true)
                .build();

        assertEquals(sm1, sm2);
        assertNotEquals(sm1, disabledNotification);

        assertEquals(sm1.hashCode(), sm2.hashCode());
        assertNotEquals(sm1.hashCode(), disabledNotification.hashCode());
    }

    /**
     * Test validation of empty text message
     * Ensures that an exception is thrown when message text is empty
     */
    @Test
    void testEmptyTextValidation() {
        SendMessage message = SendMessage
                .builder()
                .chatId("123")
                .text("")
                .build();

        assertThrows(TelegramApiValidationException.class, message::validate);
    }

    /**
     * Test validation of concurrent parseMode and entities settings
     * Ensures that an exception is thrown when both parseMode and entities are set
     */
    @Test
    void testParseModeAndEntitiesValidation() {
        List<MessageEntity> entities = new ArrayList<>();
        MessageEntity entity = new MessageEntity("bold", 0, 4);
        entities.add(entity);
        
        SendMessage message = SendMessage
                .builder()
                .chatId("123")
                .text("test")
                .parseMode("html")
                .entities(entities)
                .build();

        assertThrows(TelegramApiValidationException.class, message::validate);
    }

    /**
     * Test different parseMode settings
     * Verifies correct setting and clearing of different parse modes
     */
    @Test
    void testParseModeSettings() {
        SendMessage message = SendMessage
                .builder()
                .chatId("123")
                .text("test")
                .build();

        message.enableMarkdown(true);
        assertEquals("Markdown", message.getParseMode());
        
        message.enableHtml(true);
        assertEquals("html", message.getParseMode());
        
        message.enableMarkdownV2(true);
        assertEquals("MarkdownV2", message.getParseMode());
        
        message.enableHtml(false);
        assertNull(message.getParseMode());
    }

    /**
     * Test web page preview settings
     * Verifies enabling and disabling of web page preview functionality
     */
    @Test
    void testWebPagePreviewSettings() {
        SendMessage message = SendMessage
                .builder()
                .chatId("123")
                .text("test")
                .build();

        assertNull(message.getDisableWebPagePreview());
        
        message.disableWebPagePreview();
        assertTrue(message.getDisableWebPagePreview());
        
        message.enableWebPagePreview();
        assertNull(message.getDisableWebPagePreview());
    }

    /**
     * Test notification settings
     * Verifies enabling and disabling of notification functionality
     */
    @Test
    void testNotificationSettings() {
        SendMessage message = SendMessage
                .builder()
                .chatId("123")
                .text("test")
                .build();

        assertNull(message.getDisableNotification());
        
        message.disableNotification();
        assertTrue(message.getDisableNotification());
        
        message.enableNotification();
        assertNull(message.getDisableNotification());
    }

    /**
     * Test reply markup validation
     * Verifies setting and validation of custom keyboard markup
     */
    @Test
    void testReplyMarkupValidation() {
        ReplyKeyboardMarkup keyboardMarkup = ReplyKeyboardMarkup
                .builder()
                .keyboard(new ArrayList<>())
                .build();

        SendMessage message = SendMessage
                .builder()
                .chatId("123")
                .text("test")
                .replyMarkup(keyboardMarkup)
                .build();

        assertDoesNotThrow(message::validate);
    }

    /**
     * Test link preview options validation
     * Verifies setting and validation of link preview options
     */
    @Test
    void testLinkPreviewOptionsValidation() {
        LinkPreviewOptions previewOptions = LinkPreviewOptions
                .builder()
                .isDisabled(true)
                .build();

        SendMessage message = SendMessage
                .builder()
                .chatId("123")
                .text("test")
                .linkPreviewOptions(previewOptions)
                .build();

        assertDoesNotThrow(message::validate);
    }

    /**
     * Test getMethod name
     * Verifies correct return of API method name
     */
    @Test
    void testGetMethod() {
        SendMessage message = SendMessage
                .builder()
                .chatId("123")
                .text("test")
                .build();

        assertEquals("sendmessage", message.getMethod());
    }
}