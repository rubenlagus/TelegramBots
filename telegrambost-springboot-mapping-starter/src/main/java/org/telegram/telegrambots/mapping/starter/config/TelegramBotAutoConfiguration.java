package org.telegram.telegrambots.mapping.starter.config;

import lombok.AllArgsConstructor;
import okhttp3.OkHttpClient;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.telegram.telegrambots.longpolling.TelegramBotsLongPollingApplication;
import org.telegram.telegrambots.longpolling.interfaces.LongPollingUpdateConsumer;
import org.telegram.telegrambots.longpolling.starter.SpringLongPollingBot;
import org.telegram.telegrambots.longpolling.starter.TelegramBotInitializer;
import org.telegram.telegrambots.mapping.starter.core.BotRequestProcessor;

import java.util.Collections;
import java.util.List;

@Configuration
@AllArgsConstructor
@EnableConfigurationProperties(TelegramBotProperties.class)
public class TelegramBotAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean
    public BotRequestProcessor botRequestProcessor() {
        return new BotRequestProcessor();
    }

    @Bean
    @ConditionalOnMissingBean
    public OkHttpClient okHttpClient() {
        return new OkHttpClient();
    }

    @Bean
    public SpringLongPollingBot springLongPollingBot(TelegramBotProperties properties,
                                                     BotRequestProcessor botRequestProcessor) {
        return new SpringLongPollingBot() {
            @Override
            public String getBotToken() {
                return properties.getToken();
            }

            @Override
            public LongPollingUpdateConsumer getUpdatesConsumer() {
                return updates -> updates.forEach(botRequestProcessor::handleUpdate);
            }
        };
    }

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
