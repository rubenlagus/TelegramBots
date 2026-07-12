package org.telegram.telegrambots.meta.util;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.ChatJoinRequest;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.boost.ChatBoostRemoved;
import org.telegram.telegrambots.meta.api.objects.boost.ChatBoostUpdated;
import org.telegram.telegrambots.meta.api.objects.business.BusinessConnection;
import org.telegram.telegrambots.meta.api.objects.business.BusinessMessagesDeleted;
import org.telegram.telegrambots.meta.api.objects.chatmember.ChatMemberUpdated;
import org.telegram.telegrambots.meta.api.objects.inlinequery.ChosenInlineQuery;
import org.telegram.telegrambots.meta.api.objects.inlinequery.InlineQuery;
import org.telegram.telegrambots.meta.api.objects.message.Message;
import org.telegram.telegrambots.meta.api.objects.payments.PaidMediaPurchased;
import org.telegram.telegrambots.meta.api.objects.payments.PreCheckoutQuery;
import org.telegram.telegrambots.meta.api.objects.payments.ShippingQuery;
import org.telegram.telegrambots.meta.api.objects.polls.Poll;
import org.telegram.telegrambots.meta.api.objects.polls.PollAnswer;
import org.telegram.telegrambots.meta.api.objects.reactions.MessageReactionCountUpdated;
import org.telegram.telegrambots.meta.api.objects.reactions.MessageReactionUpdated;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;

class UpdateTypeTest {

    @ParameterizedTest
    @MethodSource("testMapping")
    void testMappingFromUpdate(Update update, UpdateType expected) {
        // when
        UpdateType actual = UpdateType.from(update);

        // then
        assertEquals(expected, actual);
    }

    private static Stream<Arguments> testMapping() {
        return Stream.of(
                Arguments.of(updateWith(u -> u.setMessage(mock(Message.class))), UpdateType.MESSAGE),
                Arguments.of(updateWith(u -> u.setInlineQuery(mock(InlineQuery.class))), UpdateType.INLINE_QUERY),
                Arguments.of(updateWith(u -> u.setChosenInlineQuery(mock(ChosenInlineQuery.class))), UpdateType.CHOSEN_INLINE_QUERY),
                Arguments.of(updateWith(u -> u.setCallbackQuery(mock(CallbackQuery.class))), UpdateType.CALLBACK_QUERY),
                Arguments.of(updateWith(u -> u.setEditedMessage(mock(Message.class))), UpdateType.EDITED_MESSAGE),
                Arguments.of(updateWith(u -> u.setChannelPost(mock(Message.class))), UpdateType.CHANNEL_POST),
                Arguments.of(updateWith(u -> u.setEditedChannelPost(mock(Message.class))), UpdateType.EDITED_CHANNEL_POST),
                Arguments.of(updateWith(u -> u.setShippingQuery(mock(ShippingQuery.class))), UpdateType.SHIPPING_QUERY),
                Arguments.of(updateWith(u -> u.setPreCheckoutQuery(mock(PreCheckoutQuery.class))), UpdateType.PRE_CHECKOUT_QUERY),
                Arguments.of(updateWith(u -> u.setPoll(mock(Poll.class))), UpdateType.POLL),
                Arguments.of(updateWith(u -> u.setPollAnswer(mock(PollAnswer.class))), UpdateType.POLL_ANSWER),
                Arguments.of(updateWith(u -> u.setMyChatMember(mock(ChatMemberUpdated.class))), UpdateType.MY_CHAT_MEMBER),
                Arguments.of(updateWith(u -> u.setChatMember(mock(ChatMemberUpdated.class))), UpdateType.CHAT_MEMBER),
                Arguments.of(updateWith(u -> u.setChatJoinRequest(mock(ChatJoinRequest.class))), UpdateType.CHAT_JOIN_REQUEST),
                Arguments.of(updateWith(u -> u.setMessageReaction(mock(MessageReactionUpdated.class))), UpdateType.MESSAGE_REACTION),
                Arguments.of(updateWith(u -> u.setMessageReactionCount(mock(MessageReactionCountUpdated.class))), UpdateType.MESSAGE_REACTION_COUNT),
                Arguments.of(updateWith(u -> u.setChatBoost(mock(ChatBoostUpdated.class))), UpdateType.CHAT_BOOST),
                Arguments.of(updateWith(u -> u.setRemovedChatBoost(mock(ChatBoostRemoved.class))), UpdateType.REMOVED_CHAT_BOOST),
                Arguments.of(updateWith(u -> u.setBusinessConnection(mock(BusinessConnection.class))), UpdateType.BUSINESS_CONNECTION),
                Arguments.of(updateWith(u -> u.setBusinessMessage(mock(Message.class))), UpdateType.BUSINESS_MESSAGE),
                Arguments.of(updateWith(u -> u.setEditedBuinessMessage(mock(Message.class))), UpdateType.EDITED_BUSINESS_MESSAGE),
                Arguments.of(updateWith(u -> u.setDeletedBusinessMessages(mock(BusinessMessagesDeleted.class))), UpdateType.DELETED_BUSINESS_MESSAGES),
                Arguments.of(updateWith(u -> u.setPaidMediaPurchased(mock(PaidMediaPurchased.class))), UpdateType.PAID_MEDIA_PURCHASED),
                Arguments.of(new Update(), UpdateType.UNKNOWN)
        );
    }

    private static Update updateWith(java.util.function.Consumer<Update> setup) {
        Update update = new Update();
        setup.accept(update);
        return update;
    }
}