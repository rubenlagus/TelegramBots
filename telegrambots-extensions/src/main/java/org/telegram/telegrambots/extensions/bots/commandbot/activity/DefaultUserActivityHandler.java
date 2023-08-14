package org.telegram.telegrambots.extensions.bots.commandbot.activity;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Default implementation for {@link UserActivityHandler} interface.
 * A concurrent hash map is used as a storage of user activity.
 *
 * @author Andrey Korsakov (loolzaaa)
 */

public class DefaultUserActivityHandler implements UserActivityHandler {

    /**
     * User activity storage, where key <-> user id
     */
    private final Map<Long, UserActivity> userActivities = new ConcurrentHashMap<>();

    /**
     * Load user activity from map by user id.
     *
     * @param id user id
     * @return user activity or null if storage map
     * contains no mapping for the key
     */

    @Override
    public UserActivity loadUserActivity(long id) {
        return userActivities.get(id);
    }

    /**
     * Save user activity in storage map.
     *
     * @param id           user id
     * @param userActivity activity of saving user
     */

    @Override
    public void saveUserActivity(long id, UserActivity userActivity) {
        userActivities.put(id, userActivity);
    }

    /**
     * Remove user activity from storage map.
     *
     * @param id user id
     * @return removed user activity, or null
     * if there was no mapping for key
     */

    @Override
    public UserActivity removeUserActivity(long id) {
        return userActivities.remove(id);
    }
}
