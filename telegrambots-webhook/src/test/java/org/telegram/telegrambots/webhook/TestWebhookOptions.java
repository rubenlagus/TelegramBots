package org.telegram.telegrambots.webhook;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.File;

import static org.junit.jupiter.api.Assertions.fail;

/**
 * @author Ruben Bermudez
 * @version 1.0
 */
public class TestWebhookOptions {

    @TempDir
    private File temporaryKeyStoreFile;

    @Test
    public void TestWhenHttpsEnabledKeyStoreFileMustBePresent() {
        WebhookOptions webhookOptions = new WebhookOptions();
        webhookOptions.setUseHttps(true);
        webhookOptions.setKeyStorePath(temporaryKeyStoreFile.getAbsolutePath());
        try {
            webhookOptions.validate();
        } catch (TelegramApiException e) {
            fail("Exception raised during Https webhook options validation");
        }
    }

    @Test
    public void TestWhenHttpsEnabledAndKeyStoreFileNotPresentExceptionIsRaised() {
        WebhookOptions webhookOptions = new WebhookOptions();
        webhookOptions.setUseHttps(true);
        webhookOptions.setKeyStorePath("/Random/path");
        try {
            webhookOptions.validate();
            fail("Exception should have been raised during Https webhook options validation");
        } catch (TelegramApiException e) {
            // Ignore
        }
    }

}
