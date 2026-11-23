package org.telegram.telegrambots.meta.util;

import org.telegram.telegrambots.meta.api.methods.botapimethods.PartialBotApiMethod;
import org.telegram.telegrambots.meta.api.objects.ephemeral.EphemeralMessageParameters;
import org.telegram.telegrambots.meta.exceptions.TelegramApiValidationException;

public final class SendMessageValidations {
    private SendMessageValidations() { }

    public static void validateChatId(String chatId, String businessConnectionId,
                                      EphemeralMessageParameters ephemeralMessageParameters,
                                      PartialBotApiMethod<?> method) throws TelegramApiValidationException {
        boolean callbackOnly = ephemeralMessageParameters != null
                && ephemeralMessageParameters.getReceiverUserId() == null
                && ephemeralMessageParameters.getCallbackQueryId() != null
                && !ephemeralMessageParameters.getCallbackQueryId().isEmpty();
        if (callbackOnly && businessConnectionId != null) {
            throw new TelegramApiValidationException("Business connection is not supported for callback-only messages", method);
        }
        if (!callbackOnly && (chatId == null || chatId.isEmpty())) {
            throw new TelegramApiValidationException(Validations.CHAT_ID_VALIDATION, method);
        }
    }
}