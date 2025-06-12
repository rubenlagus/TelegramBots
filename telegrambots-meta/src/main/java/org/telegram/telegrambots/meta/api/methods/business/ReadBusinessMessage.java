package org.telegram.telegrambots.meta.api.methods.business;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;
import org.telegram.telegrambots.meta.api.methods.botapimethods.BotApiMethodBoolean;
import org.telegram.telegrambots.meta.exceptions.TelegramApiValidationException;

/**
 * @author Ruben Bermudez
 * @version 9.0
 *
 * Marks incoming message as read on behalf of a business account.
 * Returns True on success.
 *
 * @apiNote Requires the can_read_messages business bot right.
 */
@EqualsAndHashCode(callSuper = false)
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReadBusinessMessage extends BotApiMethodBoolean {
    public static final String PATH = "readBusinessMessage";

    private static final String BUSINESS_CONNECTION_ID_FIELD = "business_connection_id";
    private static final String CHAT_ID_FIELD = "chat_id";
    private static final String MESSAGE_ID_FIELD = "message_id";

    /**
     * Unique identifier of the business connection on behalf of which to read the message
     */
    @JsonProperty(BUSINESS_CONNECTION_ID_FIELD)
    @NonNull
    private String businessConnectionId;
    /**
     * Unique identifier of the chat in which the message was received.
     * The chat must have been active in the last 24 hours.
     */
    @JsonProperty(CHAT_ID_FIELD)
    @NonNull
    private Long chatId;
    /**
     *Unique identifier of the message to mark as read
     */
    @JsonProperty(MESSAGE_ID_FIELD)
    @NonNull
    private Long messageId;

    @Override
    public String getMethod() {
        return PATH;
    }

    @Override
    public void validate() throws TelegramApiValidationException {
        if (businessConnectionId.isEmpty()) {
            throw new TelegramApiValidationException("BusinessConnectionId parameter can't be empty string", this);
        }
    }
}
