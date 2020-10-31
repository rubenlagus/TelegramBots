package org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.telegram.telegrambots.meta.api.interfaces.BotApiObject;
import org.telegram.telegrambots.meta.api.interfaces.Validable;
import org.telegram.telegrambots.meta.exceptions.TelegramApiValidationException;

import java.util.Objects;

/**
 * @author Ruben Bermudez
 * @version 1.0
 *
 * This object represents one button of the reply keyboard. For simple text buttons String
 * can be used instead of this object to specify text of the button.
 * @apiNote  Optional fields are mutually exclusive.
 * @apiNote request_contact and request_location options will only work in Telegram versions released
 * after 9 April, 2016. Older clients will ignore them.
 * @apiNote request_poll option will only work in Telegram versions released after 1X January, 2020.
 * Older clients will receive unsupported message.
 */
public class KeyboardButton implements Validable, BotApiObject {

    private static final String TEXT_FIELD = "text";
    private static final String REQUEST_CONTACT_FIELD = "request_contact";
    private static final String REQUEST_LOCATION_FIELD = "request_location";
    private static final String REQUEST_POLL_FIELD = "request_poll";
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
    /**
     * Optional.
     * If specified, the user will be asked to create a poll and send it to the bot when the button is pressed.
     * Available in private chats only
     */
    @JsonProperty(REQUEST_POLL_FIELD)
    private KeyboardButtonPollType requestPoll;

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

    public KeyboardButtonPollType getRequestPoll() {
        return requestPoll;
    }

    public KeyboardButton setRequestPoll(KeyboardButtonPollType requestPoll) {
        this.requestPoll = requestPoll;
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
        if (requestContact != null && requestPoll != null && requestContact) {
            throw new TelegramApiValidationException("Cant request contact and poll at the same time", this);
        }
        if (requestLocation != null && requestPoll != null && requestLocation) {
            throw new TelegramApiValidationException("Cant request location and poll at the same time", this);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) return true;
        if (!(o instanceof KeyboardButton)) {
            return false;
        }
        KeyboardButton keyboardButton = (KeyboardButton) o;
        return Objects.equals(requestContact, keyboardButton.requestContact)
                && Objects.equals(requestLocation, keyboardButton.requestLocation)
                && Objects.equals(requestPoll, keyboardButton.requestPoll)
                && Objects.equals(text, keyboardButton.text)
                ;
    }

    @Override
    public int hashCode() {
        return Objects.hash(
                requestContact,
                requestLocation,
                requestPoll,
                text);
    }

    @Override
    public String toString() {
        return "KeyboardButton{" +
                "text=" + text +
                ", requestContact=" + requestContact +
                ", requestLocation=" + requestLocation +
                ", requestPoll=" + requestPoll +
                '}';
    }
}
