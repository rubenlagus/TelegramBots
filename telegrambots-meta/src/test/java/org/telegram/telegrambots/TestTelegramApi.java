package org.telegram.telegrambots;

import org.junit.Assert;
import org.junit.Test;
import org.telegram.telegrambots.base.TestBase;
import org.telegram.telegrambots.exceptions.TelegramApiRequestException;

/**
 * @author Ruben Bermudez
 * @version 1.0
 * @brief TODO
 * @date 29 of October of 2016
 */
public class TestTelegramApi extends TestBase {

    @Test
    public void TestTelegramApiMustBeInitializableForLongPolling() {
        new TelegramBotsApi();
    }

    @Test
    public void TestTelegramApiMustBeInitializableForWebhook() {
        try {
            new TelegramBotsApi("keyStore", "keyStorePassword", "externalUrl", "internalUrl");
        } catch (TelegramApiRequestException e) {
            Assert.fail();
        }
    }
}
