package org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.telegram.telegrambots.meta.api.interfaces.BotApiObject;
import org.telegram.telegrambots.meta.api.interfaces.Validable;
import org.telegram.telegrambots.meta.exceptions.TelegramApiValidationException;

import java.util.Objects;

/**
 * @author Ruben Bermudez
 * @version 4.7
 *
 * This object represents type of a poll, which is allowed to be created and sent when the corresponding button is pressed.
 */
public class KeyboardButtonPollType implements BotApiObject, Validable {
    private static final String TYPE_FIELD = "type";

    /**
     * Optional.
     *
     * If quiz is passed, the user will be allowed to create only polls in the quiz mode.
     * If regular is passed, only regular polls will be allowed.
     * Otherwise, the user will be allowed to create a poll of any type.
     */
    @JsonProperty(TYPE_FIELD)
    private String type;

    public KeyboardButtonPollType() {
    }

    public KeyboardButtonPollType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public void validate() throws TelegramApiValidationException {
        if (type == null || type.isEmpty()) {
            throw new TelegramApiValidationException("Type parameter can't be empty", this);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof KeyboardButtonPollType)) return false;
        KeyboardButtonPollType that = (KeyboardButtonPollType) o;
        return Objects.equals(type, that.type);
    }

    @Override
    public int hashCode() {
        return Objects.hash(type);
    }

    @Override
    public String toString() {
        return "KeyboardButtonPollType{" +
                "type='" + type + '\'' +
                '}';
    }
}
