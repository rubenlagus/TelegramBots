package org.telegram.telegrambots.meta.api.objects;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import lombok.extern.jackson.Jacksonized;
import org.telegram.telegrambots.meta.api.interfaces.BotApiObject;

/**
 * This object represents a venue.
 * @author Ruben Bermudez
 * @version 1.0
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
public class Venue implements BotApiObject {
    private static final String LOCATION_FIELD = "location";
    private static final String TITLE_FIELD = "title";
    private static final String ADDRESS_FIELD = "address";
    private static final String FOURSQUAREID_FIELD = "foursquare_id";
    private static final String FOURSQUARETYPE_FIELD = "foursquare_type";
    private static final String GOOGLEPLACEID_FIELD = "google_place_id";
    private static final String GOOGLEPLACETYPE_FIELD = "google_place_type";

    /**
     * Venue location
     */
    @JsonProperty(LOCATION_FIELD)
    private Location location;
    /**
     * Name of the venue
     */
    @JsonProperty(TITLE_FIELD)
    private String title;
    /**
     * Address of the venue
     */
    @JsonProperty(ADDRESS_FIELD)
    private String address;
    /**
     * Optional.
     * Foursquare identifier of the venue
     */
    @JsonProperty(FOURSQUAREID_FIELD)
    private String foursquareId;
    /**
     * Optional.
     * Foursquare type of the venue.
     */
    @JsonProperty(FOURSQUARETYPE_FIELD)
    private String foursquareType;
    /**
     * Optional.
     * Google Places identifier of the venue
     */
    @JsonProperty(GOOGLEPLACEID_FIELD)
    private String googlePlaceId;
    /**
     * Optional. 
     * Google Places type of the venue. (See supported types.)
     */
    @JsonProperty(GOOGLEPLACETYPE_FIELD)
    private String googlePlaceType;
}
