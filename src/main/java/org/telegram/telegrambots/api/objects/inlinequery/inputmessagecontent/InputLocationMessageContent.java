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
 * @brief Represents the content of a location message to be sent as the result of an inline query.
 * @note This will only work in Telegram versions released after 9 April, 2016. Older clients will
 * ignore them.
 * @date 10 of April of 2016
 */
public class InputLocationMessageContent implements InputMessageContent {

    private static final String LATITUDE_FIELD = "latitude";
    private static final String LONGITUDE_FIELD = "longitude";
    @JsonProperty(LATITUDE_FIELD)
    private Float latitude; ///< Latitude of the location in degrees
    @JsonProperty(LONGITUDE_FIELD)
    private Float longitude; ///< Longitude of the location in degrees

    public InputLocationMessageContent() {
        super();
    }

    public Float getLongitude() {
        return longitude;
    }

    public InputLocationMessageContent setLongitude(Float longitude) {
        this.longitude = longitude;
        return this;
    }

    public Float getLatitude() {
        return latitude;
    }

    public InputLocationMessageContent setLatitude(Float latitude) {
        this.latitude = latitude;
        return this;
    }

    @Override
    public JSONObject toJson() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put(LATITUDE_FIELD, latitude);
        jsonObject.put(LONGITUDE_FIELD, longitude);
        return jsonObject;
    }

    @Override
    public void serialize(JsonGenerator gen, SerializerProvider serializers) throws IOException {
        gen.writeStartObject();
        gen.writeNumberField(LATITUDE_FIELD, latitude);
        gen.writeNumberField(LONGITUDE_FIELD, longitude);
        gen.writeEndObject();
        gen.flush();
    }

    @Override
    public void serializeWithType(JsonGenerator gen, SerializerProvider serializers, TypeSerializer typeSer) throws IOException {
        serialize(gen, serializers);
    }

    @Override
    public String toString() {
        return "InputLocationMessageContent{" +
                "latitude='" + latitude + '\'' +
                ", longitude='" + longitude + '\'' +
                '}';
    }
}
