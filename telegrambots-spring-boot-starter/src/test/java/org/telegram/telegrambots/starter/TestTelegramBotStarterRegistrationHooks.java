package org.telegram.telegrambots.starter;

import org.junit.jupiter.api.Test;
import org.springframework.boot.autoconfigure.AutoConfigurations;
import org.springframework.boot.test.context.runner.ApplicationContextRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.generics.BotSession;
import org.telegram.telegrambots.meta.generics.LongPollingBot;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class TestTelegramBotStarterRegistrationHooks {

    private static final DefaultBotSession someBotSession = new DefaultBotSession();
    private static final TelegramBotsApi mockTelegramBotsApi = mock(TelegramBotsApi.class);
    // Terrible workaround for mockito loosing annotations on methods
    private static boolean hookCalled = false;
    private static boolean hookCalledWithSession = false;
    private final ApplicationContextRunner contextRunner = new ApplicationContextRunner()
			.withAllowBeanDefinitionOverriding(true)
            .withConfiguration(AutoConfigurations.of(MockTelegramBotsApi.class,
                                                     TelegramBotStarterConfiguration.class));

    @Test
    void longPollingBotWithAnnotatedMethodshouldBeCalled() throws TelegramApiException {

        when(mockTelegramBotsApi.registerBot(any(LongPollingBot.class))).thenReturn(someBotSession);

        this.contextRunner.withUserConfiguration(LongPollingBotConfig.class)
                .run((context) -> {
                    assertThat(context).hasSingleBean(AnnotatedLongPollingBot.class);

                    final LongPollingBot bot = context.getBean(LongPollingBot.class);
                    final TelegramBotsApi telegramBotsApi = context.getBean(TelegramBotsApi.class);

                    assertThat(hookCalled).isTrue();
                    assertThat(hookCalledWithSession).isTrue();
                    verify(telegramBotsApi,
                           times(1)).registerBot(bot);
                    verifyNoMoreInteractions(telegramBotsApi);
                });
    }


    @Configuration
    static class MockTelegramBotsApi {

        @Bean
        public TelegramBotsApi telegramBotsApi() {
            return mockTelegramBotsApi;
        }
    }

    @Configuration
    static class LongPollingBotConfig {
        @Bean
        public LongPollingBot longPollingBot() {
            return new AnnotatedLongPollingBot();
        }
    }

    static class AnnotatedLongPollingBot extends TelegramLongPollingBot {
        public AnnotatedLongPollingBot() {
            super((String) null);
        }

        @Override
        public void onUpdateReceived(final Update update) {
        }

        @Override
        public String getBotUsername() {
            return null;
        }

        @AfterBotRegistration
        public void afterBotHook() {
            hookCalled = true;
        }

        @AfterBotRegistration
        public void afterBotHookWithSession(BotSession session) {
            hookCalledWithSession = session.equals(someBotSession);
        }
    }
}
