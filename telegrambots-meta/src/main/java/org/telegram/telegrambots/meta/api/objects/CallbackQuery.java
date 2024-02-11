package org.telegram.telegrambots.meta.api.objects;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.telegram.telegrambots.meta.api.interfaces.BotApiObject;
import org.telegram.telegrambots.meta.api.objects.serialization.MaybeInaccessibleMessageDeserializer;

/**
 * This object represents an incoming callback query from a
 * callback button in an inline keyboard.
 * If the button that originated the query was attached to a message sent by the bot,
 * the field message will be present. If the button was attached to a message sent via the bot
 * (in inline mode), the field inline_message_id will be present.
 * @apiNote  Exactly one of the fields data or game_short_name will be present.
 * @apiNote   After the user presses an inline button, Telegram clients will display a progress bar
 * until you call answerCallbackQuery. It is, therefore, necessary to react by
 * calling answerCallbackQuery even if no notification to the user is needed
 * (e.g., without specifying any of the optional parameters).
 * @author Ruben Bermudez
 * @version 1.0
 */
@EqualsAndHashCode(callSuper = false)
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class CallbackQuery implements BotApiObject {

    private static final String ID_FIELD = "id";
    private static final String FROM_FIELD = "from";
    private static final String MESSAGE_FIELD = "message";
    private static final String INLINE_MESSAGE_ID_FIELD = "inline_message_id";
    private static final String DATA_FIELD = "data";
    private static final String GAMESHORTNAME_FIELD = "game_short_name";
    private static final String CHAT_INSTANCE_FIELD = "chat_instance";

    /**
     * Unique identifier for this query
     */
    @JsonProperty(ID_FIELD)
    private String id;
    /**
     * Sender
     */
    @JsonProperty(FROM_FIELD)
    private User from;
    /**
     * Optional.
     * Message sent by the bot with the callback button that originated the query
     *
     * @apiNote  The message content and message date will not be available if the message is too old
     */
    @JsonProperty(MESSAGE_FIELD)
    @JsonDeserialize(using = MaybeInaccessibleMessageDeserializer.class)
    private MaybeInaccessibleMessage message;
    /**
     * Optional.
     * Identifier of the message sent via the bot in inline mode, that originated the query
     */
    @JsonProperty(INLINE_MESSAGE_ID_FIELD)
    private String inlineMessageId;
    /**
     *
     * Optional. Data associated with the callback button.
     * @apiNote  Be aware that a bad client can send arbitrary data in this field
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
}
