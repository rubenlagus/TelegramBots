package org.telegram.telegrambots.extensions.bots.commandbot.commands.activity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class UserActivityTest {

    UserActivity userActivity;

    @BeforeEach
    void setUp() {
        userActivity = new UserActivity();
    }

    @Test
    void shouldCorrectSetCommandState() {
        final CommandState<Integer> expectedCommandState = new CommandState<>("TEST", 1);

        userActivity.setCommandState(expectedCommandState);
        CommandState<?> actualCommandState = userActivity.getCommandState();

        assertEquals(expectedCommandState, actualCommandState);
    }

    @Test
    void shouldThrowNPE_WhenSetNullCommandState() {
        assertThrows(NullPointerException.class, () -> userActivity.setCommandState(null));
    }

    @Test
    void shouldCorrectSetLastActivity() {
        final LocalDateTime expectedLastActivity = LocalDateTime.now();

        userActivity.setLastActivity(expectedLastActivity);
        LocalDateTime actualLastActivity = userActivity.getLastActivity();

        assertEquals(expectedLastActivity, actualLastActivity);
    }

    @Test
    void shouldThrowNPE_WhenSetNullLastActivity() {
        assertThrows(NullPointerException.class, () -> userActivity.setLastActivity(null));
    }
}