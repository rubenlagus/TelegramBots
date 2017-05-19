package org.telegram.telegrambots.api.objects;

import com.fasterxml.jackson.annotation.JsonProperty;

import org.telegram.telegrambots.api.interfaces.BotApiObject;
import org.telegram.telegrambots.api.objects.inlinequery.ChosenInlineQuery;
import org.telegram.telegrambots.api.objects.inlinequery.InlineQuery;
import org.telegram.telegrambots.api.objects.payments.PreCheckoutQuery;
import org.telegram.telegrambots.api.objects.payments.ShippingQuery;

/**
 * @author Ruben Bermudez
 * @version 1.0
 *
 * This object represents an incoming update.
 *
 * @apiNote Only one of the optional parameters can be present in any given update.
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
    private static final String SHIPPING_QUERY_FIELD = "shipping_query";
    private static final String PRE_CHECKOUT_QUERY_FIELD = "pre_checkout_query";

    @JsonProperty(UPDATEID_FIELD)
    private Integer updateId;
    @JsonProperty(MESSAGE_FIELD)
    private Message message; ///< Optional. New incoming message of any kind — text, photo, sticker, etc.
    @JsonProperty(INLINEQUERY_FIELD)
    private InlineQuery inlineQuery; ///< Optional. New incoming inline query
    @JsonProperty(CHOSENINLINEQUERY_FIELD)
    private ChosenInlineQuery chosenInlineQuery; ///< Optional. The result of a inline query that was chosen by a user and sent to their chat partner
    @JsonProperty(CALLBACKQUERY_FIELD)
    private CallbackQuery callbackQuery; ///< Optional. New incoming callback query
    @JsonProperty(EDITEDMESSAGE_FIELD)
    private Message editedMessage; ///< Optional. New version of a message that is known to the bot and was edited
    @JsonProperty(CHANNELPOST_FIELD)
    private Message channelPost; ///< Optional. New incoming channel post of any kind — text, photo, sticker, etc.
    @JsonProperty(EDITEDCHANNELPOST_FIELD)
    private Message editedChannelPost; ///< Optional. New version of a channel post that is known to the bot and was edited
    @JsonProperty(SHIPPING_QUERY_FIELD)
    private ShippingQuery shippingQuery; ///< Optional. New incoming shipping query. Only for invoices with flexible price
    @JsonProperty(PRE_CHECKOUT_QUERY_FIELD)
    private PreCheckoutQuery preCheckoutQuery; ///< Optional. New incoming pre-checkout query. Contains full information about checkout

    public Update() {
        super();
    }

    public Integer getUpdateId() {
        return updateId;
    }

    public Message getMessage() {
        return message;
    }

    public InlineQuery getInlineQuery() {
        return inlineQuery;
    }

    public ChosenInlineQuery getChosenInlineQuery() {
        return chosenInlineQuery;
    }

    public CallbackQuery getCallbackQuery() {
        return callbackQuery;
    }

    public Message getEditedMessage() {
        return editedMessage;
    }

    public Message getChannelPost() {
        return channelPost;
    }

    public Message getEditedChannelPost() {
        return editedChannelPost;
    }

    public ShippingQuery getShippingQuery() {
        return shippingQuery;
    }

    public PreCheckoutQuery getPreCheckoutQuery() {
        return preCheckoutQuery;
    }

    public boolean hasMessage() {
        return message != null;
    }

    public boolean hasInlineQuery() {
        return inlineQuery != null;
    }

    public boolean hasChosenInlineQuery() {
        return chosenInlineQuery != null;
    }

    public boolean hasCallbackQuery() {
        return callbackQuery != null;
    }

    public boolean hasEditedMessage() {
        return editedMessage != null;
    }

    public boolean hasChannelPost() {
        return channelPost != null;
    }

    public boolean hasEditedChannelPost() {
        return editedChannelPost != null;
    }

    public boolean hasShippingQuery() {
        return shippingQuery != null;
    }

    public boolean hasPreCheckoutQuery() {
        return preCheckoutQuery != null;
    }

    @Override
    public String toString() {
        return "Update{" +
                "updateId=" + updateId +
                ", message=" + message +
                ", inlineQuery=" + inlineQuery +
                ", chosenInlineQuery=" + chosenInlineQuery +
                ", callbackQuery=" + callbackQuery +
                ", editedMessage=" + editedMessage +
                ", channelPost=" + channelPost +
                ", editedChannelPost=" + editedChannelPost +
                ", shippingQuery=" + shippingQuery +
                ", preCheckoutQuery=" + preCheckoutQuery +
                '}';
    }
}
