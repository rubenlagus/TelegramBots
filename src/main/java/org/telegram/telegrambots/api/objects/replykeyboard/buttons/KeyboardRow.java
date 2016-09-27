package org.telegram.telegrambots.api.objects.replykeyboard.buttons;

import org.telegram.telegrambots.api.interfaces.Validable;
import org.telegram.telegrambots.exceptions.TelegramApiValidationException;

import java.util.ArrayList;

/**
 * @author Ruben Bermudez
 * @version 1.0
 * @brief Row for ReplyKeyBoardMarkup
 * @date 10 of April of 2016
 */
public class KeyboardRow extends ArrayList<KeyboardButton> implements Validable {
    public boolean add(String text) {
        return super.add(new KeyboardButton(text));
    }

    public void add(int index, String text) {
        super.add(index, new KeyboardButton(text));
    }

    public boolean contains(String text) {
        return super.contains(new KeyboardButton(text));
    }

    public int lastIndexOf(String text) {
        return super.lastIndexOf(new KeyboardButton(text));
    }

    public int indexOf(String text) {
        return super.indexOf(new KeyboardButton(text));
    }

    public KeyboardButton set(int index, String text) {
        return super.set(index, new KeyboardButton(text));
    }

    public boolean remove(String text) {
        return super.remove(new KeyboardButton(text));
    }

    @Override
    public void validate() throws TelegramApiValidationException {
        for (KeyboardButton keyboardButton : this) {
            keyboardButton.validate();
        }
    }
}
