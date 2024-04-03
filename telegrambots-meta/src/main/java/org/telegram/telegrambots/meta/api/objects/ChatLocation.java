package org.telegram.telegrambots.meta.api.objects;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import lombok.extern.jackson.Jacksonized;
import org.telegram.telegrambots.meta.api.interfaces.BotApiObject;

/**
 * Represents a location to which a chat is connected.
 * @author Ruben Bermudez
 * @version 5.0
 */
@SuppressWarnings("WeakerAccess")
@EqualsAndHashCode(callSuper = false)
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Jacksonized
@JsonIgnoreProperties(ignoreUnknown = true)
public class ChatLocation implements BotApiObject {

    private static final String LOCATION_FIELD = "location";
    private static final String ADDRESS_FIELD = "address";

    /**
     * The location to which the supergroup is connected
     */
    @JsonProperty(LOCATION_FIELD)
    private Location location;
    /**
     * Location address; 1-64 characters, as defined by the chat owner
     */
    @JsonProperty(ADDRESS_FIELD)
    private String address;
}
