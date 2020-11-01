package org.telegram.telegrambots.starter;

import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.generics.LongPollingBot;

import java.util.Collections;
import java.util.List;
/**
 * #TelegramBotsApi added to spring context as well
 */
@Configuration
@ConditionalOnProperty(prefix = "telegrambots", name = "enabled", havingValue = "true", matchIfMissing = true)
public class TelegramBotStarterConfiguration {

    @Bean
    @ConditionalOnMissingBean(TelegramBotsApi.class)
    public TelegramBotsApi telegramBotsApi() {
        return new TelegramBotsApi();
    }

    @Bean
    @ConditionalOnMissingBean
    public TelegramBotInitializer telegramBotInitializer(TelegramBotsApi telegramBotsApi,
                                                         ObjectProvider<List<LongPollingBot>> longPollingBots,
                                                         ObjectProvider<List<SpringWebhookBot>> webHookBots) {
        return new TelegramBotInitializer(telegramBotsApi,
                longPollingBots.getIfAvailable(Collections::emptyList),
                webHookBots.getIfAvailable(Collections::emptyList));
    }
}
