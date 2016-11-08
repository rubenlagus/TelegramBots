package org.telegram.telegrambots.api.objects.inlinequery.inputmessagecontent;

import com.fasterxml.jackson.annotation.JsonProperty;

import org.telegram.telegrambots.exceptions.TelegramApiValidationException;

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
    public void validate() throws TelegramApiValidationException {
        if (latitude == null) {
            throw new TelegramApiValidationException("Latitude parameter can't be empty", this);
        }
        if (longitude == null) {
            throw new TelegramApiValidationException("Longitude parameter can't be empty", this);
        }
    }

    @Override
    public String toString() {
        return "InputLocationMessageContent{" +
                "latitude='" + latitude + '\'' +
                ", longitude='" + longitude + '\'' +
                '}';
    }
}
