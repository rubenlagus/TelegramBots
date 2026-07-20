package org.telegram.telegrambots.longpolling.interfaces;

import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.List;

public interface LongPollingUpdateConsumer extends AutoCloseable {
    void consume(List<Update> updates);

    @Override
    default void close() throws Exception {
        // no-op by default
    }
}
