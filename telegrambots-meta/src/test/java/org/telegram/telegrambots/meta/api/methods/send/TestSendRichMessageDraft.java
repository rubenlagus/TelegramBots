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
public class TestSendRichMessageDraft {

    private InputRichMessage buildRichMessage() {
        return InputRichMessage.builder()
                .markdown("**Hello**")
                .build();
    }

    @Test
    public void testSendRichMessageDraftGetPath() {
        SendRichMessageDraft method = SendRichMessageDraft.builder()
                .chatId(123456L)
                .draftId(1)
                .richMessage(buildRichMessage())
                .build();

        assertEquals("sendRichMessageDraft", method.getMethod());
    }

    @Test
    public void testSendRichMessageDraftValidation() {
        SendRichMessageDraft method = SendRichMessageDraft.builder()
                .chatId(123456L)
                .draftId(1)
                .richMessage(buildRichMessage())
                .build();

        assertDoesNotThrow(method::validate);
    }

    @Test
    public void testSendRichMessageDraftValidationZeroDraftId() {
        SendRichMessageDraft method = SendRichMessageDraft.builder()
                .chatId(123456L)
                .draftId(0)
                .richMessage(buildRichMessage())
                .build();

        assertThrows(TelegramApiValidationException.class, method::validate);
    }

    @Test
    public void testSendRichMessageDraftNullRichMessageThrowsNPE() {
        SendRichMessageDraft method = SendRichMessageDraft.builder()
                .chatId(123456L)
                .draftId(1)
                .richMessage(buildRichMessage())
                .build();

        assertThrows(NullPointerException.class, () -> method.setRichMessage(null));
    }

    @Test
    public void testSendRichMessageDraftFields() {
        InputRichMessage richMessage = buildRichMessage();
        SendRichMessageDraft method = SendRichMessageDraft.builder()
                .chatId(123456L)
                .messageThreadId(7)
                .draftId(42)
                .richMessage(richMessage)
                .build();

        assertEquals(123456L, method.getChatId());
        assertEquals(7, method.getMessageThreadId());
        assertEquals(42, method.getDraftId());
        assertEquals(richMessage, method.getRichMessage());
    }

    @Test
    public void testSendRichMessageDraftOptionalFieldsAreNull() {
        SendRichMessageDraft method = SendRichMessageDraft.builder()
                .chatId(123456L)
                .draftId(1)
                .richMessage(buildRichMessage())
                .build();

        assertNull(method.getMessageThreadId());
    }
}
