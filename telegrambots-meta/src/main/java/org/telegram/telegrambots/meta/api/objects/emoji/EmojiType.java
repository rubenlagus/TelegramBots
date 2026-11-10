package org.telegram.telegrambots.meta.api.objects.emoji;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import org.telegram.telegrambots.meta.api.interfaces.BotApiObject;

/**
 * This object describes an emoji.
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
        @JsonSubTypes.Type(value = EmojiTypeEmoji.class, name = "emoji"),
        @JsonSubTypes.Type(value = EmojiTypeCustomEmoji.class, name = "custom_emoji")
})
public interface EmojiType extends BotApiObject {
    String getType();
}