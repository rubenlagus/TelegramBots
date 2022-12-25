package org.telegram.telegrambots.client;

import org.telegram.telegrambots.meta.api.methods.PartialBotApiMethod;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.Serializable;
import java.util.concurrent.CompletableFuture;

public interface TelegramClient {
    <T extends Serializable, Method extends PartialBotApiMethod<T>> CompletableFuture<T> executeAsync(Method method) throws TelegramApiException;
    <T extends Serializable, Method extends PartialBotApiMethod<T>> T execute(Method method) throws TelegramApiException;
}
