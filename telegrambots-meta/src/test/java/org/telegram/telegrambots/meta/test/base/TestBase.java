package org.telegram.telegrambots.meta.test.base;

import org.junit.BeforeClass;
import org.telegram.telegrambots.meta.ApiContext;
import org.telegram.telegrambots.meta.test.fakes.FakeBotSession;
import org.telegram.telegrambots.meta.test.fakes.FakeWebhook;
import org.telegram.telegrambots.meta.generics.BotSession;
import org.telegram.telegrambots.meta.generics.Webhook;

/**
 * @author Ruben Bermudez
 * @version 1.0
 */
public abstract class TestBase {

    @BeforeClass
    public static void beforeClass() {
        ApiContext.register(BotSession.class, FakeBotSession.class);
        ApiContext.register(Webhook.class, FakeWebhook.class);
    }
}
