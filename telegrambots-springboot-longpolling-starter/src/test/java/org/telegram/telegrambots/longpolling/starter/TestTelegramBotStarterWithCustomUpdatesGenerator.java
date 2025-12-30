package org.telegram.telegrambots.longpolling.starter;

import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.boot.autoconfigure.AutoConfigurations;
import org.springframework.boot.test.context.runner.ApplicationContextRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.telegram.telegrambots.longpolling.BotSession;
import org.telegram.telegrambots.longpolling.TelegramBotsLongPollingApplication;
import org.telegram.telegrambots.longpolling.interfaces.LongPollingUpdateConsumer;
import org.telegram.telegrambots.meta.api.methods.updates.GetUpdates;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.List;
import java.util.function.Function;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class TestTelegramBotStarterWithCustomUpdatesGenerator {

    private static final BotSession someBotSession = mock(BotSession.class);
    private static final TelegramBotsLongPollingApplication mockApplication = mock(TelegramBotsLongPollingApplication.class);

    private final ApplicationContextRunner contextRunner = new ApplicationContextRunner()
            .withAllowBeanDefinitionOverriding(true)
            .withConfiguration(AutoConfigurations.of(MockTelegramBotsApi.class,
                    TelegramBotStarterConfiguration.class));

    @Test
    void longPollingBotWithCustomUpdates() throws TelegramApiException {

        when(mockApplication.registerBot(anyString(), any(), any(), any(LongPollingUpdateConsumer.class))).thenReturn(someBotSession);

        this.contextRunner.withUserConfiguration(CustomUpdatesLongPollingBotConfig.class)
                .run((context) -> {
                    assertThat(context).hasSingleBean(CustomUpdatesLongPollingBotConfig.Bot.class);

                    final SpringLongPollingBot bot = context.getBean(SpringLongPollingBot.class);
                    final TelegramBotsLongPollingApplication telegramBotsApi = context.getBean(TelegramBotsLongPollingApplication.class);

                    assertInstanceOf(CustomUpdatesLongPollingBotConfig.Bot.class, bot);

                    final ArgumentCaptor<Function<Integer, GetUpdates>> getUpdatesGeneratorCaptor = ArgumentCaptor.captor();
                    verify(telegramBotsApi, times(1)).registerBot(eq(bot.getBotToken()), any(), getUpdatesGeneratorCaptor.capture(), any(LongPollingUpdateConsumer.class));

                    final Function<Integer, GetUpdates> getUpdatesGenerator = getUpdatesGeneratorCaptor.getValue();
                    final GetUpdates getUpdates = getUpdatesGenerator.apply(123);
                    assertNotNull(getUpdates);
                    assertEquals(42, getUpdates.getLimit());
                    assertEquals(124, getUpdates.getOffset());
                    assertEquals(555, getUpdates.getTimeout());
                    assertEquals(List.of("message", "callback_query", "message_reaction"), getUpdates.getAllowedUpdates());
                });
    }

    @Test
    void longPollingBotWithDefaultUpdates() throws TelegramApiException {

        when(mockApplication.registerBot(anyString(), any(), any(), any(LongPollingUpdateConsumer.class))).thenReturn(someBotSession);

        this.contextRunner.withUserConfiguration(DefaultUpdatesLongPollingBotConfig.class)
                .run((context) -> {
                    assertThat(context).hasSingleBean(DefaultUpdatesLongPollingBotConfig.Bot.class);

                    final SpringLongPollingBot bot = context.getBean(SpringLongPollingBot.class);
                    final TelegramBotsLongPollingApplication telegramBotsApi = context.getBean(TelegramBotsLongPollingApplication.class);

                    assertInstanceOf(DefaultUpdatesLongPollingBotConfig.Bot.class, bot);

                    final ArgumentCaptor<Function<Integer, GetUpdates>> getUpdatesGeneratorCaptor = ArgumentCaptor.captor();
                    verify(telegramBotsApi, times(1)).registerBot(eq(bot.getBotToken()), any(), getUpdatesGeneratorCaptor.capture(), any(LongPollingUpdateConsumer.class));

                    final Function<Integer, GetUpdates> getUpdatesGenerator = getUpdatesGeneratorCaptor.getValue();
                    final GetUpdates getUpdates = getUpdatesGenerator.apply(123);
                    assertNotNull(getUpdates);
                    assertEquals(100, getUpdates.getLimit());
                    assertEquals(124, getUpdates.getOffset());
                    assertEquals(50, getUpdates.getTimeout());
                    assertEquals(List.of(), getUpdates.getAllowedUpdates());
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
    static class CustomUpdatesLongPollingBotConfig {
        @Bean
        public SpringLongPollingBot longPollingBot() {
            return new Bot();
        }

        static class Bot implements SpringLongPollingBot {

            @Override
            public String getBotToken() {
                return "custom";
            }

            @Override
            public LongPollingUpdateConsumer getUpdatesConsumer() {
                return update -> {
                };
            }

            @Override
            public GetUpdates getUpdates(final int offset) {
                return GetUpdates.builder()
                        .limit(42)
                        .timeout(555)
                        .offset(offset + 1)
                        .allowedUpdates(List.of("message", "callback_query", "message_reaction"))
                        .build();
            }
        }
    }

    @Configuration
    static class DefaultUpdatesLongPollingBotConfig {
        @Bean
        public SpringLongPollingBot longPollingBot() {
            return new Bot();
        }

        static class Bot implements SpringLongPollingBot {

            @Override
            public String getBotToken() {
                return "default";
            }

            @Override
            public LongPollingUpdateConsumer getUpdatesConsumer() {
                return update -> {
                };
            }
        }
    }
}
