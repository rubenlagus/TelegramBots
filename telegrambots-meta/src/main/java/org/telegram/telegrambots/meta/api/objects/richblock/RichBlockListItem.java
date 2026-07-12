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

import java.util.List;

/**
 * @author Ruben Bermudez
 * @version 10.1
 * An item of a list.
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
public class RichBlockListItem implements BotApiObject {
    private static final String LABEL_FIELD = "label";
    private static final String BLOCKS_FIELD = "blocks";
    private static final String HAS_CHECKBOX_FIELD = "has_checkbox";
    private static final String IS_CHECKED_FIELD = "is_checked";
    private static final String VALUE_FIELD = "value";
    private static final String TYPE_FIELD = "type";

    /**
     * Label of the item
     */
    @JsonProperty(LABEL_FIELD)
    @NonNull
    private String label;

    /**
     * The content of the item
     */
    @JsonProperty(BLOCKS_FIELD)
    @NonNull
    private List<RichBlock> blocks;

    /**
     * Optional. True, if the item has a checkbox
     */
    @JsonProperty(HAS_CHECKBOX_FIELD)
    private Boolean hasCheckbox;

    /**
     * Optional. True, if the item has a checked checkbox
     */
    @JsonProperty(IS_CHECKED_FIELD)
    private Boolean isChecked;

    /**
     * Optional. For ordered lists, the numeric value of the item label
     */
    @JsonProperty(VALUE_FIELD)
    private Integer value;

    /**
     * Optional. For ordered lists, the type of the item label;
     * must be one of "a", "A", "i", "I", or "1"
     */
    @JsonProperty(TYPE_FIELD)
    private String type;
}
