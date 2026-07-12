package org.telegram.telegrambots.abilitybots.api.objects;

import org.junit.jupiter.api.Test;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.boost.ChatBoostRemoved;
import org.telegram.telegrambots.meta.api.objects.boost.ChatBoostUpdated;
import org.telegram.telegrambots.meta.api.objects.gifts.Gift;
import org.telegram.telegrambots.meta.api.objects.gifts.GiftInfo;
import org.telegram.telegrambots.meta.api.objects.message.Message;
import org.telegram.telegrambots.meta.api.objects.reactions.MessageReactionCountUpdated;
import org.telegram.telegrambots.meta.api.objects.reactions.MessageReactionUpdated;

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
}
