package org.telegram.telegrambots.meta.api.objects;

import org.telegram.telegrambots.meta.api.interfaces.BotApiObject;

/**
 * @author Ruben Bermudez
 * @version 7.0
 * This object describes a message that can be inaccessible to the bot.
 */
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
