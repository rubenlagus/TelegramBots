package org.telegram.telegrambots.longpolling.exceptions;

import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public class TelegramApiErrorResponseException extends TelegramApiException {

    public TelegramApiErrorResponseException(String message) {
        this(message, null);
    }

    public TelegramApiErrorResponseException(Throwable cause) {
        this(null, cause);
    }

    public TelegramApiErrorResponseException(String message, Throwable cause) {
        super(message, cause);
    }
}
