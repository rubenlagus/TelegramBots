package org.telegram.telegrambots.starter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.telegram.telegrambots.TelegramBotsApi;
import org.telegram.telegrambots.exceptions.TelegramApiRequestException;
import org.telegram.telegrambots.generics.LongPollingBot;
import org.telegram.telegrambots.generics.WebhookBot;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Optional;

/**
 * Receives all beand which are #LongPollingBot and #WebhookBot and register them in #TelegramBotsApi.
 * #TelegramBotsApi added to spring context as well
 */
@Configuration
public class TelegramBotStarterConfiguration {


    private final List<LongPollingBot> longPollingBots;
    private final List<WebhookBot> webHookBots;

    private TelegramBotsApi telegramBotsApi;

    @Autowired
    public void setTelegramBotsApi(TelegramBotsApi telegramBotsApi) {
        this.telegramBotsApi = telegramBotsApi;
    }

    public TelegramBotStarterConfiguration(@Autowired(required = false) List<LongPollingBot> longPollingBots,
                                           @Autowired(required = false) List<WebhookBot> webHookBots) {

        this.longPollingBots = longPollingBots;
        this.webHookBots = webHookBots;
    }

    @PostConstruct
    public void registerBots() {
        Optional.ofNullable(longPollingBots).ifPresent(bots -> {
            bots.forEach(bot -> {
                try {
                    telegramBotsApi.registerBot(bot);
                } catch (TelegramApiRequestException e) {
                    throw new RuntimeException(e);
                }
            });
        });

        Optional.ofNullable(webHookBots).ifPresent(bots -> {
            bots.forEach(bot -> {
                try {
                    telegramBotsApi.registerBot(bot);
                } catch (TelegramApiRequestException e) {
                    throw new RuntimeException(e);
                }
            });
        });
    }


    @Bean
    @ConditionalOnMissingBean(TelegramBotsApi.class)
    public TelegramBotsApi telegramBotsApi() {
        return new TelegramBotsApi();
    }
}
