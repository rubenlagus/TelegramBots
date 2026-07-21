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
 * Represents a venue to be sent.
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
public class InputMediaVenue extends InputMedia {
    private static final String TYPE = "venue";

    private static final String LATITUDE_FIELD = "latitude";
    private static final String LONGITUDE_FIELD = "longitude";
    private static final String TITLE_FIELD = "title";
    private static final String ADDRESS_FIELD = "address";
    private static final String FOURSQUARE_ID_FIELD = "foursquare_id";
    private static final String FOURSQUARE_TYPE_FIELD = "foursquare_type";
    private static final String GOOGLE_PLACE_ID_FIELD = "google_place_id";
    private static final String GOOGLE_PLACE_TYPE_FIELD = "google_place_type";

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
     * Name of the venue
     */
    @JsonProperty(TITLE_FIELD)
    @NonNull
    private String title;
    /**
     * Address of the venue
     */
    @JsonProperty(ADDRESS_FIELD)
    @NonNull
    private String address;
    /**
     * Optional. Foursquare identifier of the venue
     */
    @JsonProperty(FOURSQUARE_ID_FIELD)
    private String foursquareId;
    /**
     * Optional. Foursquare type of the venue, if known.
     */
    @JsonProperty(FOURSQUARE_TYPE_FIELD)
    private String foursquareType;
    /**
     * Optional. Google Places identifier of the venue
     */
    @JsonProperty(GOOGLE_PLACE_ID_FIELD)
    private String googlePlaceId;
    /**
     * Optional. Google Places type of the venue.
     */
    @JsonProperty(GOOGLE_PLACE_TYPE_FIELD)
    private String googlePlaceType;

    public InputMediaVenue(Double latitude, Double longitude, String title, String address) {
        super("venue"); // Dummy media string
        this.latitude = latitude;
        this.longitude = longitude;
        this.title = title;
        this.address = address;
    }

    @Override
    public String getType() {
        return TYPE;
    }

    @Override
    public void validate() throws TelegramApiValidationException {
        if (latitude == null) {
            throw new TelegramApiValidationException("Latitude can't be null", this);
        }
        if (longitude == null) {
            throw new TelegramApiValidationException("Longitude can't be null", this);
        }
        if (title == null || title.isEmpty()) {
            throw new TelegramApiValidationException("Title can't be null", this);
        }
        if (address == null || address.isEmpty()) {
            throw new TelegramApiValidationException("Address can't be null", this);
        }
    }
}
