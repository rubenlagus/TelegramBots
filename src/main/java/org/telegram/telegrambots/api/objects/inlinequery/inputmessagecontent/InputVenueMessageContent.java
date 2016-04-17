package org.telegram.telegrambots.api.objects.inlinequery.inputmessagecontent;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.jsontype.TypeSerializer;

import org.json.JSONObject;

import java.io.IOException;

/**
 * @author Ruben Bermudez
 * @version 1.0
 * @brief Represents the content of a venue message to be sent as the result of an inline query.
 * @note This will only work in Telegram versions released after 9 April, 2016. Older clients will
 * ignore them.
 * @date 10 of April of 2016
 */
public class InputVenueMessageContent implements InputMessageContent {

    private static final String LATITUDE_FIELD = "latitude";
    private static final String LONGITUDE_FIELD = "longitude";
    private static final String TITLE_FIELD = "title";
    private static final String ADDRESS_FIELD = "address";
    private static final String FOURSQUARE_ID_FIELD = "foursquare_id";
    @JsonProperty(LATITUDE_FIELD)
    private Float latitude; ///< Latitude of the venue in degrees
    @JsonProperty(LONGITUDE_FIELD)
    private Float longitude; ///< Longitude of the venue in degrees
    @JsonProperty(TITLE_FIELD)
    private String title; ///< Name of the venue
    @JsonProperty(ADDRESS_FIELD)
    private String address; ///< Address of the venue
    @JsonProperty(FOURSQUARE_ID_FIELD)
    private String foursquareId; ///< Optional. Foursquare identifier of the venue, if known

    public InputVenueMessageContent() {
        super();
    }

    public Float getLatitude() {
        return latitude;
    }

    public InputVenueMessageContent setLatitude(Float latitude) {
        this.latitude = latitude;
        return this;
    }

    public Float getLongitude() {
        return longitude;
    }

    public InputVenueMessageContent setLongitude(Float longitude) {
        this.longitude = longitude;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public InputVenueMessageContent setTitle(String title) {
        this.title = title;
        return this;
    }

    public String getAddress() {
        return address;
    }

    public InputVenueMessageContent setAddress(String address) {
        this.address = address;
        return this;
    }

    public String getFoursquareId() {
        return foursquareId;
    }

    public InputVenueMessageContent setFoursquareId(String foursquareId) {
        this.foursquareId = foursquareId;
        return this;
    }

    @Override
    public JSONObject toJson() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put(LATITUDE_FIELD, latitude);
        jsonObject.put(LONGITUDE_FIELD, longitude);
        jsonObject.put(TITLE_FIELD, title);
        jsonObject.put(ADDRESS_FIELD, address);
        if (foursquareId != null) {
            jsonObject.put(FOURSQUARE_ID_FIELD, foursquareId);
        }
        return jsonObject;
    }

    @Override
    public void serialize(JsonGenerator gen, SerializerProvider serializers) throws IOException {
        gen.writeStartObject();
        gen.writeNumberField(LATITUDE_FIELD, latitude);
        gen.writeNumberField(LONGITUDE_FIELD, longitude);
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
        return "InputVenueMessageContent{" +
                "latitude='" + latitude + '\'' +
                ", longitude='" + longitude + '\'' +
                ", title='" + title + '\'' +
                ", address='" + address + '\'' +
                ", foursquareId='" + foursquareId + '\'' +
                '}';
    }
}
