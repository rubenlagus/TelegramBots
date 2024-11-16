package org.telegram.telegrambots.longpolling.starter;

import org.telegram.telegrambots.longpolling.interfaces.LongPollingUpdateConsumer;
import org.telegram.telegrambots.meta.generics.TelegramClient;

public interface SpringLongPollingBot {
    TelegramClient getTelegramClient();
    LongPollingUpdateConsumer getUpdatesConsumer();
}
