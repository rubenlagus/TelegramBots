package org.telegram.telegrambots.longpolling.exceptions;

import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public class TelegramApiErrorResponseException extends TelegramApiException {
    private final int code;

    public TelegramApiErrorResponseException(String message) {
        this(message, null);
    }

    public TelegramApiErrorResponseException(Throwable cause) {
        this(null, cause);
    }

    public TelegramApiErrorResponseException(String message, Throwable cause) {
        super(message, cause);
        this.code = -1;
    }

    public TelegramApiErrorResponseException(int code, String message) {
        super(message);
        this.code = code;
    }

    @Override
    public String toString() {
        return code + ": " + super.toString();
    }
}
