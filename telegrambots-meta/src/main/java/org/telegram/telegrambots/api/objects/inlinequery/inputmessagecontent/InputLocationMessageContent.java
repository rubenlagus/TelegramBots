package org.telegram.telegrambots.api.objects.inlinequery.inputmessagecontent;

import com.fasterxml.jackson.annotation.JsonProperty;

import org.telegram.telegrambots.exceptions.TelegramApiValidationException;

import java.util.Objects;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * @author Ruben Bermudez
 * @version 1.0
 * Represents the content of a location message to be sent as the result of an inline query.
 * @note This will only work in Telegram versions released after 9 April, 2016. Older clients will
 * ignore them.
 */
public class InputLocationMessageContent implements InputMessageContent {

    private static final String LATITUDE_FIELD = "latitude";
    private static final String LONGITUDE_FIELD = "longitude";
    private static final String LIVEPERIOD_FIELD = "live_period";

    @JsonProperty(LATITUDE_FIELD)
    private Float latitude; ///< Latitude of the location in degrees
    @JsonProperty(LONGITUDE_FIELD)
    private Float longitude; ///< Longitude of the location in degrees
    @JsonProperty(LIVEPERIOD_FIELD)
    private Integer livePeriod; ///< Optional. Period in seconds for which the location can be updated, should be between 60 and 86400.

    public InputLocationMessageContent() {
        super();
    }

    public InputLocationMessageContent(Float latitude, Float longitude) {
        super();
        this.latitude = checkNotNull(latitude);
        this.longitude = checkNotNull(longitude);
    }

    public Float getLongitude() {
        return longitude;
    }

    public InputLocationMessageContent setLongitude(Float longitude) {
        Objects.requireNonNull(longitude);
        this.longitude = longitude;
        return this;
    }

    public Float getLatitude() {
        return latitude;
    }

    public InputLocationMessageContent setLatitude(Float latitude) {
        Objects.requireNonNull(latitude);
        this.latitude = latitude;
        return this;
    }

    public Integer getLivePeriod() {
        return livePeriod;
    }

    public InputLocationMessageContent setLivePeriod(Integer livePeriod) {
        this.livePeriod = livePeriod;
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
        if (livePeriod != null && (livePeriod < 60 || livePeriod > 86400)) {
            throw new TelegramApiValidationException("Live period parameter must be between 60 and 86400", this);
        }
    }

    @Override
    public String toString() {
        return "InputLocationMessageContent{" +
                "latitude=" + latitude +
                ", longitude=" + longitude +
                ", livePeriod=" + livePeriod +
                '}';
    }
}
