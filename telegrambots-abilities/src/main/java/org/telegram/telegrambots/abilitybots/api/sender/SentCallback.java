package org.telegram.telegrambots.abilitybots.api.sender;

import org.telegram.telegrambots.meta.api.methods.botapimethods.BotApiMethod;

import java.io.Serializable;

/**
 * @author Ruben Bermudez
 * @version 1.0
 * Callback to execute api method asynchronously
 */
public interface SentCallback<T extends Serializable> {
    /**
     * Called when the request is successful
     * @param method Method executed
     * @param response Answer from Telegram server
     */
    void onResult(BotApiMethod<T> method, T response);

    /**
     * Called when an exception is thrown
     * @param method Method executed
     * @param exception Exception thrown
     */
    void onException(BotApiMethod<T> method, Exception exception);
}
