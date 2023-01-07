package org.telegram.telegrambots.meta.api.objects.forum;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.telegram.telegrambots.meta.api.interfaces.BotApiObject;

/**
 * This object represents a service message about General forum topic hidden in the chat.
 * Currently holds no information.
 * @author Ruben Bermudez
 * @version 6.4
 */
@SuppressWarnings("WeakerAccess")
@EqualsAndHashCode(callSuper = false)
@Getter
@Setter
@ToString
@AllArgsConstructor
public class GeneralForumTopicUnhidden implements BotApiObject {
}
