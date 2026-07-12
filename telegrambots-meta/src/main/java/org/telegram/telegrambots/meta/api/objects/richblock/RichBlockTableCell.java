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
import org.telegram.telegrambots.meta.api.interfaces.BotApiObject;
import org.telegram.telegrambots.meta.api.objects.richtext.RichText;

/**
 * @author Ruben Bermudez
 * @version 10.1
 * Cell in a table.
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
public class RichBlockTableCell implements BotApiObject {
    private static final String TEXT_FIELD = "text";
    private static final String IS_HEADER_FIELD = "is_header";
    private static final String COLSPAN_FIELD = "colspan";
    private static final String ROWSPAN_FIELD = "rowspan";
    private static final String ALIGN_FIELD = "align";
    private static final String VALIGN_FIELD = "valign";

    /**
     * Optional. Text in the cell. If omitted, then the cell is invisible.
     */
    @JsonProperty(TEXT_FIELD)
    private RichText text;

    /**
     * Optional. True, if the cell is a header cell
     */
    @JsonProperty(IS_HEADER_FIELD)
    private Boolean isHeader;

    /**
     * Optional. The number of columns the cell spans if it is bigger than 1
     */
    @JsonProperty(COLSPAN_FIELD)
    private Integer colspan;

    /**
     * Optional. The number of rows the cell spans if it is bigger than 1
     */
    @JsonProperty(ROWSPAN_FIELD)
    private Integer rowspan;

    /**
     * Horizontal cell content alignment. Currently, must be one of "left", "center", or "right".
     */
    @JsonProperty(ALIGN_FIELD)
    @NonNull
    private String align;

    /**
     * Vertical cell content alignment. Currently, must be one of "top", "middle", or "bottom".
     */
    @JsonProperty(VALIGN_FIELD)
    @NonNull
    private String valign;
}
