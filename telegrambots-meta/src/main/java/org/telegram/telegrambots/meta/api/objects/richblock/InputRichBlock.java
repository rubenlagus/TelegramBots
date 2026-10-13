package org.telegram.telegrambots.meta.api.objects.richblock;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import org.telegram.telegrambots.meta.api.interfaces.BotApiObject;
import org.telegram.telegrambots.meta.api.interfaces.Validable;

/**
 * @author Ruben Bermudez
 * @version 10.3
 * This object represents a block in a rich formatted message to be sent.
 */
@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.EXISTING_PROPERTY,
        property = "type",
        defaultImpl = Void.class
)
@JsonSubTypes({
        @JsonSubTypes.Type(value = InputRichBlockParagraph.class, name = "paragraph"),
        @JsonSubTypes.Type(value = InputRichBlockSectionHeading.class, name = "heading"),
        @JsonSubTypes.Type(value = InputRichBlockPreformatted.class, name = "pre"),
        @JsonSubTypes.Type(value = InputRichBlockFooter.class, name = "footer"),
        @JsonSubTypes.Type(value = InputRichBlockDivider.class, name = "divider"),
        @JsonSubTypes.Type(value = InputRichBlockMathematicalExpression.class, name = "mathematical_expression"),
        @JsonSubTypes.Type(value = InputRichBlockAnchor.class, name = "anchor"),
        @JsonSubTypes.Type(value = InputRichBlockList.class, name = "list"),
        @JsonSubTypes.Type(value = InputRichBlockBlockQuotation.class, name = "blockquote"),
        @JsonSubTypes.Type(value = InputRichBlockPullQuotation.class, name = "pullquote"),
        @JsonSubTypes.Type(value = InputRichBlockCollage.class, name = "collage"),
        @JsonSubTypes.Type(value = InputRichBlockSlideshow.class, name = "slideshow"),
        @JsonSubTypes.Type(value = InputRichBlockTable.class, name = "table"),
        @JsonSubTypes.Type(value = InputRichBlockDetails.class, name = "details"),
        @JsonSubTypes.Type(value = InputRichBlockMap.class, name = "map"),
        @JsonSubTypes.Type(value = InputRichBlockAnimation.class, name = "animation"),
        @JsonSubTypes.Type(value = InputRichBlockAudio.class, name = "audio"),
        @JsonSubTypes.Type(value = InputRichBlockPhoto.class, name = "photo"),
        @JsonSubTypes.Type(value = InputRichBlockVideo.class, name = "video"),
        @JsonSubTypes.Type(value = InputRichBlockVoiceNote.class, name = "voice_note"),
        @JsonSubTypes.Type(value = InputRichBlockThinking.class, name = "thinking"),
        @JsonSubTypes.Type(value = InputRichBlockExpandableBlockQuotation.class, name = "expandable_blockquote"),
        @JsonSubTypes.Type(value = InputRichBlockDocument.class, name = "document"),
        @JsonSubTypes.Type(value = InputRichBlockButtons.class, name = "buttons")
})
public interface InputRichBlock extends BotApiObject, Validable {
    String getType();
}
