package org.telegram.telegrambots.longpolling.starter;

import org.telegram.telegrambots.longpolling.interfaces.LongPollingUpdateConsumer;
import org.telegram.telegrambots.longpolling.util.DefaultGetUpdatesGenerator;
import org.telegram.telegrambots.meta.api.methods.updates.GetUpdates;

import java.util.function.Function;

public interface SpringLongPollingBot {
    String getBotToken();

    LongPollingUpdateConsumer getUpdatesConsumer();

    default Function<Integer, GetUpdates> getGetUpdatesGenerator() {
        return new DefaultGetUpdatesGenerator();
    }
}
