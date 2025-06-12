package org.telegram.telegrambots.meta.api.objects.location;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.telegram.telegrambots.meta.api.interfaces.BotApiObject;
import org.telegram.telegrambots.meta.api.interfaces.Validable;
import org.telegram.telegrambots.meta.exceptions.TelegramApiValidationException;

/**
 * @author Ruben Bermudez
 * @version 9.0
 *
 * Describes the physical address of a location.
 */
@EqualsAndHashCode(callSuper = false)
@Getter
@Setter
@ToString
@NoArgsConstructor
@RequiredArgsConstructor
@AllArgsConstructor
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class LocationAddress implements BotApiObject, Validable {
    private static final String COUNTRY_CODE_FIELD = "country_code";
    private static final String STATE_FIELD = "state";
    private static final String CITY_FIELD = "city";
    private static final String STREET_FIELD = "street";

    /**
     * The two-letter ISO 3166-1 alpha-2 country code of the country where the location is located
     */
    @JsonProperty(COUNTRY_CODE_FIELD)
    @NonNull
    private String countryCode;

    /**
     * Optional. State of the location
     */
    @JsonProperty(STATE_FIELD)
    private String state;

    /**
     * Optional. City of the location
     */
    @JsonProperty(CITY_FIELD)
    private String city;

    /**
     * Optional. Street address of the location
     */
    @JsonProperty(STREET_FIELD)
    private String street;

    @Override
    public void validate() throws TelegramApiValidationException {
        if (countryCode.isEmpty()) {
            throw new TelegramApiValidationException("Country code parameter can't be empty", this);
        }

        if (countryCode.length() != 2) {
            throw new TelegramApiValidationException("Country code must be a two-letter ISO 3166-1 alpha-2 code", this);
        }
    }
}
