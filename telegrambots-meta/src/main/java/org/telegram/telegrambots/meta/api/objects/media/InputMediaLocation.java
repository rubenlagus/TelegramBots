package org.telegram.telegrambots.meta.api.objects.media;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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
import org.telegram.telegrambots.meta.exceptions.TelegramApiValidationException;

/**
 * @author Ruben Bermudez
 * @version 10.0
 *
 * Represents a location to be sent.
 */
@EqualsAndHashCode(callSuper = true)
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Jacksonized
@JsonIgnoreProperties(ignoreUnknown = true)
public class InputMediaLocation extends InputMedia {
    private static final String TYPE = "location";

    private static final String LATITUDE_FIELD = "latitude";
    private static final String LONGITUDE_FIELD = "longitude";
    private static final String HORIZONTAL_ACCURACY_FIELD = "horizontal_accuracy";

    /**
     * Latitude of the location
     */
    @JsonProperty(LATITUDE_FIELD)
    @NonNull
    private Double latitude;
    /**
     * Longitude of the location
     */
    @JsonProperty(LONGITUDE_FIELD)
    @NonNull
    private Double longitude;
    /**
     * Optional. The radius of uncertainty for the location, measured in meters; 0-1500
     */
    @JsonProperty(HORIZONTAL_ACCURACY_FIELD)
    private Double horizontalAccuracy;

    public InputMediaLocation(Double latitude, Double longitude) {
        super("location"); // Dummy media string to satisfy InputMedia
        this.latitude = latitude;
        this.longitude = longitude;
    }

    @Override
    public String getType() {
        return TYPE;
    }

    // Override setMedia as it is not needed for location but InputMedia requires it in some constructors/logic
    // Actually InputMedia requires media field to be non-null in validate() if we call super.validate()
    // But location doesn't have media.
    
    @Override
    public void validate() throws TelegramApiValidationException {
        // No media field validation as it is not used for location
        if (latitude == null) {
            throw new TelegramApiValidationException("Latitude can't be null", this);
        }
        if (longitude == null) {
            throw new TelegramApiValidationException("Longitude can't be null", this);
        }
    }
}
