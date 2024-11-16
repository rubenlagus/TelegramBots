package org.telegram.telegrambots.longpolling.starter;

import lombok.Getter;
import org.junit.jupiter.api.Test;
import org.springframework.boot.autoconfigure.AutoConfigurations;
import org.springframework.boot.test.context.runner.ApplicationContextRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.telegram.telegrambots.longpolling.BotSession;
import org.telegram.telegrambots.longpolling.TelegramBotsLongPollingApplication;
import org.telegram.telegrambots.longpolling.interfaces.LongPollingUpdateConsumer;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.generics.TelegramClient;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TestTelegramBotStarterRegistrationHooks {

    private static final BotSession someBotSession = mock(BotSession.class);
    private static final TelegramBotsLongPollingApplication mockApplication = mock(TelegramBotsLongPollingApplication.class);

    private final ApplicationContextRunner contextRunner = new ApplicationContextRunner()
            .withAllowBeanDefinitionOverriding(true)
            .withConfiguration(AutoConfigurations.of(MockTelegramBotsApi.class,
                    TelegramBotStarterConfiguration.class));

    @Test
    void longPollingBotWithAnnotatedMethodShouldBeCalled() throws TelegramApiException {

        when(mockApplication.registerBot(any(TelegramClient.class), any(LongPollingUpdateConsumer.class))).thenReturn(someBotSession);

        this.contextRunner.withUserConfiguration(LongPollingBotConfig.class)
                .run((context) -> {
                    assertThat(context).hasSingleBean(AnnotatedLongPollingBot.class);

                    final SpringLongPollingBot bot = context.getBean(SpringLongPollingBot.class);
                    final TelegramBotsLongPollingApplication telegramBotsApi = context.getBean(TelegramBotsLongPollingApplication.class);

                    assertInstanceOf(AnnotatedLongPollingBot.class, bot);
                    assertTrue(((AnnotatedLongPollingBot) bot).isHookCalled());
                    assertEquals(someBotSession, ((AnnotatedLongPollingBot) bot).getHookCalledWithSession());
                    verify(telegramBotsApi, times(1))
                            .registerBot(eq(bot.getTelegramClient()), any(LongPollingUpdateConsumer.class));
                    verifyNoMoreInteractions(telegramBotsApi);
                });
    }


    @Configuration
    public static class MockTelegramBotsApi {
        @Bean
        public TelegramBotsLongPollingApplication telegramBotsApplication() {
            return mockApplication;
        }
    }

    @Configuration
    public static class LongPollingBotConfig {
        @Bean
        public SpringLongPollingBot longPollingBot() {
            return new AnnotatedLongPollingBot();
        }
    }

    @Getter
    public static class AnnotatedLongPollingBot implements SpringLongPollingBot {
        private boolean hookCalled = false;
        private BotSession hookCalledWithSession = null;
        private final TelegramClient telegramClient;

        public AnnotatedLongPollingBot() {
            TelegramClient telegramClient = mock(TelegramClient.class);
            when(telegramClient.getBotToken()).then(invocationOnMock -> "");
            this.telegramClient = telegramClient;
        }

        @AfterBotRegistration
        public void afterBotHook() {
            hookCalled = true;
        }

        @AfterBotRegistration
        public void afterBotHookWithSession(BotSession session) {
            hookCalledWithSession = session;
        }

        @Override
        public TelegramClient getTelegramClient() {
            return telegramClient;
        }

        @Override
        public LongPollingUpdateConsumer getUpdatesConsumer() {
            return update -> {
            };
        }
    }
}
