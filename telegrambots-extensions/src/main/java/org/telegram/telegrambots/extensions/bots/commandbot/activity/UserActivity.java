package org.telegram.telegrambots.extensions.bots.commandbot.activity;

import java.time.LocalDateTime;
import java.util.Objects;

public class UserActivity {

    private CommandState<?> commandState = new CommandState<>(null,null);
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
    public String toString() {
        return "UserActivity{" +
                "commandState=" + commandState +
                ", lastActivity=" + lastActivity +
                '}';
    }
}
