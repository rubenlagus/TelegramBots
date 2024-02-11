package org.telegram.telegrambots.meta.api.objects.messageorigin;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.telegram.telegrambots.meta.api.objects.Chat;

/**
 * @author Ruben Bermudez
 * @version 7.0
 * The message was originally sent on behalf of a chat to a group chat.
 */
@JsonDeserialize
@EqualsAndHashCode(callSuper = false)
@Getter
@Setter
@ToString
@NoArgsConstructor(force = true)
@AllArgsConstructor
@Builder
public class MessageOriginChat implements MessageOrigin {
    private static final String TYPE_FIELD = "type";
    private static final String DATE_FIELD = "date";
    private static final String SENDER_CHAT_FIELD = "sender_chat";
    private static final String AUTHOR_SIGNATURE_FIELD = "author_signature";

    /**
     * Type of the message origin, always “chat”
     */
    @JsonProperty(TYPE_FIELD)
    private String type;
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
