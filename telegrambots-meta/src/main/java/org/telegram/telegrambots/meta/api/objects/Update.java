package org.telegram.telegrambots.meta.api.objects;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.telegram.telegrambots.meta.api.interfaces.BotApiObject;
import org.telegram.telegrambots.meta.api.objects.inlinequery.ChosenInlineQuery;
import org.telegram.telegrambots.meta.api.objects.inlinequery.InlineQuery;
import org.telegram.telegrambots.meta.api.objects.payments.PreCheckoutQuery;
import org.telegram.telegrambots.meta.api.objects.payments.ShippingQuery;
import org.telegram.telegrambots.meta.api.objects.polls.Poll;
import org.telegram.telegrambots.meta.api.objects.polls.PollAnswer;

/**
 * @author Ruben Bermudez
 * @version 1.0
 *
 * This object represents an incoming update.
 *
 * @apiNote Only one of the optional parameters can be present in any given update.
 */
@EqualsAndHashCode(callSuper = false)
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
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
    private static final String POLL_FIELD = "poll";
    private static final String POLLANSWER_FIELD = "poll_answer";
    private static final String MYCHATMEMBER_FIELD = "my_chat_member";
    private static final String CHATMEMBER_FIELD = "chat_member";

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
    @JsonProperty(POLL_FIELD)
    private Poll poll; ///< Optional. New poll state. Bots receive only updates about polls, which are sent by the bot.
    /**
     * Optional.
     * A user changed their answer in a non-anonymous poll.
     *
     * @apiNote Bots receive new votes only in polls that were sent by the bot itself.
     */
    @JsonProperty(POLLANSWER_FIELD)
    private PollAnswer pollAnswer;
    /**
     * Optional.
     *
     * The bot's chat member status was updated in a chat.
     * For private chats, this update is received only when the bot is blocked or unblocked by the user.
     */
    @JsonProperty(MYCHATMEMBER_FIELD)
    private ChatMemberUpdated myChatMember;
    /**
     * Optional.
     *
     * A chat member's status was updated in a chat.
     * The bot must be an administrator in the chat and must explicitly specify “chat_member” in the list of allowed_updates to receive these updates.
     */
    @JsonProperty(CHATMEMBER_FIELD)
    private ChatMemberUpdated chatMember;

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

    public boolean hasPoll() {
        return poll != null;
    }

    public boolean hasPollAnswer() {
        return pollAnswer != null;
    }

    public boolean hasMyChatMember() {
        return myChatMember != null;
    }

    public boolean hasChatMember() {
        return chatMember != null;
    }
}
