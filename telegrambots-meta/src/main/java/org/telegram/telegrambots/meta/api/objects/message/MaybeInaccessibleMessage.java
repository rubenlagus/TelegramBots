package org.telegram.telegrambots.meta.api.objects.message;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.telegram.telegrambots.meta.api.interfaces.BotApiObject;
import org.telegram.telegrambots.meta.api.objects.chat.Chat;
import tools.jackson.databind.annotation.JsonDeserialize;

/**
 * @author Ruben Bermudez
 * @version 7.0
 * This object describes a message that can be inaccessible to the bot.
 */
@JsonDeserialize(using = MaybeInaccessibleMessageDeserializer.class)
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
