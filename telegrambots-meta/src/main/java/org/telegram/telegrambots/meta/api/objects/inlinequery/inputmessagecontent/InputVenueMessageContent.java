package org.telegram.telegrambots.meta.api.objects.inlinequery.inputmessagecontent;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.telegram.telegrambots.meta.exceptions.TelegramApiValidationException;

/**
 * Represents the content of a venue message to be sent as the result of an inline query.
 * @author Ruben Bermudez
 * @version 1.0
 * @apiNote  This will only work in Telegram versions released after 9 April, 2016. Older clients will
 * ignore them.
 */
@JsonDeserialize
@EqualsAndHashCode(callSuper = false)
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@NoArgsConstructor(force = true)
@AllArgsConstructor
@Builder
public class InputVenueMessageContent implements InputMessageContent {

    private static final String LATITUDE_FIELD = "latitude";
    private static final String LONGITUDE_FIELD = "longitude";
    private static final String TITLE_FIELD = "title";
    private static final String ADDRESS_FIELD = "address";
    private static final String FOURSQUAREID_FIELD = "foursquare_id";
    private static final String FOURSQUARETYPE_FIELD = "foursquare_type";
    private static final String GOOGLEPLACEID_FIELD = "google_place_id";
    private static final String GOOGLEPLACETYPE_FIELD = "google_place_type";

    /**
     * Latitude of the venue in degrees
     */
    @JsonProperty(LATITUDE_FIELD)
    @NonNull
    private Float latitude;
    /**
     * Longitude of the venue in degrees
     */
    @JsonProperty(LONGITUDE_FIELD)
    @NonNull
    private Float longitude;
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
     * Optional. Foursquare identifier of the venue, if known
     */
    @JsonProperty(FOURSQUAREID_FIELD)
    private String foursquareId;
    /**
     * Optional. Foursquare type of the venue, if known.
     */
    @JsonProperty(FOURSQUARETYPE_FIELD)
    private String foursquareType;
    /**
     * Optional. Google Places identifier of the venue
     */
    @JsonProperty(GOOGLEPLACEID_FIELD)
    private String googlePlaceId;
    /**
     * Optional. Google Places type of the venue. (See supported types.)
     */
    @JsonProperty(GOOGLEPLACETYPE_FIELD)
    private String googlePlaceType;

    @Override
    public void validate() throws TelegramApiValidationException {
        if (latitude == null) {
            throw new TelegramApiValidationException("Latitude parameter can't be empty", this);
        }
        if (longitude == null) {
            throw new TelegramApiValidationException("Longitude parameter can't be empty", this);
        }
        if (title == null || title.isEmpty()) {
            throw new TelegramApiValidationException("Title parameter can't be empty", this);
        }
        if (address == null || address.isEmpty()) {
            throw new TelegramApiValidationException("Address parameter can't be empty", this);
        }
    }
}
