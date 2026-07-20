package org.telegram.telegrambots.meta.api.objects.richblock;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import lombok.extern.jackson.Jacksonized;

/**
 * @author Ruben Bermudez
 * @version 10.1
 * A divider, corresponding to the HTML tag &lt;hr/&gt;.
 */
@EqualsAndHashCode(callSuper = false)
@Getter
@Setter
@ToString
@NoArgsConstructor
@SuperBuilder
@Jacksonized
@JsonIgnoreProperties(ignoreUnknown = true)
public class RichBlockDivider implements RichBlock {
    public static final String TYPE = "divider";
    private static final String TYPE_FIELD = "type";

    /**
     * Type of the block, always "divider"
     */
    @JsonProperty(TYPE_FIELD)
    private final String type = TYPE;
}
