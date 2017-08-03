package org.telegram.telegrambots.test;

import org.junit.Test;
import org.mockito.Mockito;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.bots.TelegramBatchLongPollingBot;

import java.util.Collections;

public class TelegramBatchLongPollingBotTest {

    @Test
    public void testOnUpdateReceived() throws Exception {
        TelegramBatchLongPollingBot bot = Mockito.mock(TelegramBatchLongPollingBot.class);
        Update update = new Update();
        bot.onUpdateReceived(update);
        Mockito.verify(bot).onUpdatesReceived(Collections.singletonList(update));
    }
}