package org.telegram.telegrambots;

import org.apache.hc.core5.util.Timeout;

/**
 * @author Ruben Bermudez
 * @version 1.0
 * Constants needed for Telegram Bots API
 */
public class Constants {
    public static final Timeout SOCKET_TIMEOUT = Timeout.ofSeconds(75);
    public static final String WEBHOOK_URL_PATH = "callback";
}
