package org.telegram.telegrambots.client.okhttp;

import okhttp3.ResponseBody;
import org.telegram.telegrambots.meta.api.methods.botapimethods.PartialBotApiMethod;
import org.telegram.telegrambots.meta.exceptions.TelegramApiRequestException;

import java.io.IOException;
import java.io.Serializable;

class OkHttpFutureCallback<T extends Serializable, Method extends PartialBotApiMethod<T>> extends AbstractOkHttpFutureCallback<T> {
    private final Method method;

    OkHttpFutureCallback(Method method) {
        this.method = method;
    }

    @Override
    protected T getResponseValue(ResponseBody body) throws IOException, TelegramApiRequestException {
        return method.deserializeResponse(body.string());
    }
}
