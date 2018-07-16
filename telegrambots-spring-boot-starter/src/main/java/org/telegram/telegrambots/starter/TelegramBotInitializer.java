package org.telegram.telegrambots.starter;

import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.InitializingBean;
import org.telegram.telegrambots.TelegramBotsApi;
import org.telegram.telegrambots.exceptions.TelegramApiException;
import org.telegram.telegrambots.generics.LongPollingBot;
import org.telegram.telegrambots.generics.WebhookBot;

/**
 * Receives all beand which are #LongPollingBot and #WebhookBot and register them in #TelegramBotsApi.
 */
public class TelegramBotInitializer implements InitializingBean {

	private final TelegramBotsApi telegramBotsApi;
    private final List<LongPollingBot> longPollingBots;
    private final List<WebhookBot> webHookBots;

    public TelegramBotInitializer(TelegramBotsApi telegramBotsApi,
    								List<LongPollingBot> longPollingBots,
                                    List<WebhookBot> webHookBots) {
    	Objects.requireNonNull(telegramBotsApi);
    	Objects.requireNonNull(longPollingBots);
    	Objects.requireNonNull(webHookBots);
    	this.telegramBotsApi = telegramBotsApi;
        this.longPollingBots = longPollingBots;
        this.webHookBots = webHookBots;
    }

	@Override
	public void afterPropertiesSet() throws Exception {
		try {
            for (LongPollingBot bot : longPollingBots) {
                telegramBotsApi.registerBot(bot);
            }
            for (WebhookBot bot : webHookBots) {
                telegramBotsApi.registerBot(bot);
            }
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }		
	}
}
