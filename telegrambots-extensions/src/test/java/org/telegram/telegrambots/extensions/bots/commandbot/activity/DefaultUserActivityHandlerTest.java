package org.telegram.telegrambots.extensions.bots.commandbot.activity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class DefaultUserActivityHandlerTest {

    DefaultUserActivityHandler underTest;

    @BeforeEach
    void setUp() {
        underTest = new DefaultUserActivityHandler();
    }

    @Test
    void shouldCorrectSaveLoadRemoveActivity() {
        final long id = 1L;
        final CommandState<Integer> commandState = new CommandState<>("TEST", 1);
        final LocalDateTime lastActivity = LocalDateTime.of(2023, 1, 1, 0, 0, 0, 0);
        final UserActivity expectedUserActivity = new UserActivity();
        expectedUserActivity.setCommandState(commandState);
        expectedUserActivity.setLastActivity(lastActivity);

        underTest.saveUserActivity(id, expectedUserActivity);
        UserActivity actualUserActivity = underTest.loadUserActivity(id);

        assertNotNull(actualUserActivity);
        assertEquals(expectedUserActivity, actualUserActivity);

        underTest.removeUserActivity(id);
        actualUserActivity = underTest.loadUserActivity(id);

        assertNull(actualUserActivity);
    }
}