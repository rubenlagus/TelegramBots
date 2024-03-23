package org.telegram.telegrambots.longpolling.starter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.telegram.telegrambots.longpolling.BotSession;
import org.telegram.telegrambots.longpolling.TelegramBotsLongPollingApplication;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

@Slf4j
public class TelegramBotInitializer implements InitializingBean {

    private final TelegramBotsLongPollingApplication telegramBotsApplication;
    private final List<SpringLongPollingBot> longPollingBots;

    public TelegramBotInitializer(TelegramBotsLongPollingApplication telegramBotsApplication,
                                  List<SpringLongPollingBot> longPollingBots) {
        Objects.requireNonNull(telegramBotsApplication);
        Objects.requireNonNull(longPollingBots);
        this.telegramBotsApplication = telegramBotsApplication;
        this.longPollingBots = longPollingBots;
    }

    @Override
    public void afterPropertiesSet()  {
        try {
            for (SpringLongPollingBot longPollingBot : longPollingBots) {
                BotSession session = telegramBotsApplication.registerBot(longPollingBot.getBotToken(), longPollingBot.getUpdatesConsumer());
                handleAfterRegistrationHook(longPollingBot, session);
            }
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }


    private void handleAfterRegistrationHook(Object bot, BotSession botSession) {
        Stream.of(bot.getClass().getMethods())
                .filter(method -> method.getAnnotation(AfterBotRegistration.class) != null)
                .forEach(method -> handleAnnotatedMethod(bot, method, botSession));

    }

    private void handleAnnotatedMethod(Object bot, Method method, BotSession session) {
        try {
            if (method.getParameterCount() > 1) {
                log.warn(String.format("Method %s of Type %s has too many parameters",
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
            log.warn(String.format("Method %s of Type %s has invalid parameter type",
                    method.getName(), method.getDeclaringClass().getCanonicalName()));
        } catch (InvocationTargetException | IllegalAccessException e) {
            log.error(String.format("Couldn't invoke Method %s of Type %s",
                    method.getName(), method.getDeclaringClass().getCanonicalName()));
        }
    }
}
