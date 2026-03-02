package org.telegram.telegrambots.meta.api.objects;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * @author Ruben Bermudez
 * @version 9.4
 */
public class TestUser {

    @Test
    public void testUserWithAllowsUsersToCreateTopics() {
        User user = User.builder()
                .id(12345L)
                .firstName("Test")
                .isBot(true)
                .allowsUsersToCreateTopics(true)
                .build();

        assertNotNull(user);
        assertEquals(12345L, user.getId());
        assertEquals("Test", user.getFirstName());
        assertTrue(user.getIsBot());
        assertTrue(user.getAllowsUsersToCreateTopics());
    }

    @Test
    public void testUserBuilderWithAllFields() {
        User user = User.builder()
                .id(12345L)
                .firstName("Test")
                .lastName("Bot")
                .userName("testbot")
                .isBot(true)
                .languageCode("en")
                .canJoinGroups(true)
                .canReadAllGroupMessages(false)
                .supportInlineQueries(true)
                .isPremium(false)
                .addedToAttachmentMenu(false)
                .canConnectToBusiness(true)
                .hasMainWebApp(false)
                .hasTopicsEnabled(true)
                .allowsUsersToCreateTopics(true)
                .build();

        assertEquals(12345L, user.getId());
        assertEquals("Test", user.getFirstName());
        assertEquals("Bot", user.getLastName());
        assertEquals("testbot", user.getUserName());
        assertTrue(user.getIsBot());
        assertEquals("en", user.getLanguageCode());
        assertTrue(user.getCanJoinGroups());
        assertTrue(user.getSupportInlineQueries());
        assertTrue(user.getCanConnectToBusiness());
        assertTrue(user.getHasTopicsEnabled());
        assertTrue(user.getAllowsUsersToCreateTopics());
    }
}
