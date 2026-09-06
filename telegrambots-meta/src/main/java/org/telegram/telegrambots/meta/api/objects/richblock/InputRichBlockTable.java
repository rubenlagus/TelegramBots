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
import org.telegram.telegrambots.meta.api.objects.richtext.RichText;

import java.util.List;

/**
 * @author Ruben Bermudez
 * @version 10.2
 * A table, corresponding to the HTML tag &lt;table&gt;.
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
public class InputRichBlockTable implements InputRichBlock {
    public static final String TYPE = "table";
    private static final String TYPE_FIELD = "type";
    private static final String CELLS_FIELD = "cells";
    private static final String IS_BORDERED_FIELD = "is_bordered";
    private static final String IS_STRIPED_FIELD = "is_striped";
    private static final String IS_COMPACT_FIELD = "is_compact";
    private static final String CAPTION_FIELD = "caption";

    /**
     * Type of the block, always "table"
     */
    @JsonProperty(TYPE_FIELD)
    private final String type = TYPE;

    /**
     * Cells of the table
     */
    @JsonProperty(CELLS_FIELD)
    @NonNull
    private List<List<RichBlockTableCell>> cells;

    /**
     * Optional. Pass True if the table has borders
     */
    @JsonProperty(IS_BORDERED_FIELD)
    private Boolean isBordered;

    /**
     * Optional. Pass True if the table is striped
     */
    @JsonProperty(IS_STRIPED_FIELD)
    private Boolean isStriped;
    /**
     * Optional. Pass True if table cells must have smaller indents
     */
    @JsonProperty(IS_COMPACT_FIELD)
    private Boolean isCompact;

    /**
     * Optional. Caption of the table
     */
    @JsonProperty(CAPTION_FIELD)
    private RichText caption;
}
