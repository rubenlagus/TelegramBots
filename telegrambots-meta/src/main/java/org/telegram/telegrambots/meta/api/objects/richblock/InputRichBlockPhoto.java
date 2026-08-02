package org.telegram.telegrambots.meta.api.objects.richblock;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import lombok.extern.jackson.Jacksonized;
import org.telegram.telegrambots.meta.api.objects.media.InputMediaPhoto;

/**
 * @author Ruben Bermudez
 * @version 10.2
 * A block with a photo, corresponding to the HTML tag &lt;img&gt;.
 */
@EqualsAndHashCode(callSuper = false)
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Jacksonized
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class InputRichBlockPhoto implements InputRichBlock {
    public static final String TYPE = "photo";
    private static final String TYPE_FIELD = "type";
    private static final String PHOTO_FIELD = "photo";
    private static final String CAPTION_FIELD = "caption";

    /**
     * Type of the block, always "photo"
     */
    @JsonProperty(TYPE_FIELD)
    private final String type = TYPE;

    /**
     * The photo. Caption is ignored.
     */
    @JsonProperty(PHOTO_FIELD)
    @NonNull
    private InputMediaPhoto photo;

    /**
     * Optional. Caption of the block
     */
    @JsonProperty(CAPTION_FIELD)
    private RichBlockCaption caption;
}
