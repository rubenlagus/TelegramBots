package org.telegram.telegrambots.extensions.bots.commandbot.commands.activity;

import java.util.HashMap;
import java.util.Map;

public class DefaultUserActivityHandler implements UserActivityHandler {

    private final Map<Long, UserActivity> userActivities = new HashMap<>();

    @Override
    public UserActivity loadUserActivity(long id) {
        return userActivities.get(id);
    }

    @Override
    public void saveUserActivity(long id, UserActivity userActivity) {
        userActivities.put(id, userActivity);
    }

    @Override
    public UserActivity removeUserActivity(long id) {
        return userActivities.remove(id);
    }
}
