package org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import lombok.extern.jackson.Jacksonized;
import org.telegram.telegrambots.meta.api.interfaces.BotApiObject;
import org.telegram.telegrambots.meta.api.interfaces.Validable;
import org.telegram.telegrambots.meta.api.objects.webapp.WebAppInfo;
import org.telegram.telegrambots.meta.exceptions.TelegramApiValidationException;

/**
 * @author Ruben Bermudez
 * @version 1.0
 *
 * his object represents one button of the reply keyboard.
 * At most one of the optional fields must be used to specify type of the button.
 * For simple text buttons, String can be used instead of this object to specify the button text.
 * @apiNote  Optional fields are mutually exclusive.
 * @apiNote request_contact and request_location options will only work in Telegram versions released
 * after 9 April, 2016. Older clients will ignore them.
 * @apiNote request_poll option will only work in Telegram versions released after 1X January, 2020.
 * Older clients will receive unsupported message.
 * @apiNote The optional fields web_app, request_user, request_users, request_chat, request_contact, request_location,
 * and request_poll are mutually exclusive
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
public class KeyboardButton implements Validable, BotApiObject {

    private static final String TEXT_FIELD = "text";
    private static final String REQUEST_CONTACT_FIELD = "request_contact";
    private static final String REQUEST_LOCATION_FIELD = "request_location";
    private static final String REQUEST_POLL_FIELD = "request_poll";
    private static final String WEBAPP_FIELD = "web_app";
    private static final String REQUESTUSER_FIELD = "request_user";
    private static final String REQUESTCHAT_FIELD = "request_chat";
    private static final String REQUEST_USERS_FIELD = "request_users";
    private static final String ICON_CUSTOM_EMOJI_ID_FIELD = "icon_custom_emoji_id";
    private static final String STYLE_FIELD = "style";
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

    /**
     * Optional. Description of the web app that will be launched when the user presses the button.
     * The web app will be able to send a “web_app_data” service message.
     * Available in private chats only.
     */
    @JsonProperty(WEBAPP_FIELD)
    private WebAppInfo webApp;
    /**
     * Optional.
     * If specified, pressing the button will open a list of suitable users.
     * Tapping on any user will send their identifier to the bot in a “user_shared” service message.
     * Available in private chats only.
     */
    @JsonProperty(REQUESTUSER_FIELD)
    private KeyboardButtonRequestUser requestUser;
    /**
     * Optional.
     * If specified, pressing the button will open a list of suitable chats.
     * Tapping on a chat will send its identifier to the bot in a “chat_shared” service message.
     * Available in private chats only.
     */
    @JsonProperty(REQUESTCHAT_FIELD)
    private KeyboardButtonRequestChat requestChat;
    /**
     * Optional.
     * If specified, pressing the button will open a list of suitable users.
     * Identifiers of selected users will be sent to the bot in a "users_shared" service message.
     * Available in private chats only.
     */
    @JsonProperty(REQUEST_USERS_FIELD)
    private KeyboardButtonRequestUsers requestUsers;
    /**
     * Optional.
     * Unique identifier of the custom emoji shown before the text of the button.
     * Can only be used by bots that purchased additional usernames on Fragment or in the messages directly sent
     * by the bot to private, group and supergroup chats if the owner of the bot has a Telegram Premium subscription.
     */
    @JsonProperty(ICON_CUSTOM_EMOJI_ID_FIELD)
    private String iconCustomEmojiId;
    /**
     * Optional.
     * Style of the button. Must be one of "danger" (red), "success" (green) or "primary" (blue).
     * If omitted, then an app-specific style is used.
     */
    @JsonProperty(STYLE_FIELD)
    private String style;

    @Override
    public void validate() throws TelegramApiValidationException {
        if (text.isEmpty()) {
            throw new TelegramApiValidationException("Text parameter can't be empty", this);
        }

        int requestsProvided = 0;
        requestsProvided += (requestContact == null ? 0 : 1);
        requestsProvided += (requestLocation == null ? 0 : 1);
        requestsProvided += (requestPoll == null ? 0 : 1);
        requestsProvided += (webApp == null ? 0 : 1);
        requestsProvided += (requestUser == null ? 0 : 1);
        requestsProvided += (requestChat == null ? 0 : 1);
        requestsProvided += (requestUsers == null ? 0 : 1);
        if (requestsProvided > 1) {
            throw new TelegramApiValidationException("The optional fields web_app, request_user, request_users, request_chat, request_contact, request_location, and request_poll are mutually exclusive", this);
        }
        if (webApp != null) {
            webApp.validate();
        }
        if (requestPoll != null) {
            requestPoll.validate();
        }
        if (requestUser != null) {
            requestUser.validate();
        }
        if (requestChat != null) {
            requestChat.validate();
        }
        if (requestUsers != null) {
            requestUsers.validate();
        }
    }
}
