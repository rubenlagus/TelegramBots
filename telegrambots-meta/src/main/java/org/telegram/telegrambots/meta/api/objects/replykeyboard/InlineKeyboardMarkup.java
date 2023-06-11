package org.telegram.telegrambots.meta.api.objects.replykeyboard;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;
import lombok.Singular;
import lombok.ToString;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.exceptions.TelegramApiValidationException;

import java.util.List;

/**
 * @author Ruben Bermudez
 * @version 1.0
 * This object represents an inline keyboard that appears right next to the message it
 * belongs to.
 */
@JsonDeserialize
@EqualsAndHashCode(callSuper = false)
@Getter
@Setter
@ToString
@NoArgsConstructor(force = true)
@AllArgsConstructor
@Builder
public class InlineKeyboardMarkup implements ReplyKeyboard {

    private static final String KEYBOARD_FIELD = "inline_keyboard";

    @JsonProperty(KEYBOARD_FIELD)
    @NonNull
    @Singular(value = "keyboardRow")
    private List<List<InlineKeyboardButton>> keyboard; ///< Array of button rows, each represented by an Array of Strings

    @Override
    public void validate() throws TelegramApiValidationException {
        if (keyboard == null) {
            throw new TelegramApiValidationException("Keyboard parameter can't be null", this);
        }
        for (List<InlineKeyboardButton> inlineKeyboardButtons : keyboard) {
            for (InlineKeyboardButton inlineKeyboardButton : inlineKeyboardButtons) {
                inlineKeyboardButton.validate();
            }
        }
    }
}
