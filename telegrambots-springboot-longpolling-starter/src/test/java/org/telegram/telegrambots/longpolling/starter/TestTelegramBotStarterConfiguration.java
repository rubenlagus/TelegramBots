package org.telegram.telegrambots.longpolling.starter;

import org.junit.jupiter.api.Test;
import org.springframework.boot.autoconfigure.AutoConfigurations;
import org.springframework.boot.test.context.runner.ApplicationContextRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.telegram.telegrambots.longpolling.TelegramBotsLongPollingApplication;
import org.telegram.telegrambots.longpolling.interfaces.LongPollingUpdateConsumer;
import org.telegram.telegrambots.longpolling.util.LongPollingSingleThreadUpdateConsumer;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

class TestTelegramBotStarterConfiguration {

    private final ApplicationContextRunner contextRunner = new ApplicationContextRunner()
            .withAllowBeanDefinitionOverriding(true)
            .withConfiguration(AutoConfigurations.of(MockTelegramBotsLongPollingApplication.class,
                                                     TelegramBotStarterConfiguration.class));

    @Test
    void createMockTelegramApplicationWithDefaultSettings() {
        this.contextRunner.run((context) -> {
            assertThat(context).hasSingleBean(TelegramBotsLongPollingApplication.class);
            assertThat(context).hasSingleBean(TelegramBotInitializer.class);
            assertThat(context).doesNotHaveBean(SpringLongPollingBot.class);
            verifyNoMoreInteractions(context.getBean(TelegramBotsLongPollingApplication.class));
        });
    }

    @Test
    void createOnlyLongPollingBot() {
        this.contextRunner.withUserConfiguration(LongPollingBotConfig.class)
                .run((context) -> {
                    assertThat(context).hasSingleBean(SpringLongPollingBot.class);

                    TelegramBotsLongPollingApplication telegramApplication = context.getBean(TelegramBotsLongPollingApplication.class);

                    verify(telegramApplication, times(1)).registerBot(anyString(), any(LongPollingUpdateConsumer.class));
                    verifyNoMoreInteractions(telegramApplication);
                });
    }

    @Configuration
    static class MockTelegramBotsLongPollingApplication {

        @Bean
        public TelegramBotsLongPollingApplication telegramBotsApplication() {
            return mock(TelegramBotsLongPollingApplication.class);
        }
    }

    @Configuration
    static class LongPollingBotConfig {
        @Bean
        public SpringLongPollingBot longPollingBot() {
            SpringLongPollingBot springLongPollingBotMock = mock(SpringLongPollingBot.class);
            doReturn("").when(springLongPollingBotMock).getBotToken();
            doReturn((LongPollingSingleThreadUpdateConsumer) update -> {}).when(springLongPollingBotMock).getUpdatesConsumer();
            return springLongPollingBotMock;
        }
    }
}
