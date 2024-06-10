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
import org.telegram.telegrambots.meta.api.objects.LoginUrl;
import org.telegram.telegrambots.meta.api.objects.games.CallbackGame;
import org.telegram.telegrambots.meta.api.objects.webapp.WebAppInfo;
import org.telegram.telegrambots.meta.exceptions.TelegramApiValidationException;

/**
 * @author Ruben Bermudez
 * @version 6.1
 * This object represents one button of an inline keyboard.
 * @apiNote You must use exactly one of the optional fields.
 * @apiNote This will only work in Telegram versions released after 9 April, 2016. Older clients will
 * display unsupported message.
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
public class InlineKeyboardButton implements Validable, BotApiObject {

    private static final String TEXT_FIELD = "text";
    private static final String URL_FIELD = "url";
    private static final String CALLBACK_DATA_FIELD = "callback_data";
    private static final String CALLBACK_GAME_FIELD = "callback_game";
    private static final String SWITCH_INLINE_QUERY_FIELD = "switch_inline_query";
    private static final String SWITCH_INLINE_QUERY_CURRENT_CHAT_FIELD = "switch_inline_query_current_chat";
    private static final String SWITCH_INLINE_QUERY_CHOSEN_CHAT_FIELD = "switch_inline_query_chosen_chat";
    private static final String PAY_FIELD = "pay";
    private static final String LOGIN_URL_FIELD = "login_url";
    private static final String WEBAPP_FIELD = "web_app";

    /**
     * Label text on the button
     */
    @JsonProperty(TEXT_FIELD)
    @NonNull
    private String text;
    /**
     * Optional.
     * HTTP or tg:// url to be opened when the button is pressed.
     * Links tg://user?id=<user_id> can be used to mention a user by their ID without using a username,
     * if this is allowed by their privacy settings.
     */
    @JsonProperty(URL_FIELD)
    private String url;
    /**
     * Optional.
     * Data to be sent in a callback query to the bot when button is pressed, 1-64 bytes.
     * @apiNote Not supported for messages sent on behalf of a Telegram Business account.
     */
    @JsonProperty(CALLBACK_DATA_FIELD)
    private String callbackData;
    /**
     * Optional.
     * Description of the game that will be launched when the user presses the button.
     *
     * @apiNote This type of button must always be the first button in the first row.
     */
    @JsonProperty(CALLBACK_GAME_FIELD)
    private CallbackGame callbackGame;
    /**
     * Optional.
     * If set, pressing the button will prompt the user to select one of their chats, open that chat and insert the
     * bot's username and the specified inline query in the input field.
     * May be empty, in which case just the bot's username will be inserted.
     * @apiNote Not supported for messages sent on behalf of a Telegram Business account.
     */
    @JsonProperty(SWITCH_INLINE_QUERY_FIELD)
    private String switchInlineQuery;
    /**
     * Optional.
     * If set, pressing the button will insert the bot's username and the specified inline query in the current
     * chat's input field.
     * May be empty, in which case only the bot's username will be inserted.
     * This offers a quick way for the user to open your bot in inline mode in the same chat -
     * good for selecting something from multiple options.
     * @apiNote Not supported in channels and for messages sent on behalf of a Telegram Business account.
     */
    @JsonProperty(SWITCH_INLINE_QUERY_CURRENT_CHAT_FIELD)
    private String switchInlineQueryCurrentChat;

    /**
     * Optional.
     * Specify True, to send a Pay button. Substrings “⭐” and “XTR” in the buttons's text will be replaced with a Telegram Star icon.
     *
     * @apiNote This type of button must always be the first button in the first row and can only be used in invoice messages.
     */
    @JsonProperty(PAY_FIELD)
    private Boolean pay;
    /**
     * Optional.
     * An HTTPS URL used to automatically authorize the user.
     * @apiNote Can be used as a replacement for the Telegram Login Widget.
     */
    @JsonProperty(LOGIN_URL_FIELD)
    private LoginUrl loginUrl;
    /**
     * Optional.
     * Description of the Web App that will be launched when the user presses the button.
     * The Web App will be able to send an arbitrary message on behalf of the user using the method answerWebAppQuery.
     * @apiNote Available only in private chats between a user and the bot.
     * @apiNote Not supported for messages sent on behalf of a Telegram Business account.
     */
    @JsonProperty(WEBAPP_FIELD)
    private WebAppInfo webApp;
    /**
     * Optional.
     * If set, pressing the button will prompt the user to select one of their chats of the specified type,
     * open that chat and insert the bot's username and the specified inline query in the input field.
     * @apiNote Not supported for messages sent on behalf of a Telegram Business account.
     */
    @JsonProperty(SWITCH_INLINE_QUERY_CHOSEN_CHAT_FIELD)
    private SwitchInlineQueryChosenChat switchInlineQueryChosenChat;


    @Override
    public void validate() throws TelegramApiValidationException {
        if (text.isEmpty()) {
            throw new TelegramApiValidationException("Text parameter can't be empty", this);
        }
        if (loginUrl != null) {
            loginUrl.validate();
        }
        if (webApp != null) {
            webApp.validate();
        }
        if (callbackGame != null) {
            callbackGame.validate();
        }
        if (switchInlineQueryChosenChat != null) {
            switchInlineQueryChosenChat.validate();
        }
    }
}
