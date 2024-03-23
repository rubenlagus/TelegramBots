package org.telegram.telegrambots.longpolling;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import okhttp3.OkHttpClient;
import org.telegram.telegrambots.longpolling.interfaces.LongPollingUpdateConsumer;
import org.telegram.telegrambots.longpolling.util.DefaultGetUpdatesGenerator;
import org.telegram.telegrambots.longpolling.util.ExponentialBackOff;
import org.telegram.telegrambots.longpolling.util.TelegramOkHttpClientFactory;
import org.telegram.telegrambots.meta.TelegramUrl;
import org.telegram.telegrambots.meta.api.methods.updates.GetUpdates;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.longpolling.interfaces.BackOff;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Function;
import java.util.function.Supplier;

@Slf4j
public class TelegramBotsLongPollingApplication implements AutoCloseable {
    private final AtomicBoolean isAppRunning = new AtomicBoolean(true);

    private final Supplier<ObjectMapper> objectMapperSupplier;
    private final Supplier<OkHttpClient> okHttpClientCreator;
    private final Supplier<ScheduledExecutorService> executorSupplier;
    private final Supplier<BackOff> backOffSupplier;


    private final ConcurrentHashMap<String, BotSession> botSessions = new ConcurrentHashMap<>();

    public TelegramBotsLongPollingApplication() {
        this(ObjectMapper::new);
    }

    public TelegramBotsLongPollingApplication(Supplier<ObjectMapper> objectMapperSupplier) {
        this(objectMapperSupplier, new TelegramOkHttpClientFactory.DefaultOkHttpClientCreator());
    }

    public TelegramBotsLongPollingApplication(Supplier<ObjectMapper> objectMapperSupplier, Supplier<OkHttpClient> okHttpClientCreator) {
        this(objectMapperSupplier, okHttpClientCreator, Executors::newSingleThreadScheduledExecutor);
    }

    public TelegramBotsLongPollingApplication(Supplier<ObjectMapper> objectMapperSupplier,
                                              Supplier<OkHttpClient> okHttpClientCreator,
                                              Supplier<ScheduledExecutorService> executorSupplier) {
        this(objectMapperSupplier, okHttpClientCreator, executorSupplier, ExponentialBackOff::new);
    }

    public TelegramBotsLongPollingApplication(Supplier<ObjectMapper> objectMapperSupplier,
                                              Supplier<OkHttpClient> okHttpClientCreator,
                                              Supplier<ScheduledExecutorService> executorSupplier,
                                              Supplier<BackOff> backOffSupplier) {
        this.objectMapperSupplier = objectMapperSupplier;
        this.okHttpClientCreator = okHttpClientCreator;
        this.executorSupplier = executorSupplier;
        this.backOffSupplier = backOffSupplier;
    }

    public BotSession registerBot(String botToken, LongPollingUpdateConsumer updatesConsumer) throws TelegramApiException {
        return registerBot(botToken, () -> TelegramUrl.DEFAULT_URL, new DefaultGetUpdatesGenerator(), updatesConsumer);
    }

    public BotSession registerBot(String botToken,
                                  Supplier<TelegramUrl> telegramUrlSupplier,
                                  Function<Integer, GetUpdates> getUpdatesGenerator,
                                  LongPollingUpdateConsumer updatesConsumer) throws TelegramApiException {
        if (botSessions.containsKey(botToken)) {
            throw new TelegramApiException("Bot is already registered");
        } else {
            BotSession botSession = new BotSession(
                    objectMapperSupplier.get(),
                    okHttpClientCreator.get(),
                    executorSupplier.get(),
                    botToken,
                    telegramUrlSupplier,
                    getUpdatesGenerator,
                    backOffSupplier,
                    updatesConsumer
                    );
            botSessions.put(botToken, botSession);
            if (isAppRunning.get()) {
                botSession.start();
            }
            return botSession;
        }
    }

    public void unregisterBot(String botToken) throws TelegramApiException {
        if (botSessions.containsKey(botToken)) {
            BotSession botSession = botSessions.remove(botToken);
            botSession.stop();
        } else {
            throw new TelegramApiException("Bot is not registered");
        }
    }

    public boolean isRunning() {
        return isAppRunning.get() && botSessions.values().stream().allMatch(BotSession::isRunning);
    }

    public void start() throws TelegramApiException {
        if (isAppRunning.get()) {
            throw new TelegramApiException("App is already running");
        }
        if (!botSessions.isEmpty() && botSessions.values().stream().allMatch(BotSession::isRunning)) {
            throw new TelegramApiException("All bots already running");
        }
        isAppRunning.set(true);
        for (BotSession botSession : botSessions.values()) {
            if (!botSession.isRunning()) {
                botSession.start();
            }
        }
    }

    public void stop() throws TelegramApiException {
        if (!isAppRunning.get()) {
            throw new TelegramApiException("App is not running");
        }
        if (!botSessions.isEmpty() && botSessions.values().stream().noneMatch(BotSession::isRunning)) {
            throw new TelegramApiException("All bots already stopped");
        }
        isAppRunning.set(false);
        for (BotSession botSession : botSessions.values()) {
            if (botSession.isRunning()) {
                botSession.stop();
            }
        }
    }

    @Override
    public void close() throws Exception {
        isAppRunning.set(false);
        for (BotSession botSession : botSessions.values()) {
            if (botSession != null) {
                botSession.close();
            }
        }
    }
}
