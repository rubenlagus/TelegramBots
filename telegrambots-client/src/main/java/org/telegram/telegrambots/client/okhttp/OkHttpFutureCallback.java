package org.telegram.telegrambots.client.okhttp;

import lombok.NonNull;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;
import okhttp3.ResponseBody;
import org.telegram.telegrambots.meta.api.methods.botapimethods.PartialBotApiMethod;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.IOException;
import java.io.Serializable;
import java.util.concurrent.CompletableFuture;

class OkHttpFutureCallback<T extends Serializable, Method extends PartialBotApiMethod<T>> extends CompletableFuture<T> implements Callback {
    private final Method method;

    OkHttpFutureCallback(Method method) {
        this.method = method;
    }

    @Override
    public void onFailure(@NonNull Call call, @NonNull IOException exception) {
        completeExceptionally(exception);
    }

    @Override
    public void onResponse(@NonNull Call call, @NonNull Response response) {
        T result;
        try(ResponseBody body = response.body()) {
            if (body == null) {
                completeExceptionally(new TelegramApiException("Telegram api returned empty response"));
                return;
            }
            result = method.deserializeResponse(body.string());
        } catch (Exception e) {
            completeExceptionally(e);
            return;
        }
        complete(result);
    }
}
