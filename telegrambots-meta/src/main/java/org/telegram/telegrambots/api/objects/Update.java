package org.telegram.telegrambots.api.objects;



import org.telegram.telegrambots.api.interfaces.BotApiObject;
import org.telegram.telegrambots.api.objects.inlinequery.ChosenInlineQuery;
import org.telegram.telegrambots.api.objects.inlinequery.InlineQuery;

/**
 * @author Ruben Bermudez
 * @version 1.0
 * @brief This object represents an incoming update.
 * Only one of the optional parameters can be present in any given update.
 * @date 20 of June of 2015
 */
public class Update implements BotApiObject {
    private static final String UPDATEID_FIELD = "update_id";
    private static final String MESSAGE_FIELD = "message";
    private static final String INLINEQUERY_FIELD = "inline_query";
    private static final String CHOSENINLINEQUERY_FIELD = "chosen_inline_result";
    private static final String CALLBACKQUERY_FIELD = "callback_query";
    private static final String EDITEDMESSAGE_FIELD = "edited_message";
    private static final String CHANNELPOST_FIELD = "channel_post";
    private static final String EDITEDCHANNELPOST_FIELD = "edited_channel_post";


    private Integer update_id;

    private Message message; ///< Optional. New incoming message of any kind — text, photo, sticker, etc.

    private InlineQuery inline_query; ///< Optional. New incoming inline query

    private ChosenInlineQuery chosen_inline_query; ///< Optional. The result of a inline query that was chosen by a user and sent to their chat partner

    private CallbackQuery callback_query; ///< Optional. New incoming callback query

    private Message edited_message; ///< Optional. New version of a message that is known to the bot and was edited

    private Message channel_post; ///< Optional. New incoming channel post of any kind — text, photo, sticker, etc.

    private Message edited_channel_post; ///< Optional. New version of a channel post that is known to the bot and was edited


    public Update() {
        super();
    }

    public Integer getUpdateId() {
        return update_id;
    }

    public Message getMessage() {
        return message;
    }

    public InlineQuery getInlineQuery() {
        return inline_query;
    }

    public ChosenInlineQuery getChosenInlineQuery() {
        return chosen_inline_query;
    }

    public CallbackQuery getCallbackQuery() {
        return callback_query;
    }

    public Message getEditedMessage() {
        return edited_message;
    }

    public Message getChannelPost() {
        return channel_post;
    }

    public Message getEditedChannelPost() {
        return edited_channel_post;
    }

    public boolean hasMessage() {
        return message != null;
    }

    public boolean hasInlineQuery() {
        return inline_query != null;
    }

    public boolean hasChosenInlineQuery() {
        return chosen_inline_query != null;
    }

    public boolean hasCallbackQuery() {
        return callback_query != null;
    }

    public boolean hasEditedMessage() {
        return edited_message != null;
    }

    public boolean hasChannelPost() {
        return channel_post != null;
    }

    public boolean hasEditedChannelPost() {
        return edited_channel_post != null;
    }

    @Override
    public String toString() {
        return "Update{" +
                "updateId=" + update_id +
                ", message=" + message +
                ", inlineQuery=" + inline_query +
                ", chosenInlineQuery=" + chosen_inline_query +
                ", callbackQuery=" + callback_query +
                ", editedMessage=" + edited_message +
                ", channelPost=" + channel_post +
                ", editedChannelPost=" + edited_channel_post +
                '}';
    }
}
