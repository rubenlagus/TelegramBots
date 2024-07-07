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

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

class TestTelegramBotStarterRegistrationHooks {

    private static final BotSession someBotSession = mock(BotSession.class);
    private static final TelegramBotsLongPollingApplication mockApplication = mock(TelegramBotsLongPollingApplication.class);

    private final ApplicationContextRunner contextRunner = new ApplicationContextRunner()
			.withAllowBeanDefinitionOverriding(true)
            .withConfiguration(AutoConfigurations.of(MockTelegramBotsApi.class,
                                                     TelegramBotStarterConfiguration.class));

    @Test
    void longPollingBotWithAnnotatedMethodshouldBeCalled() throws TelegramApiException {

        when(mockApplication.registerBot(anyString(), any(LongPollingUpdateConsumer.class))).thenReturn(someBotSession);

        this.contextRunner.withUserConfiguration(LongPollingBotConfig.class)
                .run((context) -> {
                    assertThat(context).hasSingleBean(AnnotatedLongPollingBot.class);

                    final SpringLongPollingBot bot = context.getBean(SpringLongPollingBot.class);
                    final TelegramBotsLongPollingApplication telegramBotsApi = context.getBean(TelegramBotsLongPollingApplication.class);

                    assertInstanceOf(AnnotatedLongPollingBot.class, bot);
                    assertTrue(((AnnotatedLongPollingBot) bot).isHookCalled());
                    assertEquals(someBotSession, ((AnnotatedLongPollingBot) bot).getHookCalledWithSession());
                    verify(telegramBotsApi, times(1)).registerBot(eq(bot.getBotToken()), any(LongPollingUpdateConsumer.class));
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

        public AnnotatedLongPollingBot() {
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
        public String getBotToken() {
            return "";
        }

        @Override
        public LongPollingUpdateConsumer getUpdatesConsumer() {
            return update -> { };
        }
    }
}
