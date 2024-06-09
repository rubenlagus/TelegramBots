package org.telegram.telegrambots.meta.api.objects.chat.background.type.fill;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import lombok.extern.jackson.Jacksonized;

import java.util.List;

/**
 * @author Ruben Bermudez
 * @version 7.3
 *
 * The background is a freeform gradient that rotates after every message in the chat.
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
public class BackgroundFillFreeformGradient implements BackgroundFill {
    public static final String FREEFORM_GRADIENT_TYPE = "freeform_gradient";

    private static final String TYPE_FIELD = "type";
    private static final String COLORS_FIELD = "colors";

    /**
     * Type of the background fill, always “freeform_gradient”
     */
    @JsonProperty(TYPE_FIELD)
    private final String type = FREEFORM_GRADIENT_TYPE;
    /**
     * A list of the 3 or 4 base colors that are used to generate the freeform gradient in the RGB24 format
     */
    @JsonProperty(COLORS_FIELD)
    private List<Integer> colors;
}
