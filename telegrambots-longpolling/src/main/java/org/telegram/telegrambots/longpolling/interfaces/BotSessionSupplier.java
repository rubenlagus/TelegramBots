package org.telegram.telegrambots.longpolling.interfaces;

import okhttp3.OkHttpClient;
import org.telegram.telegrambots.longpolling.BotSession;
import org.telegram.telegrambots.meta.TelegramUrl;
import org.telegram.telegrambots.meta.api.methods.updates.GetUpdates;
import tools.jackson.databind.json.JsonMapper;

import java.util.concurrent.ScheduledExecutorService;
import java.util.function.Function;
import java.util.function.Supplier;

@FunctionalInterface
public interface BotSessionSupplier {
    BotSession get(
            JsonMapper jsonMapper,
            OkHttpClient okHttpClient,
            ScheduledExecutorService executor,
            String botToken,
            Supplier<TelegramUrl> telegramUrlSupplier,
            Function<Integer, GetUpdates> getUpdatesGenerator,
            Supplier<BackOff> backOffSupplier,
            LongPollingUpdateConsumer updatesConsumer
    );
}
