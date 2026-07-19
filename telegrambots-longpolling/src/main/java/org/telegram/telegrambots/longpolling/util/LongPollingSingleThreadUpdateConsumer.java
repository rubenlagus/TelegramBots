package org.telegram.telegrambots.longpolling.util;

import org.telegram.telegrambots.longpolling.interfaces.LongPollingUpdateConsumer;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @deprecated Use {@link DefaultLongPollingUpdateConsumer} instead.
 *             This interface uses a shared static executor across all bot instances,
 *             which can cause issues when multiple bots are registered in the same application.
 */
@Deprecated
public interface LongPollingSingleThreadUpdateConsumer extends LongPollingUpdateConsumer {
    Executor updatesProcessorExecutor = new Executor() {
        private final ExecutorService delegate = Executors.newSingleThreadExecutor(r -> {
            Thread t = Executors.defaultThreadFactory().newThread(r);
            t.setDaemon(true);
            return t;
        });

        @Override
        public void execute(Runnable command) {
            delegate.execute(command);
        }
    };

    @Override
    default void consume(List<Update> updates) {
        updates.forEach(update -> updatesProcessorExecutor.execute(() -> consume(update)));
    }

    void consume(Update update);
}
