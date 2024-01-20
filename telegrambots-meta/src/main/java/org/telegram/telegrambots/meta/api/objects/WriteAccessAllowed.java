package org.telegram.telegrambots.meta.api.objects;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.telegram.telegrambots.meta.api.interfaces.BotApiObject;

/**
 * This object represents a service message about a user allowing a bot to write messages after adding it to the attachment menu,
 * launching a Web App from a link, or accepting an explicit request from a Web App sent by the method requestWriteAccess.
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

    private static final String FROM_REQUEST_FIELD = "from_request";
    private static final String WEB_APP_NAME_FIELD = "web_app_name";
    private static final String FROM_ATTACHMENT_MENU_FIELD = "from_attachment_menu";

    /**
     * Optional.
     * True, if the access was granted after the user accepted an explicit request from a Web App sent by the method requestWriteAccess
     */
    @JsonProperty(FROM_REQUEST_FIELD)
    private Boolean fromRequest;
    /**
     * Optional.
     * Name of the Web App, if the access was granted when the Web App was launched from a link
     */
    @JsonProperty(WEB_APP_NAME_FIELD)
    private String webAppName;
    /**
     * Optional.
     * True, if the access was granted when the bot was added to the attachment or side menu
     */
    @JsonProperty(FROM_ATTACHMENT_MENU_FIELD)
    private Boolean fromAttachmentMenu;
}
