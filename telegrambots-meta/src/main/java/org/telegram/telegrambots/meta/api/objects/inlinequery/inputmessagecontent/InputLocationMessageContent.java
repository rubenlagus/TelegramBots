package org.telegram.telegrambots.meta.api.objects.inlinequery.inputmessagecontent;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.telegram.telegrambots.meta.exceptions.TelegramApiValidationException;

/**
 * @author Ruben Bermudez
 * @version 1.0
 * Represents the content of a location message to be sent as the result of an inline query.
 * @apiNote This will only work in Telegram versions released after 9 April, 2016. Older clients will
 * ignore them.
 */
@JsonDeserialize
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InputLocationMessageContent implements InputMessageContent {

    private static final String LATITUDE_FIELD = "latitude";
    private static final String LONGITUDE_FIELD = "longitude";
    private static final String LIVEPERIOD_FIELD = "live_period";

    @JsonProperty(LATITUDE_FIELD)
    @NonNull
    private Float latitude; ///< Latitude of the location in degrees
    @JsonProperty(LONGITUDE_FIELD)
    @NonNull
    private Float longitude; ///< Longitude of the location in degrees
    @JsonProperty(LIVEPERIOD_FIELD)
    private Integer livePeriod; ///< Optional. Period in seconds for which the location can be updated, should be between 60 and 86400.

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
}
