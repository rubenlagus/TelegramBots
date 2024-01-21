package org.telegram.telegrambots.webhook.starter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpServerErrorException;
import org.telegram.telegrambots.meta.api.methods.botapimethods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Function;

/**
 * @author Ruben Bermudez
 * @version 1.0
 */
@Slf4j
@RestController
public class TelegramBotsSpringWebhookApplication implements AutoCloseable {
    private final AtomicBoolean isRunning = new AtomicBoolean(false);

    private final ConcurrentHashMap<String, SpringTelegramWebhookBot> registeredBots = new ConcurrentHashMap<>();

    public TelegramBotsSpringWebhookApplication() throws TelegramApiException {
        isRunning.set(true);
    }

    /**
     * Use this method to register a new bot in the webhook server
     * @param botPath New Bot to register
     * @param updateHandler New Bot to register
     * @param setWebhook New Bot to register
     * @param deleteWebhook New Bot to register
     *
     * @implNote Bots can only be registered while server is running
     * @implNote This will trigger a restart of the webhook server if the path already exists
     */
    public void registerBot(
            String botPath,
            Function<Update, BotApiMethod<?>> updateHandler,
            Runnable setWebhook,
            Runnable deleteWebhook
    ) throws TelegramApiException {
        registerBot(SpringTelegramWebhookBot
                .builder()
                .botPath(botPath)
                .updateHandler(updateHandler)
                .setWebhook(setWebhook)
                .deleteWebhook(deleteWebhook)
                .build());
    }

    /**
     * Use this method to unregister a bot in the webhook server
     * @param botPath BotPath to unregister
     */
    public void unregisterBot(String botPath) throws TelegramApiException {
        if (isRunning()) {
            SpringTelegramWebhookBot removedBot = registeredBots.remove(botPath);
            if (removedBot != null) {
                removedBot.runDeleteWebhook();
            }
        } else {
            throw new TelegramApiException("Server is not running");
        }
    }

    /**
     * Use this method to register a new bot in the webhook server
     * @param telegramWebhookBot New Bot to register
     *
     * @implNote Bots can only be registered while server is running
     * @implNote This will trigger a restart of the webhook server if the path already exists
     */
    public void registerBot(SpringTelegramWebhookBot telegramWebhookBot) throws TelegramApiException {
        if (isRunning()) {
            registeredBots.put(telegramWebhookBot.getBotPath(), telegramWebhookBot);
            telegramWebhookBot.runSetWebhook();
        } else {
            throw new TelegramApiException("Server is not running");
        }
    }

    /**
     * Use this method to unregister a bot in the webhook server
     * @param telegramWebhookBot Bot to unregister
     *
     * @implNote Bots can only be unregistered while server is running
     * @implNote This will trigger a restart of the webhook server
     */
    public void unregisterBot(SpringTelegramWebhookBot telegramWebhookBot) throws TelegramApiException {
        this.unregisterBot(telegramWebhookBot.getBotPath());
    }

    public boolean isRunning() {
        return isRunning.get();
    }

    public void start() throws TelegramApiException {
        if (isRunning.get()) {
            throw new TelegramApiException("Server already running");
        }
        for (Map.Entry<String, SpringTelegramWebhookBot> bot : registeredBots.entrySet()) {
            bot.getValue().runSetWebhook();
        }
        isRunning.set(true);
    }

    public void stop() throws TelegramApiException {
        if (isRunning.get()) {
            if (isRunning.get()) {
                for (Map.Entry<String, SpringTelegramWebhookBot> bot : registeredBots.entrySet()) {
                    bot.getValue().runDeleteWebhook();
                }
                isRunning.set(false);
            } else {
                throw new TelegramApiException("Server is not running");
            }
        } else {
            throw new TelegramApiException("Server is not running");
        }
    }

    @PostMapping(
            value = "/{botPath}",
            produces = "application/json",
            consumes = "application/json"
    )
    public BotApiMethod<?> receiveUpdate(@PathVariable("botPath") String botPath,
                                         @RequestBody Update update) {
        if (!isRunning.get()) {
            throw new HttpServerErrorException(HttpStatus.SERVICE_UNAVAILABLE, "Service is not running");
        }
        SpringTelegramWebhookBot responsibleBot = registeredBots.get(botPath);
        if (responsibleBot != null) {
            return responsibleBot.consumeUpdate(update);
        }
        return null;
    }

    @Override
    public void close() throws TelegramApiException {
        this.stop();
    }
}
