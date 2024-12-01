package org.telegram.telegrambots.meta.util;

import lombok.NonNull;
import org.telegram.telegrambots.meta.api.interfaces.BotApiObject;
import org.telegram.telegrambots.meta.api.methods.botapimethods.PartialBotApiMethod;
import org.telegram.telegrambots.meta.exceptions.TelegramApiValidationException;

public final class Validations {

    public static final String CHAT_ID_VALIDATION = "chatId can't be empty";
    public static final String USER_ID_VALIDATION = "userId can't be empty";

    private Validations() { }

    public static void requiredUserId(@NonNull Long userId, PartialBotApiMethod<?> method) throws TelegramApiValidationException {
        if (userId == 0) {
            throw new TelegramApiValidationException(USER_ID_VALIDATION, method);
        }
    }

    public static void requiredUserId(@NonNull Long userId, BotApiObject object) throws TelegramApiValidationException {
        if (userId == 0) {
            throw new TelegramApiValidationException(USER_ID_VALIDATION, object);
        }
    }

    public static void requiredChatId(@NonNull String chatId, PartialBotApiMethod<?> method) throws TelegramApiValidationException {
        if (chatId.isEmpty()) {
            throw new TelegramApiValidationException(CHAT_ID_VALIDATION, method);
        }
    }

    public static void requiredChatId(@NonNull String chatId, BotApiObject object) throws TelegramApiValidationException {
        if (chatId.isEmpty()) {
            throw new TelegramApiValidationException(CHAT_ID_VALIDATION, object);
        }
    }
}
