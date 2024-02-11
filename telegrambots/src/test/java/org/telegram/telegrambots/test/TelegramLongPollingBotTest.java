package org.telegram.telegrambots.test;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.telegram.telegrambots.bots.DefaultBotOptions;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;

import static java.util.Arrays.asList;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.ArgumentMatchers.any;

public class TelegramLongPollingBotTest {

    @Test
    public void testOnUpdateReceived() {
        TelegramLongPollingBot bot = Mockito.mock(TelegramLongPollingBot.class);
        Mockito.doCallRealMethod().when(bot).onUpdatesReceived(any());
        Update update1 = new Update();
        update1.setUpdateId(1);
        Update update2 = new Update();
        update2.setUpdateId(2);
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

        public TestBot() {
            super("");
        }

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

    }

}
