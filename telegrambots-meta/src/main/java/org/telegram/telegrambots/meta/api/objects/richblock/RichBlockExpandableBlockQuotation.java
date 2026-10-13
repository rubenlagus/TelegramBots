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
import org.telegram.telegrambots.meta.api.objects.richtext.RichText;

/**
 * @author Ruben Bermudez
 * @version 10.3
 * A block quotation, corresponding to the HTML tag &lt;blockquote&gt;
 * with custom attribute "collapsed".
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
public class RichBlockExpandableBlockQuotation implements RichBlock {
    public static final String TYPE = "expandable_blockquote";
    private static final String TYPE_FIELD = "type";
    private static final String TEXT_FIELD = "text";
    private static final String CREDIT_FIELD = "credit";

    /**
     * Type of the block, always "expandable_blockquote"
     */
    @JsonProperty(TYPE_FIELD)
    private final String type = TYPE;

    /**
     * Content of the block
     */
    @JsonProperty(TEXT_FIELD)
    @NonNull
    private RichText text;

    /**
     * Optional. Credit of the block
     */
    @JsonProperty(CREDIT_FIELD)
    private RichText credit;
}
