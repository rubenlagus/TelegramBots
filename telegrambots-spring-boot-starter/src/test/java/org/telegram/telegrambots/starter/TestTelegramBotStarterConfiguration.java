package org.telegram.telegrambots.starter;

import org.junit.jupiter.api.Test;
import org.springframework.boot.autoconfigure.AutoConfigurations;
import org.springframework.boot.test.context.runner.ApplicationContextRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.updates.SetWebhook;
import org.telegram.telegrambots.meta.generics.LongPollingBot;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

class TestTelegramBotStarterConfiguration {

    private final ApplicationContextRunner contextRunner = new ApplicationContextRunner()
            .withAllowBeanDefinitionOverriding(true)
            .withConfiguration(AutoConfigurations.of(MockTelegramBotsApi.class,
                                                     TelegramBotStarterConfiguration.class));

    @Test
    void createMockTelegramBotsApiWithDefaultSettings() {
        this.contextRunner.run((context) -> {
            assertThat(context).hasSingleBean(TelegramBotsApi.class);
            assertThat(context).hasSingleBean(TelegramBotInitializer.class);
            assertThat(context).doesNotHaveBean(LongPollingBot.class);
            assertThat(context).doesNotHaveBean(SpringWebhookBot.class);
            verifyNoMoreInteractions(context.getBean(TelegramBotsApi.class));
        });
    }

    @Test
    void createOnlyLongPollingBot() {
        this.contextRunner.withUserConfiguration(LongPollingBotConfig.class)
                .run((context) -> {
                    assertThat(context).hasSingleBean(LongPollingBot.class);
                    assertThat(context).doesNotHaveBean(SpringWebhookBot.class);

                    TelegramBotsApi telegramBotsApi = context.getBean(TelegramBotsApi.class);

                    verify(telegramBotsApi,
                           times(1)).registerBot(context.getBean(LongPollingBot.class));
                    verifyNoMoreInteractions(telegramBotsApi);
                });
    }

    @Test
    void createOnlyWebhookBot() {
        this.contextRunner.withUserConfiguration(WebhookBotConfig.class)
                .run((context) -> {
                    assertThat(context).hasSingleBean(SpringWebhookBot.class);
                    assertThat(context).doesNotHaveBean(LongPollingBot.class);

                    TelegramBotsApi telegramBotsApi = context.getBean(TelegramBotsApi.class);

                    verify(telegramBotsApi,
                           times(1)).registerBot(context.getBean(SpringWebhookBot.class), context.getBean(SpringWebhookBot.class).getSetWebhook());
                    verifyNoMoreInteractions(telegramBotsApi);
                });
    }

    @Test
    void createLongPoolingBotAndWebhookBot() {
        this.contextRunner.withUserConfiguration(LongPollingBotConfig.class,
                                                 WebhookBotConfig.class)
                .run((context) -> {
                    assertThat(context).hasSingleBean(LongPollingBot.class);
                    assertThat(context).hasSingleBean(SpringWebhookBot.class);

                    TelegramBotsApi telegramBotsApi = context.getBean(TelegramBotsApi.class);

                    verify(telegramBotsApi,
                           times(1)).registerBot(context.getBean(LongPollingBot.class));
                    verify(telegramBotsApi,
                           times(1)).registerBot(context.getBean(SpringWebhookBot.class), context.getBean(SpringWebhookBot.class).getSetWebhook());
                    //verifyNoMoreInteractions(telegramBotsApi);
                });
    }

    @Configuration
    static class MockTelegramBotsApi {

        @Bean
        public TelegramBotsApi telegramBotsApi() {
            return mock(TelegramBotsApi.class);
        }
    }

    @Configuration
    static class LongPollingBotConfig {
        @Bean
        public LongPollingBot longPollingBot() {
            return mock(LongPollingBot.class);
        }
    }

    @Configuration
    static class WebhookBotConfig {
        @Bean
        public SpringWebhookBot webhookBot(SetWebhook setWebhook) {
            SpringWebhookBot bot = mock(SpringWebhookBot.class);
            doReturn(setWebhook).when(bot).getSetWebhook();
            return bot;
        }

        @Bean
        public SetWebhook setWebhook() {
            return mock(SetWebhook.class);
        }
    }
}
