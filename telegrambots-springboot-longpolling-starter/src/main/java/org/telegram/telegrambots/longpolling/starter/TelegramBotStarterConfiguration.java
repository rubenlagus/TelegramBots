package org.telegram.telegrambots.longpolling.starter;

import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.telegram.telegrambots.longpolling.TelegramBotsLongPollingApplication;

import java.util.Collections;
import java.util.List;

@Configuration
@ConditionalOnProperty(prefix = "telegrambots", name = "enabled", havingValue = "true", matchIfMissing = true)
public class TelegramBotStarterConfiguration {
    @Bean
    @ConditionalOnMissingBean(TelegramBotsLongPollingApplication.class)
    public TelegramBotsLongPollingApplication telegramBotsApplication() {
        return new TelegramBotsLongPollingApplication();
    }

    @Bean
    @ConditionalOnMissingBean
    public TelegramBotInitializer telegramBotInitializer(TelegramBotsLongPollingApplication telegramBotsApplication,
                                                         ObjectProvider<List<SpringLongPollingBot>> longPollingBots) {
        return new TelegramBotInitializer(telegramBotsApplication,
                longPollingBots.getIfAvailable(Collections::emptyList));
    }
}
