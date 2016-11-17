package org.telegram.telegrambots.updateshandlers;

import org.json.JSONObject;
import org.telegram.telegrambots.api.methods.BotApiMethod;

import java.io.Serializable;

/**
 * @author Ruben Bermudez
 * @version 1.0
 * @brief Callback to execute api method asynchronously
 * @date 10 of September of 2015
 */
public interface SentCallback<T extends Serializable> {
    /**
     * Called when the request is successful
     * @param method Method executed
     * @param jsonObject Answer from Telegram server
     */
    void onResult(BotApiMethod<T> method, JSONObject jsonObject);

    /**
     * Called when the request fails
     * @param method Method executed
     * @param jsonObject Answer from Telegram server (contains error information)
     */
    void onError(BotApiMethod<T> method, JSONObject jsonObject);

    /**
     * Called when the http request throw an exception
     * @param method Method executed
     * @param exception Excepction thrown
     */
    void onException(BotApiMethod<T> method, Exception exception);
}
