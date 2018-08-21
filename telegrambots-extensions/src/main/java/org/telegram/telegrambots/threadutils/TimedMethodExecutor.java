package org.telegram.telegrambots.threadutils;


import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.PartialBotApiMethod;
import org.telegram.telegrambots.meta.bots.AbsSender;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.Serializable;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.function.Consumer;

/**
 * Baseclass to execute BotMethods in an asynchronous and timed fashion. Use Subclasses for different kinds of scheduling.
 * @param <T> The return type of the Bot Method. For example Message for SendMessage
 */
public abstract class TimedMethodExecutor<T extends Serializable> implements Runnable {
    BotApiMethod<T> method;
    private Consumer<T> callback;
    private Consumer<Exception> errorCallback;
    protected AbsSender bot;

    public TimedMethodExecutor() {
    }

    public TimedMethodExecutor(BotApiMethod<T> method, Consumer<T> callback, Consumer<Exception> errorCallback, AbsSender bot) {
        this.method = method;
        this.callback = callback;
        this.errorCallback = errorCallback;
        this.bot = bot;
    }

    public PartialBotApiMethod<T> getMethod() {
        return method;
    }

    public TimedMethodExecutor<T> setMethod(BotApiMethod<T> method) {
        this.method = method;
        return this;
    }

    public Consumer<T> getCallback() {
        return callback;
    }

    public TimedMethodExecutor<T> setCallback(Consumer<T> callback) {
        this.callback = callback;
        return this;
    }

    public AbsSender getBot() {
        return bot;
    }

    public TimedMethodExecutor<T> setBot(AbsSender bot) {
        this.bot = bot;
        return this;
    }

    public Consumer<Exception> getErrorCallback() {
        return errorCallback;
    }

    public TimedMethodExecutor<T> setErrorCallback(Consumer<Exception> errorCallback) {
        this.errorCallback = errorCallback;
        return this;
    }

    /**
     * Schedules the Bot Method for execution.
     * @param service the SchedulesExecutorService to be used to schedule the execution
     * @return The ScheduledFuture to interface with the schedules operation.
     */
    public abstract ScheduledFuture<?> schedule(ScheduledExecutorService service);

    /**
     * Executes the bot method
     */
    @Override
    public void run() {
        try {
            T returnValue;

            returnValue = bot.execute(method);

            if (callback != null) {
                callback.accept(returnValue);
            }
        } catch (TelegramApiException e) {
            errorCallback.accept(e);
        }
    }
}
