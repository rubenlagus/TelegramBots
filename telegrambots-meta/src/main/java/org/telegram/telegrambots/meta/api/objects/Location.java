package org.telegram.telegrambots.meta.api.objects;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;
import org.telegram.telegrambots.meta.api.interfaces.BotApiObject;

/**
 * @author Ruben Bermudez
 * @version 1.0
 * This object represents a point on the map.
 */
@EqualsAndHashCode(callSuper = false)
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Location implements BotApiObject {

    private static final String LONGITUDE_FIELD = "longitude";
    private static final String LATITUDE_FIELD = "latitude";
    private static final String HORIZONTALACCURACY_FIELD = "horizontal_accuracy";
    private static final String LIVEPERIOD_FIELD = "live_period";
    private static final String HEADING_FIELD = "heading";
    private static final String PROXMITYALERTRADIUS_FIELD = "proximity_alert_radius";

    @JsonProperty(LONGITUDE_FIELD)
    @NonNull
    private Double longitude; ///< Longitude as defined by sender
    @JsonProperty(LATITUDE_FIELD)
    @NonNull
    private Double latitude; ///< Latitude as defined by sender
    /**
     * Optional.
     * The radius of uncertainty for the location, measured in meters; 0-1500
     */
    @JsonProperty(HORIZONTALACCURACY_FIELD)
    private Double horizontalAccuracy;
    /**
     * Optional.
     * Time relative to the message sending date, during which the location will be updated, in seconds.
     * For active live locations only.
     */
    @JsonProperty(LIVEPERIOD_FIELD)
    private Integer livePeriod;
    /**
     * Optional.
     * The direction in which user is moving, in degrees; 1-360. For active live locations only.
     */
    @JsonProperty(HEADING_FIELD)
    private Integer heading;
    /**
     * Optional.
     * Maximum distance for proximity alerts about approaching another chat member, in meters.
     * For sent live locations only.
     */
    @JsonProperty(PROXMITYALERTRADIUS_FIELD)
    private Integer proximityAlertRadius;
}
