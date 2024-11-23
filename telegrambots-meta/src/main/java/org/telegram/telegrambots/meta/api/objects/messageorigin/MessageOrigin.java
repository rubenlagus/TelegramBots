package org.telegram.telegrambots.meta.api.objects.messageorigin;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import org.telegram.telegrambots.meta.api.interfaces.BotApiObject;

/**
 * @author Ruben Bermudez
 * @version 7.0
 * This object describes the origin of a message. It can be one of
 */
@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.EXISTING_PROPERTY,
        property = "type",
        defaultImpl = Void.class
)
@JsonSubTypes({
        @JsonSubTypes.Type(value = MessageOriginUser.class, name = MessageOrigin.USER_TYPE),
        @JsonSubTypes.Type(value = MessageOriginHiddenUser.class, name = MessageOrigin.HIDDEN_USER_TYPE),
        @JsonSubTypes.Type(value = MessageOriginChat.class, name = MessageOrigin.CHAT_TYPE),
        @JsonSubTypes.Type(value = MessageOriginChannel.class, name = MessageOrigin.CHANNEL_TYPE)
})
public interface MessageOrigin extends BotApiObject {
    String USER_TYPE = "user";
    String HIDDEN_USER_TYPE = "hidden_user";
    String CHAT_TYPE = "chat";
    String CHANNEL_TYPE = "channel";
}
