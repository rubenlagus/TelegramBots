package org.telegram.telegrambots.meta.api.objects;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.telegram.telegrambots.meta.api.interfaces.BotApiObject;

/**
 * This object represents a service message about a user allowing a bot added to the attachment menu to write messages.
 * Currently holds no information.
 * @author Ruben Bermudez
 * @version 6.4
 */
@EqualsAndHashCode(callSuper = false)
@Getter
@Setter
@ToString
@AllArgsConstructor
public class WriteAccessAllowed implements BotApiObject {
}
