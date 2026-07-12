package org.telegram.telegrambots.meta.api.objects.richtext;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * @author Ruben Bermudez
 * @version 10.1
 */
public class TestRichText {
    private ObjectMapper mapper;

    @BeforeEach
    void setUp() {
        mapper = new ObjectMapper();
    }

    @Test
    public void testRichTextBoldTypeConstant() {
        assertEquals("bold", RichTextBold.TYPE);
    }

    @Test
    public void testRichTextAnchorTypeConstant() {
        assertEquals("anchor", RichTextAnchor.TYPE);
    }

    @Test
    public void testRichTextCustomEmojiTypeConstant() {
        assertEquals("custom_emoji", RichTextCustomEmoji.TYPE);
    }

    @Test
    public void testRichTextBoldBuilder() {
        RichTextAnchor inner = RichTextAnchor.builder().name("section").build();
        RichTextBold bold = RichTextBold.builder().text(inner).build();

        assertEquals("bold", bold.getType());
        assertEquals(inner, bold.getText());
    }

    @Test
    public void testRichTextAnchorBuilder() {
        RichTextAnchor anchor = RichTextAnchor.builder().name("chapter-1").build();

        assertEquals("anchor", anchor.getType());
        assertEquals("chapter-1", anchor.getName());
    }

    @Test
    public void testRichTextCustomEmojiBuilder() {
        RichTextCustomEmoji emoji = RichTextCustomEmoji.builder()
                .customEmojiId("5368324170671202286")
                .alternativeText("👍")
                .build();

        assertEquals("custom_emoji", emoji.getType());
        assertEquals("5368324170671202286", emoji.getCustomEmojiId());
        assertEquals("👍", emoji.getAlternativeText());
    }

    @Test
    public void testRichTextAnchorRoundTrip() throws IOException {
        RichTextAnchor anchor = RichTextAnchor.builder().name("chapter-1").build();

        String json = mapper.writeValueAsString(anchor);
        assertEquals(anchor, mapper.readValue(json, RichTextAnchor.class));
    }

    @Test
    public void testRichTextPolymorphicDeserializationBold() throws IOException {
        RichTextAnchor inner = RichTextAnchor.builder().name("section").build();
        RichTextBold bold = RichTextBold.builder().text(inner).build();

        String json = mapper.writeValueAsString(bold);
        RichText deserialized = mapper.readValue(json, RichText.class);

        assertInstanceOf(RichTextBold.class, deserialized);
        assertEquals(bold, deserialized);
    }

    @Test
    public void testRichTextPolymorphicDeserializationAnchor() throws IOException {
        RichTextAnchor anchor = RichTextAnchor.builder().name("chapter-1").build();

        String json = mapper.writeValueAsString(anchor);
        RichText deserialized = mapper.readValue(json, RichText.class);

        assertInstanceOf(RichTextAnchor.class, deserialized);
        assertEquals(anchor, deserialized);
    }

    @Test
    public void testRichTextPolymorphicDeserializationCustomEmoji() throws IOException {
        RichTextCustomEmoji emoji = RichTextCustomEmoji.builder()
                .customEmojiId("5368324170671202286")
                .alternativeText("👍")
                .build();

        String json = mapper.writeValueAsString(emoji);
        RichText deserialized = mapper.readValue(json, RichText.class);

        assertInstanceOf(RichTextCustomEmoji.class, deserialized);
        assertEquals(emoji, deserialized);
    }

    @Test
    public void testRichTextItalicRoundTrip() throws IOException {
        RichTextAnchor inner = RichTextAnchor.builder().name("test").build();
        RichTextItalic italic = RichTextItalic.builder().text(inner).build();

        String json = mapper.writeValueAsString(italic);
        RichText deserialized = mapper.readValue(json, RichText.class);

        assertInstanceOf(RichTextItalic.class, deserialized);
        assertEquals(italic, deserialized);
    }

    @Test
    public void testRichTextMathematicalExpressionRoundTrip() throws IOException {
        RichTextMathematicalExpression math = RichTextMathematicalExpression.builder()
                .expression("E = mc^2")
                .build();

        String json = mapper.writeValueAsString(math);
        RichText deserialized = mapper.readValue(json, RichText.class);

        assertInstanceOf(RichTextMathematicalExpression.class, deserialized);
        assertEquals(math, deserialized);
    }

    // --- Underline ---

    @Test
    public void testRichTextUnderlineRoundTrip() throws IOException {
        RichTextAnchor inner = RichTextAnchor.builder().name("test").build();
        RichTextUnderline obj = RichTextUnderline.builder().text(inner).build();

        String json = mapper.writeValueAsString(obj);
        RichText deserialized = mapper.readValue(json, RichText.class);

        assertInstanceOf(RichTextUnderline.class, deserialized);
        assertEquals(obj, deserialized);
    }

    // --- Strikethrough ---

    @Test
    public void testRichTextStrikethroughRoundTrip() throws IOException {
        RichTextAnchor inner = RichTextAnchor.builder().name("test").build();
        RichTextStrikethrough obj = RichTextStrikethrough.builder().text(inner).build();

        String json = mapper.writeValueAsString(obj);
        RichText deserialized = mapper.readValue(json, RichText.class);

        assertInstanceOf(RichTextStrikethrough.class, deserialized);
        assertEquals(obj, deserialized);
    }

    // --- Spoiler ---

    @Test
    public void testRichTextSpoilerRoundTrip() throws IOException {
        RichTextAnchor inner = RichTextAnchor.builder().name("test").build();
        RichTextSpoiler obj = RichTextSpoiler.builder().text(inner).build();

        String json = mapper.writeValueAsString(obj);
        RichText deserialized = mapper.readValue(json, RichText.class);

        assertInstanceOf(RichTextSpoiler.class, deserialized);
        assertEquals(obj, deserialized);
    }

    // --- DateTime ---

    @Test
    public void testRichTextDateTimeRoundTrip() throws IOException {
        RichTextAnchor inner = RichTextAnchor.builder().name("test").build();
        RichTextDateTime obj = RichTextDateTime.builder()
                .text(inner)
                .unixTime(1700000000)
                .dateTimeFormat("yyyy-MM-dd")
                .build();

        String json = mapper.writeValueAsString(obj);
        RichText deserialized = mapper.readValue(json, RichText.class);

        assertInstanceOf(RichTextDateTime.class, deserialized);
        assertEquals(obj, deserialized);
    }

    // --- TextMention (raw JSON because User has required fields) ---

    @Test
    public void testRichTextTextMentionPolymorphicDeserialization() throws IOException {
        String json = "{\"type\":\"text_mention\",\"text\":{\"type\":\"anchor\",\"name\":\"test\"},\"user\":{\"id\":123,\"is_bot\":false,\"first_name\":\"Test\"}}";
        RichText deserialized = mapper.readValue(json, RichText.class);

        assertInstanceOf(RichTextTextMention.class, deserialized);
        assertNotNull(((RichTextTextMention) deserialized).getUser());
    }

    // --- Subscript ---

    @Test
    public void testRichTextSubscriptRoundTrip() throws IOException {
        RichTextAnchor inner = RichTextAnchor.builder().name("test").build();
        RichTextSubscript obj = RichTextSubscript.builder().text(inner).build();

        String json = mapper.writeValueAsString(obj);
        RichText deserialized = mapper.readValue(json, RichText.class);

        assertInstanceOf(RichTextSubscript.class, deserialized);
        assertEquals(obj, deserialized);
    }

    // --- Superscript ---

    @Test
    public void testRichTextSuperscriptRoundTrip() throws IOException {
        RichTextAnchor inner = RichTextAnchor.builder().name("test").build();
        RichTextSuperscript obj = RichTextSuperscript.builder().text(inner).build();

        String json = mapper.writeValueAsString(obj);
        RichText deserialized = mapper.readValue(json, RichText.class);

        assertInstanceOf(RichTextSuperscript.class, deserialized);
        assertEquals(obj, deserialized);
    }

    // --- Marked ---

    @Test
    public void testRichTextMarkedRoundTrip() throws IOException {
        RichTextAnchor inner = RichTextAnchor.builder().name("test").build();
        RichTextMarked obj = RichTextMarked.builder().text(inner).build();

        String json = mapper.writeValueAsString(obj);
        RichText deserialized = mapper.readValue(json, RichText.class);

        assertInstanceOf(RichTextMarked.class, deserialized);
        assertEquals(obj, deserialized);
    }

    // --- Code ---

    @Test
    public void testRichTextCodeRoundTrip() throws IOException {
        RichTextAnchor inner = RichTextAnchor.builder().name("test").build();
        RichTextCode obj = RichTextCode.builder().text(inner).build();

        String json = mapper.writeValueAsString(obj);
        RichText deserialized = mapper.readValue(json, RichText.class);

        assertInstanceOf(RichTextCode.class, deserialized);
        assertEquals(obj, deserialized);
    }

    // --- Url ---

    @Test
    public void testRichTextUrlRoundTrip() throws IOException {
        RichTextAnchor inner = RichTextAnchor.builder().name("test").build();
        RichTextUrl obj = RichTextUrl.builder().text(inner).url("https://example.com").build();

        String json = mapper.writeValueAsString(obj);
        RichText deserialized = mapper.readValue(json, RichText.class);

        assertInstanceOf(RichTextUrl.class, deserialized);
        assertEquals(obj, deserialized);
    }

    // --- EmailAddress ---

    @Test
    public void testRichTextEmailAddressRoundTrip() throws IOException {
        RichTextAnchor inner = RichTextAnchor.builder().name("test").build();
        RichTextEmailAddress obj = RichTextEmailAddress.builder()
                .text(inner)
                .emailAddress("test@example.com")
                .build();

        String json = mapper.writeValueAsString(obj);
        RichText deserialized = mapper.readValue(json, RichText.class);

        assertInstanceOf(RichTextEmailAddress.class, deserialized);
        assertEquals(obj, deserialized);
    }

    // --- PhoneNumber ---

    @Test
    public void testRichTextPhoneNumberRoundTrip() throws IOException {
        RichTextAnchor inner = RichTextAnchor.builder().name("test").build();
        RichTextPhoneNumber obj = RichTextPhoneNumber.builder()
                .text(inner)
                .phoneNumber("+1234567890")
                .build();

        String json = mapper.writeValueAsString(obj);
        RichText deserialized = mapper.readValue(json, RichText.class);

        assertInstanceOf(RichTextPhoneNumber.class, deserialized);
        assertEquals(obj, deserialized);
    }

    // --- BankCardNumber ---

    @Test
    public void testRichTextBankCardNumberRoundTrip() throws IOException {
        RichTextAnchor inner = RichTextAnchor.builder().name("test").build();
        RichTextBankCardNumber obj = RichTextBankCardNumber.builder()
                .text(inner)
                .bankCardNumber("4111111111111111")
                .build();

        String json = mapper.writeValueAsString(obj);
        RichText deserialized = mapper.readValue(json, RichText.class);

        assertInstanceOf(RichTextBankCardNumber.class, deserialized);
        assertEquals(obj, deserialized);
    }

    // --- Mention ---

    @Test
    public void testRichTextMentionRoundTrip() throws IOException {
        RichTextAnchor inner = RichTextAnchor.builder().name("test").build();
        RichTextMention obj = RichTextMention.builder()
                .text(inner)
                .username("testuser")
                .build();

        String json = mapper.writeValueAsString(obj);
        RichText deserialized = mapper.readValue(json, RichText.class);

        assertInstanceOf(RichTextMention.class, deserialized);
        assertEquals(obj, deserialized);
    }

    // --- Hashtag ---

    @Test
    public void testRichTextHashtagRoundTrip() throws IOException {
        RichTextAnchor inner = RichTextAnchor.builder().name("test").build();
        RichTextHashtag obj = RichTextHashtag.builder()
                .text(inner)
                .hashtag("telegram")
                .build();

        String json = mapper.writeValueAsString(obj);
        RichText deserialized = mapper.readValue(json, RichText.class);

        assertInstanceOf(RichTextHashtag.class, deserialized);
        assertEquals(obj, deserialized);
    }

    // --- Cashtag ---

    @Test
    public void testRichTextCashtagRoundTrip() throws IOException {
        RichTextAnchor inner = RichTextAnchor.builder().name("test").build();
        RichTextCashtag obj = RichTextCashtag.builder()
                .text(inner)
                .cashtag("TON")
                .build();

        String json = mapper.writeValueAsString(obj);
        RichText deserialized = mapper.readValue(json, RichText.class);

        assertInstanceOf(RichTextCashtag.class, deserialized);
        assertEquals(obj, deserialized);
    }

    // --- BotCommand ---

    @Test
    public void testRichTextBotCommandRoundTrip() throws IOException {
        RichTextAnchor inner = RichTextAnchor.builder().name("test").build();
        RichTextBotCommand obj = RichTextBotCommand.builder()
                .text(inner)
                .botCommand("/start")
                .build();

        String json = mapper.writeValueAsString(obj);
        RichText deserialized = mapper.readValue(json, RichText.class);

        assertInstanceOf(RichTextBotCommand.class, deserialized);
        assertEquals(obj, deserialized);
    }

    // --- AnchorLink ---

    @Test
    public void testRichTextAnchorLinkRoundTrip() throws IOException {
        RichTextAnchor inner = RichTextAnchor.builder().name("test").build();
        RichTextAnchorLink obj = RichTextAnchorLink.builder()
                .text(inner)
                .anchorName("section-1")
                .build();

        String json = mapper.writeValueAsString(obj);
        RichText deserialized = mapper.readValue(json, RichText.class);

        assertInstanceOf(RichTextAnchorLink.class, deserialized);
        assertEquals(obj, deserialized);
    }

    // --- Reference ---

    @Test
    public void testRichTextReferenceRoundTrip() throws IOException {
        RichTextAnchor inner = RichTextAnchor.builder().name("test").build();
        RichTextReference obj = RichTextReference.builder()
                .text(inner)
                .name("ref-1")
                .build();

        String json = mapper.writeValueAsString(obj);
        RichText deserialized = mapper.readValue(json, RichText.class);

        assertInstanceOf(RichTextReference.class, deserialized);
        assertEquals(obj, deserialized);
    }

    // --- ReferenceLink ---

    @Test
    public void testRichTextReferenceLinkRoundTrip() throws IOException {
        RichTextAnchor inner = RichTextAnchor.builder().name("test").build();
        RichTextReferenceLink obj = RichTextReferenceLink.builder()
                .text(inner)
                .referenceName("note-1")
                .build();

        String json = mapper.writeValueAsString(obj);
        RichText deserialized = mapper.readValue(json, RichText.class);

        assertInstanceOf(RichTextReferenceLink.class, deserialized);
        assertEquals(obj, deserialized);
    }
}
