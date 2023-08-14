package org.telegram.telegrambots.extensions.bots.commandbot.activity;

public interface UserActivityHandler {
    UserActivity loadUserActivity(long id);
    void saveUserActivity(long id, UserActivity userActivity);
    UserActivity removeUserActivity(long id);
}
