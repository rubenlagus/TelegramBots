package org.telegram.telegrambots.meta.api.objects.menubutton;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.*;
import org.telegram.telegrambots.meta.exceptions.TelegramApiValidationException;

/**
 * @author Ruben Bermudez
 * @version 6.0
 *
 * Describes that no specific value for the menu button was set.
 */
@SuppressWarnings({"unused"})
@JsonDeserialize
@EqualsAndHashCode(callSuper = true)
@Getter
@Setter
@ToString
@Builder
public class MenuButtonDefault extends MenuButton {
    private static final String TYPE = "default"; ///< Type of the button, must be default

    @Override
    public void validate() throws TelegramApiValidationException {
        super.validate();
    }

    @Override
    public String getType() {
        return TYPE;
    }
}
