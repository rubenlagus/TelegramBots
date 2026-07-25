package org.telegram.telegrambots.meta.api.objects.richblock;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import lombok.extern.jackson.Jacksonized;

import java.util.List;

/**
 * @author Ruben Bermudez
 * @version 10.2
 * A list of blocks, corresponding to the HTML tag &lt;ul&gt; or &lt;ol&gt;
 * with multiple nested tags &lt;li&gt;.
 */
@EqualsAndHashCode(callSuper = false)
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@SuperBuilder
@Jacksonized
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class InputRichBlockList implements InputRichBlock {
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
    private List<InputRichBlockListItem> items;
}
