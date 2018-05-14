package org.telegram.telegrambots.starter;

import com.google.common.collect.Lists;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Answers;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import org.telegram.telegrambots.TelegramBotsApi;
import org.telegram.telegrambots.exceptions.TelegramApiRequestException;
import org.telegram.telegrambots.generics.LongPollingBot;
import org.telegram.telegrambots.generics.WebhookBot;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

public class TestTelegramBotStarterConfiguration {

    @Mock
    private TelegramBotsApi telegramBotsApi;

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Test
    public void TestRegisterBotsWithLongPollingBots() throws TelegramApiRequestException {
        when(telegramBotsApi.registerBot(any(LongPollingBot.class))).then(Answers.RETURNS_MOCKS.get());
        LongPollingBot longPollingBot = mock(LongPollingBot.class);
        TelegramBotStarterConfiguration configuration = new TelegramBotStarterConfiguration(Lists.newArrayList(longPollingBot), null);
        configuration.setTelegramBotsApi(telegramBotsApi);

        configuration.registerBots();

        verify(telegramBotsApi, times(1)).registerBot(longPollingBot);
        verifyNoMoreInteractions(telegramBotsApi);
    }

    @Test
    public void TestRegisterBotsWithWebhookBots() throws TelegramApiRequestException {
        doNothing().when(telegramBotsApi).registerBot(any(WebhookBot.class));
        WebhookBot webhookBot = mock(WebhookBot.class);
        TelegramBotStarterConfiguration configuration = new TelegramBotStarterConfiguration(null, Lists.newArrayList(webhookBot));
        configuration.setTelegramBotsApi(telegramBotsApi);

        configuration.registerBots();

        verify(telegramBotsApi, times(1)).registerBot(webhookBot);
        verifyNoMoreInteractions(telegramBotsApi);
    }

    @Test
    public void TestRegisterBotsWithLongPollingBotsAndWebhookBots() throws TelegramApiRequestException {
        doNothing().when(telegramBotsApi).registerBot(any(WebhookBot.class));
        LongPollingBot longPollingBot = mock(LongPollingBot.class);
        WebhookBot webhookBot = mock(WebhookBot.class);
        TelegramBotStarterConfiguration configuration = new TelegramBotStarterConfiguration(Lists.newArrayList(longPollingBot), Lists.newArrayList(webhookBot));
        configuration.setTelegramBotsApi(telegramBotsApi);

        configuration.registerBots();

        verify(telegramBotsApi, times(1)).registerBot(longPollingBot);
        verify(telegramBotsApi, times(1)).registerBot(webhookBot);
        verifyNoMoreInteractions(telegramBotsApi);
    }
}
