package org.telegram.telegrambots.client.okhttp;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;
import okhttp3.ResponseBody;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.CompletableFuture;

class OkHttpFutureDownloadCallback extends CompletableFuture<InputStream> implements Callback {
    @Override
    public void onFailure(Call call, IOException e) {
        completeExceptionally(e);
    }

    @Override
    public void onResponse(Call call, Response response) throws IOException {
        try(ResponseBody body = response.body()) {
            if (body == null) {
                completeExceptionally(new TelegramApiException("Telegram api returned empty response"));
            } else {
                complete(body.byteStream());
            }
        }
    }
}