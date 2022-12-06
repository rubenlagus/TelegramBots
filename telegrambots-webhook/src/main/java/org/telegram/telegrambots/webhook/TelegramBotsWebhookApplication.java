package org.telegram.telegrambots.webhook;

import io.javalin.Javalin;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.jetty.alpn.server.ALPNServerConnectionFactory;
import org.eclipse.jetty.http2.HTTP2Cipher;
import org.eclipse.jetty.http2.server.HTTP2ServerConnectionFactory;
import org.eclipse.jetty.server.HttpConfiguration;
import org.eclipse.jetty.server.HttpConnectionFactory;
import org.eclipse.jetty.server.SecureRequestCustomizer;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.server.SslConnectionFactory;
import org.eclipse.jetty.util.ssl.SslContextFactory;
import org.telegram.telegrambots.common.webhook.TelegramBotsWebhook;
import org.telegram.telegrambots.common.webhook.TelegramWebhookBot;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import javax.ws.rs.core.MediaType;
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
    public void registerBot(TelegramWebhookBot telegramWebhookBot) {
        if (isRunning()) {
            if (registeredBots.put(telegramWebhookBot.getBotPath(), telegramWebhookBot) == null) {
                setPostHandler(telegramWebhookBot);
            } else {
                stop();
                start();
            }
        } else {
            throw new RuntimeException("Server is not running");
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
    public void unregisterBot(TelegramWebhookBot telegramWebhookBot) {
        if (isRunning()) {
            if (registeredBots.remove(telegramWebhookBot.getBotPath()) != null) {
                // TODO Call delete webhook
                stop();
                start();
            }
        } else {
            throw new RuntimeException("Server is not running");
        }
    }

    @Override
    public boolean isRunning() {
        synchronized (isRunning) {
            return isRunning.get();
        }
    }

    @Override
    public void start() {
        if (isRunning.get()) {
            throw new RuntimeException("Server already running");
        }
        synchronized (isRunning) {
            if (isRunning.get()) {
                throw new RuntimeException("Server already running");
            }
            startServerInternal();
            isRunning.set(true);
        }
    }

    @Override
    public void stop() {
        if (isRunning.get()) {
            synchronized (isRunning) {
                if (isRunning.get()) {
                    app.close();
                    app = null;
                    isRunning.set(false);
                } else {
                    throw new RuntimeException("Server is not running");
                }
            }
        } else {
            throw new RuntimeException("Server is not running");
        }
    }

    private void startServerInternal() {
        app = Javalin
                .create(javalinConfig -> {
                    if (webhookOptions.getUseHttps()) {
                        javalinConfig.jetty.server(this::createHttp2Server);
                    }
                    javalinConfig.http.defaultContentType = MediaType.APPLICATION_JSON;
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

    private void setPostHandler(TelegramWebhookBot telegramWebhookBot) {
        app.post(telegramWebhookBot.getBotPath(), ctx -> {
            Update update = ctx.bodyStreamAsClass(Update.class);
            BotApiMethod<?> response = telegramWebhookBot.onWebhookUpdateReceived(update);
            if (response != null) {
                response.validate();
                ctx.json(response);
            }
            ctx.status(200);
        });
        // TODO Call SetWebhook method
    }

    private Server createHttp2Server() {
        Server server = new Server();

        // HTTP Configuration
        HttpConfiguration httpConfig = new HttpConfiguration();
        httpConfig.setSendServerVersion(false);
        httpConfig.setSecureScheme("https");
        httpConfig.setSecurePort(webhookOptions.getPort());

        // SSL Context Factory for HTTPS and HTTP/2
        SslContextFactory.Server sslContextFactory = new SslContextFactory.Server.Server();
        sslContextFactory.setKeyStorePath(webhookOptions.getKeyStorePath());
        sslContextFactory.setKeyStorePassword(webhookOptions.getKeyStorePassword());
        sslContextFactory.setCipherComparator(HTTP2Cipher.COMPARATOR);
        sslContextFactory.setProvider("Conscrypt");

        // HTTPS Configuration
        HttpConfiguration httpsConfig = new HttpConfiguration(httpConfig);
        httpsConfig.addCustomizer(new SecureRequestCustomizer());

        // HTTP/2 Connection Factory
        HTTP2ServerConnectionFactory h2 = new HTTP2ServerConnectionFactory(httpsConfig);
        ALPNServerConnectionFactory alpn = new ALPNServerConnectionFactory();
        alpn.setDefaultProtocol("h2");

        // SSL Connection Factory
        SslConnectionFactory ssl = new SslConnectionFactory(sslContextFactory, alpn.getProtocol());

        // HTTP/2 Connector
        ServerConnector http2Connector = new ServerConnector(server, ssl, alpn, h2, new HttpConnectionFactory(httpsConfig));
        http2Connector.setPort(webhookOptions.getPort());
        server.addConnector(http2Connector);

        return server;
    }

    @Override
    public void close() throws Exception {
        app.close();
    }
}
