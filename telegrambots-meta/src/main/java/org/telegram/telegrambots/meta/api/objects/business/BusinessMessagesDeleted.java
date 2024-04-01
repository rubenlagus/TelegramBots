package org.telegram.telegrambots.meta.api.objects.business;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import lombok.extern.jackson.Jacksonized;
import org.telegram.telegrambots.meta.api.interfaces.BotApiObject;
import org.telegram.telegrambots.meta.api.objects.Chat;

import java.util.List;

/**
 * @author Ruben Bermudez
 * @version 7.2
 * This object is received when messages are deleted from a connected business account.
 */
@EqualsAndHashCode(callSuper = false)
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Jacksonized
@JsonIgnoreProperties(ignoreUnknown = true)
public class BusinessMessagesDeleted implements BotApiObject {
    private static final String BUSINESS_CONNECTION_ID_FIELD = "business_connection_id";
    private static final String CHAT_FIELD = "chat";
    private static final String MESSAGE_IDS_FIELD = "message_ids";

    /**
     * Unique identifier of the business connection
     */
    @JsonProperty(BUSINESS_CONNECTION_ID_FIELD)
    private String businessConnectionId;
    /**
     * Information about a chat in the business account.
     * The bot may not have access to the chat or the corresponding user.
     */
    @JsonProperty(CHAT_FIELD)
    private Chat chat;
    /**
     * A JSON-serialized list of identifiers of deleted messages in the chat of the business account
     */
    @JsonProperty(MESSAGE_IDS_FIELD)
    private List<Integer> messageIds;
}
