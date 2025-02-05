package org.telegram.telegrambots.longpolling.starter;

import org.telegram.telegrambots.longpolling.interfaces.LongPollingUpdateConsumer;

import java.util.ArrayList;
import java.util.List;

public interface SpringLongPollingBot {
    String getBotToken();
    LongPollingUpdateConsumer getUpdatesConsumer();
    default List<String> getAllowedUpdates() {
        return new ArrayList<>();
    }
}
