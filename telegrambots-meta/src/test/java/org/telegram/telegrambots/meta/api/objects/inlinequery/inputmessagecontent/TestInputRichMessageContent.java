package org.telegram.telegrambots.meta.api.objects.inlinequery.inputmessagecontent;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.telegram.telegrambots.meta.api.objects.richtext.InputRichMessage;
import org.telegram.telegrambots.meta.exceptions.TelegramApiValidationException;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * @author Ruben Bermudez
 * @version 10.1
 */
public class TestInputRichMessageContent {
    private ObjectMapper mapper;

    @BeforeEach
    void setUp() {
        mapper = new ObjectMapper();
    }

    @Test
    public void testInputRichMessageContentValidation() {
        InputRichMessage richMessage = InputRichMessage.builder()
                .html("<p>Hello</p>")
                .build();
        InputRichMessageContent content = InputRichMessageContent.builder()
                .richMessage(richMessage)
                .build();

        assertDoesNotThrow(content::validate);
    }

    @Test
    public void testInputRichMessageContentNullRichMessageThrowsNPE() {
        InputRichMessageContent content = InputRichMessageContent.builder()
                .richMessage(InputRichMessage.builder().html("<p>Hello</p>").build())
                .build();

        assertThrows(NullPointerException.class, () -> content.setRichMessage(null));
    }

    @Test
    public void testInputRichMessageContentRichMessageField() {
        InputRichMessage richMessage = InputRichMessage.builder()
                .markdown("**Hello**")
                .build();
        InputRichMessageContent content = InputRichMessageContent.builder()
                .richMessage(richMessage)
                .build();

        assertEquals(richMessage, content.getRichMessage());
    }

    @Test
    public void testInputRichMessageContentDeserializesViaInputMessageContent() throws IOException {
        InputRichMessage richMessage = InputRichMessage.builder()
                .html("<p>Hello</p>")
                .build();
        InputRichMessageContent original = InputRichMessageContent.builder()
                .richMessage(richMessage)
                .build();

        String json = mapper.writeValueAsString(original);
        InputMessageContent deserialized = mapper.readValue(json, InputMessageContent.class);

        assertInstanceOf(InputRichMessageContent.class, deserialized);
        assertEquals(original, deserialized);
    }
}
