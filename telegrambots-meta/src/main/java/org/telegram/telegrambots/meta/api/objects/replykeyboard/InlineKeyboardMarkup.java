package org.telegram.telegrambots.meta.api.objects.replykeyboard;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
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
 * @version 10.3
 * This object represents an inline keyboard that appears right next to the message it
 * belongs to.
 */

@EqualsAndHashCode(callSuper = false)
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Jacksonized
@JsonIgnoreProperties(ignoreUnknown = true)
public class InlineKeyboardMarkup implements ReplyKeyboard {

    private static final String KEYBOARD_FIELD = "inline_keyboard";
    private static final String FORCE_REPLY_FIELD = "force_reply";

    /**
     * Array of button rows, each represented by an Array of Strings
     */
    @JsonProperty(KEYBOARD_FIELD)
    @NonNull
    @Singular(value = "keyboardRow")
    private List<InlineKeyboardRow> keyboard;
    /**
     * Optional.
     * Pass True if the reply interface must be shown to the user, as if they had manually
     * selected the bot's message and tapped 'Reply'.
     *
     * @apiNote The value of the field can't be changed when the inline keyboard is edited.
     */
    @JsonProperty(FORCE_REPLY_FIELD)
    private Boolean forceReply;

    @Override
    public void validate() throws TelegramApiValidationException {
        for (InlineKeyboardRow inlineKeyboardButtons : keyboard) {
            inlineKeyboardButtons.validate();
        }
    }
}
