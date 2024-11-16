package org.telegram.telegrambots.longpolling.starter;

import org.junit.jupiter.api.Test;
import org.springframework.boot.autoconfigure.AutoConfigurations;
import org.springframework.boot.test.context.runner.ApplicationContextRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.telegram.telegrambots.longpolling.TelegramBotsLongPollingApplication;
import org.telegram.telegrambots.longpolling.interfaces.LongPollingUpdateConsumer;
import org.telegram.telegrambots.longpolling.util.LongPollingSingleThreadUpdateConsumer;
import org.telegram.telegrambots.meta.generics.TelegramClient;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

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

                    verify(telegramApplication, times(1)).registerBot(any(TelegramClient.class), any(LongPollingUpdateConsumer.class));
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
            doReturn(mock(TelegramClient.class)).when(springLongPollingBotMock).getTelegramClient();
            doReturn((LongPollingSingleThreadUpdateConsumer) update -> {
            }).when(springLongPollingBotMock).getUpdatesConsumer();
            return springLongPollingBotMock;
        }
    }
}
