package org.telegram.telegrambots.meta.api.objects.messageorigin;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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

/**
 * @author Ruben Bermudez
 * @version 7.0
 * The message was originally sent by an unknown user.
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
public class MessageOriginHiddenUser implements MessageOrigin {
    private static final String TYPE_FIELD = "type";
    private static final String DATE_FIELD = "date";
    private static final String SENDER_USER_NAME_FIELD = "sender_user_name";

    /**
     * Type of the message origin, always “hidden_user”
     */
    @JsonProperty(TYPE_FIELD)
    @Builder.Default
    private final String type = MessageOrigin.HIDDEN_USER_TYPE;
    /**
     * Date the message was sent originally in Unix time
     */
    @JsonProperty(DATE_FIELD)
    private Integer date;
    /**
     * Name of the user that sent the message originally
     */
    @JsonProperty(SENDER_USER_NAME_FIELD)
    private String senderUserName;

}
