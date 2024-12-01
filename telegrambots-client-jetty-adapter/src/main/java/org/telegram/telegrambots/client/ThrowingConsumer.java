package org.telegram.telegrambots.client;

/**
 * Special type of Consumer that may throw a checked exception defined by parameter E
 */
@FunctionalInterface
public interface ThrowingConsumer<T, E extends Exception> {
    void accept(T t) throws E;
}
