package org.telegram.telegrambots.meta.api.objects.voicechat;

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
 * This object represents a service message about a voice chat started in the chat.
 *
 * Currently holds no information.
 */
@EqualsAndHashCode(callSuper = false)
@Getter
@Setter
@ToString
@NoArgsConstructor
public class VoiceChatStarted implements BotApiObject {
}
