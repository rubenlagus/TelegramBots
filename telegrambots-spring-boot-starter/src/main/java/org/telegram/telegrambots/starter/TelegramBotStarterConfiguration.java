package org.telegram.telegrambots.starter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.generics.LongPollingBot;
import org.telegram.telegrambots.meta.generics.WebhookBot;

import java.util.List;

/**
 * Receives all beand which are #LongPollingBot and #WebhookBot and register them in #TelegramBotsApi.
 * #TelegramBotsApi added to spring context as well
 */
@Configuration
public class TelegramBotStarterConfiguration implements CommandLineRunner {


    private final List<LongPollingBot> longPollingBots;
    private final List<WebhookBot> webHookBots;

    @Autowired
    private TelegramBotsApi telegramBotsApi;

    public TelegramBotStarterConfiguration(List<LongPollingBot> longPollingBots,
                                           List<WebhookBot> webHookBots) {
        this.longPollingBots = longPollingBots;
        this.webHookBots = webHookBots;
    }

    @Override
    public void run(String... args) {
        try {
            for (LongPollingBot bot : longPollingBots) {
                telegramBotsApi.registerBot(bot);
            }
            for (WebhookBot bot : webHookBots) {
                telegramBotsApi.registerBot(bot);
            }
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }


    @Bean
    @ConditionalOnMissingBean(TelegramBotsApi.class)
    public TelegramBotsApi telegramBotsApi() {
        return new TelegramBotsApi();
    }
}
