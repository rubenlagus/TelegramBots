package org.telegram.telegrambots.abilitybots.api.util;

import org.junit.jupiter.api.Test;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.api.objects.boost.ChatBoost;
import org.telegram.telegrambots.meta.api.objects.boost.ChatBoostRemoved;
import org.telegram.telegrambots.meta.api.objects.boost.ChatBoostSourceGiftCode;
import org.telegram.telegrambots.meta.api.objects.boost.ChatBoostSourceGiveaway;
import org.telegram.telegrambots.meta.api.objects.boost.ChatBoostSourcePremium;
import org.telegram.telegrambots.meta.api.objects.boost.ChatBoostUpdated;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Regression tests for <a href="https://github.com/rubenlagus/TelegramBots/issues/1463">#1463</a>:
 * {@code AbilityUtils.getUser} threw {@link IllegalStateException} for
 * {@code ChatBoostUpdated} / {@code ChatBoostRemoved} updates.
 */
public class TestAbilityUtils {

    private static final User BOOSTER = new User(42L, "Alice", false);

    @Test
    void getUser_returnsUserFromPremiumChatBoost() {
        Update update = new Update();
        update.setChatBoost(ChatBoostUpdated.builder()
                .boost(ChatBoost.builder()
                        .source(ChatBoostSourcePremium.builder().user(BOOSTER).build())
                        .build())
                .build());

        assertEquals(BOOSTER, AbilityUtils.getUser(update));
    }

    @Test
    void getUser_returnsUserFromGiftCodeChatBoost() {
        Update update = new Update();
        update.setChatBoost(ChatBoostUpdated.builder()
                .boost(ChatBoost.builder()
                        .source(ChatBoostSourceGiftCode.builder().user(BOOSTER).build())
                        .build())
                .build());

        assertEquals(BOOSTER, AbilityUtils.getUser(update));
    }

    @Test
    void getUser_returnsUserFromGiveawayChatBoost() {
        Update update = new Update();
        update.setChatBoost(ChatBoostUpdated.builder()
                .boost(ChatBoost.builder()
                        .source(ChatBoostSourceGiveaway.builder().user(BOOSTER).build())
                        .build())
                .build());

        assertEquals(BOOSTER, AbilityUtils.getUser(update));
    }

    @Test
    void getUser_returnsEmptyUserFromAnonymousGiveawayChatBoost() {
        // For anonymous / unclaimed giveaways the user field is null.
        Update update = new Update();
        update.setChatBoost(ChatBoostUpdated.builder()
                .boost(ChatBoost.builder()
                        .source(ChatBoostSourceGiveaway.builder().isUnclaimed(true).build())
                        .build())
                .build());

        assertEquals(AbilityUtils.EMPTY_USER, AbilityUtils.getUser(update));
    }

    @Test
    void getUser_returnsUserFromRemovedChatBoost() {
        Update update = new Update();
        update.setRemovedChatBoost(ChatBoostRemoved.builder()
                .source(ChatBoostSourcePremium.builder().user(BOOSTER).build())
                .build());

        assertEquals(BOOSTER, AbilityUtils.getUser(update));
    }
}
