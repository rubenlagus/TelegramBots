package org.telegram.telegrambots.webhook;

import io.javalin.Javalin;
import io.javalin.community.ssl.SslPlugin;
import io.javalin.http.ContentType;
import lombok.extern.slf4j.Slf4j;
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
public class TelegramBotsWebhookApplication implements AutoCloseable {
    private final AtomicBoolean isRunning = new AtomicBoolean(false);

    private final WebhookOptions webhookOptions;

    private final ConcurrentHashMap<String, TelegramWebhookBot> registeredBots = new ConcurrentHashMap<>();

    private Javalin app;

    public TelegramBotsWebhookApplication() throws TelegramApiException {
        this(WebhookOptions.builder().build());
    }

    public TelegramBotsWebhookApplication(WebhookOptions webhookOptions) throws TelegramApiException {
        webhookOptions.validate();
        this.webhookOptions = webhookOptions;
        synchronized (isRunning) {
            startServerInternal();
            isRunning.set(true);
        }
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
        registerBot(DefaultTelegramWebhookBot
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
     *
     * @implNote Bots can only be unregistered while server is running
     * @implNote This will trigger a restart of the webhook server
     */
    public void unregisterBot(String botPath) throws TelegramApiException {
        if (isRunning()) {
            TelegramWebhookBot removedBot = registeredBots.remove(botPath);
            if (removedBot != null) {
                removedBot.runDeleteWebhook();
                stop();
                start();
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
    public void registerBot(TelegramWebhookBot telegramWebhookBot) throws TelegramApiException {
        if (isRunning()) {
            if (registeredBots.put(telegramWebhookBot.getBotPath(), telegramWebhookBot) == null) {
                setPostHandler(telegramWebhookBot);
            } else {
                stop();
                start();
            }
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
    public void unregisterBot(TelegramWebhookBot telegramWebhookBot) throws TelegramApiException {
        this.unregisterBot(telegramWebhookBot.getBotPath());
    }

    public boolean isRunning() {
        return isRunning.get();
    }

    public void start() throws TelegramApiException {
        if (isRunning.get()) {
            throw new TelegramApiException("Server already running");
        }
        synchronized (isRunning) {
            if (isRunning.get()) {
                throw new TelegramApiException("Server already running");
            }
            startServerInternal();
            isRunning.set(true);
        }
    }

    public void stop() throws TelegramApiException {
        if (isRunning.get()) {
            synchronized (isRunning) {
                if (isRunning.get()) {
                    for (Map.Entry<String, TelegramWebhookBot> bot : registeredBots.entrySet()) {
                        bot.getValue().runDeleteWebhook();
                    }
                    app.stop();
                    app = null;
                    isRunning.set(false);
                } else {
                    throw new TelegramApiException("Server is not running");
                }
            }
        } else {
            throw new TelegramApiException("Server is not running");
        }
    }

    private void startServerInternal() throws TelegramApiException {
        app = Javalin
                .create(javalinConfig -> {
                    SslPlugin sslPlugin = new SslPlugin(conf -> {
                        if (webhookOptions.getUseHttps()) {
                            conf.keystoreFromPath(webhookOptions.getKeyStorePath(), webhookOptions.getKeyStorePassword());
                            conf.insecure = false;
                            conf.secure = true;
                            conf.securePort = webhookOptions.getPort();
                            javalinConfig.bundledPlugins.enableSslRedirects();
                        } else {
                            conf.insecure = true;
                            conf.secure = false;
                            conf.insecurePort = webhookOptions.getPort();
                        }
                    });
                    javalinConfig.registerPlugin(sslPlugin);
                    javalinConfig.http.defaultContentType = ContentType.JSON;
                    javalinConfig.requestLogger.http((ctx, executionTimeMs) -> {
                        if (webhookOptions.getEnableRequestLogging()) {
                            log.info("Webhook {} request received from {}", ctx.method(), ctx.req().getRemoteAddr());
                        }
                    });
                })
                .events(events -> {
                    events.serverStarted(() -> log.info("Webhook server started"));
                    events.serverStopped(() -> log.info("Webhook server stopped"));
                })
                .start();
        for (Map.Entry<String, TelegramWebhookBot> bot : registeredBots.entrySet()) {
            setPostHandler(bot.getValue());
        }
    }

    private void setPostHandler(TelegramWebhookBot telegramWebhookBot) {
        app.post(telegramWebhookBot.getBotPath(), ctx -> {
            Update update = ctx.bodyStreamAsClass(Update.class);
            BotApiMethod<?> response = telegramWebhookBot.consumeUpdate(update);
            if (response != null) {
                response.validate();
                ctx.json(response);
            }
            ctx.status(200);
        });
        telegramWebhookBot.runSetWebhook();
    }


    @Override
    public void close() throws TelegramApiException {
        this.stop();
    }
}
