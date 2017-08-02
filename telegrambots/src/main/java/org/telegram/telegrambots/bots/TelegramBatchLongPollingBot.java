package org.telegram.telegrambots.bots;

import org.telegram.telegrambots.api.objects.Update;

import java.util.Collections;
import java.util.List;

public abstract class TelegramBatchLongPollingBot extends TelegramLongPollingBot {

    @Override
    public final void onUpdateReceived(Update update) {
        onUpdatesReceived(Collections.singletonList(update));
    }

    public abstract void onUpdatesReceived(List<Update> updates);
}
