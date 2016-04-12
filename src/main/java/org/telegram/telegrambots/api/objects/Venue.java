package org.telegram.telegrambots.api.objects;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.jsontype.TypeSerializer;

import org.json.JSONObject;
import org.telegram.telegrambots.api.interfaces.IBotApiObject;

import java.io.IOException;

/**
 * @author Ruben Bermudez
 * @version 1.0
 * @brief This object represents a venue.
 * @date 10 of April of 2016
 */
public class Venue implements IBotApiObject {
    private static final String LOCATION_FIELD = "location";
    private static final String TITLE_FIELD = "title";
    private static final String ADDRESS_FIELD = "address";
    private static final String FOURSQUARE_ID_FIELD = "foursquare_id";

    @JsonProperty(LOCATION_FIELD)
    private Location location; ///< Venue location
    @JsonProperty(TITLE_FIELD)
    private String title; ///< Name of the venue
    @JsonProperty(ADDRESS_FIELD)
    private String address; ///< Address of the venue
    @JsonProperty(FOURSQUARE_ID_FIELD)
    private String foursquareId; ///< Optional. Foursquare identifier of the venue


    public Venue() {
        super();
    }

    public Venue(JSONObject jsonObject) {
        super();
        location = new Location(jsonObject.getJSONObject(LOCATION_FIELD));
        title = jsonObject.getString(TITLE_FIELD);
        address = jsonObject.getString(ADDRESS_FIELD);
        if (jsonObject.has(FOURSQUARE_ID_FIELD)) {
            foursquareId = jsonObject.getString(FOURSQUARE_ID_FIELD);
        }
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

    @Override
    public void serialize(JsonGenerator gen, SerializerProvider serializers) throws IOException {
        gen.writeStartObject();
        gen.writeObjectField(LOCATION_FIELD, location);
        gen.writeStringField(TITLE_FIELD, title);
        gen.writeStringField(ADDRESS_FIELD, address);
        if (foursquareId != null) {
            gen.writeStringField(FOURSQUARE_ID_FIELD, foursquareId);
        }
        gen.writeEndObject();
        gen.flush();
    }

    @Override
    public void serializeWithType(JsonGenerator gen, SerializerProvider serializers, TypeSerializer typeSer) throws IOException {
        serialize(gen, serializers);
    }

    @Override
    public String toString() {
        return "Venue{" +
                "location=" + location +
                ", title=" + title +
                ", address=" + address +
                ", foursquareId=" + foursquareId +
                '}';
    }
}
