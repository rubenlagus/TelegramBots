package org.telegram.telegrambots.meta.api.objects.richblock;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import lombok.extern.jackson.Jacksonized;
import org.telegram.telegrambots.meta.api.objects.location.Location;

/**
 * @author Ruben Bermudez
 * @version 10.1
 * A block with a map, corresponding to the custom HTML tag &lt;tg-map&gt;.
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
public class RichBlockMap implements RichBlock {
    public static final String TYPE = "map";
    private static final String TYPE_FIELD = "type";
    private static final String LOCATION_FIELD = "location";
    private static final String ZOOM_FIELD = "zoom";
    private static final String WIDTH_FIELD = "width";
    private static final String HEIGHT_FIELD = "height";
    private static final String CAPTION_FIELD = "caption";

    /**
     * Type of the block, always "map"
     */
    @JsonProperty(TYPE_FIELD)
    private final String type = TYPE;

    /**
     * Location of the center of the map
     */
    @JsonProperty(LOCATION_FIELD)
    @NonNull
    private Location location;

    /**
     * Map zoom level; 13-20
     */
    @JsonProperty(ZOOM_FIELD)
    @NonNull
    private Integer zoom;

    /**
     * Expected width of the map
     */
    @JsonProperty(WIDTH_FIELD)
    @NonNull
    private Integer width;

    /**
     * Expected height of the map
     */
    @JsonProperty(HEIGHT_FIELD)
    @NonNull
    private Integer height;

    /**
     * Optional. Caption of the block
     */
    @JsonProperty(CAPTION_FIELD)
    private RichBlockCaption caption;
}
