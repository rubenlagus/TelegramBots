package org.telegram.telegrambots.meta.api.methods.send;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.telegram.telegrambots.meta.api.methods.botapimethods.PartialBotApiMethod;
import org.telegram.telegrambots.meta.api.objects.ephemeral.EphemeralMessageParameters;
import org.telegram.telegrambots.meta.exceptions.TelegramApiValidationException;
import tools.jackson.core.JacksonException;
import tools.jackson.databind.JsonNode;
import tools.jackson.databind.json.JsonMapper;

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

    static boolean isCallbackOnly(JsonNode parameters) {
        if (parameters == null || !parameters.isObject()) {
            return false;
        }
        JsonNode receiverUserId = parameters.get("receiver_user_id");
        JsonNode callbackQueryId = parameters.get("callback_query_id");
        return (receiverUserId == null || receiverUserId.isNull())
                && callbackQueryId != null && callbackQueryId.isString() && !callbackQueryId.stringValue().isEmpty();
    }

    static boolean isCallbackOnlyRequest(String serializedRequest, JsonMapper mapper) throws JacksonException {
        JsonNode request = mapper.readTree(serializedRequest);
        return request != null && isCallbackOnly(request.get("ephemeral_message_parameters"));
    }

    static boolean isCallbackOnlyParameters(String serializedParameters, JsonMapper mapper) throws JacksonException {
        return serializedParameters != null && isCallbackOnly(mapper.readTree(serializedParameters));
    }

    static void rejectCallbackOnly(PartialBotApiMethod<?> method, boolean callbackOnly) throws TelegramApiValidationException {
        if (callbackOnly && method instanceof CallbackReplyMethod) {
            throw new TelegramApiValidationException("Callback-only replies require executeWithResult or executeWithResultAsync", method);
        }
    }
}