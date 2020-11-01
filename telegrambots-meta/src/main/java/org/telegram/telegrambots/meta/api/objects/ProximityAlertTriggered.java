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
 * This object represents the content of a service message,
 * sent whenever a user in the chat triggers a proximity alert set by another user.
 */
@SuppressWarnings("WeakerAccess")
@EqualsAndHashCode(callSuper = false)
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ProximityAlertTriggered implements BotApiObject {

    private static final String TRAVELER_FIELD = "traveler";
    private static final String WATCHER_FIELD = "watcher";
    private static final String DISTANCE_FIELD = "distance";

    @JsonProperty(TRAVELER_FIELD)
    private User traveler; ///< User that triggered the alert
    @JsonProperty(WATCHER_FIELD)
    private User watcher; ///< User that set the alert
    @JsonProperty(DISTANCE_FIELD)
    private Integer distance; ///< The distance between the users
}
