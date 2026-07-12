package org.telegram.telegrambots.meta.api.objects.richtext;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import lombok.extern.jackson.Jacksonized;

/**
 * @author Ruben Bermudez
 * @version 10.1
 * A link to an anchor.
 */
@EqualsAndHashCode(callSuper = false)
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@SuperBuilder
@Jacksonized
@JsonIgnoreProperties(ignoreUnknown = true)
public class RichTextAnchorLink implements RichText {
    public static final String TYPE = "anchor_link";
    private static final String TYPE_FIELD = "type";
    private static final String TEXT_FIELD = "text";
    private static final String ANCHOR_NAME_FIELD = "anchor_name";

    /**
     * Type of the rich text, always "anchor_link"
     */
    @JsonProperty(TYPE_FIELD)
    private final String type = TYPE;

    /**
     * The link text
     */
    @JsonProperty(TEXT_FIELD)
    @NonNull
    private RichText text;

    /**
     * The name of the anchor. If the name is empty, then the link brings back to the top of the message.
     */
    @JsonProperty(ANCHOR_NAME_FIELD)
    @NonNull
    private String anchorName;
}
