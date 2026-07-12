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

/**
 * @author Ruben Bermudez
 * @version 10.1
 * A block with an anchor, corresponding to the HTML tag &lt;a&gt; with the attribute name.
 */
@EqualsAndHashCode(callSuper = false)
@Getter
@Setter
@ToString
@AllArgsConstructor
@SuperBuilder
@Jacksonized
@JsonIgnoreProperties(ignoreUnknown = true)
public class RichBlockAnchor implements RichBlock {
    public static final String TYPE = "anchor";
    private static final String TYPE_FIELD = "type";
    private static final String NAME_FIELD = "name";

    /**
     * Type of the block, always "anchor"
     */
    @JsonProperty(TYPE_FIELD)
    private final String type = TYPE;

    /**
     * The name of the anchor
     */
    @JsonProperty(NAME_FIELD)
    @NonNull
    private String name;
}
