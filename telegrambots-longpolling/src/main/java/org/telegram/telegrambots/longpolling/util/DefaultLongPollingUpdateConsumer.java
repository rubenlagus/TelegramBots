package org.telegram.telegrambots.longpolling.util;

import org.telegram.telegrambots.longpolling.interfaces.LongPollingUpdateConsumer;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.RejectedExecutionException;

/**
 * A single-threaded {@link LongPollingUpdateConsumer} that processes updates one at a time
 * on a dedicated per-instance background thread.
 *
 * <p>Extend this class and implement {@link #consume(Update)} to handle individual updates.
 * The executor is a daemon thread, so it will not prevent JVM shutdown.
 * Call {@link #close()} to shut it down explicitly when the bot is deregistered.</p>
 */
public abstract class DefaultLongPollingUpdateConsumer implements LongPollingUpdateConsumer {
    private final ExecutorService updatesProcessorExecutor = Executors.newSingleThreadExecutor();

    @Override
    public void consume(List<Update> updates) {
        updates.forEach(update -> {
            try {
                updatesProcessorExecutor.execute(() -> consume(update));
            } catch (RejectedExecutionException ignored) {
                // Executor has been shut down via close(); incoming updates are discarded
            }
        });
    }

    public abstract void consume(Update update);

    @Override
    public void close() {
        updatesProcessorExecutor.shutdown();
    }
}
