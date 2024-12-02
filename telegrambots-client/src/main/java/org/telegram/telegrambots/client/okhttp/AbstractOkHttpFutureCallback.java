package org.telegram.telegrambots.client.okhttp;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;
import okhttp3.ResponseBody;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.exceptions.TelegramApiRequestException;

import java.io.IOException;
import java.util.concurrent.CompletableFuture;

@Slf4j
abstract class AbstractOkHttpFutureCallback<T> extends CompletableFuture<T> implements Callback {
    @Override
    public void onFailure(@NonNull Call call, @NonNull IOException exception) {
        completeExceptionally(exception);
    }

    @Override
    public void onResponse(@NonNull Call call, @NonNull Response response) {
        if (!response.isSuccessful()) {
            var messageBuilder = new StringBuilder();
            messageBuilder.append("code: ").append(response.code());
            messageBuilder.append(", message: ").append(response.message());

            if(response.body() != null) {
                try {
                    messageBuilder.append(", response body: ").append(response.body().string());
                } catch (IOException e) {
                    log.error("Error reading response body", e);
                }
            }

            completeExceptionally(new TelegramApiException("Telegram API returned error: " + messageBuilder));
            return;
        }

        try(ResponseBody body = response.body()) {
            if (body == null) {
                completeExceptionally(new TelegramApiException("Telegram api returned empty response"));
            } else {
                complete(getResponseValue(body));
            }
        } catch (Exception e) {
            completeExceptionally(e);
        }
    }

    protected abstract T getResponseValue(ResponseBody body) throws IOException, TelegramApiRequestException;
}
