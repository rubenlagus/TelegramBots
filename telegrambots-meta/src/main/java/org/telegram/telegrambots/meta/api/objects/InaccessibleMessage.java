package org.telegram.telegrambots.meta.api.objects;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * This object represents a unique message identifier.
 * @author Ruben Bermudez
 * @version 5.0
 */
@SuppressWarnings("WeakerAccess")
@EqualsAndHashCode(callSuper = false)
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class InaccessibleMessage implements MaybeInaccessibleMessage {

    private static final String CHAT_FIELD = "chat";
    private static final String MESSAGE_ID_FIELD = "message_id";
    private static final String DATE_FIELD = "date";

    /**
     * Chat the message belonged to
     */
    @JsonProperty(CHAT_FIELD)
    private Chat chat;
    /**
     * Unique message identifier
     */
    @JsonProperty(MESSAGE_ID_FIELD)
    private Integer messageId;
    /**
     * Always 0.
     * The field can be used to differentiate regular and inaccessible messages.
     */
    @JsonProperty(DATE_FIELD)
    private Integer DATE;

    @Override
    public Long getChatId() {
        return chat.getId();
    }
}
