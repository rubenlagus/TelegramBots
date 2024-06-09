package org.telegram.telegrambots.meta.api.objects.chat.background.type;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import lombok.extern.jackson.Jacksonized;
import org.telegram.telegrambots.meta.api.objects.chat.background.type.fill.BackgroundFill;

/**
 * @author Ruben Bermudez
 * @version 7.3
 *
 * The background is automatically filled based on the selected colors.
 */
@EqualsAndHashCode(callSuper = false)
@Getter
@Setter
@ToString
@AllArgsConstructor
@SuperBuilder
@Jacksonized
@JsonIgnoreProperties(ignoreUnknown = true)
public class BackgroundTypeFill implements BackgroundType {
    public static final String FILL_TYPE = "fill";

    private static final String TYPE_FIELD = "type";
    private static final String FILL_FIELD = "fill";
    private static final String DARK_THEME_DIMMING_FIELD = "dark_theme_dimming";

    /**
     * Type of the background, always “fill”
     */
    @JsonProperty(TYPE_FIELD)
    private final String type = FILL_TYPE;
    /**
     * The background fill
     */
    @JsonProperty(FILL_FIELD)
    private final BackgroundFill fill;
    /**
     * Dimming of the background in dark themes, as a percentage; 0-100
     */
    @JsonProperty(DARK_THEME_DIMMING_FIELD)
    private final Integer darkThemeDimming;
}
