package org.telegram.telegrambots.longpolling;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import okhttp3.HttpUrl;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import org.telegram.telegrambots.longpolling.exceptions.TelegramApiErrorResponseException;
import org.telegram.telegrambots.longpolling.interfaces.LongPollingUpdateConsumer;
import org.telegram.telegrambots.meta.TelegramUrl;
import org.telegram.telegrambots.meta.api.methods.botapimethods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.updates.DeleteWebhook;
import org.telegram.telegrambots.meta.api.methods.updates.GetUpdates;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.exceptions.TelegramApiRequestException;
import org.telegram.telegrambots.longpolling.interfaces.BackOff;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;
import java.util.function.Supplier;

import static java.util.Optional.ofNullable;

@Data
@Slf4j
public class BotSession implements AutoCloseable {
    private AtomicBoolean running = new AtomicBoolean(false);
    private AtomicInteger lastReceivedUpdate = new AtomicInteger(0);

    private final ObjectMapper objectMapper;
    private final OkHttpClient okHttpClient;
    private final ScheduledExecutorService executor;
    private final String botToken;
    private final LongPollingUpdateConsumer updatesConsumer;
    private final Supplier<TelegramUrl> telegramUrlSupplier;
    private final Function<Integer, GetUpdates> getUpdatesGenerator;
    private final BackOff backOff;

    private volatile ScheduledFuture<?> runningPolling = null;

    public BotSession(ObjectMapper objectMapper,
                      OkHttpClient okHttpClient,
                      ScheduledExecutorService executor,
                      String botToken,
                      Supplier<TelegramUrl> telegramUrlSupplier,
                      Function<Integer, GetUpdates> getUpdatesGenerator,
                      Supplier<BackOff> backOffSupplier,
                      LongPollingUpdateConsumer updatesConsumer) {
        this.executor = executor;
        this.okHttpClient = okHttpClient;
        this.updatesConsumer = updatesConsumer;
        this.botToken = botToken;
        this.telegramUrlSupplier = telegramUrlSupplier;
        this.getUpdatesGenerator = getUpdatesGenerator;
        this.objectMapper = objectMapper;
        this.backOff = backOffSupplier.get();
    }

    public void start() throws TelegramApiException {
        if (runningPolling == null) {
                runningPolling = createPollerTask();
        }
    }

    public void stop() {
        if (runningPolling != null) {
            runningPolling.cancel(false);
            runningPolling = null;
        }
    }

    public boolean isRunning() {
        return runningPolling != null && !runningPolling.isCancelled();
    }

    @Override
    public void close() throws TelegramApiException {
        stop();
    }

    @NonNull
    private ScheduledFuture<?> createPollerTask() throws TelegramApiException {
        executeDeleteWebhook();
        return executor.scheduleAtFixedRate(() -> {
            try {
                log.debug("Getting updates");
                List<Update> updates = getUpdatesFromTelegram();
                log.debug("Received {} updates.", updates.size());
                // Reset backup with every successful request
                backOff.reset();
                // Handle updates
                if (!updates.isEmpty()) {
                    updates.removeIf(x -> x.getUpdateId() <= lastReceivedUpdate.get());
                    log.debug("{} updates left after filter by last received {}", updates.size(), lastReceivedUpdate.get());
                    lastReceivedUpdate.set(updates.parallelStream()
                            .mapToInt(Update::getUpdateId)
                            .max()
                            .orElse( lastReceivedUpdate.get()));
                    log.debug("New value for last received: {}", lastReceivedUpdate.get());
                    updatesConsumer.consume(updates);
                }
            } catch (TelegramApiErrorResponseException e) {
                long backOffMillis = backOff.nextBackOffMillis();
                log.error("Error received from Telegram GetUpdates Request, retrying in {} millis...", backOffMillis, e);
                try {
                    Thread.sleep(backOffMillis);
                } catch (InterruptedException ex) {
                    // Ignore this
                    log.warn("GetUpdates got interrupted while sleeping in backoff mode.", ex);
                }
            } catch (TelegramApiException  e) {
                log.error(e.getLocalizedMessage(), e);
            }
        }, 1, 1, TimeUnit.MICROSECONDS);
    }

    private List<Update> getUpdatesFromTelegram() throws TelegramApiRequestException, TelegramApiErrorResponseException {
        try {
            TelegramUrl telegramUrl = telegramUrlSupplier.get();
            GetUpdates getUpdates = getUpdatesGenerator.apply(lastReceivedUpdate.get());
            Request request = createRequest(telegramUrl, getUpdates);

            try (Response response = okHttpClient.newCall(request).execute()) {
                if (response.isSuccessful()) {
                    try (ResponseBody body = response.body()) {
                        if (body != null) {
                            List<Update> updates = getUpdates.deserializeResponse(body.string());
                            // Reset backup with every successful request
                            backOff.reset();
                            return updates;
                        } else {
                            log.info("Received empty body when calling getUpdates");
                        }
                    }
                } else {
                    throw new TelegramApiErrorResponseException(response.code(), response.message());
                }
            }
        } catch (Exception e) {
            throw new TelegramApiErrorResponseException(e);
        }

        return Collections.emptyList();
    }

    private void executeDeleteWebhook() throws TelegramApiRequestException, TelegramApiErrorResponseException {
        DeleteWebhook deleteWebhook = new DeleteWebhook();
        try {
            Request request = createRequest(telegramUrlSupplier.get(), deleteWebhook);
            try (Response response = okHttpClient.newCall(request).execute()) {
                if (response.isSuccessful()) {
                    try (ResponseBody body = response.body()) {
                        if (body != null) {
                            Boolean result = deleteWebhook.deserializeResponse(body.string());
                            log.info("Result when calling deleteWebhook: {}", result);
                            if (!ofNullable(result).orElse(false)) {
                                throw new TelegramApiErrorResponseException("Unable to delete Webhook");
                            }
                        } else {
                            log.info("Received empty result when calling deleteWebhook");
                        }
                    }
                } else {
                    throw new TelegramApiErrorResponseException(response.code(), response.message());
                }
            }
        } catch (IOException e) {
            throw new TelegramApiErrorResponseException("Unable to execute " + deleteWebhook.getMethod() + " method", e);
        }
    }

    @NonNull
    private Request createRequest(TelegramUrl telegramUrl, BotApiMethod<?> apiMethod) throws JsonProcessingException {
        return new Request.Builder()
                .url(buildUrl(telegramUrl, apiMethod.getMethod()))
                .header("charset", StandardCharsets.UTF_8.name())
                .header("content-type", "application/json")
                .post(RequestBody.create(objectMapper.writeValueAsString(apiMethod), MediaType.parse("application/json")))
                .build();
    }

    @NonNull
    private HttpUrl buildUrl(TelegramUrl telegramUrl, String methodPath) {
        HttpUrl.Builder builder = new HttpUrl
                .Builder()
                .scheme(telegramUrl.getSchema())
                .host(telegramUrl.getHost())
                .port(telegramUrl.getPort())
                .addPathSegment("bot" + botToken);

        if (telegramUrl.isTestServer()) {
            builder.addPathSegment("test");
        }

        builder.addPathSegment(methodPath);

        return builder.build();
    }
}
