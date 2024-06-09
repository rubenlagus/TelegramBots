package org.telegram.telegrambots.meta.api.objects.chat.background.type;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import org.telegram.telegrambots.meta.api.interfaces.BotApiObject;

/**
 * This object describes the type of a background.
 * @author Ruben Bermudez
 * @version 7.3
 */
@SuppressWarnings("WeakerAccess")
@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.EXISTING_PROPERTY,
        property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = BackgroundTypeFill.class, name = "fill"),
        @JsonSubTypes.Type(value = BackgroundTypeWallpaper.class, name = "wallpaper"),
        @JsonSubTypes.Type(value = BackgroundTypePattern.class, name = "pattern"),
        @JsonSubTypes.Type(value = BackgroundTypeChatTheme.class, name = "chat_theme")
})
public interface BackgroundType extends BotApiObject {
    String getType();
}
