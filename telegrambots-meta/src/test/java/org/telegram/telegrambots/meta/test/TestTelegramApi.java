package org.telegram.telegrambots.meta.test;

import org.junit.jupiter.api.Test;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiRequestException;
import org.telegram.telegrambots.meta.test.base.TestBase;

import static org.junit.jupiter.api.Assertions.fail;

/**
 * @author Ruben Bermudez
 * @version 1.0
 */
class TestTelegramApi extends TestBase {

    @Test
    void TestTelegramApiMustBeInitializableForLongPolling() {
        new TelegramBotsApi();
    }

    @Test
    void TestTelegramApiMustBeInitializableForWebhookWithoutSecureSupport() {
        try {
            new TelegramBotsApi("externalUrl", "internalUrl");
        } catch (TelegramApiRequestException e) {
            fail();
        }
    }

    @Test
    void TestTelegramApiMustBeInitializableForWebhook() {
        try {
            new TelegramBotsApi("keyStore", "keyStorePassword", "externalUrl", "internalUrl");
        } catch (TelegramApiRequestException e) {
            fail();
        }
    }

    @Test
    void TestTelegramApiMustBeInitializableForWebhookWithSelfSignedCertificate() {
        try {
            new TelegramBotsApi("keyStore", "keyStorePassword", "externalUrl", "internalUrl", "selfSignedPath");
        } catch (TelegramApiRequestException e) {
            fail();
        }
    }
}
