package org.telegram.telegrambots.meta.api.methods.send;

import org.junit.jupiter.api.Test;
import org.telegram.telegrambots.meta.api.objects.richtext.InputRichMessage;
import org.telegram.telegrambots.meta.exceptions.TelegramApiValidationException;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * @author Ruben Bermudez
 * @version 10.1
 */
public class TestSendRichMessage {

    private InputRichMessage buildRichMessage() {
        return InputRichMessage.builder()
                .html("<p>Hello</p>")
                .build();
    }

    @Test
    public void testSendRichMessageGetPath() {
        SendRichMessage method = SendRichMessage.builder()
                .chatId("123456")
                .richMessage(buildRichMessage())
                .build();

        assertEquals("sendRichMessage", method.getMethod());
    }

    @Test
    public void testSendRichMessageValidation() {
        SendRichMessage method = SendRichMessage.builder()
                .chatId("123456")
                .richMessage(buildRichMessage())
                .build();

        assertDoesNotThrow(method::validate);
    }

    @Test
    public void testSendRichMessageValidationFailsWithBothHtmlAndMarkdown() {
        InputRichMessage invalidRichMessage = InputRichMessage.builder()
                .html("<p>Hello</p>")
                .markdown("**Hello**")
                .build();
        SendRichMessage method = SendRichMessage.builder()
                .chatId("123456")
                .richMessage(invalidRichMessage)
                .build();

        assertThrows(TelegramApiValidationException.class, method::validate);
    }

    @Test
    public void testSendRichMessageValidationFailsWithNeitherHtmlNorMarkdown() {
        InputRichMessage invalidRichMessage = new InputRichMessage();
        SendRichMessage method = SendRichMessage.builder()
                .chatId("123456")
                .richMessage(invalidRichMessage)
                .build();

        assertThrows(TelegramApiValidationException.class, method::validate);
    }

    @Test
    public void testSendRichMessageWithLongChatId() {
        SendRichMessage method = SendRichMessage.builder()
                .chatId(123456L)
                .richMessage(buildRichMessage())
                .build();

        assertEquals("123456", method.getChatId());
        assertDoesNotThrow(method::validate);
    }

    @Test
    public void testSendRichMessageNullRichMessageThrowsNPE() {
        SendRichMessage method = SendRichMessage.builder()
                .chatId("123456")
                .richMessage(buildRichMessage())
                .build();

        assertThrows(NullPointerException.class, () -> method.setRichMessage(null));
    }

    @Test
    public void testSendRichMessageFields() {
        InputRichMessage richMessage = buildRichMessage();
        SendRichMessage method = SendRichMessage.builder()
                .chatId("123456")
                .messageThreadId(42)
                .directMessagesTopicId(7)
                .richMessage(richMessage)
                .disableNotification(true)
                .protectContent(false)
                .allowPaidBroadcast(true)
                .messageEffectId("effect123")
                .build();

        assertEquals("123456", method.getChatId());
        assertEquals(42, method.getMessageThreadId());
        assertEquals(7, method.getDirectMessagesTopicId());
        assertEquals(richMessage, method.getRichMessage());
        assertEquals(true, method.getDisableNotification());
        assertEquals(false, method.getProtectContent());
        assertEquals(true, method.getAllowPaidBroadcast());
        assertEquals("effect123", method.getMessageEffectId());
    }

    @Test
    public void testSendRichMessageOptionalFieldsAreNull() {
        SendRichMessage method = SendRichMessage.builder()
                .chatId("123456")
                .richMessage(buildRichMessage())
                .build();

        assertNull(method.getBusinessConnectionId());
        assertNull(method.getMessageThreadId());
        assertNull(method.getDirectMessagesTopicId());
        assertNull(method.getDisableNotification());
        assertNull(method.getProtectContent());
        assertNull(method.getAllowPaidBroadcast());
        assertNull(method.getMessageEffectId());
        assertNull(method.getReplyParameters());
        assertNull(method.getReplyMarkup());
    }
}
