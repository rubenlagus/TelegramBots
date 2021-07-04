package org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons;

import org.telegram.telegrambots.meta.api.interfaces.Validable;
import org.telegram.telegrambots.meta.exceptions.TelegramApiValidationException;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @author Ruben Bermudez
 * @version 1.0
 * Row for ReplyKeyBoardMarkup
 */
public class KeyboardRow extends ArrayList<KeyboardButton> implements Validable {
    public KeyboardRow(int initialCapacity) {
        super(initialCapacity);
    }

    public KeyboardRow() {
    }

    public KeyboardRow(Collection<? extends KeyboardButton> c) {
        super(c);
    }

    public boolean add(String text) {
        return super.add(new KeyboardButton(text));
    }

    public void add(int index, String text) {
        super.add(index, new KeyboardButton(text));
    }

    public void addAll(List<String> buttonNames) {
        buttonNames.forEach(name -> super.add(new KeyboardButton(name)));
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
