package org.telegram.telegrambots.meta.api.objects.replykeyboard;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.Singular;
import lombok.ToString;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiValidationException;

import java.util.List;

/**
 * @author Ruben Bermudez
 * @version 1.0
 * This object represents a custom keyboard with reply options.
 */
@JsonDeserialize
@EqualsAndHashCode(callSuper = false)
@Getter
@Setter
@ToString
@NoArgsConstructor(force = true)
@RequiredArgsConstructor
@AllArgsConstructor
@Builder
public class ReplyKeyboardMarkup implements ReplyKeyboard {

    private static final String KEYBOARD_FIELD = "keyboard";
    private static final String RESIZEKEYBOARD_FIELD = "resize_keyboard";
    private static final String ONETIMEKEYBOARD_FIELD = "one_time_keyboard";
    private static final String SELECTIVE_FIELD = "selective";
    private static final String INPUTFIELDPLACEHOLDER_FIELD = "input_field_placeholder";
    private static final String ISPERSISTENT_FIELD = "is_persistent";

    @JsonProperty(KEYBOARD_FIELD)
    @NonNull
    @Singular(value = "keyboardRow")
    @Setter
    private List<KeyboardRow> keyboard; ///< Array of button rows, each represented by an Array of Strings
    @JsonProperty(RESIZEKEYBOARD_FIELD)
    private Boolean resizeKeyboard; ///< Optional. Requests clients to resize the keyboard vertically for optimal fit (e.g., make the keyboard smaller if there are just two rows of buttons). Defaults to false.
    @JsonProperty(ONETIMEKEYBOARD_FIELD)
    private Boolean oneTimeKeyboard; ///< Optional. Requests clients to hide the keyboard as soon as it's been used. Defaults to false.
    /**
     * Optional. Use this parameter if you want to show the keyboard to specific users only.
     * Targets:
     *      1) users that are @mentioned in the text of the Message object;
     *      2) if the bot's message is a reply (has reply_to_message_id), sender of the original message.
     */
    @JsonProperty(SELECTIVE_FIELD)
    private Boolean selective;
    /**
     * Optional.
     * The placeholder to be shown in the input field when the keyboard is active; 1-64 characters
     */
    @JsonProperty(INPUTFIELDPLACEHOLDER_FIELD)
    private String inputFieldPlaceholder;
    /**
     * Optional.
     * Requests clients to always show the keyboard when the regular keyboard is hidden.
     * Defaults to false, in which case the custom keyboard can be hidden and opened with a keyboard icon.
     */
    @JsonProperty(ISPERSISTENT_FIELD)
    private Boolean isPersistent;
    @Override
    public void validate() throws TelegramApiValidationException {
        if (keyboard == null) {
            throw new TelegramApiValidationException("Keyboard parameter can't be null", this);
        }
        if (inputFieldPlaceholder != null && (inputFieldPlaceholder.length() < 1 || inputFieldPlaceholder.length() > 64)) {
            throw new TelegramApiValidationException("InputFieldPlaceholder must be between 1 and 64 characters", this);
        }
        for (KeyboardRow keyboardButtons : keyboard) {
            keyboardButtons.validate();
        }
    }
}
