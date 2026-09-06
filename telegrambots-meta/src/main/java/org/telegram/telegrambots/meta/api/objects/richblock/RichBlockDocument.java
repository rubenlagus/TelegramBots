package org.telegram.telegrambots.meta.api.objects.richblock;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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
import org.telegram.telegrambots.meta.api.objects.Document;

/**
 * @author Ruben Bermudez
 * @version 10.3
 * A block with a general file, corresponding to the custom HTML tag &lt;tg-document&gt;.
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
public class RichBlockDocument implements RichBlock {
    public static final String TYPE = "document";
    private static final String TYPE_FIELD = "type";
    private static final String DOCUMENT_FIELD = "document";
    private static final String CAPTION_FIELD = "caption";

    /**
     * Type of the block, always "document"
     */
    @JsonProperty(TYPE_FIELD)
    private final String type = TYPE;

    /**
     * The document
     */
    @JsonProperty(DOCUMENT_FIELD)
    @NonNull
    private Document document;

    /**
     * Optional. Caption of the block
     */
    @JsonProperty(CAPTION_FIELD)
    private RichBlockCaption caption;
}
