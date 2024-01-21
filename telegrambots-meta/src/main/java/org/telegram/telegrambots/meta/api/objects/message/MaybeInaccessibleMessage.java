package org.telegram.telegrambots.meta.api.objects.message;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import org.telegram.telegrambots.meta.api.interfaces.BotApiObject;
import org.telegram.telegrambots.meta.api.objects.Chat;

/**
 * @author Ruben Bermudez
 * @version 7.0
 * This object describes a message that can be inaccessible to the bot.
 */
@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.EXISTING_PROPERTY,
        property = "date",
        visible = true,
        defaultImpl = Message.class
)
@JsonSubTypes({
        @JsonSubTypes.Type(value = InaccessibleMessage.class, name = "0")
})
public interface MaybeInaccessibleMessage extends BotApiObject {
    String DATE_FIELD = "date";

    @JsonIgnore
    default boolean isUserMessage() {
        return true;
    }
    @JsonIgnore
    default boolean isGroupMessage() {
        return false;
    }
    @JsonIgnore
    default boolean isSuperGroupMessage() {
        return false;
    }

    @JsonIgnore
    Long getChatId();

    Chat getChat();

    Integer getMessageId();

    Integer getDate();
}
