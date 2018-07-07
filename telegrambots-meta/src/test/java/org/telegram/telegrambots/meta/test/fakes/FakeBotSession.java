package org.telegram.telegrambots.meta.test.fakes;

import org.telegram.telegrambots.meta.generics.BotOptions;
import org.telegram.telegrambots.meta.generics.BotSession;
import org.telegram.telegrambots.meta.generics.LongPollingBot;

/**
 * @author Ruben Bermudez
 * @version 1.0
 */
public class FakeBotSession implements BotSession {
    @Override
    public void setToken(String token) {

    }

    @Override
    public void setCallback(LongPollingBot callback) {

    }

    @Override
    public void start() {

    }

    @Override
    public void stop() {

    }

    @Override
    public void setOptions(BotOptions options) {

    }

    @Override
    public boolean isRunning() {
        return false;
    }
}
