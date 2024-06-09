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
import org.telegram.telegrambots.meta.api.objects.chat.background.type.fill.BackgroundFill;

/**
 * @author Ruben Bermudez
 * @version 7.3
 *
 * The background is a PNG or TGV (gzipped subset of SVG with MIME type “application/x-tgwallpattern”)
 * pattern to be combined with the background fill chosen by the user.
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
public class BackgroundTypePattern implements BackgroundType {
    public static final String PATTERN_TYPE = "pattern";

    private static final String TYPE_FIELD = "type";
    private static final String DOCUMENT_FIELD = "document";
    private static final String FILL_FIELD = "fill";
    private static final String INTENSITY_FIELD = "intensity";
    private static final String IS_INVERTED_FIELD = "is_inverted";
    private static final String IS_MOVING_FIELD = "is_moving";

    /**
     * Type of the background, always “pattern”
     */
    @JsonProperty(TYPE_FIELD)
    private final String type = PATTERN_TYPE;
    /**
     * Document with the pattern
     */
    @JsonProperty(DOCUMENT_FIELD)
    private final Document document;
    /**
     * The background fill that is combined with the pattern
     */
    @JsonProperty(FILL_FIELD)
    private final BackgroundFill fill;
    /**
     * Intensity of the pattern when it is shown above the filled background; 0-100
     */
    @JsonProperty(INTENSITY_FIELD)
    private final Integer intensity;
    /**
     * Optional.
     * True, if the background fill must be applied only to the pattern itself.
     * All other pixels are black in this case.
     * For dark themes only
     */
    @JsonProperty(IS_INVERTED_FIELD)
    private Boolean isInverted;
    /**
     * Optional.
     * True, if the background moves slightly when the device is tilted
     */
    @JsonProperty(IS_MOVING_FIELD)
    private Boolean isMoving;
}
