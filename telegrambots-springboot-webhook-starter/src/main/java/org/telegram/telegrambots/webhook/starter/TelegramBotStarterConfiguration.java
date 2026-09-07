package org.telegram.telegrambots.webhook.starter;

import org.springframework.beans.factory.ListableBeanFactory;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.telegram.telegrambots.extensions.bots.commandbot.TelegramWebhookCommandBot;
import org.telegram.telegrambots.extensions.bots.commandbot.commands.ICommandRegistry;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.webhook.TelegramWebhookBot;
import org.telegram.telegrambots.webhook.starter.extension.CommandRegistrar;

import java.util.Collections;
import java.util.List;
import java.util.Map;

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
                                                         ObjectProvider<List<TelegramWebhookBot>> webhookBots) {
        return new TelegramBotInitializer(telegramBotsApplication,
                webhookBots.getIfAvailable(Collections::emptyList));
    }

    @Bean
    @ConditionalOnMissingBean
    @ConditionalOnBean(TelegramWebhookCommandBot.class)
    public CommandRegistrar commandRegistrar(ListableBeanFactory beanFactory,
                                             Map<String, ICommandRegistry> registries) {
        return new CommandRegistrar(beanFactory, registries);
    }
}
