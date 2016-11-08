package org.telegram.telegrambots.api.objects.replykeyboard.buttons;

import com.fasterxml.jackson.annotation.JsonProperty;

import org.telegram.telegrambots.api.interfaces.InputBotApiObject;
import org.telegram.telegrambots.api.interfaces.Validable;
import org.telegram.telegrambots.api.objects.games.CallbackGame;
import org.telegram.telegrambots.exceptions.TelegramApiValidationException;

/**
 * @author Ruben Bermudez
 * @version 1.0
 * @brief This object represents one button of an inline keyboard. You must use exactly one of the
 * optional fields.
 * @note This will only work in Telegram versions released after 9 April, 2016. Older clients will
 * display unsupported message.
 * @date 10 of April of 2016
 */
public class InlineKeyboardButton implements InputBotApiObject, Validable {

    private static final String TEXT_FIELD = "text";
    private static final String URL_FIELD = "url";
    private static final String CALLBACK_DATA_FIELD = "callback_data";
    private static final String CALLBACK_GAME_FIELD = "callback_game";
    private static final String SWITCH_INLINE_QUERY_FIELD = "switch_inline_query";
    private static final String SWITCH_INLINE_QUERY_CURRENT_CHAT_FIELD = "switch_inline_query_current_chat";

    @JsonProperty(TEXT_FIELD)
    private String text; ///< Label text on the button
    @JsonProperty(URL_FIELD)
    private String url; ///< Optional. HTTP url to be opened when button is pressed
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
     * @note: This offers an easy way for users to start using your bot in inline mode when
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

    public InlineKeyboardButton() {
        super();
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

    @Override
    public void validate() throws TelegramApiValidationException {
        if (text == null || text.isEmpty()) {
            throw new TelegramApiValidationException("Text parameter can't be empty", this);
        }
    }

    @Override
    public String toString() {
        return "InlineKeyboardButton{" +
                "text='" + text + '\'' +
                ", url='" + url + '\'' +
                ", callbackData='" + callbackData + '\'' +
                ", callbackGame=" + callbackGame +
                ", switchInlineQuery='" + switchInlineQuery + '\'' +
                ", switchInlineQueryCurrentChat='" + switchInlineQueryCurrentChat + '\'' +
                '}';
    }
}
