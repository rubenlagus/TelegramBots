package org.telegram.telegrambots.meta.util;

import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.function.Function;

public enum UpdateType {
    MESSAGE(Update::getMessage),
    INLINE_QUERY(Update::getInlineQuery),
    CHOSEN_INLINE_QUERY(Update::getChosenInlineQuery),
    CALLBACK_QUERY(Update::getCallbackQuery),
    EDITED_MESSAGE(Update::getEditedMessage),
    CHANNEL_POST(Update::getChannelPost),
    EDITED_CHANNEL_POST(Update::getEditedChannelPost),
    SHIPPING_QUERY(Update::getShippingQuery),
    PRE_CHECKOUT_QUERY(Update::getPreCheckoutQuery),
    POLL(Update::getPoll),
    POLL_ANSWER(Update::getPollAnswer),
    MY_CHAT_MEMBER(Update::getMyChatMember),
    CHAT_MEMBER(Update::getChatMember),
    CHAT_JOIN_REQUEST(Update::getChatJoinRequest),
    MESSAGE_REACTION(Update::getMessageReaction),
    MESSAGE_REACTION_COUNT(Update::getMessageReactionCount),
    CHAT_BOOST(Update::getChatBoost),
    REMOVED_CHAT_BOOST(Update::getRemovedChatBoost),
    BUSINESS_CONNECTION(Update::getBusinessConnection),
    BUSINESS_MESSAGE(Update::getBusinessMessage),
    EDITED_BUSINESS_MESSAGE(Update::getEditedBuinessMessage),
    DELETED_BUSINESS_MESSAGES(Update::getDeletedBusinessMessages),
    PAID_MEDIA_PURCHASED(Update::getPaidMediaPurchased),
    UNKNOWN(u -> null);

    private final Function<Update, Object> extractor;

    UpdateType(Function<Update, Object> extractor) {
        this.extractor = extractor;
    }

    public boolean matches(Update update) {
        return extractor.apply(update) != null;
    }

    public static UpdateType from(Update update) {
        for (UpdateType type : values()) {
            if (type != UNKNOWN && type.matches(update)) {
                return type;
            }
        }
        return UNKNOWN;
    }
}
