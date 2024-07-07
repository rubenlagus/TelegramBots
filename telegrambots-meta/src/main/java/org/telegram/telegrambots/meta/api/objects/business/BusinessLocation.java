package org.telegram.telegrambots.meta.api.objects.business;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import lombok.extern.jackson.Jacksonized;
import org.telegram.telegrambots.meta.api.interfaces.BotApiObject;
import org.telegram.telegrambots.meta.api.objects.Location;

/**
 * @author Ruben Bermudez
 * @version 7.2
 * Contains information about the location of a Telegram Business account.
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
public class BusinessLocation implements BotApiObject {
    private static final String ADDRESS_FIELD = "address";
    private static final String LOCATION_FIELD = "location";

    /**
     * Address of the business
     */
    @JsonProperty(ADDRESS_FIELD)
    @NonNull
    private String address;
    /**
     * Optional.
     * Location of the business
     */
    @JsonProperty(LOCATION_FIELD)
    private Location location;
}
