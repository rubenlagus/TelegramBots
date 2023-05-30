package org.telegram.telegrambots.meta.api.objects;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.telegram.telegrambots.meta.api.interfaces.BotApiObject;

/**
 * This object represents a service message about a user allowing a bot added to the attachment menu to write messages.
 *
 * @author Ruben Bermudez
 * @version 6.4
 */
@EqualsAndHashCode(callSuper = false)
@Getter
@Setter
@ToString
@AllArgsConstructor
public class WriteAccessAllowed implements BotApiObject {

    private static final String WEB_APP_NAME_FIELD = "web_app_name";

    /**
     * Optional.
     * Name of the Web App which was launched from a link
     */
    @JsonProperty(WEB_APP_NAME_FIELD)
    private String webAppName;
}
