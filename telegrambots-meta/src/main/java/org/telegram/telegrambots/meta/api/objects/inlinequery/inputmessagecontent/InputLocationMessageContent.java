package org.telegram.telegrambots.meta.api.objects.inlinequery.inputmessagecontent;

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
import org.telegram.telegrambots.meta.exceptions.TelegramApiValidationException;

/**
 * Represents the content of a location message to be sent as the result of an inline query.
 * @author Ruben Bermudez
 * @version 1.0
 * @apiNote This will only work in Telegram versions released after 9 April, 2016. Older clients will
 * ignore them.
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
public class InputLocationMessageContent implements InputMessageContent {

    private static final String LATITUDE_FIELD = "latitude";
    private static final String LONGITUDE_FIELD = "longitude";
    private static final String LIVE_PERIOD_FIELD = "live_period";
    private static final String HORIZONTALACCURACY_FIELD = "horizontal_accuracy";
    private static final String HEADING_FIELD = "heading";
    private static final String PROXIMITYALERTRADIUS_FIELD = "proximity_alert_radius";

    /**
     * Latitude of the location in degrees
     */
    @JsonProperty(LATITUDE_FIELD)
    @NonNull
    private Double latitude;
    /**
     * Longitude of the location in degrees
     */
    @JsonProperty(LONGITUDE_FIELD)
    @NonNull
    private Double longitude;
    /**
     * Optional.
     * Period in seconds for which the location can be updated, should be between 60 and 86400.
     */
    @JsonProperty(LIVE_PERIOD_FIELD)
    private Integer livePeriod;
    /**
     * Optional.
     * Period in seconds during which the location can be updated,
     * should be between 60 and 86400, or 0x7FFFFFFF for live locations that can be edited indefinitely.
     */
    @JsonProperty(HORIZONTALACCURACY_FIELD)
    private Double horizontalAccuracy;
    /**
     * Optional.
     * For live locations, a direction in which the user is moving, in degrees.
     * Must be between 1 and 360 if specified.
     */
    @JsonProperty(HEADING_FIELD)
    private Integer heading;
    /**
     * Optional.
     * For live locations, a maximum distance for proximity alerts about approaching another chat member, in meters.
     * Must be between 1 and 100000 if specified.
     */
    @JsonProperty(PROXIMITYALERTRADIUS_FIELD)
    private Integer proximityAlertRadius;

    @Override
    public void validate() throws TelegramApiValidationException {
        if (livePeriod != null && (livePeriod < 60 || livePeriod > 86400) && livePeriod != 0x7FFFFFFF) {
            throw new TelegramApiValidationException("Live period parameter must be between 60 and 86400 or be 0x7FFFFFFF", this);
        }
        if (horizontalAccuracy != null && (horizontalAccuracy < 0 || horizontalAccuracy > 1500)) {
            throw new TelegramApiValidationException("Horizontal Accuracy parameter must be between 0 and 1500", this);
        }
        if (heading != null && (heading < 1 || heading > 360)) {
            throw new TelegramApiValidationException("Heading Accuracy parameter must be between 1 and 360", this);
        }
        if (proximityAlertRadius != null && (proximityAlertRadius < 1 || proximityAlertRadius > 100000)) {
            throw new TelegramApiValidationException("Approaching notification distance parameter must be between 1 and 100000", this);
        }
    }
}
