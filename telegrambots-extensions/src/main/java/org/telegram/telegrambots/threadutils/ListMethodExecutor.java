package org.telegram.telegrambots.threadutils;

import org.telegram.telegrambots.api.methods.BotApiMethod;
import org.telegram.telegrambots.bots.AbsSender;

import java.io.Serializable;
import java.util.List;
import java.util.ListIterator;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

/**
 * Executes a list of methods. A method is executed every second to avoid the send limit of telegram.
 * @param <T> The return type of the Bot Method. For example Message for SendMessage
 */
public class ListMethodExecutor<T extends Serializable> extends PeriodicalMethodExecutor<T> {
    private List<BotApiMethod<T>> methods;
    private ListIterator<BotApiMethod<T>> iterator;
    private ScheduledFuture future;

    public ListMethodExecutor() {
        super(null, null, null, null, TimeUnit.SECONDS, 0, 1);
    }

    public ListMethodExecutor(Consumer<T> callback, Consumer<Exception> errorCallback, AbsSender bot, TimeUnit timeUnit, long initialDelay, long period, List<BotApiMethod<T>> methods) {
        super(null, callback, errorCallback, bot, timeUnit, initialDelay, period);
        this.methods = methods;
    }

    public ListMethodExecutor(Consumer<T> callback, Consumer<Exception> errorCallback, AbsSender bot, List<BotApiMethod<T>> methods) {
        super(null, callback, errorCallback, bot, TimeUnit.SECONDS, 0, 1);
        this.methods = methods;
    }

    public List<BotApiMethod<T>> getMethods() {
        return methods;
    }

    public ListMethodExecutor<T> setMethods(List<BotApiMethod<T>> methods) {
        this.methods = methods;
        return this;
    }

    @Override
    public ScheduledFuture<?> schedule(ScheduledExecutorService service) {
        iterator = methods.listIterator();
        future = super.schedule(service);
        return future;
    }

    @Override
    public void run() {
        if (iterator.hasNext()) {
            method = iterator.next();
            super.run();
        } else {
            future.cancel(false);
        }
    }
}
