package org.telegram.telegrambots.meta.api.objects.gifts;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import lombok.extern.jackson.Jacksonized;
import org.telegram.telegrambots.meta.api.interfaces.BotApiObject;

/**
 * @author Ruben Bermudez
 * @version 9.3
 * This object describes the background of a gift.
 */
@EqualsAndHashCode(callSuper = false)
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Jacksonized
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class GiftBackground implements BotApiObject {
    private static final String CENTER_COLOR_FIELD = "center_color";
    private static final String EDGE_COLOR_FIELD = "edge_color";
    private static final String TEXT_COLOR_FIELD = "text_color";

    /**
     * Center color of the background in RGB format
     */
    @JsonProperty(CENTER_COLOR_FIELD)
    @NonNull
    private Integer centerColor;
    /**
     * Edge color of the background in RGB format
     */
    @JsonProperty(EDGE_COLOR_FIELD)
    @NonNull
    private Integer edgeColor;
    /**
     * Text color of the background in RGB format
     */
    @JsonProperty(TEXT_COLOR_FIELD)
    @NonNull
    private Integer textColor;
}
