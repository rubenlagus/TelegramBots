package org.telegram.telegrambots.webhook.starter;

import lombok.Getter;
import org.junit.jupiter.api.Test;
import org.springframework.boot.autoconfigure.AutoConfigurations;
import org.springframework.boot.test.context.runner.ApplicationContextRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

class TestTelegramBotStarterRegistrationHooks {

    private static final TelegramBotsSpringWebhookApplication mockApplication = mock(TelegramBotsSpringWebhookApplication.class);

    private final ApplicationContextRunner contextRunner = new ApplicationContextRunner()
			.withAllowBeanDefinitionOverriding(true)
            .withConfiguration(AutoConfigurations.of(MockTelegramApplication.class,
                                                     TelegramBotStarterConfiguration.class));

    @Test
    void createOnlyWebhookBot() {
        this.contextRunner.withUserConfiguration(WebookBotConfig.class)
                .run((context) -> {
                    assertThat(context).hasSingleBean(AnnotatedWebhookBot.class);

                    TelegramBotsSpringWebhookApplication telegramApplication = context.getBean(TelegramBotsSpringWebhookApplication.class);
                    SpringTelegramWebhookBot bot = context.getBean(SpringTelegramWebhookBot.class);

                    verify(telegramApplication, times(1)).registerBot(eq(bot));
                    verifyNoMoreInteractions(telegramApplication);
                    assertInstanceOf(AnnotatedWebhookBot.class, bot);
                    assertTrue(((AnnotatedWebhookBot) bot).isHookCalled());
                });
    }


    @Configuration
    public static class MockTelegramApplication {
        @Bean
        public TelegramBotsSpringWebhookApplication telegramBotsApplication() {
            return mockApplication;
        }
    }

    @Configuration
    public static class WebookBotConfig {
        @Bean
        public SpringTelegramWebhookBot longPollingBot() {
            return new AnnotatedWebhookBot();
        }
    }

    @Getter
    public static class AnnotatedWebhookBot extends SpringTelegramWebhookBot {
        private boolean hookCalled = false;

        public AnnotatedWebhookBot() {
            super("TEST", update -> null, () -> { }, () -> { });
        }

        @AfterBotRegistration
        public void afterBotHook() {
            hookCalled = true;
        }
    }
}
