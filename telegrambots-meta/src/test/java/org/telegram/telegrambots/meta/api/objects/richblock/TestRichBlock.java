package org.telegram.telegrambots.meta.api.objects.richblock;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.telegram.telegrambots.meta.api.objects.richtext.RichTextAnchor;
import org.telegram.telegrambots.meta.api.objects.richtext.RichTextMathematicalExpression;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

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
}
