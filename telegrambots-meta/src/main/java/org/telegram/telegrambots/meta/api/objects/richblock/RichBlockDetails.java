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

import java.util.List;

/**
 * @author Ruben Bermudez
 * @version 10.1
 * An expandable block for details disclosure, corresponding to the HTML tag &lt;details&gt;.
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
public class RichBlockDetails implements RichBlock {
    public static final String TYPE = "details";
    private static final String TYPE_FIELD = "type";
    private static final String SUMMARY_FIELD = "summary";
    private static final String BLOCKS_FIELD = "blocks";
    private static final String IS_OPEN_FIELD = "is_open";

    /**
     * Type of the block, always "details"
     */
    @JsonProperty(TYPE_FIELD)
    private final String type = TYPE;

    /**
     * Always shown summary of the block
     */
    @JsonProperty(SUMMARY_FIELD)
    @NonNull
    private RichText summary;

    /**
     * Content of the block
     */
    @JsonProperty(BLOCKS_FIELD)
    @NonNull
    private List<RichBlock> blocks;

    /**
     * Optional. True, if the content of the block is visible by default
     */
    @JsonProperty(IS_OPEN_FIELD)
    private Boolean isOpen;
}
