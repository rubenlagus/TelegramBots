package org.telegram.telegrambots.meta.api.objects.menubutton;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import org.telegram.telegrambots.meta.api.interfaces.BotApiObject;
import org.telegram.telegrambots.meta.api.interfaces.Validable;
import org.telegram.telegrambots.meta.exceptions.TelegramApiValidationException;

/**
 * @author Ruben Bermudez
 * @version 5.3
 *
 * This object describes bot's menu button in a private chat. It should be one of
 *
 * MenuButtonCommands
 * MenuButtonWebApp
 * MenuButtonDefault
 *
 * If a menu button other than MenuButtonDefault is set for a private chat, then it is applied in the chat.
 * Otherwise the default menu button is applied.
 *
 * By default, the menu button opens the list of bot's commands.
 */
@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.EXISTING_PROPERTY,
        property = "type",
        defaultImpl = Void.class
)
@JsonSubTypes({
        @JsonSubTypes.Type(value = MenuButtonDefault.class, name = "default"),
        @JsonSubTypes.Type(value = MenuButtonWebApp.class, name = "web_app"),
        @JsonSubTypes.Type(value = MenuButtonCommands.class, name = "commands")
})
@EqualsAndHashCode(callSuper = false)
@Getter
@Setter
@ToString
@NoArgsConstructor
@SuperBuilder
public abstract class MenuButton implements BotApiObject, Validable {
    public static final String TYPE_FIELD = "type";

    @JsonProperty(TYPE_FIELD)
    public abstract String getType();

    @Override
    public void validate() throws TelegramApiValidationException {

    }
}
