package org.telegram.telegrambots.longpolling.starter;

import org.telegram.telegrambots.longpolling.interfaces.LongPollingUpdateConsumer;
import org.telegram.telegrambots.longpolling.util.DefaultGetUpdatesGenerator;
import org.telegram.telegrambots.meta.api.methods.updates.GetUpdates;

public interface SpringLongPollingBot {
    String getBotToken();
    LongPollingUpdateConsumer getUpdatesConsumer();
    default GetUpdates getUpdates(final int lastReceivedUpdate) {
        return DefaultGetUpdatesGenerator.INSTANCE.apply(lastReceivedUpdate);
    }
}
