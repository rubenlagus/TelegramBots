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
 * The message was originally sent on behalf of a chat to a group chat.
 */

@EqualsAndHashCode(callSuper = false)
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Jacksonized
public class MessageOriginChat implements MessageOrigin {
    private static final String TYPE_FIELD = "type";
    private static final String DATE_FIELD = "date";
    private static final String SENDER_CHAT_FIELD = "sender_chat";
    private static final String AUTHOR_SIGNATURE_FIELD = "author_signature";

    /**
     * Type of the message origin, always “chat”
     */
    @JsonProperty(TYPE_FIELD)
    @Builder.Default
    private final String type = MessageOrigin.CHAT_TYPE;
    /**
     * Date the message was sent originally in Unix time
     */
    @JsonProperty(DATE_FIELD)
    private Integer date;
    /**
     * Chat that sent the message originally
     */
    @JsonProperty(SENDER_CHAT_FIELD)
    private Chat senderChat;
    /**
     * Optional.
     * For messages originally sent by an anonymous chat administrator, original message author signature
     */
    @JsonProperty(AUTHOR_SIGNATURE_FIELD)
    private String authorSignature;

}
