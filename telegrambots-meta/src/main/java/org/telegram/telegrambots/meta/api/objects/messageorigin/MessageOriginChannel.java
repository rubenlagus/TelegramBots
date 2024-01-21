package org.telegram.telegrambots.meta.api.objects.messageorigin;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import lombok.extern.jackson.Jacksonized;
import org.telegram.telegrambots.meta.api.objects.Chat;

/**
 * @author Ruben Bermudez
 * @version 7.0
 * The message was originally sent to a channel chat.
 */

@EqualsAndHashCode(callSuper = false)
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Jacksonized
public class MessageOriginChannel implements MessageOrigin {
    private static final String TYPE_FIELD = "type";
    private static final String DATE_FIELD = "date";
    private static final String CHAT_FIELD = "chat";
    private static final String MESSAGE_ID_FIELD = "message_id";
    private static final String AUTHOR_SIGNATURE_FIELD = "author_signature";

    /**
     * Type of the message origin, always “channel”
     */
    @JsonProperty(TYPE_FIELD)
    @Builder.Default
    private final String type = MessageOrigin.CHANNEL_TYPE;
    /**
     * Date the message was sent originally in Unix time
     */
    @JsonProperty(DATE_FIELD)
    private Integer date;
    /**
     * Channel chat to which the message was originally sent
     */
    @JsonProperty(CHAT_FIELD)
    private Chat chat;
    /**
     * Unique message identifier inside the chat
     */
    @JsonProperty(MESSAGE_ID_FIELD)
    private Integer messageId;
    /**
     * Optional.
     * Signature of the original post author
     */
    @JsonProperty(AUTHOR_SIGNATURE_FIELD)
    private String authorSignature;

}
