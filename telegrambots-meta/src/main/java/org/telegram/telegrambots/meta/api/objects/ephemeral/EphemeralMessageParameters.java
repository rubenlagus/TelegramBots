package org.telegram.telegrambots.meta.api.objects.ephemeral;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import lombok.extern.jackson.Jacksonized;
import org.telegram.telegrambots.meta.api.interfaces.BotApiObject;
import org.telegram.telegrambots.meta.api.interfaces.Validable;
import org.telegram.telegrambots.meta.exceptions.TelegramApiValidationException;

/**
 * @author Ruben Bermudez
 * @version 10.3
 * Describes the parameters of an ephemeral message to send.
 */
@EqualsAndHashCode(callSuper = false)
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Jacksonized
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class EphemeralMessageParameters implements BotApiObject, Validable {
    private static final String RECEIVER_USER_ID_FIELD = "receiver_user_id";
    private static final String CALLBACK_QUERY_ID_FIELD = "callback_query_id";
    private static final String REPLACE_CALLBACK_QUERY_MESSAGE_FIELD = "replace_callback_query_message";

    /**
     * Identifier of the user who will receive the message.
     * It is not guaranteed that the user will receive the message, especially if they are offline.
     *
     * @implNote Required by the Bot API, but not enforced by the constructor so that the deprecated
     * per-field setters on the send methods can populate this object in any order.
     * Enforced by {@link #validate()} instead.
     */
    @JsonProperty(RECEIVER_USER_ID_FIELD)
    private Long receiverUserId;
    /**
     * Optional. Identifier of the callback query which triggered the message, if any
     */
    @JsonProperty(CALLBACK_QUERY_ID_FIELD)
    private String callbackQueryId;
    /**
     * Optional. Pass True if the ephemeral message must be shown in place of the original message
     */
    @JsonProperty(REPLACE_CALLBACK_QUERY_MESSAGE_FIELD)
    private Boolean replaceCallbackQueryMessage;

    @Override
    public void validate() throws TelegramApiValidationException {
        if (receiverUserId == null) {
            throw new TelegramApiValidationException("ReceiverUserId parameter can't be empty", this);
        }
    }
}
