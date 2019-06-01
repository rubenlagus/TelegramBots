package org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.telegram.telegrambots.meta.api.interfaces.InputBotApiObject;
import org.telegram.telegrambots.meta.api.interfaces.Validable;
import org.telegram.telegrambots.meta.api.objects.games.CallbackGame;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.fields.LoginUrl;
import org.telegram.telegrambots.meta.exceptions.TelegramApiValidationException;

import java.util.Objects;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * @author Ruben Bermudez
 * @version 1.0
 * This object represents one button of an inline keyboard. You must use exactly one of the
 * optional fields.
 * @note This will only work in Telegram versions released after 9 April, 2016. Older clients will
 * display unsupported message.
 */
public class InlineKeyboardButton implements InputBotApiObject, Validable {

    private static final String TEXT_FIELD = "text";
    private static final String URL_FIELD = "url";
    private static final String LOGIN_URL_FIELD = "login_url";
    private static final String CALLBACK_DATA_FIELD = "callback_data";
    private static final String CALLBACK_GAME_FIELD = "callback_game";
    private static final String SWITCH_INLINE_QUERY_FIELD = "switch_inline_query";
    private static final String SWITCH_INLINE_QUERY_CURRENT_CHAT_FIELD = "switch_inline_query_current_chat";
    private static final String PAY_FIELD = "pay";

    @JsonProperty(TEXT_FIELD)
    private String text; ///< Label text on the button
    @JsonProperty(URL_FIELD)
    private String url; ///< Optional. HTTP or tg:// url to be opened when button is pressed
    @JsonProperty(LOGIN_URL_FIELD)
    private LoginUrl loginUrl; ///< Optional. An HTTP URL used to automatically authorize the user. Can be used as a replacement for the Telegram Login Widget.
    @JsonProperty(CALLBACK_DATA_FIELD)
    private String callbackData; ///< Optional. Data to be sent in a callback query to the bot when button is pressed
    /**
     * Optional. Description of the game that will be launched when the user presses the button.
     *
     * @note This type of button must always be the first button in the first row.
     */
    @JsonProperty(CALLBACK_GAME_FIELD)
    private CallbackGame callbackGame;
    /**
     * Optional.
     * If set, pressing the button will prompt the user to select one of their chats,
     * open that chat and insert the bot‘s username and the specified inline query in the input field.
     * Can be empty, in which case just the bot’s username will be inserted.
     *
     * @note This offers an easy way for users to start using your bot in inline mode when
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
     * @note This type of button must always be the first button in the first row.
     */
    @JsonProperty(PAY_FIELD)
    private Boolean pay;

    public InlineKeyboardButton() {
        super();
    }

    public InlineKeyboardButton(String text) {
        this.text = checkNotNull(text);
    }

    public String getText() {
        return text;
    }

    public InlineKeyboardButton setText(String text) {
        this.text = text;
        return this;
    }

    public String getUrl() {
        return url;
    }

    public InlineKeyboardButton setUrl(String url) {
        this.url = url;
        return this;
    }

    public LoginUrl getLoginUrl() {
        return loginUrl;
    }

    public InlineKeyboardButton setLoginUrl(LoginUrl loginUrl) {
        this.loginUrl = loginUrl;
        return this;
    }

    public String getCallbackData() {
        return callbackData;
    }

    public InlineKeyboardButton setCallbackData(String callbackData) {
        this.callbackData = callbackData;
        return this;
    }

    public String getSwitchInlineQuery() {
        return switchInlineQuery;
    }

    public InlineKeyboardButton setSwitchInlineQuery(String switchInlineQuery) {
        this.switchInlineQuery = switchInlineQuery;
        return this;
    }

    public CallbackGame getCallbackGame() {
        return callbackGame;
    }

    public InlineKeyboardButton setCallbackGame(CallbackGame callbackGame) {
        this.callbackGame = callbackGame;
        return this;
    }

    public String getSwitchInlineQueryCurrentChat() {
        return switchInlineQueryCurrentChat;
    }

    public InlineKeyboardButton setSwitchInlineQueryCurrentChat(String switchInlineQueryCurrentChat) {
        this.switchInlineQueryCurrentChat = switchInlineQueryCurrentChat;
        return this;
    }

    public Boolean getPay() {
        return pay;
    }

    public InlineKeyboardButton setPay(Boolean pay) {
        this.pay = pay;
        return this;
    }

    @Override
    public void validate() throws TelegramApiValidationException {
        if (text == null || text.isEmpty()) {
            throw new TelegramApiValidationException("Text parameter can't be empty", this);
        }
        if (loginUrl != null) {
            loginUrl.validate();
        }
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) return true;
        if (!(o instanceof InlineKeyboardButton)) {
            return false;
        }
        InlineKeyboardButton inlineKeyboardButton = (InlineKeyboardButton) o;
        return Objects.equals(callbackData, inlineKeyboardButton.callbackData)
                && Objects.equals(callbackGame, inlineKeyboardButton.callbackGame)
                && Objects.equals(pay, inlineKeyboardButton.pay)
                && Objects.equals(switchInlineQuery, inlineKeyboardButton.switchInlineQuery)
                && Objects.equals(switchInlineQueryCurrentChat, inlineKeyboardButton.switchInlineQueryCurrentChat)
                && Objects.equals(text, inlineKeyboardButton.text)
                && Objects.equals(url, inlineKeyboardButton.url)
                && Objects.equals(loginUrl, inlineKeyboardButton.loginUrl)
                ;
    }

    @Override
    public int hashCode() {
        return Objects.hash(
                callbackData,
                callbackGame,
                pay,
                switchInlineQuery,
                switchInlineQueryCurrentChat,
                text,
                url,
                loginUrl);
    }

    @Override
    public String toString() {
        return "InlineKeyboardButton{" +
                "text='" + text + '\'' +
                ", url='" + url + '\'' +
                ", loginUrl='" + loginUrl + '\'' +
                ", callbackData='" + callbackData + '\'' +
                ", callbackGame=" + callbackGame +
                ", switchInlineQuery='" + switchInlineQuery + '\'' +
                ", switchInlineQueryCurrentChat='" + switchInlineQueryCurrentChat + '\'' +
                ", pay=" + pay +
                '}';
    }
}
