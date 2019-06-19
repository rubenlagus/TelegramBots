package org.telegram.telegrambots.test;

import org.junit.Test;
import org.mockito.Mockito;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.bots.DefaultBotOptions;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

import static java.util.Arrays.asList;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;
import static org.mockito.Matchers.any;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;

public class TelegramLongPollingBotTest {

    @Test
    public void testOnUpdateReceived() throws Exception {
        TelegramLongPollingBot bot = Mockito.mock(TelegramLongPollingBot.class);
        Mockito.doCallRealMethod().when(bot).onUpdatesReceived(any());
        Update update1 = new Update();
        Update update2 = new Update();
        bot.onUpdatesReceived(asList(update1, update2));
        Mockito.verify(bot).onUpdateReceived(update1);
        Mockito.verify(bot).onUpdateReceived(update2);
    }

    @Test
    public void testExecutorShutdown() throws Exception {
        TestBot bot = Mockito.spy(new TestBot());
        DefaultBotSession session = new DefaultBotSession();
        session.setCallback(bot);
        session.setOptions(new DefaultBotOptions());
        session.start();
        session.stop();
        Mockito.verify(bot).onClosing();
        ExecutorService executor = bot.getExecutor();
        assertThat("Executor was not shut down", executor.isShutdown(), is(true));
        executor.awaitTermination(1, TimeUnit.SECONDS);
        assertThat("Executor could not terminate", executor.isTerminated(), is(true));
    }

    private static class TestBot extends TelegramLongPollingBot {

        @Override
        public void onUpdateReceived(Update update) {
        }

        ExecutorService getExecutor() {
            return exe;
        }

        @Override
        public String getBotUsername() {
            return "";
        }

        @Override
        public String getBotToken() {
            return "";
        }
    }

}
