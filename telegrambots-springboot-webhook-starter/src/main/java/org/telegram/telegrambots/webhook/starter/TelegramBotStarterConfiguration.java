package org.telegram.telegrambots.webhook.starter;

import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.Collections;
import java.util.List;

@Configuration
@ConditionalOnProperty(prefix = "telegrambots", name = "enabled", havingValue = "true", matchIfMissing = true)
public class TelegramBotStarterConfiguration {
    @Bean
    @ConditionalOnMissingBean(TelegramBotsSpringWebhookApplication.class)
    public TelegramBotsSpringWebhookApplication telegramBotsApplication() throws TelegramApiException {
        return new TelegramBotsSpringWebhookApplication();
    }

    @Bean
    @ConditionalOnMissingBean
    public TelegramBotInitializer telegramBotInitializer(TelegramBotsSpringWebhookApplication telegramBotsApplication,
                                                         ObjectProvider<List<SpringTelegramWebhookBot>> webhookBots) {
        return new TelegramBotInitializer(telegramBotsApplication,
                webhookBots.getIfAvailable(Collections::emptyList));
    }
}
