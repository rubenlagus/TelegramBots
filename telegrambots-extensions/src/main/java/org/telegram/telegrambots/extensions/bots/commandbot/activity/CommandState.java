package org.telegram.telegrambots.extensions.bots.commandbot.activity;

import java.util.Objects;

/**
 * This class holds the state of a command
 * along with its identifier.
 * <p>
 * The state can be an object of any type.
 * When implementing, you need to be careful
 * about unchecked casts:
 * <pre>
 * {@code
 *     @Override
 *     public CommandState<?> processMessage(AbsSender absSender, Message message, String[] arguments, CommandState<?> commandState) {
 *          CommandState<State> currentState = (CommandState<State>) commandState;
 *     }
 *
 *     private static enum State {
 *          FIRST, SECOND;
 *     }
 * }
 * </pre>
 *
 * @param <T> command state type
 * @author Andrey Korsakov (loolzaaa)
 */

public class CommandState<T> {

    /**
     * Command identifier
     */
    private final String identifier;

    /**
     * Object represents command state
     */
    private final T state;

    /**
     * Creates new command state using identifier and some
     * object that represents current state
     *
     * @param identifier command identifier
     * @param state      command state
     */
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
