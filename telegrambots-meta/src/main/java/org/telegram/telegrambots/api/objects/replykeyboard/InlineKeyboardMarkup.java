package org.telegram.telegrambots.api.objects.replykeyboard;



import org.telegram.telegrambots.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.exceptions.TelegramApiValidationException;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Ruben Bermudez
 * @version 1.0
 * @brief This object represents an inline keyboard that appears right next to the message it
 * belongs to
 * @note Inline keyboards are currently being tested and are only available in one-on-one chats
 * (i.e., user-bot or user-user in the case of inline bots).
 * @date 10 of April of 2016
 */
public class InlineKeyboardMarkup implements ReplyKeyboard {

    private static final String KEYBOARD_FIELD = "inline_keyboard";


    private List<List<InlineKeyboardButton>> inline_keyboard; ///< Array of button rows, each represented by an Array of Strings

    public InlineKeyboardMarkup() {
        super();
        inline_keyboard = new ArrayList<>();
    }

    public List<List<InlineKeyboardButton>> getKeyboard() {
        return inline_keyboard;
    }

    public InlineKeyboardMarkup setKeyboard(List<List<InlineKeyboardButton>> keyboard) {
        this.inline_keyboard = keyboard;
        return this;
    }

    @Override
    public void validate() throws TelegramApiValidationException {
        if (inline_keyboard == null) {
            throw new TelegramApiValidationException("Keyboard parameter can't be null", this);
        }
        for (List<InlineKeyboardButton> inlineKeyboardButtons : inline_keyboard) {
            for (InlineKeyboardButton inlineKeyboardButton : inlineKeyboardButtons) {
                inlineKeyboardButton.validate();
            }
        }
    }

    @Override
    public String toString() {
        return "InlineKeyboardMarkup{" +
                "inline_keyboard=" + inline_keyboard +
                '}';
    }
}
