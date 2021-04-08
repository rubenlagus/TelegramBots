package org.telegram.telegrambots.starter;

import org.junit.jupiter.api.Test;
import org.springframework.boot.autoconfigure.AutoConfigurations;
import org.springframework.boot.test.context.runner.ApplicationContextRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.telegram.telegrambots.extensions.bots.commandbot.TelegramLongPollingCommandBot;
import org.telegram.telegrambots.extensions.bots.commandbot.commands.BotCommand;
import org.telegram.telegrambots.extensions.bots.commandbot.commands.CommandRegistry;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.updates.SetWebhook;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.bots.AbsSender;
import org.telegram.telegrambots.meta.generics.LongPollingBot;
import org.telegram.telegrambots.starter.Annotation.TelegramCommand;

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
    void createTelegramLongPollingCommandBotAndBotCommand(){
        this.contextRunner.withUserConfiguration(TelegramCommandLongPollingBot.class, TelegramCommandConfig.class, CommandRegistryMock.class)
                .run(context -> {
                    assertThat(context).hasSingleBean(TelegramLongPollingCommandBot.class);
                    assertThat(context).hasSingleBean(BotCommand.class);

                    TelegramLongPollingCommandBot telegramLongPollingCommandBot = context.getBean(TelegramLongPollingCommandBot.class);

                    verify(telegramLongPollingCommandBot, times(1)).register(context.getBean(BotCommand.class));
                    verifyNoMoreInteractions(telegramLongPollingCommandBot);
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
    static class TelegramCommandLongPollingBot {
        @Bean
        public MyBot telegramLongPollingCommandBot(){
            return mock(MyBot.class);
        }
    }

    @Configuration
    static class TelegramCommandConfig {
        @Bean
        public MyBotCommand botCommand(){
            return new MyBotCommand("foo", "bar");
        }
    }

    @TelegramCommand(commandBot = MyBot.class)
    static class MyBotCommand extends BotCommand{
        public MyBotCommand(String commandIdentifier, String description) {
            super(commandIdentifier, description);
        }

        @Override
        public void execute(AbsSender absSender, User user, Chat chat, String[] arguments) { }
    }

    @TelegramCommand(commandBot = TelegramLongPollingCommandBot.class)
    static class OtherBotCommand extends BotCommand{
        public OtherBotCommand(String commandIdentifier, String description) {
            super(commandIdentifier, description);
        }

        @Override
        public void execute(AbsSender absSender, User user, Chat chat, String[] arguments) { }
    }

    @Configuration
    static class CommandRegistryMock{
        @Bean
        public CommandRegistry commandRegistry(){
            return mock(CommandRegistry.class);
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
