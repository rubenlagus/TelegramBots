package org.telegram.telegrambots.test.base;

import org.junit.BeforeClass;
import org.telegram.telegrambots.ApiContext;
import org.telegram.telegrambots.test.fakes.FakeBotSession;
import org.telegram.telegrambots.test.fakes.FakeWebhook;
import org.telegram.telegrambots.generics.BotSession;
import org.telegram.telegrambots.generics.Webhook;

/**
 * @author Ruben Bermudez
 * @version 1.0
 * @brief TODO
 * @date 29 of October of 2016
 */
public abstract class TestBase {

    @BeforeClass
    public static void beforeClass() {
        ApiContext.register(BotSession.class, FakeBotSession.class);
        ApiContext.register(Webhook.class, FakeWebhook.class);
    }
}
