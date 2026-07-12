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

import java.util.List;

/**
 * @author Ruben Bermudez
 * @version 10.1
 * A collage, corresponding to the custom HTML tag &lt;tg-collage&gt;.
 */
@EqualsAndHashCode(callSuper = false)
@Getter
@Setter
@ToString
@AllArgsConstructor
@SuperBuilder
@Jacksonized
@JsonIgnoreProperties(ignoreUnknown = true)
public class RichBlockCollage implements RichBlock {
    public static final String TYPE = "collage";
    private static final String TYPE_FIELD = "type";
    private static final String BLOCKS_FIELD = "blocks";
    private static final String CAPTION_FIELD = "caption";

    /**
     * Type of the block, always "collage"
     */
    @JsonProperty(TYPE_FIELD)
    private final String type = TYPE;

    /**
     * Elements of the collage
     */
    @JsonProperty(BLOCKS_FIELD)
    @NonNull
    private List<RichBlock> blocks;

    /**
     * Optional. Caption of the block
     */
    @JsonProperty(CAPTION_FIELD)
    private RichBlockCaption caption;
}
