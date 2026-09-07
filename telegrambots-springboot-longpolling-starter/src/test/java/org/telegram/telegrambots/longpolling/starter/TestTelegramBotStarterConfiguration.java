package org.telegram.telegrambots.longpolling.starter;

import org.junit.jupiter.api.Test;
import org.springframework.boot.autoconfigure.AutoConfigurations;
import org.springframework.boot.test.context.runner.ApplicationContextRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.telegram.telegrambots.extensions.bots.commandbot.CommandLongPollingTelegramBot;
import org.telegram.telegrambots.extensions.bots.commandbot.commands.IBotCommand;
import org.telegram.telegrambots.extensions.bots.commandbot.commands.ICommandRegistry;
import org.telegram.telegrambots.longpolling.TelegramBotsLongPollingApplication;
import org.telegram.telegrambots.longpolling.interfaces.LongPollingUpdateConsumer;
import org.telegram.telegrambots.longpolling.starter.extension.Command;
import org.telegram.telegrambots.longpolling.starter.extension.CommandRegistrar;
import org.telegram.telegrambots.longpolling.starter.extension.ICommand;
import org.telegram.telegrambots.longpolling.util.LongPollingSingleThreadUpdateConsumer;
import org.telegram.telegrambots.meta.TelegramUrl;
import org.telegram.telegrambots.meta.api.objects.message.Message;
import org.telegram.telegrambots.meta.generics.TelegramClient;

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
            assertThat(context).doesNotHaveBean(TelegramUrl.class);
            verifyNoMoreInteractions(context.getBean(TelegramBotsLongPollingApplication.class));
        });
    }

    @Test
    void createOnlyLongPollingBot() {
        this.contextRunner.withUserConfiguration(LongPollingBotConfig.class)
                .run((context) -> {
                    assertThat(context).hasSingleBean(SpringLongPollingBot.class);

                    TelegramBotsLongPollingApplication telegramApplication = context.getBean(TelegramBotsLongPollingApplication.class);

                    verify(telegramApplication, times(1)).registerBot(anyString(), any(), any(), any(LongPollingUpdateConsumer.class));
                    verifyNoMoreInteractions(telegramApplication);
                });
    }

    @Test
    void shouldCreateRegistrarOnlyWhenWebhookCommandBotExists() {
        contextRunner.run(context ->
                assertThat(context).doesNotHaveBean(CommandRegistrar.class));

        contextRunner.withUserConfiguration(ExtensionBotConfig.class).run(context ->
                assertThat(context).hasSingleBean(CommandRegistrar.class));
    }

    @Test
    void shouldRegisterAnnotatedCommandsOnStartup() {
        contextRunner.withUserConfiguration(ExtensionBotConfig.class).run(context -> {
            ICommandRegistry registry = context.getBean("myWebhookBot", ICommandRegistry.class);

            verify(registry, times(1)).register(any(IBotCommand.class));

            var captor = org.mockito.ArgumentCaptor.forClass(IBotCommand.class);
            verify(registry).register(captor.capture());

            IBotCommand adapted = captor.getValue();
            assertThat(adapted.getCommandIdentifier()).isEqualTo("start");
            assertThat(adapted.getDescription()).isEqualTo("Start command");

            StartCommand delegate = context.getBean(StartCommand.class);
            adapted.processMessage(mock(TelegramClient.class), mock(Message.class), new String[]{"a", "b"});
            assertThat(delegate.called).isTrue();
        });
    }

    @Configuration
    static class ExtensionBotConfig {
        @Bean(name = "myWebhookBot")
        ICommandRegistry registry() {
            return mock(ICommandRegistry.class);
        }

        @Bean
        CommandLongPollingTelegramBot CommandLongPollingTelegramBot() {
            return mock(CommandLongPollingTelegramBot.class);
        }

        @Bean
        StartCommand startCommand() {
            return new StartCommand();
        }
    }

    @Command(command = "start", description = "Start command", bot = "myWebhookBot")
    static class StartCommand implements ICommand {
        volatile boolean called = false;

        @Override
        public void processMessage(TelegramClient telegramClient, Message message, String[] arguments) {
            called = true;
        }
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
