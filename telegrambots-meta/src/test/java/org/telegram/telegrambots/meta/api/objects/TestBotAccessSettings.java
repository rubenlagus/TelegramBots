package org.telegram.telegrambots.meta.api.objects;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * @author Ruben Bermudez
 * @version 10.0
 */
public class TestBotAccessSettings {

    @Test
    public void testBotAccessSettingsDefaultConstructor() {
        BotAccessSettings settings = new BotAccessSettings();

        assertNull(settings.getIsAccessRestricted());
        assertNull(settings.getAddedUsers());
    }

    @Test
    public void testBotAccessSettingsWithAccessRestricted() {
        BotAccessSettings settings = BotAccessSettings.builder()
                .isAccessRestricted(true)
                .build();

        assertTrue(settings.getIsAccessRestricted());
        assertNull(settings.getAddedUsers());
    }

    @Test
    public void testBotAccessSettingsWithAddedUsers() {
        User user1 = User.builder()
                .id(111L)
                .firstName("User1")
                .isBot(false)
                .build();
        User user2 = User.builder()
                .id(222L)
                .firstName("User2")
                .isBot(false)
                .build();

        BotAccessSettings settings = BotAccessSettings.builder()
                .isAccessRestricted(true)
                .addedUsers(Arrays.asList(user1, user2))
                .build();

        assertTrue(settings.getIsAccessRestricted());
        assertEquals(2, settings.getAddedUsers().size());
        assertEquals(111L, settings.getAddedUsers().get(0).getId());
        assertEquals(222L, settings.getAddedUsers().get(1).getId());
    }

    @Test
    public void testBotAccessSettingsAllArgsConstructor() {
        User user = User.builder()
                .id(111L)
                .firstName("User1")
                .isBot(false)
                .build();

        BotAccessSettings settings = new BotAccessSettings(false, List.of(user));

        assertEquals(false, settings.getIsAccessRestricted());
        assertEquals(1, settings.getAddedUsers().size());
    }
}
