package org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.telegram.telegrambots.meta.api.interfaces.BotApiObject;
import org.telegram.telegrambots.meta.api.interfaces.Validable;
import org.telegram.telegrambots.meta.exceptions.TelegramApiValidationException;

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
@EqualsAndHashCode(callSuper = false)
@Getter
@Setter
@ToString
@NoArgsConstructor
@RequiredArgsConstructor
@AllArgsConstructor
@Builder
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
    @NonNull
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
}
