package org.telegram.telegrambots.meta.api.methods.send;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.telegram.telegrambots.meta.api.objects.richtext.InputRichMessage;
import org.telegram.telegrambots.meta.exceptions.TelegramApiValidationException;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * @author Ruben Bermudez
 * @version 10.3
 */
public class TestSendRichMessageDraft {
    private final ObjectMapper mapper = new ObjectMapper();

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

    @Test
    public void testCanStopAndKeepOnStopSerialize() throws IOException {
        SendRichMessageDraft method = SendRichMessageDraft.builder()
                .chatId(1L)
                .draftId(7)
                .richMessage(buildRichMessage())
                .canStop(true)
                .keepOnStop(true)
                .build();

        String json = mapper.writeValueAsString(method);

        assertTrue(json.contains("\"can_stop\":true"), json);
        assertTrue(json.contains("\"keep_on_stop\":true"), json);
    }

    @Test
    public void testCanStopAndKeepOnStopOmittedWhenUnset() throws IOException {
        SendRichMessageDraft method = SendRichMessageDraft.builder()
                .chatId(1L)
                .draftId(7)
                .richMessage(buildRichMessage())
                .build();

        String json = mapper.writeValueAsString(method);

        assertFalse(json.contains("can_stop"), json);
        assertFalse(json.contains("keep_on_stop"), json);
    }
}
