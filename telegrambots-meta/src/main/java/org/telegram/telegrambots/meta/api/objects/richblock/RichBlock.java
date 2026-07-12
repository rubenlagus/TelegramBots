package org.telegram.telegrambots.meta.api.objects.richblock;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import org.telegram.telegrambots.meta.api.interfaces.BotApiObject;

/**
 * @author Ruben Bermudez
 * @version 10.1
 * This object represents a block in a rich formatted message.
 */
@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.EXISTING_PROPERTY,
        property = "type",
        defaultImpl = Void.class
)
@JsonSubTypes({
        @JsonSubTypes.Type(value = RichBlockParagraph.class, name = "paragraph"),
        @JsonSubTypes.Type(value = RichBlockSectionHeading.class, name = "heading"),
        @JsonSubTypes.Type(value = RichBlockPreformatted.class, name = "pre"),
        @JsonSubTypes.Type(value = RichBlockFooter.class, name = "footer"),
        @JsonSubTypes.Type(value = RichBlockDivider.class, name = "divider"),
        @JsonSubTypes.Type(value = RichBlockMathematicalExpression.class, name = "mathematical_expression"),
        @JsonSubTypes.Type(value = RichBlockAnchor.class, name = "anchor"),
        @JsonSubTypes.Type(value = RichBlockList.class, name = "list"),
        @JsonSubTypes.Type(value = RichBlockBlockQuotation.class, name = "blockquote"),
        @JsonSubTypes.Type(value = RichBlockPullQuotation.class, name = "pullquote"),
        @JsonSubTypes.Type(value = RichBlockCollage.class, name = "collage"),
        @JsonSubTypes.Type(value = RichBlockSlideshow.class, name = "slideshow"),
        @JsonSubTypes.Type(value = RichBlockTable.class, name = "table"),
        @JsonSubTypes.Type(value = RichBlockDetails.class, name = "details"),
        @JsonSubTypes.Type(value = RichBlockMap.class, name = "map"),
        @JsonSubTypes.Type(value = RichBlockAnimation.class, name = "animation"),
        @JsonSubTypes.Type(value = RichBlockAudio.class, name = "audio"),
        @JsonSubTypes.Type(value = RichBlockPhoto.class, name = "photo"),
        @JsonSubTypes.Type(value = RichBlockVideo.class, name = "video"),
        @JsonSubTypes.Type(value = RichBlockVoiceNote.class, name = "voice_note"),
        @JsonSubTypes.Type(value = RichBlockThinking.class, name = "thinking")
})
public interface RichBlock extends BotApiObject {
    String getType();
}
