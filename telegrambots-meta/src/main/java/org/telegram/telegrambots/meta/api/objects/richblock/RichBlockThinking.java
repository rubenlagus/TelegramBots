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
import org.telegram.telegrambots.meta.api.objects.richtext.RichText;

/**
 * @author Ruben Bermudez
 * @version 10.1
 * A block with a "Thinking..." placeholder, corresponding to the custom HTML tag &lt;tg-thinking&gt;.
 * The block may be used only in sendRichMessageDraft.
 */
@EqualsAndHashCode(callSuper = false)
@Getter
@Setter
@ToString
@AllArgsConstructor
@SuperBuilder
@Jacksonized
@JsonIgnoreProperties(ignoreUnknown = true)
public class RichBlockThinking implements RichBlock {
    public static final String TYPE = "thinking";
    private static final String TYPE_FIELD = "type";
    private static final String TEXT_FIELD = "text";

    /**
     * Type of the block, always "thinking"
     */
    @JsonProperty(TYPE_FIELD)
    private final String type = TYPE;

    /**
     * Text of the block
     */
    @JsonProperty(TEXT_FIELD)
    @NonNull
    private RichText text;
}
