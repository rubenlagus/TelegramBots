package org.telegram.telegrambots.extensions.bots.commandbot.activity;

/**
 * This interface allows you to implement
 * a mechanism for storing and managing user activity.
 *
 * @author Andrey Korsakov (loolzaaa)
 */

public interface UserActivityHandler {

    /**
     * Load user activity by user id from some storage.
     *
     * @param id user id
     * @return activity of specified user or null if
     * there is no user activity with specified id
     */

    UserActivity loadUserActivity(long id);

    /**
     * Save user activity to some storage by user id.
     *
     * @param id           user id
     * @param userActivity activity of saving user
     */

    void saveUserActivity(long id, UserActivity userActivity);

    /**
     * Remove user activity by user id from some storage.
     *
     * @param id user id
     * @return removed user activity or null if
     * there is no user activity with specified id
     */

    UserActivity removeUserActivity(long id);
}
