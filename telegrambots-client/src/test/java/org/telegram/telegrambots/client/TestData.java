package org.telegram.telegrambots.client;

import org.telegram.telegrambots.meta.api.objects.chat.Chat;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.api.objects.chat.ChatFullInfo;

public class TestData {
    public static final Chat GROUP_FULL_CHAT = ChatFullInfo
            .builder()
            .id(10L)
            .type("group")
            .title("My Group Chat")
            .description("Awesome description")
            .build();

    public static final Chat GROUP_CHAT = Chat
            .builder()
            .id(10L)
            .type("group")
            .title("My Group Chat")
            .build();

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
            null,
            null,
            false
    );
    public static final Chat PRIVATE_CHAT = ChatFullInfo
            .builder()
            .id(TEST_USER.getId())
            .type("private")
            .firstName(TEST_USER.getFirstName())
            .lastName(TEST_USER.getLastName())
            .userName(TEST_USER.getUserName())
            .build();

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
            false,
            null,
            false
    );
}
