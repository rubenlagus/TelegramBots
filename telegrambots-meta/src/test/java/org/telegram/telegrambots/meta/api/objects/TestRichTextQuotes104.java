package org.telegram.telegrambots.meta.api.objects;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import tools.jackson.databind.JsonNode;
import tools.jackson.databind.json.JsonMapper;
import org.junit.jupiter.api.Test;
import org.telegram.telegrambots.meta.api.objects.richtext.RichText;
import org.telegram.telegrambots.meta.api.objects.richtext.RichTextBold;
import org.telegram.telegrambots.meta.api.objects.richtext.RichTextConcat;
import org.telegram.telegrambots.meta.api.objects.richtext.RichTextPlain;
import org.telegram.telegrambots.meta.api.objects.richtext.RichTextTonAddress;

import java.io.IOException;
import java.io.ObjectStreamClass;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestRichTextQuotes104 {
    private final JsonMapper mapper = new JsonMapper();

    @Test
    public void testTonAddressRegistrationAndRoundTrip() throws IOException {
        assertTrue(Arrays.stream(RichText.class.getAnnotation(JsonSubTypes.class).value())
                .anyMatch(type -> type.name().equals("ton_address") && type.value() == RichTextTonAddress.class));

        RichTextTonAddress address = new RichTextTonAddress(new RichTextPlain("wallet"), "EQ123");
        String json = mapper.writeValueAsString(address);
        assertEquals(mapper.readTree("{\"type\":\"ton_address\",\"text\":\"wallet\",\"address\":\"EQ123\"}"),
                mapper.readTree(json));
        assertEquals(address, mapper.readValue(json, RichTextTonAddress.class));
        RichText polymorphic = mapper.readValue(json, RichText.class);
        assertInstanceOf(RichTextTonAddress.class, polymorphic);
        assertEquals(address, polymorphic);
        assertEquals(address, RichTextTonAddress.builder().text(new RichTextPlain("wallet")).address("EQ123").build());
        assertThrows(NullPointerException.class, () -> new RichTextTonAddress(null, "EQ123"));
        assertThrows(NullPointerException.class, () -> new RichTextTonAddress(new RichTextPlain("wallet"), null));
    }

    @Test
    public void testTonAddressWithNestedRichTextAndUnknownType() throws IOException {
        String json = "{\"type\":\"ton_address\",\"text\":[\"wallet \","
                + "{\"type\":\"bold\",\"text\":\"address\"}],\"address\":\"EQ123\"}";
        RichTextTonAddress address = (RichTextTonAddress) mapper.readValue(json, RichText.class);
        RichTextConcat text = assertInstanceOf(RichTextConcat.class, address.getText());
        assertEquals(2, text.getTexts().size());
        assertInstanceOf(RichTextPlain.class, text.getTexts().get(0));
        assertInstanceOf(RichTextBold.class, text.getTexts().get(1));
        assertEquals(mapper.readTree(json), mapper.readTree(mapper.writeValueAsString(address)));
        assertNull(mapper.readValue("{\"type\":\"future_type\",\"text\":\"value\"}", RichText.class));
        TextQuote quote = mapper.readValue("{\"text\":\"\",\"position\":0,\"rich_texts\":["
                + "\"known\",{\"type\":\"future_type\"}]}", TextQuote.class);
        assertInstanceOf(RichTextPlain.class, quote.getRichTexts().get(0));
        assertNull(quote.getRichTexts().get(1));
    }

    @Test
    public void testTextQuoteRichTextsRoundTrip() throws IOException {
        String json = "{\"text\":\"\",\"position\":7,\"is_manual\":true,\"rich_texts\":["
                + "\"first\",[\"second \",{\"type\":\"bold\",\"text\":\"part\"}]]}";
        TextQuote quote = mapper.readValue(json, TextQuote.class);
        assertEquals("", quote.getText());
        assertEquals(7, quote.getPosition());
        assertEquals(true, quote.getIsManual());
        assertInstanceOf(RichTextPlain.class, quote.getRichTexts().get(0));
        RichTextConcat nested = assertInstanceOf(RichTextConcat.class, quote.getRichTexts().get(1));
        assertInstanceOf(RichTextBold.class, nested.getTexts().get(1));
        assertEquals(mapper.readTree(json), mapper.readTree(mapper.writeValueAsString(quote)));

        TextQuote built = TextQuote.builder().text("").position(7).richTexts(quote.getRichTexts()).build();
        assertEquals(quote.getRichTexts(), built.getRichTexts());
    }

    @Test
    public void testReplyQuoteRichTextsRoundTrip() throws IOException {
        String json = "{\"message_id\":42,\"quote_position\":3,\"quote_rich_texts\":["
                + "\"first\",[\"second \",{\"type\":\"bold\",\"text\":\"part\"}]]}";
        ReplyParameters reply = mapper.readValue(json, ReplyParameters.class);
        assertEquals(42, reply.getMessageId());
        assertEquals(3, reply.getQuotePosition());
        assertInstanceOf(RichTextPlain.class, reply.getQuoteRichTexts().get(0));
        RichTextConcat nested = assertInstanceOf(RichTextConcat.class, reply.getQuoteRichTexts().get(1));
        assertInstanceOf(RichTextBold.class, nested.getTexts().get(1));
        assertEquals(mapper.readTree(json), mapper.readTree(mapper.writeValueAsString(reply)));

        ReplyParameters built = ReplyParameters.builder().messageId(42).quoteRichTexts(reply.getQuoteRichTexts()).build();
        assertEquals(reply.getQuoteRichTexts(), built.getQuoteRichTexts());
        built.setQuoteRichTexts(List.of(new RichTextPlain("replacement")));
        assertEquals("replacement", ((RichTextPlain) built.getQuoteRichTexts().get(0)).getText());
    }

    @Test
    public void testReplyQuoteRichTextLimits() throws Exception {
        ReplyParameters maxLength = ReplyParameters.builder().messageId(42)
                .quoteRichTexts(List.of(new RichTextPlain("a".repeat(1024))))
                .build();
        maxLength.validate();

        ReplyParameters tooLong = ReplyParameters.builder().messageId(42)
                .quoteRichTexts(List.of(RichTextBold.builder().text(new RichTextPlain("a".repeat(1025))).build()))
                .build();
        assertThrows(org.telegram.telegrambots.meta.exceptions.TelegramApiValidationException.class, tooLong::validate);

        ReplyParameters tooManyTexts = ReplyParameters.builder().messageId(42)
                .quoteRichTexts(java.util.Collections.nCopies(33, new RichTextPlain("a")))
                .build();
        assertThrows(org.telegram.telegrambots.meta.exceptions.TelegramApiValidationException.class, tooManyTexts::validate);
    }

    @Test
    public void testPreservesPre104SerializationIds() {
        assertEquals(-5841820526754490266L, ObjectStreamClass.lookup(TextQuote.class).getSerialVersionUID());
        assertEquals(-4041620488663382981L, ObjectStreamClass.lookup(ReplyParameters.class).getSerialVersionUID());
    }

    @Test
    public void testOptionalFieldsAndLegacyConstructors() throws IOException {
        TextQuote quote = new TextQuote("old", null, 2, null);
        assertNull(quote.getRichTexts());
        assertFalse(mapper.readTree(mapper.writeValueAsString(quote)).has("rich_texts"));
        assertNull(mapper.readValue("{\"text\":\"old\",\"position\":2}", TextQuote.class).getRichTexts());
        TextQuote fullQuote = new TextQuote("old", null, 2, null, List.of());
        assertTrue(mapper.readTree(mapper.writeValueAsString(fullQuote)).get("rich_texts").isArray());

        ReplyParameters reply = new ReplyParameters(42, null, "chat", 1, "poll", true,
                "old", "HTML", List.of(), 2);
        assertEquals(42, reply.getMessageId());
        assertEquals("chat", reply.getChatId());
        assertEquals(1, reply.getChecklistTaskId());
        assertEquals("poll", reply.getPollOptionId());
        assertEquals(true, reply.getAllowSendingWithoutReply());
        assertEquals("HTML", reply.getQuoteParseMode());
        assertEquals(List.of(), reply.getQuoteEntities());
        assertEquals(2, reply.getQuotePosition());
        assertNull(reply.getQuoteRichTexts());
        JsonNode node = mapper.readTree(mapper.writeValueAsString(reply));
        assertFalse(node.has("quote_rich_texts"));
        assertEquals("old", node.get("quote").asText());
        assertNull(mapper.readValue("{\"message_id\":42}", ReplyParameters.class).getQuoteRichTexts());
        assertNull(ReplyParameters.builder().messageId(42).build().getQuoteRichTexts());

        reply.setQuoteRichTexts(List.of());
        assertTrue(mapper.readTree(mapper.writeValueAsString(reply)).get("quote_rich_texts").isArray());
        ReplyParameters fullReply = new ReplyParameters(42, null, "chat", 1, "poll", true,
                "old", "HTML", List.of(), 2, List.of(new RichTextPlain("rich")));
        assertEquals("rich", ((RichTextPlain) fullReply.getQuoteRichTexts().get(0)).getText());
    }

    @Test
    public void testEntityTypes() throws IOException {
        for (String type : List.of("ton_address", "bank_card_number")) {
            MessageEntity entity = new MessageEntity(type, 1, 4);
            JsonNode json = mapper.readTree(mapper.writeValueAsString(entity));
            assertEquals(type, json.get("type").asText());
            assertEquals(entity, mapper.readValue(json.toString(), MessageEntity.class));
        }
    }
}