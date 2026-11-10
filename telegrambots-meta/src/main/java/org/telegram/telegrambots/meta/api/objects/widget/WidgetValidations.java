package org.telegram.telegrambots.meta.api.objects.widget;

import java.nio.charset.StandardCharsets;
import java.util.zip.Deflater;
import org.telegram.telegrambots.meta.api.interfaces.BotApiObject;
import org.telegram.telegrambots.meta.api.methods.botapimethods.PartialBotApiMethod;
import org.telegram.telegrambots.meta.exceptions.TelegramApiValidationException;

/** Shared validation for widget request and inline content fields. */
public final class WidgetValidations {
    private WidgetValidations() { }

    /** Validates required HTML: 1-32768 UTF-8 bytes and at most 4096 zlib bytes at maximum compression. */
    public static void validateHtml(String html, BotApiObject object) throws TelegramApiValidationException {
        String error = htmlError(html);
        if (error != null) {
            throw new TelegramApiValidationException(error, object);
        }
    }

    /** Validates required HTML for an API method using the same limits as the object overload. */
    public static void validateHtml(String html, PartialBotApiMethod<?> method) throws TelegramApiValidationException {
        String error = htmlError(html);
        if (error != null) {
            throw new TelegramApiValidationException(error, method);
        }
    }

    /** Validates optional data for an API object: at most 1024 UTF-8 bytes; null and empty are allowed. */
    public static void validateOptionalData(String data, String fieldName, BotApiObject object) throws TelegramApiValidationException {
        if (data != null && data.getBytes(StandardCharsets.UTF_8).length > 1024) {
            throw new TelegramApiValidationException(fieldName + " must not exceed 1024 UTF-8 bytes", object);
        }
    }

    /** Validates optional data for an API method using the same limits as the object overload. */
    public static void validateOptionalData(String data, String fieldName, PartialBotApiMethod<?> method) throws TelegramApiValidationException {
        if (data != null && data.getBytes(StandardCharsets.UTF_8).length > 1024) {
            throw new TelegramApiValidationException(fieldName + " must not exceed 1024 UTF-8 bytes", method);
        }
    }

    /** Validates literal captions; markup captions are left to Telegram because raw markup length is not the parsed length. */
    public static void validateCaption(String caption, String parseMode, BotApiObject object) throws TelegramApiValidationException {
        String error = captionError(caption, parseMode);
        if (error != null) {
            throw new TelegramApiValidationException(error, object);
        }
    }

    /** Validates literal captions for an API method using the same rule as the object overload. */
    public static void validateCaption(String caption, String parseMode, PartialBotApiMethod<?> method) throws TelegramApiValidationException {
        String error = captionError(caption, parseMode);
        if (error != null) {
            throw new TelegramApiValidationException(error, method);
        }
    }

    private static String captionError(String caption, String parseMode) {
        if (caption != null && parseMode == null && caption.codePointCount(0, caption.length()) > 1024) {
            return "caption must not exceed 1024 characters after entity parsing";
        }
        return null;
    }

    private static String htmlError(String html) {
        if (html == null) {
            return "html must be 1-32768 UTF-8 bytes";
        }
        byte[] bytes = html.getBytes(StandardCharsets.UTF_8);
        if (bytes.length == 0 || bytes.length > 32768) {
            return "html must be 1-32768 UTF-8 bytes";
        }
        Deflater deflater = new Deflater(Deflater.BEST_COMPRESSION);
        try {
            deflater.setInput(bytes);
            deflater.finish();
            byte[] buffer = new byte[1024];
            int size = 0;
            while (!deflater.finished()) {
                size += deflater.deflate(buffer);
                if (size > 4096) {
                    return "html must not exceed 4096 bytes when compressed with zlib";
                }
            }
            return null;
        } finally {
            deflater.end();
        }
    }
}