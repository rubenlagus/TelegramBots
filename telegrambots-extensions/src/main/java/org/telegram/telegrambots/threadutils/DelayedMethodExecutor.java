package org.telegram.telegrambots.threadutils;

import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.bots.AbsSender;

import java.io.Serializable;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

/**
 * Executes a Method after a set delay
 * @param <T> The return type of the Bot Method. For example Message for SendMessage
 */
public class DelayedMethodExecutor<T extends Serializable> extends TimedMethodExecutor<T> {
    private TimeUnit timeUnit;
    private long delay;


    public DelayedMethodExecutor() {
        super();
    }

    public DelayedMethodExecutor(BotApiMethod<T> method, Consumer<T> callback, Consumer<Exception> errorCallback, AbsSender bot, TimeUnit timeUnit, long delay) {
        super(method, callback, errorCallback, bot);
        this.timeUnit = timeUnit;
        this.delay = delay;
    }

    public TimeUnit getTimeUnit() {
        return timeUnit;
    }

    public DelayedMethodExecutor<T> setTimeUnit(TimeUnit timeUnit) {
        this.timeUnit = timeUnit;
        return this;
    }

    public long getDelay() {
        return delay;
    }

    public DelayedMethodExecutor<T> setDelay(long delay) {
        this.delay = delay;
        return this;
    }

    @Override
    public ScheduledFuture<?> schedule(final ScheduledExecutorService service) {
        return service.schedule(this, delay, timeUnit);
    }

}
