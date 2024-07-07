package org.telegram.telegrambots.longpolling.starter;

import org.telegram.telegrambots.longpolling.interfaces.LongPollingUpdateConsumer;

public interface SpringLongPollingBot {
    String getBotToken();
    LongPollingUpdateConsumer getUpdatesConsumer();
}
