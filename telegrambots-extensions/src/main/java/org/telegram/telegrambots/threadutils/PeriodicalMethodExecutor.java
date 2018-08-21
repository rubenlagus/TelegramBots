package org.telegram.telegrambots.threadutils;

import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.bots.AbsSender;

import java.io.Serializable;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

/**
 * Executes a set Method after an initial delay, with a set period.
 * @param <T>
 */
public class PeriodicalMethodExecutor<T extends Serializable> extends TimedMethodExecutor<T> {
    private TimeUnit timeUnit;
    private long initialDelay;
    private long period;

    public PeriodicalMethodExecutor() {
        super();
    }

    public PeriodicalMethodExecutor(BotApiMethod<T> method, Consumer<T> callback, Consumer<Exception> errorCallback, AbsSender bot, TimeUnit timeUnit, long initialDelay, long period) {
        super(method, callback, errorCallback, bot);
        this.timeUnit = timeUnit;
        this.initialDelay = initialDelay;
        this.period = period;
    }

    public TimeUnit getTimeUnit() {
        return timeUnit;
    }

    public PeriodicalMethodExecutor<T> setTimeUnit(TimeUnit timeUnit) {
        this.timeUnit = timeUnit;
        return this;
    }

    public long getInitialDelay() {
        return initialDelay;
    }

    public PeriodicalMethodExecutor<T> setInitialDelay(long initialDelay) {
        this.initialDelay = initialDelay;
        return this;
    }

    public long getPeriod() {
        return period;
    }

    public PeriodicalMethodExecutor<T> setPeriod(long period) {
        this.period = period;
        return this;
    }

    @Override
    public ScheduledFuture<?> schedule(ScheduledExecutorService service) {
        return service.scheduleAtFixedRate(this, initialDelay, period, timeUnit);
    }
}
