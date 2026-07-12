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
 * A table, corresponding to the HTML tag &lt;table&gt;.
 */
@EqualsAndHashCode(callSuper = false)
@Getter
@Setter
@ToString
@AllArgsConstructor
@SuperBuilder
@Jacksonized
@JsonIgnoreProperties(ignoreUnknown = true)
public class RichBlockTable implements RichBlock {
    public static final String TYPE = "table";
    private static final String TYPE_FIELD = "type";
    private static final String CELLS_FIELD = "cells";
    private static final String IS_BORDERED_FIELD = "is_bordered";
    private static final String IS_STRIPED_FIELD = "is_striped";
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
     * Optional. True, if the table has borders
     */
    @JsonProperty(IS_BORDERED_FIELD)
    private Boolean isBordered;

    /**
     * Optional. True, if the table is striped
     */
    @JsonProperty(IS_STRIPED_FIELD)
    private Boolean isStriped;

    /**
     * Optional. Caption of the table
     */
    @JsonProperty(CAPTION_FIELD)
    private RichText caption;
}
