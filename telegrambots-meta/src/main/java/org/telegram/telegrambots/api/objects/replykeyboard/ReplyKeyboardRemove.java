package org.telegram.telegrambots.api.objects.replykeyboard;

import com.fasterxml.jackson.annotation.JsonProperty;

import org.telegram.telegrambots.exceptions.TelegramApiValidationException;

/**
 * @author Ruben Bermudez
 * @version 1.0
 * @brief Upon receiving a message with this object, Telegram clients will hide the current custom
 * keyboard and display the default letter-keyboard. By default, custom keyboards are displayed
 * until a new keyboard is sent by a bot. An exception is made for one-time keyboards that are
 * hidden immediately after the user presses a button (@see ReplyKeyboardMarkup).
 * @date 20 of June of 2015
 */
public class ReplyKeyboardRemove implements ReplyKeyboard {
    private static final String REMOVEKEYBOARD_FIELD = "remove_keyboard";
    private static final String SELECTIVE_FIELD = "selective";

    @JsonProperty(REMOVEKEYBOARD_FIELD)
    private Boolean removeKeyboard; ///< Requests clients to remove the custom keyboard
    /**
     * Optional. Use this parameter if you want to show the keyboard to specific users only.
     * Targets: 1) users that are @mentioned in the text of the Message object; 2) if the bot's
     * message is a reply (has reply_to_message_id), sender of the original message.
     */
    @JsonProperty(SELECTIVE_FIELD)
    private Boolean selective;

    public ReplyKeyboardRemove() {
        super();
        this.removeKeyboard = true;
    }

    public Boolean getRemoveKeyboard() {
        return removeKeyboard;
    }

    public Boolean getSelective() {
        return selective;
    }

    public ReplyKeyboardRemove setSelective(Boolean selective) {
        this.selective = selective;
        return this;
    }

    @Override
    public void validate() throws TelegramApiValidationException {
        if (removeKeyboard == null) {
            throw new TelegramApiValidationException("RemoveKeyboard parameter can't be null", this);
        }
    }

    @Override
    public String toString() {
        return "ReplyKeyboardRemove{" +
                "removeKeyboard=" + removeKeyboard +
                ", selective=" + selective +
                '}';
    }
}
