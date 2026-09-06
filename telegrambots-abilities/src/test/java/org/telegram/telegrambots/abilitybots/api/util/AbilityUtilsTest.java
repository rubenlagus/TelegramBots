package org.telegram.telegrambots.abilitybots.api.util;

import org.junit.jupiter.api.Test;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.api.objects.boost.ChatBoost;
import org.telegram.telegrambots.meta.api.objects.boost.ChatBoostSourcePremium;
import org.telegram.telegrambots.meta.api.objects.boost.ChatBoostUpdated;
import org.telegram.telegrambots.meta.api.objects.chat.Chat;
import org.telegram.telegrambots.meta.api.objects.message.Message;
import org.telegram.telegrambots.meta.api.objects.message.MessageGenerationStopped;
import org.telegram.telegrambots.meta.api.objects.payments.BotSubscriptionUpdated;
import org.telegram.telegrambots.meta.api.objects.reactions.MessageReactionCountUpdated;
import org.telegram.telegrambots.meta.api.objects.reactions.MessageReactionUpdated;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class AbilityUtilsTest {

    @Test
    void testGetUserFromGuestMessage() {
        User user = User.builder().id(123L).firstName("Guest").isBot(false).build();
        Update update = new Update();
        update.setGuestMessage(Message.builder().from(user).build());
        assertEquals(user, AbilityUtils.getUser(update));
    }

    @Test
    void testGetChatIdFromGuestMessage() {
        User user = User.builder().id(123L).firstName("Guest").isBot(false).build();
        Update update = new Update();
        update.setGuestMessage(Message.builder()
                .from(user)
                .chat(Chat.builder().id(123L).type("private").build())
                .build());
        assertEquals(123L, AbilityUtils.getChatId(update));
    }

    @Test
    void testIsGroupUpdateFromGuestMessage() {
        Update update = new Update();
        update.setGuestMessage(Message.builder().chat(Chat.builder().id(456L).type("group").build()).build());
        assertTrue(AbilityUtils.isGroupUpdate(update));
    }

    @Test
    void testIsSuperGroupUpdateFromGuestMessage() {
        Update update = new Update();
        update.setGuestMessage(Message.builder().chat(Chat.builder().id(456L).type("supergroup").build()).build());
        assertTrue(AbilityUtils.isSuperGroupUpdate(update));
    }

    @Test
    void testGetUserFromMessageReaction() {
        User user = User.builder().id(123L).firstName("John").isBot(false).build();
        Update update = new Update();
        update.setMessageReaction(MessageReactionUpdated.builder().user(user).build());
        assertEquals(user, AbilityUtils.getUser(update));
    }

    @Test
    void testGetChatIdFromMessageReaction() {
        Chat chat = Chat.builder().id(456L).type("private").build();
        Update update = new Update();
        update.setMessageReaction(MessageReactionUpdated.builder().chat(chat).build());
        assertEquals(456L, AbilityUtils.getChatId(update));
    }

    @Test
    void testGetChatIdFromMessageReactionCount() {
        Chat chat = Chat.builder().id(456L).type("private").build();
        Update update = new Update();
        update.setMessageReactionCount(MessageReactionCountUpdated.builder().chat(chat).build());
        assertEquals(456L, AbilityUtils.getChatId(update));
    }

    @Test
    void testGetUserFromChatBoost() {
        User user = User.builder().id(123L).firstName("John").isBot(false).build();
        Update update = new Update();
        update.setChatBoost(ChatBoostUpdated.builder()
                .boost(ChatBoost.builder()
                        .source(ChatBoostSourcePremium.builder().user(user).build())
                        .build())
                .build());
        assertEquals(user, AbilityUtils.getUser(update));
    }

    @Test
    void testGetChatIdFromChatBoost() {
        Chat chat = Chat.builder().id(456L).type("private").build();
        Update update = new Update();
        update.setChatBoost(ChatBoostUpdated.builder().chat(chat).build());
        assertEquals(456L, AbilityUtils.getChatId(update));
    }

    @Test
    void testGetUserFromSubscription() {
        User user = User.builder().id(123L).firstName("John").isBot(false).build();
        Update update = new Update();
        update.setSubscription(BotSubscriptionUpdated.builder()
                .user(user)
                .invoicePayload("monthly-plan")
                .state("canceled")
                .build());
        assertEquals(user, AbilityUtils.getUser(update));
    }

    @Test
    void testGetChatIdFromSubscription() {
        User user = User.builder().id(123L).firstName("John").isBot(false).build();
        Update update = new Update();
        update.setSubscription(BotSubscriptionUpdated.builder()
                .user(user)
                .invoicePayload("monthly-plan")
                .state("active")
                .build());
        assertEquals(123L, AbilityUtils.getChatId(update));
    }

    @Test
    void testGetChatIdFromStoppedMessageGeneration() {
        Update update = new Update();
        update.setStoppedMessageGeneration(MessageGenerationStopped.builder()
                .chat(Chat.builder().id(456L).type("private").build())
                .draftId(7)
                .build());
        assertEquals(456L, AbilityUtils.getChatId(update));
    }

    /**
     * A stopped-generation update carries no originating user, so getUser must fall through to
     * EMPTY_USER rather than throwing IllegalStateException.
     */
    @Test
    void testGetUserFromStoppedMessageGenerationIsEmptyUser() {
        Update update = new Update();
        update.setStoppedMessageGeneration(MessageGenerationStopped.builder()
                .chat(Chat.builder().id(456L).type("private").build())
                .draftId(7)
                .build());
        assertEquals(AbilityUtils.EMPTY_USER, AbilityUtils.getUser(update));
    }
}
