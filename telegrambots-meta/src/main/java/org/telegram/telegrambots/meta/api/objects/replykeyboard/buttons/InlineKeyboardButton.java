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
import org.telegram.telegrambots.meta.api.objects.LoginUrl;
import org.telegram.telegrambots.meta.api.objects.games.CallbackGame;
import org.telegram.telegrambots.meta.api.objects.webapp.WebAppInfo;
import org.telegram.telegrambots.meta.exceptions.TelegramApiValidationException;

/**
 * @author Ruben Bermudez
 * @version 6.1
 * This object represents one button of an inline keyboard. You must use exactly one of the
 * optional fields.
 * @apiNote This will only work in Telegram versions released after 9 April, 2016. Older clients will
 * display unsupported message.
 */
@EqualsAndHashCode(callSuper = false)
@Getter
@Setter
@ToString
@NoArgsConstructor(force = true)
@RequiredArgsConstructor
@AllArgsConstructor
@Builder
public class InlineKeyboardButton implements Validable, BotApiObject {

    private static final String TEXT_FIELD = "text";
    private static final String URL_FIELD = "url";
    private static final String CALLBACK_DATA_FIELD = "callback_data";
    private static final String CALLBACK_GAME_FIELD = "callback_game";
    private static final String SWITCH_INLINE_QUERY_FIELD = "switch_inline_query";
    private static final String SWITCH_INLINE_QUERY_CURRENT_CHAT_FIELD = "switch_inline_query_current_chat";
    private static final String PAY_FIELD = "pay";
    private static final String LOGIN_URL_FIELD = "login_url";
    private static final String WEBAPP_FIELD = "web_app";

    @JsonProperty(TEXT_FIELD)
    @NonNull
    private String text; ///< Label text on the button
    /**
     * Optional.
     * HTTP or tg:// url to be opened when the button is pressed.
     * Links tg://user?id=<user_id> can be used to mention a user by their ID without using a username,
     * if this is allowed by their privacy settings.
     */
    @JsonProperty(URL_FIELD)
    private String url;
    @JsonProperty(CALLBACK_DATA_FIELD)
    private String callbackData; ///< Optional. Data to be sent in a callback query to the bot when button is pressed
    /**
     * Optional. Description of the game that will be launched when the user presses the button.
     *
     * @apiNote This type of button must always be the first button in the first row.
     */
    @JsonProperty(CALLBACK_GAME_FIELD)
    private CallbackGame callbackGame;
    /**
     * Optional.
     * If set, pressing the button will prompt the user to select one of their chats,
     * open that chat and insert the bot‘s username and the specified inline query in the input field.
     * Can be empty, in which case just the bot’s username will be inserted.
     *
     * @apiNote This offers an easy way for users to start using your bot in inline mode when
     * they are currently in a private chat with it.
     * Especially useful when combined with switch_pm… actions – in this case the user will
     * be automatically returned to the chat they switched from, skipping the chat selection screen.
     */
    @JsonProperty(SWITCH_INLINE_QUERY_FIELD)
    private String switchInlineQuery;
    /**
     * Optional. If set, pressing the button will insert the bot‘s username and the specified
     * inline query in the current chat's input field. Can be empty,
     * in which case only the bot’s username will be inserted.
     */
    @JsonProperty(SWITCH_INLINE_QUERY_CURRENT_CHAT_FIELD)
    private String switchInlineQueryCurrentChat;

    /**
     * Optional. Specify True, to send a Buy button.
     *
     * @apiNote This type of button must always be the first button in the first row.
     */
    @JsonProperty(PAY_FIELD)
    private Boolean pay;
    /**
     * Optional.
     * An HTTPS URL used to automatically authorize the user.
     * Can be used as a replacement for the Telegram Login Widget.
     */
    @JsonProperty(LOGIN_URL_FIELD)
    private LoginUrl loginUrl;

    /**
     * Optional.
     * Description of the web app that will be launched when the user presses the button.
     * The web app will be able to send an arbitrary message on behalf of the user using the method answerWebAppQuery.
     * Available only in private chats between users and the bot.
     */
    @JsonProperty(WEBAPP_FIELD)
    private WebAppInfo webApp;

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
    }
}
