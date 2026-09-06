package org.telegram.telegrambots.abilitybots.api.objects;

import org.junit.jupiter.api.Test;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.api.objects.boost.ChatBoostRemoved;
import org.telegram.telegrambots.meta.api.objects.boost.ChatBoostUpdated;
import org.telegram.telegrambots.meta.api.objects.gifts.Gift;
import org.telegram.telegrambots.meta.api.objects.gifts.GiftInfo;
import org.telegram.telegrambots.meta.api.objects.chat.Chat;
import org.telegram.telegrambots.meta.api.objects.message.Message;
import org.telegram.telegrambots.meta.api.objects.message.MessageGenerationStopped;
import org.telegram.telegrambots.meta.api.objects.payments.BotSubscriptionUpdated;
import org.telegram.telegrambots.meta.api.objects.reactions.MessageReactionCountUpdated;
import org.telegram.telegrambots.meta.api.objects.reactions.MessageReactionUpdated;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class FlagTest {

    @Test
    void testMessageReactionFlag() {
        Update update = new Update();
        update.setMessageReaction(new MessageReactionUpdated());
        assertTrue(Flag.MESSAGE_REACTION.test(update));
    }

    @Test
    void testMessageReactionCountFlag() {
        Update update = new Update();
        update.setMessageReactionCount(new MessageReactionCountUpdated());
        assertTrue(Flag.MESSAGE_REACTION_COUNT.test(update));
    }

    @Test
    void testChatBoostFlag() {
        Update update = new Update();
        update.setChatBoost(new ChatBoostUpdated());
        assertTrue(Flag.CHAT_BOOST.test(update));
    }

    @Test
    void testRemovedChatBoostFlag() {
        Update update = new Update();
        update.setRemovedChatBoost(new ChatBoostRemoved());
        assertTrue(Flag.REMOVED_CHAT_BOOST.test(update));
    }

    @Test
    void testGiftFlag() {
        Update update = new Update();
        Message message = new Message();
        message.setGift(GiftInfo.builder()
                .gift(Gift.builder().id("id").sticker(new org.telegram.telegrambots.meta.api.objects.stickers.Sticker()).starCount(1).build())
                .convertStarCount(1)
                .build());
        update.setMessage(message);
        assertTrue(Flag.GIFT.test(update));
    }

    @Test
    void testSubscriptionFlag() {
        Update update = new Update();
        update.setSubscription(BotSubscriptionUpdated.builder()
                .user(User.builder().id(123L).firstName("John").isBot(false).build())
                .invoicePayload("monthly-plan")
                .state("canceled")
                .build());
        assertTrue(Flag.HAS_SUBSCRIPTION.test(update));
    }

    @Test
    void testSubscriptionFlagIsFalseWithoutSubscription() {
        assertFalse(Flag.HAS_SUBSCRIPTION.test(new Update()));
    }

    @Test
    void testStoppedMessageGenerationFlag() {
        Update update = new Update();
        update.setStoppedMessageGeneration(MessageGenerationStopped.builder()
                .chat(Chat.builder().id(456L).type("private").build())
                .draftId(7)
                .build());
        assertTrue(Flag.STOPPED_MESSAGE_GENERATION.test(update));
    }

    @Test
    void testStoppedMessageGenerationFlagIsFalseWithoutIt() {
        assertFalse(Flag.STOPPED_MESSAGE_GENERATION.test(new Update()));
    }
}
