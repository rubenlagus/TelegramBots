package org.telegram.telegrambots.meta.api.objects.richtext;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.DisabledButton;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

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

    // --- Plain ---

    @Test
    public void testRichTextPlainTypeConstant() {
        assertEquals("plain", RichTextPlain.TYPE);
    }

    @Test
    public void testRichTextPlainBuilder() {
        RichTextPlain plain = RichTextPlain.builder().text("Hello, world!").build();

        assertEquals("plain", plain.getType());
        assertEquals("Hello, world!", plain.getText());
    }

    @Test
    public void testRichTextPlainRoundTrip() throws IOException {
        RichTextPlain obj = RichTextPlain.builder().text("Hello, world!").build();

        String json = mapper.writeValueAsString(obj);
        RichText deserialized = mapper.readValue(json, RichText.class);

        assertInstanceOf(RichTextPlain.class, deserialized);
        assertEquals(obj, deserialized);
    }

    @Test
    public void testRichTextPlainPolymorphicDeserialization() throws IOException {
        String json = "{\"type\":\"plain\",\"text\":\"Hello, world!\"}";
        RichText deserialized = mapper.readValue(json, RichText.class);

        assertInstanceOf(RichTextPlain.class, deserialized);
        assertEquals("Hello, world!", ((RichTextPlain) deserialized).getText());
    }

    // --- Plain text must serialize as a bare JSON string (issue #1599) ---
    // The Bot API defines RichText as "either a String for plain text, an Array of RichText, or
    // any of the following types" - there is no "plain" type on the wire. Emitting
    // {"type":"plain",...} makes Telegram answer "Unsupported rich text type".

    @Test
    public void testRichTextPlainSerializesAsBareString() throws IOException {
        RichTextPlain plain = RichTextPlain.builder().text("Иван").build();

        assertEquals("\"Иван\"", mapper.writeValueAsString(plain));
    }

    @Test
    public void testRichTextPlainNestedInBoldSerializesAsBareString() throws IOException {
        RichTextBold bold = RichTextBold.builder()
                .text(RichTextPlain.builder().text("Иван").build())
                .build();

        assertEquals("{\"type\":\"bold\",\"text\":\"Иван\"}", mapper.writeValueAsString(bold));
    }

    @Test
    public void testRichTextPlainNestedInBoldDeclaredAsRichTextSerializesAsBareString() throws IOException {
        RichText bold = RichTextBold.builder()
                .text(RichTextPlain.builder().text("Иван").build())
                .build();

        assertEquals("{\"type\":\"bold\",\"text\":\"Иван\"}", mapper.writeValueAsString(bold));
    }

    @Test
    public void testRichTextConcatSerializesPlainChildrenAsBareStrings() throws IOException {
        RichTextConcat concat = RichTextConcat.builder()
                .texts(java.util.List.of(
                        RichTextPlain.builder().text("Hello ").build(),
                        RichTextBold.builder().text(RichTextPlain.builder().text("world").build()).build(),
                        RichTextPlain.builder().text("!").build()
                ))
                .build();

        assertEquals("[\"Hello \",{\"type\":\"bold\",\"text\":\"world\"},\"!\"]",
                mapper.writeValueAsString(concat));
    }

    // --- Raw JSON string form ("Politics" → plain text node) ---

    @Test
    public void testRichTextRawStringDeserialization() throws IOException {
        String json = "\"Politics\"";
        RichText deserialized = mapper.readValue(json, RichText.class);

        assertInstanceOf(RichTextPlain.class, deserialized);
        assertEquals("Politics", ((RichTextPlain) deserialized).getText());
    }

    // --- Typed RichText object with bare-string text (issue #1593) ---

    @Test
    public void testRichTextBoldWithBareStringText() throws IOException {
        // Exact form from issue #1593: {"type":"bold","text":"world"} where text is a bare string,
        // not a typed object. RichTextDeserializer must handle this recursively.
        String json = "{\"type\":\"bold\",\"text\":\"world\"}";
        RichText deserialized = mapper.readValue(json, RichText.class);

        assertInstanceOf(RichTextBold.class, deserialized);
        RichTextBold bold = (RichTextBold) deserialized;
        assertInstanceOf(RichTextPlain.class, bold.getText());
        assertEquals("world", ((RichTextPlain) bold.getText()).getText());
    }

    // --- Raw JSON array form (["Hello ", {bold}, "!"]) ---

    @Test
    public void testRichTextRawArrayDeserialization() throws IOException {
        String json = "[\"Hello \", {\"type\":\"bold\",\"text\":{\"type\":\"plain\",\"text\":\"world\"}}, \"!\"]";
        RichText deserialized = mapper.readValue(json, RichText.class);

        assertInstanceOf(RichTextConcat.class, deserialized);
        RichTextConcat concat = (RichTextConcat) deserialized;
        assertEquals(3, concat.getTexts().size());
        assertInstanceOf(RichTextPlain.class, concat.getTexts().get(0));
        assertInstanceOf(RichTextBold.class, concat.getTexts().get(1));
        assertInstanceOf(RichTextPlain.class, concat.getTexts().get(2));
    }

    @Test
    public void testRichTextArrayWithBoldHavingBareStringText() throws IOException {
        // Exact array form from issue #1593: strings and bold-with-bare-string mixed
        String json = "[\"Hello \", {\"type\":\"bold\",\"text\":\"world\"}, \"!\"]";
        RichText deserialized = mapper.readValue(json, RichText.class);

        assertInstanceOf(RichTextConcat.class, deserialized);
        RichTextConcat concat = (RichTextConcat) deserialized;
        assertEquals(3, concat.getTexts().size());
        assertInstanceOf(RichTextPlain.class, concat.getTexts().get(0));
        assertEquals("Hello ", ((RichTextPlain) concat.getTexts().get(0)).getText());
        assertInstanceOf(RichTextBold.class, concat.getTexts().get(1));
        RichTextBold bold = (RichTextBold) concat.getTexts().get(1);
        assertInstanceOf(RichTextPlain.class, bold.getText());
        assertEquals("world", ((RichTextPlain) bold.getText()).getText());
        assertInstanceOf(RichTextPlain.class, concat.getTexts().get(2));
        assertEquals("!", ((RichTextPlain) concat.getTexts().get(2)).getText());
    }

    @Test
    public void testRichTextConcatRoundTrip() throws IOException {
        RichTextConcat concat = RichTextConcat.builder()
                .texts(java.util.List.of(
                        RichTextPlain.builder().text("Hello ").build(),
                        RichTextBold.builder().text(RichTextPlain.builder().text("world").build()).build(),
                        RichTextPlain.builder().text("!").build()
                ))
                .build();

        String json = mapper.writeValueAsString(concat);
        RichText deserialized = mapper.readValue(json, RichText.class);

        assertInstanceOf(RichTextConcat.class, deserialized);
        assertEquals(3, ((RichTextConcat) deserialized).getTexts().size());
    }

    // --- End-to-end: message with heading (raw string) and paragraph (array) ---

    @Test
    public void testRichMessageBlocksWithStringAndArrayText() throws IOException {
        String json = "{\"blocks\":["
                + "{\"type\":\"heading\",\"size\":2,\"text\":\"Politics\"},"
                + "{\"type\":\"paragraph\",\"text\":[\"Hello \",{\"type\":\"bold\",\"text\":{\"type\":\"plain\",\"text\":\"world\"}},\"!\"]}"
                + "]}";

        org.telegram.telegrambots.meta.api.objects.richtext.RichMessage msg =
                mapper.readValue(json, org.telegram.telegrambots.meta.api.objects.richtext.RichMessage.class);

        assertNotNull(msg);
        assertEquals(2, msg.getBlocks().size());

        // First block: heading with plain string text
        org.telegram.telegrambots.meta.api.objects.richblock.RichBlockSectionHeading heading =
                (org.telegram.telegrambots.meta.api.objects.richblock.RichBlockSectionHeading) msg.getBlocks().get(0);
        assertInstanceOf(RichTextPlain.class, heading.getText());
        assertEquals("Politics", ((RichTextPlain) heading.getText()).getText());

        // Second block: paragraph with array text
        org.telegram.telegrambots.meta.api.objects.richblock.RichBlockParagraph para =
                (org.telegram.telegrambots.meta.api.objects.richblock.RichBlockParagraph) msg.getBlocks().get(1);
        assertInstanceOf(RichTextConcat.class, para.getText());
        assertEquals(3, ((RichTextConcat) para.getText()).getTexts().size());
    }

    @Test
    public void testRichMessageBlocksWithBoldHavingBareStringText() throws IOException {
        // Issue #1593 exact paragraph payload: bold.text is a bare string, not a typed object
        String json = "{\"blocks\":["
                + "{\"type\":\"heading\",\"size\":2,\"text\":\"Politics\"},"
                + "{\"type\":\"paragraph\",\"text\":[\"Hello \",{\"type\":\"bold\",\"text\":\"world\"},\"!\"]}"
                + "]}";

        org.telegram.telegrambots.meta.api.objects.richtext.RichMessage msg =
                mapper.readValue(json, org.telegram.telegrambots.meta.api.objects.richtext.RichMessage.class);

        assertNotNull(msg);
        assertEquals(2, msg.getBlocks().size());

        org.telegram.telegrambots.meta.api.objects.richblock.RichBlockParagraph para =
                (org.telegram.telegrambots.meta.api.objects.richblock.RichBlockParagraph) msg.getBlocks().get(1);
        assertInstanceOf(RichTextConcat.class, para.getText());
        RichTextConcat concat = (RichTextConcat) para.getText();
        assertEquals(3, concat.getTexts().size());
        assertInstanceOf(RichTextBold.class, concat.getTexts().get(1));
        RichTextBold bold = (RichTextBold) concat.getTexts().get(1);
        assertInstanceOf(RichTextPlain.class, bold.getText());
        assertEquals("world", ((RichTextPlain) bold.getText()).getText());
    }

    @Test
    public void testRichTextButtonTypeConstant() {
        assertEquals("button", RichTextButton.TYPE);
    }

    @Test
    public void testSerializeRichTextButton() throws IOException {
        RichTextButton button = RichTextButton.builder()
                .button(RichMessageButton.builder()
                        .text(new RichTextPlain("Press me"))
                        .callbackData("cb")
                        .style("primary")
                        .build())
                .build();

        String json = mapper.writeValueAsString(button);

        assertTrue(json.contains("\"type\":\"button\""), json);
        assertTrue(json.contains("\"callback_data\":\"cb\""), json);
        assertTrue(json.contains("\"style\":\"primary\""), json);
        assertTrue(json.contains("\"text\":\"Press me\""), json);
    }

    /**
     * Guards the two-place registration: RichText is deserialized by RichTextDeserializer, not by
     * JsonSubTypes, so a subtype missing from RichTextDeserializer.TYPE_MAP deserializes to null.
     */
    @Test
    public void testDeserializeRichTextButtonAsRichText() throws IOException {
        String json = "{\"type\":\"button\",\"button\":{\"text\":\"Press me\",\"callback_data\":\"cb\"}}";

        RichText result = mapper.readValue(json, RichText.class);

        assertNotNull(result, "RichTextButton missing from RichTextDeserializer.TYPE_MAP");
        assertInstanceOf(RichTextButton.class, result);
        RichMessageButton button = ((RichTextButton) result).getButton();
        assertEquals("cb", button.getCallbackData());
        assertInstanceOf(RichTextPlain.class, button.getText());
        assertEquals("Press me", ((RichTextPlain) button.getText()).getText());
    }

    @Test
    public void testRichMessageButtonOptionalFieldsOmitted() throws IOException {
        RichMessageButton button = RichMessageButton.builder()
                .text(new RichTextPlain("bare"))
                .build();

        String json = mapper.writeValueAsString(button);

        assertFalse(json.contains("style"), json);
        assertFalse(json.contains("url"), json);
        assertFalse(json.contains("callback_data"), json);
        assertFalse(json.contains("disabled"), json);
    }

    @Test
    public void testRichMessageButtonWithDisabled() throws IOException {
        RichMessageButton button = RichMessageButton.builder()
                .text(new RichTextPlain("nope"))
                .disabled(new DisabledButton())
                .build();

        assertTrue(mapper.writeValueAsString(button).contains("\"disabled\":{}"));
    }
}
