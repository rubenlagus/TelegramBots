package org.telegram.telegrambots.meta.api.objects.chat.sharetheme;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import org.telegram.telegrambots.meta.api.interfaces.BotApiObject;

/**
 * This object describes the theme of the screen for sharing a link to a user or a chat.
 * @version 10.4
 */
@SuppressWarnings("WeakerAccess")
@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.EXISTING_PROPERTY,
        property = "type",
        defaultImpl = Void.class
)
@JsonSubTypes({
        @JsonSubTypes.Type(value = ChatShareThemeEmoji.class, name = "emoji"),
        @JsonSubTypes.Type(value = ChatShareThemeUniqueGift.class, name = "unique_gift")
})
public interface ChatShareTheme extends BotApiObject {
    String getType();
}