package org.telegram.telegrambots.meta.api.objects.replykeyboard;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.DisabledButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardRow;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * @author Ruben Bermudez
 * @version 10.3
 */
public class TestReplyMarkupForceReply {
    private ObjectMapper mapper;

    @BeforeEach
    void setUp() {
        mapper = new ObjectMapper();
    }

    @Test
    public void testInlineKeyboardMarkupSerializesForceReply() throws IOException {
        InlineKeyboardMarkup markup = InlineKeyboardMarkup.builder()
                .keyboardRow(new InlineKeyboardRow(InlineKeyboardButton.builder().text("a").build()))
                .forceReply(true)
                .build();

        String json = mapper.writeValueAsString(markup);

        assertTrue(json.contains("\"force_reply\":true"), json);
    }

    @Test
    public void testInlineKeyboardMarkupOmitsForceReplyWhenUnset() throws IOException {
        InlineKeyboardMarkup markup = InlineKeyboardMarkup.builder()
                .keyboardRow(new InlineKeyboardRow(InlineKeyboardButton.builder().text("a").build()))
                .build();

        assertFalse(mapper.writeValueAsString(markup).contains("force_reply"));
    }

    @Test
    public void testReplyKeyboardMarkupSerializesForceReply() throws IOException {
        ReplyKeyboardMarkup markup = ReplyKeyboardMarkup.builder()
                .keyboardRow(new KeyboardRow("a"))
                .forceReply(true)
                .build();

        String json = mapper.writeValueAsString(markup);

        assertTrue(json.contains("\"force_reply\":true"), json);
    }

    @Test
    public void testDisabledButtonSerializesAsEmptyObject() throws IOException {
        InlineKeyboardButton button = InlineKeyboardButton.builder()
                .text("nope")
                .disabled(new DisabledButton())
                .build();

        String json = mapper.writeValueAsString(button);

        assertTrue(json.contains("\"disabled\":{}"), json);
        assertDoesNotThrow(button::validate);
    }

    @Test
    public void testDisabledButtonDeserializes() throws IOException {
        String json = "{\"text\":\"nope\",\"disabled\":{}}";

        InlineKeyboardButton button = mapper.readValue(json, InlineKeyboardButton.class);

        assertNotNull(button.getDisabled());
    }

    /**
     * ReplyKeyboard had no discriminator on the wire and relied on Jackson's Id.DEDUCTION.
     * Sharing force_reply between ForceReplyKeyboard and ReplyKeyboardMarkup makes the former's
     * property set a subset of the latter's, so deduction goes ambiguous and - because
     * defaultImpl = Void.class - silently yields null. These cases pin the resolution.
     */
    @ParameterizedTest(name = "{1} -> {0}")
    @CsvSource(delimiterString = "|", value = {
            "ForceReplyKeyboard   | {\"force_reply\":true}",
            "ForceReplyKeyboard   | {\"force_reply\":true,\"selective\":true}",
            "ForceReplyKeyboard   | {\"force_reply\":true,\"input_field_placeholder\":\"x\"}",
            "ReplyKeyboardMarkup  | {\"keyboard\":[[{\"text\":\"a\"}]]}",
            "ReplyKeyboardMarkup  | {\"keyboard\":[[{\"text\":\"a\"}]],\"force_reply\":true}",
            "InlineKeyboardMarkup | {\"inline_keyboard\":[[{\"text\":\"a\"}]]}",
            "InlineKeyboardMarkup | {\"inline_keyboard\":[[{\"text\":\"a\"}]],\"force_reply\":true}",
            "ReplyKeyboardRemove  | {\"remove_keyboard\":true}",
    })
    public void testReplyKeyboardSubtypeResolution(String expectedType, String json) throws IOException {
        ReplyKeyboard keyboard = mapper.readValue(json, ReplyKeyboard.class);

        assertNotNull(keyboard, "ReplyKeyboard deserialized to null for " + json);
        assertEquals(expectedType, keyboard.getClass().getSimpleName(), json);
    }

    @Test
    public void testForceReplyKeyboardRoundTrips() throws IOException {
        ForceReplyKeyboard original = ForceReplyKeyboard.builder().selective(true).build();

        ReplyKeyboard result = mapper.readValue(mapper.writeValueAsString(original), ReplyKeyboard.class);

        assertInstanceOf(ForceReplyKeyboard.class, result);
        assertTrue(((ForceReplyKeyboard) result).getSelective());
    }
}
