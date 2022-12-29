package org.telegram.telegrambots.client;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;
import okhttp3.ResponseBody;
import org.jetbrains.annotations.NotNull;
import org.telegram.telegrambots.meta.api.methods.PartialBotApiMethod;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.exceptions.TelegramApiRequestException;

import java.io.IOException;
import java.io.Serializable;
import java.util.concurrent.CompletableFuture;

class OkHttpFutureCallback<T extends Serializable, Method extends PartialBotApiMethod<T>> extends CompletableFuture<T> implements Callback {
    private final Method method;

    OkHttpFutureCallback(Method method) {
        this.method = method;
    }

    @Override
    public void onFailure(@NotNull Call call, @NotNull IOException e) {
        completeExceptionally(e);
    }

    @Override
    public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
        ResponseBody body = response.body();
        if (body == null) {
            completeExceptionally(new TelegramApiException("Telegram api returned empty response"));
        } else {
            try {
                complete(method.deserializeResponse(body.string()));
            } catch (TelegramApiRequestException e) {
                completeExceptionally(e);
            }
        }
    }
}