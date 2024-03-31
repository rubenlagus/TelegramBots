package org.telegram.telegrambots.client;

import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.User;

public class TestData {
    public static final Chat GROUP_CHAT = new Chat(
            10L,
            "group",
            "My Group Chat",
            null,
            null,
            null,
            null,
            "Awesome description",
            null,
            null,
            null,
            false,
            null,
            null,
            null,
            null,
            null,
            null,
            null,
            null,
            null,
            null,
            null,
            null,
            null,
            null,
            null,
            null,
            null
    );

    public static final User TEST_USER = new User(
            1000L,
            "Test",
            false,
            "User",
            "testUser",
            "en",
            null,
            null,
            null,
            false,
            null
    );
    public static final Chat PRIVATE_CHAT = new Chat(
            TEST_USER.getId(),
            "private",
            null,
            TEST_USER.getFirstName(),
            TEST_USER.getLastName(),
            TEST_USER.getUserName(),
            null,
            null,
            null,
            null,
            null,
            null,
            null,
            null,
            null,
            null,
            null,
            null,
            null,
            null,
            null,
            null,
            null,
            null,
            null,
            null,
            null,
            null,
            null
    );


    public static final User BOT_USER = new User(
            5000L,
            "My Bot",
            true,
            null,
            "myBot",
            null,
            true,
            true,
            false,
            null,
            false
    );
}
