package org.telegram.telegrambots.starter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.generics.BotSession;
import org.telegram.telegrambots.meta.generics.LongPollingBot;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

import static java.lang.String.format;

/**
 * Receives all beand which are #LongPollingBot and #WebhookBot and register them in #TelegramBotsApi.
 */
public class TelegramBotInitializer implements InitializingBean {
    private static final Logger log = LoggerFactory.getLogger(TelegramBotInitializer.class);

	private final TelegramBotsApi telegramBotsApi;
    private final List<LongPollingBot> longPollingBots;
    private final List<SpringWebhookBot> webHookBots;

    public TelegramBotInitializer(TelegramBotsApi telegramBotsApi,
    								List<LongPollingBot> longPollingBots,
                                    List<SpringWebhookBot> webHookBots) {
    	Objects.requireNonNull(telegramBotsApi);
    	Objects.requireNonNull(longPollingBots);
    	Objects.requireNonNull(webHookBots);
    	this.telegramBotsApi = telegramBotsApi;
        this.longPollingBots = longPollingBots;
        this.webHookBots = webHookBots;
    }

	@Override
	public void afterPropertiesSet() {
		try {
            for (LongPollingBot bot : longPollingBots) {
                BotSession session = telegramBotsApi.registerBot(bot);
                handleAfterRegistrationHook(bot, session);
            }
            for (SpringWebhookBot bot : webHookBots) {
                telegramBotsApi.registerBot(bot, bot.getSetWebhook());
            }
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }		
	}

	private void handleAnnotatedMethod(Object bot, Method method, BotSession session) {
        try {
            if (method.getParameterCount() > 1) {
                log.warn(format("Method %s of Type %s has too many parameters",
                                method.getName(), method.getDeclaringClass().getCanonicalName()));
                return;
            }
            if (method.getParameterCount() == 0) {
                method.invoke(bot);
                return;
            }
            if (method.getParameterTypes()[0].equals(BotSession.class)) {
                method.invoke(bot, session);
                return;
            }
            log.warn(format("Method %s of Type %s has invalid parameter type",
                            method.getName(), method.getDeclaringClass().getCanonicalName()));
        } catch (InvocationTargetException | IllegalAccessException e) {
            log.error(format("Couldn't invoke Method %s of Type %s",
                    method.getName(), method.getDeclaringClass().getCanonicalName()));
        }
    }

    private void handleAfterRegistrationHook(Object bot, BotSession botSession) {
        Stream.of(bot.getClass().getMethods())
                .filter(method -> method.getAnnotation(AfterBotRegistration.class) != null)
                .forEach(method -> handleAnnotatedMethod(bot, method, botSession));

    }
}
