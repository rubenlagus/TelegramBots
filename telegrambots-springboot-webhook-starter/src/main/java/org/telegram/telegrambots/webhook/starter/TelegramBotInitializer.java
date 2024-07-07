package org.telegram.telegrambots.webhook.starter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

@Slf4j
public class TelegramBotInitializer implements InitializingBean {

    private final TelegramBotsSpringWebhookApplication telegramBotsApplication;
    private final List<SpringTelegramWebhookBot> webhookBots;

    public TelegramBotInitializer(TelegramBotsSpringWebhookApplication telegramBotsApplication,
                                  List<SpringTelegramWebhookBot> webhookBots) {
        Objects.requireNonNull(telegramBotsApplication);
        Objects.requireNonNull(webhookBots);
        this.telegramBotsApplication = telegramBotsApplication;
        this.webhookBots = webhookBots;
    }

    @Override
    public void afterPropertiesSet()  {
        try {
            for (SpringTelegramWebhookBot webhookBot : webhookBots) {
                telegramBotsApplication.registerBot(webhookBot);
                handleAfterRegistrationHook(webhookBot);
            }
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }


    private void handleAfterRegistrationHook(Object bot) {
        Stream.of(bot.getClass().getMethods())
                .filter(method -> method.getAnnotation(AfterBotRegistration.class) != null)
                .forEach(method -> handleAnnotatedMethod(bot, method));

    }

    private void handleAnnotatedMethod(Object bot, Method method) {
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
            log.warn(String.format("Method %s of Type %s has invalid parameter type",
                    method.getName(), method.getDeclaringClass().getCanonicalName()));
        } catch (InvocationTargetException | IllegalAccessException e) {
            log.error(String.format("Couldn't invoke Method %s of Type %s",
                    method.getName(), method.getDeclaringClass().getCanonicalName()));
        }
    }
}
