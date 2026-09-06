package org.telegram.telegrambots.meta.api.objects.richblock;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.telegram.telegrambots.meta.api.objects.location.Location;
import org.telegram.telegrambots.meta.api.objects.LoginUrl;
import org.telegram.telegrambots.meta.api.objects.media.InputMediaDocument;
import org.telegram.telegrambots.meta.api.objects.media.InputMediaPhoto;
import org.telegram.telegrambots.meta.api.objects.media.InputMediaVoiceNote;
import org.telegram.telegrambots.meta.api.objects.richtext.InputRichMessage;
import org.telegram.telegrambots.meta.api.objects.richtext.RichMessageButton;
import org.telegram.telegrambots.meta.api.objects.richtext.RichTextPlain;
import org.telegram.telegrambots.meta.exceptions.TelegramApiValidationException;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * @author Ruben Bermudez
 * @version 10.2
 */
public class TestInputRichBlock {
    private ObjectMapper mapper;

    @BeforeEach
    void setUp() {
        mapper = new ObjectMapper();
    }

    @Test
    public void testTypeConstants() {
        assertEquals("paragraph", InputRichBlockParagraph.TYPE);
        assertEquals("heading", InputRichBlockSectionHeading.TYPE);
        assertEquals("pre", InputRichBlockPreformatted.TYPE);
        assertEquals("footer", InputRichBlockFooter.TYPE);
        assertEquals("divider", InputRichBlockDivider.TYPE);
        assertEquals("mathematical_expression", InputRichBlockMathematicalExpression.TYPE);
        assertEquals("anchor", InputRichBlockAnchor.TYPE);
        assertEquals("list", InputRichBlockList.TYPE);
        assertEquals("blockquote", InputRichBlockBlockQuotation.TYPE);
        assertEquals("pullquote", InputRichBlockPullQuotation.TYPE);
        assertEquals("collage", InputRichBlockCollage.TYPE);
        assertEquals("slideshow", InputRichBlockSlideshow.TYPE);
        assertEquals("table", InputRichBlockTable.TYPE);
        assertEquals("details", InputRichBlockDetails.TYPE);
        assertEquals("map", InputRichBlockMap.TYPE);
        assertEquals("animation", InputRichBlockAnimation.TYPE);
        assertEquals("audio", InputRichBlockAudio.TYPE);
        assertEquals("photo", InputRichBlockPhoto.TYPE);
        assertEquals("video", InputRichBlockVideo.TYPE);
        assertEquals("voice_note", InputRichBlockVoiceNote.TYPE);
        assertEquals("thinking", InputRichBlockThinking.TYPE);
    }

    @Test
    public void testSerializeParagraphEmitsType() throws IOException {
        InputRichBlockParagraph paragraph = InputRichBlockParagraph.builder()
                .text(new RichTextPlain("Hello"))
                .build();

        String json = mapper.writeValueAsString(paragraph);

        assertTrue(json.contains("\"type\":\"paragraph\""), json);
        assertTrue(json.contains("Hello"), json);
    }

    @Test
    public void testOptionalFieldsAreOmitted() throws IOException {
        InputRichBlockPreformatted pre = InputRichBlockPreformatted.builder()
                .text(new RichTextPlain("code"))
                .build();

        String json = mapper.writeValueAsString(pre);

        assertNull(pre.getLanguage());
        assertTrue(json.contains("\"type\":\"pre\""), json);
        assertEquals(-1, json.indexOf("language"), json);
    }

    @Test
    public void testDeserializePolymorphicBlockByType() throws IOException {
        String json = "{\"type\":\"heading\",\"text\":\"Title\",\"size\":2}";

        InputRichBlock block = mapper.readValue(json, InputRichBlock.class);

        InputRichBlockSectionHeading heading = assertInstanceOf(InputRichBlockSectionHeading.class, block);
        assertEquals("heading", heading.getType());
        assertEquals(2, heading.getSize());
        assertInstanceOf(RichTextPlain.class, heading.getText());
    }

    @Test
    public void testRoundTripListOfMixedBlocks() throws IOException {
        List<InputRichBlock> blocks = List.of(
                InputRichBlockParagraph.builder().text(new RichTextPlain("Intro")).build(),
                InputRichBlockDivider.builder().build(),
                InputRichBlockAnchor.builder().name("section-1").build(),
                InputRichBlockMathematicalExpression.builder().expression("e^{i\\pi}+1=0").build()
        );

        String json = mapper.writeValueAsString(blocks);
        List<InputRichBlock> parsed = mapper.readValue(json,
                mapper.getTypeFactory().constructCollectionType(List.class, InputRichBlock.class));

        assertEquals(4, parsed.size());
        assertInstanceOf(InputRichBlockParagraph.class, parsed.get(0));
        assertInstanceOf(InputRichBlockDivider.class, parsed.get(1));
        assertInstanceOf(InputRichBlockAnchor.class, parsed.get(2));
        assertInstanceOf(InputRichBlockMathematicalExpression.class, parsed.get(3));
        assertEquals("section-1", ((InputRichBlockAnchor) parsed.get(2)).getName());
        assertEquals("e^{i\\pi}+1=0", ((InputRichBlockMathematicalExpression) parsed.get(3)).getExpression());
    }

    @Test
    public void testNestedBlocksRoundTrip() throws IOException {
        InputRichBlockDetails details = InputRichBlockDetails.builder()
                .summary(new RichTextPlain("More"))
                .blocks(List.of(InputRichBlockParagraph.builder().text(new RichTextPlain("Body")).build()))
                .isOpen(true)
                .build();

        String json = mapper.writeValueAsString(details);
        InputRichBlock parsed = mapper.readValue(json, InputRichBlock.class);

        InputRichBlockDetails result = assertInstanceOf(InputRichBlockDetails.class, parsed);
        assertEquals(1, result.getBlocks().size());
        assertInstanceOf(InputRichBlockParagraph.class, result.getBlocks().get(0));
        assertEquals(true, result.getIsOpen());
    }

    @Test
    public void testListItemRoundTrip() throws IOException {
        InputRichBlockList list = InputRichBlockList.builder()
                .items(List.of(InputRichBlockListItem.builder()
                        .blocks(List.of(InputRichBlockParagraph.builder().text(new RichTextPlain("Item")).build()))
                        .hasCheckbox(true)
                        .isChecked(false)
                        .value(1)
                        .type("1")
                        .build()))
                .build();

        String json = mapper.writeValueAsString(list);
        InputRichBlock parsed = mapper.readValue(json, InputRichBlock.class);

        InputRichBlockList result = assertInstanceOf(InputRichBlockList.class, parsed);
        assertEquals(1, result.getItems().size());
        assertEquals(1, result.getItems().get(0).getValue());
        assertEquals(true, result.getItems().get(0).getHasCheckbox());
    }

    @Test
    public void testMapBlockRoundTrip() throws IOException {
        InputRichBlockMap map = InputRichBlockMap.builder()
                .location(Location.builder().latitude(1.0).longitude(2.0).build())
                .zoom(12)
                .width(600)
                .height(400)
                .build();

        String json = mapper.writeValueAsString(map);
        InputRichBlockMap parsed = assertInstanceOf(InputRichBlockMap.class,
                mapper.readValue(json, InputRichBlock.class));

        assertEquals(12, parsed.getZoom());
        assertEquals(600, parsed.getWidth());
        assertEquals(400, parsed.getHeight());
        assertEquals(1.0, parsed.getLocation().getLatitude());
    }

    @Test
    public void testMediaBlocksRoundTrip() throws IOException {
        InputRichBlockPhoto photo = InputRichBlockPhoto.builder()
                .photo(new InputMediaPhoto("photoFileId"))
                .build();
        InputRichBlockVoiceNote voiceNote = InputRichBlockVoiceNote.builder()
                .voiceNote(new InputMediaVoiceNote("voiceFileId"))
                .build();

        String json = mapper.writeValueAsString(List.of(photo, voiceNote));
        List<InputRichBlock> parsed = mapper.readValue(json,
                mapper.getTypeFactory().constructCollectionType(List.class, InputRichBlock.class));

        assertEquals("photoFileId",
                assertInstanceOf(InputRichBlockPhoto.class, parsed.get(0)).getPhoto().getMedia());
        assertEquals("voiceFileId",
                assertInstanceOf(InputRichBlockVoiceNote.class, parsed.get(1)).getVoiceNote().getMedia());
    }

    @Test
    public void testInputMediaVoiceNoteTypeAndDuration() throws IOException {
        InputMediaVoiceNote voiceNote = InputMediaVoiceNote.builder()
                .media("fileId")
                .duration(42)
                .build();

        assertEquals("voice_note", voiceNote.getType());

        String json = mapper.writeValueAsString(voiceNote);
        assertTrue(json.contains("\"type\":\"voice_note\""), json);
        assertTrue(json.contains("\"duration\":42"), json);
    }

    @Test
    public void testNewTypeConstants() {
        assertEquals("buttons", InputRichBlockButtons.TYPE);
        assertEquals("expandable_blockquote", InputRichBlockExpandableBlockQuotation.TYPE);
        assertEquals("document", InputRichBlockDocument.TYPE);
    }

    @Test
    public void testSerializeButtonsBlock() throws IOException {
        InputRichBlockButtons block = InputRichBlockButtons.builder()
                .buttons(List.of(RichMessageButton.builder()
                        .text(new RichTextPlain("Yes"))
                        .callbackData("y")
                        .build()))
                .align("left")
                .build();

        String json = mapper.writeValueAsString(block);

        assertTrue(json.contains("\"type\":\"buttons\""), json);
        assertTrue(json.contains("\"align\":\"left\""), json);
        assertTrue(json.contains("\"callback_data\":\"y\""), json);
    }

    @Test
    public void testSerializeExpandableBlockQuotation() throws IOException {
        InputRichBlockExpandableBlockQuotation block = InputRichBlockExpandableBlockQuotation.builder()
                .text(new RichTextPlain("Quoted"))
                .build();

        String json = mapper.writeValueAsString(block);

        assertTrue(json.contains("\"type\":\"expandable_blockquote\""), json);
        assertFalse(json.contains("credit"), json);
    }

    @Test
    public void testSerializeDocumentBlock() throws IOException {
        InputRichBlockDocument block = InputRichBlockDocument.builder()
                .document(new InputMediaDocument("file-id"))
                .build();

        String json = mapper.writeValueAsString(block);

        assertTrue(json.contains("\"type\":\"document\""), json);
        assertFalse(json.contains("caption"), json);
    }

    @Test
    public void testInputTableIsCompactOmittedWhenUnset() throws IOException {
        InputRichBlockTable table = InputRichBlockTable.builder()
                .cells(List.of())
                .build();

        assertFalse(mapper.writeValueAsString(table).contains("is_compact"));
    }

    /**
     * InputRichBlockTable is an outbound object, so emitting is_compact is the direction that
     * reaches Telegram. The omitted-when-unset case above cannot show that it serializes at all.
     */
    @Test
    public void testInputTableIsCompactSerializesAndRoundTrips() throws IOException {
        InputRichBlockTable table = InputRichBlockTable.builder()
                .cells(List.of())
                .isCompact(true)
                .build();

        String json = mapper.writeValueAsString(table);

        assertTrue(json.contains("\"is_compact\":true"), json);

        InputRichBlockTable parsed = mapper.readValue(json, InputRichBlockTable.class);
        assertTrue(parsed.getIsCompact());
    }

    @Test
    public void testInputRichMessageValidatesNestedButtons() {
        InputRichMessage message = InputRichMessage.builder()
                .blocks(List.of(InputRichBlockButtons.builder()
                        .buttons(List.of(RichMessageButton.builder()
                                .text(new RichTextPlain("x"))
                                .loginUrl(new LoginUrl(""))   // invalid: empty url
                                .build()))
                        .build()))
                .build();

        assertThrows(TelegramApiValidationException.class, message::validate);
    }
}
