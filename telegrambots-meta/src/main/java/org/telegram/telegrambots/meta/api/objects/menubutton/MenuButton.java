package org.telegram.telegrambots.meta.api.objects.menubutton;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.*;
import org.telegram.telegrambots.meta.api.interfaces.BotApiObject;
import org.telegram.telegrambots.meta.api.interfaces.Validable;
import org.telegram.telegrambots.meta.api.objects.menubutton.serialization.MenuButtonDeserializer;
import org.telegram.telegrambots.meta.api.objects.menubutton.serialization.MenuButtonSerializer;
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
@JsonSerialize(using = MenuButtonSerializer.class)
@JsonDeserialize(using = MenuButtonDeserializer.class)
@EqualsAndHashCode(callSuper = false)
@Getter
@Setter
@ToString
@NoArgsConstructor
public abstract class MenuButton implements BotApiObject, Validable {
    public static final String TYPE_FIELD = "type";

    @JsonProperty(TYPE_FIELD)
    public abstract String getType();

    @Override
    public void validate() throws TelegramApiValidationException {

    }
}
