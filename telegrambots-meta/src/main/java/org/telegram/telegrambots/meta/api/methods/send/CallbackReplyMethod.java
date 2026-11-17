package org.telegram.telegrambots.meta.api.methods.send;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.telegram.telegrambots.meta.api.methods.botapimethods.PartialBotApiMethod;
import org.telegram.telegrambots.meta.api.objects.ephemeral.EphemeralMessageParameters;
import org.telegram.telegrambots.meta.exceptions.TelegramApiValidationException;

/** A send request that can return callback-query success instead of a message. */
public interface CallbackReplyMethod {
    String getChatId();
    String getBusinessConnectionId();
    EphemeralMessageParameters getEphemeralMessageParameters();

    @JsonIgnore
    default boolean isCallbackOnly() {
        EphemeralMessageParameters parameters = getEphemeralMessageParameters();
        return parameters != null && parameters.getReceiverUserId() == null
                && parameters.getCallbackQueryId() != null && !parameters.getCallbackQueryId().isEmpty();
    }

    /** Reject a callback-only request on an API that can only return a message. */
    static void rejectCallbackOnly(PartialBotApiMethod<?> method) throws TelegramApiValidationException {
        if (method instanceof CallbackReplyMethod && ((CallbackReplyMethod) method).isCallbackOnly()) {
            throw new TelegramApiValidationException("Callback-only replies require executeWithResult or executeWithResultAsync", method);
        }
    }
}