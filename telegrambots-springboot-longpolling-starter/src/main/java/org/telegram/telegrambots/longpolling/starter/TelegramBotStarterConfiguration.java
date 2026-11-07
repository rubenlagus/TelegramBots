package org.telegram.telegrambots.longpolling.starter;

import org.springframework.beans.factory.ListableBeanFactory;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.telegram.telegrambots.extensions.bots.commandbot.CommandLongPollingTelegramBot;
import org.telegram.telegrambots.extensions.bots.commandbot.commands.ICommandRegistry;
import org.telegram.telegrambots.longpolling.TelegramBotsLongPollingApplication;
import org.telegram.telegrambots.longpolling.starter.extension.CommandRegistrar;
import org.telegram.telegrambots.meta.TelegramUrl;

import java.util.Collections;
import java.util.List;
import java.util.Map;

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
                                                         ObjectProvider<List<SpringLongPollingBot>> longPollingBots,
                                                         ObjectProvider<TelegramUrl> telegramUrl) {
        return new TelegramBotInitializer(telegramBotsApplication,
                longPollingBots.getIfAvailable(Collections::emptyList),
                telegramUrl.getIfAvailable(() -> TelegramUrl.DEFAULT_URL)
        );
    }

    @Bean
    @ConditionalOnMissingBean
    @ConditionalOnBean(CommandLongPollingTelegramBot.class)
    public CommandRegistrar commandRegistrar(ListableBeanFactory beanFactory,
                                             Map<String, ICommandRegistry> registries) {
        return new CommandRegistrar(beanFactory, registries);
    }
}
