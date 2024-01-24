package org.telegram.telegrambots.client.okhttp;

import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import org.telegram.telegrambots.meta.api.methods.PartialBotApiMethod;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.Serializable;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;

public class OkHttpTelegramClient extends TelegramHttpClient {
    final OkHttpClient client;

    public OkHttpTelegramClient(ObjectMapper objectMapper, OkHttpClient client, String botToken, String baseUrl) {
        super(Objects.requireNonNull(objectMapper), Objects.requireNonNull(botToken), Objects.requireNonNull(baseUrl));
        this.client = Objects.requireNonNull(client);
    }

    public OkHttpTelegramClient(OkHttpClient client, String botToken, String baseUrl) {
        this(new ObjectMapper(), client, botToken, baseUrl);
    }

    public OkHttpTelegramClient(OkHttpClient client, String botToken) {
        this(client, botToken, "https://api.telegram.org");
    }

    public OkHttpTelegramClient(String botToken, String baseUrl) {
        this(new OkHttpClient.Builder().build(), botToken, baseUrl);
    }

    public OkHttpTelegramClient(String botToken) {
        this(new OkHttpClient.Builder().build(), botToken);
    }


    @Override
    protected <T extends Serializable, Method extends PartialBotApiMethod<T>> CompletableFuture<T> sendRequest(Method method, RequestBody body) throws TelegramApiException {
        if (method == null) {
            throw new TelegramApiException("Parameter method can not be null");
        }
        if (body == null) {
            throw new TelegramApiException("Parameter body can not be null");
        }

        Request request = new Request.Builder().post(body).url(buildUrl(method.getMethod())).build();

        OkHttpFutureCallback<T, Method> callback = new OkHttpFutureCallback<>(method);
        client.newCall(request).enqueue(callback);

        return callback;
    }

    @Override
    protected <T extends Serializable, Method extends PartialBotApiMethod<T>> CompletableFuture<T> sendRequest(Method method, MultipartBody body) throws TelegramApiException {
        return sendRequest(method, (RequestBody) body);
    }

}
