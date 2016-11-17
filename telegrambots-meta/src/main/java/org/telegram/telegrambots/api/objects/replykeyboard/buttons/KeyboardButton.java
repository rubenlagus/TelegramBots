package org.telegram.telegrambots.api.objects.replykeyboard.buttons;

import com.fasterxml.jackson.annotation.JsonProperty;

import org.telegram.telegrambots.api.interfaces.InputBotApiObject;
import org.telegram.telegrambots.api.interfaces.Validable;
import org.telegram.telegrambots.exceptions.TelegramApiValidationException;

/**
 * @author Ruben Bermudez
 * @version 1.0
 * @brief This object represents one button of the reply keyboard. For simple text buttons String
 * can be used instead of this object to specify text of the button.
 * @note Optional fields are mutually exclusive.
 * @note request_contact and request_location options will only work in Telegram versions released
 * after 9 April, 2016. Older clients will ignore them.
 * @date 10 of April of 2016
 */
public class KeyboardButton implements InputBotApiObject, Validable {

    private static final String TEXT_FIELD = "text";
    private static final String REQUEST_CONTACT_FIELD = "request_contact";
    private static final String REQUEST_LOCATION_FIELD = "request_location";
    /**
     * Text of the button.
     * If none of the optional fields are used, it will be sent to the bot as a message when the button is pressed
     */
    @JsonProperty(TEXT_FIELD)
    private String text;
    /**
     * Optional.
     * If True, the user's phone number will be sent as a contact when the button is pressed.
     * Available in private chats only
     */
    @JsonProperty(REQUEST_CONTACT_FIELD)
    private Boolean requestContact;
    /**
     * Optional.
     * If True, the user's current location will be sent when the button is pressed.
     * Available in private chats only
     */
    @JsonProperty(REQUEST_LOCATION_FIELD)
    private Boolean requestLocation;

    public KeyboardButton() {
        super();
    }

    public KeyboardButton(String text) {
        super();
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public KeyboardButton setText(String text) {
        this.text = text;
        return this;
    }

    public Boolean getRequestContact() {
        return requestContact;
    }

    public KeyboardButton setRequestContact(Boolean requestContact) {
        this.requestContact = requestContact;
        return this;
    }

    public Boolean getRequestLocation() {
        return requestLocation;
    }

    public KeyboardButton setRequestLocation(Boolean requestLocation) {
        this.requestLocation = requestLocation;
        return this;
    }

    @Override
    public void validate() throws TelegramApiValidationException {
        if (text == null || text.isEmpty()) {
            throw new TelegramApiValidationException("Text parameter can't be empty", this);
        }
        if (requestContact != null && requestLocation != null && requestContact && requestLocation) {
            throw new TelegramApiValidationException("Cant request contact and location at the same time", this);
        }
    }

    @Override
    public String toString() {
        return "KeyboardButton{" +
                "text=" + text +
                ", requestContact=" + requestContact +
                ", requestLocation=" + requestLocation +
                '}';
    }
}
