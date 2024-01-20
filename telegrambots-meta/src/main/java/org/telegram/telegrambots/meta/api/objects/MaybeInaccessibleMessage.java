package org.telegram.telegrambots.meta.api.objects;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import org.telegram.telegrambots.meta.api.interfaces.BotApiObject;
import org.telegram.telegrambots.meta.api.objects.serialization.MaybeInaccessibleMessageDeserializer;

/**
 * @author Ruben Bermudez
 * @version 7.0
 * This object describes a message that can be inaccessible to the bot.
 */
@JsonDeserialize(using = MaybeInaccessibleMessageDeserializer.class)
public interface MaybeInaccessibleMessage extends BotApiObject {
    String DATE_FIELD = "date";

    default boolean isUserMessage() {
        return true;
    }

    default boolean isGroupMessage() {
        return false;
    }
    default boolean isSuperGroupMessage() {
        return false;
    }

    Long getChatId();
}
