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

import java.util.List;

/**
 * @author Ruben Bermudez
 * @version 10.1
 * A list of blocks, corresponding to the HTML tag &lt;ul&gt; or &lt;ol&gt; with multiple nested tags &lt;li&gt;.
 */
@EqualsAndHashCode(callSuper = false)
@Getter
@Setter
@ToString
@AllArgsConstructor
@SuperBuilder
@Jacksonized
@JsonIgnoreProperties(ignoreUnknown = true)
public class RichBlockList implements RichBlock {
    public static final String TYPE = "list";
    private static final String TYPE_FIELD = "type";
    private static final String ITEMS_FIELD = "items";

    /**
     * Type of the block, always "list"
     */
    @JsonProperty(TYPE_FIELD)
    private final String type = TYPE;

    /**
     * Items of the list
     */
    @JsonProperty(ITEMS_FIELD)
    @NonNull
    private List<RichBlockListItem> items;
}
