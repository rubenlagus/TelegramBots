package org.telegram.telegrambots.meta.test;

import org.junit.Assert;
import org.junit.Test;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.test.base.TestBase;
import org.telegram.telegrambots.meta.exceptions.TelegramApiRequestException;

/**
 * @author Ruben Bermudez
 * @version 1.0
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
