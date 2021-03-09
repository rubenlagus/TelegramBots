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
 * @version 5.1
 *
 * This object represents a service message about a change in auto-delete timer settings.
 */
@EqualsAndHashCode(callSuper = false)
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class MessageAutoDeleteTimerChanged implements BotApiObject {
    private static final String MESSAGEAUTODELETETIME_FIELD = "message_auto_delete_time";

    @JsonProperty(MESSAGEAUTODELETETIME_FIELD)
    private Integer messageAutoDeleteTime; ///< New auto-delete time for messages in the chat
}
