package org.telegram.telegrambots.threadutils.test;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.TelegramBotsApi;
import org.telegram.telegrambots.api.methods.BotApiMethod;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.exceptions.TelegramApiException;
import org.telegram.telegrambots.exceptions.TelegramApiRequestException;
import org.telegram.telegrambots.threadutils.DelayedMethodExecutor;
import org.telegram.telegrambots.threadutils.ListMethodExecutor;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class TimedMethodExecutorTest {
    private static final String BOT_TOKEN = "TOKEN";
    private static final String CHAT_ID = "CHAT_ID";

    private static class MockBot extends TelegramLongPollingBot {

        @Override
        public void onUpdateReceived(Update update) {

        }

        @Override
        public String getBotUsername() {
            return "mockBot";
        }

        @Override
        public String getBotToken() {
            return BOT_TOKEN;
        }
    }

    public static MockBot bot;
    public static ScheduledExecutorService service;

    @BeforeAll
    public static void setUpBot() throws TelegramApiRequestException {
        ApiContextInitializer.init();

        TelegramBotsApi api = new TelegramBotsApi();

        bot = new MockBot();

        api.registerBot(bot);

        service = new ScheduledThreadPoolExecutor(1);
    }

    @Test
    public void TestScheduledSendMessage() throws TelegramApiException {
        DelayedMethodExecutor<Message> executor = new DelayedMethodExecutor<>();

        bot.execute(new SendMessage(CHAT_ID, "Test Start"));

        SendMessage botMethod = new SendMessage();
        botMethod.setChatId(CHAT_ID)
                .setText("This message is send 10 seconds after the test starts");

        executor.setDelay(10)
                .setTimeUnit(TimeUnit.SECONDS)
                .setBot(bot)
                .setMethod(botMethod)
                .setCallback(message -> System.out.println(message.toString()))
                .setErrorCallback(Throwable::printStackTrace);

        ScheduledFuture<?> future = executor.schedule(service);

        while (!future.isDone()) {}

    }

    @Test
    public void TestScheduledSendMessageException() throws TelegramApiException {
        DelayedMethodExecutor<Message> executor = new DelayedMethodExecutor<>();

        bot.execute(new SendMessage(CHAT_ID, "Test Start"));

        SendMessage botMethod = new SendMessage();
        botMethod.setChatId(CHAT_ID);

        executor.setDelay(10)
                .setTimeUnit(TimeUnit.SECONDS)
                .setBot(bot)
                .setMethod(botMethod)
                .setCallback(message -> System.out.println(message.toString()))
                .setErrorCallback(Throwable::printStackTrace);

        ScheduledFuture<?> future = executor.schedule(service);

        while (!future.isDone()) {}

    }

    @Test
    public void TestListMethodExecutor() throws TelegramApiException {
        ListMethodExecutor<Message> executor = new ListMethodExecutor<>();

        bot.execute(new SendMessage(CHAT_ID, "Test Start"));

        List<BotApiMethod<Message>> methods = Arrays.asList(
                new SendMessage().setChatId(CHAT_ID).setText("Message 1"),
                new SendMessage().setChatId(CHAT_ID).setText("Message 2"),
                new SendMessage().setChatId(CHAT_ID).setText("Message 3"),
                new SendMessage().setChatId(CHAT_ID).setText("Message 4"),
                new SendMessage().setChatId(CHAT_ID).setText("Message 5"),
                new SendMessage().setChatId(CHAT_ID).setText("Message 6"),
                new SendMessage().setChatId(CHAT_ID).setText("Message 7"),
                new SendMessage().setChatId(CHAT_ID).setText("Message 8")
        );

        executor.setMethods(methods);
        executor.setBot(bot)
                .setCallback(message -> System.out.println(message.toString()))
                .setErrorCallback(Throwable::printStackTrace);

        ScheduledFuture<?> future = executor.schedule(service);

        while (!future.isDone()) {}

    }


}
