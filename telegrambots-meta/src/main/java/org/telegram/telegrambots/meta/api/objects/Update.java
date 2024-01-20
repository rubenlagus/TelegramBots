package org.telegram.telegrambots.meta.api.objects;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.telegram.telegrambots.meta.api.interfaces.BotApiObject;
import org.telegram.telegrambots.meta.api.objects.boost.ChatBoostRemoved;
import org.telegram.telegrambots.meta.api.objects.boost.ChatBoostUpdated;
import org.telegram.telegrambots.meta.api.objects.inlinequery.ChosenInlineQuery;
import org.telegram.telegrambots.meta.api.objects.inlinequery.InlineQuery;
import org.telegram.telegrambots.meta.api.objects.payments.PreCheckoutQuery;
import org.telegram.telegrambots.meta.api.objects.payments.ShippingQuery;
import org.telegram.telegrambots.meta.api.objects.polls.Poll;
import org.telegram.telegrambots.meta.api.objects.polls.PollAnswer;
import org.telegram.telegrambots.meta.api.objects.reactions.MessageReactionCountUpdated;
import org.telegram.telegrambots.meta.api.objects.reactions.MessageReactionUpdated;

/**
 * This object represents an incoming update.
 * @apiNote Only one of the optional parameters can be present in any given update.
 * @author Ruben Bermudez
 * @version 1.0
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
    private static final String CHATJOINREQUEST_FIELD = "chat_join_request";
    private static final String MESSAGE_REACTION_FIELD = "message_reaction";
    private static final String MESSAGE_REACTION_COUNT_FIELD = "message_reaction_count";
    private static final String CHAT_BOOST_FIELD = "chat_boost";
    private static final String REMOVED_CHAT_BOOST_FIELD = "removed_chat_boost";

    @JsonProperty(UPDATEID_FIELD)
    private Integer updateId;
    /**
     * Optional.
     * New incoming message of any kind — text, photo, sticker, etc.
     */
    @JsonProperty(MESSAGE_FIELD)
    private Message message;
    /**
     * Optional.
     * New incoming inline query
     */
    @JsonProperty(INLINEQUERY_FIELD)
    private InlineQuery inlineQuery;
    /**
     * Optional.
     * The result of an inline query that was chosen by a user and sent to their chat partner
     */
    @JsonProperty(CHOSENINLINEQUERY_FIELD)
    private ChosenInlineQuery chosenInlineQuery;
    /**
     * Optional.
     * New incoming callback query
     */
    @JsonProperty(CALLBACKQUERY_FIELD)
    private CallbackQuery callbackQuery;
    /**
     * Optional.
     * New version of a message that is known to the bot and was edited
     */
    @JsonProperty(EDITEDMESSAGE_FIELD)
    private Message editedMessage;
    /**
     * Optional.
     * New incoming channel post of any kind — text, photo, sticker, etc.
     */
    @JsonProperty(CHANNELPOST_FIELD)
    private Message channelPost;
    /**
     * Optional.
     * New version of a channel post that is known to the bot and was edited
     */
    @JsonProperty(EDITEDCHANNELPOST_FIELD)
    private Message editedChannelPost;
    /**
     * Optional.
     * New incoming shipping query. Only for invoices with flexible price
     */
    @JsonProperty(SHIPPING_QUERY_FIELD)
    private ShippingQuery shippingQuery;
    /**
     * Optional.
     * New incoming pre-checkout query. Contains full information about checkout
     */
    @JsonProperty(PRE_CHECKOUT_QUERY_FIELD)
    private PreCheckoutQuery preCheckoutQuery;
    /**
     * Optional.
     * New poll state. Bots receive only updates about polls, which are sent by the bot.
     */
    @JsonProperty(POLL_FIELD)
    private Poll poll;
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
     * The bot's chat member status was updated in a chat.
     * For private chats, this update is received only when the bot is blocked or unblocked by the user.
     */
    @JsonProperty(MYCHATMEMBER_FIELD)
    private ChatMemberUpdated myChatMember;
    /**
     * Optional.
     * A chat member's status was updated in a chat.
     * The bot must be an administrator in the chat and must explicitly specify “chat_member” in the list of allowed_updates to receive these updates.
     */
    @JsonProperty(CHATMEMBER_FIELD)
    private ChatMemberUpdated chatMember;
    @JsonProperty(CHATJOINREQUEST_FIELD)
    private ChatJoinRequest chatJoinRequest;
    /**
     * Optional.
     * A reaction to a message was changed by a user.
     * The bot must be an administrator in the chat and must explicitly specify "message_reaction" in the list of
     * allowed_updates to receive these updates.
     * The update isn't received for reactions set by bots.
     */
    @JsonProperty(MESSAGE_REACTION_FIELD)
    private MessageReactionUpdated messageReaction;
    /**
     * Optional.
     * Reactions to a message with anonymous reactions were changed.
     * The bot must be an administrator in the chat and must explicitly specify "message_reaction_count" in the
     * list of allowed_updates to receive these updates.
     */
    @JsonProperty(MESSAGE_REACTION_COUNT_FIELD)
    private MessageReactionCountUpdated messageReactionCount;
    /**
     * Optional.
     * A chat boost was added or changed.
     * The bot must be an administrator in the chat to receive these updates.
     */
    @JsonProperty(CHAT_BOOST_FIELD)
    private ChatBoostUpdated chatBoost;
    /**
     * Optional.
     * A boost was removed from a chat.
     * The bot must be an administrator in the chat to receive these updates.
     */
    @JsonProperty(REMOVED_CHAT_BOOST_FIELD)
    private ChatBoostRemoved removedChatBoost;

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

    public boolean hasChatJoinRequest() {
        return chatJoinRequest != null;
    }
}
