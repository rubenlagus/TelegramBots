package org.telegram.telegrambots.abilitybots.api.util;

import org.junit.jupiter.api.Test;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.api.objects.boost.ChatBoost;
import org.telegram.telegrambots.meta.api.objects.boost.ChatBoostSourcePremium;
import org.telegram.telegrambots.meta.api.objects.boost.ChatBoostUpdated;
import org.telegram.telegrambots.meta.api.objects.chat.Chat;
import org.telegram.telegrambots.meta.api.objects.reactions.MessageReactionCountUpdated;
import org.telegram.telegrambots.meta.api.objects.reactions.MessageReactionUpdated;

import static org.junit.jupiter.api.Assertions.assertEquals;

class AbilityUtilsTest {

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
}
