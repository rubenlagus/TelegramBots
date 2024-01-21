package org.telegram.telegrambots.longpolling.interfaces;

/**
 * @author Calvin
 * @version 1.0
 * Interface for backoff retries
 */
public interface BackOff {

    /**
     * Should be able to reset to the starting
     */
    void reset();

    /**
     * specify the next backoff interval in milliseconds
     */
    long nextBackOffMillis();
}
