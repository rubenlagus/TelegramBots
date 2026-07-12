package org.telegram.telegrambots.meta.api.objects.richblock;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import lombok.extern.jackson.Jacksonized;
import org.telegram.telegrambots.meta.api.objects.Video;

/**
 * @author Ruben Bermudez
 * @version 10.1
 * A block with a video, corresponding to the HTML tag &lt;video&gt;.
 */
@EqualsAndHashCode(callSuper = false)
@Getter
@Setter
@ToString
@AllArgsConstructor
@SuperBuilder
@Jacksonized
@JsonIgnoreProperties(ignoreUnknown = true)
public class RichBlockVideo implements RichBlock {
    public static final String TYPE = "video";
    private static final String TYPE_FIELD = "type";
    private static final String VIDEO_FIELD = "video";
    private static final String HAS_SPOILER_FIELD = "has_spoiler";
    private static final String CAPTION_FIELD = "caption";

    /**
     * Type of the block, always "video"
     */
    @JsonProperty(TYPE_FIELD)
    private final String type = TYPE;

    /**
     * The video
     */
    @JsonProperty(VIDEO_FIELD)
    @NonNull
    private Video video;

    /**
     * Optional. True, if the media preview is covered by a spoiler animation
     */
    @JsonProperty(HAS_SPOILER_FIELD)
    private Boolean hasSpoiler;

    /**
     * Optional. Caption of the block
     */
    @JsonProperty(CAPTION_FIELD)
    private RichBlockCaption caption;
}
