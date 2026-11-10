package org.telegram.telegrambots.meta.api.objects.message;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import org.telegram.telegrambots.meta.api.methods.botapimethods.PartialBotApiMethod;
import org.telegram.telegrambots.meta.api.objects.ApiResponse;
import org.telegram.telegrambots.meta.exceptions.TelegramApiRequestException;
import tools.jackson.core.JacksonException;
import tools.jackson.databind.JsonNode;
import tools.jackson.databind.json.JsonMapper;

import java.io.Serializable;

/** A normal sent message or successful callback-only ephemeral reply. */
@Getter
@ToString
@EqualsAndHashCode
@JsonInclude(JsonInclude.Include.NON_NULL)
public final class SendMessageResult implements Serializable {
    private static final JsonMapper OBJECT_MAPPER = new JsonMapper();

    private final Message message;
    private final boolean callbackSuccess;

    @JsonCreator
    public SendMessageResult(@JsonProperty("message") Message message,
                             @JsonProperty("callbackSuccess") boolean callbackSuccess) {
        if ((message != null) == callbackSuccess) {
            throw new IllegalArgumentException("Exactly one send result variant is required");
        }
        this.message = message;
        this.callbackSuccess = callbackSuccess;
    }

    public static SendMessageResult message(Message message) {
        return new SendMessageResult(message, false);
    }

    public static SendMessageResult callbackSuccess() {
        return new SendMessageResult(null, true);
    }

    /** Parses a Telegram response, preserving API error details. */
    public static SendMessageResult deserializeResponse(String answer, PartialBotApiMethod<?> method)
            throws TelegramApiRequestException {
        try {
            JsonNode root = OBJECT_MAPPER.readTree(answer);
            if (root == null || !root.isObject() || !root.has("ok") || !root.get("ok").isBoolean()) {
                throw new TelegramApiRequestException("Unable to deserialize response: missing boolean ok");
            }
            if (!root.get("ok").booleanValue()) {
                ApiResponse<?> response = OBJECT_MAPPER.treeToValue(root, ApiResponse.class);
                throw new TelegramApiRequestException("Error executing " + method.getClass().getName() + " query", response);
            }
            JsonNode result = root.get("result");
            if (result != null && result.isBoolean() && result.booleanValue()) {
                return callbackSuccess();
            }
            if (result == null || !result.isObject() || !result.path("message_id").isIntegralNumber()
                    || !result.path("date").isIntegralNumber() || !result.path("chat").isObject()) {
                throw new TelegramApiRequestException("Unable to deserialize response: expected a message or true");
            }
            return message(OBJECT_MAPPER.treeToValue(result, Message.class));
        } catch (JacksonException | IllegalArgumentException e) {
            throw new TelegramApiRequestException("Unable to deserialize response", e);
        }
    }
}