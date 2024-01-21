package org.telegram.telegrambots.webhook;

import io.javalin.Javalin;
import io.javalin.community.ssl.SSLPlugin;
import io.javalin.http.ContentType;
import lombok.extern.slf4j.Slf4j;
import org.telegram.telegrambots.common.webhook.TelegramBotsWebhook;
import org.telegram.telegrambots.common.webhook.TelegramWebhookBot;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * @author Ruben Bermudez
 * @version 1.0
 */
@Slf4j
public class TelegramBotsWebhookApplication implements TelegramBotsWebhook {
    private final AtomicBoolean isRunning = new AtomicBoolean(false);

    private final WebhookOptions webhookOptions;

    private final ConcurrentHashMap<String, TelegramWebhookBot> registeredBots = new ConcurrentHashMap<>();

    private Javalin app;

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
     * @param telegramWebhookBot New Bot to register
     *
     * @implNote Bots can only be registered while server is running
     * @implNote This will trigger a restart of the webhook server if the path already exists
     */
    @Override
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
    @Override
    public void unregisterBot(TelegramWebhookBot telegramWebhookBot) throws TelegramApiException {
        if (isRunning()) {
            if (registeredBots.remove(telegramWebhookBot.getBotPath()) != null) {
                telegramWebhookBot.deleteWebhook();
                stop();
                start();
            }
        } else {
            throw new TelegramApiException("Server is not running");
        }
    }

    @Override
    public boolean isRunning() {
        return isRunning.get();
    }

    @Override
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

    @Override
    public void stop() throws TelegramApiException {
        if (isRunning.get()) {
            synchronized (isRunning) {
                if (isRunning.get()) {
                    for (Map.Entry<String, TelegramWebhookBot> bot : registeredBots.entrySet()) {
                        bot.getValue().deleteWebhook();
                    }
                    app.close();
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
                    SSLPlugin sslPlugin = new SSLPlugin(conf -> {
                        if (webhookOptions.getUseHttps()) {
                            conf.keystoreFromPath(webhookOptions.getKeyStorePath(), webhookOptions.getKeyStorePassword());
                            conf.insecure = false;
                            conf.secure = true;
                            conf.securePort = webhookOptions.getPort();
                            javalinConfig.plugins.enableSslRedirects();
                        } else {
                            conf.insecure = true;
                            conf.secure = false;
                            conf.insecurePort = webhookOptions.getPort();
                        }
                    });
                    javalinConfig.plugins.register(sslPlugin);
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
                .start(webhookOptions.getPort());
        for (Map.Entry<String, TelegramWebhookBot> bot : registeredBots.entrySet()) {
            setPostHandler(bot.getValue());
        }
    }

    private void setPostHandler(TelegramWebhookBot telegramWebhookBot) throws TelegramApiException {
        app.post(telegramWebhookBot.getBotPath(), ctx -> {
            Update update = ctx.bodyStreamAsClass(Update.class);
            BotApiMethod<?> response = telegramWebhookBot.onWebhookUpdateReceived(update);
            if (response != null) {
                response.validate();
                ctx.json(response);
            }
            ctx.status(200);
        });
        telegramWebhookBot.setWebhook();
    }

    @Override
    public void close() {
        app.close();
    }
}
