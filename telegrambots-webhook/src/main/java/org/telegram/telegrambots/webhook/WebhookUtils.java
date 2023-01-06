package org.telegram.telegrambots.webhook;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * @author Ruben Bermudez
 * @version 1.0
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class WebhookUtils {
    private static final int SOCKET_TIMEOUT = 75 * 1000;
    private static final String TEXT_PLAIN_CONTENT_TYPE = "text/plain";

    // TODO Add setWebhook request using telegram-client module
}
