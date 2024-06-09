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
 * The background is filled using the selected color.
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
public class BackgroundFillSolid implements BackgroundFill {
    public static final String SOLID_TYPE = "solid";

    private static final String TYPE_FIELD = "type";
    private static final String COLOR_FIELD = "color";

    /**
     * Type of the background fill, always “solid”
     */
    @JsonProperty(TYPE_FIELD)
    private final String type = SOLID_TYPE;
    /**
     * The color of the background fill in the RGB24 format
     */
    @JsonProperty(COLOR_FIELD)
    private Integer color;
}
