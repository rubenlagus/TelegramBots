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

/**
 * @author Ruben Bermudez
 * @version 7.3
 *
 * The background is a gradient fill.
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
public class BackgroundFillGradient implements BackgroundFill {
    public static final String GRADIENT_TYPE = "gradient";

    private static final String TYPE_FIELD = "type";
    private static final String TOP_COLOR_FIELD = "top_color";
    private static final String BOTTOM_COLOR_FIELD = "bottom_color";
    private static final String ROTATION_ANGLE_FIELD = "rotation_angle";

    /**
     * Type of the background fill, always “gradient”
     */
    @JsonProperty(TYPE_FIELD)
    private final String type = GRADIENT_TYPE;
    /**
     * Top color of the gradient in the RGB24 format
     */
    @JsonProperty(TOP_COLOR_FIELD)
    private Integer topColor;
    /**
     * Bottom color of the gradient in the RGB24 format
     */
    @JsonProperty(BOTTOM_COLOR_FIELD)
    private Integer bottomColor;
    /**
     * Clockwise rotation angle of the background fill in degrees; 0-359
     */
    @JsonProperty(ROTATION_ANGLE_FIELD)
    private Integer rotationAngle;
}
