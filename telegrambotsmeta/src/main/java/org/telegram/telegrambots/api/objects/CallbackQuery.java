package org.telegram.telegrambots.api.objects;

import com.fasterxml.jackson.annotation.JsonProperty;

import org.telegram.telegrambots.api.interfaces.BotApiObject;

/**
 * @author Ruben Bermudez
 * @version 1.0
 * @brief This object represents an incoming callback query from a
 * callback button in an inline keyboard.
 * If the button that originated the query was attached to a message sent by the bot,
 * the field message will be present. If the button was attached to a message sent via the bot
 * (in inline mode), the field inline_message_id will be present.
 * @note Exactly one of the fields data or game_short_name will be present.
 * @note  After the user presses an inline button, Telegram clients will display a progress bar
 * until you call answerCallbackQuery. It is, therefore, necessary to react by
 * calling answerCallbackQuery even if no notification to the user is needed
 * (e.g., without specifying any of the optional parameters).
 * @date 10 of April of 2016
 */
public class CallbackQuery implements BotApiObject {

    private static final String ID_FIELD = "id";
    private static final String FROM_FIELD = "from";
    private static final String MESSAGE_FIELD = "message";
    private static final String INLINE_MESSAGE_ID_FIELD = "inline_message_id";
    private static final String DATA_FIELD = "data";
    private static final String GAMESHORTNAME_FIELD = "game_short_name";
    private static final String CHAT_INSTANCE_FIELD = "chat_instance";

    @JsonProperty(ID_FIELD)
    private String id; ///< Unique identifier for this query
    @JsonProperty(FROM_FIELD)
    private User from; ///< Sender
    /**
     * Optional.
     * Message with the callback button that originated the query.
     *
     * @note The message content and message date will not be available if the message is too old
     */
    @JsonProperty(MESSAGE_FIELD)
    private Message message;
    @JsonProperty(INLINE_MESSAGE_ID_FIELD)
    private String inlineMessageId; ///< Optional. Identifier of the message sent via the bot in inline mode, that originated the query
    /**
     *
     * Optional. Data associated with the callback button.
     * @note Be aware that a bad client can send arbitrary data in this field
     */
    @JsonProperty(DATA_FIELD)
    private String data;
    /**
     * Optional. Short name of a Game to be returned, serves as the unique identifier for the game
     */
    @JsonProperty(GAMESHORTNAME_FIELD)
    private String gameShortName;
    /**
     * Identifier, uniquely corresponding to the chat to which the message with the
     * callback button was sent. Useful for high scores in games.
     */
    @JsonProperty(CHAT_INSTANCE_FIELD)
    private String chatInstance;

    public CallbackQuery() {
        super();
    }

    public String getId() {
        return this.id;
    }

    public User getFrom() {
        return this.from;
    }

    public Message getMessage() {
        return this.message;
    }

    public String getInlineMessageId() {
        return this.inlineMessageId;
    }

    public String getData() {
        return data;
    }

    public String getGameShortName() {
        return gameShortName;
    }

    public String getChatInstance() {
        return chatInstance;
    }

    @Override
    public String toString() {
        return "CallbackQuery{" +
                "id='" + id + '\'' +
                ", from=" + from +
                ", message=" + message +
                ", inlineMessageId='" + inlineMessageId + '\'' +
                ", data='" + data + '\'' +
                ", gameShortName='" + gameShortName + '\'' +
                ", chatInstance='" + chatInstance + '\'' +
                '}';
    }
}
