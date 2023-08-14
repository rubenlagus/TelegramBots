package org.telegram.telegrambots.extensions.bots.commandbot.activity;

import java.util.Objects;

public class CommandState<T> {

    private final String identifier;
    private final T state;

    public CommandState(String identifier, T state) {
        this.identifier = identifier;
        this.state = state;
    }

    public String getIdentifier() {
        return identifier;
    }

    public T getState() {
        return state;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CommandState<?> that = (CommandState<?>) o;
        return Objects.equals(getIdentifier(), that.getIdentifier()) && Objects.equals(getState(), that.getState());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getIdentifier(), getState());
    }

    @Override
    public String toString() {
        return "CommandState{" +
                "identifier='" + identifier + '\'' +
                ", state=" + state +
                '}';
    }
}
