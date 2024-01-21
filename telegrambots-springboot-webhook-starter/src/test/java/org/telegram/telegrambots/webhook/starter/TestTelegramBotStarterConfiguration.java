package org.telegram.telegrambots.webhook.starter;

import org.junit.jupiter.api.Test;
import org.springframework.boot.autoconfigure.AutoConfigurations;
import org.springframework.boot.test.context.runner.ApplicationContextRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.telegram.telegrambots.meta.api.methods.botapimethods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.function.Function;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

class TestTelegramBotStarterConfiguration {
    private final ApplicationContextRunner contextRunner = new ApplicationContextRunner()
            .withAllowBeanDefinitionOverriding(true)
            .withConfiguration(AutoConfigurations.of(MockTelegramApplication.class,
                                                     TelegramBotStarterConfiguration.class));

    @Test
    void createMockTelegramApplicationWithDefaultSettings() {
        this.contextRunner.run((context) -> {
            assertThat(context).hasSingleBean(TelegramBotsSpringWebhookApplication.class);
            assertThat(context).hasSingleBean(TelegramBotInitializer.class);
            assertThat(context).doesNotHaveBean(SpringTelegramWebhookBot.class);
            verifyNoMoreInteractions(context.getBean(TelegramBotsSpringWebhookApplication.class));
        });
    }

    @Test
    void createOnlyLongPollingBot() {
        this.contextRunner.withUserConfiguration(LongPollingBotConfig.class)
                .run((context) -> {
                    assertThat(context).hasSingleBean(SpringTelegramWebhookBot.class);

                    TelegramBotsSpringWebhookApplication telegramApplication = context.getBean(TelegramBotsSpringWebhookApplication.class);

                    verify(telegramApplication, times(1)).registerBot(any(SpringTelegramWebhookBot.class));
                    verifyNoMoreInteractions(telegramApplication);
                });
    }

    @Configuration
    static class MockTelegramApplication {
        @Bean
        public TelegramBotsSpringWebhookApplication telegramBotsApplication() {
            return mock(TelegramBotsSpringWebhookApplication.class);
        }
    }

    @Configuration
    static class LongPollingBotConfig {
        @Bean
        public SpringTelegramWebhookBot webhookBot() {
            SpringTelegramWebhookBot springLongPollingBotMock = mock(SpringTelegramWebhookBot.class);
            doReturn("").when(springLongPollingBotMock).getBotPath();
            doReturn((Function<Update, BotApiMethod<?>>) update -> null).when(springLongPollingBotMock).getUpdateHandler();
            return springLongPollingBotMock;
        }
    }
}
