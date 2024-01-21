package org.telegram.telegrambots.meta.api.objects.messageorigin;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import org.telegram.telegrambots.meta.api.interfaces.BotApiObject;
import org.telegram.telegrambots.meta.api.objects.messageorigin.serialization.MessageOriginDeserializer;

/**
 * @author Ruben Bermudez
 * @version 7.0
 * This object describes the origin of a message. It can be one of
 */
@JsonDeserialize(using = MessageOriginDeserializer.class)
public interface MessageOrigin extends BotApiObject {
    String USER_TYPE = "user";
    String HIDDEN_USER_TYPE = "hidden_user";
    String CHAT_TYPE = "chat";
    String CHANNEL_TYPE = "channel";
}
