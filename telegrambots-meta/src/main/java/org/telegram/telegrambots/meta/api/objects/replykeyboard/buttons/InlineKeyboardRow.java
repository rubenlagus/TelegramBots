package org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons;

import org.apache.commons.lang3.stream.Streams;
import org.telegram.telegrambots.meta.api.interfaces.Validable;
import org.telegram.telegrambots.meta.exceptions.TelegramApiValidationException;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Ruben Bermudez
 * @version 1.0
 * Row for ReplyKeyBoardMarkup
 */
public class InlineKeyboardRow extends ArrayList<InlineKeyboardButton> implements Validable {
    public InlineKeyboardRow(int initialCapacity) {
        super(initialCapacity);
    }

    public InlineKeyboardRow() {
    }

    public InlineKeyboardRow(Collection<? extends InlineKeyboardButton> c) {
        super(c);
    }

    public InlineKeyboardRow(InlineKeyboardButton... buttons) {
        super(List.of(buttons));
    }

    public InlineKeyboardRow(String... buttonTexts) {
        super(Streams.of(buttonTexts).map(InlineKeyboardButton::new).collect(Collectors.toList()));
    }

    public boolean add(String text) {
        return super.add(new InlineKeyboardButton(text));
    }

    public void add(int index, String text) {
        super.add(index, new InlineKeyboardButton(text));
    }

    public void addAll(List<String> buttonNames) {
        buttonNames.forEach(name -> super.add(new InlineKeyboardButton(name)));
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

    public InlineKeyboardButton set(int index, String text) {
        return super.set(index, new InlineKeyboardButton(text));
    }

    public boolean remove(String text) {
        return super.remove(new InlineKeyboardButton(text));
    }

    @Override
    public void validate() throws TelegramApiValidationException {
        for (InlineKeyboardButton keyboardButton : this) {
            keyboardButton.validate();
        }
    }
}
