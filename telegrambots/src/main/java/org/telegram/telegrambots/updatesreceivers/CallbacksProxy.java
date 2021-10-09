package org.telegram.telegrambots.updatesreceivers;

import org.telegram.telegrambots.meta.generics.WebhookBot;

import java.util.concurrent.ConcurrentHashMap;

public class CallbacksProxy {

    private final ConcurrentHashMap<String, WebhookBot> callbacks = new ConcurrentHashMap<>();

    public boolean containsBotPath(String botPath) {
        return callbacks.containsKey(botPath);
    }

    public WebhookBot getWebhookBot(String botPath) {
        return callbacks.get(botPath);
    }

    public void registerCallback(WebhookBot callback) {
        if (!callbacks.containsKey(callback.getBotPath())) {
            callbacks.put(callback.getBotPath(), callback);
        }
    }

}
