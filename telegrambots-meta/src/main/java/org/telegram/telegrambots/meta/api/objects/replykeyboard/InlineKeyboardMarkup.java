package org.telegram.telegrambots.meta.api.objects.replykeyboard;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.Singular;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import lombok.extern.jackson.Jacksonized;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiValidationException;

import java.util.List;

/**
 * @author Ruben Bermudez
 * @version 1.0
 * This object represents an inline keyboard that appears right next to the message it
 * belongs to.
 */

@EqualsAndHashCode(callSuper = false)
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@SuperBuilder
@Jacksonized
public class InlineKeyboardMarkup implements ReplyKeyboard {

    private static final String KEYBOARD_FIELD = "inline_keyboard";

    /**
     * Array of button rows, each represented by an Array of Strings
     */
    @JsonProperty(KEYBOARD_FIELD)
    @NonNull
    @Singular(value = "keyboardRow")
    private List<InlineKeyboardRow> keyboard;

    @Override
    public void validate() throws TelegramApiValidationException {
        for (InlineKeyboardRow inlineKeyboardButtons : keyboard) {
            inlineKeyboardButtons.validate();
        }
    }
}
