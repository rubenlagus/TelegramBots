package org.telegram.telegrambots.meta.api.methods.updatingmessages;

import org.junit.jupiter.api.Test;
import org.telegram.telegrambots.meta.api.objects.richtext.InputRichMessage;
import org.telegram.telegrambots.meta.exceptions.TelegramApiValidationException;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * @author Ruben Bermudez
 * @version 10.1
 */
public class TestEditMessageText {

    @Test
    public void testEditMessageTextWithTextOnlyPasses() {
        EditMessageText method = EditMessageText.builder()
                .chatId("123456")
                .messageId(1)
                .text("Hello world")
                .build();

        assertDoesNotThrow(method::validate);
    }

    @Test
    public void testEditMessageTextWithRichMessageOnlyPasses() {
        InputRichMessage richMessage = InputRichMessage.builder()
                .html("<p>Hello</p>")
                .build();
        EditMessageText method = EditMessageText.builder()
                .chatId("123456")
                .messageId(1)
                .richMessage(richMessage)
                .build();

        assertDoesNotThrow(method::validate);
    }

    @Test
    public void testEditMessageTextWithNeitherTextNorRichMessageThrows() {
        EditMessageText method = EditMessageText.builder()
                .chatId("123456")
                .messageId(1)
                .build();

        assertThrows(TelegramApiValidationException.class, method::validate);
    }

    @Test
    public void testEditMessageTextWithEmptyTextThrows() {
        EditMessageText method = EditMessageText.builder()
                .chatId("123456")
                .messageId(1)
                .text("")
                .build();

        assertThrows(TelegramApiValidationException.class, method::validate);
    }

    @Test
    public void testEditMessageTextInlineMessageWithRichMessagePasses() {
        InputRichMessage richMessage = InputRichMessage.builder()
                .markdown("**bold**")
                .build();
        EditMessageText method = EditMessageText.builder()
                .inlineMessageId("inline_123")
                .richMessage(richMessage)
                .build();

        assertDoesNotThrow(method::validate);
    }

    @Test
    public void testEditMessageTextWithInlineMessageIdNoChatIdRequired() {
        EditMessageText method = EditMessageText.builder()
                .inlineMessageId("inline_123")
                .text("Hello")
                .build();

        assertDoesNotThrow(method::validate);
    }

    @Test
    public void testEditMessageTextMissingChatIdThrows() {
        EditMessageText method = EditMessageText.builder()
                .messageId(1)
                .text("Hello")
                .build();

        assertThrows(TelegramApiValidationException.class, method::validate);
    }

    @Test
    public void testEditMessageTextWithBothTextAndRichMessagePasses() {
        InputRichMessage richMessage = InputRichMessage.builder()
                .html("<p>Hello</p>")
                .build();
        EditMessageText method = EditMessageText.builder()
                .chatId("123456")
                .messageId(1)
                .text("Hello world")
                .richMessage(richMessage)
                .build();

        assertDoesNotThrow(method::validate);
    }
}
