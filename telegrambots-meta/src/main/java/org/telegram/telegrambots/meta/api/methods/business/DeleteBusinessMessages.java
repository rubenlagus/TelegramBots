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

import java.util.List;

/**
 * @author Ruben Bermudez
 * @version 9.0
 *
 * Delete messages on behalf of a business account.
 * Returns True on success.
 *
 * @apiNote Requires the can_delete_outgoing_messages business bot right to delete messages sent by the bot itself,
 * or the can_delete_all_messages business bot right to delete any message.
 */
@EqualsAndHashCode(callSuper = false)
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DeleteBusinessMessages extends BotApiMethodBoolean {
    public static final String PATH = "deleteBusinessMessages";

    private static final String BUSINESS_CONNECTION_ID_FIELD = "business_connection_id";
    private static final String MESSAGE_IDS_FIELD = "message_ids";

    /**
     * Unique identifier of the business connection on behalf of which to delete the messages
     */
    @JsonProperty(BUSINESS_CONNECTION_ID_FIELD)
    @NonNull
    private String businessConnectionId;
    /**
     * A JSON-serialized list of 1-100 identifiers of messages to delete.
     * All messages must be from the same chat.
     */
    @JsonProperty(MESSAGE_IDS_FIELD)
    @NonNull
    private List<Integer> messageIds;

    @Override
    public String getMethod() {
        return PATH;
    }

    @Override
    public void validate() throws TelegramApiValidationException {
        if (businessConnectionId.isEmpty()) {
            throw new TelegramApiValidationException("BusinessConnectionId parameter can't be empty string", this);
        }
        if (messageIds.isEmpty()) {
            throw new TelegramApiValidationException("MessageIds parameter can't be empty", this);
        }
        if (messageIds.size() > 100) {
            throw new TelegramApiValidationException("MessageIds parameter can't exceed 100 elements", this);
        }
        for (Integer messageId : messageIds) {
            if (messageId == null) {
                throw new TelegramApiValidationException("MessageIds parameter can't contain null elements", this);
            }
        }
    }
}
