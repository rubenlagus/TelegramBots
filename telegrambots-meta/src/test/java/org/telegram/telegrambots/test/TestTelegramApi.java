package org.telegram.telegrambots.test;

import org.junit.Assert;
import org.junit.Test;
import org.telegram.telegrambots.TelegramBotsApi;
import org.telegram.telegrambots.test.base.TestBase;
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
    public void TestTelegramApiMustBeInitializableForWebhookWithoutSecureSupport() {
        try {
            new TelegramBotsApi("externalUrl", "internalUrl");
        } catch (TelegramApiRequestException e) {
            Assert.fail();
        }
    }

    @Test
    public void TestTelegramApiMustBeInitializableForWebhook() {
        try {
            new TelegramBotsApi("keyStore", "keyStorePassword", "externalUrl", "internalUrl");
        } catch (TelegramApiRequestException e) {
            Assert.fail();
        }
    }

    @Test
    public void TestTelegramApiMustBeInitializableForWebhookWithSelfSignedCertificate() {
        try {
            new TelegramBotsApi("keyStore", "keyStorePassword", "externalUrl", "internalUrl", "selfSignedPath");
        } catch (TelegramApiRequestException e) {
            Assert.fail();
        }
    }
}
