package org.telegram.telegrambots.webhook;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.File;

/**
 * @author Ruben Bermudez
 * @version 1.0
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class WebhookOptions {
    @Builder.Default
    private Integer port = 9091;

    @Builder.Default
    private Boolean enableRequestLogging = false;

    @Builder.Default
    private Boolean useHttps = false;

    private String keyStorePath;

    private String keyStorePassword;

    public void validate() throws TelegramApiException {
        if (useHttps) {
            File file = new File(keyStorePath);
            if (!file.exists() || !file.canRead()) {
                throw new TelegramApiException("Can't find or access server keystore file.");
            }
        }
    }
}
