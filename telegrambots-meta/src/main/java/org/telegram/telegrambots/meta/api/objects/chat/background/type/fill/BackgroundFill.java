package org.telegram.telegrambots.meta.api.objects.chat.background.type.fill;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import org.telegram.telegrambots.meta.api.interfaces.BotApiObject;

/**
 * This object represents a chat background.
 * @author Ruben Bermudez
 * @version 7.3
 */
@SuppressWarnings("WeakerAccess")
@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.EXISTING_PROPERTY,
        property = "type",
        defaultImpl = Void.class
)
@JsonSubTypes({
        @JsonSubTypes.Type(value = BackgroundFillSolid.class, name = "solid"),
        @JsonSubTypes.Type(value = BackgroundFillGradient.class, name = "gradient"),
        @JsonSubTypes.Type(value = BackgroundFillFreeformGradient.class, name = "freeform_gradient")
})
public interface BackgroundFill extends BotApiObject {
    String getType();
}
