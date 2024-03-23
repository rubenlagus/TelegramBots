package org.telegram.telegrambots.longpolling.interfaces;

import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.List;

public interface LongPollingUpdateConsumer {
    void consume(List<Update> updates);
}
