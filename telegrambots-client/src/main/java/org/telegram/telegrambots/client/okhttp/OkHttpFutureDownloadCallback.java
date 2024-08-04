package org.telegram.telegrambots.client.okhttp;

import lombok.NonNull;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;
import okhttp3.ResponseBody;
import org.apache.commons.io.IOUtils;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.CompletableFuture;

class OkHttpFutureDownloadCallback extends CompletableFuture<InputStream> implements Callback {
    @Override
    public void onFailure(@NonNull Call call, @NonNull IOException exception) {
        completeExceptionally(exception);
    }

    @Override
    public void onResponse(@NonNull Call call, @NonNull Response response) {
        try(ResponseBody body = response.body()) {
            if (body == null) {
                completeExceptionally(new TelegramApiException("Telegram api returned empty response"));
            } else {
                complete(new ByteArrayInputStream(IOUtils.toByteArray(body.byteStream())));
            }
        } catch (Exception e) {
            completeExceptionally(e);
        }
    }
}