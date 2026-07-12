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

import java.util.List;

/**
 * @author Ruben Bermudez
 * @version 10.1
 * A block quotation, corresponding to the HTML tag &lt;blockquote&gt;.
 */
@EqualsAndHashCode(callSuper = false)
@Getter
@Setter
@ToString
@AllArgsConstructor
@SuperBuilder
@Jacksonized
@JsonIgnoreProperties(ignoreUnknown = true)
public class RichBlockBlockQuotation implements RichBlock {
    public static final String TYPE = "blockquote";
    private static final String TYPE_FIELD = "type";
    private static final String BLOCKS_FIELD = "blocks";
    private static final String CREDIT_FIELD = "credit";

    /**
     * Type of the block, always "blockquote"
     */
    @JsonProperty(TYPE_FIELD)
    private final String type = TYPE;

    /**
     * Content of the block
     */
    @JsonProperty(BLOCKS_FIELD)
    @NonNull
    private List<RichBlock> blocks;

    /**
     * Optional. Credit of the block
     */
    @JsonProperty(CREDIT_FIELD)
    private RichText credit;
}
