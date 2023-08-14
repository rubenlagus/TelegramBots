package org.telegram.telegrambots.extensions.bots.commandbot.activity;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 * This class reflects user activity.
 * Instances contain the state of the last active command,
 * as well as the time the user was last active.
 *
 * @author Andrey Korsakov (loolzaaa)
 * @see CommandState
 */

public class UserActivity {

    /**
     * Object holds the state of a command.
     *
     * @see CommandState
     */
    private CommandState<?> commandState = new CommandState<>(null, null);
    /**
     * Last user activity date and time
     */
    private LocalDateTime lastActivity = LocalDateTime.now();

    public CommandState<?> getCommandState() {
        return commandState;
    }

    public void setCommandState(CommandState<?> commandState) {
        Objects.requireNonNull(commandState, "commandState must not be null");
        this.commandState = commandState;
    }

    public LocalDateTime getLastActivity() {
        return lastActivity;
    }

    public void setLastActivity(LocalDateTime lastActivity) {
        Objects.requireNonNull(lastActivity, "lastActivity must not be null");
        this.lastActivity = lastActivity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserActivity that = (UserActivity) o;
        return Objects.equals(getCommandState(), that.getCommandState()) && Objects.equals(getLastActivity(), that.getLastActivity());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getCommandState(), getLastActivity());
    }

    @Override
    public String toString() {
        return "UserActivity{" +
                "commandState=" + commandState +
                ", lastActivity=" + lastActivity +
                '}';
    }
}
