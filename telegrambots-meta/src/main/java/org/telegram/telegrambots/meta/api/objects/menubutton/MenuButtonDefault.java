package org.telegram.telegrambots.meta.api.objects.menubutton;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import lombok.extern.jackson.Jacksonized;
import org.telegram.telegrambots.meta.exceptions.TelegramApiValidationException;

/**
 * @author Ruben Bermudez
 * @version 6.0
 *
 * Describes that no specific value for the menu button was set.
 */
@SuppressWarnings({"unused"})

@EqualsAndHashCode(callSuper = true)
@Getter
@Setter
@ToString
@NoArgsConstructor
@SuperBuilder
@Jacksonized
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
