package org.telegram.telegrambots.meta.api.objects.richblock;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.telegram.telegrambots.meta.api.objects.richtext.RichTextAnchor;
import org.telegram.telegrambots.meta.api.objects.richtext.RichTextMathematicalExpression;
import org.telegram.telegrambots.meta.api.objects.richtext.RichTextPlain;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * @author Ruben Bermudez
 * @version 10.1
 */
public class TestRichBlock {
    private ObjectMapper mapper;

    @BeforeEach
    void setUp() {
        mapper = new ObjectMapper();
    }

    @Test
    public void testRichBlockParagraphTypeConstant() {
        assertEquals("paragraph", RichBlockParagraph.TYPE);
    }

    @Test
    public void testRichBlockDividerTypeConstant() {
        assertEquals("divider", RichBlockDivider.TYPE);
    }

    @Test
    public void testRichBlockSectionHeadingTypeConstant() {
        assertEquals("heading", RichBlockSectionHeading.TYPE);
    }

    @Test
    public void testRichBlockListTypeConstant() {
        assertEquals("list", RichBlockList.TYPE);
    }

    @Test
    public void testRichBlockParagraphBuilder() {
        RichTextAnchor text = RichTextAnchor.builder().name("section").build();
        RichBlockParagraph paragraph = RichBlockParagraph.builder().text(text).build();

        assertEquals("paragraph", paragraph.getType());
        assertEquals(text, paragraph.getText());
    }

    @Test
    public void testRichBlockDividerBuilder() {
        RichBlockDivider divider = RichBlockDivider.builder().build();

        assertEquals("divider", divider.getType());
    }

    @Test
    public void testRichBlockSectionHeadingBuilder() {
        RichTextAnchor text = RichTextAnchor.builder().name("heading").build();
        RichBlockSectionHeading heading = RichBlockSectionHeading.builder()
                .text(text)
                .size(1)
                .build();

        assertEquals("heading", heading.getType());
        assertEquals(text, heading.getText());
        assertEquals(1, heading.getSize());
    }

    @Test
    public void testRichBlockMathematicalExpressionBuilder() {
        RichBlockMathematicalExpression math = RichBlockMathematicalExpression.builder()
                .expression("E = mc^2")
                .build();

        assertEquals("mathematical_expression", math.getType());
        assertEquals("E = mc^2", math.getExpression());
    }

    @Test
    public void testRichBlockListBuilder() {
        RichTextAnchor text = RichTextAnchor.builder().name("item").build();
        RichBlockParagraph paragraph = RichBlockParagraph.builder().text(text).build();
        RichBlockListItem item = RichBlockListItem.builder()
                .label("1.")
                .blocks(List.of(paragraph))
                .build();
        RichBlockList list = RichBlockList.builder()
                .items(List.of(item))
                .build();

        assertEquals("list", list.getType());
        assertEquals(1, list.getItems().size());
        assertEquals(item, list.getItems().get(0));
    }

    @Test
    public void testRichBlockListItemOptionalFields() {
        RichTextAnchor text = RichTextAnchor.builder().name("item").build();
        RichBlockParagraph paragraph = RichBlockParagraph.builder().text(text).build();
        RichBlockListItem item = RichBlockListItem.builder()
                .label("item1")
                .blocks(List.of(paragraph))
                .build();

        assertNull(item.getHasCheckbox());
        assertNull(item.getIsChecked());
        assertNull(item.getValue());
        assertNull(item.getType());
    }

    @Test
    public void testRichBlockPolymorphicDeserializationParagraph() throws IOException {
        RichTextAnchor text = RichTextAnchor.builder().name("section").build();
        RichBlockParagraph paragraph = RichBlockParagraph.builder().text(text).build();

        String json = mapper.writeValueAsString(paragraph);
        RichBlock deserialized = mapper.readValue(json, RichBlock.class);

        assertInstanceOf(RichBlockParagraph.class, deserialized);
        assertEquals(paragraph, deserialized);
    }

    @Test
    public void testRichBlockPolymorphicDeserializationDivider() throws IOException {
        RichBlockDivider divider = RichBlockDivider.builder().build();

        String json = mapper.writeValueAsString(divider);
        RichBlock deserialized = mapper.readValue(json, RichBlock.class);

        assertInstanceOf(RichBlockDivider.class, deserialized);
        assertEquals(divider, deserialized);
    }

    @Test
    public void testRichBlockPolymorphicDeserializationSectionHeading() throws IOException {
        RichTextAnchor text = RichTextAnchor.builder().name("test").build();
        RichBlockSectionHeading heading = RichBlockSectionHeading.builder()
                .text(text)
                .size(2)
                .build();

        String json = mapper.writeValueAsString(heading);
        RichBlock deserialized = mapper.readValue(json, RichBlock.class);

        assertInstanceOf(RichBlockSectionHeading.class, deserialized);
        assertEquals(heading, deserialized);
    }

    @Test
    public void testRichBlockPolymorphicDeserializationMathematicalExpression() throws IOException {
        RichBlockMathematicalExpression math = RichBlockMathematicalExpression.builder()
                .expression("x^2 + y^2")
                .build();

        String json = mapper.writeValueAsString(math);
        RichBlock deserialized = mapper.readValue(json, RichBlock.class);

        assertInstanceOf(RichBlockMathematicalExpression.class, deserialized);
        assertEquals(math, deserialized);
    }

    @Test
    public void testRichBlockCaption() {
        RichTextAnchor text = RichTextAnchor.builder().name("caption").build();
        RichTextMathematicalExpression credit = RichTextMathematicalExpression.builder()
                .expression("Author")
                .build();
        RichBlockCaption caption = RichBlockCaption.builder()
                .text(text)
                .credit(credit)
                .build();

        assertEquals(text, caption.getText());
        assertEquals(credit, caption.getCredit());
    }

    @Test
    public void testRichBlockCaptionOptionalCredit() {
        RichTextAnchor text = RichTextAnchor.builder().name("caption").build();
        RichBlockCaption caption = RichBlockCaption.builder()
                .text(text)
                .build();

        assertEquals(text, caption.getText());
        assertNull(caption.getCredit());
    }

    // --- Preformatted ---

    @Test
    public void testRichBlockPreformattedRoundTrip() throws IOException {
        RichTextAnchor text = RichTextAnchor.builder().name("test").build();
        RichBlockPreformatted obj = RichBlockPreformatted.builder()
                .text(text)
                .language("java")
                .build();

        String json = mapper.writeValueAsString(obj);
        RichBlock deserialized = mapper.readValue(json, RichBlock.class);

        assertInstanceOf(RichBlockPreformatted.class, deserialized);
        assertEquals(obj, deserialized);
    }

    @Test
    public void testRichBlockPreformattedNoLanguageRoundTrip() throws IOException {
        RichTextAnchor text = RichTextAnchor.builder().name("test").build();
        RichBlockPreformatted obj = RichBlockPreformatted.builder()
                .text(text)
                .build();

        String json = mapper.writeValueAsString(obj);
        RichBlock deserialized = mapper.readValue(json, RichBlock.class);

        assertInstanceOf(RichBlockPreformatted.class, deserialized);
        assertEquals(obj, deserialized);
    }

    // --- Footer ---

    @Test
    public void testRichBlockFooterRoundTrip() throws IOException {
        RichTextAnchor text = RichTextAnchor.builder().name("test").build();
        RichBlockFooter obj = RichBlockFooter.builder().text(text).build();

        String json = mapper.writeValueAsString(obj);
        RichBlock deserialized = mapper.readValue(json, RichBlock.class);

        assertInstanceOf(RichBlockFooter.class, deserialized);
        assertEquals(obj, deserialized);
    }

    // --- Anchor ---

    @Test
    public void testRichBlockAnchorRoundTrip() throws IOException {
        RichBlockAnchor obj = RichBlockAnchor.builder().name("chapter-1").build();

        String json = mapper.writeValueAsString(obj);
        RichBlock deserialized = mapper.readValue(json, RichBlock.class);

        assertInstanceOf(RichBlockAnchor.class, deserialized);
        assertEquals(obj, deserialized);
    }

    // --- List ---

    @Test
    public void testRichBlockListRoundTrip() throws IOException {
        RichTextAnchor text = RichTextAnchor.builder().name("item").build();
        RichBlockParagraph paragraph = RichBlockParagraph.builder().text(text).build();
        RichBlockListItem item = RichBlockListItem.builder()
                .label("1.")
                .blocks(List.of(paragraph))
                .build();
        RichBlockList obj = RichBlockList.builder().items(List.of(item)).build();

        String json = mapper.writeValueAsString(obj);
        RichBlock deserialized = mapper.readValue(json, RichBlock.class);

        assertInstanceOf(RichBlockList.class, deserialized);
        assertEquals(obj, deserialized);
    }

    // --- BlockQuotation ---

    @Test
    public void testRichBlockBlockQuotationRoundTrip() throws IOException {
        RichTextAnchor text = RichTextAnchor.builder().name("test").build();
        RichBlockParagraph inner = RichBlockParagraph.builder().text(text).build();
        RichBlockBlockQuotation obj = RichBlockBlockQuotation.builder()
                .blocks(List.of(inner))
                .build();

        String json = mapper.writeValueAsString(obj);
        RichBlock deserialized = mapper.readValue(json, RichBlock.class);

        assertInstanceOf(RichBlockBlockQuotation.class, deserialized);
        assertEquals(obj, deserialized);
    }

    // --- PullQuotation ---

    @Test
    public void testRichBlockPullQuotationRoundTrip() throws IOException {
        RichTextAnchor text = RichTextAnchor.builder().name("test").build();
        RichBlockPullQuotation obj = RichBlockPullQuotation.builder()
                .text(text)
                .build();

        String json = mapper.writeValueAsString(obj);
        RichBlock deserialized = mapper.readValue(json, RichBlock.class);

        assertInstanceOf(RichBlockPullQuotation.class, deserialized);
        assertEquals(obj, deserialized);
    }

    // --- Collage ---

    @Test
    public void testRichBlockCollageRoundTrip() throws IOException {
        RichTextAnchor text = RichTextAnchor.builder().name("test").build();
        RichBlockParagraph inner = RichBlockParagraph.builder().text(text).build();
        RichBlockCollage obj = RichBlockCollage.builder()
                .blocks(List.of(inner))
                .build();

        String json = mapper.writeValueAsString(obj);
        RichBlock deserialized = mapper.readValue(json, RichBlock.class);

        assertInstanceOf(RichBlockCollage.class, deserialized);
        assertEquals(obj, deserialized);
    }

    // --- Slideshow ---

    @Test
    public void testRichBlockSlideshowRoundTrip() throws IOException {
        RichTextAnchor text = RichTextAnchor.builder().name("test").build();
        RichBlockParagraph inner = RichBlockParagraph.builder().text(text).build();
        RichBlockSlideshow obj = RichBlockSlideshow.builder()
                .blocks(List.of(inner))
                .build();

        String json = mapper.writeValueAsString(obj);
        RichBlock deserialized = mapper.readValue(json, RichBlock.class);

        assertInstanceOf(RichBlockSlideshow.class, deserialized);
        assertEquals(obj, deserialized);
    }

    // --- Table ---

    @Test
    public void testRichBlockTableRoundTrip() throws IOException {
        RichTextAnchor text = RichTextAnchor.builder().name("cell").build();
        RichBlockTableCell cell = RichBlockTableCell.builder().text(text).align("left").valign("top").build();
        RichBlockTable obj = RichBlockTable.builder()
                .cells(List.of(List.of(cell)))
                .build();

        String json = mapper.writeValueAsString(obj);
        RichBlock deserialized = mapper.readValue(json, RichBlock.class);

        assertInstanceOf(RichBlockTable.class, deserialized);
        assertEquals(obj, deserialized);
    }

    // --- Details ---

    @Test
    public void testRichBlockDetailsRoundTrip() throws IOException {
        RichTextAnchor summary = RichTextAnchor.builder().name("summary").build();
        RichTextAnchor innerText = RichTextAnchor.builder().name("inner").build();
        RichBlockParagraph innerBlock = RichBlockParagraph.builder().text(innerText).build();
        RichBlockDetails obj = RichBlockDetails.builder()
                .summary(summary)
                .blocks(List.of(innerBlock))
                .build();

        String json = mapper.writeValueAsString(obj);
        RichBlock deserialized = mapper.readValue(json, RichBlock.class);

        assertInstanceOf(RichBlockDetails.class, deserialized);
        assertEquals(obj, deserialized);
    }

    // --- Map (raw JSON because Location is complex) ---

    @Test
    public void testRichBlockMapPolymorphicDeserialization() throws IOException {
        String json = "{\"type\":\"map\",\"location\":{\"latitude\":41.9,\"longitude\":12.5},\"zoom\":15,\"width\":800,\"height\":600}";
        RichBlock deserialized = mapper.readValue(json, RichBlock.class);

        assertInstanceOf(RichBlockMap.class, deserialized);
        RichBlockMap map = (RichBlockMap) deserialized;
        assertNotNull(map.getLocation());
        assertEquals(15, map.getZoom());
        assertEquals(800, map.getWidth());
        assertEquals(600, map.getHeight());
    }

    // --- Animation (raw JSON because Animation is complex) ---

    @Test
    public void testRichBlockAnimationPolymorphicDeserialization() throws IOException {
        String json = "{\"type\":\"animation\",\"animation\":{\"file_id\":\"abc\",\"file_unique_id\":\"xyz\",\"width\":100,\"height\":100,\"duration\":5}}";
        RichBlock deserialized = mapper.readValue(json, RichBlock.class);

        assertInstanceOf(RichBlockAnimation.class, deserialized);
        RichBlockAnimation animation = (RichBlockAnimation) deserialized;
        assertNotNull(animation.getAnimation());
    }

    // --- Audio (raw JSON because Audio is complex) ---

    @Test
    public void testRichBlockAudioPolymorphicDeserialization() throws IOException {
        String json = "{\"type\":\"audio\",\"audio\":{\"file_id\":\"abc\",\"file_unique_id\":\"xyz\",\"duration\":60}}";
        RichBlock deserialized = mapper.readValue(json, RichBlock.class);

        assertInstanceOf(RichBlockAudio.class, deserialized);
        RichBlockAudio audio = (RichBlockAudio) deserialized;
        assertNotNull(audio.getAudio());
    }

    // --- Photo (raw JSON because PhotoSize list is complex) ---

    @Test
    public void testRichBlockPhotoPolymorphicDeserialization() throws IOException {
        String json = "{\"type\":\"photo\",\"photo\":[{\"file_id\":\"abc\",\"file_unique_id\":\"xyz\",\"width\":100,\"height\":100}]}";
        RichBlock deserialized = mapper.readValue(json, RichBlock.class);

        assertInstanceOf(RichBlockPhoto.class, deserialized);
        RichBlockPhoto photo = (RichBlockPhoto) deserialized;
        assertNotNull(photo.getPhoto());
        assertEquals(1, photo.getPhoto().size());
    }

    // --- Video (raw JSON because Video is complex) ---

    @Test
    public void testRichBlockVideoPolymorphicDeserialization() throws IOException {
        String json = "{\"type\":\"video\",\"video\":{\"file_id\":\"abc\",\"file_unique_id\":\"xyz\",\"width\":100,\"height\":100,\"duration\":5}}";
        RichBlock deserialized = mapper.readValue(json, RichBlock.class);

        assertInstanceOf(RichBlockVideo.class, deserialized);
        RichBlockVideo video = (RichBlockVideo) deserialized;
        assertNotNull(video.getVideo());
    }

    // --- VoiceNote (raw JSON because Voice is complex) ---

    @Test
    public void testRichBlockVoiceNotePolymorphicDeserialization() throws IOException {
        String json = "{\"type\":\"voice_note\",\"voice_note\":{\"file_id\":\"abc\",\"file_unique_id\":\"xyz\",\"duration\":5}}";
        RichBlock deserialized = mapper.readValue(json, RichBlock.class);

        assertInstanceOf(RichBlockVoiceNote.class, deserialized);
        RichBlockVoiceNote voiceNote = (RichBlockVoiceNote) deserialized;
        assertNotNull(voiceNote.getVoiceNote());
    }

    // --- Thinking ---

    @Test
    public void testRichBlockThinkingRoundTrip() throws IOException {
        RichTextAnchor text = RichTextAnchor.builder().name("test").build();
        RichBlockThinking obj = RichBlockThinking.builder().text(text).build();

        String json = mapper.writeValueAsString(obj);
        RichBlock deserialized = mapper.readValue(json, RichBlock.class);

        assertInstanceOf(RichBlockThinking.class, deserialized);
        assertEquals(obj, deserialized);
    }

    // ===========================================================================
    // Plain-string "text" wire form — the root cause of the webhook queue-poisoning
    // bug. Each of the 8 blocks whose text field had @NonNull removed must survive
    // deserialization when Telegram sends a raw JSON string instead of a typed object.
    // ===========================================================================

    @Test
    public void testRichBlockSectionHeadingWithStringText() throws IOException {
        String json = "{\"type\":\"heading\",\"size\":1,\"text\":\"Карусель\"}";
        RichBlock deserialized = mapper.readValue(json, RichBlock.class);

        assertInstanceOf(RichBlockSectionHeading.class, deserialized);
        RichBlockSectionHeading heading = (RichBlockSectionHeading) deserialized;
        assertInstanceOf(org.telegram.telegrambots.meta.api.objects.richtext.RichTextPlain.class, heading.getText());
        assertEquals("Карусель", ((org.telegram.telegrambots.meta.api.objects.richtext.RichTextPlain) heading.getText()).getText());
    }

    @Test
    public void testRichBlockParagraphWithStringText() throws IOException {
        String json = "{\"type\":\"paragraph\",\"text\":\"Hello world\"}";
        RichBlock deserialized = mapper.readValue(json, RichBlock.class);

        assertInstanceOf(RichBlockParagraph.class, deserialized);
        assertInstanceOf(org.telegram.telegrambots.meta.api.objects.richtext.RichTextPlain.class,
                ((RichBlockParagraph) deserialized).getText());
    }

    @Test
    public void testRichBlockFooterWithStringText() throws IOException {
        String json = "{\"type\":\"footer\",\"text\":\"© 2026\"}";
        RichBlock deserialized = mapper.readValue(json, RichBlock.class);

        assertInstanceOf(RichBlockFooter.class, deserialized);
        assertInstanceOf(org.telegram.telegrambots.meta.api.objects.richtext.RichTextPlain.class,
                ((RichBlockFooter) deserialized).getText());
    }

    @Test
    public void testRichBlockPreformattedWithStringText() throws IOException {
        String json = "{\"type\":\"pre\",\"text\":\"System.out.println(\\\"hi\\\");\"}";
        RichBlock deserialized = mapper.readValue(json, RichBlock.class);

        assertInstanceOf(RichBlockPreformatted.class, deserialized);
        assertInstanceOf(org.telegram.telegrambots.meta.api.objects.richtext.RichTextPlain.class,
                ((RichBlockPreformatted) deserialized).getText());
    }

    @Test
    public void testRichBlockPullQuotationWithStringText() throws IOException {
        String json = "{\"type\":\"pullquote\",\"text\":\"To be or not to be\"}";
        RichBlock deserialized = mapper.readValue(json, RichBlock.class);

        assertInstanceOf(RichBlockPullQuotation.class, deserialized);
        assertInstanceOf(org.telegram.telegrambots.meta.api.objects.richtext.RichTextPlain.class,
                ((RichBlockPullQuotation) deserialized).getText());
    }

    @Test
    public void testRichBlockThinkingWithStringText() throws IOException {
        String json = "{\"type\":\"thinking\",\"text\":\"Thinking…\"}";
        RichBlock deserialized = mapper.readValue(json, RichBlock.class);

        assertInstanceOf(RichBlockThinking.class, deserialized);
        assertInstanceOf(org.telegram.telegrambots.meta.api.objects.richtext.RichTextPlain.class,
                ((RichBlockThinking) deserialized).getText());
    }

    @Test
    public void testRichBlockCaptionWithStringText() throws IOException {
        // RichBlockCaption is not a RichBlock subtype, deserialize directly
        String json = "{\"text\":\"Caption text\"}";
        RichBlockCaption deserialized = mapper.readValue(json, RichBlockCaption.class);

        assertInstanceOf(org.telegram.telegrambots.meta.api.objects.richtext.RichTextPlain.class, deserialized.getText());
        assertEquals("Caption text", ((org.telegram.telegrambots.meta.api.objects.richtext.RichTextPlain) deserialized.getText()).getText());
    }

    @Test
    public void testRichBlockDetailsWithStringSummary() throws IOException {
        String json = "{\"type\":\"details\",\"summary\":\"Click to expand\",\"blocks\":[{\"type\":\"divider\"}]}";
        RichBlock deserialized = mapper.readValue(json, RichBlock.class);

        assertInstanceOf(RichBlockDetails.class, deserialized);
        assertInstanceOf(org.telegram.telegrambots.meta.api.objects.richtext.RichTextPlain.class,
                ((RichBlockDetails) deserialized).getSummary());
    }

    // --- Array wire form in block context ---

    @Test
    public void testRichBlockParagraphWithArrayText() throws IOException {
        String json = "{\"type\":\"paragraph\",\"text\":[\"Hello \",{\"type\":\"bold\",\"text\":{\"type\":\"plain\",\"text\":\"world\"}},\"!\"]}";
        RichBlock deserialized = mapper.readValue(json, RichBlock.class);

        assertInstanceOf(RichBlockParagraph.class, deserialized);
        assertInstanceOf(org.telegram.telegrambots.meta.api.objects.richtext.RichTextConcat.class,
                ((RichBlockParagraph) deserialized).getText());
        assertEquals(3, ((org.telegram.telegrambots.meta.api.objects.richtext.RichTextConcat)
                ((RichBlockParagraph) deserialized).getText()).getTexts().size());
    }

    // --- Unknown RichText type → null, no NPE (future-proof defence) ---

    @Test
    public void testRichBlockSectionHeadingWithUnknownRichTextTypeYieldsNullWithoutNpe() throws IOException {
        String json = "{\"type\":\"heading\",\"size\":2,\"text\":{\"type\":\"future_xyz\",\"value\":\"test\"}}";
        RichBlock deserialized = mapper.readValue(json, RichBlock.class);

        assertInstanceOf(RichBlockSectionHeading.class, deserialized);
        // Unknown type → safe null, not an exception
        assertNull(((RichBlockSectionHeading) deserialized).getText());
    }

    @Test
    public void testRichBlockParagraphWithUnknownRichTextTypeYieldsNullWithoutNpe() throws IOException {
        String json = "{\"type\":\"paragraph\",\"text\":{\"type\":\"future_xyz\",\"value\":\"test\"}}";
        RichBlock deserialized = mapper.readValue(json, RichBlock.class);

        assertInstanceOf(RichBlockParagraph.class, deserialized);
        assertNull(((RichBlockParagraph) deserialized).getText());
    }

    // --- Exact webhook scenario from bug report ---

    @Test
    public void testWebhookChannelPostWithHeadingStringAndSlideshow() throws IOException {
        // Reproduces the exact payload that caused queue-poisoning:
        // heading with plain-string text + slideshow with nested photo blocks.
        String json = "{"
                + "\"type\":\"heading\",\"size\":1,"
                + "\"text\":\"Карусель\""
                + "}";
        RichBlock heading = mapper.readValue(json, RichBlock.class);

        assertInstanceOf(RichBlockSectionHeading.class, heading);
        RichBlockSectionHeading h = (RichBlockSectionHeading) heading;
        assertEquals(1, h.getSize());
        assertInstanceOf(org.telegram.telegrambots.meta.api.objects.richtext.RichTextPlain.class, h.getText());
        assertEquals("Карусель", ((org.telegram.telegrambots.meta.api.objects.richtext.RichTextPlain) h.getText()).getText());
    }

    @Test
    public void testFullRichMessageWithHeadingStringAndSlideshowBlocks() throws IOException {
        // Full rich_message structure as received from Telegram in the webhook scenario.
        String json = "{"
                + "\"blocks\":["
                + "{\"type\":\"heading\",\"size\":1,\"text\":\"Карусель\"},"
                + "{\"type\":\"slideshow\",\"blocks\":["
                + "  {\"type\":\"photo\",\"photo\":[{\"file_id\":\"abc\",\"file_unique_id\":\"xyz\",\"width\":800,\"height\":600}]}"
                + "]}"
                + "]}";

        org.telegram.telegrambots.meta.api.objects.richtext.RichMessage msg =
                mapper.readValue(json, org.telegram.telegrambots.meta.api.objects.richtext.RichMessage.class);

        assertNotNull(msg);
        assertEquals(2, msg.getBlocks().size());

        // Heading block: plain-string text must deserialize correctly
        assertInstanceOf(RichBlockSectionHeading.class, msg.getBlocks().get(0));
        RichBlockSectionHeading heading = (RichBlockSectionHeading) msg.getBlocks().get(0);
        assertInstanceOf(org.telegram.telegrambots.meta.api.objects.richtext.RichTextPlain.class, heading.getText());
        assertEquals("Карусель", ((org.telegram.telegrambots.meta.api.objects.richtext.RichTextPlain) heading.getText()).getText());

        // Slideshow block: nested photo blocks parse fine
        assertInstanceOf(RichBlockSlideshow.class, msg.getBlocks().get(1));
        RichBlockSlideshow slideshow = (RichBlockSlideshow) msg.getBlocks().get(1);
        assertEquals(1, slideshow.getBlocks().size());
        assertInstanceOf(RichBlockPhoto.class, slideshow.getBlocks().get(0));
    }

    @Test
    public void testNewTypeConstants() {
        assertEquals("buttons", RichBlockButtons.TYPE);
        assertEquals("expandable_blockquote", RichBlockExpandableBlockQuotation.TYPE);
        assertEquals("document", RichBlockDocument.TYPE);
    }

    @Test
    public void testDeserializeButtonsBlock() throws IOException {
        String json = "{\"type\":\"buttons\",\"align\":\"center\",\"buttons\":"
                + "[{\"text\":\"Yes\",\"callback_data\":\"y\"},{\"text\":\"No\",\"callback_data\":\"n\"}]}";

        RichBlock block = mapper.readValue(json, RichBlock.class);

        assertInstanceOf(RichBlockButtons.class, block);
        RichBlockButtons buttons = (RichBlockButtons) block;
        assertEquals("center", buttons.getAlign());
        assertEquals(2, buttons.getButtons().size());
        assertEquals("y", buttons.getButtons().get(0).getCallbackData());
    }

    @Test
    public void testDeserializeExpandableBlockQuotation() throws IOException {
        String json = "{\"type\":\"expandable_blockquote\",\"text\":\"Quoted\",\"credit\":\"The Author\"}";

        RichBlock block = mapper.readValue(json, RichBlock.class);

        assertInstanceOf(RichBlockExpandableBlockQuotation.class, block);
        RichBlockExpandableBlockQuotation quote = (RichBlockExpandableBlockQuotation) block;
        assertInstanceOf(RichTextPlain.class, quote.getText());
        assertEquals("Quoted", ((RichTextPlain) quote.getText()).getText());
        assertEquals("The Author", ((RichTextPlain) quote.getCredit()).getText());
    }

    @Test
    public void testDeserializeDocumentBlock() throws IOException {
        String json = "{\"type\":\"document\",\"document\":{\"file_id\":\"abc\",\"file_unique_id\":\"u\"}}";

        RichBlock block = mapper.readValue(json, RichBlock.class);

        assertInstanceOf(RichBlockDocument.class, block);
        assertEquals("abc", ((RichBlockDocument) block).getDocument().getFileId());
    }

    @Test
    public void testTableIsCompact() throws IOException {
        String json = "{\"type\":\"table\",\"cells\":[],\"is_compact\":true}";

        RichBlock block = mapper.readValue(json, RichBlock.class);

        assertInstanceOf(RichBlockTable.class, block);
        assertTrue(((RichBlockTable) block).getIsCompact());
    }
}
