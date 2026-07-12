package org.telegram.telegrambots.meta.api.objects.richtext;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.telegram.telegrambots.meta.api.objects.message.Message;
import org.telegram.telegrambots.meta.api.objects.richblock.RichBlock;
import org.telegram.telegrambots.meta.api.objects.richblock.RichBlockDivider;
import org.telegram.telegrambots.meta.api.objects.richblock.RichBlockParagraph;

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
public class TestRichMessage {
    private ObjectMapper mapper;

    @BeforeEach
    void setUp() {
        mapper = new ObjectMapper();
    }

    @Test
    public void testRichMessageWithBlocks() {
        RichTextAnchor anchor = RichTextAnchor.builder().name("section1").build();
        RichBlockParagraph paragraph = RichBlockParagraph.builder().text(anchor).build();
        List<RichBlock> blocks = List.of(paragraph);

        RichMessage message = RichMessage.builder()
                .blocks(blocks)
                .build();

        assertEquals(1, message.getBlocks().size());
        assertEquals(paragraph, message.getBlocks().get(0));
    }

    @Test
    public void testRichMessageWithMultipleBlocks() {
        RichTextAnchor anchor = RichTextAnchor.builder().name("test").build();
        RichBlockParagraph paragraph = RichBlockParagraph.builder().text(anchor).build();
        RichBlockDivider divider = RichBlockDivider.builder().build();
        List<RichBlock> blocks = List.of(paragraph, divider);

        RichMessage message = RichMessage.builder()
                .blocks(blocks)
                .build();

        assertEquals(2, message.getBlocks().size());
    }

    @Test
    public void testRichMessageWithIsRtl() {
        RichTextAnchor anchor = RichTextAnchor.builder().name("test").build();
        RichBlockParagraph paragraph = RichBlockParagraph.builder().text(anchor).build();

        RichMessage message = RichMessage.builder()
                .blocks(List.of(paragraph))
                .isRtl(true)
                .build();

        assertTrue(message.getIsRtl());
    }

    @Test
    public void testRichMessageIsRtlNullByDefault() {
        RichTextAnchor anchor = RichTextAnchor.builder().name("test").build();
        RichBlockParagraph paragraph = RichBlockParagraph.builder().text(anchor).build();

        RichMessage message = RichMessage.builder()
                .blocks(List.of(paragraph))
                .build();

        assertNull(message.getIsRtl());
    }

    @Test
    public void testMessageRichMessageDeserialization() throws IOException {
        String json = "{\"message_id\":1,\"date\":1000,\"chat\":{\"id\":123,\"type\":\"private\"},\"from\":{\"id\":456,\"is_bot\":false,\"first_name\":\"Test\"},\"rich_message\":{\"blocks\":[{\"type\":\"paragraph\",\"text\":{\"type\":\"anchor\",\"name\":\"test\"}}]}}";
        Message message = mapper.readValue(json, Message.class);

        assertNotNull(message.getRichMessage());
        assertNotNull(message.getRichMessage().getBlocks());
        assertEquals(1, message.getRichMessage().getBlocks().size());
        assertInstanceOf(RichBlockParagraph.class, message.getRichMessage().getBlocks().get(0));
    }
}
