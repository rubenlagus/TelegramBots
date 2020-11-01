package org.telegram.telegrambots.meta.api.objects;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.telegram.telegrambots.meta.api.interfaces.BotApiObject;

/**
 * @author Ruben Bermudez
 * @version 5.0
 * Represents a location to which a chat is connected.
 */
@SuppressWarnings("WeakerAccess")
@EqualsAndHashCode(callSuper = false)
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ChatLocation implements BotApiObject {

    private static final String LOCATION_FIELD = "location";
    private static final String ADDRESS_FIELD = "address";

    @JsonProperty(LOCATION_FIELD)
    private Location location; ///< The location to which the supergroup is connected
    @JsonProperty(ADDRESS_FIELD)
    private String address; ///< Location address; 1-64 characters, as defined by the chat owner
}
