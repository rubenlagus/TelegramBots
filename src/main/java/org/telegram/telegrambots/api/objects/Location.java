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
 * @brief This object represents a point on the map.
 * @date 20 of June of 2015
 */
public class Location implements IBotApiObject {

    private static final String LONGITUDE_FIELD = "longitude";
    private static final String LATITUDE_FIELD = "latitude";
    @JsonProperty(LONGITUDE_FIELD)
    private Double longitude; ///< Longitude as defined by sender
    @JsonProperty(LATITUDE_FIELD)
    private Double latitude; ///< Latitude as defined by sender

    public Location() {
        super();
    }

    public Location(JSONObject jsonObject) {
        super();
        this.longitude = jsonObject.getDouble(LONGITUDE_FIELD);
        this.latitude = jsonObject.getDouble(LATITUDE_FIELD);
    }

    public Double getLongitude() {
        return longitude;
    }

    public Double getLatitude() {
        return latitude;
    }

    @Override
    public void serialize(JsonGenerator gen, SerializerProvider serializers) throws IOException {
        gen.writeStartObject();
        gen.writeNumberField(LONGITUDE_FIELD, longitude);
        gen.writeNumberField(LATITUDE_FIELD, latitude);
        gen.writeEndObject();
        gen.flush();
    }

    @Override
    public void serializeWithType(JsonGenerator gen, SerializerProvider serializers, TypeSerializer typeSer) throws IOException {
        serialize(gen, serializers);
    }

    @Override
    public String toString() {
        return "Location{" +
                "longitude=" + longitude +
                ", latitude=" + latitude +
                '}';
    }
}
