package org.telegram.telegrambots.starter;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.generics.LongPollingBot;
import org.telegram.telegrambots.meta.generics.WebhookBot;

/**
 * #TelegramBotsApi added to spring context as well
 */
@Configuration
@ConditionalOnProperty(prefix="telegrambots",name = "enabled", havingValue = "true", matchIfMissing = true)
public class TelegramBotStarterConfiguration {

    @Bean
    @ConditionalOnMissingBean(TelegramBotsApi.class)
    public TelegramBotsApi telegramBotsApi() {
        return new TelegramBotsApi();
    }

    @Bean
	@ConditionalOnMissingBean
	public TelegramBotInitializer telegramBotInitializer(TelegramBotsApi telegramBotsApi,
										Optional<List<LongPollingBot>> longPollingBots,
							            Optional<List<WebhookBot>> webHookBots) {
		return new TelegramBotInitializer(telegramBotsApi,
						longPollingBots.orElseGet(Collections::emptyList),
						webHookBots.orElseGet(Collections::emptyList));
    }
}
