package org.telegram.telegrambots.meta.api.objects.chat.background.type;

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
import org.telegram.telegrambots.meta.api.objects.Document;

/**
 * @author Ruben Bermudez
 * @version 7.3
 *
 * The background is a wallpaper in the JPEG format.
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
public class BackgroundTypeWallpaper implements BackgroundType {
    public static final String WALLPAPER_TYPE = "wallpaper";

    private static final String TYPE_FIELD = "type";
    private static final String DOCUMENT_FIELD = "document";
    private static final String DARK_THEME_DIMMING_FIELD = "dark_theme_dimming";
    private static final String IS_BLURRED_FIELD = "is_blurred";
    private static final String IS_MOVING_FIELD = "is_moving";

    /**
     * Type of the background, always “wallpaper”
     */
    @JsonProperty(TYPE_FIELD)
    private final String type = WALLPAPER_TYPE;
    /**
     * Document with the wallpaper
     */
    @JsonProperty(DOCUMENT_FIELD)
    private final Document document;
    /**
     * Dimming of the background in dark themes, as a percentage; 0-100
     */
    @JsonProperty(DARK_THEME_DIMMING_FIELD)
    private final Integer darkThemeDimming;
    /**
     * Optional.
     * True, if the wallpaper is downscaled to fit in a 450x450 square and then box-blurred with radius 12
     */
    @JsonProperty(IS_BLURRED_FIELD)
    private Boolean isBlurred;
    /**
     * Optional.
     * True, if the background moves slightly when the device is tilted
     */
    @JsonProperty(IS_MOVING_FIELD)
    private Boolean isMoving;
}
