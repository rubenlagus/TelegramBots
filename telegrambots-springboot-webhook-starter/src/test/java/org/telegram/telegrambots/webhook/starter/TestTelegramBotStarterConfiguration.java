package org.telegram.telegrambots.webhook.starter;

import org.junit.jupiter.api.Test;
import org.springframework.boot.autoconfigure.AutoConfigurations;
import org.springframework.boot.test.context.runner.ApplicationContextRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.telegram.telegrambots.extensions.bots.commandbot.TelegramWebhookCommandBot;
import org.telegram.telegrambots.extensions.bots.commandbot.commands.IBotCommand;
import org.telegram.telegrambots.extensions.bots.commandbot.commands.ICommandRegistry;
import org.telegram.telegrambots.meta.api.methods.botapimethods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.message.Message;
import org.telegram.telegrambots.meta.generics.TelegramClient;
import org.telegram.telegrambots.webhook.starter.extension.Command;
import org.telegram.telegrambots.webhook.starter.extension.CommandRegistrar;
import org.telegram.telegrambots.webhook.starter.extension.ICommand;

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
        TelegramWebhookCommandBot telegramWebhookCommandBot() {
            return mock(TelegramWebhookCommandBot.class);
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
