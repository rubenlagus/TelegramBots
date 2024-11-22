package org.telegram.telegrambots.meta.api.objects;

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
@RequiredArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Jacksonized
@JsonIgnoreProperties(ignoreUnknown = true)
public class MessageId implements BotApiObject {

    private static final String MESSAGEID_FIELD = "message_id";

    /**
     * Unique message identifier. In specific instances (e.g., message containing a video sent to a big chat),
     * the server might automatically schedule a message instead of sending it immediately.
     * In such cases, this field will be 0 and the relevant message will be unusable until it is actually sent
     */
    @JsonProperty(MESSAGEID_FIELD)
    private Long messageId;

}
