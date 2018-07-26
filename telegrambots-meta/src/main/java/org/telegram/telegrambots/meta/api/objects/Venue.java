package org.telegram.telegrambots.meta.api.objects;

import com.fasterxml.jackson.annotation.JsonProperty;

import org.telegram.telegrambots.meta.api.interfaces.BotApiObject;

/**
 * @author Ruben Bermudez
 * @version 1.0
 * This object represents a venue.
 */
public class Venue implements BotApiObject {
    private static final String LOCATION_FIELD = "location";
    private static final String TITLE_FIELD = "title";
    private static final String ADDRESS_FIELD = "address";
    private static final String FOURSQUAREID_FIELD = "foursquare_id";
    private static final String FOURSQUARETYPE_FIELD = "foursquare_type";

    @JsonProperty(LOCATION_FIELD)
    private Location location; ///< Venue location
    @JsonProperty(TITLE_FIELD)
    private String title; ///< Name of the venue
    @JsonProperty(ADDRESS_FIELD)
    private String address; ///< Address of the venue
    @JsonProperty(FOURSQUAREID_FIELD)
    private String foursquareId; ///< Optional. Foursquare identifier of the venue
    @JsonProperty(FOURSQUARETYPE_FIELD)
    private String foursquareType; ///< Optional. Foursquare type of the venue.

    public Venue() {
        super();
    }

    public Location getLocation() {
        return location;
    }

    public String getTitle() {
        return title;
    }

    public String getAddress() {
        return address;
    }

    public String getFoursquareId() {
        return foursquareId;
    }

    public String getFoursquareType() {
        return foursquareType;
    }

    @Override
    public String toString() {
        return "Venue{" +
                "location=" + location +
                ", title='" + title + '\'' +
                ", address='" + address + '\'' +
                ", foursquareId='" + foursquareId + '\'' +
                ", foursquareType='" + foursquareType + '\'' +
                '}';
    }
}
